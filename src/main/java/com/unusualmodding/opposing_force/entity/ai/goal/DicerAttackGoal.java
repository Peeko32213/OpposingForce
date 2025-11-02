package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Dicer;
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

    public DicerAttackGoal(Dicer dicer) {
        super(dicer);
        this.dicer = dicer;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.dicer.laserCooldown > 0 && this.dicer.getPose() == Pose.STANDING;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.dicer.laserCooldown > 0;
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
    }

    @Override
    public void tick() {
        LivingEntity target = this.dicer.getTarget();
        if (target != null) {
            double distance = this.dicer.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.dicer.getAttackState();
            this.dicer.lookAt(target, 30F, 30F);
            this.dicer.getLookControl().setLookAt(target, 30F, 30F);
            if (attackState == 1) {
                this.timer++;
                this.dicer.getNavigation().stop();
                if (this.timer == 1) this.dicer.setPose(OPPoses.SLASHING.get());
                if (this.timer == 9) {
                    if (this.dicer.distanceTo(target) < this.getAttackReachSqr(target)) {
                        this.dicer.doHurtTarget(target);
                        this.dicer.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (this.timer > 21) {
                    this.timer = 0;
                    this.dicer.setAttackState(0);
                }
            } else if (attackState == 2) {
                this.timer++;
                this.dicer.getNavigation().moveTo(target, 2.0D);
                if (this.timer == 1) this.dicer.setPose(OPPoses.TAIL_SPINNING.get());
                if (this.timer == 8) {
                    if (this.dicer.distanceTo(target) < this.getAttackReachSqr(target)) {
                        this.dicer.doHurtTarget(target);
                        this.dicer.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (this.timer > 20) {
                    this.timer = 0;
                    this.dicer.setAttackState(0);
                }
            } else if (attackState == 3) {
                this.timer++;
                this.dicer.getNavigation().stop();
                if (this.timer == 1) this.dicer.setPose(OPPoses.CROSS_SLASHING.get());
                if (this.timer == 28) this.dicer.addDeltaMovement(this.dicer.getLookAngle().scale(2.75D).multiply(1.0D, 0, 1.0D));
                if (this.timer > 28 && this.timer < 32) {
                    this.hurtNearbyEntities();
                }
                if (this.timer > 50) {
                    this.timer = 0;
                    this.dicer.setAttackState(0);
                }
            } else {
                this.dicer.getNavigation().moveTo(target, 2.0D);
                if (distance < this.monster.getBbWidth() * 3.0F * this.monster.getBbWidth() * 3.0F + target.getBbWidth()) {
                    if (this.dicer.getRandom().nextFloat() < 0.2F) {
                        this.dicer.setAttackState(3);
                    }
                    else if (this.dicer.getRandom().nextFloat() < 0.4F) {
                        this.dicer.setAttackState(2);
                    }
                    else {
                        this.dicer.setAttackState(1);
                    }
                }
            }
        }
    }

    private void hurtNearbyEntities() {
        List<LivingEntity> nearbyEntities = dicer.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), dicer, dicer.getBoundingBox().inflate(2.0));
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.get(0);
            if (!(entity instanceof Dicer)) {
                if (entity.hurt(entity.damageSources().mobAttack(this.dicer), (float) this.dicer.getAttributeValue(Attributes.ATTACK_DAMAGE))) {
                    this.dicer.playSound(OPSoundEvents.DICER_ATTACK.get(), 1.0F, 1.0F / (this.dicer.getRandom().nextFloat() * 0.4F + 0.8F));
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