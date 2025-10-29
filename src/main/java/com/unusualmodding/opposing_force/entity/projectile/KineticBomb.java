package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.OPEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

    @Override
    protected float getExplosionRadius() {
        return 5.0F;
    }

    @Override
    protected float getMaxFuse() {
        return 40.0F;
    }

    @Override
    protected void createExplosion() {
        Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
        float radius = this.getExplosionRadius();
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.level().playSound(null, location.x(), location.y(), location.z(), SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.NEUTRAL, 2.5F, 1.25F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }
        for (Entity entity : this.level().getEntities(this, new AABB(location.subtract(radius, radius, radius), location.add(radius, radius, radius)))) {
            if (entity.distanceToSqr(location) > radius * radius) {
                continue;
            }
            float scaledDistance = (float) (1 - (entity.position().distanceTo(location) / radius));
            float damage = 0.00001F;
            Vec3 knockback = entity.position().add(0, entity.getBbHeight() * 1.25, 0).subtract(location).normalize().scale(Mth.sqrt(scaledDistance));
            entity.hurt(entity.damageSources().explosion(this, this.getOwner()), damage);
            if (entity instanceof LivingEntity livingEntity) {
                if (livingEntity.isDamageSourceBlocked(entity.damageSources().explosion(this, this.getOwner()))) {
                    knockback = knockback.scale(4);
                }
            }
            knockback = knockback.scale(2);
            entity.setOnGround(false);
            entity.setDeltaMovement(entity.getDeltaMovement().add(knockback));
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