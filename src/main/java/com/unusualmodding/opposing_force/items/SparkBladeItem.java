package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPMobEffects;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import com.unusualmodding.opposing_force.registry.enums.OPTiers;
import com.unusualmodding.opposing_force.utils.OPMath;
import com.unusualmodding.opposing_force.utils.ParticleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class SparkBladeItem extends SwordItem {

    public SparkBladeItem(Properties properties) {
        super(OPTiers.OPItemTiers.ELECTRIC, 4, -2.8F, properties);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (super.hurtEnemy(stack, target, attacker)) {
            if (!target.level().isClientSide) {
                this.sendElectricParticles(target, 3);
            }
            target.addEffect(new MobEffectInstance(OPMobEffects.ELECTRIFIED.get(), 100, 0));
            target.playSound(OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), 1.0F, 1.0F / (target.level().getRandom().nextFloat() * 0.4F + 0.8F));
            return true;
        }
        return false;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity, int useTime) {
        int i = this.getUseDuration(stack) - useTime;
        if (i >= 10) {
            if (entity instanceof Player player) {
                player.getCooldowns().addCooldown(this, 80);
                Vec3 teleportPos = findTeleportLocation(level, player, 14);
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!player.getAbilities().instabuild) {
                    stack.hurtAndBreak(2, player, (player1) -> player1.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }

                level.playSound(null, player.getX(), player.getY(), player.getZ(), OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), player.getSoundSource(), 1.0F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
                this.sendElectricParticles(player, 3);
                this.tryToHurt(player);

                player.teleportTo(teleportPos.x, teleportPos.y, teleportPos.z);

                level.playSound(null, teleportPos.x(), teleportPos.y(), teleportPos.z(), OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), player.getSoundSource(), 1.0F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
                this.sendElectricParticles(player, 3);
                this.tryToHurt(player);
            }
        }
        super.releaseUsing(stack, level, entity, useTime);
    }

    public static Vec3 findTeleportLocation(Level level, LivingEntity entity, float maxDistance) {
        var blockHitResult = getTargetBlock(level, entity, ClipContext.Fluid.NONE, maxDistance);
        var pos = blockHitResult.getBlockPos();

        Vec3 bbOffset = entity.getForward().normalize().multiply(entity.getBbWidth() / 3, 0, entity.getBbHeight() / 3);
        Vec3 bbImpact = blockHitResult.getLocation().subtract(bbOffset);
        int ledgeY = (int) level.clip(new ClipContext(Vec3.atBottomCenterOf(pos).add(0, 3, 0), Vec3.atBottomCenterOf(pos), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null)).getLocation().y;
        boolean isAir = level.getBlockState(new BlockPos(new Vec3i(pos.getX(), ledgeY, pos.getZ()))).isAir();
        boolean los = level.clip(new ClipContext(bbImpact, bbImpact.add(0, ledgeY - pos.getY(), 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getType() == HitResult.Type.MISS;

        if (isAir && los && Math.abs(ledgeY - pos.getY()) <= 3) {
            Vec3 correctedPos = new Vec3(pos.getX(), ledgeY, pos.getZ());
            return correctedPos.add(0.5, 0.076, 0.5);
        } else {
            return level.clip(new ClipContext(bbImpact, bbImpact.add(0, -entity.getBbHeight(), 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getLocation().add(0, 0.076, 0);
        }
    }

    public static BlockHitResult getTargetBlock(Level level, LivingEntity entity, ClipContext.Fluid clipContext, double reach) {
        var rotation = entity.getLookAngle().normalize().scale(reach);
        var pos = entity.getEyePosition();
        var dest = rotation.add(pos);
        return level.clip(new ClipContext(pos, dest, ClipContext.Block.COLLIDER, clipContext, entity));
    }

    private void tryToHurt(LivingEntity attacker) {
        float radius = 2;
        for (Entity entity : attacker.level().getEntities(attacker, new AABB(attacker.position().subtract(radius, radius, radius), attacker.position().add(radius, radius, radius)))) {
            if (entity.distanceToSqr(attacker.position()) > radius * radius || !OPMath.hasLineOfSight(attacker, entity)) {
                continue;
            }
            Vec3 direction = entity.position().subtract(attacker.position()).normalize();
            if (entity.hurt(attacker.damageSources().source(OPDamageTypes.ELECTRIC), 3.0F)) {
                entity.playSound(OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), 1.0F, 1.0F / (entity.level().getRandom().nextFloat() * 0.4F + 0.8F));
            }
            entity.push(direction.x * 0.25, 0.25, direction.z * 0.25);
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(OPMobEffects.ELECTRIFIED.get(), 100, 0));
            }
            attacker.swing(InteractionHand.MAIN_HAND);
        }
    }

    public void sendElectricParticles(LivingEntity entity, int lightningLength) {
        for (int i1 = 0; i1 < 12; i1++) {
            ParticleUtils.spawnLightningParticles(entity.getX(), entity.getY() + entity.getBbHeight() * 0.5F, entity.getZ(), 2 + entity.getRandom().nextInt(lightningLength), 0.3F + (entity.getRandom().nextFloat() / 8), 0.5F + (entity.getRandom().nextFloat() / 8), 0.8F + (entity.getRandom().nextFloat() / 8));
        }
    }
}
