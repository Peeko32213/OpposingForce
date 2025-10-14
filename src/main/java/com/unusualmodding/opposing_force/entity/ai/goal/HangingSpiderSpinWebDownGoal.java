package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.HangingSpider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

import java.util.EnumSet;

public class HangingSpiderSpinWebDownGoal extends Goal {

    private final HangingSpider hangingSpider;
    private BlockPos targetFloorPos = null;
    private boolean navigatingToFloor = false;
    private int stuckTicks;

    public HangingSpiderSpinWebDownGoal(HangingSpider hangingSpider) {
        this.hangingSpider = hangingSpider;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.hangingSpider.isAggressive() && this.hangingSpider.getTarget() != null && this.hangingSpider.getTarget().isAlive() && !hangingSpider.onGround() && !hangingSpider.isWebOut() && hangingSpider.getNavigation().isDone() && !hangingSpider.isGoingUp() && hangingSpider.getGoingDownCooldown() <= 0;
    }

    @Override
    public void start() {
        Vec3 mobCenter = hangingSpider.position();
        BlockHitResult result = hangingSpider.raycastFloorOrCeiling(mobCenter, true);
        if (result.getType() != HitResult.Type.BLOCK) {
            result = hangingSpider.searchForNearbyBlock(mobCenter, 15, true);
        }

        if (result.getType() == HitResult.Type.BLOCK) {
            targetFloorPos = BlockPos.containing(result.getLocation());
            navigatingToFloor = true;

            BlockPos.MutableBlockPos walkTarget = targetFloorPos.mutable();
            while (walkTarget.getY() > hangingSpider.level().getMinBuildHeight() && hangingSpider.level().getBlockState(walkTarget.below()).isAir()) {
                walkTarget.move(Direction.DOWN);
            }

            hangingSpider.getNavigation().moveTo(walkTarget.getX() + 0.5, walkTarget.getY(), walkTarget.getZ() + 0.5, 1.0);
        } else {
            Vec3 randomPos = mobCenter.add(hangingSpider.getRandom().nextGaussian() * 5, 0, hangingSpider.getRandom().nextGaussian() * 5);
            hangingSpider.getNavigation().moveTo(randomPos.x, randomPos.y, randomPos.z, 1.0);
            navigatingToFloor = false;
        }
    }

    @Override
    public void tick() {
        if (navigatingToFloor && hangingSpider.getDeltaMovement().horizontalDistanceSqr() < 0.1) stuckTicks++;
        if (stuckTicks > 100) hangingSpider.getNavigation().recomputePath();

        if (navigatingToFloor && targetFloorPos != null) {

            BlockPos.MutableBlockPos walkTarget = targetFloorPos.mutable();
            while (walkTarget.getY() > hangingSpider.level().getMinBuildHeight() && hangingSpider.level().getBlockState(walkTarget.below()).isAir()) {
                walkTarget.move(Direction.DOWN);
            }

            hangingSpider.getNavigation().moveTo(walkTarget.getX() + 0.5, walkTarget.getY() + 1, walkTarget.getZ() + 0.5, 1.0);

            double distanceSq = hangingSpider.distanceToSqr(Vec3.atCenterOf(targetFloorPos).with(Direction.Axis.Y, hangingSpider.position().y));
            if (distanceSq < 2.0 && hangingSpider.getNavigation().isDone()) {
                hangingSpider.setGoingDown(true);
                hangingSpider.playSound(SoundEvents.SPIDER_AMBIENT);
                hangingSpider.activateWeb(Vec3.atBottomCenterOf(targetFloorPos.above()).toVector3f());
                navigatingToFloor = false;
            }
        }

        if (hangingSpider.isGoingDown()) {
            if (hangingSpider.tickCount % 15 == 0) {
                hangingSpider.playSound(SoundEvents.SPIDER_STEP, 1,1);
            }
            hangingSpider.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.04D);
            if (hangingSpider.getY() > hangingSpider.getTargetPos().y) {
                hangingSpider.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.08D);
                stop();
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return (navigatingToFloor || hangingSpider.isGoingDown()) && !hangingSpider.onGround() && !hangingSpider.isGoingUp();
    }

    @Override
    public void stop() {
        if (hangingSpider.isGoingDown()) {
            hangingSpider.setGoingDown(false);
            hangingSpider.setGoingUpCooldown(300);
            hangingSpider.deactivateWeb();
            hangingSpider.playSound(SoundEvents.SPIDER_HURT);
        }
        navigatingToFloor = false;
        targetFloorPos = null;
    }
}