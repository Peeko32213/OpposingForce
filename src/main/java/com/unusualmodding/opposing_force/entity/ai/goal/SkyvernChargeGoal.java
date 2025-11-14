package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Skyvern;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class SkyvernChargeGoal extends Goal {

    private final Skyvern skyvern;

    private Vec3 startOrbitFrom;
    private int orbitTime;
    private int maxOrbitTime;
    private int timer;
    private int collisionTicks;
    private boolean clockwise;

    public SkyvernChargeGoal(Skyvern entity) {
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.skyvern = entity;
    }

    @Override
    public void start() {
        this.orbitTime = 0;
        this.maxOrbitTime = 40;
        this.collisionTicks = 0;
        this.startOrbitFrom = null;
        this.skyvern.setPose(Pose.STANDING);
    }

    @Override
    public void stop() {
        LivingEntity target = skyvern.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            skyvern.setTarget(null);
        }
        this.skyvern.setAggressive(false);
        this.skyvern.getNavigation().stop();
        this.skyvern.setPose(Pose.STANDING);
    }

    @Override
    public boolean canUse() {
        LivingEntity target = skyvern.getTarget();
        return target != null && target.isAlive() && !skyvern.isPassenger();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = skyvern.getTarget();
        if (target == null) {
            return false;
        } else if (!target.isAlive()) {
            return false;
        } else if (!skyvern.isWithinRestriction(target.blockPosition())) {
            return false;
        } else {
            return !(target instanceof Player) || !target.isSpectator() && !((Player) target).isCreative() || !skyvern.getNavigation().isDone();
        }
    }

    @Override
    public void tick() {
        LivingEntity target = skyvern.getTarget();
        RandomSource random = skyvern.getRandom();
        int attackState = skyvern.getAttackState();
        if (target != null) {
            if (startOrbitFrom == null) {
                this.skyvern.getNavigation().moveTo(target, 1.6D);
            } else if (orbitTime < maxOrbitTime) {
                this.orbitTime++;
                Vec3 orbitPos = orbitAroundPos(64.0F);
                this.skyvern.getNavigation().moveTo(orbitPos.x, orbitPos.y + 3 + random.nextInt(6), orbitPos.z, 1.6D);
            } else {
                this.orbitTime = 0;
                this.startOrbitFrom = null;
            }

            if (attackState == 1) {
                this.tickCharge();
            } else if (orbitTime == 0 && attackState == 0) {
                if (this.isWithinRange(target)) {
                    this.skyvern.setAttackState(1);
                }
            }
        }
    }

    private void tickCharge() {
        timer++;
        LivingEntity target = skyvern.getTarget();

        if (this.skyvern.verticalCollisionBelow || this.skyvern.horizontalCollision || this.skyvern.verticalCollision || this.skyvern.minorHorizontalCollision) {
            this.collisionTicks++;
        }

        if (target != null) {
            if (timer < 16) {
                this.skyvern.getNavigation().stop();
                this.skyvern.setDeltaMovement(0.0D, 0.0D, 0.0D);
                this.skyvern.setYRot(Mth.rotLerp(1.0F, skyvern.getYRot(), (float) (Mth.atan2(target.getZ() - skyvern.getZ(), target.getX() - skyvern.getX()) * (180F / Math.PI)) - 90.0F));
                this.skyvern.getLookControl().setLookAt(target, 360.0F, 90.0F);
            }

            if (timer == 12) {
                this.skyvern.setPose(OPPoses.ATTACK_START.get());
            }

            if (timer > 16) {
                Vec3 rollDirection = new Vec3(target.getX() - skyvern.getX(), target.getY() - skyvern.getY(), target.getZ() - skyvern.getZ()).normalize();
                float YRot = Mth.approachDegrees(skyvern.getYRot(), (float) (Mth.atan2(rollDirection.z, rollDirection.x) * (180F / Math.PI)) - 90.0F, 1.5F);
                this.skyvern.setYRot(YRot);
                this.skyvern.setYBodyRot(YRot);
                this.skyvern.setDeltaMovement(-Mth.sin(YRot * ((float) Math.PI / 180F)) * 1.75F, skyvern.getDeltaMovement().y, Mth.cos(YRot * ((float) Math.PI / 180F)) * 1.75F);
                this.hurtNearbyEntities();
            }

            if (timer == 32 || collisionTicks > 20) {
                this.clockwise = skyvern.getRandom().nextBoolean();
                this.skyvern.setPose(OPPoses.ATTACK_END.get());
                this.maxOrbitTime = 40 + skyvern.getRandom().nextInt(40);
                this.startOrbitFrom = target.getEyePosition();
                this.timer = 0;
                this.skyvern.setAttackState(0);
            }
        }
    }

    public Vec3 orbitAroundPos(float circleDistance) {
        final float angle = 2.0F * (float) (Math.toRadians((clockwise ? -orbitTime : orbitTime) * 2.0F));
        final double extraX = circleDistance * Mth.sin((angle));
        final double extraZ = circleDistance * Mth.cos(angle);
        return startOrbitFrom.add(extraX, 0.0F, extraZ);
    }

    private void hurtNearbyEntities() {
        List<LivingEntity> nearbyEntities = skyvern.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), skyvern, skyvern.getBoundingBox().inflate(1.6));
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.get(0);
            if (!(entity instanceof Skyvern)) {
                entity.hurt(entity.damageSources().mobAttack(this.skyvern), (float) this.skyvern.getAttributeValue(Attributes.ATTACK_DAMAGE));
                if (entity.isDamageSourceBlocked(skyvern.damageSources().mobAttack(skyvern)) && entity instanceof Player player) {
                    player.disableShield(true);
                }
                this.skyvern.swing(InteractionHand.MAIN_HAND);
            }
        }
    }

    private boolean isWithinRange(LivingEntity target) {
        if (target == null) {
            return false;
        }
        return Math.abs(target.getY() - skyvern.getY()) < 4 && skyvern.distanceTo(target) < 64 && !(skyvern.getY() < target.getY());
    }
}