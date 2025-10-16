package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.entity.Slug;
import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.core.particles.BlockParticleOption;
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
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);

        if (result) {
            if (!target.hasEffect(OPEffects.SLUG_INFESTATION.get()) && !(target instanceof Slug)) {
                target.addEffect(new MobEffectInstance(OPEffects.SLUG_INFESTATION.get(), 400, stack.getEnchantmentLevel(OPEnchantments.PLAGUE.get()), false, true));
                target.playSound(OPSoundEvents.SLUG_ATTACK.get(), 1.5F, 1);

                for (int i = 0; i < 16; i++) {
                    ((ServerLevel) target.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, OPBlocks.SLUG_EGGS.get().defaultBlockState()), target.getX(), target.getY() + target.getBbHeight() * 0.5F, target.getZ(), 1, target.getBbWidth() * 0.5, target.getBbHeight() * 0.5, target.getBbWidth() * 0.5, 0);
                }
            }
        }
        return result;
    }
}
