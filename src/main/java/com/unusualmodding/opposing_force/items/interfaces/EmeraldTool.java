package com.unusualmodding.opposing_force.items.interfaces;

import com.google.common.collect.ImmutableMultimap;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public interface EmeraldTool {

    default ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(OPAttributes.EXPERIENCE_GAIN.get(), new AttributeModifier(UUID.fromString("1e0f1128-0ee5-4b45-a1c4-12b4da5b1144"), "Increased experience gain", 0.5F, AttributeModifier.Operation.MULTIPLY_BASE));
        return builder;
    }
}
