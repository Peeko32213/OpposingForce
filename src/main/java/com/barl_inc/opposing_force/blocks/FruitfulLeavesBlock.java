package com.barl_inc.opposing_force.blocks;

import com.barl_inc.opposing_force.blocks.utils.FruitLeaves;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Supplier;

public class FruitfulLeavesBlock extends LeavesBlock implements FruitLeaves {

    private final Supplier<Item> fruit;
    private final Supplier<Block> leaves;

    public FruitfulLeavesBlock(Properties properties, Supplier<Item> fruit, Supplier<Block> leaves) {
        super(properties);
        this.fruit = fruit;
        this.leaves = leaves;
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        int amount = 1 + level.getRandom().nextInt(2);
        popResource(level, pos, new ItemStack(fruit.get(), amount));
        level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.getRandom().nextFloat() * 0.4F);
        level.setBlock(pos, this.leaves.get().defaultBlockState().setValue(LeavesBlock.PERSISTENT, state.getValue(LeavesBlock.PERSISTENT)).setValue(LeavesBlock.DISTANCE, state.getValue(LeavesBlock.DISTANCE)), 2);
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public FruitState getAge() {
        return FruitState.FRUITFUL;
    }
}
