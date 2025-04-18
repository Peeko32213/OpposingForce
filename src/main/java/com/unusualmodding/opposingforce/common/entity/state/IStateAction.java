package com.unusualmodding.opposingforce.common.entity.state;

import com.google.common.collect.ImmutableMap;
import com.unusualmodding.opposingforce.OpposingForce;
import net.minecraft.world.entity.Entity;

import java.util.List;

public interface IStateAction {
    ImmutableMap<String, StateHelper> getStates();
    List<WeightedState<StateHelper>> getWeightedStatesToPerform();
    boolean getAction();
    void setAction(boolean action);
    default StateHelper getRandomState(Entity entity) {
        StateHelper state =  WeightedRandomState.getRandomState(entity.level().random, getWeightedStatesToPerform());
        if(state == null) {
            throw new RuntimeException("State is null");
        }
       return state;
    }

    default StateHelper getStateForName(Entity entity, String name) {
        if(getStates().containsKey(name)) {
            return getStates().get(name);
        } else {
            OpposingForce.LOGGER.error("Could not get state {} for {}", name, entity.getEncodeId());
            return null;
        }
    }

}
