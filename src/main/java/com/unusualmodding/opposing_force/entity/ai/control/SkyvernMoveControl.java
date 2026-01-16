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
        if (this.operation == Operation.MOVE_TO) {
            final Vec3 vector3d = new Vec3(this.wantedX - mob.getX(), this.wantedY - mob.getY(), this.wantedZ - mob.getZ());
            final double length = vector3d.length();
            Vec3 vec3 = vector3d.scale(this.speedModifier * 0.15F / length);
            if (length < mob.getBoundingBox().getSize()) {
                this.operation = Operation.WAIT;
                this.mob.setDeltaMovement(mob.getDeltaMovement().add(vec3).scale(0.7F));
            } else {
                final Vec3 deltaMovement = mob.getDeltaMovement();
                this.mob.setDeltaMovement(mob.getDeltaMovement().add(vec3).scale(0.9F));
                float f = -((float) Mth.atan2(deltaMovement.x, deltaMovement.z)) * 180.0F / (float) Math.PI;
                this.mob.setYRot(Mth.approachDegrees(mob.getYRot(), f, 20));
                this.mob.yBodyRot = mob.getYRot();
            }
        }
    }
}