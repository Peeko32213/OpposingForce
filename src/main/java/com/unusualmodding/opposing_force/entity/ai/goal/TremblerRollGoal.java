package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.entity.Trembler;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;

public class TremblerRollGoal extends AttackGoal {

    protected final Trembler trembler;
    private Vec3 rollDirection = Vec3.ZERO;

    public TremblerRollGoal(Trembler trembler) {
        super(trembler);
        this.trembler = trembler;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.trembler.getStunnedTicks() <= 0;
    }

    @Override
    public void start() {
        super.start();
        this.trembler.setRolling(false);
        this.trembler.setSprinting(false);
    }

    @Override
    public void stop() {
        super.stop();
        this.trembler.setRolling(false);
        this.trembler.setSprinting(false);
    }

    @Override
    public void tick() {
        LivingEntity target = this.trembler.getTarget();
        if (target != null) {
            double distance = this.trembler.distanceToSqr(target.getX(), target.getY(), target.getZ());
            int speedFactor = trembler.hasEffect(MobEffects.MOVEMENT_SPEED) ? trembler.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
            int slownessFactor = trembler.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? trembler.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
            float effectSpeed = 0.15F * (speedFactor - slownessFactor);
            BlockPos pos = trembler.blockPosition();

            if (this.trembler.isRolling()) {
                this.timer++;
                this.trembler.getNavigation().stop();
                if (this.timer < 12) {
                    this.trembler.lookAt(target, 360F, 30F);
                    this.trembler.getLookControl().setLookAt(target, 30F, 30F);
                }

                if (this.timer == 12) {
                    Vec3 targetPos = target.position();
                    this.rollDirection = (new Vec3(pos.getX() - targetPos.x(), 0.0D, pos.getZ() - targetPos.z())).normalize();
                    this.trembler.setSprinting(true);
                }

                if (this.timer > 12) {
                    this.trembler.setDeltaMovement(this.rollDirection.x * (-0.6 - effectSpeed), this.trembler.getDeltaMovement().y, this.rollDirection.z * (-0.6 - effectSpeed));
                    this.tryToHurt();
                }

                if (this.timer > 53 || this.trembler.horizontalCollision) {
                    this.trembler.setSprinting(false);
                    this.trembler.getNavigation().stop();
                    this.rollDirection = Vec3.ZERO;
                }

                if (this.timer > 69 || this.trembler.horizontalCollision) {
                    this.timer = 0;
                    this.trembler.setRolling(false);
                    this.trembler.rollCooldown();
                }
            } else {
                if (distance < 80 && this.trembler.getRollCooldown() <= 0 && this.trembler.isWithinYRange(target) && this.trembler.onGround()) {
                    this.trembler.setRolling(true);
                }
                else {
                    if (distance < 16) {
                        this.trembler.getNavigation().moveTo(target, 1.0D);
                    } else {
                        this.trembler.getNavigation().moveTo(target, 1.4D);
                    }
                    this.trembler.lookAt(Objects.requireNonNull(target), 30F, 30F);
                    this.trembler.getLookControl().setLookAt(target, 30F, 30F);
                }
            }
        }
    }

    private void tryToHurt() {
        List<LivingEntity> nearbyEntities = this.trembler.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this.trembler, this.trembler.getBoundingBox());
        if (!nearbyEntities.isEmpty()) {
            LivingEntity entity = nearbyEntities.get(0);
            if (!(entity instanceof Trembler)) {
                int speedFactor = trembler.hasEffect(MobEffects.MOVEMENT_SPEED) ? trembler.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() + 1 : 0;
                int slownessFactor = trembler.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) ? trembler.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier() + 1 : 0;
                float effectSpeed = 0.15F * (speedFactor - slownessFactor);
                float speedForce = Mth.clamp(trembler.getSpeed() * 1.65F, 0.2F, 3.0F) + effectSpeed;
                float knockbackForce = entity.isDamageSourceBlocked(trembler.level().damageSources().mobAttack(trembler)) ? 1.75F : 2.25F;
                entity.hurt(entity.damageSources().mobAttack(this.trembler), (float) this.trembler.getAttributeValue(Attributes.ATTACK_DAMAGE));
                entity.knockback((knockbackForce * speedForce) * 1.5F, this.rollDirection.x(), this.rollDirection.z());
                if (entity.isDamageSourceBlocked(this.trembler.damageSources().mobAttack(this.trembler)) && entity instanceof Player player){
                    player.disableShield(true);
                }
                this.trembler.swing(InteractionHand.MAIN_HAND);
            }
        }
    }
}