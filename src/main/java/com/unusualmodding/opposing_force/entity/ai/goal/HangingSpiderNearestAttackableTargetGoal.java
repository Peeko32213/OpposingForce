package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.HangingSpider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.phys.AABB;

public class HangingSpiderNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    private final HangingSpider hangingSpider;

    public HangingSpiderNearestAttackableTargetGoal(HangingSpider hangingSpider, Class<T> target) {
        super(hangingSpider, target, 10, true, true, null);
        this.hangingSpider = hangingSpider;
    }

    @Override
    protected AABB getTargetSearchArea(double distance) {
        if (hangingSpider.isUpsideDown()) {
            AABB aabb = this.hangingSpider.getBoundingBox();
            double newDistance = 2.0F;
            return new AABB(aabb.minX - newDistance, hangingSpider.level().getMinBuildHeight() - 5, aabb.minZ - newDistance, aabb.maxX + newDistance, aabb.maxY + 1, aabb.maxZ + newDistance);
        } else {
            return this.hangingSpider.getBoundingBox().inflate(20, 20, 20);
        }
    }
}