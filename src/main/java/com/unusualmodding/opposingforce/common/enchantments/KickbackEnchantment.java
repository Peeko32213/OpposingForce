package com.unusualmodding.opposingforce.common.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class KickbackEnchantment extends Enchantment {

    public KickbackEnchantment(Enchantment.Rarity rarityIn, EquipmentSlot... slots) {
        super(rarityIn, EnchantmentCategory.CROSSBOW, slots);
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return 12 + (enchantmentLevel - 1) * 20;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return this.getMinCost(enchantmentLevel) + 25;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isTradeable() {
        return true;
    }

    @Override
    public boolean isDiscoverable() {
        return true;
    }

    public boolean checkCompatibility(Enchantment other) {
        return super.checkCompatibility(other);
    }
}
