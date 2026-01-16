package com.unusualmodding.opposing_force.entity.ai.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://mozilla.org/MPL/2.0/.
 *
 * Source: SmartBrainLib - https://github.com/Tslat/SmartBrainLib/tree/1.20
 * Modifications by: Opposing Force - 1/16/2026
 */

public class SmoothWaterBoundPathNavigation extends WaterBoundPathNavigation implements ExtendedNavigator {

    private final boolean canBreach;

    public SmoothWaterBoundPathNavigation(Mob mob, Level level, boolean canBreach) {
        super(mob, level);
        this.canBreach = canBreach;
    }

    /**
     * Determine whether the entity can breach the surface as part of its pathing
     * <p>
     * Defaults to false for non-dolphins
     */
    public boolean canBreach() {
        return this.canBreach;
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

    /**
     * Patch {@link Path#getEntityPosAtNode} to use a proper rounding check
     */
    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        this.nodeEvaluator = new SwimNodeEvaluator(this.allowBreaching = this.canBreach());
        this.nodeEvaluator.setCanPassDoors(true);
        return createSmoothPathFinder(this.nodeEvaluator, maxVisitedNodes);
    }
}