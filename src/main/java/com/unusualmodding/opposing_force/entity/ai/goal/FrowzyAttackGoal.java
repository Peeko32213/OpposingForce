package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Frowzy;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class FrowzyAttackGoal extends AttackGoal {

    private final Frowzy frowzy;

    public FrowzyAttackGoal(Frowzy frowzy) {
        super(frowzy);
        this.frowzy = frowzy;
    }

    @Override
    public void tick() {
        LivingEntity target = this.frowzy.getTarget();
        if (target != null) {
            this.frowzy.getLookControl().setLookAt(target.getX(), target.getEyeY(), target.getZ());
            double distance = this.frowzy.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.frowzy.getAttackState();
            this.frowzy.getNavigation().moveTo(target, 1.0D);

            if (attackState == 1) {
                timer++;
                if (timer == 1) {
                    this.frowzy.playSound(OPSoundEvents.FROWZY_ATTACK.get(), 1.0F, this.frowzy.getVoicePitch());
                }
                if (timer == 5) {
                    if (this.frowzy.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                        this.frowzy.doHurtTarget(target);
                        this.frowzy.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (timer >= 20) {
                    timer = 0;
                    this.frowzy.setAttackState(0);
                }
            } else if (distance < getAttackReachSqr(target)) {
                this.frowzy.setAttackState(1);
            }
        }
    }
}