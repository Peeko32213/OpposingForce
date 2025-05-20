package com.unusualmodding.opposing_force.entity.ai.goal.volt;

import com.unusualmodding.opposing_force.entity.VoltEntity;
import com.unusualmodding.opposing_force.entity.projectile.ElectricBall;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class VoltAttackGoal extends Goal {

    protected final VoltEntity volt;
    private int attackTime = 0;
    private int retreatCooldown = 0;
    private float retreatYaw = 0;

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

            switch (attackState) {
                case 21 -> tickShootAttack();
                case 22 -> tickRetreat();
                default -> {
                    this.volt.getNavigation().moveTo(target, 0.5D);
                    this.retreatCooldown = Math.max(this.retreatCooldown - 1, 0);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }
    }

    protected void checkForCloseRangeAttack (double distance){
        if (volt.onGround() || volt.isInLava() || volt.isInWater() && distance >= 12) {
            volt.setAttackState(21);
        }
        else if (distance < 12 && this.retreatCooldown <= 0) {
            volt.setAttackState(22);
        }
    }

    protected void tickShootAttack () {
        attackTime++;
        LivingEntity target = volt.getTarget();
        volt.getNavigation().stop();

        if (attackTime == 10) {
            ElectricBall projectile = new ElectricBall(volt, volt.level(), volt.position().x(), volt.getEyePosition().y(), volt.position().z());
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

    public void tickRetreat() {
        attackTime++;
        LivingEntity target = volt.getTarget();
        volt.getNavigation().stop();
        if (attackTime == 1) {
            float targetAngle = -1;
            retreatYaw = (float) Math.toRadians(targetAngle + 90 + volt.getRandom().nextFloat() * 150 - 75);
        }
        if (attackTime == 2 && (volt.onGround())) {
            float speed = 1.6f;
            Vec3 m = volt.getDeltaMovement().add(speed * Math.cos(retreatYaw), 0, speed * Math.sin(retreatYaw));
            volt.setDeltaMovement(m.x, 0.85, m.z);
        }
        if (target != null) volt.getLookControl().setLookAt(target, 30, 30);
        if (attackTime >= 3) {
            attackTime = 0;
            this.volt.setAttackState(0);
            this.retreatCooldown = volt.getRandom().nextInt(5) + 10;
        }
    }
}