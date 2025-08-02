package com.unusualmodding.opposing_force.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

public class StoneArmorItem extends ArmorItem {

    public StoneArmorItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(super.getAttributeModifiers(slot, stack));
        UUID uuid = ArmorItem.ARMOR_MODIFIER_UUID_PER_TYPE.get(this.type);
        builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Movement speed", -0.10F, AttributeModifier.Operation.MULTIPLY_BASE));

        return slot == this.getEquipmentSlot() ? builder.build() : super.getAttributeModifiers(slot, stack);
    }


    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) OpposingForce.PROXY.getArmorRenderProperties());
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (slot == EquipmentSlot.HEAD) {
            return OpposingForce.MOD_ID + ":textures/models/armor/stone_layer_3.png";
        }
        if (slot == EquipmentSlot.LEGS) {
            return OpposingForce.MOD_ID + ":textures/models/armor/stone_layer_1.png";
        }
        if (slot == EquipmentSlot.FEET) {
            return OpposingForce.MOD_ID + ":textures/models/armor/stone_layer_4.png";
        }
        else {
            return OpposingForce.MOD_ID + ":textures/models/armor/stone_layer_2.png";
        }
    }
}
