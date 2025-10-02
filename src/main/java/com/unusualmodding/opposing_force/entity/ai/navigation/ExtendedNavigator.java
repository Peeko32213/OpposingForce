package com.unusualmodding.opposing_force.entity.ai.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

/*
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://mozilla.org/MPL/2.0/.
 *
 * Source: SmartBrainLib - https://github.com/Tslat/SmartBrainLib/tree/1.20
 * Modifications by: Opposing Force - 10/1/2025
 */

public interface ExtendedNavigator {

    float EPSILON = 1.0E-8F;

    Mob getMobEN();

    Path getPathEN();

    default boolean canPathOnto(BlockPathTypes pathType) {
        return switch (pathType) {
            case WATER, LAVA, OPEN -> false;
            default -> true;
        };
    }

    default boolean canPathInto(BlockPathTypes pathType) {
        return switch (pathType) {
            case DAMAGE_FIRE, DANGER_FIRE, DAMAGE_OTHER -> true;
            default -> false;
        };
    }

    default boolean isCloseToNextNode(float distance) {
        final Mob mob = getMobEN();
        final Path path = getPathEN();
        final Vec3 nextNodePos = getEntityPosAtNode(path.getNextNodeIndex());

        return Math.abs(mob.getX() - nextNodePos.x) < distance &&
                Math.abs(mob.getZ() - nextNodePos.z) < distance &&
                Math.abs(mob.getY() - nextNodePos.y) < 1;
    }

    default boolean isAboutToTraverseVertically() {
        final Mob mob = getMobEN();
        final Path path = getPathEN();
        final int fromNode = path.getNextNodeIndex();
        final int fromNodeHeight = path.getNode(fromNode).y;
        final int toNode = Math.min(path.getNodeCount(), fromNode + Mth.ceil(mob.getBbWidth() * 0.5d) + 1);

        for (int i = fromNode + 1; i < toNode; i++) {
            if (path.getNode(i).y != fromNodeHeight)
                return true;
        }

        return false;
    }

    default boolean attemptShortcut(int targetNode, Vec3 safeSurfacePos) {
        final Mob mob = getMobEN();
        final Path path = getPathEN();
        final Vec3 position = mob.position();
        final Vec3 minBounds = safeSurfacePos.add(-mob.getBbWidth() * 0.5d, 0, -mob.getBbWidth() * 0.5d);
        final Vec3 maxBounds = minBounds.add(mob.getBbWidth(), mob.getBbHeight(), mob.getBbWidth());

        for (int nodeIndex = targetNode - 1; nodeIndex > path.getNextNodeIndex(); nodeIndex--) {
            final Vec3 nodeDelta = getEntityPosAtNode(nodeIndex).subtract(position);

            if (isCollisionFreeTraversal(nodeDelta, minBounds, maxBounds)) {
                path.setNextNodeIndex(nodeIndex);

                return true;
            }
        }

        return false;
    }

    default Vec3 getEntityPosAtNode(int nodeIndex) {
        final Mob mob = getMobEN();
        final Path path = getPathEN();
        final double lateralOffset = Mth.floor(mob.getBbWidth() + 1d) / 2d;

        return Vec3.atLowerCornerOf(path.getNodePos(nodeIndex)).add(lateralOffset, 0, lateralOffset);
    }

    default boolean isCollisionFreeTraversal(Vec3 traversalVector, Vec3 minBoundsPos, Vec3 leadingEdgePos) {
        final float traversalDistance = (float)traversalVector.length();

        if (traversalDistance < EPSILON)
            return true;

        final VoxelRayDetails ray = new VoxelRayDetails();

        for (Direction.Axis axis : Direction.Axis.values()) {
            final int index = axis.ordinal();
            final float axisLength = lengthForAxis(traversalVector, axis);
            final boolean isPositive = axisLength >= 0;
            final float maxPos = lengthForAxis(isPositive ? leadingEdgePos : minBoundsPos, axis);

            ray.absStep[index] = isPositive ? 1 : -1;
            ray.minPos[index] = lengthForAxis(isPositive ? minBoundsPos : leadingEdgePos, axis);
            ray.leadingEdgeBound[index] = Mth.floor(maxPos - ray.absStep[index] * EPSILON);
            ray.trailingEdgeBound[index] = Mth.floor(ray.minPos[index] + ray.absStep[index] * EPSILON);
            ray.axisLengthNormalised[index] = axisLength / traversalDistance;
            ray.axisSteps[index] = Mth.abs(traversalDistance / axisLength);
            final float dist = isPositive ? (ray.leadingEdgeBound[index] + 1 - maxPos) : (maxPos - ray.leadingEdgeBound[index]);
            ray.rayTargetLength[index] = ray.axisSteps[index] < Float.POSITIVE_INFINITY ? ray.axisSteps[index] * dist : Float.POSITIVE_INFINITY;
        }

        return collidesWhileTraversing(ray, traversalDistance);
    }

