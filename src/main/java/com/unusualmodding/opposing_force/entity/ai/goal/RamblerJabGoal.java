package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Rambler;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

import java.util.Objects;

public class RamblerJabGoal extends AttackGoal {

    private final Rambler rambler;

    public RamblerJabGoal(Rambler rambler) {
        super(rambler);
        this.rambler = rambler;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.rambler.flailCooldown > 0;
    }

    @Override
    public void start() {
        super.start();
        this.rambler.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        super.stop();
        this.rambler.setPose(Pose.STANDING);
    }

    @Override
    public void tick() {
        LivingEntity target = this.rambler.getTarget();
        if (target != null) {
            int attackState = this.rambler.getAttackState();
            double distance = this.rambler.distanceToSqr(target.getX(), target.getY(), target.getZ());
            this.rambler.getNavigation().moveTo(target, 1.25D);
            this.rambler.lookAt(Objects.requireNonNull(target), 30F, 30F);
            this.rambler.getLookControl().setLookAt(target, 30F, 30F);

            if (this.rambler.getAttackState() == 1) {
                this.timer++;
                if (this.timer == 1) {
                    this.rambler.setPose(OPPoses.JAB.get());
                }
                if (this.timer == 7) {
                    if (this.rambler.distanceTo(target) < this.getAttackReachSqr(target)) {
                        this.rambler.doHurtTarget(target);
                        this.rambler.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (this.timer == 40) {
                    this.timer = 0;
                    this.rambler.setAttackState(0);
                }
            } else {
                if (distance <= this.getAttackReachSqr(target) && attackState == 0) {
                    this.rambler.setAttackState(1);
                }
            }
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.monster.getBbWidth() * 1.5F * this.monster.getBbWidth() * 1.5F + target.getBbWidth();
    }
}