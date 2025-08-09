package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LaserBolt extends AbstractFrictionlessProjectile {

    RandomSource rand = level.getRandom();

    public LaserBolt(EntityType<? extends AbstractFrictionlessProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public LaserBolt(LivingEntity entity, Level level, double x, double y, double z) {
        super(OPEntities.LASER_BOLT.get(), level, entity, x, y, z);
        this.setOwner(entity);
        this.moveTo(x, y, z, this.getYRot(), this.getXRot());
        this.reapplyPosition();
    }

    public LaserBolt(Level level, double x, double y, double z, Vec3 movement) {
        super(OPEntities.LASER_BOLT.get(), x, y, z, movement, level);
    }

    public LaserBolt(PlayMessages.SpawnEntity spawnEntity, Level pLevel) {
        this(OPEntities.LASER_BOLT.get(), pLevel);
    }

    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public void tick() {
        super.tick();

        if (tickCount > 200 || this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            if (!this.level().isClientSide) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSoundEvents.LASER_BOLT_IMPACT.get(), SoundSource.NEUTRAL, 1.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                this.discard();
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        DamageSource damageSource = this.damageSources().source(OPDamageTypes.LASER);

        if (!this.level().isClientSide) {
            this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), OPSoundEvents.LASER_BOLT_IMPACT.get(), SoundSource.NEUTRAL, 1.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
            entity.hurt(damageSource, 5);
//            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();

        if (!this.level().isClientSide) {
            this.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), OPSoundEvents.LASER_BOLT_IMPACT.get(), SoundSource.NEUTRAL, 1.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
            this.discard();
        }
    }

    @Override
    protected float getEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 0;
    }
}
