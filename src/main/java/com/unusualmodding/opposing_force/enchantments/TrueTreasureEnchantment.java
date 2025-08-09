package com.unusualmodding.opposing_force.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class TrueTreasureEnchantment extends OPEnchantment {

    public TrueTreasureEnchantment(String name, Rarity rarity, EnchantmentCategory category, int levels, int minXP, EquipmentSlot... equipmentSlot) {
        super(name, rarity, category, levels, minXP, equipmentSlot);
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }
}
