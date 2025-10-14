package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.UmberSpider;
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
        return !this.umberSpider.isVehicle() && this.umberSpider.level().getBrightness(LightLayer.BLOCK, this.umberSpider.blockPosition()) <= this.umberSpider.getLightThreshold() && super.canUse();
    }
}