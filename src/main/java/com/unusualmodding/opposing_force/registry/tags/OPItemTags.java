package com.unusualmodding.opposing_force.registry.tags;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class OPItemTags {

    public static final TagKey<Item> BLASTER_AMMO = registerItemTag("blaster_ammo");

    public static final TagKey<Item> RAW_VEGETABLES = registerItemTag("raw_vegetables");

    private static TagKey<Item> registerItemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(OpposingForce.MOD_ID, name));
    }

    private static TagKey<Item> registerExternalItemTag(String modId, String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(modId, name));
    }
}
