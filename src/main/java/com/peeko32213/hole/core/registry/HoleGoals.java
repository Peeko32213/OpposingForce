package com.peeko32213.hole.core.registry;

import com.mojang.serialization.Codec;
import com.peeko32213.hole.Hole;
import com.peeko32213.hole.common.entity.goal.codec.DroppingMeleeGoalCodec;
import com.peeko32213.hole.common.entity.goal.codec.FearTheLightGoalCodec;
import com.peeko32213.hole.common.entity.goal.codec.WanderStrollUpsideDownCodec;
import com.peeko32213.hole.common.entity.goal.codec.target.SmartNearestTargetGoalCodec;
import com.peeko32213.hole.common.entity.util.DroppingMeleeGoal;
import com.scouter.goalsmith.GoalSmith;
import com.scouter.goalsmith.data.GoalCodec;
import com.scouter.goalsmith.data.PMRegistries;
import com.scouter.goalsmith.data.TargetGoalCodec;
import com.scouter.goalsmith.data.goalcodec.RandomStrollGoalCodec;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class HoleGoals {
    public static final DeferredRegister<Codec<? extends GoalCodec>> GOAL_TYPE_SERIALIZER = DeferredRegister.create(PMRegistries.Keys.GOAL_TYPE_SERIALIZERS, Hole.MODID);
    public static final DeferredRegister<Codec<? extends TargetGoalCodec>> TARGET_GOAL_TYPE_SERIALIZER = DeferredRegister.create(PMRegistries.Keys.TARGET_GOAL_TYPE_SERIALIZERS, GoalSmith.MODID);

    public static final RegistryObject<Codec<DroppingMeleeGoalCodec>> DROPPING_MELEE_GOAL = GOAL_TYPE_SERIALIZER.register("dropping_melee_goal", () -> DroppingMeleeGoalCodec.CODEC);
    public static final RegistryObject<Codec<FearTheLightGoalCodec>> FEAR_THE_LIGHT_GOAL = GOAL_TYPE_SERIALIZER.register("fear_the_light_goal", () -> FearTheLightGoalCodec.CODEC);
    public static final RegistryObject<Codec<WanderStrollUpsideDownCodec>> WANDER_STROLL_UPSIDE_DOWN_GOAL = GOAL_TYPE_SERIALIZER.register("wander_stroll_upside_down_goal", () -> WanderStrollUpsideDownCodec.CODEC);


    public static final RegistryObject<Codec<SmartNearestTargetGoalCodec>> SMART_NEAREST_TARGET_GOAL = TARGET_GOAL_TYPE_SERIALIZER.register("smart_nearest_target_goal", () -> SmartNearestTargetGoalCodec.CODEC);

}
