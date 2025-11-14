package com.unusualmodding.opposing_force.items.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class BoneSwordItem extends ConfigurableSwordItem {

    private static final double MIN_ATTACK_DAMAGE = 4.0D;
    private static final double MAX_ATTACK_DAMAGE = 8.0D;

    private final Map<Integer, Multimap<Attribute, AttributeModifier>> durabilityDependentAttributes = new HashMap<>();

    public BoneSwordItem(Properties properties) {
        super(OPToolDefinitions.BONE, 0, 0, properties);
    }

    private Multimap<Attribute, AttributeModifier> getOrCreateDurabilityAttributes(int durability, int maxDurability) {
        if (durabilityDependentAttributes.containsKey(durability)) {
            return durabilityDependentAttributes.get(durability);
        } else {
            float scaledDurability = durability / (float) maxDurability;
            double attackDamage = MIN_ATTACK_DAMAGE + (MAX_ATTACK_DAMAGE - MIN_ATTACK_DAMAGE) * scaledDurability;
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Attack Damage", attackDamage, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Attack Speed", -2.4F, AttributeModifier.Operation.ADDITION));
            Multimap<Attribute, AttributeModifier> attributeModifierMultimap = builder.build();
            durabilityDependentAttributes.put(durability, attributeModifierMultimap);
            return attributeModifierMultimap;
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? getOrCreateDurabilityAttributes(stack.getDamageValue(), stack.getMaxDamage()) : super.getAttributeModifiers(slot, stack);
    }
}
