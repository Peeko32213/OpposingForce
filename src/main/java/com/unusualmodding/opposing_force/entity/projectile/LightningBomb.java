package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.network.ElectricChargeSyncS2CPacket;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPMobEffects;
import com.unusualmodding.opposing_force.registry.OPNetwork;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class LightningBomb extends ThrowableItemProjectile {

    private static final EntityDataAccessor<Float> EXPLOSION_RADIUS = SynchedEntityData.defineId(LightningBomb.class, EntityDataSerializers.FLOAT);

    protected int fuse = 0;
    protected int prevFuse;
    protected int maxFuse = 80;
    protected float spin = 0;
    protected float prevSpin;

    public LightningBomb(EntityType<? extends LightningBomb> entity, Level level) {
        super(entity, level);
    }

    public LightningBomb(Level level, LivingEntity entity) {
        super(OPEntities.LIGHTNING_BOMB.get(), entity, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return OPItems.LIGHTNING_BOMB.get();
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(EXPLOSION_RADIUS, 5.0F);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat("ExplosionRadius", this.getExplosionRadius());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setExplosionRadius(compoundTag.getInt("ExplosionRadius"));
    }

    public void setExplosionRadius(float radius) {
        this.entityData.set(EXPLOSION_RADIUS, radius);
    }

    public float getExplosionRadius() {
        return this.entityData.get(EXPLOSION_RADIUS);
    }

    @Override
    public void tick() {
        super.tick();
        this.fuse++;
        if (this.level().isClientSide) {
            prevFuse = fuse;
            prevSpin = spin;
            if (fuse > 5) {
                float horizontalSpeed = 0.01F;
                Vec3 direction = this.getPosition(0).subtract(this.getPosition(1));
                Vec3 particlePosition = this.getPosition(1).add(direction.normalize().scale(0.25F));
                this.level().addParticle(ParticleTypes.SMOKE, particlePosition.x(), particlePosition.y(), particlePosition.z(), random.nextGaussian() * horizontalSpeed, Mth.abs((float) random.nextGaussian()) * horizontalSpeed * 2, random.nextGaussian() * horizontalSpeed);
            }

            this.prevSpin = this.spin;
            Vec3 deltaMovement = this.getPosition(0).subtract(this.getPosition(1));
            float spinAmount = (float) (deltaMovement.length() / (Mth.TWO_PI * 6F));
            float spinInRadians = spinAmount * Mth.TWO_PI * 2F;
            this.spin += spinInRadians;
        }
        if (fuse > maxFuse) {
            this.detonate();
        }
        if (this.isOnFire() || this.isInWaterOrBubble()) {
            this.detonate();
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        this.detonate();
    }

    protected void detonate() {
        this.createExplosion();
        this.discard();
    }

    protected void createExplosion() {
        Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
        float radius = this.getExplosionRadius();
        if (!this.level().isClientSide) {
            for (int i = 0; i < 16; i++) {
                ElectricChargeSyncS2CPacket packet = ElectricChargeSyncS2CPacket.builder()
                        .pos(location.x(), location.y(), location.z())
                        .range(6 + this.random.nextInt(2))
                        .size(0.08F)
                        .color(0.3F + (this.random.nextFloat() / 8), 0.5F + (this.random.nextFloat() / 8), 0.8F + (this.random.nextFloat() / 8), 1F)
                        .build();
                OPNetwork.sendToClients(packet);
            }
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.level().playSound(null, location.x(), location.y(), location.z(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.NEUTRAL, 2.5F, 1.8F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }

        for (Entity entity : this.level().getEntities(this, new AABB(location.subtract(radius, radius, radius), location.add(radius, radius, radius)))) {
            if (entity.distanceToSqr(location) > radius * radius) {
                continue;
            }
            float scaledDistance = (float) (1 - (entity.position().distanceTo(location) / radius));
            float damage = Mth.lerp(Mth.sqrt(scaledDistance), 8, 16);
            Vec3 knockback = entity.position().add(0, entity.getBbHeight() * 0.5, 0).subtract(location).normalize().scale(Mth.sqrt(scaledDistance));
            entity.hurt(entity.damageSources().explosion(this, this.getOwner()), damage);
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(OPMobEffects.ELECTRIFIED.get(), 300), this.getOwner());
                if (livingEntity.isDamageSourceBlocked(entity.damageSources().explosion(this, this.getOwner()))) {
                    knockback = knockback.scale(3);
                }
            }
            entity.setOnGround(false);
            entity.setDeltaMovement(entity.getDeltaMovement().add(knockback));
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
            for (int i = 0; i < 16; i++) {
                this.level().addParticle(ParticleTypes.LARGE_SMOKE, location.x(), location.y(), location.z(), 0, 0, 0);
            }
            this.level().addParticle(ParticleTypes.FLASH, true, location.x(), location.y(), location.z(), 0, 0, 0);
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        Vec3 motion = this.getDeltaMovement();
        float conservedEnergy = 0.5F;
        if (motion.lengthSqr() < 0.1) {
            this.setDeltaMovement(Vec3.ZERO);
            if (!this.onGround()) {
                this.playSound(SoundEvents.CHAIN_HIT, 1F, 1F);
            }
            this.setOnGround(true);
            return;
        }

        Direction direction = result.getDirection();
        switch (direction.getAxis()) {
            case X -> {
                this.setDeltaMovement(-motion.x() * conservedEnergy, motion.y(), motion.z());
                this.playSound(SoundEvents.CHAIN_HIT, 1F, 1F);
            }
            case Y -> {
                this.setDeltaMovement(motion.x() * conservedEnergy, -motion.y() * conservedEnergy, motion.z() * conservedEnergy);
                this.playSound(SoundEvents.CHAIN_HIT, 1F, 1F);
            }
            case Z -> {
                this.setDeltaMovement(motion.x(), motion.y(), -motion.z() * conservedEnergy);
                this.playSound(SoundEvents.CHAIN_HIT, 1F, 1F);
            }
        }
    }

    public float getFuse(float partialTicks) {
        return Mth.lerp(partialTicks, prevFuse, fuse) / (float) (maxFuse - 2);
    }

    public float getSpin(float partialTicks) {
        return Mth.lerp(partialTicks, prevSpin, spin);
    }
}