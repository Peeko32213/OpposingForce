package com.unusualmodding.opposing_force.entity.projectile;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBomb extends ThrowableProjectile {

    protected float fuse = 0;
    protected float prevFuse;
    protected float spin = 0;
    protected float prevSpin;

    public AbstractBomb(EntityType<? extends AbstractBomb> entity, Level level) {
        super(entity, level);
    }

    public AbstractBomb(EntityType<? extends AbstractBomb> entityType, Level level, LivingEntity entity) {
        super(entityType, entity, level);
    }

    protected float getExplosionRadius() {
        return -1F;
    }

    protected float getMaxFuse() {
        return -1F;
    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.SMOKE;
    }

    protected boolean explodesOnWaterContact() {
        return true;
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        fuse++;
        if (this.level().isClientSide) {
            prevFuse = fuse;
            prevSpin = spin;
            if (fuse > 3) {
                float horizontalSpeed = 0.01F;
                Vec3 direction = this.getPosition(0).subtract(this.getPosition(1));
                Vec3 particlePosition = this.getPosition(1).add(direction.normalize().scale(0.25F));
                this.level().addParticle(this.getTrailParticle(), particlePosition.x(), particlePosition.y(), particlePosition.z(), random.nextGaussian() * horizontalSpeed, Mth.abs((float) random.nextGaussian()) * horizontalSpeed * 2, random.nextGaussian() * horizontalSpeed);
            }

            this.prevSpin = this.spin;
            Vec3 deltaMovement = this.getPosition(0).subtract(this.getPosition(1));
            float spinAmount = (float) (deltaMovement.length() / (Mth.TWO_PI * 6F));
            float spinInRadians = spinAmount * Mth.TWO_PI * 2F;
            this.spin += spinInRadians;
        } else {
            this.reapplyPosition();
        }
        if (fuse > this.getMaxFuse()) {
            this.createExplosion();
            this.discard();
        }
        if (this.isOnFire() || (this.explodesOnWaterContact() && this.isInWaterOrBubble())) {
            this.createExplosion();
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(this.damageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 1.0F);
        this.fuse /= 2;
        this.setDeltaMovement(Vec3.ZERO);
        this.level().gameEvent(GameEvent.PROJECTILE_LAND, result.getLocation(), GameEvent.Context.of(this, null));
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        float conservedEnergy = 0.5F;
        Vec3 motion = this.getDeltaMovement();
        Direction direction = result.getDirection();

        this.level().gameEvent(GameEvent.PROJECTILE_LAND, result.getLocation(), GameEvent.Context.of(this, null));
        if (motion.lengthSqr() < 0.1) {
            this.setDeltaMovement(Vec3.ZERO);
            if (!this.onGround()) {
                this.playSound(SoundEvents.CHAIN_HIT, 1F, 1F);
            }
            this.setOnGround(true);
            return;
        } else {
            this.playSound(SoundEvents.CHAIN_HIT, 1F, 1F);
        }

        switch (direction.getAxis()) {
            case X -> this.setDeltaMovement(-motion.x() * conservedEnergy, motion.y(), motion.z());
            case Y -> this.setDeltaMovement(motion.x() * conservedEnergy, -motion.y() * conservedEnergy, motion.z() * conservedEnergy);
            case Z -> this.setDeltaMovement(motion.x(), motion.y(), -motion.z() * conservedEnergy);
        }
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity entity) {
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 1.0F;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getDirectEntity();
            if (entity != null) {
                if (entity instanceof AbstractBomb) return false;
                if (!this.level().isClientSide) {
                    Vec3 vec3 = entity.getLookAngle();
                    this.setDeltaMovement(this.getDeltaMovement().add(vec3.scale(0.6)));
                    this.setOwner(entity);
                    this.level().playSound(null, this.position().x(), this.position().y(), this.position().z(), SoundEvents.TRIDENT_HIT, SoundSource.BLOCKS, 1.0F, 1.2F);
                }
                this.markHurt();
                return true;
            } else {
                return false;
            }
        }
    }

    protected void createExplosion() {
    }

    public float getFuse(float partialTicks) {
        return Mth.lerp(partialTicks, prevFuse, fuse) / this.getMaxFuse();
    }

    public float getSpin(float partialTicks) {
        return Mth.lerp(partialTicks, prevSpin, spin);
    }
}