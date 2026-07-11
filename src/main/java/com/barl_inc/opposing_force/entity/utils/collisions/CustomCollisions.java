package com.barl_inc.opposing_force.entity.utils.collisions;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public interface CustomCollisions {

    // Override Entity#getAllowedMovement with entity method
    static Vec3 getAllowedMovementForEntity(Entity entity, Vec3 vecIN) {
        AABB aabb = entity.getBoundingBox();
        List<VoxelShape> list = entity.level().getEntityCollisions(entity, aabb.expandTowards(vecIN));
        Vec3 vec3 = vecIN.lengthSqr() == 0.0D ? vecIN : collideBoundingBox2(entity, vecIN, aabb, entity.level(), list);
        boolean flag = vecIN.x != vec3.x;
        boolean flag1 = vecIN.y != vec3.y;
        boolean flag2 = vecIN.z != vec3.z;
        boolean flag3 = entity.onGround() || flag1 && vecIN.y < 0.0D;
        if (entity.getStepHeight() > 0.0F && flag3 && (flag || flag2)) {
            Vec3 vec31 = collideBoundingBox2(entity, new Vec3(vecIN.x, entity.getStepHeight(), vecIN.z), aabb, entity.level(), list);
            Vec3 vec32 = collideBoundingBox2(entity, new Vec3(0.0D, entity.getStepHeight(), 0.0D), aabb.expandTowards(vecIN.x, 0.0D, vecIN.z), entity.level(), list);
            if (vec32.y < (double) entity.getStepHeight()) {
                Vec3 vec33 = collideBoundingBox2(entity, new Vec3(vecIN.x, 0.0D, vecIN.z), aabb.move(vec32), entity.level(), list).add(vec32);
                if (vec33.horizontalDistanceSqr() > vec31.horizontalDistanceSqr()) {
                    vec31 = vec33;
                }
            }

            if (vec31.horizontalDistanceSqr() > vec3.horizontalDistanceSqr()) {
                return vec31.add(collideBoundingBox2(entity, new Vec3(0.0D, -vec31.y + vecIN.y, 0.0D), aabb.move(vec31), entity.level(), list));
            }
        }

        return vec3;
    }

    boolean canPassThrough(BlockPos mutablePos, BlockState blockstate, VoxelShape voxelshape);

    //1.18 logic
    private static Vec3 collideBoundingBox2(@Nullable Entity entity, Vec3 vec3, AABB aabb, Level level, List<VoxelShape> voxelShapes) {
        ImmutableList.Builder<VoxelShape> builder = ImmutableList.builderWithExpectedSize(voxelShapes.size() + 1);
        if (!voxelShapes.isEmpty()) {
            builder.addAll(voxelShapes);
        }

        WorldBorder worldborder = level.getWorldBorder();
        boolean flag = entity != null && worldborder.isInsideCloseToBorder(entity, aabb.expandTowards(vec3));
        if (flag) {
            builder.add(worldborder.getCollisionShape());
        }

        builder.addAll(new CustomCollisionsBlockCollisions(level, entity, aabb.expandTowards(vec3)));
        return collideWithShapes2(vec3, aabb, builder.build());
    }

    private static Vec3 collideWithShapes2(Vec3 vec3, AABB aabb, List<VoxelShape> voxelShapes) {
        if (voxelShapes.isEmpty()) {
            return vec3;
        } else {
            double d0 = vec3.x;
            double d1 = vec3.y;
            double d2 = vec3.z;
            if (d1 != 0.0D) {
                d1 = Shapes.collide(Direction.Axis.Y, aabb, voxelShapes, d1);
                if (d1 != 0.0D) {
                    aabb = aabb.move(0.0D, d1, 0.0D);
                }
            }

            boolean flag = Math.abs(d0) < Math.abs(d2);
            if (flag && d2 != 0.0D) {
                d2 = Shapes.collide(Direction.Axis.Z, aabb, voxelShapes, d2);
                if (d2 != 0.0D) {
                    aabb = aabb.move(0.0D, 0.0D, d2);
                }
            }

            if (d0 != 0.0D) {
                d0 = Shapes.collide(Direction.Axis.X, aabb, voxelShapes, d0);
                if (!flag && d0 != 0.0D) {
                    aabb = aabb.move(d0, 0.0D, 0.0D);
                }
            }

            if (!flag && d2 != 0.0D) {
                d2 = Shapes.collide(Direction.Axis.Z, aabb, voxelShapes, d2);
            }

            return new Vec3(d0, d1, d2);
        }
    }

}