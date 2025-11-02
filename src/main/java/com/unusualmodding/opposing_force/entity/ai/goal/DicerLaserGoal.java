package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Dicer;
import com.unusualmodding.opposing_force.entity.projectile.DicerLaser;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

public class DicerLaserGoal extends AttackGoal {

    private final Dicer dicer;
    private DicerLaser laser;

    public DicerLaserGoal(Dicer dicer) {
        super(dicer);
        this.dicer = dicer;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.dicer.laserCooldown == 0 && this.dicer.getPose() == Pose.STANDING;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.dicer.laserCooldown == 0;
    }

    @Override
    public void start() {
        super.start();
        this.dicer.setLasering(false);
        this.dicer.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        super.stop();
        this.dicer.setLasering(false);
        this.dicer.setPose(Pose.STANDING);
        this.dicer.laserCooldown = 100 + this.dicer.getRandom().nextInt(100);
        if (this.laser != null) {
            this.laser.discard();
        }
    }

    @Override
    public void tick() {
        LivingEntity target = this.dicer.getTarget();
        if (target != null) {
            double distance = this.dicer.distanceToSqr(target.getX(), target.getY(), target.getZ());
            if (this.dicer.isLasering()) {
                this.timer++;
                this.dicer.getNavigation().stop();
                if (this.timer < 5) {
                    this.dicer.lookAt(this.dicer.getTarget(), 30F, 30F);
                    this.dicer.getLookControl().setLookAt(this.dicer.getTarget(), 30F, 30F);
                    this.dicer.setYRot(this.dicer.yHeadRot);
                    this.dicer.setYBodyRot(this.dicer.yHeadRot);
                    this.dicer.yRotO = this.dicer.getYRot();
                    this.dicer.yBodyRotO = this.dicer.getYRot();
                }

                if (this.timer > 5) {
                    this.dicer.getLookControl().setLookAt(target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(), 1.5F, 90.0F);
                    this.dicer.setYRot(this.dicer.yHeadRot);
                    this.dicer.setYBodyRot(this.dicer.yHeadRot);
                    this.dicer.yRotO = this.dicer.getYRot();
                    this.dicer.yBodyRotO = this.dicer.getYRot();
                }

                if (this.timer == 10) {
                    this.dicer.setPose(OPPoses.LASERING.get());
                    this.laser = new DicerLaser(dicer.level(), dicer, dicer.getX(), dicer.getY() + 2.45F, dicer.getZ(), (float) ((dicer.yHeadRot + 90) * Math.PI / 180), (float) (-dicer.getXRot() * Math.PI / 180), 89, 4);
                    this.dicer.level().addFreshEntity(laser);
                }

                if (this.timer == 100) {
                    this.laser.discard();
                }
                if (this.timer > 110) {
                    this.timer = 0;
                    this.dicer.setAttackState(0);
                    this.dicer.laserCooldown = 100 + this.dicer.getRandom().nextInt(100);
                }
            } else {
                this.dicer.lookAt(target, 30F, 30F);
                this.dicer.getLookControl().setLookAt(target, 30F, 30F);
                if (distance < 512) {
                    this.dicer.setLasering(true);
                }
            }
        }
    }
}