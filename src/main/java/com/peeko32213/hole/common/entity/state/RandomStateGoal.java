package com.peeko32213.hole.common.entity.state;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class RandomStateGoal<T extends LivingEntity & IStateAction> extends Goal {
    private final T entity;
    private final StateHelper helper;
    private int playTicks;
    private int cooldownTicks;
    private int timesActionPerformed;

    //available in newer java version, sadly we're not there yet :(
    //public RandomStateGoal(T entity) {
    //  int states = entity.getStatesToPerform().size();
    //  StateHelper stateHelper = entity.getStatesToPerform().get(entity.getRandom().nextInt(states));
    //  this(entity, stateHelper);
    //}

    public RandomStateGoal(T entity) {
        this.entity = entity;
        this.helper = entity.getRandomState(entity);
        if (helper.isAffectsAI()) {
            this.entity.setAction(true);
            this.setFlags(helper.getAffectedFlags());
        }
    }

    @Override
    public boolean canUse() {
        boolean hasConditions = this.helper.getStartingPredicate().test((LivingEntity) this.entity);
        return playTicks < this.helper.getPlayTime() && cooldownTicks-- < 0 && hasConditions;
    }

    @Override
    public void start() {
        this.entity.getEntityData().set(helper.getState(), true);
    }

    @Override
    public void tick() {
        super.tick();
        this.playTicks++;
        if(this.timesActionPerformed < this.helper.getEntityAction().getTimesToPerform() &&
                this.playTicks >= this.helper.getEntityAction().getTimeToPerformAction()) {
            this.helper.getEntityAction().getAction().accept(entity);
            this.timesActionPerformed++;

        }
    }

    @Override
    public boolean canContinueToUse() {
        return playTicks < this.helper.getPlayTime();
    }


    @Override
    public void stop() {
        this.entity.getEntityData().set(helper.getState(), false);
        this.entity.setAction(false);
        this.cooldownTicks = this.helper.getStopTime();
        this.playTicks = 0;
        this.timesActionPerformed = 0;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
