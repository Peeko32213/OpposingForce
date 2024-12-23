package com.peeko32213.hole.common.entity.state;

import com.google.common.collect.ImmutableMap;
import com.peeko32213.hole.Hole;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public interface IAttackStateAction {

    default ImmutableMap<String, StateHelper> getSpecialAttack() {
        return ImmutableMap.of();
    }

    @Nullable
    default StateHelper getSpecialAttackToPerform() {
        return null;
    }

    default ImmutableMap<String, StateHelper> getAttackStates() {
        return ImmutableMap.of();
    }

    default List<WeightedState<StateHelper>> getWeightedAttackStatesToPerform() {
        return Collections.emptyList();
    }

    default StateHelper getRandomAttackState(Entity entity) {
        StateHelper state =  WeightedRandomState.getRandomState(entity.level().random, getWeightedAttackStatesToPerform());
        if(state == null) {
            throw new RuntimeException("State is null");
        }
       return state;
    }

    default StateHelper getAttackStateForName(Entity entity, String name) {
        if(getAttackStates().containsKey(name)) {
            return getAttackStates().get(name);
        } else {
            Hole.LOGGER.error("Could not get state {} for {}", name, entity.getEncodeId());
            return null;
        }
    }

    default StateHelper getSpecialStateForName(Entity entity, String name) {
        if(getSpecialAttack().containsKey(name)) {
            return getSpecialAttack().get(name);
        } else {
            Hole.LOGGER.error("Could not get state {} for {}", name, entity.getEncodeId());
            return null;
        }
    }
}
