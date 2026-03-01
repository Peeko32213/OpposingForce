package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.items.tools.ConfigurablePickaxetem;
import com.unusualmodding.opposing_force.items.tools.OPToolDefinitions;
import com.unusualmodding.opposing_force.registry.OPParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TremblingSlammer extends ConfigurablePickaxetem {

    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    public TremblingSlammer(Properties properties) {
        super(OPToolDefinitions.TREMBLING, 4, -3.0F, properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return (enchantment.category == EnchantmentCategory.WEAPON || enchantment.category == EnchantmentCategory.BREAKABLE || enchantment.category == EnchantmentCategory.VANISHABLE) && enchantment != Enchantments.SWEEPING_EDGE;
    }

    public static void tremblingSlammerCriticalHit(ItemStack stack, Player player, Entity target) {
        double x = -Mth.sin(player.getYRot() * ((float) Math.PI / 180F));
        double z = Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(OPParticles.TREMBLING_SLAMMER_IMPACT.get(), player.getX() + x * 1.5D, player.getY(0.5D), player.getZ() + z * 1.5D, 0, x, 0.0D, z, 0.0D);
        }
    }

    public static void breakBlocksAroundMinedBlock(Level level, BlockPos initialBlockPos, Player player) {
        ItemStack stack = player.getMainHandItem();
        InteractionHand hand = player.getUsedItemHand();
        if (player instanceof ServerPlayer serverPlayer && !level.isClientSide) {
            if (HARVESTED_BLOCKS.contains(initialBlockPos)) return;
            for (BlockPos pos : getBlocksToBeDestroyed(initialBlockPos, serverPlayer)) {
                if (pos.equals(initialBlockPos) || level.getBlockState(pos).getBlock() != level.getBlockState(initialBlockPos).getBlock() || !stack.getItem().isCorrectToolForDrops(stack, level.getBlockState(pos))) continue;
                HARVESTED_BLOCKS.add(pos);
                level.destroyBlock(pos, true, player);
                if (player.getRandom().nextBoolean()) {
                    player.getItemInHand(hand).hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(player1.getUsedItemHand()));
                }
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }

    private static List<BlockPos> getBlocksToBeDestroyed(BlockPos initalBlockPos, ServerPlayer player) {
        List<BlockPos> positions = new ArrayList<>();
        BlockHitResult hitResult = player.level().clip(new ClipContext(player.getEyePosition(1.0F), player.getEyePosition(1.0F).add(player.getViewVector(1.0F).scale(6.0F)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if (hitResult.getType() == HitResult.Type.MISS) return positions;
        Direction direction = hitResult.getDirection();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                BlockPos pos = switch (direction.getAxis()) {
                    case Y -> initalBlockPos.offset(x, 0, y);
                    case Z -> initalBlockPos.offset(x, y, 0);
                    case X -> initalBlockPos.offset(0, y, x);
                };
                positions.add(pos);
            }
        }
        return positions;
    }
}
