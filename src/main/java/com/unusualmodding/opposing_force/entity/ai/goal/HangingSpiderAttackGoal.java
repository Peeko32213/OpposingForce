package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.HangingSpider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class HangingSpiderAttackGoal extends AttackGoal {

    private final HangingSpider hangingSpider;

    public HangingSpiderAttackGoal(HangingSpider hangingSpider) {
        super(hangingSpider);
        this.hangingSpider = hangingSpider;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !this.hangingSpider.isGoingUp() && !this.hangingSpider.isGoingDown();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !this.hangingSpider.isGoingUp() && !this.hangingSpider.isGoingDown();
    }

    @Override
    public void tick() {
        LivingEntity target = this.hangingSpider.getTarget();
        if (target != null) {
            this.hangingSpider.lookAt(Objects.requireNonNull(target), 30F, 30F);
            this.hangingSpider.getLookControl().setLookAt(target, 30F, 30F);

            double distance = this.hangingSpider.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.hangingSpider.getAttackState();

            this.hangingSpider.getNavigation().moveTo(target, 1.2F);

            if (attackState == 1) {
                this.timer++;
                if (this.timer == 4) {
                    if (this.hangingSpider.distanceTo(target) < this.getAttackReachSqr(target)) {
                        this.hangingSpider.doHurtTarget(target);
                        this.hangingSpider.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (this.timer >= 20) {
                    this.timer = 0;
                    this.hangingSpider.setAttackState(0);
                }
            } else {
                if (distance <= this.getAttackReachSqr(target)) {
                    this.hangingSpider.setAttackState(1);
                }
            }
        }
    }
}