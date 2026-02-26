package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPParticles;
import com.unusualmodding.opposing_force.utils.OPMath;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class FireBomb extends AbstractBomb {

    public FireBomb(EntityType<? extends FireBomb> entity, Level level) {
        super(entity, level);
    }

    public FireBomb(Level level, LivingEntity entity) {
        super(OPEntities.FIRE_BOMB.get(), level, entity);
    }

    public FireBomb(Level level, double x, double y, double z) {
        super(OPEntities.FIRE_BOMB.get(), level, x, y, z);
    }

    @Override
    protected float getExplosionRadius() {
        return 5.0F;
    }

    @Override
    protected float getMaxFuse() {
        return 60.0F;
    }

//    @Override
//    protected ParticleOptions getTrailParticle() {
//        return ParticleTypes.LAVA;
//    }

    @Override
    protected void createExplosion() {
        super.createExplosion();
        Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
        float radius = this.getExplosionRadius();
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.level().playSound(null, location.x(), location.y(), location.z(), SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 2.5F, 1.25F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }

        for (Entity entity : this.level().getEntities(this, new AABB(location.subtract(radius, radius, radius), location.add(radius, radius, radius)))) {
            if (entity.distanceToSqr(location) > radius * radius || !OPMath.hasLineOfSight(this, entity)) {
                continue;
            }
            if (entity instanceof LivingEntity livingEntity) {
                this.doDamage(livingEntity, 5, 10);
                this.doKnockback(livingEntity, 1, 1);
                livingEntity.setSecondsOnFire(10);
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
            this.spawnParticles(ParticleTypes.FLAME, 32, 0.4);
            this.spawnParticles(ParticleTypes.LARGE_SMOKE, 20, 0.3);
            this.spawnParticles(ParticleTypes.LAVA, 20, 0.5);
            this.spawnExplosionParticles(OPParticles.FIRE_BOMB_EXPLOSION.get());
            this.level().addParticle(OPParticles.FIRE_BOMB_FLASH.get(), true, location.x, location.y, location.z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return OPItems.FIRE_BOMB.get();
    }
}