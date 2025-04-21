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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Comparator;
import java.util.List;

public class ElectricBall extends ThrowableItemProjectile {

    private static final EntityDataAccessor<Float> CHARGE_SCALE = SynchedEntityData.defineId(ElectricBall.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> BOUNCY = SynchedEntityData.defineId(ElectricBall.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> MAX_BOUNCES = SynchedEntityData.defineId(ElectricBall.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> THUNDER = SynchedEntityData.defineId(ElectricBall.class, EntityDataSerializers.BOOLEAN);

    private int bounces = 0;
    public double baseDamage = 4;

    private final boolean darkBlue = random.nextBoolean();
    private final boolean alphaVar = random.nextBoolean();

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
        this.getEntityData().define(THUNDER, false);
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

    public boolean isThunder() {
        return this.entityData.get(THUNDER);
    }
    public void setThunder(boolean thunder) {
        this.entityData.set(THUNDER, thunder);
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

        ElectricBallSyncS2CPacket packetImpactEntity = ElectricBallSyncS2CPacket.builder()
                .pos(this.getX(), this.getY(), this.getZ())
                .range((int) (8 + this.getChargeScale()))
                .size(0.16f)
                .color(darkBlue ? 0.051f : 0.227f, darkBlue ? 0.173f : 0.592f, darkBlue ? 0.384f : 0.718f, alphaVar ? 0.66f : 0.53f)
                .build();

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
            if (!this.isBouncy()) {
                OPMessages.sendToClients(packetImpactEntity);
                this.discard();
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();

        ElectricBallSyncS2CPacket packetImpactBlock = ElectricBallSyncS2CPacket.builder()
                .pos(this.getX(), this.getY(), this.getZ())
                .range((int) (8 + this.getChargeScale()))
                .size(0.16f)
                .color(darkBlue ? 0.051f : 0.227f, darkBlue ? 0.173f : 0.592f, darkBlue ? 0.384f : 0.718f, alphaVar ? 0.66f : 0.53f)
                .senderId(this.getId())
                .build();

        if (!this.level().isClientSide) {
            if (this.isBouncy()) {
                Direction hitDirection = result.getDirection();
                Vector3f surfaceNormal = hitDirection.step();
                Vec3 velocity = this.getDeltaMovement();
                Vec3 newVel = new Vec3(velocity.toVector3f().reflect(surfaceNormal));
                bounce(newVel);
            } else {
                this.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                OPMessages.sendToClients(packetImpactBlock);
                this.discard();
            }
        }
    }

    private void bounce(Vec3 newVel) {
        bounces++;
        float conservedEnergy = 1F;
        newVel = newVel.scale(conservedEnergy);
        this.setDeltaMovement(newVel);

        Vec3 origin = this.position();
        List<Integer> entityIds = this.level().getEntities(this, this.getBoundingBox().inflate(10), e -> e.isAlive() && e != this)
                .stream()
                .sorted(Comparator.comparingDouble(e -> e.position().distanceToSqr(origin)))
                .map(Entity::getId)
                .toList();


        ElectricBallSyncS2CPacket packetBounceDissipate = ElectricBallSyncS2CPacket.builder()
                .pos(this.getX(), this.getY(), this.getZ())
                .range((int) (8 + this.getChargeScale()))
                .size(0.16f)
                .color(darkBlue ? 0.051f : 0.227f, darkBlue ? 0.173f : 0.592f, darkBlue ? 0.384f : 0.718f, alphaVar ? 0.66f : 0.53f)
                .chain(entityIds)
                .build();

        if (!level().isClientSide) {
            this.hasImpulse = true;
            if (bounces >= 0) {
                this.playSound(OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), 0.5F, 1.25F);
            }
            if (bounces > getMaxBounces()) {
                this.playSound(OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), 0.5F, 1.0F);
                OPMessages.sendToClients(packetBounceDissipate);
                this.discard();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 movement = this.getDeltaMovement();
        double d0 = this.getX() + movement.x;
        double d1 = this.getY() + movement.y;
        double d2 = this.getZ() + movement.z;

        float particleMax = 5 + this.level().getRandom().nextInt(3);

        for (int i = 0; i < particleMax; i++) {
            if (this.isThunder()) {
                ElectricBallSyncS2CPacket packet = ElectricBallSyncS2CPacket.builder()
                        .pos(d0, d1, d2)
                        .range((int) (0 + this.getChargeScale()))
                        .size(0.16f)
                        .color(darkBlue ? 0.051f : 0.227f, darkBlue ? 0.173f : 0.592f, darkBlue ? 0.384f : 0.718f, alphaVar ? 0.66f : 0.53f)
                        .build();
                OPMessages.sendToClients(packet);
            }

            // Send packet with just the coordinates
            ElectricBallSyncS2CPacket packet = ElectricBallSyncS2CPacket.builder()
                    .pos(d0, d1, d2)
                    .range((int) (1 + this.getChargeScale()))
                    .size(0.16f)
                    .color(darkBlue ? 0.051f : 0.227f, darkBlue ? 0.173f : 0.592f, darkBlue ? 0.384f : 0.718f, alphaVar ? 0.66f : 0.53f)
                    .build();
            OPMessages.sendToClients(packet);

            if (!this.level().isClientSide && this.level().getBlockState(this.blockPosition().below(0)).is(Blocks.WATER)) {
                ElectricBallSyncS2CPacket packetImpactWater = ElectricBallSyncS2CPacket.builder()
                        .pos(d0, d1, d2)
                        .range((int) (13 + this.getChargeScale()))
                        .size(0.16f)
                        .color(darkBlue ? 0.051f : 0.227f, darkBlue ? 0.173f : 0.592f, darkBlue ? 0.384f : 0.718f, alphaVar ? 0.66f : 0.53f)
                        .build();
                OPMessages.sendToClients(packetImpactWater);

                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                this.discard();
            }

            else if (!this.level().isClientSide && tickCount > 300) {
                ElectricBallSyncS2CPacket packetDissipate = ElectricBallSyncS2CPacket.builder()
                        .pos(d0, d1, d2)
                        .range((int) (8 + this.getChargeScale()))
                        .size(0.16f)
                        .color(darkBlue ? 0.051f : 0.227f, darkBlue ? 0.173f : 0.592f, darkBlue ? 0.384f : 0.718f, alphaVar ? 0.66f : 0.53f)
                        .build();
                OPMessages.sendToClients(packetDissipate);

                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                this.discard();
            }
        }
    }

    public void setSoundEvent(SoundEvent pSoundEvent) {}

    @Override
    protected float getGravity() {
        return 0.0F;
    }
}