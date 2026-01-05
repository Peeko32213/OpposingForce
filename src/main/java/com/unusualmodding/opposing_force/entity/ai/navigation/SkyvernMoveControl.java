package com.unusualmodding.opposing_force.entity.ai.navigation;

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
        if (this.operation == Operation.MOVE_TO) {
            final Vec3 vector3d = new Vec3(this.wantedX - mob.getX(), this.wantedY - mob.getY(), this.wantedZ - mob.getZ());
            final double length = vector3d.length();
            if (length < mob.getBoundingBox().getSize()) {
                this.operation = Operation.WAIT;
                this.mob.setDeltaMovement(mob.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.07D / length).multiply(0.9F, 0.9F, 0.9F)));
            } else {
                this.mob.setDeltaMovement(mob.getDeltaMovement().add(vector3d.scale(this.speedModifier * 0.08D / length).multiply(0.9F, 0.9F, 0.9F)));
                final Vec3 vector3d1 = mob.getDeltaMovement();
                float f = -((float) Mth.atan2(vector3d1.x, vector3d1.z)) * 180.0F / (float) Math.PI;
                this.mob.setYRot(Mth.approachDegrees(mob.getYRot(), f, 6));
                this.mob.yBodyRot = mob.getYRot();
            }
        }
    }
}