package com.unusualmodding.opposingforce.common.entity.custom.projectile;

import com.unusualmodding.opposingforce.common.message.ElectricBallSyncS2CPacket;
import com.unusualmodding.opposingforce.core.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class ElectricBall extends ThrowableItemProjectile {

    private static final EntityDataAccessor<Float> CHARGE_SCALE = SynchedEntityData.defineId(ElectricBall.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> BOUNCY = SynchedEntityData.defineId(ElectricBall.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> MAX_BOUNCES = SynchedEntityData.defineId(ElectricBall.class, EntityDataSerializers.INT);

    private int bounces = 0;
    public double baseDamage = 4;

    public ElectricBall(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public ElectricBall(LivingEntity livingEntity, Level level) {
        super(OPEntities.ELECTRICITY_BALL.get(), livingEntity, level);
    }

    public ElectricBall(LivingEntity livingEntity, Level level, double x, double y, double z) {
        super(OPEntities.ELECTRICITY_BALL.get(), livingEntity, level);
        this.setPos(x, y, z);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return OPItems.ELECTRIC_CHARGE.get();
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
    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(this.getChargeScale());
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        if (!this.level().isClientSide) {
             if (entity instanceof LivingEntity target) {
                 if (target.isBlocking()) {
                     this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.SHIELD_BLOCK, SoundSource.NEUTRAL, 1F, 1F);
                 } else {
                     entity.hurt(this.damageSources().source(OPDamageTypes.ELECTRIFIED, this.getOwner()), (float) this.getBaseDamage());
                     target.addEffect(new MobEffectInstance(OPEffects.ELECTRIFIED.get(), 160), this.getOwner());
                 }
            }
            this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.6F, 1F);
            if (!this.isBouncy()) this.discard();
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
                this.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                this.discard();
            }
        }
    }

    private void bounce(Vec3 newVel) {
        bounces++;
        Vec3 velocity = this.getDeltaMovement();
        float conservedEnergy = 1F;
        newVel = newVel.scale(conservedEnergy);
        this.setDeltaMovement(newVel);
        //double missingDistance = velocity.subtract(this.position().subtract(new Vec3(xo, yo, zo))).length();
        //Vec3 missingVel = newVel.normalize().scale(missingDistance);
        //this.move(MoverType.SELF, missingVel);
        if (!level().isClientSide) {
            this.hasImpulse = true;
            if (bounces >= 0) {
                this.playSound(OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), 0.5F, 1.25F);
            }
            if (bounces > getMaxBounces()) {
                this.playSound(OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), 0.5F, 1.0F);
                this.discard();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide && this.level().getBlockState(this.blockPosition().below(0)).is(Blocks.WATER)) {
            this.discard();
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
        }
        else if (!this.level().isClientSide && tickCount > 300) {
            this.discard();
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
        }

        Vec3 movement = this.getDeltaMovement();
        double d0 = this.getX() + movement.x;
        double d1 = this.getY() + movement.y;
        double d2 = this.getZ() + movement.z;

        float range = 1F;
        float particleMax = 5 + this.level().getRandom().nextInt(3);
        RandomSource rand = this.level().getRandom();

        for (int i = 0; i < particleMax; i++) {
            float angle = (float) ((i / particleMax) * Math.PI * 2);

            // Generate the initial random vector
            float x = (rand.nextFloat() - 0.5F) * 0.3F;
            float y = (rand.nextFloat() - 0.5F) * 0.3F;
            float z = range * 0.5F + range * 0.5F * rand.nextFloat();

            // Rotate around Y-axis
            double rotX = x * Math.cos(angle) - z * Math.sin(angle);
            double rotZ = x * Math.sin(angle) + z * Math.cos(angle);

            // Add movement vector
            double finalX = rotX + movement.x;
            double finalY = y + movement.y;
            double finalZ = rotZ + movement.z;

            // Send packet with just the coordinates


            ElectricBallSyncS2CPacket packet = ElectricBallSyncS2CPacket.builder()
                    .pos(d0, d1, d2)
                    .direction(finalX, finalY, finalZ)
                    .range(5)
                    .size(0.16f)
                    .color(1.0f, 0.0f, 1.0f, 0.9f)
                    .build();

            OPMessages.sendToClients(packet);
        }
    }

    public void setSoundEvent(SoundEvent pSoundEvent) {}

    @Override
    protected float getGravity() {
        return 0.0F;
    }
}
