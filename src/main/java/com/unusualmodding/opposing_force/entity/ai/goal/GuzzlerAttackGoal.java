package com.unusualmodding.opposing_force.entity.ai.goal;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.FireSlime;
import com.unusualmodding.opposing_force.entity.Guzzler;
import com.unusualmodding.opposing_force.events.ScreenShakeEvent;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class GuzzlerAttackGoal extends AttackGoal {

    private final Guzzler guzzler;
    private final float maxAttackDistance;
    private int seeTime;
    private boolean strafingClockwise;
    private boolean strafingBackwards;
    private int strafingTime = -1;
    private int spewCooldown = 0;

    public GuzzlerAttackGoal(Guzzler guzzler, float maxAttackDistance) {
        super(guzzler);
        this.guzzler = guzzler;
        this.maxAttackDistance = maxAttackDistance * maxAttackDistance;
    }

    @Override
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
                this.guzzler.getNavigation().moveTo(target, 0.5F);
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
                this.tickSpewAttack();
            } else if (this.guzzler.getAttackState() == 2) {
                this.tickSlamAttack();
            } else {
                this.spewCooldown--;
                if (this.spewCooldown <= 0) {
                    this.guzzler.setAttackState(1);
                } else if (distance < getAttackReachSqr(target)) {
                    this.guzzler.setAttackState(2);
                }
            }
        }
    }

    protected void tickSpewAttack() {
        this.timer++;
        this.spewCooldown = 28;
        LivingEntity target = this.guzzler.getTarget();

        if (this.timer == 7) {
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
            float speed = 0.1F + (0.005F * (float) this.guzzler.distanceToSqr(target.getX(), target.getY(), target.getZ()));
            slime.shoot(d1, d2 + (double) f3, d3, Mth.clamp(speed, 0.1F, 1.25F));
            slime.setYRot(this.guzzler.getYRot() % 360.0F);
            slime.setXRot(Mth.clamp(this.guzzler.getYRot(), -90.0F, 90.0F) % 360.0F);
            if (!this.guzzler.level().isClientSide) {
                this.guzzler.level().addFreshEntity(slime);
            }
        }

        if (this.timer > 3 && this.timer < 9) {
            this.guzzler.level().broadcastEntityEvent(this.guzzler, (byte) 39);
        }

        if (timer > 15) {
            this.timer = 0;
            this.guzzler.setAttackState(0);
        }
    }

    protected void tickSlamAttack() {
        this.timer++;
        this.guzzler.getNavigation().stop();
        this.guzzler.setDeltaMovement(0.0F, this.guzzler.getDeltaMovement().y, 0.0F);

        if (this.timer == 31) {
            for (LivingEntity entity : this.guzzler.level().getEntitiesOfClass(LivingEntity.class, this.guzzler.getBoundingBox().inflate(3.5D))) {
                boolean reachable = entity.getY() > this.guzzler.getY() && entity.distanceTo(this.guzzler) > 3.5;
                boolean self = entity instanceof Guzzler || entity instanceof FireSlime;
                if (self || reachable) {
                    continue;
                }
                Vec3 vec3 = this.guzzler.position().add(0.0, 1.5F, 0.0);
                Vec3 vec32 = entity.getEyePosition().subtract(vec3);
                Vec3 vec33 = vec32.normalize();
                this.guzzler.doHurtTarget(entity);
                double knockbackResistanceY = 0.25 * (1.0 - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double knockbackResistance = 2.0 * (1.0 - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                entity.push(vec33.x() * knockbackResistance, vec33.y() * knockbackResistanceY, vec33.z() * knockbackResistance);
            }
            this.guzzler.playSound(OPSoundEvents.GUZZLER_SLAM.get(), 2.0F, 1.0F / (this.guzzler.getRandom().nextFloat() * 0.4F + 0.8F));
            OpposingForce.PROXY.screenShake(new ScreenShakeEvent(this.guzzler.position(), 20, 3.0F, 16, false));
            this.guzzler.level().broadcastEntityEvent(this.guzzler, (byte) 40);
        }

        if (this.timer > 60) {
            this.timer = 0;
            this.guzzler.setAttackState(0);
        }
    }
}
