package com.unusualmodding.opposing_force.registry.tags;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class OPBlockTags {

    public static final TagKey<Block> HOLE_MUSHROOM_BLOCKS = registerBlockTag("hole_mushroom_blocks");

    private static TagKey<Block> registerBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(OpposingForce.MOD_ID, name));
    }
}
