package com.barl_inc.opposing_force.items.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorMaterial;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ArmorDefinition {

    private final ArmorMaterial material;
    private final List<AttributeEntry> attributes;
    private final Function<EquipmentSlot, String> textureFunction;
    private boolean walkOnPowderedSnow = false;

    public record AttributeEntry(Attribute attribute, double value, AttributeModifier.Operation operation) {}

    private ArmorDefinition(ArmorMaterial material, List<AttributeEntry> attributes, Function<EquipmentSlot, String> textureFunction, boolean walkOnPowderedSnow) {
        this.material = material;
        this.attributes = attributes;
        this.textureFunction = textureFunction;
        this.walkOnPowderedSnow = walkOnPowderedSnow;
    }

    public ArmorMaterial material() { return material; }
    public List<AttributeEntry> attributes() { return attributes; }
    public Function<EquipmentSlot, String> textureFunction() { return textureFunction; }
    public boolean canWalkOnPowderedSnow() { return walkOnPowderedSnow; }

    public static class Builder {
        private ArmorMaterial material;
        private final List<AttributeEntry> attributes = new ArrayList<>();
        private Function<EquipmentSlot, String> textureFunction = s -> null;
        private boolean walkOnPowderedSnow = false;

        public Builder material(ArmorMaterial material) {
            this.material = material;
            return this;
        }

        public Builder attribute(Attribute attribute, double value, AttributeModifier.Operation op) {
            attributes.add(new AttributeEntry(attribute, value, op));
            return this;
        }

        public Builder texture(Function<EquipmentSlot, String> func) {
            textureFunction = func;
            return this;
        }

        public Builder walkOnPowderedSnow() {
            walkOnPowderedSnow = true;
            return this;
        }

        public ArmorDefinition build() {
            Objects.requireNonNull(material, "Armor material must not be null");
            return new ArmorDefinition(material, List.copyOf(attributes), textureFunction, walkOnPowderedSnow);
        }
    }
}
