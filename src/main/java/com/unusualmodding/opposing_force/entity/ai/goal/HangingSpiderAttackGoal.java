package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.HangingSpider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class HangingSpiderAttackGoal extends AttackGoal {

    private final HangingSpider hangingSpider;

    public HangingSpiderAttackGoal(HangingSpider hangingSpider) {
        super(hangingSpider);
        this.hangingSpider = hangingSpider;
    }

    @Override
    public void tick() {
        LivingEntity target = this.hangingSpider.getTarget();
        if (this.timer > 0) {
            this.timer--;
        }
        if (target != null) {
            double distance = this.hangingSpider.distanceTo(target);
            if (this.hangingSpider.isUpsideDown()) {
                double d0 = this.hangingSpider.getX() - target.getX();
                double d2 = this.hangingSpider.getZ() - target.getZ();
                double xzDistSqr = d0 * d0 + d2 * d2;
//                BlockPos ceilingPos = new BlockPos((int) target.getX(), (int) (this.hangingSpider.getY() - 3 - hangingSpider.getRandom().nextInt(3)), (int) target.getZ());
//                BlockPos lowestPos = HangingSpider.getLowestPos(hangingSpider.level(), ceilingPos);
//                this.hangingSpider.getMoveControl().setWantedPosition(lowestPos.getX() + 0.5F, ceilingPos.getY(), lowestPos.getZ() + 0.5F, 1.1D);
                if (xzDistSqr < 2.5F) {
                    this.hangingSpider.setUpsideDown(false);
//                    this.hangingSpider.setGoingDown(true);
                }
            } else {
                if (this.hangingSpider.onGround()) {
                    this.hangingSpider.getNavigation().moveTo(target, 1.2D);
                }
            }
            if (distance < 1.6D && this.timer == 0) {
                this.hangingSpider.doHurtTarget(target);
                this.hangingSpider.swing(InteractionHand.MAIN_HAND);
                this.timer = 20;
            }
        }
    }
}