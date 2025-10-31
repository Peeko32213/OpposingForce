package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Rambler;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class RamblerRollGoal extends AttackGoal {

    private final Rambler rambler;
    private int collisionTicks;

    public RamblerRollGoal(Rambler rambler) {
        super(rambler);
        this.rambler = rambler;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.rambler.rollCooldown == 0 && this.rambler.getPose() == Pose.STANDING;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.rambler.rollCooldown == 0;
    }

    @Override
    public void start() {
        super.start();
        this.collisionTicks = 0;
        this.rambler.setRolling(false);
        this.rambler.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        super.stop();
        this.collisionTicks = 0;
        this.rambler.setRolling(false);
        this.rambler.setPose(Pose.STANDING);
        this.rambler.rollCooldown = 200 + this.rambler.getRandom().nextInt(200);
    }

    @Override
    public void tick() {
        LivingEntity target = this.rambler.getTarget();
        if (target != null) {
            double distance = this.rambler.distanceToSqr(target.getX(), target.getY(), target.getZ());

            if (this.rambler.isRolling()) {
                this.timer++;
                if (rambler.horizontalCollision) {
                    this.collisionTicks++;
                } else if (this.collisionTicks > 0) {
                    this.collisionTicks--;
                }

                if (this.timer == 1) this.rambler.setPose(OPPoses.START_ROLLING.get());

                if (this.timer > 20 && this.timer < 200) this.hurtNearbyEntities();

                if (this.timer > 20 && this.timer < 220) {
                    Vec3 rollDirection = new Vec3(target.getX() - rambler.getX(), target.getY() - rambler.getY(), target.getZ() - rambler.getZ()).normalize();
                    float YRot = Mth.approachDegrees(rambler.getYRot(), (float) (Mth.atan2(rollDirection.z, rollDirection.x) * (180F / Math.PI)) - 90.0F, 4.0F);
                    this.rambler.setYRot(YRot);
                    this.rambler.setYBodyRot(YRot);
                    this.rambler.setDeltaMovement(-Mth.sin(YRot * ((float) Math.PI / 180F)) * 0.35F, rambler.getDeltaMovement().y, Mth.cos(YRot * ((float) Math.PI / 180F)) * 0.35F);
                }

                if (this.timer == 200) this.rambler.setPose(OPPoses.STOP_ROLLING.get());

                if (this.timer > 200 && this.timer < 240) this.rambler.getNavigation().stop();

                if (this.timer > 240) {
                    this.timer = 0;
                    this.rambler.rollCooldown = 200 + this.rambler.getRandom().nextInt(200);
                    this.rambler.setRolling(false);
                }

                if (this.collisionTicks > 40) {
                    this.rambler.setRolling(false);
                    this.rambler.setPose(OPPoses.STOP_ROLLING.get());
                    this.rambler.rollCooldown = 200 + this.rambler.getRandom().nextInt(200);
                    this.timer = 0;
                }

            } else {
                this.rambler.lookAt(target, 30F, 30F);
                this.rambler.getLookControl().setLookAt(target, 30F, 30F);
                if (this.rambler.rollCooldown == 0) {
                    this.rambler.getNavigation().moveTo(target, 1.25D);
                    if (distance <= this.getAttackReachSqr(target)) {
                        this.rambler.setRolling(true);
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
        return this.monster.getBbWidth() * 3.0F * this.monster.getBbWidth() * 3.0F + target.getBbWidth();
    }
}