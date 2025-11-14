package com.unusualmodding.opposing_force.items.tools;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Tier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToolDefinition {

    private final Tier tier;
    private final List<AttributeEntry> attributes;

    public record AttributeEntry(Attribute attribute, double value, AttributeModifier.Operation operation) {}

    private ToolDefinition(Tier tier, List<AttributeEntry> attributes) {
        this.tier = tier;
        this.attributes = attributes;
    }

    public Tier tier() {
        return tier;
    }

    public List<AttributeEntry> attributes() {
        return attributes;
    }

    public static class Builder {

        private Tier tier;
        private final List<AttributeEntry> attributes = new ArrayList<>();

        public Builder tier(Tier tier) {
            this.tier = tier;
            return this;
        }

        public Builder attribute(Attribute attribute, double value, AttributeModifier.Operation operation) {
            attributes.add(new AttributeEntry(attribute, value, operation));
            return this;
        }

        public ToolDefinition build() {
            Objects.requireNonNull(tier, "Tool tier must not be null");
            return new ToolDefinition(tier, List.copyOf(attributes));
        }
    }
}
