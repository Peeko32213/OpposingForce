package com.unusualmodding.opposingforce.common.entity.custom.ai.goal.attack;

import com.unusualmodding.opposingforce.common.entity.custom.monster.VoltEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class VoltAttackGoal extends Goal {

    protected final VoltEntity volt;
    private int attackTime = 0;

    public VoltAttackGoal(VoltEntity mob) {
        this.volt = mob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return volt.getTarget() != null && volt.getTarget().isAlive();
    }

    public void start() {
        volt.setRunning(true);
        volt.setAttackState(0);
        this.attackTime = 0;
    }

    public void stop() {
        volt.setRunning(false);
        volt.setAttackState(0);
    }

    public void tick() {
        LivingEntity target = volt.getTarget();
        if (target != null) {

            volt.lookAt(volt.getTarget(), 30F, 30F);
            volt.getLookControl().setLookAt(volt.getTarget(), 30F, 30F);

            double distance = volt.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = volt.getAttackState();

            if (attackState == 21) {
                tickShootAttack();
            } else {
                this.volt.getNavigation().moveTo(target, 1.0D);
                this.checkForCloseRangeAttack(distance);
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        if (distance > 5) {
            volt.setAttackState(21);
        }
    }

    protected void tickShootAttack () {
        attackTime++;
        volt.setDeltaMovement(0, volt.getDeltaMovement().y, 0);

        if(attackTime == 10) {
        }
        if(attackTime >= 20) {
            attackTime =0;
            this.volt.setAttackState(0);
        }
    }
}