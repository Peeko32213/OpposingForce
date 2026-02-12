package com.unusualmodding.opposing_force.entity.ai.goal.skyvern;

import com.unusualmodding.opposing_force.entity.Skyvern;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SkyvernFlightGoal extends Goal {

    private final Skyvern skyvern;
    private double x;
    private double y;
    private double z;

    public SkyvernFlightGoal(Skyvern skyvern) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.skyvern = skyvern;
    }

    @Override
    public boolean canUse() {
        if (skyvern.isVehicle() || (skyvern.getTarget() != null && skyvern.getTarget().isAlive()) || skyvern.isPassenger()) {
            return false;
        } else {
            Vec3 target = this.getForwardLocation();
            if (target == null) {
                return false;
            } else {
                this.x = target.x;
                this.y = target.y;
                this.z = target.z;
                return true;
            }
        }
    }

    @Override
    public void start() {
        this.skyvern.getNavigation().moveTo(this.x, this.y, this.z, 1.0F);
    }

    @Override
    public void stop() {
        this.skyvern.getNavigation().stop();
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return skyvern.isFlying() && !skyvern.getNavigation().isDone();
    }

    @Nullable
    private Vec3 getForwardLocation() {
        RandomSource random = skyvern.getRandom();
        Vec3 look = skyvern.getLookAngle();
        double forwardDistance = 32.0D + random.nextDouble() * 8.0D;
        double sideOffset = 32.0D + random.nextDouble() * 8.0D;
        double verticalOffset = (random.nextDouble() - 0.5D) * 16.0D;
        Vec3 sideways = new Vec3(-look.z, 0, look.x).normalize();
        Vec3 targetPos = skyvern.position().add(look.scale(forwardDistance)).add(sideways.scale(sideOffset)).add(0, verticalOffset, 0);
        BlockPos pos = BlockPos.containing(targetPos);
        if (this.canBlockPosBeSeen(pos) && skyvern.level().isEmptyBlock(pos)) return targetPos;
        return this.getRandomLocation();
    }

    @Nullable
    private Vec3 getRandomLocation() {
        RandomSource random = skyvern.getRandom();
        BlockPos blockpos = null;
        BlockPos origin = skyvern.hasRestriction() ? this.skyvern.getRestrictCenter() : skyvern.blockPosition();
        for (int i = 0; i < 15; i++) {
            BlockPos targetPos = origin.offset(random.nextInt(16 * 2) - 16, random.nextInt(16 * 2) - 16, random.nextInt(16 * 2) - 16);
            if (canBlockPosBeSeen(targetPos) && this.skyvern.level().isEmptyBlock(targetPos)) {
                blockpos = targetPos;
            }
        }
        return blockpos == null ? null : new Vec3(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D);
    }

    public boolean canBlockPosBeSeen(BlockPos pos) {
        double x = pos.getX() + 0.5F;
        double y = pos.getY() + 0.5F;
        double z = pos.getZ() + 0.5F;
        HitResult result = skyvern.level().clip(new ClipContext(new Vec3(skyvern.getX(), skyvern.getY() + (double) skyvern.getEyeHeight(), skyvern.getZ()), new Vec3(x, y, z), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, skyvern));
        double dist = result.getLocation().distanceToSqr(x, y, z);
        return dist <= 1.0D || result.getType() == HitResult.Type.MISS;
    }
}
