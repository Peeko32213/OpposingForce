package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.UmberSpider;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class UmberSpiderPanicGoal extends Goal {

    protected final UmberSpider umberSpider;

    protected double posX;
    protected double posY;
    protected double posZ;

    public UmberSpiderPanicGoal(UmberSpider mob) {
        this.umberSpider = mob;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!this.shouldPanic() && this.umberSpider.fleeLightFor <= 0 && this.umberSpider.isAttacking() && !this.umberSpider.isOnFire()) {
            return false;
        } else {
            return this.findRandomPosition();
        }
    }

    protected boolean shouldPanic() {
        return !this.umberSpider.isVehicle() && this.umberSpider.getLastHurtByMob() != null;
    }

    protected boolean findRandomPosition() {
        Vec3 vec3 = LandRandomPos.getPos(this.umberSpider, 7, 4);
        if (vec3 == null) {
            return false;
        } else {
            this.posX = vec3.x;
            this.posY = vec3.y;
            this.posZ = vec3.z;
            return true;
        }
    }

    @Override
    public void start() {
        this.umberSpider.getNavigation().moveTo(this.posX, this.posY, this.posZ, 1.5F);
        this.umberSpider.setAttacking(false);
    }

    @Override
    public boolean canContinueToUse() {
        return !this.umberSpider.getNavigation().isDone();
    }
}