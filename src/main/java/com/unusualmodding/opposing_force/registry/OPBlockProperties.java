package com.unusualmodding.opposing_force.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class OPBlockProperties {

    public static BlockBehaviour.Properties mushroom(MapColor color) {
        return BlockBehaviour.Properties.of().mapColor(color).replaceable().noCollission().instabreak().sound(SoundType.GRASS).lightLevel((state) -> 1).hasPostProcess(OPBlockProperties::always).ignitedByLava().offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY);
    }

    private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
        return true;
    }
}
