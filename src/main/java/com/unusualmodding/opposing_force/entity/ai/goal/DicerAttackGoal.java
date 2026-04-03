package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Dicer;
import com.unusualmodding.opposing_force.entity.projectile.DicerLaser;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class DicerAttackGoal extends AttackGoal {

    private final Dicer dicer;
    private DicerLaser laser;

    public DicerAttackGoal(Dicer dicer) {
        super(dicer);
        this.dicer = dicer;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && dicer.getPose() == Pose.STANDING;
    }

    @Override
    public void start() {
        super.start();
        this.dicer.setRunning(true);
        this.dicer.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        super.stop();
        this.dicer.setRunning(false);
        this.dicer.setPose(Pose.STANDING);
        if (laser != null) {
            this.laser.discard();
        }
    }

    @Override
    public void tick() {
        LivingEntity target = this.dicer.getTarget();
        if (target != null) {
            double distance = this.dicer.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.dicer.getAttackState();
            if (attackState != 3 && attackState != 2) {
                this.dicer.lookAt(target, 30F, 30F);
                this.dicer.getLookControl().setLookAt(target, 30F, 30F);
            }
            if (attackState == 1) {
                this.dicer.getNavigation().stop();
                this.tickSlash();
            } else if (attackState == 2) {
                this.dicer.getNavigation().stop();
                this.tickCrossSlash();
            } else if (attackState == 3) {
                this.dicer.getNavigation().stop();
                this.tickLaser();
            } else {
                this.dicer.getNavigation().moveTo(target, 2.0D);
                if (distance < 512 && dicer.laserCooldown == 0) {
                    this.dicer.setAttackState(3);
                } else if (distance < this.getAttackReachSqr(target)) {
                    if (this.dicer.getRandom().nextFloat() < 0.25F && dicer.crossSlashCooldown == 0) {
                        this.dicer.setAttackState(2);
                    }
                    else if (dicer.slashCooldown == 0) {
                        this.dicer.setAttackState(1);
                    }
                }
            }
        }
    }

    protected void tickSlash() {
        this.timer++;
        LivingEntity target = dicer.getTarget();
        if (timer == 1) {
            this.dicer.setPose(OPPoses.ATTACKING.get());
            this.dicer.slashAlt = dicer.getRandom().nextBoolean();
        }
        if (timer == 9) {
            if (this.isInAttackRange(target, 1.5D)) {
                this.dicer.doHurtTarget(target);
                this.dicer.swing(InteractionHand.MAIN_HAND);
            }
        }
        if (timer > 20) {
            this.dicer.setPose(Pose.STANDING);
            this.timer = 0;
            this.dicer.setAttackState(0);
            this.dicer.slashCooldown = 12;
        }
    }

    protected void tickCrossSlash() {
        this.timer++;
        LivingEntity target = dicer.getTarget();
        if (timer == 1) dicer.setPose(OPPoses.CROSS_SLASHING.get());
        if (timer < 21) {
            this.dicer.lookAt(target, 30F, 30F);
            this.dicer.getLookControl().setLookAt(target, 30F, 30F);
        }
        if (timer == 28) dicer.addDeltaMovement(dicer.getLookAngle().scale(3.25D).multiply(1.0D, 0, 1.0D));
        if (timer > 28 && timer < 32) {
            this.hurtNearbyEntities();
        }
        if (timer > 50) {
            this.dicer.setPose(Pose.STANDING);
            this.timer = 0;
            this.dicer.setAttackState(0);
            this.dicer.crossSlashCooldown = 80 + dicer.getRandom().nextInt(50);
        }
    }

    protected void tickLaser() {
        this.timer++;
        LivingEntity target = dicer.getTarget();
        if (timer == 1) dicer.setPose(OPPoses.LASERING.get());
        if (timer < 5) {
            this.dicer.lookAt(target, 30F, 30F);
            this.dicer.getLookControl().setLookAt(target, 30F, 30F);
            this.dicer.setYRot(dicer.yHeadRot);
            this.dicer.setYBodyRot(dicer.yHeadRot);
            this.dicer.yRotO = dicer.getYRot();
            this.dicer.yBodyRotO = dicer.getYRot();
        }

        if (timer > 5) {
            this.dicer.getLookControl().setLookAt(target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(), 1.0F, 90.0F);
            this.dicer.setYRot(this.dicer.yHeadRot);
            this.dicer.setYBodyRot(this.dicer.yHeadRot);
            this.dicer.yRotO = this.dicer.getYRot();
            this.dicer.yBodyRotO = this.dicer.getYRot();
        }

        if (timer == 10) {
            int damage = dicer.isElite() ? 5 : 4;
            this.laser = new DicerLaser(dicer.level(), dicer, dicer.getX(), dicer.getY() + 2.45F, dicer.getZ(), (float) ((dicer.yHeadRot + 90) * Math.PI / 180), (float) (-dicer.getXRot() * Math.PI / 180), 89, damage);
            if (dicer.isElite()) {
                this.laser.setFiery(true);
            }
            this.dicer.level().addFreshEntity(laser);
        }

        if (timer == 100) {
            this.laser.discard();
        }
        if (timer > 110) {
            this.dicer.setPose(Pose.STANDING);
            this.timer = 0;
            this.dicer.setAttackState(0);
            this.dicer.laserCooldown = 100 + dicer.getRandom().nextInt(100);
        }
    }

    private void hurtNearbyEntities() {
        List<LivingEntity> nearbyEntities = dicer.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), dicer, dicer.getBoundingBox().inflate(1.6d));
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.get(0);
            if (!(entity instanceof Dicer)) {
                if (entity.hurt(entity.damageSources().mobAttack(dicer), (float) dicer.getAttributeValue(Attributes.ATTACK_DAMAGE))) {
                    this.dicer.playSound(OPSoundEvents.DICER_ATTACK.get(), 1.0F, 1.0F / (dicer.getRandom().nextFloat() * 0.4F + 0.8F));
                }
                if (dicer.isElite()) {
                    entity.setSecondsOnFire(5);
                }
                entity.knockback(0.3F, dicer.position().x - entity.getX(), dicer.position().z - entity.getZ());
                if (entity.isDamageSourceBlocked(dicer.damageSources().mobAttack(dicer)) && entity instanceof Player player) {
                    player.disableShield(true);
                }
                this.dicer.swing(InteractionHand.MAIN_HAND);
            }
        }
    }
}