package com.unusualmodding.opposing_force.entity.ai.control;

import com.unusualmodding.opposing_force.entity.Skyvern;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class SkyvernMoveControl extends MoveControl {

    public SkyvernMoveControl(Skyvern entity) {
        super(entity);
    }

    @Override
    public void tick() {
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            Vec3 vector3d = new Vec3(this.wantedX - mob.getX(), this.wantedY - mob.getY(), this.wantedZ - mob.getZ());
            double d0 = vector3d.length();
            double width = mob.getBoundingBox().getSize();
            Vec3 vector3d1 = vector3d.scale(this.speedModifier * 0.1D / d0);
            this.mob.setDeltaMovement(mob.getDeltaMovement().add(vector3d1).scale(0.9D));
            if (d0 < width) {
                this.operation = Operation.WAIT;
            } else if (d0 >= width) {
                float yaw = -((float) Mth.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                this.mob.setYRot(Mth.approachDegrees(mob.getYRot(), yaw, 8));
            }
        }
    }
}