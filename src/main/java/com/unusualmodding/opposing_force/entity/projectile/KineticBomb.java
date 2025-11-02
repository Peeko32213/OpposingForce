package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.utils.OPMath;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class KineticBomb extends AbstractBomb {

    public KineticBomb(EntityType<? extends KineticBomb> entity, Level level) {
        super(entity, level);
    }

    public KineticBomb(Level level, LivingEntity entity) {
        super(OPEntities.KINETIC_BOMB.get(), level, entity);
    }

    public KineticBomb(Level level, double x, double y, double z) {
        super(OPEntities.KINETIC_BOMB.get(), level, x, y, z);
    }

    @Override
    protected float getExplosionRadius() {
        return 5.0F;
    }

    @Override
    protected float getMaxFuse() {
        return 20.0F;
    }

    @Override
    protected void createExplosion() {
        super.createExplosion();
        Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
        float radius = this.getExplosionRadius();
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.level().playSound(null, location.x(), location.y(), location.z(), SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.NEUTRAL, 2.5F, 1.25F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }
        for (Entity entity : this.level().getEntities(this, new AABB(location.subtract(radius, radius, radius), location.add(radius, radius, radius)))) {
            if (entity.distanceToSqr(location) > radius * radius || !OPMath.hasLineOfSight(this, entity)) {
                continue;
            }
            if (entity instanceof LivingEntity livingEntity) {
                this.doKnockback(livingEntity, 2.6, 1.8);
//                float scaledDistance = (float) (1 - (livingEntity.position().distanceTo(location) / radius));
//                double knockbackResistance = 1.0 - Mth.clamp(livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE), 0.0, 1.0);
//                Vec3 knockback = livingEntity.position().add(0, livingEntity.getBbHeight() * 0.75, 0).subtract(location).normalize().scale(Mth.sqrt(scaledDistance) * knockbackResistance);
//                if (!this.level().isClientSide) {
//                    livingEntity.hurtMarked = true;
//                }
//                livingEntity.setOnGround(false);
//                livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(knockback.x() * 2.6, knockback.y() * 1.8, knockback.z() * 2.6));
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
            this.level().addParticle(ParticleTypes.FLASH, true, location.x(), location.y(), location.z(), 0, 0, 0);
        }
    }
}