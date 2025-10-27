package com.unusualmodding.opposing_force.enchantments;

import com.unusualmodding.opposing_force.registry.OPEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class OPEnchantment extends Enchantment {

    private final int levels;
    private final int minXP;
    private final String registryName;
    private final boolean isTradeable;
    private final boolean isDiscoverable;
    private final boolean isAllowedOnBooks;

    public OPEnchantment(String name, Rarity rarity, EnchantmentCategory category, int levels, int minXP, boolean isTradeable, boolean isDiscoverable, boolean isAllowedOnBooks, EquipmentSlot... equipmentSlot) {
        super(rarity, category, equipmentSlot);
        this.levels = levels;
        this.minXP = minXP;
        this.registryName = name;
        this.isTradeable = isTradeable;
        this.isDiscoverable = isDiscoverable;
        this.isAllowedOnBooks = isAllowedOnBooks;
    }

    public OPEnchantment(String name, Rarity rarity, EnchantmentCategory category, int levels, int minXP, EquipmentSlot... equipmentSlot) {
        this(name, rarity, category, levels, minXP, true, true, true, equipmentSlot);
    }

    @Override
    public int getMinCost(int i) {
        return 1 + (i - 1) * minXP;
    }

    @Override
    public int getMaxCost(int i) {
        return super.getMinCost(i) + 30;
    }

    @Override
    public int getMaxLevel() {
        return levels;
    }

    @Override
    protected boolean checkCompatibility(@NotNull Enchantment enchantment) {
        return this != enchantment && OPEnchantments.areCompatible(this, enchantment);
    }

    @Override
    public boolean isTradeable() {
        return isTradeable;
    }

    @Override
    public boolean isDiscoverable() {
        return isDiscoverable;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return isAllowedOnBooks;
    }

    public String getName(){
        return registryName;
    }
}
