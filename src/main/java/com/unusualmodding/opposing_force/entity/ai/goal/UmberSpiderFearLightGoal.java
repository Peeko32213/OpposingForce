package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.UmberSpider;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class UmberSpiderFearLightGoal extends Goal {

    private final UmberSpider umberSpider;

    public UmberSpiderFearLightGoal(UmberSpider mob) {
        this.umberSpider = mob;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return !this.umberSpider.isVehicle() && (this.umberSpider.level().getBrightness(LightLayer.BLOCK, this.umberSpider.blockPosition()) > this.umberSpider.getLightThreshold() || this.umberSpider.isOnFire()) && this.umberSpider.fleeFromPosition != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void stop() {
        this.umberSpider.fleeFromPosition = null;
        this.umberSpider.fleeLightFor = 50;
    }

    @Override
    public void tick() {
        this.umberSpider.fleeLightFor = 50;
        this.umberSpider.setAttacking(false);

        if (this.umberSpider.getNavigation().isDone()) {
            Vec3 vec3 = LandRandomPos.getPosAway(this.umberSpider, 10, 7, this.umberSpider.fleeFromPosition);
            if (vec3 != null) {
                this.umberSpider.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.6D);
            }
        }
    }
}