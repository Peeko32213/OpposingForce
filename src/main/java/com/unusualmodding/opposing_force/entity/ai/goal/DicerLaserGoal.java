package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Dicer;
import com.unusualmodding.opposing_force.entity.projectile.DicerLaser;
import com.unusualmodding.opposing_force.registry.OPEntities;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class DicerLaserGoal extends AttackGoal {

    private final Dicer dicer;
    private DicerLaser laser;

    public DicerLaserGoal(Dicer dicer) {
        super(dicer);
        this.dicer = dicer;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        if (this.laser != null) {
            this.laser.discard();
        }
    }

    @Override
    public void tick() {
        LivingEntity target = this.dicer.getTarget();
        if (target != null) {
            double distance = this.dicer.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.dicer.getAttackState();
            if (attackState == 3) {
                this.timer++;
                this.dicer.getNavigation().stop();
                this.dicer.getLookControl().setLookAt(target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(), 2.0F, 80);
                if (this.timer == 1) {
                    this.laser = new DicerLaser(OPEntities.DICER_LASER.get(), this.dicer.level(), this.dicer, this.dicer.getX() + 0.8F * Math.sin(-this.dicer.getYRot() * Math.PI / 180), this.dicer.getY() + 1.4F, this.dicer.getZ() + 0.8F * Math.cos(-this.dicer.getYRot() * Math.PI / 180), (float) ((this.dicer.yHeadRot + 90) * Math.PI / 180), (float) (-this.dicer.getXRot() * Math.PI / 180), 21, 4);
                    this.dicer.level().addFreshEntity(laser);
                }
                if (this.timer > 50) {
                    this.timer = 0;
                    this.dicer.setAttackState(0);
                    this.dicer.laserCooldown();
                }
            } else {
                this.dicer.getNavigation().moveTo(target, 2.0D);
                this.dicer.lookAt(target, 30F, 30F);
                this.dicer.getLookControl().setLookAt(target, 30F, 30F);
                if (this.dicer.getLaserCooldown() == 0 && distance > getAttackReachSqr(target) && distance < 100) {
                    this.dicer.setAttackState(3);
                } else if (distance < getAttackReachSqr(target)) {
                    if (this.dicer.getRandom().nextBoolean()) {
                        this.dicer.setAttackState(2);
                    } else {
                        this.dicer.setAttackState(1);
                    }
                }
            }
        }
    }
}