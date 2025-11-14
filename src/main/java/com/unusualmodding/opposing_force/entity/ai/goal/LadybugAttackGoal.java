package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Ladybug;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

public class LadybugAttackGoal extends AttackGoal {

    private final Ladybug ladybug;
    private int flightCooldown = 0;

    public LadybugAttackGoal(Ladybug ladybug) {
        super(ladybug);
        this.ladybug = ladybug;
    }

    @Override
    public void start() {
        super.start();
        this.ladybug.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        super.stop();
        this.ladybug.setPose(Pose.STANDING);
    }

    @Override
    public void tick() {
        LivingEntity target = this.ladybug.getTarget();
        if (target != null) {
            double distance = this.ladybug.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.ladybug.getAttackState();
            this.ladybug.lookAt(target, 30F, 30F);
            this.ladybug.getLookControl().setLookAt(target, 30F, 30F);
            if (this.flightCooldown > 0) flightCooldown--;
            if (!this.isWithinFlightRange(target) && this.flightCooldown == 0) {
                this.flightCooldown = 100;
                this.ladybug.setFlying(true);
            }
            if (attackState == 1) {
                this.timer++;
                this.ladybug.getNavigation().stop();
                if (this.timer == 1) this.ladybug.setPose(OPPoses.ATTACKING.get());
                if (this.timer == 10) this.ladybug.addDeltaMovement(this.ladybug.getLookAngle().scale(2.0D).multiply(0.34D, 0, 0.34D));
                if (this.timer == 13) {
                    if (this.ladybug.distanceTo(target) < this.getAttackReachSqr(target)) {
                        this.ladybug.doHurtTarget(target);
                        this.ladybug.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (this.timer > 24) {
                    this.timer = 0;
                    this.ladybug.setAttackState(0);
                }
            } else {
                this.ladybug.getNavigation().moveTo(target, 1.25D);
                if (distance < this.getAttackReachSqr(target)) {
                    this.ladybug.setAttackState(1);
                }
            }
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.monster.getBbWidth() * 1.5F * this.monster.getBbWidth() * 1.5F + target.getBbWidth();
    }

    public boolean isWithinFlightRange(LivingEntity target) {
        if (target == null) {
            return false;
        }
        return Math.abs(target.getY() - ladybug.getY()) < 2;
    }
}