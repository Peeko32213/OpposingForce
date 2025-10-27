package com.unusualmodding.opposing_force.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import com.unusualmodding.opposing_force.registry.enums.OPItemTiers;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.SwordItem;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class EmeraldSwordItem extends SwordItem {

    private Multimap<Attribute, AttributeModifier> attributes;

    public EmeraldSwordItem(Properties properties) {
        super(OPItemTiers.EMERALD, 3, -2.4F, properties);
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        if (attributes == null) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> attributeBuilder = new ImmutableMultimap.Builder<>();
            attributeBuilder.putAll(defaultModifiers);
            attributeBuilder.putAll(createExtraAttributes().build());
            attributes = attributeBuilder.build();
        }
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.attributes : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    private ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(OPAttributes.EXPERIENCE_GAIN.get(), new AttributeModifier(UUID.fromString("1e0f1128-0ee5-4b45-a1c4-12b4da5b1144"), "Increased experience gain", 0.5F, AttributeModifier.Operation.MULTIPLY_BASE));
        return builder;
    }
}
