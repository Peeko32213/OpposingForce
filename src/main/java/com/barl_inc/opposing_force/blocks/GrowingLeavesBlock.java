package com.barl_inc.opposing_force.blocks;

import com.barl_inc.opposing_force.blocks.utils.FruitLeaves;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GrowingLeavesBlock extends LeavesBlock implements FruitLeaves {

    private final FruitState fruitState;

    public GrowingLeavesBlock(Properties properties, FruitState fruitState) {
        super(properties);
        this.fruitState = fruitState;
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.randomTick(state, level, pos, random);
        this.onRandomTick(state, level, pos, random);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return FruitLeaves.getNext(state.getBlock()).isPresent() && super.isRandomlyTicking(state);
    }

    @Override
    public FruitState getAge() {
        return this.fruitState;
    }
}
