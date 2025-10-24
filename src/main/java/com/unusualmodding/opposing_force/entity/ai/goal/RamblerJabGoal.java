package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Rambler;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class RamblerJabGoal extends AttackGoal {

    private final Rambler rambler;

    public RamblerJabGoal(Rambler rambler) {
        super(rambler);
        this.rambler = rambler;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.rambler.flailCooldown > 0 && this.rambler.rollCooldown > 0 && this.rambler.getPose() == Pose.STANDING;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.rambler.flailCooldown > 0 && this.rambler.rollCooldown > 0;
    }

    @Override
    public void start() {
        super.start();
        this.rambler.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        super.stop();
        this.rambler.setPose(Pose.STANDING);
    }

    @Override
    public void tick() {
        LivingEntity target = this.rambler.getTarget();
        if (target != null) {
            int attackState = this.rambler.getAttackState();
            double distance = this.rambler.distanceToSqr(target.getX(), target.getY(), target.getZ());
            this.rambler.getNavigation().moveTo(target, 1.25D);
            this.rambler.lookAt(target, 30F, 30F);
            this.rambler.getLookControl().setLookAt(target, 30F, 30F);

            if (this.rambler.getAttackState() == 1) {
                this.timer++;
                if (this.timer == 1) this.rambler.setPose(OPPoses.JAB.get());
                if (this.timer == 7) {
                    if (this.rambler.distanceTo(target) < this.getAttackReachSqr(target)) {
                        target.hurt(this.rambler.damageSources().mobAttack(this.rambler), (float) this.rambler.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5F);
                        target.knockback(rambler.getAttribute(Attributes.ATTACK_KNOCKBACK).getValue() * 0.5F, Mth.sin(rambler.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(rambler.getYRot() * ((float) Math.PI / 180F)));
                        this.rambler.setDeltaMovement(this.rambler.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
                        this.rambler.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (this.timer == 40) {
                    this.timer = 0;
                    this.rambler.setAttackState(0);
                }
            } else if (this.rambler.getAttackState() == 2) {
                this.timer++;
                this.rambler.getNavigation().stop();
                if (this.timer == 1) {
                    this.rambler.setPose(OPPoses.JAB_RUSH.get());
                }
                if (this.timer == 10) {
                    if (this.rambler.distanceTo(target) < this.getAttackReachSqr(target)) {
                        this.rambler.doHurtTarget(target);
                        this.rambler.swing(InteractionHand.MAIN_HAND);
                    }
                }
                if (this.timer == 30) {
                    this.timer = 0;
                    this.rambler.setAttackState(0);
                }
            } else {
                if (distance <= this.getAttackReachSqr(target) && attackState == 0) {
                    if (this.rambler.getRandom().nextFloat() < 0.33F) {
                        this.rambler.setAttackState(2);
                    } else {
                        this.rambler.setAttackState(1);
                    }
                }
            }
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.monster.getBbWidth() * 1.5F * this.monster.getBbWidth() * 1.5F + target.getBbWidth();
    }
}