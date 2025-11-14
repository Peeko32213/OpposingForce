package com.unusualmodding.opposing_force.entity.ai.navigation;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;

public class SkyvernLookControl extends LookControl {

    public SkyvernLookControl(Mob entity) {
        super(entity);
    }

    @Override
    public void tick() {
        if (this.lookAtCooldown > 0 && this.getYRotD().isPresent()) {
            this.lookAtCooldown--;
            this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.getYRotD().get(), this.yMaxRotSpeed);
        } else {
            this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, 10.0F);
        }

        if (!this.mob.getNavigation().isDone()) {
            this.mob.yHeadRot = Mth.rotateIfNecessary(this.mob.yHeadRot, this.mob.yBodyRot, (float) this.mob.getMaxHeadYRot());
        }
    }
}