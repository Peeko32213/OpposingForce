package com.unusualmodding.opposing_force.blocks.utils;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.unusualmodding.opposing_force.registry.OPBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

public interface FruitLeaves extends FruitLeavesChangeOverTime<FruitLeaves.FruitState> {

    Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() ->
            ImmutableBiMap.<Block, Block>builder()
                    .put(OPBlocks.APPLE_LEAVES.get(), OPBlocks.FLOWERING_APPLE_LEAVES.get())
                    .put(OPBlocks.FLOWERING_APPLE_LEAVES.get(), OPBlocks.FRUITFUL_APPLE_LEAVES.get())

                    .put(OPBlocks.INFESTED_APPLE_LEAVES.get(), OPBlocks.FLOWERING_INFESTED_APPLE_LEAVES.get())
                    .put(OPBlocks.FLOWERING_INFESTED_APPLE_LEAVES.get(), OPBlocks.FRUITFUL_INFESTED_APPLE_LEAVES.get())
                    .build()
    );

    static Optional<Block> getNext(Block block) {
        return Optional.ofNullable(NEXT_BY_BLOCK.get().get(block));
    }

    default Optional<BlockState> getNext(BlockState state) {
        return getNext(state.getBlock()).map((block) -> block.withPropertiesOf(state));
    }

    enum FruitState {
        FRUITLESS,
        FLOWERING,
        FRUITFUL;
    }
}
