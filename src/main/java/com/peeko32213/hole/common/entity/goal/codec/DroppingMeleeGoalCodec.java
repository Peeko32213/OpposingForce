package com.peeko32213.hole.common.entity.goal.codec;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.hole.common.entity.EntityPaleSpider;
import com.peeko32213.hole.common.entity.util.DroppingMeleeGoal;
import com.peeko32213.hole.core.registry.HoleGoals;
import com.scouter.goalsmith.data.GoalCodec;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.slf4j.Logger;


public record DroppingMeleeGoalCodec(int goalPriority) implements GoalCodec {
        private static final Logger LOGGER = LogUtils.getLogger();
        public static final Codec<DroppingMeleeGoalCodec> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                Codec.INT.fieldOf("goal_priority").forGetter(codec -> codec.goalPriority)
        ).apply(builder, DroppingMeleeGoalCodec::new));

        @Override
        public Goal addGoal(PathfinderMob pathfinderMob) {
            if(pathfinderMob instanceof EntityPaleSpider spider) {
                DroppingMeleeGoal meleeGoal = new DroppingMeleeGoal(spider);
                spider.goalSelector.addGoal(goalPriority, meleeGoal);
                return meleeGoal;
            }

            LOGGER.error("Unsupported Operation, Tried adding DroppingMeleeGoalCodec to non-EntityPaleSpider!");
            return null;
        }

        @Override
        public Codec<? extends GoalCodec> codec() {
            return HoleGoals.DROPPING_MELEE_GOAL.get();
        }
    }
