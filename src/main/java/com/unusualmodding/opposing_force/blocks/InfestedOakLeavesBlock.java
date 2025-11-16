package com.unusualmodding.opposing_force.blocks;

import com.google.common.collect.Maps;
import com.unusualmodding.opposing_force.entity.Tart;
import com.unusualmodding.opposing_force.registry.OPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class InfestedOakLeavesBlock extends LeavesBlock {

    private final Block hostBlock;
    private static final Map<BlockState, BlockState> INFESTED_TO_HOST_STATES = Maps.newIdentityHashMap();

    public InfestedOakLeavesBlock(Block hostBlock, Properties properties) {
        super(properties.destroyTime(hostBlock.defaultDestroyTime() / 2.0F).explosionResistance(0.2F));
        this.hostBlock = hostBlock;
    }

    public Block getHostBlock() {
        return this.hostBlock;
    }

    private void spawnInfestation(ServerLevel level, BlockPos pos) {
        Tart tart = OPEntities.TART.get().create(level);
        if (tart != null) {
            tart.moveTo((double) pos.getX() + (double) 0.5F, pos.getY(), (double) pos.getZ() + (double) 0.5F, 0.0F, 0.0F);
            level.addFreshEntity(tart);
        }
    }

    public void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
        if (level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            this.spawnInfestation(level, pos);
        }
    }

    public BlockState hostStateByInfested(BlockState pInfested) {
        return getNewStateWithProperties(pInfested, () -> this.getHostBlock().defaultBlockState());
    }

    private static BlockState getNewStateWithProperties(BlockState state, Supplier<BlockState> supplier) {
        return InfestedOakLeavesBlock.INFESTED_TO_HOST_STATES.computeIfAbsent(state, (block) -> {
            BlockState blockState = supplier.get();
            for (Property property : block.getProperties()) {
                blockState = blockState.hasProperty(property) ? blockState.setValue(property, block.getValue(property)) : blockState;
            }
            return blockState;
        });
    }
}
