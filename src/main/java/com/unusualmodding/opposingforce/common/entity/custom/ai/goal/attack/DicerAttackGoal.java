package com.unusualmodding.opposingforce.common.entity.custom.ai.goal.attack;

import com.unusualmodding.opposingforce.common.entity.custom.monster.DicerEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class DicerAttackGoal extends Goal {

    protected final DicerEntity dicer;
    private int attackTime = 0;

    public DicerAttackGoal(DicerEntity mob) {
        this.dicer = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return dicer.getTarget() != null && dicer.getTarget().isAlive();
    }

    public void start() {
        dicer.setRunning(true);
        dicer.setAttackState(0);
        this.attackTime = 0;
    }

    public void stop() {
        dicer.setRunning(false);
        dicer.setAttackState(0);
    }

    public void tick() {
        LivingEntity target = dicer.getTarget();
        if (target != null) {

            dicer.lookAt(dicer.getTarget(), 30F, 30F);
            dicer.getLookControl().setLookAt(dicer.getTarget(), 30F, 30F);

            double distance = dicer.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = dicer.getAttackState();

            switch (attackState) {
                case 21, 22 -> tickTailStabAttack();
                case 23 -> tickClawAttack();
                default -> {
                    this.dicer.getNavigation().moveTo(target, 2.25D);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        int r = (dicer.getRandom().nextInt(100) + 1);
        if (distance <= 5) {
            if (r <= 40) {
                dicer.setAttackState(21);
            }
            else if (r <= 80) {
                dicer.setAttackState(21);
            }
            else {
                dicer.setAttackState(23);
            }
        }
        else if (distance <= 7) {
            if (r <= 20) {
                dicer.setAttackState(21);
            }
            else if (r <= 40) {
                dicer.setAttackState(21);
            }
            else {
                dicer.setAttackState(23);
            }
        }
    }

    protected void tickTailStabAttack () {
        attackTime++;
        dicer.setDeltaMovement(0, dicer.getDeltaMovement().y, 0);

        if(attackTime == 10) {
            if (dicer.distanceTo(this.dicer.getTarget()) < 2.5f) {
                dicer.doHurtTarget(dicer.getTarget());
            }
        }
        if(attackTime >= 20) {
            attackTime =0;
            this.dicer.setAttackState(0);
        }
    }

    protected void tickClawAttack () {
        attackTime++;
        dicer.setDeltaMovement(0, dicer.getDeltaMovement().y, 0);

        if(attackTime == 9) {
            if (dicer.distanceTo(this.dicer.getTarget()) < 2.5f) {
                dicer.doHurtTarget(dicer.getTarget());
            }
        }
        if(attackTime >= 14) {
            attackTime =0;
            this.dicer.setAttackState(0);
        }
    }
}