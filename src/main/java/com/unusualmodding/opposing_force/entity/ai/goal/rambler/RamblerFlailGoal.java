package com.unusualmodding.opposing_force.entity.ai.goal.rambler;

import com.unusualmodding.opposing_force.entity.Rambler;
import com.unusualmodding.opposing_force.entity.ai.goal.AttackGoal;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class RamblerFlailGoal extends AttackGoal {

    private final Rambler rambler;

    public RamblerFlailGoal(Rambler rambler) {
        super(rambler);
        this.rambler = rambler;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.rambler.flailCooldown == 0 && this.rambler.getPose() == Pose.STANDING;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.rambler.flailCooldown == 0;
    }

    @Override
    public void start() {
        super.start();
        this.rambler.setFlailing(false);
        this.rambler.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        super.stop();
        this.rambler.setFlailing(false);
        this.rambler.setPose(Pose.STANDING);
        this.rambler.flailCooldown = 200 + this.rambler.getRandom().nextInt(200);
    }

    @Override
    public void tick() {
        LivingEntity target = this.rambler.getTarget();
        if (target != null) {
            this.rambler.lookAt(target, 30F, 30F);
            this.rambler.getLookControl().setLookAt(target, 30F, 30F);
            double distance = this.rambler.distanceToSqr(target.getX(), target.getY(), target.getZ());

            if (this.rambler.isFlailing()) {
                this.timer++;
                this.rambler.getNavigation().moveTo(target, 2.0D);

                if (this.timer == 1) this.rambler.setPose(OPPoses.START_FLAILING.get());

                if (this.timer > 1 && this.timer < 20) {
                    if (this.rambler.tickCount % 8 == 0) {
                        this.rambler.playSound(OPSoundEvents.RAMBLER_ATTACK.get(), 1.0F, 1.0F / (this.rambler.getRandom().nextFloat() * 0.4F + 0.8F));
                    }
                }

                if (this.timer > 20 && this.timer < 120) {
                    this.hurtNearbyEntities();
                    if (this.rambler.tickCount % 4 == 0) {
                        this.rambler.playSound(OPSoundEvents.RAMBLER_ATTACK.get(), 1.0F, 1.0F / (this.rambler.getRandom().nextFloat() * 0.4F + 0.8F));
                    }
                }

                if (this.timer == 120) this.rambler.setPose(OPPoses.STOP_FLAILING.get());

                if (this.timer > 120 && this.timer < 215) this.rambler.getNavigation().stop();

                if (this.timer > 215) {
                    this.timer = 0;
                    this.rambler.flailCooldown = 200 + this.rambler.getRandom().nextInt(200);
                    this.rambler.setFlailing(false);
                }
            } else {
                if (this.rambler.flailCooldown == 0) {
                    this.rambler.getNavigation().moveTo(target, 1.25D);
                    if (distance <= this.getAttackReachSqr(target)) {
                        this.rambler.setFlailing(true);
                    }
                } else {
                    this.rambler.getNavigation().stop();
                    this.rambler.setPose(Pose.STANDING);
                }
            }
        }
    }

    private void hurtNearbyEntities() {
        List<LivingEntity> nearbyEntities = rambler.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), rambler, rambler.getBoundingBox().inflate(1.25));
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.get(0);
            if (!(entity instanceof Rambler)) {
                entity.hurt(entity.damageSources().mobAttack(this.rambler), (float) this.rambler.getAttributeValue(Attributes.ATTACK_DAMAGE));
                entity.knockback((float) rambler.getAttribute(Attributes.ATTACK_KNOCKBACK).getValue(), rambler.position().x - entity.getX(), rambler.position().z - entity.getZ());
                if (entity.isDamageSourceBlocked(rambler.damageSources().mobAttack(rambler)) && entity instanceof Player player) {
                    player.disableShield(true);
                }
                this.rambler.swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.monster.getBbWidth() * 2.5F * this.monster.getBbWidth() * 2.5F + target.getBbWidth();
    }
}