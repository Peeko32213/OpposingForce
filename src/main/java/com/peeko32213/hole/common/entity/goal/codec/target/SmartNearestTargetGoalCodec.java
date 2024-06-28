package com.peeko32213.hole.common.entity.goal.codec.target;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.hole.common.entity.util.SmartNearestTargetGoal;
import com.peeko32213.hole.core.registry.HoleGoals;
import com.scouter.goalsmith.codec.NullableFieldCodec;
import com.scouter.goalsmith.data.GoalRegistry;
import com.scouter.goalsmith.data.PredicateCodec;
import com.scouter.goalsmith.data.TargetGoalCodec;
import com.scouter.goalsmith.data.predicates.TruePredicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class SmartNearestTargetGoalCodec implements TargetGoalCodec {
    public static final Codec<SmartNearestTargetGoalCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("target_goal_priority").forGetter(codec -> codec.goalPriority),
            TagKey.codec(Registries.ENTITY_TYPE).fieldOf("target_type").forGetter(codec -> codec.targetType),
            Codec.BOOL.fieldOf("must_see").forGetter(codec -> codec.mustSee),
            NullableFieldCodec.makeDefaultableField("big", Codec.BOOL, false).forGetter(codec -> codec.big)
    ).apply(instance, SmartNearestTargetGoalCodec::new));
    private final int goalPriority;
    private final TagKey<EntityType<?>> targetType;
    private final boolean mustSee;
    private final boolean big;

    public SmartNearestTargetGoalCodec(int goalPriority, TagKey<EntityType<?>> targetType, boolean mustSee, boolean big) {
        this.goalPriority = goalPriority;
        this.targetType = targetType;
        this.mustSee = mustSee;
        this.big = big;
    }


    @Override
    public Goal addTargetGoal(PathfinderMob mob) {
        SmartNearestTargetGoal goal = new SmartNearestTargetGoal(mob, targetType, mustSee, big);
        mob.targetSelector.addGoal(goalPriority, goal);
        return goal;
    }

    @Override
    public Codec<? extends TargetGoalCodec> codec() {
        return HoleGoals.SMART_NEAREST_TARGET_GOAL.get();
    }

    @Override
    public String toString() {
        return "SmartNearestTargetGoalCodec{" +
                "goalPriority=" + goalPriority +
                ", targetType=" + targetType +
                ", mustSee=" + mustSee +
                ", big=" + big +
                '}';
    }
}
