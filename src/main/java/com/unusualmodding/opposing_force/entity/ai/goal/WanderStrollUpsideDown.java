package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.PaleSpider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class WanderStrollUpsideDown extends RandomStrollGoal {


    private final PaleSpider spider;

    public WanderStrollUpsideDown(PaleSpider spider, double speedModifier, int interval) {
        super(spider, speedModifier, interval);
        this.spider = spider;
    }

    @Nullable
    protected Vec3 getPosition() {
        if (spider.isUpsideDown()) {
            for (int i = 0; i < 15; i++) {
                RandomSource rand = spider.getRandom();
                BlockPos randPos = spider.blockPosition().offset(rand.nextInt(16) - 8, -2, rand.nextInt(16) - 8);
                BlockPos lowestPos = PaleSpider.getLowestPos(spider.level(), randPos);
                if (spider.level().getBlockState(lowestPos).isFaceSturdy(spider.level(), lowestPos, Direction.DOWN)) {
                    return Vec3.atCenterOf(lowestPos);
                }
            }
            return null;
        } else {
            return super.getPosition();
        }
    }

    public boolean canUse() {
        return super.canUse();
    }

    public boolean canContinueToUse() {
        if (spider.isUpsideDown()) {
            double d0 = spider.getX() - wantedX;
            double d2 = spider.getZ() - wantedZ;
            double d4 = d0 * d0 + d2 * d2;
            return d4 > 4;
        } else {
            return super.canContinueToUse();
        }
    }

    public void stop() {
        super.stop();
        this.wantedX = 0;
        this.wantedY = 0;
        this.wantedZ = 0;
    }

    public void start() {
        if (spider.isUpsideDown()) {
            this.mob.getMoveControl().setWantedPosition(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier * 0.7F);
        } else {
            this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
        }
    }
}