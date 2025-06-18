package com.unusualmodding.opposing_force.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TremblerShellBlock extends FallingBlock implements SimpleWaterloggedBlock {

    protected static final VoxelShape SHAPE_X = Block.box(4.5D, 0.0D, 2.0D, 11.5D, 12.0D, 14.0D);
    protected static final VoxelShape SHAPE_Z = Block.box(2.0D, 0.0D, 4.5D, 14.0D, 12.0D, 11.5D);

    public TremblerShellBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    protected void falling(FallingBlockEntity fallingBlockEntity) {
        fallingBlockEntity.setHurtsEntities(1.5F, 60);
    }

    public void onLand(Level level, BlockPos pos, BlockState state, BlockState state1, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            level.playSound(fallingBlockEntity, pos, SoundEvents.DRIPSTONE_BLOCK_FALL, SoundSource.BLOCKS, 1.0F, 0.6F);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return state.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis() == Direction.Axis.Z ? SHAPE_X : SHAPE_Z;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
