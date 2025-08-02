package com.unusualmodding.opposing_force.registry.enums;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Supplier;

public enum OPArmorMaterials implements ArmorMaterial {

    DEEPWOVEN ("deepwoven", 12, new int[] {2, 4, 3, 1}, 9, OPSoundEvents.ARMOR_EQUIP_DEEPWOVEN.get(), 0f, 0f, ()-> Ingredient.of(Tags.Items.LEATHER)),

    WOODEN ("wooden", 6, new int[] {1, 2, 2, 1}, 9, OPSoundEvents.ARMOR_EQUIP_WOODEN.get(), 0f, 0f, ()-> Ingredient.of(Tags.Items.LEATHER)),

    EMERALD ("emerald", 33, new int[] {3, 6, 8, 3}, 15, OPSoundEvents.ARMOR_EQUIP_EMERALD.get(), 0f, 0f, ()-> Ingredient.of(Tags.Items.GEMS_EMERALD)),

    STONE ("stone", 33, new int[] {3, 7, 6, 2}, 5, OPSoundEvents.ARMOR_EQUIP_STONE.get(), 2.0f, 0.15f, ()-> Ingredient.of(Tags.Items.STONE))
    ;

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int [] BASE_DURABILITY = {
            11, 16, 16, 13
    };

    OPArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionAmounts[pType.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return OpposingForce.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
