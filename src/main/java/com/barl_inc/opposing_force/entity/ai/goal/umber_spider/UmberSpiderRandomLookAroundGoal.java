package com.barl_inc.opposing_force.entity.ai.goal.umber_spider;

import com.barl_inc.opposing_force.entity.UmberSpider;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.level.LightLayer;

import java.util.EnumSet;

public class UmberSpiderRandomLookAroundGoal extends RandomLookAroundGoal {

    private final UmberSpider umberSpider;

    public UmberSpiderRandomLookAroundGoal(UmberSpider mob) {
        super(mob);
        this.umberSpider = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return !this.umberSpider.isVehicle() && this.canLookAround() && super.canUse();
    }

    private boolean canLookAround() {
        if (this.umberSpider.isElite()) {
            return true;
        }
        else return this.umberSpider.level().getBrightness(LightLayer.BLOCK, this.umberSpider.blockPosition()) <= this.umberSpider.getLightThreshold();
    }
}