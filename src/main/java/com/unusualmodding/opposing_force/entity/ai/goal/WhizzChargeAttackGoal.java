package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Whizz;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class WhizzChargeAttackGoal extends AttackGoal {

    private final Whizz whizz;

    public WhizzChargeAttackGoal(Whizz whizz) {
        super(whizz);
        this.whizz = whizz;
    }

    @Override
    public void start() {
        super.start();
        this.whizz.setCharging(false);
    }

    @Override
    public void stop() {
        super.stop();
        this.whizz.setCharging(false);
    }

    @Override
    public void tick() {
        LivingEntity target = this.whizz.getTarget();
        if (target != null) {
            double distance = this.whizz.distanceToSqr(target.getX(), target.getY(), target.getZ());
            if (this.whizz.isCharging()) {
                this.timer++;
                this.whizz.getNavigation().stop();

                if (this.timer == 20) {
                    Vec3 vec3 = this.whizz.getDeltaMovement();
                    Vec3 chargeVec = new Vec3(target.getX() - this.whizz.getX(), 0.0F, target.getZ() - this.whizz.getZ());
                    if (chargeVec.lengthSqr() > 1.0E-7) {
                        chargeVec = chargeVec.normalize().scale(1).add(vec3.scale(0.75));
                    }
                    this.whizz.lookAt(target, 30F, 30F);
                    this.whizz.getLookControl().setLookAt(target, 30F, 30F);
                    this.whizz.setDeltaMovement(chargeVec.x, 0.0D, chargeVec.z);
                }

                if (this.timer > 20 && this.timer < 60) {
                    if (this.whizz.distanceTo(target) < getAttackReachSqr(target)) {
                        this.whizz.doHurtTarget(target);
                        this.whizz.swing(InteractionHand.MAIN_HAND);
                    }
                }

                if (this.timer > 59) {
                    this.timer = 0;
                    this.whizz.setCharging(false);
                    this.whizz.chargeCooldown();
                }
            } else {
                if (distance < 16 && this.whizz.getChargeCooldown() <= 0) {
                    this.whizz.setCharging(true);
                } else {
                    this.whizz.getNavigation().moveTo(target, 0.75D);
                    this.whizz.lookAt(target, 30F, 30F);
                    this.whizz.getLookControl().setLookAt(target, 30F, 30F);
                }
            }
        }
    }
}