package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.network.PlayMessages;

import java.util.List;

public class TremblerShell extends Entity {

    public TremblerShell(EntityType<? extends TremblerShell> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.blocksBuilding = true;
    }

    public TremblerShell(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(OPEntities.TREMBLER_SHELL.get(), level);
    }

    @Override
    protected float getEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height;
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public void push(double pX, double pY, double pZ) {
    }

    @Override
    protected Vec3 getRelativePortalPosition(Direction.Axis pAxis, BlockUtil.FoundRectangle pPortal) {
        return LivingEntity.resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition(pAxis, pPortal));
    }

    public Item getDropItem() {
        return OPItems.TREMBLER_SHELL.get();
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {

    }

    private void hitShield(List<Entity> pEntities) {
        for (Entity entity : pEntities) {
            if (entity instanceof Player player) {
                if (player.isBlocking()) {
                    double x = player.getX() - this.getX();
                    double z = player.getZ() - this.getZ();
                    double shellX = this.getX() - player.getX();
                    double shellZ = this.getZ() - player.getZ();
                    double d2 = Math.max(x * x + z * z, 0.001D);

                    this.setDeltaMovement(shellX / d2 * 0.4D, 0.005D, shellZ / d2 * 0.4D);

                    player.getCooldowns().addCooldown(player.getUseItem().getItem(), 100);
                    player.stopUsingItem();
                    player.level().broadcastEntityEvent(player, (byte) 30);
                }
            }
        }
    }

    private void knockBack(List<Entity> pEntities) {
        for (Entity entity : pEntities) {
            if (entity instanceof LivingEntity) {
                double x = entity.getX() - this.getX();
                double z = entity.getZ() - this.getZ();
                double shellX = this.getX() - entity.getX();
                double shellZ = this.getZ() - entity.getZ();
                double d2 = Math.max(x * x + z * z, 0.001D);

                this.setDeltaMovement(shellX / d2 * 0.4D, 0.005D, shellZ / d2 * 0.4D);
                entity.push(x / d2 * 0.05D, 0.005D, z / d2 * 0.05D);
            }
        }
    }

    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        Vec3 vec3 = (new Vec3(x, y, z)).normalize().add(this.random.triangle(0.0D, 0.0172275D * (double) inaccuracy), this.random.triangle(0.0D, 0.0172275D * (double) inaccuracy), this.random.triangle(0.0D, 0.0172275D * (double) inaccuracy)).scale((double) velocity);
        this.setDeltaMovement(vec3);
        double d0 = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    protected static BlockHitResult getTremblerShellPOVHitResult(Level pLevel, Entity entity, ClipContext.Fluid pFluidMode) {
        float xRot = entity.getXRot();
        float yRot = entity.getYRot();
        Vec3 vec3 = entity.getEyePosition();
        float f2 = Mth.cos(-yRot * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-yRot * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-xRot * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-xRot * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double range = 1.15;
        Vec3 vec31 = vec3.add((double)f6 * range, (double)f5 * range, (double)f7 * range);
        return pLevel.clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, pFluidMode, entity));
    }

    private void blockKnockBack() {
        Vec3 vec3 = this.getDeltaMovement();
        BlockHitResult hitResult = getTremblerShellPOVHitResult(this.level(), this, ClipContext.Fluid.NONE);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            if (hitResult.getDirection().getAxis() == Direction.Axis.X) {
                this.shoot(this.getDeltaMovement().reverse().x, vec3.y, vec3.z, 0.80F, 0F);
                this.setYRot(this.getYRot() + 180);
                this.yRotO = this.getYRot();
            }
            if (hitResult.getDirection().getAxis() == Direction.Axis.Z) {
                this.shoot(vec3.x, vec3.y, this.getDeltaMovement().reverse().z, 0.80F, 0F);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        Level level = this.level();

        if (!this.isNoGravity()) {
            double yVelocity = -0.04D;
            FluidType fluidType = this.getEyeInFluidType();

            if (fluidType != ForgeMod.EMPTY_TYPE.get()) {
                yVelocity *= this.getFluidMotionScale(fluidType);
            }

            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, yVelocity, 0.0D));
        }

        BlockPos bottomPosition = this.getBlockPosBelowThatAffectsMyMovement();
        float friction = this.onGround() ? level.getBlockState(bottomPosition).getFriction(level, bottomPosition, this) * 1.55F : 1.55F;
        float defaultFriction = this.level().getBlockState(bottomPosition).getFriction(level, bottomPosition, this);

        double y = this.getDeltaMovement().get(Direction.Axis.Y);
        if (y == -0.04 && !this.isInFluidType() && defaultFriction == 0.6F) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(friction, 0.98D, friction));
        }
        if (this.isInFluidType()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.85, 1, 0.85));
        }


        if (this.getDeltaMovement() != Vec3.ZERO) {
            this.move(MoverType.SELF, this.getDeltaMovement());
        }

        if (this.isInLava()) {
            this.lavaHurt();
            this.fallDistance *= 0.5F;
        }
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(this.getDropItem());
    }
}
