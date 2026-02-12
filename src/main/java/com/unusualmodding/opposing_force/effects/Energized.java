package com.unusualmodding.opposing_force.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Energized extends MobEffect {

    private static final String SPEED_UUID = "fb240011-15fd-4b14-90b1-03f504ed4b1e";
    private static final String DAMAGE_UUID = "66ca87e5-eaa9-426f-9bc3-518d35f352ea";

    public Energized() {
        super(MobEffectCategory.BENEFICIAL, 0x3a97b7);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, SPEED_UUID, 0.0F, AttributeModifier.Operation.MULTIPLY_BASE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, DAMAGE_UUID, 0.0F, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        if (modifier.getId().equals(UUID.fromString(DAMAGE_UUID))) return (amplifier + 1) * 0.5F;
        return (amplifier + 1) * 0.1F;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        this.addAttributeModifiers(entity, entity.getAttributes(), amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
