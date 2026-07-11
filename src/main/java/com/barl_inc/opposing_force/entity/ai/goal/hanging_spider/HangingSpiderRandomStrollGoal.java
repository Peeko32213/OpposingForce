package com.barl_inc.opposing_force.entity.ai.goal.hanging_spider;

import com.barl_inc.opposing_force.entity.HangingSpider;
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