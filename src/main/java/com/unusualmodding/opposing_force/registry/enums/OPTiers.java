package com.unusualmodding.opposing_force.registry.enums;

import com.unusualmodding.opposing_force.registry.OPBlocks;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

public class OPTiers {

    public static class OPArmorMaterials {
        public static final ArmorMaterial DEEPWOVEN = new OPArmorMaterial("deepwoven", 12, new int[] {2, 5, 4, 1}, 12, OPSoundEvents.ARMOR_EQUIP_DEEPWOVEN.get(), 0F, 0F, ()-> Ingredient.of(OPItems.DEEP_SILK.get()));
        public static final ArmorMaterial WOODEN = new OPArmorMaterial("wooden", 5, new int[] {1, 3, 2, 1}, 9, OPSoundEvents.ARMOR_EQUIP_WOODEN.get(), 0F, 0F, ()-> Ingredient.of(ItemTags.PLANKS));
        public static final ArmorMaterial EMERALD = new OPArmorMaterial("emerald", 33, new int[] {3, 8, 6, 3}, 14, OPSoundEvents.ARMOR_EQUIP_EMERALD.get(), 2F, 0F, ()-> Ingredient.of(Tags.Items.GEMS_EMERALD));
        public static final ArmorMaterial STONE = new OPArmorMaterial("stone", 7, new int[] {2, 5, 4, 1}, 5, OPSoundEvents.ARMOR_EQUIP_STONE.get(), 0F, 0.15F, ()-> Ingredient.of(Tags.Items.STONE));
        public static final ArmorMaterial MOON_SHOES = new OPArmorMaterial("moon_shoes", 13, new int[] {1, 1, 1, 1}, 8, OPSoundEvents.ARMOR_EQUIP_MOON_SHOES.get(), 0F, 0F, ()-> Ingredient.of(Tags.Items.FEATHERS));
    }

    public static class OPItemTiers {
        public static final Tier VILE = new OPItemTier(0, 224, 3.0F, 0.0F, 8, () -> Ingredient.of(OPBlocks.SLUG_EGGS.get()));
        public static final Tier EMERALD = new OPItemTier(3, 1561, 8.0F, 3.0F, 14, () -> Ingredient.of(Tags.Items.GEMS_EMERALD));
        public static final Tier UMBER = new OPItemTier(2, 196, 6.0F, 1.0F, 10, () -> Ingredient.of(OPItems.UMBER_FANG.get()));
        public static final Tier ELECTRIC = new OPItemTier(2, 624, 10.0F, 2.0F, 8, () -> Ingredient.of(OPItems.ELECTRIC_CHARGE.get()));
        public static final Tier MOUNTAIN = new OPItemTier(3, 1024, 8.0F, 4.0F, 5, () -> Ingredient.of(Items.DIAMOND));
        public static final Tier LASER = new OPItemTier(3, 1024, 8.0F, 3.0F, 9, () -> Ingredient.of(OPItems.DICER_LENS.get()));
    }
}
