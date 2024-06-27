package com.peeko32213.hole.common.entity.util;

import com.scouter.goalsmith.data.goals.target.NearestAttackableTargetGoalImproved;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class SmartNearestTargetGoal extends NearestAttackableTargetGoalImproved {


    private final boolean big;
    public SmartNearestTargetGoal(Mob pMob, TagKey<EntityType<?>> pTargetType, boolean pMustSee, boolean big) {
        super(pMob, pTargetType, 10, pMustSee, false, (Predicate<LivingEntity>) null);
        this.big = big;
    }


    protected AABB getTargetSearchArea(double targetDistance) {
        if(big) {
            AABB bb = this.mob.getBoundingBox().inflate(targetDistance, targetDistance, targetDistance);
            return new AABB(bb.minX, 0, bb.minZ, bb.maxX, 32, bb.maxZ);
        }
        return this.mob.getBoundingBox().inflate(targetDistance, targetDistance, targetDistance);
    }

}
