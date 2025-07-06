package com.unusualmodding.opposing_force.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.unusualmodding.opposing_force.blocks.entity.MobHeadBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class WallMobHeadBlock extends WallSkullBlock {

    private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(4.0D, 4.0D, 8.0D, 12.0D, 12.0D, 16.0D),
            Direction.SOUTH, Block.box(4.0D, 4.0D, 0.0D, 12.0D, 12.0D, 8.0D),
            Direction.EAST, Block.box(0.0D, 4.0D, 4.0D, 8.0D, 12.0D, 12.0D),
            Direction.WEST, Block.box(8.0D, 4.0D, 4.0D, 16.0D, 12.0D, 12.0D)));

    private static final Map<Direction, VoxelShape> DICER_AABBS = Maps.newEnumMap(ImmutableMap.of(
            Direction.NORTH, Block.box(5.5D, 4.0D, 11.0D, 10.5D, 9.0D, 16.0D),
            Direction.SOUTH, Block.box(5.5D, 4.0D, 0.0D, 10.5D, 9.0D, 5.0D),
            Direction.EAST, Block.box(0.0D, 4.0D, 5.5D, 5.0D, 9.0D, 10.5D),
            Direction.WEST, Block.box(11.0D, 4.0D, 5.5D, 16.0D, 9.0D, 10.5D)));

    public WallMobHeadBlock(SkullBlock.Type type, Properties properties) {
        super(type, properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (MobHeadBlock.Types.DICER.equals(this.getType())) {
            return DICER_AABBS.get(blockState.getValue(FACING));
        }
        return AABBS.get(blockState.getValue(FACING));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MobHeadBlockEntity(blockPos, blockState);
    }
}