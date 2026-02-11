package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.items.tools.ConfigurableAxeItem;
import com.unusualmodding.opposing_force.items.tools.OPToolDefinitions;
import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SawbladeItem extends ConfigurableAxeItem {

    public static final UUID SAWBLADE_SPEED_MODIFIER_UUID = UUID.fromString("ad2337e7-989c-4e7a-8916-20fe59a18fbd");
    public static HashMap<BlockPos, Integer> HIGHEST_LEAF = new HashMap<>();

    public SawbladeItem(Properties properties) {
        super(OPToolDefinitions.SAWBLADE, 3, -3.0F, properties);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemStack) {
        return 72000;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        player.playSound(OPSoundEvents.SAWBLADE_SAW_START.get());
//        AttributeModifier speedModifier = new AttributeModifier(SAWBLADE_SPEED_MODIFIER_UUID, "Sawblade Speed", 2.0, AttributeModifier.Operation.MULTIPLY_BASE);
//        if (!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(speedModifier)) {
//            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(speedModifier);
//        }
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity living, @NotNull ItemStack itemStack, int timeUsing) {
        super.onUseTick(level, living, itemStack, timeUsing);
        if (living instanceof Player player && !level.isClientSide) {
            this.hurtNearbyEntities(player);
        }
        OpposingForce.PROXY.playWorldSound(living, (byte) 6);
    }

    private void hurtNearbyEntities(Player player) {
        List<LivingEntity> nearbyEntities = player.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), player, player.getBoundingBox().inflate(1.5D));
        for (LivingEntity entity : nearbyEntities) {
            InteractionHand hand = player.getUsedItemHand();
            float knockback = (float) player.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
            knockback += EnchantmentHelper.getKnockbackBonus(player);
            float damage = 3.0F;
            damage += EnchantmentHelper.getDamageBonus(player.getItemInHand(hand), entity.getMobType()) * 0.5F;

            if (!isEntityInFront(player, entity) || player.level().isClientSide) continue;
            if (entity.hurt(player.level().damageSources().source(OPDamageTypes.SAWBLADE), damage)) {
                entity.invulnerableTime -= 5;
                if (player.getItemInHand(hand).getEnchantmentLevel(Enchantments.FIRE_ASPECT) > 0) {
                    entity.setSecondsOnFire(player.getItemInHand(hand).getEnchantmentLevel(Enchantments.FIRE_ASPECT));
                }
                if (player.getItemInHand(hand).getEnchantmentLevel(Enchantments.KNOCKBACK) > 0) {
                    entity.knockback(knockback * 0.15F, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
                }
                if (player.getRandom().nextBoolean()) {
                    player.getItemInHand(hand).hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(player1.getUsedItemHand()));
                }
            }
            break;
        }
    }

    private static boolean isEntityInFront(Player player, LivingEntity target) {
        Vec3 look = player.getLookAngle().normalize();
        Vec3 toTarget = target.position().subtract(player.position()).normalize();
        double dot = look.dot(toTarget);
        return dot > 0.3D;
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity living, int useTimeLeft) {
        super.releaseUsing(stack, level, living, useTimeLeft);
        if (living instanceof Player player) {
            player.getCooldowns().addCooldown(this, 40);
            clearSawbladeAttributes(player);
        }
        OpposingForce.PROXY.clearSoundCacheFor(living);
        living.playSound(OPSoundEvents.SAWBLADE_SAW_END.get());
    }

    public static void onPlayerTick(Player player) {
        ItemStack stack = player.getUseItem();
        if (stack.getItem() == OPItems.SAWBLADE.get()) {
            AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeModifier speedModifier = new AttributeModifier(SAWBLADE_SPEED_MODIFIER_UUID, "Sawblade Speed", 3.0, AttributeModifier.Operation.MULTIPLY_TOTAL);
            if (speedAttribute != null && !speedAttribute.hasModifier(speedModifier)) {
                speedAttribute.addTransientModifier(speedModifier);
            }
        } else {
            clearSawbladeAttributes(player);
        }
    }

    public static void clearSawbladeAttributes(Player player) {
        player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SAWBLADE_SPEED_MODIFIER_UUID);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int i, boolean held) {
        super.inventoryTick(stack, level, entity, i, held);
        boolean using = entity instanceof LivingEntity living && living.getUseItem().equals(stack);
        if (level.isClientSide) {
            int useTime = getUseTime(stack);
            CompoundTag compoundTag = stack.getOrCreateTag();
            if (compoundTag.getInt("PrevUseTime") != compoundTag.getInt("UseTime")) {
                compoundTag.putInt("PrevUseTime", getUseTime(stack));
            }
            if (using && useTime < 5.0F) {
                setUseTime(stack, useTime + 1);
            }
            if (!using && useTime > 0.0F) {
                setUseTime(stack, useTime - 1);
            }
        }
    }

    public static void setUseTime(ItemStack stack, int useTime) {
        CompoundTag compoundTag = stack.getOrCreateTag();
        compoundTag.putInt("PrevUseTime", getUseTime(stack));
        compoundTag.putInt("UseTime", useTime);
    }

    public static int getUseTime(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        return compoundTag != null ? compoundTag.getInt("UseTime") : 0;
    }

    public static float getLerpedUseTime(ItemStack stack, float f) {
        CompoundTag compoundTag = stack.getTag();
        float prev = compoundTag != null ? (float) compoundTag.getInt("PrevUseTime") : 0F;
        float current = compoundTag != null ? (float) compoundTag.getInt("UseTime") : 0F;
        return prev + f * (current - prev);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.is(OPItems.SAWBLADE.get()) || !newStack.is(OPItems.SAWBLADE.get());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category == EnchantmentCategory.WEAPON && enchantment != Enchantments.SWEEPING_EDGE;
    }

    public static float sawbladeComputeFov(Player player, float currentFovModifier) {
        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute != null) {
            AttributeModifier speedModifier = speedAttribute.getModifier(SAWBLADE_SPEED_MODIFIER_UUID);
            if (speedModifier != null) {
                double currentSpeed = speedAttribute.getValue();
                double modifierScale = 1.0D + speedModifier.getAmount();
                double speedWithoutSawblade = currentSpeed / modifierScale;
                return (float) ((speedWithoutSawblade / (double) player.getAbilities().getWalkingSpeed() + 1.0D) / 2.0D);
            }
        }
        return currentFovModifier;
    }

    public static void chopTree(Level level, BlockPos blockPos, Player player) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        BlockState startState = level.getBlockState(blockPos);
        Block block = startState.getBlock();

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            if (!startState.is(BlockTags.LOGS)) return;

            BlockPos tempPos = blockPos.immutable();
            while (level.getBlockState(tempPos.below()).getBlock().equals(block)) {
                tempPos = tempPos.below().immutable();
            }

            for (BlockPos belowPos : BlockPos.betweenClosed(tempPos.getX() - 1, tempPos.getY() - 1, tempPos.getZ() - 1, tempPos.getX() + 1, tempPos.getY() - 1, tempPos.getZ() + 1)) {
                if (level.getBlockState(belowPos).getBlock().equals(block)) {
                    tempPos = belowPos.immutable();
                    while (level.getBlockState(tempPos.below()).getBlock().equals(block)) {
                        tempPos = tempPos.below().immutable();
                    }
                    break;
                }
            }

            blockPos = tempPos.immutable();

            int logAmount = isTreeAndReturnLogAmount(level, blockPos);
            if (logAmount < 0) return;

            List<BlockPos> logsToBreak = getLogsToBreak(level, blockPos, new ArrayList<>(), logAmount, block);
            BlockPos highestLogPos = blockPos.immutable();

            for (BlockPos logPos : logsToBreak) {
                if (logPos.getY() > highestLogPos.getY()) highestLogPos = logPos.immutable();

                BlockState logState = level.getBlockState(logPos);
                if (!logState.is(BlockTags.LOGS)) continue;
                level.destroyBlock(logPos, true, player);

                if (!player.isCreative() && level.getRandom().nextBoolean()) {
                    stack.hurtAndBreak(1, serverPlayer, (livingEntity) -> livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }
            }
        }
    }

    public static int isTreeAndReturnLogAmount(Level level, BlockPos pos) {
        HIGHEST_LEAF.put(pos, 0);

        int leafcount = 8;
        int logCount = 0;
        int prevleafcount = -1;
        int prevlogCount = -1;

        int highesty = 0;
        for (int y = 1; y <= 30; y++) {
            if (prevleafcount == leafcount && prevlogCount == logCount) break;
            prevleafcount = leafcount;
            prevlogCount = logCount;
            for (BlockPos blockPos : BlockPos.betweenClosed(pos.getX() - 2, pos.getY() + (y-1), pos.getZ() - 2, pos.getX() + 2, pos.getY() + (y-1), pos.getZ() + 2)) {
                BlockState state = level.getBlockState(blockPos);
                if (state.is(BlockTags.LEAVES)) {
                    if (state.getOptionalValue(LeavesBlock.PERSISTENT).orElse(false)) return -1;
                    leafcount--;
                    if (blockPos.getY() > highesty) highesty = blockPos.getY();
                }
                else if (state.is(BlockTags.LOGS)) logCount++;
            }
        }
        HIGHEST_LEAF.put(pos.immutable(), highesty);
        if (leafcount < 0) {
            return logCount;
        }
        return -1;
    }

    private static List<BlockPos> getLogsToBreak(Level level, BlockPos pos, List<BlockPos> logsToBreak, int logCount, Block logType) {
        if (logsToBreak.size() >= 256) return logsToBreak;
        List<BlockPos> checkAround = new ArrayList<>();
        int downY = pos.getY() - 1;
        List<BlockPos> aroundLogs = new ArrayList<>();
        for (BlockPos aL : BlockPos.betweenClosed(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1, pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)) {
            aroundLogs.add(aL.immutable());
        }
        for (BlockPos aroundLogPos : aroundLogs) {
            if (logsToBreak.contains(aroundLogPos)) continue;
            BlockState logState = level.getBlockState(aroundLogPos);
            if (logState.getBlock().equals(logType)) {
                if (aroundLogPos.getY() != downY) checkAround.add(aroundLogPos);
                logsToBreak.add(aroundLogPos);
            }
        }
        if (checkAround.isEmpty()) return logsToBreak;
        for (BlockPos capos : checkAround) {
            for (BlockPos logPos : getLogsToBreak(level, capos, logsToBreak, logCount, logType)) {
                if (!logsToBreak.contains(logPos)) logsToBreak.add(logPos.immutable());
            }
        }
        BlockPos up = pos.above(2);
        return getLogsToBreak(level, up.immutable(), logsToBreak, logCount, logType);
    }

    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
        return toolAction == ToolActions.AXE_DIG;
    }
}