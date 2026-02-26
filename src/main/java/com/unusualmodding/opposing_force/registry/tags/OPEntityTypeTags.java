package com.unusualmodding.opposing_force.registry.tags;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class OPEntityTypeTags {

    public static final TagKey<EntityType<?>> HANGING_SPIDER_TARGETS = modEntityTypeTag("hanging_spider_targets");
    public static final TagKey<EntityType<?>> NO_LEAF_COLLISIONS = modEntityTypeTag("no_leaf_collisions");

    private static TagKey<EntityType<?>> modEntityTypeTag(String name) {
        return entityTypeTag(OpposingForce.MOD_ID, name);
    }

    private static TagKey<EntityType<?>> forgeEntityTypeTag(String name) {
        return entityTypeTag("forge", name);
    }

    private static TagKey<EntityType<?>> entityTypeTag(String modId, String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(modId, name));
    }
}
