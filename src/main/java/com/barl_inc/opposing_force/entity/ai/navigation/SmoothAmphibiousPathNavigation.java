package com.barl_inc.opposing_force.entity.ai.navigation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
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

public class SmoothAmphibiousPathNavigation extends AmphibiousPathNavigation implements ExtendedNavigator {

    public SmoothAmphibiousPathNavigation(Mob mob, Level level) {
        super(mob, level);
    }

    /**
     * Determine whether the navigator should prefer shallow swimming patterns
     * <p>
     * Adjusts path node penalty when determining paths
     */
    public boolean prefersShallowSwimming() {
        return false;
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
        this.nodeEvaluator = new AmphibiousNodeEvaluator(prefersShallowSwimming());
        this.nodeEvaluator.setCanPassDoors(true);
        return createSmoothPathFinder(this.nodeEvaluator, maxVisitedNodes);
    }
}