package com.peeko32213.hole.common.entity.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class SmartNearestTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public SmartNearestTargetGoal(Mob goalOwnerIn, Class<T> targetClassIn, boolean checkSight) {
        super(goalOwnerIn, targetClassIn, checkSight);
    }

    public SmartNearestTargetGoal(Mob goalOwnerIn, Class<T> targetClassIn, boolean checkSight, boolean nearbyOnlyIn) {
        super(goalOwnerIn, targetClassIn, checkSight, nearbyOnlyIn);
    }

    public SmartNearestTargetGoal(Mob goalOwnerIn, Class<T> targetClassIn, int targetChanceIn, boolean checkSight, boolean nearbyOnlyIn, @Nullable Predicate<LivingEntity> targetPredicate) {
        super(goalOwnerIn, targetClassIn, targetChanceIn, checkSight, nearbyOnlyIn, targetPredicate);
    }

    protected AABB getTargetSearchArea(double targetDistance) {
        return this.mob.getBoundingBox().inflate(targetDistance, targetDistance, targetDistance);
    }

}
