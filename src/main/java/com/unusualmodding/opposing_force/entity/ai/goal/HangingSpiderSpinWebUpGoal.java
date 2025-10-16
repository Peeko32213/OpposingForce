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

    public HangingSpiderSpinWebUpGoal(HangingSpider hangingSpider) {
        this.hangingSpider = hangingSpider;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return !hangingSpider.isAggressive() && !hangingSpider.isUpsideDown() && !hangingSpider.isGoingDown() && !hangingSpider.isWebOut() && hangingSpider.getNavigation().isDone() && hangingSpider.getGoingUpCooldown() <= 0;
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
        } else {
            hangingSpider.setGoingUp(false);
            hangingSpider.setGoingUpCooldown(hangingSpider.getRandom().nextInt(20 * 10) + (20 * 10));
            hangingSpider.deactivateWeb();
            stop();
        }
    }

    @Override
    public void tick() {
        if (targetCeilingPos != null) {
            double distanceSq = hangingSpider.distanceToSqr(Vec3.atCenterOf(targetCeilingPos).with(Direction.Axis.Y, hangingSpider.position().y));
            if (distanceSq < 2.0 && hangingSpider.getNavigation().isDone()) {
                hangingSpider.setGoingUp(true);
                hangingSpider.activateWeb(Vec3.atBottomCenterOf(targetCeilingPos.above()).toVector3f());
            }
        }

        if (hangingSpider.isGoingUp()) {
            if (hangingSpider.tickCount % 15 == 0) {
                hangingSpider.playSound(SoundEvents.SPIDER_STEP, 0.05F,1);
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
        return hangingSpider.isGoingUp() && !hangingSpider.isUpsideDown() && !hangingSpider.isGoingDown();
    }

    @Override
    public void stop() {
        if (hangingSpider.isGoingUp()) {
            hangingSpider.setGoingUp(false);
            hangingSpider.setGoingUpCooldown(hangingSpider.getRandom().nextInt(20 * 10) + (20 * 10));
            hangingSpider.setGoingDownCooldown(hangingSpider.getRandom().nextInt(90 * 50) + (40 * 20));
            hangingSpider.deactivateWeb();
            hangingSpider.playSound(SoundEvents.SPIDER_HURT);
        }
        targetCeilingPos = null;
    }
}