package com.peeko32213.hole.common.entity.goal.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.hole.common.entity.util.FearTheLightGoal;
import com.peeko32213.hole.core.registry.HoleGoals;
import com.scouter.goalsmith.data.GoalCodec;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

public class FearTheLightGoalCodec implements GoalCodec {
    private final int goalPriority;
    private final double movementSpeed;
    private final int executeChance;
    private final int lightLevel;

    public static final Codec<FearTheLightGoalCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("goal_priority").forGetter(FearTheLightGoalCodec::getGoalPriority),
            Codec.DOUBLE.fieldOf("movement_speed").forGetter(FearTheLightGoalCodec::getMovementSpeed),
            Codec.INT.fieldOf("execute_chance").forGetter(FearTheLightGoalCodec::getExecuteChance),
            Codec.INT.fieldOf("light_level").forGetter(FearTheLightGoalCodec::getLightLevel)
    ).apply(instance, FearTheLightGoalCodec::new));

    public FearTheLightGoalCodec(int goalPriority, double movementSpeed, int executeChance, int lightLevel) {
        this.goalPriority = goalPriority;
        this.movementSpeed = movementSpeed;
        this.executeChance = executeChance;
        this.lightLevel = lightLevel;
    }

    public int getGoalPriority() {
        return goalPriority;
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public int getExecuteChance() {
        return executeChance;
    }

    public int getLightLevel() {
        return lightLevel;
    }

    @Override
    public Goal addGoal(PathfinderMob pathfinderMob) {
        FearTheLightGoal fearGoal = new FearTheLightGoal(pathfinderMob, movementSpeed, executeChance, lightLevel);
        pathfinderMob.goalSelector.addGoal(goalPriority, fearGoal);
        return fearGoal;
    }

    @Override
    public Codec<? extends GoalCodec> codec() {
        return HoleGoals.FEAR_THE_LIGHT_GOAL.get();
    }
}
