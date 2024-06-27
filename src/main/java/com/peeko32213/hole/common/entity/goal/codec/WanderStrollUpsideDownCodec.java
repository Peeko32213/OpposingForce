package com.peeko32213.hole.common.entity.goal.codec;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.hole.common.entity.EntityPaleSpider;
import com.peeko32213.hole.common.entity.util.WanderStrollUpsideDown;
import com.peeko32213.hole.core.registry.HoleGoals;
import com.scouter.goalsmith.data.GoalCodec;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.slf4j.Logger;

public class WanderStrollUpsideDownCodec implements GoalCodec {
    private final int goalPriority;
    private final double speedModifier;
    private final int interval;
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Codec<WanderStrollUpsideDownCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("goal_priority").forGetter(WanderStrollUpsideDownCodec::getGoalPriority),
            Codec.DOUBLE.fieldOf("speed_modifier").forGetter(WanderStrollUpsideDownCodec::getSpeedModifier),
            Codec.INT.fieldOf("interval").forGetter(WanderStrollUpsideDownCodec::getInterval)
    ).apply(instance, WanderStrollUpsideDownCodec::new));

    public WanderStrollUpsideDownCodec(int goalPriority, double speedModifier, int interval) {
        this.goalPriority = goalPriority;
        this.speedModifier = speedModifier;
        this.interval = interval;
    }

    public int getGoalPriority() {
        return goalPriority;
    }

    public double getSpeedModifier() {
        return speedModifier;
    }

    public int getInterval() {
        return interval;
    }

    @Override
    public Goal addGoal(PathfinderMob pathfinderMob) {
        if (pathfinderMob instanceof EntityPaleSpider spider) {
            WanderStrollUpsideDown wanderGoal = new WanderStrollUpsideDown(spider, speedModifier, interval);
            spider.goalSelector.addGoal(goalPriority, wanderGoal);
            return wanderGoal;
        }
        LOGGER.error("Unsupported Operation, Tried adding WanderStrollUpsideDown to non-EntityPaleSpider!");
        return null;
    }

    @Override
    public Codec<? extends GoalCodec> codec() {
        return HoleGoals.WANDER_STROLL_UPSIDE_DOWN_GOAL.get();
    }
}