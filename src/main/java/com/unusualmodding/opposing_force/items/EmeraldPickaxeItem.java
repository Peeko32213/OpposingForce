package com.unusualmodding.opposing_force.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.unusualmodding.opposing_force.items.interfaces.EmeraldTool;
import com.unusualmodding.opposing_force.registry.enums.OPItemTiers;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.PickaxeItem;
import org.jetbrains.annotations.NotNull;

public class EmeraldPickaxeItem extends PickaxeItem implements EmeraldTool {

    private Multimap<Attribute, AttributeModifier> attributes;

    public EmeraldPickaxeItem(Properties properties) {
        super(OPItemTiers.EMERALD, 1, -2.8F, properties);
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
}
