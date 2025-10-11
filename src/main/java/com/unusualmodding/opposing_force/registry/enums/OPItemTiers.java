package com.unusualmodding.opposing_force.registry.enums;

import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Supplier;

public enum OPItemTiers implements Tier {

    VILE(0, 196, 3.0F, 0.0F, 8, () -> Ingredient.of(OPItems.SLUG_EGGS.get())),
    EMERALD(3, 1561, 8.0F, 3.0F, 14, () -> Ingredient.of(Tags.Items.GEMS_EMERALD)),
    UMBER(2, 196, 6.0F, 2.0F, 10, () -> Ingredient.of(OPItems.UMBER_FANG.get()));;

    private final int level;
    private final int durability;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    OPItemTiers(int level, int durability, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient) {
        this.level = level;
        this.durability = durability;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    public int getUses() {
        return this.durability;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.damage;
    }

    public int getLevel() {
        return this.level;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
