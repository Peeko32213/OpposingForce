package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.network.ElectricChargeSyncS2CPacket;
import com.unusualmodding.opposing_force.registry.*;
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

public class ElectricCharge extends AbstractElectricCharge {

    private static final EntityDataAccessor<Float> CHARGE_SCALE = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> BOUNCY = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> MAX_BOUNCES = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.INT);

    private int bounces = 0;
    public double baseDamage = 4;

    private final boolean darkBlue = random.nextBoolean();
    private final boolean alphaVar = random.nextBoolean();

    RandomSource rand = level.getRandom();

    public ElectricCharge(EntityType<? extends AbstractElectricCharge> entityType, Level level) {
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

    @Override
    public void tick() {
        super.tick();
        Vec3 pos = this.position();

        this.spawnElectricParticles(this, 1, 5);
        this.hurtEntitiesAround(pos, (this.getChargeScale()) + 1, this.getChargeScale() + 2, false);

        if (this.level().getBlockState(this.blockPosition().below(0)).is(Blocks.WATER)) {
            this.spawnElectricParticles(this, 10, 6);
            if (!this.level().isClientSide) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                this.hurtEntitiesAround(pos, this.getChargeScale() + 5, this.getChargeScale() + 4, true);
                this.discard();
            }
        }

        if (tickCount > 300 || this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            this.spawnElectricParticles(this, 5, 4);
            if (!this.level().isClientSide) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                this.discard();
            }
        }
    }

    public void spawnElectricParticles(ElectricCharge charge, int range, float particleMax) {
        Vec3 movement = charge.getDeltaMovement();

        double x = charge.getX() + movement.x;
        double y = charge.getY() - charge.getBbHeight() + movement.y;
        double z = charge.getZ() + movement.z;

        for (int i = 0; i < particleMax; i++) {
            ElectricChargeSyncS2CPacket packet = ElectricChargeSyncS2CPacket.builder()
                    .pos(x, y, z)
                    .range((int) (range + charge.getChargeScale() / 1.25F))
                    .size(0.12f)
                    .color(darkBlue ? 0.051f : 0.227f, darkBlue ? 0.173f : 0.592f, darkBlue ? 0.384f : 0.718f, alphaVar ? 0.66f : 0.53f)
                    .build();
            OPNetwork.sendToClients(packet);
        }
    }

    public boolean hurtEntitiesAround(Vec3 center, float radius, float damageAmount, boolean inWater) {
        AABB aabb = new AABB(center.subtract(radius, radius, radius), center.add(radius, radius, radius));
        Entity shooter = this.getOwner();
        boolean flag = false;
        for (LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, aabb, EntitySelector.NO_CREATIVE_OR_SPECTATOR)) {
            DamageSource damageSource = this.damageSources().source(OPDamageTypes.ELECTRIFIED);
            if (this.hasLineOfSight(living) && !living.is(this) && !living.isAlliedTo(this) && living.getType() != this.getType() && living.distanceToSqr(center.x, center.y, center.z) <= radius * radius) {
                if (!living.is(shooter) && !inWater) {
                    if (living.hurt(damageSource, damageAmount)) {
                        living.addEffect(new MobEffectInstance(OPEffects.ELECTRIFIED.get(), 160), shooter);
                        this.playSound(OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                        flag = true;
                    }
                }
                if (living.isInWaterRainOrBubble() && inWater) {
                    if (living.hurt(damageSource, damageAmount)) {
                        living.addEffect(new MobEffectInstance(OPEffects.ELECTRIFIED.get(), 200), shooter);
                        this.playSound(OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        if (!this.level().isClientSide) {
            this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), SoundSource.NEUTRAL, 0.6F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
            if (!this.isBouncy()) {
                this.spawnElectricParticles(this, 4, 5);
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
                this.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                this.spawnElectricParticles(this, 4, 5);
                this.discard();
            }
        }
    }

    private void bounce(Vec3 newVel) {
        bounces++;
        float conservedEnergy = 0.9F;
        newVel = newVel.scale(conservedEnergy);
        this.setDeltaMovement(newVel);

        if (!level().isClientSide) {
            this.hasImpulse = true;
            if (bounces >= 0) {
                this.playSound(OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
            }
            if (bounces > getMaxBounces()) {
                this.playSound(OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE.get(), 0.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                this.spawnElectricParticles(this, 4, 5);
                this.discard();
            }
        }
    }

    public void setSoundEvent(SoundEvent soundEvent) {
    }

    @Override
    protected float getEyeHeight(Pose pPose, EntityDimensions pDimensions) {
        return 0;
    }
}