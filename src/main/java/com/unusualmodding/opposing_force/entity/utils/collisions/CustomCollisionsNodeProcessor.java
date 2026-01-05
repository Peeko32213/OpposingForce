package com.unusualmodding.opposing_force.entity.utils.collisions;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.jetbrains.annotations.NotNull;

public class CustomCollisionsNodeProcessor extends WalkNodeEvaluator {

    public CustomCollisionsNodeProcessor() {
    }

    public static @NotNull BlockPathTypes getBlockPathTypeStatic(BlockGetter blockGetter, BlockPos.MutableBlockPos pos) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        BlockPathTypes pathnodetype = getNodes(blockGetter, pos);
        if (pathnodetype == BlockPathTypes.OPEN && j >= 1) {
            BlockPathTypes pathnodetype1 = getNodes(blockGetter, pos.set(i, j - 1, k));
            pathnodetype = pathnodetype1 != BlockPathTypes.WALKABLE && pathnodetype1 != BlockPathTypes.OPEN && pathnodetype1 != BlockPathTypes.WATER && pathnodetype1 != BlockPathTypes.LAVA ? BlockPathTypes.WALKABLE : BlockPathTypes.OPEN;
            if (pathnodetype1 == BlockPathTypes.DAMAGE_FIRE) {
                pathnodetype = BlockPathTypes.DAMAGE_FIRE;
            }

            if (pathnodetype1 == BlockPathTypes.DAMAGE_OTHER) {
                pathnodetype = BlockPathTypes.DAMAGE_OTHER;
            }

            if (pathnodetype1 == BlockPathTypes.STICKY_HONEY) {
                pathnodetype = BlockPathTypes.STICKY_HONEY;
            }
        }

        if (pathnodetype == BlockPathTypes.WALKABLE) {
            pathnodetype = checkNeighbourBlocks(blockGetter, pos.set(i, j, k), pathnodetype);
        }

        return pathnodetype;
    }

    protected static BlockPathTypes getNodes(BlockGetter blockGetter, BlockPos pos) {
        BlockState blockstate = blockGetter.getBlockState(pos);
        BlockPathTypes type = blockstate.getBlockPathType(blockGetter, pos, null);
        if (type != null) return type;
        if (blockstate.isAir()) {
            return BlockPathTypes.OPEN;
        } else if (blockstate.getBlock() == Blocks.BAMBOO) {
            return BlockPathTypes.OPEN;
        } else {
            return getBlockPathTypeRaw(blockGetter, pos);
        }
    }

    @Override
    public @NotNull BlockPathTypes getBlockPathType(@NotNull BlockGetter blockGetter, int x, int y, int z) {
        return getBlockPathTypeStatic(blockGetter, new BlockPos.MutableBlockPos(x, y, z));
    }

    @Override
    protected @NotNull BlockPathTypes evaluateBlockPathType(BlockGetter world, @NotNull BlockPos pos, @NotNull BlockPathTypes nodeType) {
        BlockState state = world.getBlockState(pos);
        return ((CustomCollisions) this.mob).canPassThrough(pos, state, state.getBlockSupportShape(world, pos)) ? BlockPathTypes.OPEN : super.evaluateBlockPathType(world, pos, nodeType);
    }
}