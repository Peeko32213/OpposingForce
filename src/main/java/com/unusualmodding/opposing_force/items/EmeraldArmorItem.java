package com.unusualmodding.opposing_force.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Consumer;

public class EmeraldArmorItem extends ArmorItem {

    public EmeraldArmorItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(super.getAttributeModifiers(slot, stack));
        UUID uuid = ArmorItem.ARMOR_MODIFIER_UUID_PER_TYPE.get(this.type);
        builder.put(OPAttributes.EXPERIENCE_GAIN.get(), new AttributeModifier(uuid, "Experience gain", 0.25F, AttributeModifier.Operation.MULTIPLY_BASE));
        return slot == this.getEquipmentSlot() ? builder.build() : super.getAttributeModifiers(slot, stack);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) OpposingForce.PROXY.getArmorRenderProperties());
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (slot.equals(EquipmentSlot.LEGS)) return OpposingForce.MOD_ID + ":textures/models/armor/emerald_armor_layer_2.png";
        else return OpposingForce.MOD_ID + ":textures/models/armor/emerald_armor_layer_1.png";
    }
}
