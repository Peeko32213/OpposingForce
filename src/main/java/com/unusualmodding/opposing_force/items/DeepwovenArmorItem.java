package com.unusualmodding.opposing_force.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

public class DeepwovenArmorItem extends ArmorItem {

    public DeepwovenArmorItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(super.getAttributeModifiers(slot, stack));
        UUID uuid = ArmorItem.ARMOR_MODIFIER_UUID_PER_TYPE.get(this.type);
        builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Movement speed", 0.05F, AttributeModifier.Operation.MULTIPLY_BASE));
        builder.put(OPAttributes.STEALTH.get(), new AttributeModifier(uuid, "Stealth", 0.15D, AttributeModifier.Operation.ADDITION));
        return slot == this.getEquipmentSlot() ? builder.build() : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return stack.is(OPItems.DEEPWOVEN_BOOTS.get());
    }

    @SubscribeEvent
    public static void hatEquippedEvent(LivingEquipmentChangeEvent event) {
        if (event.getTo().is(OPItems.DEEPWOVEN_HAT.get()) || event.getFrom().is(OPItems.DEEPWOVEN_HAT.get())) {
            if (event.getEntity() instanceof Player player) {
                player.refreshDisplayName();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) OpposingForce.PROXY.getArmorRenderProperties());
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (slot == EquipmentSlot.HEAD) {
            return OpposingForce.MOD_ID + ":textures/models/armor/deepwoven_layer_3.png";
        }
        if (slot == EquipmentSlot.LEGS) {
            return OpposingForce.MOD_ID + ":textures/models/armor/deepwoven_layer_2.png";
        } else {
            return OpposingForce.MOD_ID + ":textures/models/armor/deepwoven_layer_1.png";
        }
    }
}
