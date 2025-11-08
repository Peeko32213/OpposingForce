package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.enchantments.ThrowingEnchantment;
import com.unusualmodding.opposing_force.items.interfaces.CustomSweepAttack;
import com.unusualmodding.opposing_force.registry.OPEnchantments;
import com.unusualmodding.opposing_force.registry.OPParticles;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import com.unusualmodding.opposing_force.registry.enums.OPTiers.OPItemTiers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

public class LaserBladeItem extends SwordItem implements CustomSweepAttack {

    private int swingSoundCooldown = 0;

    public LaserBladeItem(Properties properties) {
        super(OPItemTiers.LASER, 3, -2.4F, properties);
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
            ThrowingEnchantment.throwBlade(level, player, hand, itemstack);
            return InteractionResultHolder.success(itemstack);
        }
        else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
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
        if (this.swingSoundCooldown > 0) {
            this.swingSoundCooldown--;
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity target) {
        int fireAspect = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, stack);
        ParticleOptions particle = fireAspect > 0 ? OPParticles.FIRE_LASER_SWEEP.get() : OPParticles.LASER_SWEEP.get();
        this.sweep(player, target, particle);
        return super.onLeftClickEntity(stack, player, target);
    }

    @Override
    public boolean onEntitySwing(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        Level level = entity.level();
        if (!level.isClientSide && this.swingSoundCooldown == 0) {
            this.swingSoundCooldown = 7;
            level.playSound(null, entity.blockPosition(), OPSoundEvents.LASER_BLADE_SWING.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        }
        return super.onEntitySwing(stack, entity);
    }
}
