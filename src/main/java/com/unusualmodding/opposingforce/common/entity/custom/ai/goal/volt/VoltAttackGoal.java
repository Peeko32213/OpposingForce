package com.unusualmodding.opposingforce.common.entity.custom.ai.goal.volt;

import com.unusualmodding.opposingforce.common.entity.custom.monster.VoltEntity;
import com.unusualmodding.opposingforce.common.entity.custom.projectile.ElectricBall;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class VoltAttackGoal extends Goal {

    protected final VoltEntity volt;
    private int attackTime = 0;
    private int retreatCooldown = 0;

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
        this.retreatCooldown = 0;
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
            }
            else {
                this.retreatCooldown = Math.max(this.retreatCooldown - 1, 0);
                this.volt.getNavigation().moveTo(target, 0.5D);
                this.checkForCloseRangeAttack(distance);
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        if (volt.onGround()) {
            volt.setAttackState(21);
        }
        else if (distance < 40 && this.retreatCooldown <= 0 && volt.onGround()) {
            this.retreat();
        }
    }

    protected void tickShootAttack () {
        attackTime++;
        LivingEntity target = volt.getTarget();
        volt.setDeltaMovement(0, volt.getDeltaMovement().y, 0);

        if (attackTime == 10) {
            ElectricBall projectile = new ElectricBall(volt, volt.level());
            double tx = target.getX() - volt.getX();
            double ty = target.getY() + target.getEyeHeight() - 1.1D - projectile.getY();
            double tz = target.getZ() - volt.getZ();
            float heightOffset = Mth.sqrt((float) (tx * tx + tz * tz)) * 0.01F;
            projectile.shoot(tx, ty + heightOffset, tz, 0.6F, 1.0F);
            this.volt.level().addFreshEntity(projectile);
        }
        if (attackTime >= 24) {
            attackTime =0;
            this.volt.setAttackState(0);
        }
    }

    public void retreat() {
        Vec3 diff = new Vec3(volt.getTarget().getX() - volt.getX(), (volt.getTarget().getY() - volt.getY()) + 4.25, volt.getTarget().getZ() -volt.getZ());
        Vec3 vel = diff.multiply(0.5D,0.4D, 0.5D).add(0,0.45,0).normalize();
        volt.setDeltaMovement(vel);
        this.retreatCooldown = volt.getRandom().nextInt(5) + 5;
    }
}