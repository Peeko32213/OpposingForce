package com.unusualmodding.opposing_force.items.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SawbladeItem extends ConfigurableAxeItem {

    public static HashMap<BlockPos, Integer> highestLeaf = new HashMap<>();

    public SawbladeItem(Properties properties) {
        super(OPToolDefinitions.SAWBLADE, 1, -3.0F, properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category == EnchantmentCategory.WEAPON && enchantment != Enchantments.SWEEPING_EDGE;
    }

    public static void chopTree(Level level, BlockPos blockPos, Player player) {
        if (level.isClientSide) return;
        ServerPlayer serverPlayer = (ServerPlayer) player;

        BlockState startState = level.getBlockState(blockPos);
        Block block = startState.getBlock();

        if (!startState.is(BlockTags.LOGS)) return;

        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

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

            if (!player.isCreative() && level.getRandom().nextFloat() < 0.33F) {
                stack.hurtAndBreak(1, serverPlayer, (livingEntity) -> livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
    }

    public static int isTreeAndReturnLogAmount(Level level, BlockPos pos) {
        highestLeaf.put(pos, 0);

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
        highestLeaf.put(pos.immutable(), highesty);
        if (leafcount < 0) {
            return logCount;
        }
        return -1;
    }

    private static List<BlockPos> getLogsToBreak(Level level, BlockPos pos, List<BlockPos> logsToBreak, int logCount, Block logType) {
        List<BlockPos> checkAround = new ArrayList<>();
        int downY = pos.getY()-1;
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
