package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Terror;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class TerrorAttackGoal extends AttackGoal {

    protected final Terror terror;

    public TerrorAttackGoal(Terror terror) {
        super(terror);
        this.terror = terror;
    }

    @Override
    public void tick() {
        LivingEntity target = this.terror.getTarget();
        if (target != null) {
            double distance = this.terror.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.terror.getAttackState();
            this.terror.lookAt(this.terror.getTarget(), 30F, 30F);
            this.terror.getLookControl().setLookAt(this.terror.getTarget(), 30F, 30F);

            if (attackState == 1) {
                timer++;
                this.terror.getNavigation().stop();

                if (this.timer == 4) {
                    if (!this.terror.isInWater()) {
                        this.terror.addDeltaMovement(this.terror.getLookAngle().scale(2.0D).multiply(0.2D, 0.3D, 0.2D));
                    } else {
                        this.terror.addDeltaMovement(this.terror.getLookAngle().scale(2.0D).multiply(0.3D, 0.4D, 0.3D));
                    }
                }

                if (timer == 8) {
                    if (this.terror.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                        this.terror.doHurtTarget(target);
                        this.terror.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (timer >= 28) {
                    timer = 0;
                    this.terror.setAttackState(0);
                }
            } else {
                this.terror.getNavigation().moveTo(target, 1.0D);

                if (distance <= 20) {
                    this.terror.setAttackState(1);
                }
            }
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.terror.getBbWidth() * 1.5F * this.terror.getBbWidth() * 1.5F + target.getBbWidth();
    }
}