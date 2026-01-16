package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Ladybug;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

public class LadybugAttackGoal extends AttackGoal {

    private final Ladybug ladybug;

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
            if (!this.isWithinFlightRange(target) && ladybug.flightCooldown == 0) {
                this.ladybug.flightCooldown = 100;
                this.ladybug.setFlying(true);
            }
            if (attackState == 1) {
                this.timer++;
                this.ladybug.getNavigation().stop();
                if (this.timer == 1) this.ladybug.setPose(OPPoses.ATTACKING.get());
                if (this.timer < 16) {
                    this.ladybug.lookAt(target, 30F, 30F);
                    this.ladybug.getLookControl().setLookAt(target, 30F, 30F);
                }
                if (this.timer == 20) this.ladybug.addDeltaMovement(this.ladybug.getLookAngle().scale(2.0D).multiply(0.45D, 0, 0.45D));
                if (this.timer == 23) {
                    if (this.isInAttackRange(target, 1)) {
                        this.ladybug.doHurtTarget(target);
                        this.ladybug.strongKnockback(target, 0.7D, 0.01D);
                        this.ladybug.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (this.timer > 33) {
                    this.timer = 0;
                    this.ladybug.setAttackState(0);
                }
            } else {
                this.ladybug.lookAt(target, 30F, 30F);
                this.ladybug.getLookControl().setLookAt(target, 30F, 30F);
                this.ladybug.getNavigation().moveTo(target, ladybug.isFlying() ? 1.4D : 1.3D);
                if (distance < this.getAttackReachSqr(target)) {
                    this.ladybug.setAttackState(1);
                }
            }
        }
    }

    public boolean isWithinFlightRange(LivingEntity target) {
        if (target == null) {
            return false;
        }
        return Math.abs(target.getY() - ladybug.getY()) < 2;
    }
}