package com.unusualmodding.opposing_force.effects;

import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;

public class GloomToxin extends MobEffect {

    public GloomToxin() {
        super(MobEffectCategory.HARMFUL, 0x6349af);
    }

    public void applyEffectTick(LivingEntity entity, int pAmplifier) {
        if (entity.level().getBrightness(LightLayer.BLOCK, entity.blockPosition()) <= 7 && entity.level().getBrightness(LightLayer.SKY, entity.blockPosition()) <= 7) {
            entity.hurt(entity.damageSources().source(OPDamageTypes.GLOOM_TOXIN), 1.0F);
        }
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        int i = 25 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}
