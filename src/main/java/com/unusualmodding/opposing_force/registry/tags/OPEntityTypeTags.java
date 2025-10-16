package com.unusualmodding.opposing_force.registry.tags;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class OPEntityTypeTags {

    public static final TagKey<EntityType<?>> HANGING_SPIDER_TARGETS = registerEntityTag("hanging_spider_targets");

    private static TagKey<EntityType<?>> registerEntityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(OpposingForce.MOD_ID, name));
    }
}
