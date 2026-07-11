package com.barl_inc.opposing_force.effects;

import com.barl_inc.opposing_force.registry.OPDamageTypes;
import com.barl_inc.opposing_force.registry.OPSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class Electrified extends MobEffect {

    public Electrified() {
        super(MobEffectCategory.HARMFUL, 0x0080c3);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.isInWaterRainOrBubble()) {
            if (entity.hurt(entity.damageSources().source(OPDamageTypes.ELECTRIFIED), 2.0F)) {
                entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (entity.getRandom().nextFloat() * 0.4F + 0.8F));
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int j = 20 >> amplifier;
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }
}
