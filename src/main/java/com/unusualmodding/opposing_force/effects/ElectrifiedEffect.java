package com.unusualmodding.opposing_force.effects;

import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ElectrifiedEffect extends MobEffect {

    public ElectrifiedEffect() {
        super(MobEffectCategory.HARMFUL, 0x0080c3);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.isInWaterRainOrBubble()) {
            entity.hurt(entity.damageSources().source(OPDamageTypes.ELECTRIFIED), 2.0F + (amplifier * 2));
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    public String getDescriptionId() {
        return "opposing_force.potion.electrified";
    }
}
