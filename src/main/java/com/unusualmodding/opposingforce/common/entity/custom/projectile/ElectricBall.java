package com.unusualmodding.opposingforce.common.entity.custom.projectile;

import com.unusualmodding.opposingforce.common.message.ParticleSyncS2CPacket;
import com.unusualmodding.opposingforce.core.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ElectricBall extends ThrowableItemProjectile {

    public double xPower;
    public double yPower;
    public double zPower;

    public ElectricBall(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public ElectricBall(LivingEntity livingEntity, Level level) {
        super(OPEntities.ELECTRICITY_BALL.get(), livingEntity, level);
        this.noPhysics = true;
    }

    public ElectricBall(LivingEntity livingEntity, Level level, double x, double y, double z) {
        super(OPEntities.ELECTRICITY_BALL.get(), livingEntity, level);
        this.noPhysics = true;
        this.setPos(x, y, z);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return OPItems.ELECTRIC_CHARGE.get();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (entity instanceof LivingEntity target) {
            if (target.isBlocking()) {
                this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.SHIELD_BLOCK, SoundSource.NEUTRAL, 1F, 1F);
            } else {
                entity.hurt(this.damageSources().source(OPDamageTypes.ELECTRIFIED, this.getOwner()), 4);
                target.addEffect(new MobEffectInstance(OPEffects.ELECTRIFIED.get(), 100), this.getOwner());
            }
        }
        if (!this.level().isClientSide) {
            this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.6F, 1F);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        BlockPos pos = blockHitResult.getBlockPos();
        BlockState state = level().getBlockState(pos);
        if (!this.level().isClientSide) {
            if (state.is(Blocks.WATER)) {
                this.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
                this.discard();
            }
            this.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
            this.discard();
        }
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isInvulnerableTo(pSource)) {
            return false;
        } else {
            this.markHurt();
            Entity entity = pSource.getEntity();
            if (entity != null) {
                if (!this.level().isClientSide) {
                    Vec3 vec3 = entity.getLookAngle();
                    this.setDeltaMovement(vec3);
                    this.xPower = vec3.x * 0.1;
                    this.yPower = vec3.y * 0.1;
                    this.zPower = vec3.z * 0.1;
                    this.setOwner(entity);
                }
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide && this.level().getBlockState(this.blockPosition().below(0)).is(Blocks.WATER)) {
            this.discard();
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSounds.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 0.5F, 1F);
        } else if (!this.level().isClientSide && tickCount > 300) {
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
            double x = (rand.nextFloat() - 0.5F) * 0.3F;
            double y = (rand.nextFloat() - 0.5F) * 0.3F;
            double z = range * 0.5F + range * 0.5F * rand.nextFloat();

            // Rotate around Y-axis
            double rotX = x * Math.cos(angle) - z * Math.sin(angle);
            double rotZ = x * Math.sin(angle) + z * Math.cos(angle);

            // Add movement vector
            double finalX = rotX + movement.x;
            double finalY = y + movement.y;
            double finalZ = rotZ + movement.z;

            // Send packet with just the coordinates
            OPMessages.sendToClients(new ParticleSyncS2CPacket((float) d0, (float)d1, (float)d2, (float)finalX, (float)finalY, (float)finalZ));
        }

    }

    public void setSoundEvent(SoundEvent pSoundEvent) {
    }

    @Override
    protected float getGravity() {
        return 0.0f;
    }
}
