package com.unusualmodding.opposing_force.blocks.trees;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AppleTreeGrower extends AbstractTreeGrower {

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean beehive) {
        if (random.nextInt(10) == 0) {
            if (random.nextInt(10) == 0) {
                return beehive ? ResourceKey.create(Registries.CONFIGURED_FEATURE, OpposingForce.modPrefix("fancy_infested_apple_tree_bees")) : ResourceKey.create(Registries.CONFIGURED_FEATURE, OpposingForce.modPrefix("fancy_infested_apple_tree"));
            } else {
                return beehive ? ResourceKey.create(Registries.CONFIGURED_FEATURE, OpposingForce.modPrefix("infested_apple_tree_bees")) : ResourceKey.create(Registries.CONFIGURED_FEATURE, OpposingForce.modPrefix("infested_apple_tree"));
            }
        } else {
            if (random.nextInt(10) == 0) {
                return beehive ? ResourceKey.create(Registries.CONFIGURED_FEATURE, OpposingForce.modPrefix("fancy_apple_tree_bees")) : ResourceKey.create(Registries.CONFIGURED_FEATURE, OpposingForce.modPrefix("fancy_apple_tree"));
            } else {
                return beehive ? ResourceKey.create(Registries.CONFIGURED_FEATURE, OpposingForce.modPrefix("apple_tree_bees")) : ResourceKey.create(Registries.CONFIGURED_FEATURE, OpposingForce.modPrefix("apple_tree"));
            }
        }
    }
}
