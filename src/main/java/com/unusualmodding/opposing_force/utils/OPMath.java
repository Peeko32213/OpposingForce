package com.unusualmodding.opposing_force.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class OPMath {

    public static Vec3 getGroundBelowPosition(BlockGetter level, Vec3 in) {
        BlockPos pos = BlockPos.containing(in);
        while (pos.getY() > level.getMinBuildHeight() && level.getBlockState(pos).getCollisionShape(level, pos).isEmpty()) {
            pos = pos.below();
        }
        float top;
        BlockState state = level.getBlockState(pos);
        VoxelShape shape = state.getCollisionShape(level, pos);
        if (shape.isEmpty()) {
            top = 0.0F;
        } else {
            Vec3 modIn = new Vec3(in.x % 1.0D, 1.0D, in.z % 1.0D);
            Optional<Vec3> closest = shape.closestPointTo(modIn);
            top = closest.map(vec3 -> (float) vec3.y).orElse(0.0F);
        }
        return Vec3.upFromBottomCenterOf(pos, top);
    }

    public static boolean hasLineOfSight(LivingEntity entity1, Entity entity2) {
        if (entity2.level() != entity1.level()) {
            return false;
        } else {
            Vec3 entity1Pos = new Vec3(entity1.getX(), entity1.getEyeY(), entity1.getZ());
            Vec3 entity2Pos = new Vec3(entity2.getX(), entity2.getEyeY(), entity2.getZ());
            if (entity2Pos.distanceTo(entity1Pos) > 128.0D) {
                return false;
            } else {
                return entity1.level().clip(new ClipContext(entity1Pos, entity2Pos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity1)).getType() == HitResult.Type.MISS;
            }
        }
    }

    public static boolean hasLineOfSight(Entity entity1, Entity entity2) {
        if (entity2.level() != entity1.level()) {
            return false;
        } else {
            Vec3 entity1Pos = new Vec3(entity1.getX(), entity1.getEyeY(), entity1.getZ());
            Vec3 entity2Pos = new Vec3(entity2.getX(), entity2.getEyeY(), entity2.getZ());
            if (entity2Pos.distanceTo(entity1Pos) > 128.0D) {
                return false;
            } else {
                return entity1.level().clip(new ClipContext(entity1Pos, entity2Pos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity1)).getType() == HitResult.Type.MISS;
            }
        }
    }
}
