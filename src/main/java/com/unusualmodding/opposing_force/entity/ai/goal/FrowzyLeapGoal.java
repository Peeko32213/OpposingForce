package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Frowzy;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class FrowzyLeapGoal extends Goal {

    private final Frowzy frowzy;

    public FrowzyLeapGoal(Frowzy frowzy) {
        this.frowzy = frowzy;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.LOOK));
    }

    @Override
    public void start() {
        LivingEntity target = this.frowzy.getTarget();
        if (target != null && this.frowzy.onGround()) {
            this.frowzy.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
            double distance = this.frowzy.distanceToSqr(target.getX(), target.getY(), target.getZ());
            if (distance > getAttackReachSqr(target) && this.frowzy.jumpCooldown <= 0) {
                this.frowzy.getNavigation().stop();
                Vec3 vec3 = this.frowzy.getDeltaMovement();
                Vec3 leapVec = new Vec3(target.getX() - this.frowzy.getX(), 0.0F, target.getZ() - this.frowzy.getZ());
                if (leapVec.lengthSqr() > 1.0E-7) {
                    leapVec = leapVec.normalize().scale(1).add(vec3.scale(0.75));
                }
                this.frowzy.setDeltaMovement(leapVec.x, 0.4D, leapVec.z);
            }
        }
    }

    @Override
    public void stop() {
        this.frowzy.jumpCooldown = 100 + this.frowzy.getRandom().nextInt(2 * 40);
    }

    @Override
    public boolean canUse() {
        return this.frowzy.getTarget() != null && this.frowzy.getTarget().isAlive() && this.frowzy.jumpCooldown <= 0;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.frowzy.onGround();
    }

    protected double getAttackReachSqr(LivingEntity target) {
        return this.frowzy.getBbWidth() * 4.0F * this.frowzy.getBbWidth() * 4.0F + target.getBbWidth();
    }
}