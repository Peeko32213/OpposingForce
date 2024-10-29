package com.peeko32213.hole.common.effect;

import com.peeko32213.hole.core.registry.HoleDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class EffectElectrified extends MobEffect {

    public EffectElectrified() {
        super(MobEffectCategory.HARMFUL, 0X0080c3);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {

        if(entity.isInWaterOrBubble()){
            entity.hurt(entity.damageSources().magic(), 10.0F);
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    public String getDescriptionId() {
        return "hole.potion.electrified";
    }
}
