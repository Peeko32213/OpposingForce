package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Tart;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

import java.util.Objects;

public class TartAttackGoal extends AttackGoal {

    private final Tart tart;

    public TartAttackGoal(Tart tart) {
        super(tart);
        this.tart = tart;
    }

    @Override
    public void start() {
        super.start();
        this.tart.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        super.stop();
        this.tart.setPose(Pose.STANDING);
    }

    @Override
    public void tick() {
        LivingEntity target = this.tart.getTarget();
        if (target != null) {
            this.tart.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
            double distance = this.tart.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.tart.getAttackState();
            this.tart.getNavigation().moveTo(target, 1.4D);

            if (attackState == 1) {
                timer++;
                this.tart.getNavigation().stop();
                if (timer == 1) {
                    this.tart.setPose(OPPoses.ATTACKING.get());
                    this.tart.playSound(OPSoundEvents.TART_ATTACK.get(), 1.0F, 0.9F + tart.getRandom().nextFloat() * 0.2F);
                }
                if (timer == 3) {
                    this.tart.addDeltaMovement(this.tart.getLookAngle().scale(1.0D).multiply(0.3D, 0, 0.3D));
                }
                if (timer == 5) {
                    if (this.tart.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                        this.tart.doHurtTarget(target);
                        this.tart.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (timer > 40) {
                    timer = 0;
                    this.tart.setAttackState(0);
                }
            } else if (distance < getAttackReachSqr(target)) {
                this.tart.setAttackState(1);
            }
        }
    }
}