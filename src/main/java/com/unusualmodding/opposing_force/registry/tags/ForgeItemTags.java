package com.unusualmodding.opposing_force.registry.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ForgeItemTags {

    public static final TagKey<Item> BERRIES = registerForgeItemTag("berries");
    public static final TagKey<Item> FRUITS = registerForgeItemTag("fruits");
    public static final TagKey<Item> VEGETABLES = registerForgeItemTag("vegetables");

    private static TagKey<Item> registerForgeItemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("forge", name));
    }
}
