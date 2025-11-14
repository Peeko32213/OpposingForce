package com.unusualmodding.opposing_force.items.tools;

import com.unusualmodding.opposing_force.registry.OPAttributes;
import com.unusualmodding.opposing_force.registry.enums.OPTiers;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class OPToolDefinitions {

    public static final ToolDefinition BONE = new ToolDefinition.Builder()
            .tier(OPTiers.OPItemTiers.BONE)
            .build();

    public static final ToolDefinition EMERALD = new ToolDefinition.Builder()
            .tier(OPTiers.OPItemTiers.EMERALD)
            .attribute(OPAttributes.EXPERIENCE_GAIN.get(), 0.5F, AttributeModifier.Operation.MULTIPLY_BASE)
            .build();
}
