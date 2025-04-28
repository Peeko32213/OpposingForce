package com.unusualmodding.opposingforce.common.entity.custom.projectile;

import com.unusualmodding.opposingforce.common.message.ElectricBallSyncS2CPacket;
import com.unusualmodding.opposingforce.core.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.*;
import org.joml.Vector3f;

public class ElectricBall extends AbstractElectricBall {

    private static final EntityDataAccessor<Float> CHARGE_SCALE = SynchedEntityData.defineId(ElectricBall.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> BOUNCY = SynchedEntityData.defineId(ElectricBall.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> MAX_BOUNCES = SynchedEntityData.defineId(ElectricBall.class, EntityDataSerializers.INT);

    private int bounces = 0;
    public double baseDamage = 4;

    private final boolean darkBlue = random.nextBoolean();
    private final boolean alphaVar = random.nextBoolean();

    RandomSource rand = level.getRandom();

    public ElectricBall(EntityType<? extends AbstractElectricBall> entityType, Level level) {
        super(entityType, level);
    }

    public ElectricBall(LivingEntity entity, Level level, double x, double y, double z) {
        super(OPEntities.ELECTRICITY_BALL.get(), level, entity, x, y, z);
        this.setOwner(entity);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(CHARGE_SCALE, 1F);
        this.getEntityData().define(BOUNCY, false);
        this.getEntityData().define(MAX_BOUNCES, 0);
    }

    public float getChargeScale() {
        return this.entityData.get(CHARGE_SCALE);
    }
    public void setChargeScale(float scale) {
        this.entityData.set(CHARGE_SCALE, scale);
    }

    public int getMaxBounces() {
        return this.entityData.get(MAX_BOUNCES);
    }
    public void setMaxBounces(int bounces) {
        this.entityData.set(MAX_BOUNCES, bounces);
    }

    public boolean isBouncy() {
        return this.entityData.get(BOUNCY);
    }
    public void setBouncy(boolean bounce) {
        this.entityData.set(BOUNCY, bounce);
    }

    public void setBaseDamage(double baseDamage) {
        this.baseDamage = baseDamage;
    }
    public double getBaseDamage() {
        return this.baseDamage;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> dataAccessor) {
        if (CHARGE_SCALE.equals(dataAccessor)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(dataAccessor);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 pos = this.position();

        this.spawnElectricParticles(this, 1, 4);
        this.hurtEntitiesAround(pos, this.getChargeScale() + 2.0F, this.getChargeScale() + 4.0F, false);

        if (this.level().getBlockState(this.blockPosition().below(0)).is(Blocks.WATER)) {
            this.spawnElectricParticles(this, 10, 6);
            if (!this.level().isClientSide) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                this.hurtEntitiesAround(pos, this.getChargeScale() + 8.0F, this.getChargeScale() + 7.0F, true);
                this.discard();
            }
        }

        if (tickCount > 300 || this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            this.spawnElectricParticles(this, 4, 3);
            if (!this.level().isClientSide) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                this.discard();
            }
        }
    }

    public void spawnElectricParticles(ElectricBall charge, int range, float particleMax) {
        Vec3 movement = charge.getDeltaMovement();

        double d0 = charge.getX() + movement.x;
        double d1 = charge.getY() + (charge.getBbHeight() - charge.getBbHeight() / 2) + movement.y;
        double d2 = charge.getZ() + movement.z;

        for (int i = 0; i < particleMax; i++) {
            ElectricBallSyncS2CPacket packet = ElectricBallSyncS2CPacket.builder()
                    .pos(d0, d1, d2)
                    .range((int) (range + charge.getChargeScale()))
                    .size(0.12f)
                    .color(darkBlue ? 0.051f : 0.227f, darkBlue ? 0.173f : 0.592f, darkBlue ? 0.384f : 0.718f, alphaVar ? 0.66f : 0.53f)
                    .build();
            OPMessages.sendToClients(packet);
        }
    }

    public boolean hurtEntitiesAround(Vec3 center, float radius, float damageAmount, boolean inWater){
        AABB aabb = new AABB(center.subtract(radius, radius, radius), center.add(radius, radius, radius));
        Entity shooter = this.getOwner();
        boolean flag = false;
        for (LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, aabb, EntitySelector.NO_CREATIVE_OR_SPECTATOR)) {
            DamageSource damageSource = this.damageSources().source(OPDamageTypes.ELECTRIFIED);
            if (!living.is(this) && !living.isAlliedTo(this) && living.getType() != this.getType() && living.distanceToSqr(center.x, center.y, center.z) <= radius * radius && !living.is(shooter) && !inWater) {
                if (living.hurt(damageSource, damageAmount)) {
                    living.addEffect(new MobEffectInstance(OPEffects.ELECTRIFIED.get(), 160), shooter);
                    flag = true;
                }
            }
            if (!living.is(this) && living.getType() != this.getType() && living.distanceToSqr(center.x, center.y, center.z) <= radius * radius && living.isInWaterRainOrBubble() && inWater) {
                if (living.hurt(damageSource, damageAmount)) {
                    living.addEffect(new MobEffectInstance(OPEffects.ELECTRIFIED.get(), 160), shooter);
                    flag = true;
                }
            }
        }
        return flag;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(this.getChargeScale());
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        if (!this.level().isClientSide) {
            this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.6F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
            if (!this.isBouncy()) {
                this.spawnElectricParticles(this, 4, 3);
                this.discard();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();

        if (!this.level().isClientSide) {
            if (this.isBouncy()) {
                Direction hitDirection = result.getDirection();
                Vector3f surfaceNormal = hitDirection.step();
                Vec3 velocity = this.getDeltaMovement();
                Vec3 newVel = new Vec3(velocity.toVector3f().reflect(surfaceNormal));
                bounce(newVel);
            } else {
                this.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                this.spawnElectricParticles(this, 4, 3);
                this.discard();
            }
        }
    }

    private void bounce(Vec3 newVel) {
        bounces++;
        float conservedEnergy = 1F;
        newVel = newVel.scale(conservedEnergy);
        this.setDeltaMovement(newVel);

        if (!level().isClientSide) {
            this.hasImpulse = true;
            if (bounces >= 0) {
                this.playSound(OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), 0.5F, 1.25F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
            }
            if (bounces > getMaxBounces()) {
                this.playSound(OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                this.spawnElectricParticles(this, 4, 3);
                this.discard();
            }
        }
    }

    public void setSoundEvent(SoundEvent pSoundEvent) {}

    @Override
    protected float getEyeHeight(Pose pPose, EntityDimensions pDimensions) {
        return 0;
    }
}