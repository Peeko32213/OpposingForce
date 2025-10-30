package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.entity.Whizz;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.utils.OPMath;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WhizzBomb extends AbstractBomb {

    public WhizzBomb(EntityType<? extends WhizzBomb> entity, Level level) {
        super(entity, level);
    }

    public WhizzBomb(Level level, LivingEntity entity) {
        super(OPEntities.WHIZZ_BOMB.get(), level, entity);
    }

    public WhizzBomb(Level level, double x, double y, double z) {
        super(OPEntities.WHIZZ_BOMB.get(), level, x, y, z);
        this.setOwner(null);
    }

    @Override
    protected float getExplosionRadius() {
        return 1.0F;
    }

    @Override
    protected float getMaxFuse() {
        return 80.0F;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        if (!(result.getEntity() instanceof Whizz)) {
            super.onHitEntity(result);
        }
    }

    @Override
    protected void createExplosion() {
        super.createExplosion();
        Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
        float radius = this.getExplosionRadius();
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.level().playSound(null, location.x(), location.y(), location.z(), SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.NEUTRAL, 2.5F, 1.25F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            for (int i = 0; i < 8; i++) {
                if (this.getOwner() == null) {
                    this.dispenseWhizz(this.level());
                } else {
                    this.summonWhizz((Player) this.getOwner());
                }
            }
        }
        for (Entity entity : this.level().getEntities(this, new AABB(location.subtract(radius, radius, radius), location.add(radius, radius, radius)))) {
            if (entity.distanceToSqr(location) > radius * radius || entity instanceof Whizz || !OPMath.hasLineOfSight(this, entity)) {
                continue;
            }
            float scaledDistance = (float) (1 - (entity.position().distanceTo(location) / radius));
            Vec3 knockback = entity.position().add(0, entity.getBbHeight() * 0.5, 0).subtract(location).normalize().scale(Mth.sqrt(scaledDistance));
            if (!this.level().isClientSide) {
                entity.hurtMarked = true;
            }
            entity.setOnGround(false);
            entity.setDeltaMovement(entity.getDeltaMovement().add(knockback));
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

    private void summonWhizz(Player summoner) {
        Whizz whizz = OPEntities.WHIZZ.get().create(summoner.level());
        if (whizz != null) {
            float f = summoner.getRandom().nextFloat() * 360;
            float distance = 1.5F;
            whizz.moveTo(this.getX() + this.random.nextFloat() * distance, this.getEyeY() + this.random.nextFloat() * distance, this.getZ() + this.random.nextFloat() * distance, f, -60);
            whizz.yBodyRot = f;
            whizz.setYHeadRot(f);
            whizz.tame(summoner);
            whizz.setFromBomb(true);
            whizz.finalizeSpawn((ServerLevel) summoner.level(), summoner.level().getCurrentDifficultyAt(summoner.blockPosition()), MobSpawnType.TRIGGERED, null, null);
            summoner.level().addFreshEntity(whizz);
            whizz.copyTarget(summoner);
        }
    }

    private void dispenseWhizz(Level level) {
        Whizz whizz = OPEntities.WHIZZ.get().create(level);
        if (whizz != null) {
            float f = level.getRandom().nextFloat() * 360;
            float distance = 1.5F;
            whizz.moveTo(this.getX() + this.random.nextFloat() * distance, this.getEyeY() + this.random.nextFloat() * distance, this.getZ() + this.random.nextFloat() * distance, f, -60);
            whizz.yBodyRot = f;
            whizz.setYHeadRot(f);
            whizz.setFromBomb(true);
            whizz.finalizeSpawn((ServerLevel) level, level.getCurrentDifficultyAt(whizz.blockPosition()), MobSpawnType.TRIGGERED, null, null);
            level.addFreshEntity(whizz);
        }
    }
}