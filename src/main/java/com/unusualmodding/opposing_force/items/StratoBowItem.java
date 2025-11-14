package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.entity.projectile.StratoArrow;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

public class StratoBowItem extends BowItem {

    public StratoBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int useDuration) {
        super.onUseTick(level, entity, stack, useDuration);
        if (entity instanceof Player player) {
            if (player.getDeltaMovement().y < 0.0 && !player.isFallFlying() && !player.onGround() && !player.onClimbable() && !player.isInWaterOrBubble() && !player.getAbilities().flying) {
                player.setDeltaMovement(player.getDeltaMovement().x, player.getDeltaMovement().y * 0.1, player.getDeltaMovement().z);
                player.resetFallDistance();
                if (level.getRandom().nextFloat() < 0.4F) {
                    player.level().addParticle(ParticleTypes.POOF, player.position().x, player.position().y, player.position().z, (level.getRandom().nextFloat() - 0.5F) / 3.0F, -0.15D, (level.getRandom().nextFloat() - 0.5F) / 3.0F);
                }
            }
        }
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player) {
            boolean infinity = player.getAbilities().instabuild || EnchantmentHelper.getTagEnchantmentLevel(Enchantments.INFINITY_ARROWS, stack) > 0;
            ItemStack ammoStack = player.getProjectile(stack);

            int useDuration = this.getUseDuration(stack) - timeLeft;
            useDuration = ForgeEventFactory.onArrowLoose(stack, level, player, useDuration, !ammoStack.isEmpty() || infinity);
            if (useDuration < 0) return;

            if (!ammoStack.isEmpty() || infinity) {
                if (ammoStack.isEmpty()) {
                    ammoStack = new ItemStack(Items.ARROW);
                }
                float powerForTime = getPowerForTime(useDuration);
                if (!((double) powerForTime < 0.1D)) {
                    boolean hasArrow = player.getAbilities().instabuild || (ammoStack.getItem() instanceof ArrowItem && ((ArrowItem) ammoStack.getItem()).isInfinite(ammoStack, stack, player));
                    boolean inAir = !player.onGround() && !player.onClimbable() && !player.isInWaterOrBubble() && !player.getAbilities().flying && player.getDeltaMovement().y < 0.0 && !player.isFallFlying();
                    if (!level.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem) (ammoStack.getItem() instanceof ArrowItem ? ammoStack.getItem() : Items.ARROW);
                        AbstractArrow arrow = arrowitem.createArrow(level, ammoStack, player);
                        arrow = customArrow(arrow);
                        arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, powerForTime * (inAir ? 3.5F : 3.0F), (inAir ? 0.0F : 1.0F));
                        if (powerForTime == 1.0F) {
                            arrow.setCritArrow(true);
                        }
                        int j = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
                        if (j > 0) {
                            arrow.setBaseDamage(arrow.getBaseDamage() + (double)j * 0.5D + 0.5D);
                        }
                        int k = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
                        if (k > 0) {
                            arrow.setKnockback(k);
                        }
                        if (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0) {
                            arrow.setSecondsOnFire(100);
                        }
                        stack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(player.getUsedItemHand()));
                        if (hasArrow || player.getAbilities().instabuild && (ammoStack.is(Items.SPECTRAL_ARROW) || ammoStack.is(Items.TIPPED_ARROW))) {
                            arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }
                        level.addFreshEntity(arrow);
                    }
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + powerForTime * 0.5F);
                    if (!hasArrow && !player.getAbilities().instabuild) {
                        ammoStack.shrink(1);
                        if (ammoStack.isEmpty()) {
                            player.getInventory().removeItem(ammoStack);
                        }
                    }
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    public @NotNull AbstractArrow customArrow(AbstractArrow arrow) {
        return new StratoArrow(arrow.level(), arrow.getOwner());
    }
}
