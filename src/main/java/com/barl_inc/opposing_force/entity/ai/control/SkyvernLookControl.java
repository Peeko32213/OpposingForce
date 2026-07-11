package com.barl_inc.opposing_force.entity.ai.control;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;

public class SkyvernLookControl extends LookControl {

    public SkyvernLookControl(Mob entity) {
        super(entity);
    }

    @Override
    public void tick() {
        if (this.lookAtCooldown > 0) {
            this.lookAtCooldown--;
            this.getYRotD().ifPresent(f -> this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, f + 20.0F, this.yMaxRotSpeed));
            this.getXRotD().ifPresent(f -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), f + 1.0F, this.xMaxRotAngle)));
        } else {
            if (this.mob.getNavigation().isDone()) {
                this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), 0.0F, 5.0F));
            }
            this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, this.yMaxRotSpeed);
        }
        float f = Mth.wrapDegrees(this.mob.yHeadRot - this.mob.yBodyRot);
        if (f < -20) {
            this.mob.yBodyRot -= 4.0F;
        } else if (f > 20) {
            this.mob.yBodyRot += 4.0F;
        }
    }
}