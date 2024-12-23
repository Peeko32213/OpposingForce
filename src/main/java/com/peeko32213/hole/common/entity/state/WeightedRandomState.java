package com.peeko32213.hole.common.entity.state;

import net.minecraft.util.RandomSource;

import java.util.List;

public class WeightedRandomState {
    public static<T extends WeightedState> T getRandomItem(RandomSource randomSource, List<WeightedState<T>> weightedStates) {
        int totalWeight = 0;
        for (WeightedState<T> instance : weightedStates) {
            totalWeight += instance.getWeight();
        }

        int randomIndex = randomSource.nextInt(totalWeight);
        int sumWeight = 0;

        for (WeightedState<T> weightedItem : weightedStates) {
            sumWeight += weightedItem.getWeight();
            if (randomIndex < sumWeight) {
                return weightedItem.getItem();
            }
        }

        // This should not happen unless the list is empty
        return null;
    }

    public static StateHelper getRandomState(RandomSource randomSource, List<WeightedState<StateHelper>> weightedStates) {
        int totalWeight = weightedStates.stream().mapToInt(WeightedState::getWeight).sum();
        int randomIndex = randomSource.nextInt(totalWeight);
        int sumWeight = 0;

        for (WeightedState<StateHelper> weightedItem : weightedStates) {
            sumWeight += weightedItem.getWeight();
            if (randomIndex < sumWeight) {
                return weightedItem.getItem();
            }
        }

        // This should not happen unless the list is empty
        return null;
    }
}
