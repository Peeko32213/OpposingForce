package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Nymph;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

import java.util.Objects;

public class NymphAttackGoal extends AttackGoal {

    private final Nymph nymph;

    public NymphAttackGoal(Nymph nymph) {
        super(nymph);
        this.nymph = nymph;
    }

    @Override
    public void start() {
        super.start();
        this.nymph.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        super.stop();
        this.nymph.setPose(Pose.STANDING);
    }

    @Override
    public void tick() {
        LivingEntity target = this.nymph.getTarget();
        if (target != null) {
            this.nymph.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
            double distance = this.nymph.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.nymph.getAttackState();
            this.nymph.getNavigation().moveTo(target, 1.3D);

            if (attackState == 1) {
                timer++;
                this.nymph.getNavigation().stop();
                if (timer == 1) this.nymph.setPose(OPPoses.ATTACKING.get());
                if (timer == 12) {
                    if (this.nymph.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                        this.nymph.doHurtTarget(target);
                        this.nymph.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (timer > 25) {
                    timer = 0;
                    this.nymph.setAttackState(0);
                }
            } else if (distance < getAttackReachSqr(target)) {
                this.nymph.setAttackState(1);
            }
        }
    }
}