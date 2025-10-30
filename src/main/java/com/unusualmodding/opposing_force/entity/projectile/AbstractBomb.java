package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.events.ScreenShakeEvent;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBomb extends ThrowableProjectile {

    private float fuse = 0;
    private float prevFuse;
    private float spin = 0;
    private float prevSpin;
    private int bounceSoundCooldown = 0;
    private int hitCooldown = 0;

    private int lSteps;
    private double lx;
    private double ly;
    private double lz;
    private double lyr;
    private double lxr;
    private double lxd;
    private double lyd;
    private double lzd;

    public AbstractBomb(EntityType<? extends AbstractBomb> entity, Level level) {
        super(entity, level);
    }

    public AbstractBomb(EntityType<? extends AbstractBomb> entityType, Level level, LivingEntity entity) {
        super(entityType, entity, level);
    }

    public AbstractBomb(EntityType<? extends AbstractBomb> entityType, Level level, double x, double y, double z) {
        super(entityType, x, y, z, level);
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

    protected void spawnParticles(ParticleOptions particle, int amount, double speed) {
        Vec3 location = this.position().add(0, this.getBbHeight() * 0.5, 0);
        for (int i = 0; i < amount; i++) {
            double theta = random.nextFloat() * 2 * Math.PI;
            double alpha = random.nextFloat() * 2 * Math.PI;
            double cos = Math.cos(alpha);
            double xVelocity = Math.sin(theta) * cos * (random.nextFloat() * 0.3 + 0.7);
            double yVelocity = cos * Math.cos(theta) * (random.nextFloat() * 0.3 + 0.7);
            double zVelocity = Math.sin(alpha) * (random.nextFloat() * 0.3 + 0.7);
            level().addParticle(particle, false, location.x(), location.y(), location.z(), xVelocity * speed, yVelocity * speed, zVelocity * speed);
        }
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void lerpTo(double x, double y, double z, float yr, float xr, int steps, boolean b) {
        this.lx = x;
        this.ly = y;
        this.lz = z;
        this.lyr = yr;
        this.lxr = xr;
        this.lSteps = steps;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    @Override
    public void lerpMotion(double lerpX, double lerpY, double lerpZ) {
        this.lxd = lerpX;
        this.lyd = lerpY;
        this.lzd = lerpZ;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
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

            if (this.lSteps > 0) {
                double d5 = this.getX() + (this.lx - this.getX()) / (double) this.lSteps;
                double d6 = this.getY() + (this.ly - this.getY()) / (double) this.lSteps;
                double d7 = this.getZ() + (this.lz - this.getZ()) / (double) this.lSteps;
                this.setYRot(Mth.wrapDegrees((float) this.lyr));
                this.setXRot(this.getXRot() + (float) (this.lxr - (double) this.getXRot()) / (float) this.lSteps);
                this.lSteps--;
                this.setPos(d5, d6, d7);
            } else {
                this.reapplyPosition();
            }
        } else {
            this.reapplyPosition();
            this.setRot(this.getYRot(), this.getXRot());
        }
        if (fuse > this.getMaxFuse()) {
            this.createExplosion();
            this.discard();
        }
        if (this.isOnFire() || (this.explodesOnWaterContact() && this.isInWaterOrBubble())) {
            this.createExplosion();
            this.discard();
        }
        if (bounceSoundCooldown > 0) bounceSoundCooldown--;
        if (hitCooldown > 0) hitCooldown--;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Entity entity = hitResult.getEntity();
        Entity owner = this.getOwner();
        if ((owner == null || !entity.is(owner) && !entity.isAlliedTo(owner) && !owner.isAlliedTo(entity))) {
            this.playSound(SoundEvents.CHAIN_HIT);
            hitResult.getEntity().hurt(this.damageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 1.0F);
        }
        this.level().gameEvent(GameEvent.PROJECTILE_LAND, hitResult.getLocation(), GameEvent.Context.of(this, null));
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);
        if (hitResult instanceof BlockHitResult blockHitResult) {
            BlockState state = this.level().getBlockState(blockHitResult.getBlockPos());
            if (!state.getCollisionShape(this.level(), blockHitResult.getBlockPos()).isEmpty()) {
                bounceFromDirection(blockHitResult.getDirection());
            }
        } else if(hitResult instanceof EntityHitResult entityHitResult && !ownedBy(entityHitResult.getEntity()) && !(entityHitResult.getEntity() instanceof AbstractBomb)){
            Vec3 vec3 = entityHitResult.getEntity().getEyePosition().subtract(this.getEyePosition());
            float f = -((float) Mth.atan2(vec3.x, vec3.z)) * 180.0F / (float) Math.PI;
            bounceFromDirection(Direction.fromYRot(f));
        }
    }

    public void bounceFromDirection(Direction direction) {
        Vec3 deltaMovement = this.getDeltaMovement();
        float conservedEnergy = 0.4F;
        if (deltaMovement.lengthSqr() < 0.1) {
            this.setDeltaMovement(Vec3.ZERO);
            if (!this.onGround()) {
                this.playSound(SoundEvents.CHAIN_HIT, 1F, 1F);
            }
            this.setOnGround(true);
            return;
        }
        if (bounceSoundCooldown == 0) {
            bounceSoundCooldown = 5;
            this.playSound(SoundEvents.CHAIN_HIT);
        }
        double x = deltaMovement.x();
        double y = deltaMovement.y();
        double z = deltaMovement.z();
        switch (direction.getAxis()) {
            case X:
                x = -x * conservedEnergy;
                break;
            case Y:
                x = x * conservedEnergy;
                y = -y * conservedEnergy;
                z = z * conservedEnergy;
                break;
            case Z:
                z = -z * conservedEnergy;
                break;
        }
        this.setDeltaMovement(x, y, z);
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
        if (this.isInvulnerableTo(source) || this.hitCooldown > 0) {
            return false;
        } else {
            Entity entity = source.getDirectEntity();
            if (entity != null) {
                if (entity instanceof AbstractBomb) return false;
                if (!this.level().isClientSide) {
                    Vec3 vec3 = entity.getLookAngle();
                    this.setDeltaMovement(this.getDeltaMovement().add(vec3.scale(0.5)));
                    this.setOwner(entity);
                    this.level().playSound(null, this.position().x(), this.position().y(), this.position().z(), SoundEvents.TRIDENT_HIT, SoundSource.BLOCKS, 1.0F, 1.25F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                }
                this.markHurt();
                this.hitCooldown = 10;
                return true;
            } else {
                return false;
            }
        }
    }

    protected void createExplosion() {
        OpposingForce.PROXY.screenShake(new ScreenShakeEvent(this.position(), 10, 2.0F, this.getExplosionRadius() * 2, false));
    }

    public float getFuse(float partialTicks) {
        return Mth.lerp(partialTicks, prevFuse, fuse) / this.getMaxFuse();
    }

    public float getSpin(float partialTicks) {
        return Mth.lerp(partialTicks, prevSpin, spin);
    }
}