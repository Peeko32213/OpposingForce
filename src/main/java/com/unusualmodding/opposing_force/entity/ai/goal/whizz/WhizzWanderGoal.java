package com.unusualmodding.opposing_force.entity.ai.goal.whizz;

import com.unusualmodding.opposing_force.entity.Whizz;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class WhizzWanderGoal extends Goal {

    private final Whizz whizz;

    public WhizzWanderGoal(Whizz whizz) {
        this.whizz = whizz;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return whizz.getNavigation().isDone() && whizz.getRandom().nextInt(10) == 0 && !whizz.isInSittingPose();
    }

    @Override
    public boolean canContinueToUse() {
        return whizz.getNavigation().isInProgress() && !whizz.isInSittingPose();
    }

    @Override
    public void start() {
        Vec3 vec3d = this.getRandomLocation();
        if (vec3d != null) {
            whizz.getNavigation().moveTo(whizz.getNavigation().createPath(BlockPos.containing(vec3d), 1), 1.0);
        }
    }

    @Nullable
    private Vec3 getRandomLocation() {
        Vec3 vec3d2 = whizz.getViewVector(0.0F);
        Vec3 vec3d3 = HoverRandomPos.getPos(whizz, 16, 5, vec3d2.x, vec3d2.z, 1.5707964F, 3, 1);
        return vec3d3 != null ? vec3d3 : AirAndWaterRandomPos.getPos(whizz, 16, 2, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
    }
}