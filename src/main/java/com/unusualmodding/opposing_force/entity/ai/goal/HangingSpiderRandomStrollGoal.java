package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.HangingSpider;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class HangingSpiderRandomStrollGoal extends WaterAvoidingRandomStrollGoal {

    private final HangingSpider hangingSpider;

    public HangingSpiderRandomStrollGoal(HangingSpider spider) {
        super(spider, 1.0D, 120);
        this.hangingSpider = spider;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !hangingSpider.isGoingDown() && !hangingSpider.isGoingUp() && !hangingSpider.isUpsideDown();
    }
}