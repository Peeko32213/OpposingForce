package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Skyvern;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class SkyvernRidingGoal extends Goal {

    private final Skyvern skyvern;

    public SkyvernRidingGoal(Skyvern skyvern) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.skyvern = skyvern;
    }

    @Override
    public boolean canUse() {
        return skyvern.isRidingMode();
    }

    @Override
    public void stop() {
    }

    @Override
    public void tick() {
        Player ridingPlayer = skyvern.getRidingPlayer();
        if (ridingPlayer != null) {
            this.skyvern.getNavigation().stop();
            Vec3 forwardsVec = new Vec3(ridingPlayer.xxa * 2.5F, 0, 10F).yRot((float) -Math.toRadians(skyvern.yBodyRot)).add(skyvern.position());
            this.skyvern.getMoveControl().setWantedPosition(forwardsVec.x, forwardsVec.y, forwardsVec.z, 3.0F);
            this.skyvern.setTargetPitch(this.skyvern.horizontalCollision ? -45.0F : 0.0F);
        }
    }
}