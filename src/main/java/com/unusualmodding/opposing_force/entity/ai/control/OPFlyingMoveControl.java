package com.unusualmodding.opposing_force.entity.ai.control;

import com.unusualmodding.opposing_force.entity.base.OPMonster;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class OPFlyingMoveControl extends MoveControl {

    private final OPMonster monster;

    public OPFlyingMoveControl(OPMonster monster) {
        super(monster);
        this.monster = monster;
    }

    @Override
    public void tick() {
        if (!monster.refuseToMove()) {
            if (this.operation == Operation.MOVE_TO) {
                Vec3 vector3d = new Vec3(this.wantedX - monster.getX(), this.wantedY - monster.getY(), this.wantedZ - monster.getZ());
                double d0 = vector3d.length();
                double width = monster.getBoundingBox().getSize();
                Vec3 vector3d1 = vector3d.scale(this.speedModifier * 0.05D / d0);
                this.monster.setDeltaMovement(monster.getDeltaMovement().add(vector3d1).scale(0.95D).add(0, -0.01, 0));
                if (d0 < width) {
                    this.operation = Operation.WAIT;
                } else if (d0 >= width) {
                    float yaw = -((float) Mth.atan2(vector3d1.x, vector3d1.z)) * (180F / (float) Math.PI);
                    this.monster.setYRot(Mth.approachDegrees(monster.getYRot(), yaw, 8));
                }
            }
        }
    }
}