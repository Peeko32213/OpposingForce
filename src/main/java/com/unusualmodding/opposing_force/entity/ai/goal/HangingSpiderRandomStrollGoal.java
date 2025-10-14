package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.HangingSpider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Random;

public class HangingSpiderRandomStrollGoal extends RandomStrollGoal {

    private final HangingSpider hangingSpider;

    public HangingSpiderRandomStrollGoal(HangingSpider spider) {
        super(spider, 1D, 50);
        this.hangingSpider = spider;
    }

    @Nullable
    protected Vec3 getPosition() {
        if (this.hangingSpider.isUpsideDown()) {
            for (int i = 0; i < 15; i++) {
                Random rand = new Random();
                BlockPos randPos = this.hangingSpider.blockPosition().offset(rand.nextInt(16) - 8, -2, rand.nextInt(16) - 8);
                BlockPos lowestPos = HangingSpider.getLowestPos(hangingSpider.level(), randPos);
                if (hangingSpider.level().getBlockState(lowestPos).isFaceSturdy(hangingSpider.level(), lowestPos, Direction.DOWN)) {
                    return Vec3.atCenterOf(lowestPos);
                }
            }
            return null;
        } else {
            return super.getPosition();
        }
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !hangingSpider.isGoingDown() && !hangingSpider.isGoingUp();
    }

    @Override
    public boolean canContinueToUse() {
        if (this.hangingSpider.isUpsideDown()) {
            double d0 = this.hangingSpider.getX() - wantedX;
            double d2 = this.hangingSpider.getZ() - wantedZ;
            double d4 = d0 * d0 + d2 * d2;
            return d4 > 4;
        } else {
            return super.canContinueToUse();
        }
    }

    @Override
    public void stop() {
        super.stop();
        this.wantedX = 0;
        this.wantedY = 0;
        this.wantedZ = 0;
    }

    @Override
    public void start() {
        if (this.hangingSpider.isUpsideDown()) {
            this.mob.getMoveControl().setWantedPosition(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier * 0.7F);
        } else {
            this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
        }
    }
}