package com.barl_inc.opposing_force.registry.tags;

import com.barl_inc.opposing_force.OpposingForce;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class OPItemTags {

    public static final TagKey<Item> BLASTER_AMMO = modItemTag("blaster_ammo");
    public static final TagKey<Item> RAW_VEGETABLES = modItemTag("raw_vegetables");
    public static final TagKey<Item> KNIVES = modItemTag("knives");
    public static final TagKey<Item> RAMBLER_SKULLS = modItemTag("rambler_skulls");
    public static final TagKey<Item> PREVENT_CAPE_RENDERING = modItemTag("prevent_cape_rendering");
    public static final TagKey<Item> LASER_BLADES = modItemTag("laser_blades");
    public static final TagKey<Item> BLASTERS = modItemTag("blasters");

    public static final TagKey<Item> BERRIES = forgeItemTag("berries");
    public static final TagKey<Item> FRUITS = forgeItemTag("fruits");
    public static final TagKey<Item> VEGETABLES = forgeItemTag("vegetables");

    private static TagKey<Item> modItemTag(String name) {
        return itemTag(OpposingForce.MOD_ID, name);
    }

    private static TagKey<Item> forgeItemTag(String name) {
        return itemTag("forge", name);
    }

    private static TagKey<Item> itemTag(String modId, String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(modId, name));
    }
}
