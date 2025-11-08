package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.enchantments.ThrowingEnchantment;
import com.unusualmodding.opposing_force.items.interfaces.CustomSweepAttack;
import com.unusualmodding.opposing_force.registry.OPEnchantments;
import com.unusualmodding.opposing_force.registry.OPParticles;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import com.unusualmodding.opposing_force.registry.enums.OPTiers.OPItemTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LaserBladeItem extends SwordItem implements CustomSweepAttack, DyeableLeatherItem {

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
            if (player.level().isClientSide && isDyed(stack)) {
                CompoundTag compoundTag = stack.getTagElement("bladeColor");
                if (compoundTag != null && compoundTag.contains("color", 99) && compoundTag.getInt("color") != -1) {
                    int decimal = compoundTag.getInt("color");
                    float r = (float) ((decimal & 16711680) >> 16) / 255.0F;
                    float g = (float) ((decimal & '\uff00') >> 8) / 255.0F;
                    float b = (float) ((decimal & 255)) / 255.0F;
                    player.level().addAlwaysVisibleParticle(OPParticles.DYED_SWEEP.get(), true, player.getX() + x, player.getY(0.5D), player.getZ() + z, r, g, b);
                }
            }
            else if (player.level() instanceof ServerLevel serverLevel && !isDyed(stack)) {
                serverLevel.sendParticles(OPParticles.LASER_SWEEP.get(), player.getX() + x, player.getY(0.5D), player.getZ() + z, 0, x, 0.0D, z, 0.0D);
            }
        }
        return super.onLeftClickEntity(stack, player, target);
    }

    @Override
    public boolean onEntitySwing(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        Level level = entity.level();
        if (!level.isClientSide && this.swingSoundCooldown == 0) {
            this.swingSoundCooldown = 10;
            level.playSound(null, entity.blockPosition(), OPSoundEvents.LASER_BLADE_SWING.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        }
        return super.onEntitySwing(stack, entity);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, level, list, tooltipFlag);

        CompoundTag compoundTag = stack.getTagElement("bladeColor");
        if (compoundTag != null && compoundTag.contains("color", 99) && compoundTag.getInt("color") != -1) {
            int remainder;
            int decimal = compoundTag.getInt("color");
            StringBuilder hex= new StringBuilder();
            char[] hexchars = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
            while(decimal > 0) {
                remainder = decimal % 16;
                hex.insert(0, hexchars[remainder]);
                decimal = decimal / 16;
            }
            list.add(Component.translatable("item.opposing_force.laser_blade.color").withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)).append(Component.translatable("#" + hex).withStyle(Style.EMPTY.withColor(compoundTag.getInt("color")))));
        } else {
            list.add(Component.translatable("item.opposing_force.laser_blade.dyeable").withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(true)));
        }
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(blockPos);
        ItemStack itemStack = context.getPlayer().getItemInHand(context.getHand());

        if (state.is(Blocks.WATER_CAULDRON) && this.hasCustomColor(itemStack)) {
            ItemStack itemStack2 = itemStack.copy();
            this.clearColor(itemStack2);
            context.getPlayer().setItemInHand(context.getHand(), itemStack2);
            LayeredCauldronBlock.lowerFillLevel(level.getBlockState(blockPos), level, blockPos);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }

    public static boolean isDyed(ItemStack stack) {
        CompoundTag compoundtag = stack.getTagElement("bladeColor");
        return compoundtag != null && compoundtag.contains("color", 99);
    }

    @Override
    public boolean hasCustomColor(ItemStack stack) {
        CompoundTag compoundtag = stack.getTagElement("bladeColor");
        return compoundtag != null && compoundtag.contains("color", 99);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundTag compoundtag = stack.getTagElement("bladeColor");
        return compoundtag != null && compoundtag.contains("color", 99) ? compoundtag.getInt("color") : -1;
    }

    @Override
    public void clearColor(ItemStack stack) {
        CompoundTag compoundtag = stack.getTagElement("bladeColor");
        if (compoundtag != null && compoundtag.contains("color")) {
            compoundtag.remove("color");
        }
    }

    @Override
    public void setColor(ItemStack stack, int color) {
        stack.getOrCreateTagElement("bladeColor").putInt("color", color);
    }
}
