package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.FireSlime;
import com.unusualmodding.opposing_force.entity.Guzzler;
import com.unusualmodding.opposing_force.events.ScreenShakeEvent;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class GuzzlerAttackGoal extends Goal {

    private final Guzzler guzzler;
    private final double speed;
    private final float maxAttackDistance;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;
    private int attackTime = 0;

    public GuzzlerAttackGoal(Guzzler guzzler, double speed, float maxAttackDistance) {
        this.guzzler = guzzler;
        this.speed = speed;
        this.maxAttackDistance = maxAttackDistance * maxAttackDistance;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.guzzler.getTarget() != null && this.guzzler.shouldSpew();
    }

    @Override
    public boolean canContinueToUse() {
        return (this.canUse() || !this.guzzler.getNavigation().isDone()) && this.guzzler.shouldSpew();
    }

    @Override
    public void start() {
        super.start();
        this.guzzler.setAggressive(true);
        this.guzzler.setAttackState(0);
        this.attackTime = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.guzzler.setAggressive(false);
        this.guzzler.setAttackState(0);
        this.seeTime = 0;
    }

    public void tick() {
        LivingEntity target = this.guzzler.getTarget();
        if (target != null) {
            double distance = this.guzzler.distanceToSqr(target.getX(), target.getY(), target.getZ());

            boolean flag = this.guzzler.hasLineOfSight(target);
            boolean flag1 = this.seeTime > 0;

            if (flag != flag1) {
                this.seeTime = 0;
            }

            if (flag) {
                ++this.seeTime;
            } else {
                --this.seeTime;
            }

            if (!(distance > (double) this.maxAttackDistance) && this.seeTime >= 20) {
                this.guzzler.getNavigation().stop();
                this.strafingTime++;
            } else {
                this.guzzler.getNavigation().moveTo(target, this.speed);
                this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
                if ((double) this.guzzler.getRandom().nextFloat() < 0.3D) {
                    this.strafingClockwise = !this.strafingClockwise;
                }
                if ((double) this.guzzler.getRandom().nextFloat() < 0.3D) {
                    this.strafingBackwards = !this.strafingBackwards;
                }
                this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
                if (distance > (double) (this.maxAttackDistance * 0.75F)) {
                    this.strafingBackwards = false;
                } else if (distance < (double) (this.maxAttackDistance * 0.25F)) {
                    this.strafingBackwards = true;
                }

                if (this.guzzler.getAttackState() != 2) {
                    this.guzzler.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                }
                this.guzzler.lookAt(target, 30.0F, 30.0F);
            } else {
                this.guzzler.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            if (this.guzzler.getAttackState() == 1) {
                tickSpitAttack();
            } else if (this.guzzler.getAttackState() == 2) {
                tickStompAttack();
            } else {
                if (this.guzzler.getSpewCooldown() <= 0) {
                    this.guzzler.setAttackState(1);
                } else if (distance < 14) {
                    this.guzzler.setAttackState(2);
                }
            }
        }
    }

    protected void tickSpitAttack() {
        this.attackTime++;

        if (this.attackTime == 7) {
            FireSlime slime = OPEntities.FIRE_SLIME.get().create(this.guzzler.level());
            slime.setParentId(this.guzzler.getUUID());
            slime.setPos(this.guzzler.getX(), this.guzzler.getEyeY(), this.guzzler.getZ());
            final double d0 = this.guzzler.getTarget().getEyeY() - (double) 1.1F;
            final double d1 = this.guzzler.getTarget().getX() - this.guzzler.getX();
            final double d2 = d0 - slime.getY();
            final double d3 = this.guzzler.getTarget().getZ() - this.guzzler.getZ();
            final float f3 = Mth.sqrt((float) (d1 * d1 + d2 * d2 + d3 * d3)) * 0.23F;
            this.guzzler.gameEvent(GameEvent.PROJECTILE_SHOOT);
            this.guzzler.playSound(OPSoundEvents.GUZZLER_SPEW.get(), 2.0F, 1.0F / (this.guzzler.getRandom().nextFloat() * 0.4F + 0.8F));
            slime.shoot(d1, d2 + (double) f3, d3, 1.3F, 0.0F);
            slime.setYRot(this.guzzler.getYRot() % 360.0F);
            slime.setXRot(Mth.clamp(this.guzzler.getYRot(), -90.0F, 90.0F) % 360.0F);
            if (!this.guzzler.level().isClientSide) {
                this.guzzler.level().addFreshEntity(slime);
            }
        }

        if (this.attackTime > 3 && this.attackTime < 9) {
            this.guzzler.level().broadcastEntityEvent(this.guzzler, (byte) 39);
        }

        if (attackTime > 15) {
            this.attackTime = 0;
            this.guzzler.spewCooldown();
            this.guzzler.setAttackState(0);
        }
    }

    protected void tickStompAttack() {
        this.attackTime++;
        this.guzzler.getNavigation().stop();
        this.guzzler.setDeltaMovement(0.0F, this.guzzler.getDeltaMovement().y, 0.0F);

        if (this.attackTime == 57) {
            for (LivingEntity entity : this.guzzler.level().getEntitiesOfClass(LivingEntity.class, this.guzzler.getBoundingBox().inflate(4.0D))) {

                boolean reachable = entity.getY() > this.guzzler.getY() && entity.distanceTo(this.guzzler) > 4;
                boolean self = entity instanceof Guzzler || entity instanceof FireSlime;

                if (self || reachable) {
                    continue;
                }
                Vec3 vec3 = this.guzzler.position().add(0.0, 1.5F, 0.0);
                Vec3 vec32 = entity.getEyePosition().subtract(vec3);
                Vec3 vec33 = vec32.normalize();
                this.guzzler.doHurtTarget(entity);
                double knockbackResistanceY = 0.35 * (1.0 - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double knockbackResistance = 2 * (1.0 - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                entity.push(vec33.x() * knockbackResistance, vec33.y() * knockbackResistanceY, vec33.z() * knockbackResistance);
            }
            OpposingForce.PROXY.screenShake(new ScreenShakeEvent(this.guzzler.position(), 20, 2.0F, 25, false));
            this.guzzler.level().broadcastEntityEvent(this.guzzler, (byte) 40);
        }

        if (this.attackTime > 90) {
            this.attackTime = 0;
            this.guzzler.setAttackState(0);
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }
}
