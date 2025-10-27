package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class LightningBomb extends ThrowableItemProjectile {

    protected int oFuse;
    protected int fuse;
    protected int maxFuse = 80;

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
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            oFuse = fuse;
            fuse++;
            if (fuse >= maxFuse) {
                fuse = maxFuse;
            }
            if (!onGround()) {
                this.level().addParticle(ParticleTypes.SMOKE, getX(), getY() + getBbHeight(), getZ(), 0, 0, 0);
            }
        } else {
            fuse++;
            if (fuse >= maxFuse) {
                if (!this.level().isClientSide()) {
                    boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this.getOwner());
                    this.level().explode(this, null, new ExplosionDamageCalculator(), this.getX(), this.getY(), this.getZ(), 2.0F, false, flag ? Level.ExplosionInteraction.TNT : Level.ExplosionInteraction.NONE);
                    this.remove(RemovalReason.DISCARDED);
                }
            }
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        if (!this.level().isClientSide()) {
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this.getOwner());
            this.level().explode(this, null, new ExplosionDamageCalculator(), this.getX(), this.getY(), this.getZ(), 2.0F, false, flag ? Level.ExplosionInteraction.TNT : Level.ExplosionInteraction.NONE);
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        Vec3 motion = this.getDeltaMovement();
        if (motion.lengthSqr() < 0.1) {
            this.setDeltaMovement(Vec3.ZERO);
            this.setOnGround(true);
            return;
        }

        Direction direction = result.getDirection();
        switch (direction.getAxis()) {
            case X -> this.setDeltaMovement(-motion.x() * 0.35, motion.y(), motion.z());
            case Y -> this.setDeltaMovement(motion.x() * 0.4, -motion.y() * 0.4, motion.z() * 0.4);
            case Z -> this.setDeltaMovement(motion.x(), motion.y(), -motion.z() * 0.35);
        }
    }

    public float getSwelling(float partialTicks) {
        return Mth.lerp(partialTicks, oFuse, fuse) / (float) (maxFuse - 2);
    }
}