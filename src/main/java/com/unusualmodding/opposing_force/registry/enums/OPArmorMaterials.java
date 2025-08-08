package com.unusualmodding.opposing_force.registry.enums;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Supplier;

public enum OPArmorMaterials implements ArmorMaterial {

    DEEPWOVEN ("deepwoven", 12, new int[] {2, 4, 3, 1}, 12, OPSoundEvents.ARMOR_EQUIP_DEEPWOVEN.get(), 0F, 0F, ()-> Ingredient.of(OPItems.DEEP_SILK.get())),
    WOODEN ("wooden", 6, new int[] {1, 3, 2, 1}, 9, OPSoundEvents.ARMOR_EQUIP_WOODEN.get(), 0F, 0F, ()-> Ingredient.of(ItemTags.PLANKS)),
    EMERALD ("emerald", 28, new int[] {3, 8, 6, 3}, 20, OPSoundEvents.ARMOR_EQUIP_EMERALD.get(), 0F, 0F, ()-> Ingredient.of(Tags.Items.GEMS_EMERALD)),
    STONE ("stone", 8, new int[] {2, 6, 5, 2}, 5, OPSoundEvents.ARMOR_EQUIP_STONE.get(), 2.0F, 0.15F, ()-> Ingredient.of(Tags.Items.STONE)),
    CLOUD_BOOTS ("cloud_boots", 10, new int[] {0, 0, 0, 0}, 0, OPSoundEvents.ARMOR_EQUIP_COUD_BOOTS.get(), 0F, 0F, ()-> Ingredient.of(Tags.Items.FEATHERS));

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
