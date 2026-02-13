package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.Volt;
import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPMobEffects;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import com.unusualmodding.opposing_force.utils.OPMath;
import com.unusualmodding.opposing_force.utils.ParticleUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class ElectricCharge extends FrictionlessProjectile {

    private static final EntityDataAccessor<Float> CHARGE_SCALE = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> QUASAR = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.FLOAT);

    public ElectricCharge(EntityType<? extends FrictionlessProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public ElectricCharge(LivingEntity entity, Level level, double x, double y, double z) {
        super(OPEntities.ELECTRIC_CHARGE.get(), level, entity, x, y, z);
        this.setOwner(entity);
    }

    public ElectricCharge(Level level, double x, double y, double z, Vec3 movement) {
        super(OPEntities.ELECTRIC_CHARGE.get(), x, y, z, movement, level);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(QUASAR, false);
        this.getEntityData().define(CHARGE_SCALE, 1.0F);
        this.getEntityData().define(DAMAGE, 5.0F);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat("Damage", this.getChargeDamage());
        compoundTag.putFloat("ChargeScale", this.getChargeScale());
        compoundTag.putBoolean("Quasar", this.isQuasar());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setChargeDamage(compoundTag.getFloat("Damage"));
        this.setChargeScale(compoundTag.getFloat("ChargeScale"));
        this.setQuasar(compoundTag.getBoolean("Quasar"));
    }

    public float getChargeDamage() {
        return this.entityData.get(DAMAGE);
    }
    public void setChargeDamage(float damage) {
        this.entityData.set(DAMAGE, damage);
    }

    public float getChargeScale() {
        return this.entityData.get(CHARGE_SCALE);
    }
    public void setChargeScale(float scale) {
        this.entityData.set(CHARGE_SCALE, scale);
    }

    public boolean isQuasar() {
        return this.entityData.get(QUASAR);
    }
    public void setQuasar(boolean quasar) {
        this.entityData.set(QUASAR, quasar);
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);
        if (hitResult instanceof BlockHitResult blockHitResult) {
            BlockState state = this.level().getBlockState(blockHitResult.getBlockPos());
            if (!state.getCollisionShape(this.level(), blockHitResult.getBlockPos()).isEmpty()) {
                this.createExplosion(this.getChargeScale() * 4.0F, 6);
                if (!this.level().isClientSide) this.discard();
            }
        }
        else if (hitResult instanceof EntityHitResult entityHitResult && !(entityHitResult.getEntity() instanceof ElectricCharge)) {
            this.createExplosion(this.getChargeScale() * 4.0F, 6);
            if (!this.level().isClientSide) this.discard();
        }
    }

    protected void createExplosion(float radius, int particleRange) {
        Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
        if (!this.level().isClientSide) {
            this.spawnElectricParticles(this, particleRange + random.nextInt(particleRange), 0.25F, 16);
            this.level().playSound(null, location.x(), location.y(), location.z(), OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 2.5F, 1.25F + (this.random.nextFloat() - this.random.nextFloat()) * 0.25F);
        }
        for (Entity entity : this.level().getEntities(this, new AABB(location.subtract(radius, radius, radius), location.add(radius, radius, radius)))) {
            if (entity.distanceToSqr(location) > radius * radius || !OPMath.hasLineOfSight(this, entity) || entity == this.getOwner()) {
                continue;
            }
            if (entity instanceof LivingEntity livingEntity) {
                this.doDamage(livingEntity, this.getChargeDamage() * 0.5F, this.getChargeDamage());
                livingEntity.addEffect(new MobEffectInstance(OPMobEffects.ELECTRIFIED.get(), 200, 0));
                this.doKnockback(livingEntity, radius, 0.75D, 0.5D);
            }
        }
    }

    protected void doKnockback(LivingEntity entity, float radius, double horizontalMultiplier, double verticalMultiplier) {
        Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
        float scaledDistance = (float) (1 - (entity.position().distanceTo(location) / radius));
        double knockbackResistance = 1.0 - Mth.clamp(entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 0.0, 1.0);
        Vec3 knockback = entity.position().add(0, entity.getBbHeight() * 0.75, 0).subtract(location).normalize().scale(Mth.sqrt(scaledDistance) * knockbackResistance);
        if (!this.level().isClientSide) entity.hurtMarked = true;
        entity.setOnGround(false);
        entity.setDeltaMovement(entity.getDeltaMovement().add(knockback.x() * horizontalMultiplier, knockback.y() * verticalMultiplier, knockback.z() * horizontalMultiplier));
    }

    protected void doDamage(LivingEntity entity, float minDamage, float maxDamage) {
        Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
        float radius = this.getChargeScale() * 4.0F;
        float scaledDistance = (float) (1 - (entity.position().distanceTo(location) / radius));
        float damage = Mth.lerp(Mth.sqrt(scaledDistance), minDamage, maxDamage);
        DamageSource damageSource = this.damageSources().source(OPDamageTypes.ELECTRIC);
        entity.hurt(damageSource, damage);
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity entity) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide && this.isAlive()) {
            OpposingForce.PROXY.playWorldSound(this, (byte) 1);
        }

        this.spawnElectricParticles(this, 1 + random.nextInt(3), 0, 16);

        if (this.level().getBlockState(this.blockPosition().below(0)).is(Blocks.WATER)) {
            this.createExplosion(this.getChargeScale() * 8.0F, 12);
            if (!this.level().isClientSide) this.discard();
        }

        if (this.tickCount > (this.isQuasar() ? 50 : 200) || this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            this.createExplosion(this.getChargeScale() * 4.0F, 6);
            if (!this.level().isClientSide) this.discard();
        }

        if (this.isQuasar()) this.doQuasarEffects(1F);
    }

    @Override
    public void remove(Entity.@NotNull RemovalReason reason) {
        OpposingForce.PROXY.clearSoundCacheFor(this);
        super.remove(reason);
    }

    public void doQuasarEffects(float scaleMultiplier) {
        AABB pull = this.getBoundingBox().inflate(8 + this.getChargeScale(), 8 + this.getChargeScale(), 8 + this.getChargeScale());
        for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, pull)) {
            if (entity != getOwner() && !(entity instanceof Volt)) {
                Vec3 center = pull.getCenter();
                float distance = (float) center.distanceTo(entity.position());
                if (distance > (this.getChargeScale() * 3)) {
                    continue;
                }
                float distanceFrom = 1 - distance / (this.getChargeScale() * 3);
                float scale = distanceFrom * distanceFrom * distanceFrom * distanceFrom * scaleMultiplier;
                Vec3 diff = center.subtract(entity.position()).scale(scale);
                entity.push(diff.x, diff.y, diff.z);
                entity.resetFallDistance();
            }
        }
    }

    public void spawnElectricParticles(ElectricCharge charge, int range, float yHeight, float particleMax) {
        Vec3 movement = charge.getDeltaMovement();

        double x = charge.getX() + movement.x;
        double y = charge.getY() + movement.y + yHeight;
        double z = charge.getZ() + movement.z;

        int lightningLength = (int) (range + charge.getChargeScale() / 1.25F);

        for (int i = 0; i < particleMax; i++) {
            if (!this.level().isClientSide) {
                if (this.isQuasar()) {
                    ParticleUtils.spawnLightningParticles(x, y, z, lightningLength, 0.1F + random.nextFloat(), 0.1F + random.nextFloat(), 0.1F + random.nextFloat());
                }
                else {
                    ParticleUtils.spawnLightningParticles(x, y, z, lightningLength, 0.3F + (random.nextFloat() / 8), 0.5F + (random.nextFloat() / 8), 0.8F + (random.nextFloat() / 8));
                }
            }
        }
    }
}