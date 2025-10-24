package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Dicer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

public class DicerAttackGoal extends AttackGoal {

    private final Dicer dicer;

    public DicerAttackGoal(Dicer dicer) {
        super(dicer);
        this.dicer = dicer;
    }

    @Override
    public void start() {
        super.start();
        this.dicer.setRunning(true);
        this.dicer.setCrossSlashing(false);
        this.dicer.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        super.stop();
        this.dicer.setRunning(false);
        this.dicer.setCrossSlashing(false);
        this.dicer.setPose(Pose.STANDING);
    }

    @Override
    public void tick() {
        LivingEntity target = this.dicer.getTarget();
        if (target != null) {
            double distance = this.dicer.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.dicer.getAttackState();
            this.dicer.lookAt(target, 30F, 30F);
            this.dicer.getLookControl().setLookAt(target, 30F, 30F);
            if (attackState == 1) {
                this.timer++;
                this.dicer.getNavigation().stop();
                if (this.timer == 7) {
                    this.dicer.addDeltaMovement(this.dicer.getLookAngle().scale(2.0D).multiply(0.2D, 0, 0.2D));
                }
                if (this.timer == 9) {
                    if (this.dicer.distanceTo(target) < this.getAttackReachSqr(target)) {
                        this.dicer.doHurtTarget(target);
                        this.dicer.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (this.timer >= 21) {
                    this.timer = 0;
                    this.dicer.setAttackState(0);
                }
            } else if (this.dicer.isCrossSlashing()) {
                this.timer++;
                this.dicer.getNavigation().stop();
                if (this.timer == 28) {
                    this.dicer.addDeltaMovement(this.dicer.getLookAngle().scale(3.0D).multiply(1.0D, 0, 1.0D));
                }
                if (this.timer == 29) {
                    if (this.dicer.distanceTo(target) < this.monster.getBbWidth() * 3.0F * this.monster.getBbWidth() * 3.0F + target.getBbWidth()) {
                        this.dicer.doHurtTarget(target);
                        this.dicer.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (this.timer > 50) {
                    this.timer = 0;
                    this.dicer.setCrossSlashing(false);
                }
            } else {
                this.dicer.getNavigation().moveTo(target, 2.0D);
                if (distance < this.monster.getBbWidth() * 3.0F * this.monster.getBbWidth() * 3.0F + target.getBbWidth()) {
                    if (this.dicer.getRandom().nextBoolean()) {
                        this.dicer.setCrossSlashing(true);
                    } else {
                        this.dicer.setAttackState(1);
                    }
                }
            }
        }
    }
}