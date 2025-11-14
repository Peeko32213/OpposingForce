package com.unusualmodding.opposing_force.items.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.AxeItem;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ConfigurableAxeItem extends AxeItem {

    protected final UUID TOOL_MODIFIER_UUID = UUID.fromString("c5d7e1bc-f006-4793-b517-6475b5586191");
    protected final ToolDefinition definition;
    private Multimap<Attribute, AttributeModifier> attributes;

    public ConfigurableAxeItem(ToolDefinition toolDefinition, float attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(toolDefinition.tier(), attackDamageModifier, attackSpeedModifier, properties);
        this.definition = toolDefinition;
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
        for (var entry : definition.attributes()) {
            builder.put(entry.attribute(), new AttributeModifier(TOOL_MODIFIER_UUID, entry.attribute().getDescriptionId(), entry.value(), entry.operation()));
        }
        return builder;
    }
}
