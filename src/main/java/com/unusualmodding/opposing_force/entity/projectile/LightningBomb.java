package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.network.ElectricChargeSyncS2CPacket;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPNetwork;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

public class LightningBomb extends ThrowableItemProjectile {

    protected int oFuse;
    protected int fuse = 0;
    protected int maxFuse = 100;
    protected float oSpin;
    protected float spin = 0;

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
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Fuse", fuse);
        compoundTag.putInt("MaxFuse", maxFuse);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        fuse = compoundTag.getInt("Fuse");
        maxFuse = compoundTag.getInt("MaxFuse");
    }

    @Override
    protected void updateRotation() {
        Vec3 deltaMovement = getDeltaMovement();
        double distanceSq = deltaMovement.horizontalDistanceSqr();
        double distance = Math.sqrt(distanceSq);
        setXRot(lerpRotation(xRotO, (float) (Mth.atan2(deltaMovement.y, distance) * 180.0F / (float) Math.PI)));
        if (distanceSq > 0.01) {
            setYRot(lerpRotation(yRotO, (float) (Mth.atan2(deltaMovement.x, deltaMovement.z) * 180.0F / (float) Math.PI)));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            oFuse = fuse;
            oSpin = spin;
            fuse++;
            if (fuse >= maxFuse) {
                fuse = maxFuse;
            }
            if (!onGround()) {
                this.level().addParticle(ParticleTypes.SMOKE, getX(), getY() + getBbHeight(), getZ(), 0, 0, 0);
            }
            double distance = getDeltaMovement().lengthSqr();
            if (distance > 0.01) {
                spin += (float) (Math.sqrt(distance) * 15);
            }
        } else {
            fuse++;
            if (fuse >= maxFuse) {
                this.explode();
            }
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        this.explode();
    }

    protected void explode() {
        double px = this.getX() + this.random.nextDouble() - 0.5;
        double py = this.getY() + this.random.nextDouble() - 0.5;
        double pz = this.getZ() + this.random.nextDouble() - 0.5;
        if (!this.level.isClientSide) {
            boolean flag = ForgeEventFactory.getMobGriefingEvent(this.level(), this.getOwner());
            this.level().explode(this, null, new ExplosionDamageCalculator(), this.getX(), this.getY(0.0625), this.getZ(), 1.5F, false, flag ? Level.ExplosionInteraction.TNT : Level.ExplosionInteraction.NONE);
            for (int i = 0; i < 16; i++) {
                ElectricChargeSyncS2CPacket packet = ElectricChargeSyncS2CPacket.builder()
                        .pos(px, py, pz)
                        .range(8)
                        .size(0.08F)
                        .color(0.3F + (this.random.nextFloat() / 8), 0.5F + (this.random.nextFloat() / 8), 0.8F + (this.random.nextFloat() / 8), 1F)
                        .build();
                OPNetwork.sendToClients(packet);
            }
            this.remove(RemovalReason.DISCARDED);
        } else {
            for (int i = 0; i < 16; i++) {
                this.level.addParticle(ParticleTypes.LARGE_SMOKE, px, py, pz, 0, 0, 0);
            }
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        Vec3 motion = this.getDeltaMovement();
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
                this.setDeltaMovement(-motion.x() * 0.35, motion.y(), motion.z());
                this.playSound(SoundEvents.CHAIN_HIT, 1F, 1F);
            }
            case Y -> {
                this.setDeltaMovement(motion.x() * 0.4, -motion.y() * 0.4, motion.z() * 0.4);
                this.playSound(SoundEvents.CHAIN_HIT, 1F, 1F);
            }
            case Z -> {
                this.setDeltaMovement(motion.x(), motion.y(), -motion.z() * 0.35);
                this.playSound(SoundEvents.CHAIN_HIT, 1F, 1F);
            }
        }
    }

    public float getSwelling(float partialTicks) {
        return Mth.lerp(partialTicks, oFuse, fuse) / (float) (maxFuse - 2);
    }

    public float getSpin(float partialTicks) {
        return Mth.lerp(partialTicks, oSpin, spin);
    }
}