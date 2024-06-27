package com.peeko32213.hole.core.utils;

import com.peeko32213.hole.Hole;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class HoleTags {

    public static final TagKey<EntityType<?>> PALE_SPIDER = registerEntityTag("pale_spider");
    public static final TagKey<EntityType<?>> UMBER_SPIDER = registerEntityTag("umber_spider");


    private static TagKey<EntityType<?>> registerEntityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(Hole.MODID, name));
    }
}
