package com.unusualmodding.opposing_force.blocks;

import com.unusualmodding.opposing_force.blocks.entity.MobHeadBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MobHeadBlock extends SkullBlock {

    protected static final VoxelShape DICER_SHAPE = Block.box(4, 0, 4, 12, 8, 12);

    public MobHeadBlock(Type type, Properties properties) {
        super(type, properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return DICER_SHAPE;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MobHeadBlockEntity(blockPos, blockState);
    }

    @SuppressWarnings("unused")
    public enum Types implements SkullBlock.Type {
        DICER
    }
}