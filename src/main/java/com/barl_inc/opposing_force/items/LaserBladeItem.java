package com.barl_inc.opposing_force.items;

import com.barl_inc.opposing_force.enchantments.ThrowingEnchantment;
import com.barl_inc.opposing_force.items.interfaces.CustomSweepAttack;
import com.barl_inc.opposing_force.registry.OPCriterion;
import com.barl_inc.opposing_force.registry.OPEnchantments;
import com.barl_inc.opposing_force.registry.OPSoundEvents;
import com.barl_inc.opposing_force.registry.enums.OPTiers.OPItemTiers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.jetbrains.annotations.NotNull;

public class LaserBladeItem extends SwordItem implements CustomSweepAttack {

    protected ParticleOptions sweepParticle;
    private int swingSoundCooldown = 0;

    public LaserBladeItem(ParticleOptions sweepParticle) {
        super(OPItemTiers.LASER, 3, -2.4F, new Properties().stacksTo(1));
        this.sweepParticle = sweepParticle;
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
        return toolAction == ToolActions.SWORD_DIG;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getEnchantmentLevel(OPEnchantments.THROWING.get()) > 0) {
            itemstack.hurtAndBreak(2, player, (player1) -> player1.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            ThrowingEnchantment.throwBlade(level, player, hand, itemstack);
            return InteractionResultHolder.success(itemstack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity, int timeLeft) {
        super.releaseUsing(stack, level, entity, timeLeft);
        if (!level.isClientSide && entity instanceof Player player) {
            if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
                player.getCooldowns().addCooldown(stack.getItem(), 10);
            }
        }
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (super.hurtEnemy(stack, target, attacker)) {
            target.playSound(OPSoundEvents.LASER_BLADE_HIT.get(), 1.0F, 1.0F / (target.level().getRandom().nextFloat() * 0.4F + 0.8F));
            return true;
        }
        return false;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean isSelected) {
        if (this.swingSoundCooldown > 0) this.swingSoundCooldown--;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity target) {
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
            if (player.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(this.sweepParticle, player.getX() + x, player.getY(0.5D), player.getZ() + z, 0, x, 0.0D, z, 0.0D);
            }
        }
        return super.onLeftClickEntity(stack, player, target);
    }

    @Override
    public boolean onEntitySwing(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        Level level = entity.level();
        if (!level.isClientSide && this.swingSoundCooldown == 0) {
            this.swingSoundCooldown = 10;
            if (entity instanceof ServerPlayer serverPlayer && !serverPlayer.gameMode.isDestroyingBlock) {
                level.playSound(null, entity.blockPosition(), OPSoundEvents.LASER_BLADE_SWING.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            }
        }
        return super.onEntitySwing(stack, entity);
    }

    public static void deflectProjectiles(Entity entity, Projectile projectile, HitResult hitResult, ProjectileImpactEvent event) {
        if (hitResult instanceof EntityHitResult result) {
            Entity resultEntity = result.getEntity();
            if (entity != null && resultEntity instanceof LivingEntity entityBlocking) {
                if (entityBlocking.isUsingItem() && entityBlocking.getUseItem().getItem() instanceof LaserBladeItem && entityBlocking.getUseItem().getUseDuration() - entityBlocking.getUseItemRemainingTicks() <= 5) {
                    Vec3 lookVec = entityBlocking.getLookAngle().normalize();
                    Vec3 directionToTarget = entity.position().subtract(entityBlocking.position()).normalize();
                    double dot = lookVec.dot(directionToTarget);
                    int useTicks = entityBlocking.getUseItem().getUseDuration() - entityBlocking.getUseItemRemainingTicks();

                    if (dot > 0.0 && useTicks <= 3) {
                        projectile.setOwner(entityBlocking);
                        Vec3 rebound = entityBlocking.getLookAngle();
                        projectile.shoot(rebound.x(), rebound.y(), rebound.z(), 1.5F, 0.0F);
                        if (projectile instanceof AbstractHurtingProjectile hurting) {
                            hurting.xPower = rebound.x() * 0.1D;
                            hurting.yPower = rebound.y() * 0.1D;
                            hurting.zPower = rebound.z() * 0.1D;
                        }
                        entityBlocking.level().playSound(null, entityBlocking.getX(), entityBlocking.getY(), entityBlocking.getZ(), OPSoundEvents.LASER_BLADE_BLOCK.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (entityBlocking.level().getRandom().nextFloat() * 0.4F + 0.8F));
                        event.setCanceled(true);
                    } else if (useTicks > 3) {
                        if (entityBlocking instanceof Player player) {
                            if (!player.getCooldowns().isOnCooldown(player.getUseItem().getItem())) {
                                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), OPSoundEvents.LASER_BLADE_BLOCK.get(), SoundSource.PLAYERS, 1.0F, 0.8F / (player.level().getRandom().nextFloat() * 0.4F + 0.8F));
                                player.getCooldowns().addCooldown(player.getUseItem().getItem(), 50);
                                player.stopUsingItem();
                                player.getUseItem().hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                            }
                        }
                    }
                }
            }
        }
    }

    public static void parryAttack(LivingEntity entity, Entity attacker, LivingAttackEvent event) {
        if (entity != null && attacker != null) {
            Vec3 lookVec = entity.getLookAngle().normalize();
            Vec3 directionToTarget = attacker.position().subtract(entity.position()).normalize();
            double dot = lookVec.dot(directionToTarget);
            if (entity.isUsingItem() && entity.getUseItem().getItem() instanceof LaserBladeItem) {
                int useTicks = entity.getUseItem().getUseDuration() - entity.getUseItemRemainingTicks();
                if (useTicks <= 3 && dot > 0.0) {
                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), OPSoundEvents.LASER_BLADE_BLOCK.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (entity.level().getRandom().nextFloat() * 0.4F + 0.8F));
                    if (attacker instanceof LivingEntity livingAttacker) {
                        double knockbackMulti = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.KNOCKBACK, entity.getUseItem());
                        if (knockbackMulti != 0) {
                            knockbackMulti = 1 + knockbackMulti * 0.5D;
                        } else {
                            knockbackMulti = 1;
                        }
                        int fireAspect = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, entity.getUseItem());
                        if (fireAspect > 0) {
                            livingAttacker.setSecondsOnFire(3 * fireAspect);
                        }

                        if (livingAttacker instanceof Warden && entity instanceof Player) {
                            OPCriterion.PARRY_WARDEN_WITH_LASER_BLADE.get().trigger((ServerPlayer) entity);
                        }

                        livingAttacker.knockback(0.55F * knockbackMulti, attacker.getDeltaMovement().x, attacker.getDeltaMovement().z);
                        livingAttacker.knockback(0.5F * knockbackMulti, entity.getX() - livingAttacker.getX(), entity.getZ() - livingAttacker.getZ());
                    }

                    event.setCanceled(true);
                } else if (useTicks > 3) {
                    if (entity instanceof Player player) {
                        if (!player.getCooldowns().isOnCooldown(entity.getUseItem().getItem())) {
                            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), OPSoundEvents.LASER_BLADE_BLOCK.get(), SoundSource.PLAYERS, 1.0F, 0.8F / (player.level().getRandom().nextFloat() * 0.4F + 0.8F));
                            player.getCooldowns().addCooldown(entity.getUseItem().getItem(), 50);
                            player.stopUsingItem();
                            player.getUseItem().hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                        }
                    }
                }
            }
        }
    }
}
