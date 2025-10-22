package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Terror;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class TerrorAttackGoal extends AttackGoal {

    protected final Terror terror;

    public TerrorAttackGoal(Terror terror) {
        super(terror);
        this.terror = terror;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && (!this.terror.isInWater() ? this.terror.hasLegs() : terror.isInWater());
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && (!this.terror.isInWater() ? this.terror.hasLegs() : terror.isInWater());
    }

    @Override
    public void start() {
        super.start();
        this.terror.setRunning(false);
        this.terror.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        super.start();
        this.terror.setRunning(false);
        this.terror.setPose(Pose.STANDING);
    }

    @Override
    public void tick() {
        LivingEntity target = this.terror.getTarget();
        if (target != null) {
            double distance = this.terror.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int attackState = this.terror.getAttackState();
            this.terror.lookAt(this.terror.getTarget(), 30F, 30F);
            this.terror.getLookControl().setLookAt(this.terror.getTarget(), 30F, 30F);

            if (attackState == 1) {
                timer++;

                if (this.timer == 1) {
                    this.terror.setPose(OPPoses.START_SAWING.get());
                }

                if (timer < 20) {
                    this.terror.getNavigation().stop();
                }

                if (timer > 20) {
                    this.terror.getNavigation().moveTo(target, 1.4D);
                    this.terror.setRunning(true);
                    this.hurtNearbyEntities();
                }

                if (timer > 200) {
                    timer = 0;
                    this.terror.setAttackState(0);
                }
            } else {
                this.terror.getNavigation().moveTo(target, 1.1D);

                if (distance < 32 && this.terror.getPose() == Pose.STANDING) {
                    this.terror.setAttackState(1);
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
}