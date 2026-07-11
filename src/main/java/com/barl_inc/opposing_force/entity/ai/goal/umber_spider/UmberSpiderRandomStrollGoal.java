package com.barl_inc.opposing_force.entity.ai.goal.umber_spider;

import com.barl_inc.opposing_force.entity.UmberSpider;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.level.LightLayer;

public class UmberSpiderRandomStrollGoal extends WaterAvoidingRandomStrollGoal {

    private final UmberSpider umberSpider;

    public UmberSpiderRandomStrollGoal(UmberSpider mob) {
        super(mob, 1.0D, 0.001F);
        this.umberSpider = mob;
    }

    @Override
    public boolean canUse() {
        return !this.umberSpider.isVehicle() && this.canStroll() && super.canUse();
    }

    private boolean canStroll() {
        if (this.umberSpider.isElite()) {
            return true;
        }
        else return this.umberSpider.level().getBrightness(LightLayer.BLOCK, this.umberSpider.blockPosition()) <= this.umberSpider.getLightThreshold();
    }
}