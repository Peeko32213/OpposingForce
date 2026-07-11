package com.barl_inc.opposing_force.entity.ai.goal.whizz;

import com.barl_inc.opposing_force.entity.Whizz;
import com.barl_inc.opposing_force.entity.ai.goal.AttackGoal;
import com.barl_inc.opposing_force.entity.utils.OPPoses;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

public class WhizzAttackGoal extends AttackGoal {

    private final Whizz whizz;

    public WhizzAttackGoal(Whizz whizz) {
        super(whizz);
        this.whizz = whizz;
    }

    @Override
    public void start() {
        super.start();
        this.whizz.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        super.stop();
        this.whizz.setPose(Pose.STANDING);
    }

    @Override
    public void tick() {
        LivingEntity target = this.whizz.getTarget();
        if (target != null) {
            double distance = this.whizz.distanceToSqr(target.getX(), target.getY(), target.getZ());
            this.whizz.getNavigation().moveTo(target.getX(), target.getEyeY() - 0.5D, target.getZ(), 1.25D);
            this.whizz.lookAt(target, 30F, 30F);
            this.whizz.getLookControl().setLookAt(target, 30F, 30F);

            if (this.whizz.isAttacking()) {
                this.timer++;
                if (this.timer == 1) this.whizz.setPose(OPPoses.ATTACKING.get());
                if (this.timer == 15) {
                    if (this.whizz.distanceTo(target) < this.getAttackReachSqr(target)) {
                        this.whizz.doHurtTarget(target);
                        this.whizz.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (this.timer == 24) {
                    this.timer = 0;
                    this.whizz.setAttacking(false);
                }
            } else {
                if (distance <= this.getAttackReachSqr(target)) {
                    this.whizz.setAttacking(true);
                }
            }
        }
    }
}