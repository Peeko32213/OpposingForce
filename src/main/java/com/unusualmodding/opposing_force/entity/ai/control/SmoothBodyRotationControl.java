package com.unusualmodding.opposing_force.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class SmoothBodyRotationControl extends BodyRotationControl {

    protected final Mob entity;
    protected final float bodyLagMoving;
    protected final float bodyLagStill;
    protected final float headLag;
    protected final float bodyMaxStill;
    protected final float bodyMaxMoving;
    protected final float headMax;

    public SmoothBodyRotationControl(Mob entity, float bodyLagMoving, float bodyMaxMoving, float bodyLagStill, float bodyMaxStill, float headLag, float headMax) {
        super(entity);
        this.entity = entity;
        this.bodyLagMoving = bodyLagMoving;
        this.bodyMaxMoving = bodyMaxMoving;
        this.bodyLagStill = bodyLagStill;
        this.bodyMaxStill = bodyMaxStill;
        this.headLag = headLag;
        this.headMax = headMax;
    }

    @Override
    public void clientTick() {
        if (this.isMoving()) {
            double dx = entity.getX() - entity.xo;
            double dz = entity.getZ() - entity.zo;
            float moveAngle = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0F);
            this.entity.yBodyRot = this.approachAngle(entity.yBodyRot, moveAngle, bodyLagMoving, bodyMaxMoving);
            this.entity.yHeadRot = this.approachAngle(entity.yHeadRot, moveAngle, headLag, headMax);
            this.clampHeadBodyDifference();
        } else {
            this.entity.yBodyRot = this.approachAngle(entity.yBodyRot, entity.yHeadRot, bodyLagStill, bodyMaxStill);
            this.clampHeadBodyDifference();
        }
    }

    protected void clampHeadBodyDifference() {
        float diff = Mth.wrapDegrees(entity.yHeadRot - entity.yBodyRot);
        float clamped = Mth.clamp(diff, -headMax, headMax);
        this.entity.yHeadRot = entity.yBodyRot + clamped;
    }

    protected float approachAngle(float current, float target, float factor, float maxDelta) {
        float d = Mth.wrapDegrees(target - current);
        if (d < -maxDelta) {
            d = -maxDelta;
        } else if (d > maxDelta) {
            d = maxDelta;
        }
        return current + d * factor;
    }

    protected boolean isMoving() {
        double dx = entity.getX() - entity.xo;
        double dz = entity.getZ() - entity.zo;
        return dx * dx + dz * dz > (double) 2.5000003E-7F;
    }
}
