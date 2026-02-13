package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPMobEffects;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import com.unusualmodding.opposing_force.utils.OPMath;
import com.unusualmodding.opposing_force.utils.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class ElectricCharge extends FrictionlessProjectile {

    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> RAINBOW = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.BOOLEAN);

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
        this.getEntityData().define(DAMAGE, 5.0F);
        this.getEntityData().define(RAINBOW, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat("Damage", this.getChargeDamage());
        compoundTag.putBoolean("Rainbow", this.isRainbow());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setChargeDamage(compoundTag.getFloat("Damage"));
        this.setRainbow(compoundTag.getBoolean("Rainbow"));
    }

    public float getChargeDamage() {
        return this.entityData.get(DAMAGE);
    }

    public void setChargeDamage(float damage) {
        this.entityData.set(DAMAGE, damage);
    }

    public boolean isRainbow() {
        return this.entityData.get(RAINBOW);
    }

    public void setRainbow(boolean rainbow) {
        this.entityData.set(RAINBOW, rainbow);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            this.createExplosion(this.position(), 4.0F, 6);
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            Vec3i vec3i = result.getDirection().getNormal();
            Vec3 vec3 = Vec3.atLowerCornerOf(vec3i).multiply(0.25, 0.25, 0.25);
            Vec3 vec31 = result.getLocation().add(vec3);
            this.createExplosion(vec31, 4.0F, 6);
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult result) {
        HitResult.Type hitresult$type = result.getType();
        if (hitresult$type == HitResult.Type.ENTITY) {
            EntityHitResult entityhitresult = (EntityHitResult)result;
            this.onHitEntity(entityhitresult);
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, result.getLocation(), GameEvent.Context.of(this, null));
        } else if (hitresult$type == HitResult.Type.BLOCK) {
            BlockHitResult blockhitresult = (BlockHitResult)result;
            this.onHitBlock(blockhitresult);
            BlockPos blockpos = blockhitresult.getBlockPos();
            this.level().gameEvent(GameEvent.PROJECTILE_LAND, blockpos, GameEvent.Context.of(this, this.level().getBlockState(blockpos)));
        }
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    protected void createExplosion(Vec3 location, float radius, int particleRange) {
        if (!this.level().isClientSide) {
            this.spawnElectricParticles(this, particleRange + random.nextInt(particleRange), 0.25F, 16);
            this.level().playSound(null, location.x(), location.y(), location.z(), OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 2.5F, 1.25F + (this.random.nextFloat() - this.random.nextFloat()) * 0.25F);
        }
        for (Entity entity : this.level().getEntities(this, new AABB(location.subtract(radius, radius, radius), location.add(radius, radius, radius)))) {
            if (entity.distanceToSqr(location) > radius * radius || !OPMath.hasLineOfSight(this, entity) || entity == this.getOwner()) {
                continue;
            }
            if (entity instanceof LivingEntity livingEntity) {
                this.doDamage(livingEntity, 4, this.getChargeDamage());
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

    protected void doDamage(LivingEntity entity, float radius, float maxDamage) {
        DamageSource damagesource = new DamageSource(level().registryAccess().lookup(Registries.DAMAGE_TYPE).orElseThrow().getOrThrow(OPDamageTypes.ELECTRIC), this);
        Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
        float scaledDistance = (float) (1 - (entity.position().distanceTo(location) / radius));
        float damage = Mth.lerp(Mth.sqrt(scaledDistance), 1, maxDamage);
        LivingEntity owner = this.getOwner() instanceof LivingEntity living ? living : null;
        if (owner != null) owner.setLastHurtMob(entity);
        if (entity.hurt(damagesource, damage)) {
            if (owner != null) {
                EnchantmentHelper.doPostDamageEffects(owner, entity);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide && this.isAlive()) OpposingForce.PROXY.playWorldSound(this, (byte) 1);

        this.spawnElectricParticles(this, 1 + random.nextInt(3), 0, 16);

        if (!this.level().isClientSide && this.level().getFluidState(this.blockPosition()).is(FluidTags.WATER)) {
            this.spawnElectricParticles(this, 8 + random.nextInt(8), 0.25F, 16);
            this.level().playSound(null, this.position().x(), this.position().y(), this.position().z(), OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 2.5F, 1.25F + (this.random.nextFloat() - this.random.nextFloat()) * 0.25F);
            DamageSource damagesource = new DamageSource(level().registryAccess().lookup(Registries.DAMAGE_TYPE).orElseThrow().getOrThrow(OPDamageTypes.ELECTRIC), this);

            for (Entity entity : this.level().getEntities(this, new AABB(this.position().subtract(6, 6, 6), this.position().add(6, 6, 6)))) {
                if (entity.distanceToSqr(this.position()) > 6 * 6 || !entity.isInWaterOrBubble()) {
                    continue;
                }
                if (entity instanceof LivingEntity livingEntity) {
                    LivingEntity owner = this.getOwner() instanceof LivingEntity living ? living : null;
                    if (owner != null) owner.setLastHurtMob(entity);
                    if (entity.hurt(damagesource, this.getChargeDamage() * 1.5F)) {
                        if (owner != null) {
                            EnchantmentHelper.doPostDamageEffects(owner, entity);
                        }
                    }
                    livingEntity.addEffect(new MobEffectInstance(OPMobEffects.ELECTRIFIED.get(), 300, 0));
                }
            }
            this.discard();
        }
        if (!this.level().isClientSide && this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            this.createExplosion(this.position(), 4.0F, 6);
            this.discard();
        }
    }

    @Override
    public void remove(Entity.@NotNull RemovalReason reason) {
        OpposingForce.PROXY.clearSoundCacheFor(this);
        super.remove(reason);
    }

    public void spawnElectricParticles(ElectricCharge charge, int range, float yHeight, float particleMax) {
        Vec3 movement = charge.getDeltaMovement();
        double x = charge.getX() + movement.x;
        double y = charge.getY() + movement.y + yHeight;
        double z = charge.getZ() + movement.z;
        int lightningLength = (int) (range + 0.8F);

        for (int i = 0; i < particleMax; i++) {
            if (!this.level().isClientSide) {
                if (this.isRainbow()) {
                    ParticleUtils.spawnLightningParticles(x, y, z, lightningLength, 0.1F + random.nextFloat(), 0.1F + random.nextFloat(), 0.1F + random.nextFloat());
                } else {
                    ParticleUtils.spawnLightningParticles(x, y, z, lightningLength, 0.3F + (random.nextFloat() / 8), 0.5F + (random.nextFloat() / 8), 0.8F + (random.nextFloat() / 8));
                }
            }
        }
    }
}