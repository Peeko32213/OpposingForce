package com.unusualmodding.opposing_force.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class OPMath {

    public static Vec3 readVec3(FriendlyByteBuf buf) {
        return new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static FriendlyByteBuf writeVec3(FriendlyByteBuf buf, Vec3 vec3) {
        buf.writeDouble(vec3.x());
        buf.writeDouble(vec3.y());
        buf.writeDouble(vec3.z());
        return buf;
    }

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
}
