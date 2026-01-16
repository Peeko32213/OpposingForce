package com.unusualmodding.opposing_force.entity.ai.navigation;

import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SmoothFlyingPathNavigation extends FlyingPathNavigation implements ExtendedNavigator {

    private final float distance;

    public SmoothFlyingPathNavigation(Mob mob, Level level, float distance) {
        super(mob, level);
        this.distance = distance;
    }

    @Override
    public Mob getMobEN() {
        return this.mob;
    }

    @Nullable
    @Override
    public Path getPathEN() {
        return super.getPath();
    }

    @Override
    protected void followThePath() {
        Vec3 vector3d = this.getTempMobPos();
        this.maxDistanceToWaypoint = this.mob.getBbWidth() * distance;
        Vec3i vector3i = this.path.getNextNodePos();
        double d0 = Math.abs(this.mob.getX() - ((double) vector3i.getX() + 0.5D));
        double d1 = Math.abs(this.mob.getY() - (double) vector3i.getY());
        double d2 = Math.abs(this.mob.getZ() - ((double) vector3i.getZ() + 0.5D));
        boolean flag = d0 < (double) this.maxDistanceToWaypoint && d2 < (double) this.maxDistanceToWaypoint && d1 < 1.0D;
        if (flag || this.canCutCorner(this.path.getNextNode().type) && this.shouldTargetNextNodeInDirection(vector3d)) {
            this.path.advance();
        }

        this.doStuckDetection(vector3d);
    }

    private boolean shouldTargetNextNodeInDirection(Vec3 currentPosition) {
        if (this.path != null && this.path.getNextNodeIndex() + 1 >= this.path.getNodeCount()) {
            return false;
        } else {
            Vec3 vector3d = Vec3.atBottomCenterOf(this.path.getNextNodePos());
            if (!currentPosition.closerThan(vector3d, 2.0D)) {
                return false;
            } else {
                Vec3 vector3d1 = Vec3.atBottomCenterOf(this.path.getNodePos(this.path.getNextNodeIndex() + 1));
                Vec3 vector3d2 = vector3d1.subtract(vector3d);
                Vec3 vector3d3 = currentPosition.subtract(vector3d);
                return vector3d2.dot(vector3d3) > 0.0D;
            }
        }
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new FlyNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        return createSmoothPathFinder(this.nodeEvaluator, maxVisitedNodes);
    }
}