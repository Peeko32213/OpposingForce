package com.unusualmodding.opposing_force.blocks.entity;

import com.unusualmodding.opposing_force.registry.OPBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MobHeadBlockEntity extends SkullBlockEntity {

    public MobHeadBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return OPBlockEntityTypes.MOB_HEAD.get();
    }
}