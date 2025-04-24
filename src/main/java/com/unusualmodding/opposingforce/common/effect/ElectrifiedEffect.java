package com.unusualmodding.opposingforce.common.effect;

import com.unusualmodding.opposingforce.core.registry.OPDamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ElectrifiedEffect extends MobEffect {

    public ElectrifiedEffect() {
        super(MobEffectCategory.HARMFUL, 0X0080c3);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.isInWaterRainOrBubble()){
            entity.hurt(entity.damageSources().source(OPDamageTypes.ELECTRIFIED), 3.0F + (amplifier * 2));
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    public String getDescriptionId() {
        return "hole.potion.electrified";
    }
}
