package com.peeko32213.hole.common.entity.state;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.function.Predicate;

public class StateHelper {
    private final EntityDataAccessor<Boolean> state;
    private final int playTime;
    private final int stopTime;
    private final boolean affectsAI;
    private final String name;
    private final EnumSet<Goal.Flag> affectedFlags;
    private final Predicate<LivingEntity> startingPredicate;
    private final EntityAction entityAction;

    public StateHelper(EntityDataAccessor<Boolean> state, String name) {
        this(state, name, 0, 0, false, EnumSet.noneOf(Goal.Flag.class), defaultStartingPredicate(), defaultEntityAction());
    }

    public StateHelper(EntityDataAccessor<Boolean> state, String name, int playTime) {
        this(state,name, playTime, 0, false, EnumSet.noneOf(Goal.Flag.class), defaultStartingPredicate(), defaultEntityAction());
    }

    public StateHelper(EntityDataAccessor<Boolean> state, String name, int playTime, int stopTime) {
        this(state,name, playTime, stopTime, false, EnumSet.noneOf(Goal.Flag.class), defaultStartingPredicate(), defaultEntityAction());
    }

    public StateHelper(EntityDataAccessor<Boolean> state, String name, int playTime, int stopTime, boolean affectsAI) {
        this(state,name, playTime, stopTime, affectsAI, EnumSet.noneOf(Goal.Flag.class), defaultStartingPredicate(), defaultEntityAction());
    }

    public StateHelper(EntityDataAccessor<Boolean> state, String name , int playTime, int stopTime, boolean affectsAI,
                       EnumSet<Goal.Flag> affectedFlags) {
        this(state,name, playTime, stopTime, affectsAI, affectedFlags, defaultStartingPredicate(), defaultEntityAction());
    }

    public StateHelper(EntityDataAccessor<Boolean> state, String name,  int playTime, int stopTime, boolean affectsAI,
                       EnumSet<Goal.Flag> affectedFlags, Predicate<LivingEntity> startingPredicate) {
        this(state,name, playTime, stopTime, affectsAI, affectedFlags, startingPredicate, defaultEntityAction());
    }
    //todo add stop and start action
    public StateHelper(EntityDataAccessor<Boolean> state, String name, int playTime, int stopTime, boolean affectsAI,
                       EnumSet<Goal.Flag> affectedFlags, Predicate<LivingEntity> startingPredicate,
                       EntityAction entityAction) {
        if (state == null || startingPredicate == null || entityAction == null) {
            throw new IllegalArgumentException("State, starting predicate, and entity action cannot be null.");
        }
        this.state = state;
        this.playTime = playTime;
        this.stopTime = stopTime;
        this.affectsAI = affectsAI;
        this.affectedFlags = affectedFlags;
        this.startingPredicate = startingPredicate;
        this.entityAction = entityAction;
        this.name = name;
    }


    public EntityDataAccessor<Boolean> getState() {
        return state;
    }

    public int getPlayTime() {
        return playTime;
    }

    public int getStopTime() {
        return stopTime;
    }

    public boolean isAffectsAI() {
        return affectsAI;
    }

    public EnumSet<Goal.Flag> getAffectedFlags() {
        return affectedFlags;
    }

    public Predicate<LivingEntity> getStartingPredicate() {
        return startingPredicate;
    }

    public String getName() {
        return name;
    }

    public EntityAction getEntityAction() {
        return entityAction;
    }

    // Default method for startingPredicate that does nothing
    private static Predicate<LivingEntity> defaultStartingPredicate() {
        return (e) -> true;
    }

    // Default entity action
    private static EntityAction defaultEntityAction() {
        return new EntityAction(0, (e) -> {}, 0); // Default action that does nothing
    }

    public static class Builder {
        private EntityDataAccessor<Boolean> state;
        private int playTime = 0;
        private int stopTime = 0;
        private boolean affectsAI = false;
        private EnumSet<Goal.Flag> affectedFlags = EnumSet.noneOf(Goal.Flag.class);
        private Predicate<LivingEntity> startingPredicate = (e) -> true;
        private String name;
        private EntityAction entityAction = new EntityAction(0, (e) -> {}, 0); // Default action that does nothing

        public static Builder state(EntityDataAccessor<Boolean> state, String name) {
            return new Builder(state, name);
        }
        public Builder(EntityDataAccessor<Boolean> state, String name) {
            if (state == null) {
                throw new IllegalArgumentException("State cannot be null.");
            }
            this.state = state;
            this.name = name;
        }

        public Builder playTime(int playTimeTicks) {
            this.playTime = playTimeTicks;
            return this;
        }

        public Builder stopTime(int stopTimeTicks) {
            this.stopTime = stopTimeTicks;
            return this;
        }

        public Builder affectsAI(boolean affectsAI) {
            this.affectsAI = affectsAI;
            return this;
        }

        public Builder affectedFlags(EnumSet<Goal.Flag> affectedFlags) {
            this.affectedFlags = affectedFlags;
            return this;
        }

        public Builder startingPredicate(Predicate<LivingEntity> startingPredicate) {
            if (startingPredicate == null) {
                throw new IllegalArgumentException("Starting predicate cannot be null.");
            }
            this.startingPredicate = startingPredicate;
            return this;
        }

        public Builder entityAction(EntityAction entityAction) {
            if (entityAction == null) {
                throw new IllegalArgumentException("Entity action cannot be null.");
            }
            this.entityAction = entityAction;
            return this;
        }

        public StateHelper build() {
            return new StateHelper(state,name ,playTime, stopTime, affectsAI, affectedFlags, startingPredicate, entityAction);
        }
    }
}


