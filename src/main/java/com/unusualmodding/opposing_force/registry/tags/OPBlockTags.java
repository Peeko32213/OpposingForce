package com.unusualmodding.opposing_force.registry.tags;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class OPBlockTags {

    public static final TagKey<Block> CAVE_MOB_SPAWNABLE_ON = modBlockTag("cave_mob_spawnable_on");
    public static final TagKey<Block> FROWZY_SPAWNABLE_ON = modBlockTag("frowzy_spawnable_on");
    public static final TagKey<Block> UMBER_SPIDER_SPAWNABLE_ON = modBlockTag("umber_spider_spawnable_on");

    private static TagKey<Block> modBlockTag(String name) {
        return blockTag(OpposingForce.MOD_ID, name);
    }

    private static TagKey<Block> forgeBlockTag(String name) {
        return blockTag("forge", name);
    }

    public static TagKey<Block> blockTag(String modid, String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(modid, name));
    }
}
