package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.entity.Slug;
import com.unusualmodding.opposing_force.registry.OPEffects;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class VileBoulderItem extends SwordItem {
    public VileBoulderItem(Tier tier, int attackModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackModifier, attackSpeedModifier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(itemStack, target, attacker);

        if (result) {
            if (target.getRandom().nextBoolean() && !target.hasEffect(OPEffects.SLUG_INFESTATION.get()) && !(target instanceof Slug)) {
                target.addEffect(new MobEffectInstance(OPEffects.SLUG_INFESTATION.get(), 240, 0, false, true));
                target.playSound(OPSoundEvents.SLUG_ATTACK.get(), 1.5F, 1);

                for (int i = 0; i < 16; i++) {
                    ((ServerLevel) target.level()).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, OPItems.SLUG_EGGS.get().getDefaultInstance()), target.getX(), target.getY() + target.getBbHeight() * 0.5F, target.getZ(), 1, target.getBbWidth() * 0.5, target.getBbHeight() * 0.5, target.getBbWidth() * 0.5, 0);
                }
            }
        }
        return result;
    }
}
