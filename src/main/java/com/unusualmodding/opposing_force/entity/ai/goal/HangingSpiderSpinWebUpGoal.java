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

public class HangingSpiderSpinWebUpGoal extends Goal {

    private final HangingSpider hangingSpider;
    private BlockPos targetCeilingPos = null;
    private boolean navigatingToCeiling = false;
    private int stuckTicks;

    public HangingSpiderSpinWebUpGoal(HangingSpider hangingSpider) {
        this.hangingSpider = hangingSpider;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return !hangingSpider.isUpsideDown() && !hangingSpider.isGoingDown() && !hangingSpider.isWebOut() && hangingSpider.getNavigation().isDone() && hangingSpider.getGoingUpCooldown() <= 0;
    }

    @Override
    public void start() {
        Vec3 mobCenter = hangingSpider.position();
        BlockHitResult hit = hangingSpider.raycastFloorOrCeiling(mobCenter, false);

        if (hit.getType() != HitResult.Type.BLOCK) {
            hit = hangingSpider.searchForNearbyBlock(mobCenter, 15, false);
        }

        if (hit.getType() == HitResult.Type.BLOCK) {
            targetCeilingPos = BlockPos.containing(hit.getLocation());
            navigatingToCeiling = true;

            BlockPos.MutableBlockPos walkTarget = targetCeilingPos.mutable();
            while (walkTarget.getY() > hangingSpider.level().getMinBuildHeight() && hangingSpider.level().getBlockState(walkTarget.below()).isAir()) {
                walkTarget.move(Direction.DOWN);
            }

            hangingSpider.getNavigation().moveTo(walkTarget.getX() + 0.5, walkTarget.getY() + 1, walkTarget.getZ() + 0.5, 1.0);
        } else {
            Vec3 randomPos = mobCenter.add(hangingSpider.getRandom().nextGaussian() * 5, 0, hangingSpider.getRandom().nextGaussian() * 5);
            hangingSpider.getNavigation().moveTo(randomPos.x, randomPos.y, randomPos.z, 1.0);
            navigatingToCeiling = false;
        }
    }

    @Override
    public void tick() {
        if (navigatingToCeiling && hangingSpider.getDeltaMovement().horizontalDistanceSqr() < 0.1) stuckTicks++;
        if (stuckTicks > 100) hangingSpider.getNavigation().recomputePath();

        if (navigatingToCeiling && targetCeilingPos != null) {

            BlockPos.MutableBlockPos walkTarget = targetCeilingPos.mutable();
            while (walkTarget.getY() > hangingSpider.level().getMinBuildHeight() && hangingSpider.level().getBlockState(walkTarget.below()).isAir()) {
                walkTarget.move(Direction.DOWN);
            }

            hangingSpider.getNavigation().moveTo(walkTarget.getX() + 0.5, walkTarget.getY() + 1, walkTarget.getZ() + 0.5, 1.0);

            double distanceSq = hangingSpider.distanceToSqr(Vec3.atCenterOf(targetCeilingPos).with(Direction.Axis.Y, hangingSpider.position().y));
            if (distanceSq < 2.0 && hangingSpider.getNavigation().isDone()) {
                hangingSpider.setGoingUp(true);
                hangingSpider.playSound(SoundEvents.SPIDER_AMBIENT);
                hangingSpider.activateWeb(Vec3.atBottomCenterOf(targetCeilingPos.above()).toVector3f());
                navigatingToCeiling = false;
            }
        }

        if (hangingSpider.isGoingUp()) {
            if (hangingSpider.tickCount % 15 == 0) {
                hangingSpider.playSound(SoundEvents.SPIDER_STEP, 0.1F,1);
            }
            hangingSpider.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(-0.01D);
            if (hangingSpider.getY() > hangingSpider.getTargetPos().y) {
                hangingSpider.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.08D);
                stop();
            }
        }
    }


    @Override
    public boolean canContinueToUse() {
        return (navigatingToCeiling || hangingSpider.isGoingUp()) && !hangingSpider.isUpsideDown() && !hangingSpider.isGoingDown();
    }

    @Override
    public void stop() {
        if (hangingSpider.isGoingUp()) {
            hangingSpider.setGoingUp(false);
            hangingSpider.setGoingDownCooldown(300);
            hangingSpider.deactivateWeb();
            hangingSpider.playSound(SoundEvents.SPIDER_HURT);
        }
        navigatingToCeiling = false;
        targetCeilingPos = null;
    }
}