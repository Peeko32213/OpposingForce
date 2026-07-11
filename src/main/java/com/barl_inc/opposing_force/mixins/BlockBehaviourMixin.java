package com.barl_inc.opposing_force.mixins;

import com.barl_inc.opposing_force.registry.tags.OPEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@SuppressWarnings("deprecation")
@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {

    @Redirect(method = "getCollisionShape", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/shapes/VoxelShape;"))
    private VoxelShape opposingForce$redirectLeafCollision(BlockState state, BlockGetter blockGetter, BlockPos pos, BlockState state1, BlockGetter blockGetter1, BlockPos pos1, CollisionContext context) {
        if (state1.getBlock() instanceof LeavesBlock && context instanceof EntityCollisionContext entityContext) {
            Entity entity = entityContext.getEntity();
            if (entity != null && entity.getType().is(OPEntityTypeTags.NO_LEAF_COLLISIONS)) {
                return Shapes.empty();
            }
        }
        return state.getShape(blockGetter, pos);
    }

    @Redirect(method = "getVisualShape", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour;getCollisionShape(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;"))
    private VoxelShape opposingForce$redirectLeafVisual(BlockBehaviour blockBehaviour, BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        if (state.getBlock() instanceof LeavesBlock && context instanceof EntityCollisionContext entityContext) {
            Entity entity = entityContext.getEntity();
            if (entity != null && entity.getType().is(OPEntityTypeTags.NO_LEAF_COLLISIONS)) {
                return Shapes.empty();
            }
        }
        return blockBehaviour.getCollisionShape(state, blockGetter, pos, context);
    }
}