    default boolean collidesWhileTraversing(VoxelRayDetails ray, float traversalDistance) {
        final Mob mob = getMobEN();
        final Level level = mob.level();

        try (BulkSectionAccess sectionAccess = new BulkSectionAccess(level)) {
            final NodeEvaluator nodeEvaluator = mob.getNavigation().getNodeEvaluator();
            final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            float target = 0;

            do {
                final Direction.Axis longestEdge = ray.rayTargetLength[0] < ray.rayTargetLength[1] ?
                        ray.rayTargetLength[0] < ray.rayTargetLength[2] ? Direction.Axis.X : Direction.Axis.Z :
                        ray.rayTargetLength[1] < ray.rayTargetLength[2] ? Direction.Axis.Y : Direction.Axis.Z;
                final int index = longestEdge.ordinal();
                final float rayDelta = ray.rayTargetLength[index] - target;
                target = ray.rayTargetLength[index];
                ray.leadingEdgeBound[index] += ray.absStep[index];
                ray.rayTargetLength[index] += ray.axisSteps[index];

                for (Direction.Axis axis : Direction.Axis.values()) {
                    final int index2 = axis.ordinal();
                    ray.minPos[index2] += rayDelta * ray.axisLengthNormalised[index2];
                    ray.trailingEdgeBound[index2] = Mth.floor(ray.minPos[index2] + ray.absStep[index2] * EPSILON);
                }

                final int xStep = ray.absStep[0];
                final int yStep = ray.absStep[1];
                final int zStep = ray.absStep[2];
                final int xBound = longestEdge == Direction.Axis.X ? ray.leadingEdgeBound[0] : ray.trailingEdgeBound[0];
                final int yBound = longestEdge == Direction.Axis.Y ? ray.leadingEdgeBound[1] : ray.trailingEdgeBound[1];
                final int zBound = longestEdge == Direction.Axis.Z ? ray.leadingEdgeBound[2] : ray.trailingEdgeBound[2];
                final int xStepBound = ray.leadingEdgeBound[0] + xStep;
                final int yStepBound = ray.leadingEdgeBound[1] + yStep;
                final int zStepBound = ray.leadingEdgeBound[2] + zStep;

                for (int x = xBound; x != xStepBound; x += xStep) {
                    for (int z = zBound; z != zStepBound; z += zStep) {
                        for (int y = yBound; y != yStepBound; y += yStep) {
                            if (!sectionAccess.getBlockState(pos.set(x, y, z)).isPathfindable(level, pos, PathComputationType.LAND))
                                return false;
                        }

                        if (!canPathOnto(nodeEvaluator.getBlockPathType(level, x, yBound - 1, z)))
                            return false;

                        final BlockPathTypes insidePathType = nodeEvaluator.getBlockPathType(level, x, yBound, z);
                        final float pathMalus = mob.getPathfindingMalus(insidePathType);

                        if (pathMalus < 0 || pathMalus >= 8)
                            return false;

                        if (canPathInto(insidePathType))
                            return false;
                    }
                }
            } while (target <= traversalDistance);
        }

        return true;
    }

    record VoxelRayDetails(float[] minPos, int[] leadingEdgeBound, int[] trailingEdgeBound, int[] absStep, float[] axisSteps, float[] rayTargetLength, float[] axisLengthNormalised) {
        public VoxelRayDetails() {
            this(new float[3], new int[3], new int[3], new int[3], new float[3], new float[3], new float[3]);
        }
    }

    default float lengthForAxis(Vec3 vector, Direction.Axis axis) {
        return (float)axis.choose(vector.x, vector.y, vector.z);
    }
}