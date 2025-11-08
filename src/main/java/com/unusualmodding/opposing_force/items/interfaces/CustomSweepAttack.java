package com.unusualmodding.opposing_force.items.interfaces;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public interface CustomSweepAttack {

    default void sweep(Player player, Entity target, ParticleOptions particle) {
        double walkDist = player.walkDist - player.walkDistO;
        boolean canCriticalHit = player.getAttackStrengthScale(0.5F) > 0.9F && player.fallDistance > 0.0F && !player.onGround() && !player.onClimbable() && !player.isInWater() && !player.hasEffect(MobEffects.BLINDNESS) && !player.isPassenger() && target instanceof LivingEntity;
        if (!canCriticalHit && player.getAttackStrengthScale(0.5F) > 0.9F && walkDist < (double) player.getSpeed() && player.onGround() && !player.isSprinting()) {
            float sweepingEdge = 1.0F + EnchantmentHelper.getSweepingDamageRatio(player) * (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            for (LivingEntity livingentity : player.level().getEntitiesOfClass(LivingEntity.class, player.getItemInHand(InteractionHand.MAIN_HAND).getSweepHitBox(player, target))) {
                if (livingentity != player && livingentity != target && !player.isAlliedTo(livingentity) && (!(livingentity instanceof ArmorStand) || !((ArmorStand)livingentity).isMarker()) && player.distanceToSqr(livingentity) < Mth.square(player.getEntityReach())) {
                    livingentity.knockback(0.4F, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
                    livingentity.hurt(player.damageSources().playerAttack(player), sweepingEdge);
                }
            }
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, player.getSoundSource(), 1.0F, 1.0F);
            double x = -Mth.sin(player.getYRot() * ((float) Math.PI / 180F));
            double z = Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
            if (player.level() instanceof ServerLevel) {
                ((ServerLevel) player.level()).sendParticles(particle, player.getX() + x, player.getY(0.5D), player.getZ() + z, 0, x, 0.0D, z, 0.0D);
            }
        }
    }
}
