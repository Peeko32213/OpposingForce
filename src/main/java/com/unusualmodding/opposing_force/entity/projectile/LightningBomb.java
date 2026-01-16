package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPMobEffects;
import com.unusualmodding.opposing_force.utils.OPMath;
import com.unusualmodding.opposing_force.utils.ParticleUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LightningBomb extends AbstractBomb {

    public LightningBomb(EntityType<? extends LightningBomb> entity, Level level) {
        super(entity, level);
    }

    public LightningBomb(Level level, LivingEntity entity) {
        super(OPEntities.LIGHTNING_BOMB.get(), level, entity);
    }

    public LightningBomb(Level level, double x, double y, double z) {
        super(OPEntities.LIGHTNING_BOMB.get(), level, x, y, z);
    }

    @Override
    protected float getExplosionRadius() {
        return 5.0F;
    }

    @Override
    protected void createExplosion() {
        super.createExplosion();
        Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
        float radius = this.getExplosionRadius();
        if (!this.level().isClientSide) {
            for (int i = 0; i < 16; i++) {
                ParticleUtils.spawnLightningParticles(location.x(), location.y(), location.z(), 6 + this.random.nextInt(2), 0.3F + (this.random.nextFloat() / 8), 0.5F + (this.random.nextFloat() / 8), 0.8F + (this.random.nextFloat() / 8));
            }
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.level().playSound(null, location.x(), location.y(), location.z(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.NEUTRAL, 2.5F, 1.8F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }

        for (Entity entity : this.level().getEntities(this, new AABB(location.subtract(radius, radius, radius), location.add(radius, radius, radius)))) {
            if (entity.distanceToSqr(location) > radius * radius || !OPMath.hasLineOfSight(this, entity)) {
                continue;
            }
            if (entity instanceof LivingEntity livingEntity) {
                this.doKnockback(livingEntity, 1, 1);
                this.doDamage(livingEntity, 6, 12);
                livingEntity.addEffect(new MobEffectInstance(OPMobEffects.ELECTRIFIED.get(), 300), this.getOwner());
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
            this.spawnParticles(ParticleTypes.LARGE_SMOKE, 16, 0.3);
            this.level().addParticle(ParticleTypes.FLASH, true, location.x(), location.y(), location.z(), 0, 0, 0);
        }
    }
}