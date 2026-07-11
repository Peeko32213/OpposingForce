package com.barl_inc.opposing_force.entity.ai.goal;

import com.barl_inc.opposing_force.entity.Terror;
import com.barl_inc.opposing_force.entity.utils.OPPoses;
import com.barl_inc.opposing_force.registry.OPSoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class TerrorAttackGoal extends AttackGoal {

    private final Terror terror;
    private int cooldown;

    public TerrorAttackGoal(Terror terror) {
        super(terror);
        this.terror = terror;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.terror.getPose() == Pose.STANDING;
    }

    @Override
    public void start() {
        super.start();
        this.cooldown = 0;
        this.terror.setSawing(false);
        this.terror.setRunning(false);
        this.terror.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        super.stop();
        this.cooldown = 0;
        this.terror.setSawing(false);
        this.terror.setRunning(false);
        this.terror.setPose(Pose.STANDING);
    }

    @Override
    public void tick() {
        LivingEntity target = this.terror.getTarget();
        if (target != null) {
            double distance = this.terror.distanceToSqr(target.getX(), target.getY(), target.getZ());
            this.terror.lookAt(this.terror.getTarget(), 30F, 30F);
            this.terror.getLookControl().setLookAt(this.terror.getTarget(), 30F, 30F);

            if (this.terror.isSawing()) {
                timer++;

                if (this.timer == 1) {
                    this.terror.playSound(OPSoundEvents.TERROR_SAW_START.get(), 1.0F, 1.0F);
                    this.terror.setPose(OPPoses.START_SAWING.get());
                }

                if (timer < 20) {
                    this.terror.getNavigation().stop();
                }

                if (timer > 20 && timer < 100) {
                    this.terror.getNavigation().moveTo(target, 1.5D);
                    this.terror.setRunning(true);
                    this.hurtNearbyEntities();
                }

                if (timer == 100) {
                    this.terror.setPose(OPPoses.RECOVERING.get());
                    this.terror.playSound(OPSoundEvents.TERROR_SAW_END.get(), 1.0F, 1.0F);
                }

                if (timer > 100) {
                    this.terror.getNavigation().moveTo(target, 0.75D);
                    this.terror.setRunning(false);
                }

                if (timer > 150) {
                    timer = 0;
                    this.terror.setSawing(false);
                    this.cooldown = 5 + terror.getRandom().nextInt(10);
                }
            } else {
                if (cooldown > 0) this.cooldown--;
                this.terror.getNavigation().moveTo(target, 1.25D);
                if (distance < this.getAttackReachSqr(target) && this.terror.getPose() == Pose.STANDING && cooldown == 0) {
                    this.terror.setSawing(true);
                }
            }
        }
    }

    private void hurtNearbyEntities() {
        List<LivingEntity> nearbyEntities = terror.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), terror, terror.getBoundingBox().inflate(1.1));
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.get(0);
            if (!(entity instanceof Terror)) {
                entity.hurt(entity.damageSources().mobAttack(this.terror), (float) this.terror.getAttributeValue(Attributes.ATTACK_DAMAGE));
                if (entity.isDamageSourceBlocked(terror.damageSources().mobAttack(terror)) && entity instanceof Player player) {
                    player.disableShield(true);
                }
                this.terror.swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    @Override
    protected double getAttackReachSqr(LivingEntity target) {
        return this.monster.getBbWidth() * 3.0F * this.monster.getBbWidth() * 3.0F + target.getBbWidth();
    }
}