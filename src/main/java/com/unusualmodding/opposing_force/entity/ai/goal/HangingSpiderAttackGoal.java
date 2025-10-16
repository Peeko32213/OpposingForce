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
        return !hangingSpider.isGoingDown() && !hangingSpider.isGoingUp() && super.canUse();
    }

    @Override
    public void tick() {
        LivingEntity target = this.hangingSpider.getTarget();
        if (target != null) {
            if (hangingSpider.isUpsideDown()) {
                hangingSpider.setGoingDownCooldown(0);
            }
            double distance = this.hangingSpider.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = hangingSpider.getAttackState();
            if (!(this.hangingSpider.isUpsideDown() || this.hangingSpider.isGoingDown() || this.hangingSpider.isGoingUp())) {
                this.hangingSpider.getNavigation().moveTo(target, 1.0D);
            }
            this.hangingSpider.lookAt(Objects.requireNonNull(target), 30F, 30F);
            this.hangingSpider.getLookControl().setLookAt(target, 30F, 30F);

            if (attackState == 1) {
                this.timer++;
                if (this.timer == 13) {
                    if (this.hangingSpider.distanceTo(target) < this.getAttackReachSqr(target)) {
                        this.hangingSpider.doHurtTarget(target);
                        this.hangingSpider.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (this.timer >= 22) {
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

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.monster.getBbWidth() * 1.5F * this.monster.getBbWidth() * 1.5F + target.getBbWidth();
    }
}