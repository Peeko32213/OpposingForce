package com.barl_inc.opposing_force.items.armor;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.registry.OPAttributes;
import com.barl_inc.opposing_force.registry.enums.OPTiers;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

public class OPArmorDefinitions {

    public static final ArmorDefinition BONE = new ArmorDefinition.Builder()
            .material(OPTiers.OPArmorMaterials.BONE)
            .texture(slot -> slot == EquipmentSlot.LEGS ? OpposingForce.MOD_ID + ":textures/models/armor/bone_armor_layer_2.png" : OpposingForce.MOD_ID + ":textures/models/armor/bone_armor_layer_1.png")
            .build();

    public static final ArmorDefinition DEEPWOVEN = new ArmorDefinition.Builder()
            .material(OPTiers.OPArmorMaterials.DEEPWOVEN)
            .attribute(Attributes.MOVEMENT_SPEED, 0.05F, AttributeModifier.Operation.MULTIPLY_BASE)
            .attribute(OPAttributes.STEALTH.get(), 0.15F, AttributeModifier.Operation.ADDITION)
            .texture(slot -> slot == EquipmentSlot.LEGS ? OpposingForce.MOD_ID + ":textures/models/armor/deepwoven_armor_layer_2.png" : OpposingForce.MOD_ID + ":textures/models/armor/deepwoven_armor_layer_1.png")
            .walkOnPowderedSnow()
            .build();

    public static final ArmorDefinition EMERALD = new ArmorDefinition.Builder()
            .material(OPTiers.OPArmorMaterials.EMERALD)
            .attribute(OPAttributes.EXPERIENCE_GAIN.get(), 0.25F, AttributeModifier.Operation.MULTIPLY_BASE)
            .texture(slot -> slot == EquipmentSlot.LEGS ? OpposingForce.MOD_ID + ":textures/models/armor/emerald_armor_layer_2.png" : OpposingForce.MOD_ID + ":textures/models/armor/emerald_armor_layer_1.png")
            .build();

    public static final ArmorDefinition MOON_SHOES = new ArmorDefinition.Builder()
            .material(OPTiers.OPArmorMaterials.MOON_SHOES)
            .attribute(Attributes.MOVEMENT_SPEED, 0.15F, AttributeModifier.Operation.MULTIPLY_BASE)
            .attribute(OPAttributes.AIR_SPEED.get(), 0.15F, AttributeModifier.Operation.MULTIPLY_BASE)
            .attribute(OPAttributes.JUMP_POWER.get(), 2.0F, AttributeModifier.Operation.ADDITION)
            .attribute(ForgeMod.STEP_HEIGHT_ADDITION.get(), 0.5F, AttributeModifier.Operation.ADDITION)
            .attribute(ForgeMod.ENTITY_GRAVITY.get(), -0.3F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .texture(slot -> OpposingForce.MOD_ID + ":textures/models/armor/moon_shoes_layer_1.png")
            .build();

    public static final ArmorDefinition RECON_KNIGHT = new ArmorDefinition.Builder()
            .material(OPTiers.OPArmorMaterials.RECON_KNIGHT)
            .attribute(Attributes.MOVEMENT_SPEED, 0.1F, AttributeModifier.Operation.MULTIPLY_BASE)
//            .attribute(OPAttributes.RANGED_DAMAGE.get(), 0.5F, AttributeModifier.Operation.ADDITION)
            .texture(slot -> slot == EquipmentSlot.LEGS ? OpposingForce.MOD_ID + ":textures/models/armor/recon_knight_armor_layer_2.png" : OpposingForce.MOD_ID + ":textures/models/armor/recon_knight_armor_layer_1.png")
            .build();

    public static final ArmorDefinition SLUG_BARON = new ArmorDefinition.Builder()
            .material(OPTiers.OPArmorMaterials.SLUG_BARON)
            .attribute(OPAttributes.SUMMON_DAMAGE.get(), 0.15F, AttributeModifier.Operation.MULTIPLY_BASE)
            .attribute(OPAttributes.SUMMON_DURATION.get(), 0.1F, AttributeModifier.Operation.MULTIPLY_BASE)
            .texture(slot -> slot == EquipmentSlot.LEGS ? OpposingForce.MOD_ID + ":textures/models/armor/slug_baron_armor_layer_2.png" : OpposingForce.MOD_ID + ":textures/models/armor/slug_baron_armor_layer_1.png")
            .build();

    public static final ArmorDefinition STONE = new ArmorDefinition.Builder()
            .material(OPTiers.OPArmorMaterials.STONE)
            .attribute(ForgeMod.ENTITY_GRAVITY.get(), 0.05F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .attribute(Attributes.MOVEMENT_SPEED, -0.1F, AttributeModifier.Operation.MULTIPLY_BASE)
            .texture(slot -> slot == EquipmentSlot.LEGS ? OpposingForce.MOD_ID + ":textures/models/armor/stone_armor_layer_2.png" : OpposingForce.MOD_ID + ":textures/models/armor/stone_armor_layer_1.png")
            .build();

    public static final ArmorDefinition WOODEN = new ArmorDefinition.Builder()
            .material(OPTiers.OPArmorMaterials.WOODEN)
            .texture(slot -> slot == EquipmentSlot.LEGS ? OpposingForce.MOD_ID + ":textures/models/armor/wooden_armor_layer_2.png" : OpposingForce.MOD_ID + ":textures/models/armor/wooden_armor_layer_1.png")
            .build();
}
