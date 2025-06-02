package com.unusualmodding.opposing_force.blocks;

import com.google.common.collect.Maps;
import com.unusualmodding.opposing_force.entity.Whizz;
import com.unusualmodding.opposing_force.registry.OPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Map;
import java.util.function.Supplier;

public class InfestedAmethyst extends Block {

    private final Block hostBlock;
    private static final Map<Block, Block> BLOCK_BY_HOST_BLOCK = Maps.newIdentityHashMap();
    private static final Map<BlockState, BlockState> HOST_TO_INFESTED_STATES = Maps.newIdentityHashMap();
    private static final Map<BlockState, BlockState> INFESTED_TO_HOST_STATES = Maps.newIdentityHashMap();

    public InfestedAmethyst(Block hostBlock, BlockBehaviour.Properties properties) {
        super(properties.destroyTime(hostBlock.defaultDestroyTime() / 2.0F).explosionResistance(0.75F));
        this.hostBlock = hostBlock;
        BLOCK_BY_HOST_BLOCK.put(hostBlock, this);
    }

    public Block getHostBlock() {
        return this.hostBlock;
    }

    public static boolean isCompatibleHostBlock(BlockState pState) {
        return BLOCK_BY_HOST_BLOCK.containsKey(pState.getBlock());
    }

    private void spawnInfestation(ServerLevel level, BlockPos pos) {
        Whizz whizz = OPEntities.WHIZZ.get().create(level);
        if (whizz != null) {
            whizz.moveTo((double) pos.getX() + (double) 0.5F, pos.getY(), (double) pos.getZ() + (double) 0.5F, 0.0F, 0.0F);
            level.addFreshEntity(whizz);
            whizz.spawnAnim();
        }
    }

    public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
        if (level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            this.spawnInfestation(level, pos);
        }
    }

    public static BlockState infestedStateByHost(BlockState pHost) {
        return getNewStateWithProperties(HOST_TO_INFESTED_STATES, pHost, () -> BLOCK_BY_HOST_BLOCK.get(pHost.getBlock()).defaultBlockState());
    }

    public BlockState hostStateByInfested(BlockState pInfested) {
        return getNewStateWithProperties(INFESTED_TO_HOST_STATES, pInfested, () -> this.getHostBlock().defaultBlockState());
    }

    private static BlockState getNewStateWithProperties(Map<BlockState, BlockState> stateMap, BlockState state, Supplier<BlockState> supplier) {
        return stateMap.computeIfAbsent(state, (block) -> {
            BlockState blockState = supplier.get();
            for (Property property : block.getProperties()) {
                blockState = blockState.hasProperty(property) ? blockState.setValue(property, block.getValue(property)) : blockState;
            }
            return blockState;
        });
    }
}
