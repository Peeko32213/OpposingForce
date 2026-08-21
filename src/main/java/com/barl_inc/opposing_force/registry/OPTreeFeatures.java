package com.barl_inc.opposing_force.registry;

import com.mojang.logging.LogUtils;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.slf4j.Logger;

public class OPTreeFeatures {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_INFESTED_APPLE_TREE_BEES = FeatureUtils.createKey("fancy_infested_apple_tree_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> INFESTED_APPLE_TREE_BEES = FeatureUtils.createKey("infested_apple_tree_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_APPLE_TREE_BEES = FeatureUtils.createKey("fancy_apple_tree_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> APPLE_TREE_BEES = FeatureUtils.createKey("apple_tree_bees");


    public static void INIT() {
        LOGGER.info("Loaded Opposing Force Tree Features");
    }
}
