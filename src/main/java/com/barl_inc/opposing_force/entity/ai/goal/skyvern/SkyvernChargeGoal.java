package com.barl_inc.opposing_force.entity.ai.goal.skyvern;

import com.barl_inc.opposing_force.entity.Skyvern;
import com.barl_inc.opposing_force.entity.utils.OPPoses;
import com.barl_inc.opposing_force.registry.OPSoundEvents;
import net.minecraft.util.Mth;
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
        int attackState = skyvern.getAttackState();
        if (target != null) {
            if (startOrbitFrom == null) {
                this.skyvern.getNavigation().moveTo(target, 1.5D);
            } else if (orbitTime < maxOrbitTime) {
                this.orbitTime++;
                Vec3 orbitPos = orbitAroundPos(48.0F);
                this.skyvern.getNavigation().moveTo(orbitPos.x, orbitPos.y, orbitPos.z, 1.5D);
            } else {
                this.orbitTime = 0;
                this.startOrbitFrom = null;
            }
            if (attackState == 1) this.tickCharge();
            else if (attackState == 0 && this.isWithinRange(target) && orbitTime == 0) this.skyvern.setAttackState(1);
        }
    }

    private void tickCharge() {
        this.timer++;
        LivingEntity target = skyvern.getTarget();

        if (skyvern.verticalCollisionBelow || skyvern.horizontalCollision || skyvern.verticalCollision || skyvern.minorHorizontalCollision) {
            this.collisionTicks++;
        }

        if (timer == 7) {
            this.skyvern.setPose(OPPoses.ATTACKING.get());
            this.skyvern.playSound(OPSoundEvents.SKYVERN_CHARGE_WARN.get(), 3.0F, 0.9F + skyvern.getRandom().nextFloat() * 0.3F);
        }

        if (timer < 9) {
            this.skyvern.getNavigation().stop();
            this.skyvern.setDeltaMovement(0.0D, 0.0D, 0.0D);
            this.skyvern.setYRot(Mth.rotLerp(1.0F, skyvern.getYRot(), (float) (Mth.atan2(target.getZ() - skyvern.getZ(), target.getX() - skyvern.getX()) * (180F / Math.PI)) - 90.0F));
            this.skyvern.getLookControl().setLookAt(target, 360.0F, 90.0F);
        }

        if (timer == 9) {
            this.skyvern.playSound(OPSoundEvents.SKYVERN_WHOOSH.get(), 3.0F, 0.9F + skyvern.getRandom().nextFloat() * 0.2F);
        }

        if (timer > 9) {
            Vec3 chargeDirection = new Vec3(target.getX() - skyvern.getX(), target.getY(), target.getZ() - skyvern.getZ()).normalize();
            float yRot = Mth.approachDegrees(skyvern.getYRot(), (float) (Mth.atan2(chargeDirection.z, chargeDirection.x) * (180F / Math.PI)) - 90.0F, 0.5F);
            float speed = 2.0F;
            this.skyvern.setYRot(yRot);
            this.skyvern.setYBodyRot(yRot);
            this.skyvern.setDeltaMovement(-Mth.sin(yRot * ((float) Math.PI / 180F)) * speed, skyvern.getDeltaMovement().y, Mth.cos(yRot * ((float) Math.PI / 180F)) * speed);
            this.hurtNearbyEntities();
        }

        if (timer == 28 || collisionTicks > 10) {
            this.clockwise = skyvern.getRandom().nextBoolean();
            this.skyvern.setPose(Pose.STANDING);
            this.maxOrbitTime = 30 + skyvern.getRandom().nextInt(30);
            this.startOrbitFrom = target.getEyePosition();
            this.timer = 0;
            this.skyvern.setAttackState(0);
        }
    }

    public Vec3 orbitAroundPos(float circleDistance) {
        final float angle = 2.0F * (float) (Math.toRadians((clockwise ? -orbitTime : orbitTime) * 2.0F));
        final double extraX = circleDistance * Mth.sin((angle));
        final double extraZ = circleDistance * Mth.cos(angle);
        return startOrbitFrom.add(extraX, 16.0F, extraZ);
    }

    private void hurtNearbyEntities() {
        List<LivingEntity> nearbyEntities = skyvern.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), skyvern, skyvern.getBoundingBox().inflate(1.5D));
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
        return Math.abs(target.getY() - skyvern.getY()) < 5 && skyvern.distanceTo(target) < 256 && !(skyvern.getY() < target.getY() + 0.5F);
    }
}