package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.misc.ElectricExplosion;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.utils.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class ElectricCharge extends FrictionlessProjectile {

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
        this.getEntityData().define(RAINBOW, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Rainbow", this.isRainbow());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setRainbow(compoundTag.getBoolean("Rainbow"));
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
            this.createExplosion(3.0F);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.createExplosion(3.0F);
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

    protected void createExplosion(float radius) {
        if (!this.level().isClientSide) {
            this.spawnElectricParticles(this, (int) (radius + 1 + random.nextInt((int) radius + 1)), 0.25F, 16);
            ElectricExplosion explosion = new ElectricExplosion(this.level(), this, this.getX(), this.getY(0.0625D), this.getZ(), radius, Explosion.BlockInteraction.KEEP);
            explosion.explode();
            explosion.finalizeExplosion(true);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide && this.isAlive()) OpposingForce.PROXY.playWorldSound(this, (byte) 1);

        this.spawnElectricParticles(this, 1 + random.nextInt(3), 0, 16);

        if (!this.level().isClientSide && this.level().getFluidState(this.blockPosition()).is(FluidTags.WATER)) {
            this.createExplosion(4.0F);
            this.discard();
        }
        if (!this.level().isClientSide && this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            this.createExplosion(3.0F);
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