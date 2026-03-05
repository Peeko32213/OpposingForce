package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPParticles;
import com.unusualmodding.opposing_force.utils.OPMath;
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
            entity.resetFallDistance();
            if (entity instanceof LivingEntity livingEntity) {
                this.doKnockback(livingEntity, 2.6, 1.8);
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
            this.spawnExplosionParticles(OPParticles.KINETIC_BOMB_EXPLOSION.get());
            this.level().addParticle(OPParticles.KINETIC_BOMB_FLASH.get(), true, location.x, location.y, location.z, 0, 0, 0);
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return OPItems.KINETIC_BOMB.get();
    }
}