package com.unusualmodding.opposing_force.effects;

import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPSounds;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ElectrifiedEffect extends MobEffect {

    public ElectrifiedEffect() {
        super(MobEffectCategory.HARMFUL, 0x0080c3);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.isInWaterRainOrBubble()) {
            entity.hurt(entity.damageSources().source(OPDamageTypes.ELECTRIFIED), 2.0F + (amplifier * 1.5F));
            if (entity.tickCount % 10 == 0 && entity.isAlive()) {
                entity.playSound(OPSounds.ELECTRIC_ZAP.get(), 1.0F, 1.0F / (entity.getRandom().nextFloat() * 0.4F + 0.8F));
            }
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
