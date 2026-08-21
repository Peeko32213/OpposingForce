package com.barl_inc.opposing_force.registry;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.grower.TreeGrower;
import org.slf4j.Logger;

import java.util.Optional;

public class OPTreeGrowers {

    public static final Logger LOGGER = LogUtils.getLogger();
    //TODO make extended treegrower with more chances
    public static final TreeGrower APPLE_TREE =  new TreeGrower("apple_tree", 0.5, Optional.of(OPTreeFeatures.FANCY_APPLE_TREE_BEES),Optional.empty(), Optional.empty());


    public static void INIT() {
        LOGGER.info("Loaded Opposing Force Treegrowers");
    }



}
