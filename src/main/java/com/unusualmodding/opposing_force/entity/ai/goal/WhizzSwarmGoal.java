package com.unusualmodding.opposing_force.entity.ai.goal;

import com.mojang.datafixers.DataFixUtils;
import com.unusualmodding.opposing_force.entity.Whizz;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class WhizzSwarmGoal extends Goal {

    private final Whizz whizz;
    private int timeToRecalcPath;
    private int nextStartTick;

    public WhizzSwarmGoal(Whizz whizz) {
        this.whizz = whizz;
        this.nextStartTick = this.nextStartTick(whizz);
    }

    protected int nextStartTick(Whizz whizz) {
        return reducedTickDelay(200 + whizz.getRandom().nextInt(200) % 20);
    }

    @Override
    public boolean canUse() {
        if (this.whizz.hasFollowers()) {
            return false;
        } else if (this.whizz.isFollower()) {
            return true;
        } else if (this.nextStartTick > 0) {
            --this.nextStartTick;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(this.whizz);
            Predicate<Whizz> predicate = (whizz) -> whizz.canBeFollowed() || !whizz.isFollower();
            List<? extends Whizz> list = this.whizz.level().getEntitiesOfClass(this.whizz.getClass(), this.whizz.getBoundingBox().inflate(10.0D, 10.0D, 10.0D), predicate);
            Whizz whizz1 = DataFixUtils.orElse(list.stream().filter(Whizz::canBeFollowed).findAny(), this.whizz);
            whizz1.addFollowers(list.stream().filter((whizz) -> !whizz.isFollower()));
            return this.whizz.isFollower();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.whizz.isFollower() && this.whizz.inRangeOfLeader();
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.whizz.stopFollowing();
    }

    @Override
    public void tick() {
        if (this.timeToRecalcPath-- <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.whizz.pathToLeader();
        }
    }
}