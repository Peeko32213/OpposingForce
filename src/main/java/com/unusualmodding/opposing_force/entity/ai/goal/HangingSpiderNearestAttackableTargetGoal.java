package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.HangingSpider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.phys.AABB;

public class HangingSpiderNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    public HangingSpiderNearestAttackableTargetGoal(HangingSpider spider, Class mob, boolean canSee) {
        super(spider, mob, canSee);
    }

    @Override
    protected AABB getTargetSearchArea(double targetDistance) {
        AABB bb = this.mob.getBoundingBox().inflate(targetDistance, targetDistance, targetDistance);
        return new AABB(bb.minX, 0, bb.minZ, bb.maxX, 256, bb.maxZ);
    }
}