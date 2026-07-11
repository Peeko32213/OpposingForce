package com.barl_inc.opposing_force.items.tools;

import com.barl_inc.opposing_force.registry.OPAttributes;
import com.barl_inc.opposing_force.registry.enums.OPTiers;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class OPToolDefinitions {

    public static final ToolDefinition BONE = new ToolDefinition.Builder()
            .tier(OPTiers.OPItemTiers.BONE)
            .build();

    public static final ToolDefinition SAWBLADE = new ToolDefinition.Builder()
            .tier(OPTiers.OPItemTiers.SAWBLADE)
            .build();

    public static final ToolDefinition TREMBLING = new ToolDefinition.Builder()
            .tier(OPTiers.OPItemTiers.TREMBLING)
            .attribute(Attributes.ATTACK_KNOCKBACK, 2.0F, AttributeModifier.Operation.ADDITION)
            .build();

    public static final ToolDefinition EMERALD = new ToolDefinition.Builder()
            .tier(OPTiers.OPItemTiers.EMERALD)
            .attribute(OPAttributes.EXPERIENCE_GAIN.get(), 0.5F, AttributeModifier.Operation.MULTIPLY_BASE)
            .build();

    public static final ToolDefinition LAPIS = new ToolDefinition.Builder()
            .tier(OPTiers.OPItemTiers.LAPIS)
            .build();
}
