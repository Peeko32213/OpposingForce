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

    public HangingSpiderSpinWebDownGoal(HangingSpider hangingSpider) {
        this.hangingSpider = hangingSpider;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return hangingSpider.isUpsideDown() && !hangingSpider.isGoingUp() && !hangingSpider.isWebOut() && hangingSpider.getNavigation().isDone() && hangingSpider.getGoingDownCooldown() <= 0;
    }

    @Override
    public void start() {
        Vec3 mobCenter = hangingSpider.position();
        BlockHitResult hit = hangingSpider.raycastFloorOrCeiling(mobCenter, true);
        hangingSpider.setUpsideDown(false);

        if (hit.getType() != HitResult.Type.BLOCK) {
            hit = hangingSpider.searchForNearbyBlock(mobCenter, 15, true);
        }

        if (hit.getType() == HitResult.Type.BLOCK) {
            targetFloorPos = BlockPos.containing(hit.getLocation());
        } else {
            hangingSpider.setGoingDown(false);
            hangingSpider.setGoingDownCooldown(hangingSpider.getRandom().nextInt(90 * 50) + (40 * 20));
            hangingSpider.deactivateWeb();
            stop();
        }
    }

    @Override
    public void tick() {
        if (targetFloorPos != null) {
            double distanceSq = hangingSpider.distanceToSqr(Vec3.atCenterOf(targetFloorPos).with(Direction.Axis.Y, hangingSpider.position().y));
            if (distanceSq < 2.0 && hangingSpider.getNavigation().isDone()) {
                hangingSpider.setGoingDown(true);
                hangingSpider.activateWeb(Vec3.atBottomCenterOf(targetFloorPos.above()).toVector3f());
            }
        }

        if (hangingSpider.isGoingDown()) {
            if (hangingSpider.tickCount % 30 == 0) {
                hangingSpider.playSound(SoundEvents.SPIDER_STEP, 0.05F,1);
            }
            hangingSpider.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.005D);
            if (hangingSpider.getY() > hangingSpider.getTargetPos().y) {
                hangingSpider.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.08D);
                stop();
            }
        }
    }


    @Override
    public boolean canContinueToUse() {
        return hangingSpider.isGoingDown() && !hangingSpider.isUpsideDown() && !hangingSpider.isGoingUp() && !hangingSpider.onGround() && !hangingSpider.isInWaterOrBubble();
    }

    @Override
    public void stop() {
        if (hangingSpider.isGoingDown()) {
            hangingSpider.setGoingDown(false);
            hangingSpider.setGoingDownCooldown(hangingSpider.getRandom().nextInt(90 * 50) + (40 * 20));
            hangingSpider.deactivateWeb();
            hangingSpider.playSound(SoundEvents.SPIDER_HURT);
        }
        targetFloorPos = null;
    }
}