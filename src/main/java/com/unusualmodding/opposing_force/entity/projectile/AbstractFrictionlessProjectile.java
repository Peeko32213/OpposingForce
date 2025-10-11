package com.unusualmodding.opposing_force.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractFrictionlessProjectile extends AbstractHurtingProjectile implements ItemSupplier {

    public double accelerationPower;

    protected AbstractFrictionlessProjectile(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
        accelerationPower = 0.0;
    }

    protected AbstractFrictionlessProjectile(EntityType<? extends AbstractHurtingProjectile> entityType, Level level, Entity owner, double x, double y, double z) {
        this(entityType, level);
        this.setPos(x, y, z);
        this.accelerationPower = 0;
    }

    public AbstractFrictionlessProjectile(EntityType<? extends AbstractHurtingProjectile> entityType, double x, double y, double z, Vec3 movement, Level level) {
        this(entityType, level);
        this.moveTo(x, y, z, this.getYRot(), this.getXRot());
        this.reapplyPosition();
        this.assignDirectionalMovement(movement, this.accelerationPower);
        this.accelerationPower = 0.0;
    }

    @Override
    public boolean canCollideWith(@NotNull Entity entity) {
        return !(entity instanceof AbstractFrictionlessProjectile) && super.canCollideWith(entity);
    }

    @Override
    protected boolean canHitEntity(@NotNull Entity target) {
        if (target instanceof AbstractFrictionlessProjectile) {
            return false;
        } else {
            return super.canHitEntity(target);
        }
    }

    @Override
    public void push(double x, double y, double z) {}

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public @NotNull ItemStack getItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected float getInertia() {
        return 1.0F;
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide && this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            this.discard();
        } else {
            Entity entity = this.getOwner();
            if (this.level().isClientSide || (entity == null || !entity.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
                HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
                if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                    this.onHit(hitresult);
                }

                this.checkInsideBlocks();
                Vec3 vec3 = this.getDeltaMovement();
                double d0 = this.getX() + vec3.x;
                double d1 = this.getY() + vec3.y;
                double d2 = this.getZ() + vec3.z;
                ProjectileUtil.rotateTowardsMovement(this, 0.2F);
                float f;
                if (this.isInWater()) {
                    for (int i = 0; i < 4; i++) {
                        this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25, d1 - vec3.y * 0.25, d2 - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
                    }
                    f = 0.8F;
                } else {
                    f = this.getInertia();
                }

                this.setDeltaMovement(vec3.add(vec3.normalize().scale(this.accelerationPower)).scale(f));
                this.setPos(d0, d1, d2);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    private void assignDirectionalMovement(Vec3 movement, double accelerationPower) {
        this.setDeltaMovement(movement.normalize().scale(accelerationPower));
        this.hasImpulse = true;
    }

    public boolean hasLineOfSight(Entity entity) {
        if (entity.level() != this.level()) {
            return false;
        } else {
            Vec3 vec3 = new Vec3(this.getX(), this.getEyeY(), this.getZ());
            Vec3 vec31 = new Vec3(entity.getX(), entity.getEyeY(), entity.getZ());
            if (vec31.distanceTo(vec3) > 128.0D) {
                return false;
            } else {
                return this.level().clip(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).getType() == HitResult.Type.MISS;
            }
        }
    }
}
