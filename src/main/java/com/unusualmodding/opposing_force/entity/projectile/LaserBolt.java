package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

public class LaserBolt extends FrictionlessProjectile {

    private static final EntityDataAccessor<Integer> DISRUPTOR_LEVEL = SynchedEntityData.defineId(LaserBolt.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DISRUPTOR = SynchedEntityData.defineId(LaserBolt.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RAPID_FIRE = SynchedEntityData.defineId(LaserBolt.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FREEZING = SynchedEntityData.defineId(LaserBolt.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(LaserBolt.class, EntityDataSerializers.FLOAT);

    protected RandomSource randomSource = level.getRandom();

    public LaserBolt(EntityType<? extends FrictionlessProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public LaserBolt(LivingEntity entity, Level level, double x, double y, double z) {
        super(OPEntities.LASER_BOLT.get(), level, entity, x, y, z);
        this.setOwner(entity);
        this.moveTo(x, y, z, this.getYRot(), this.getXRot());
        this.reapplyPosition();
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DAMAGE, 5.0F);
        this.getEntityData().define(DISRUPTOR, false);
        this.getEntityData().define(DISRUPTOR_LEVEL, 0);
        this.getEntityData().define(RAPID_FIRE, false);
        this.getEntityData().define(FREEZING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat("Damage", this.getLaserDamage());
        compoundTag.putBoolean("IsDisruptor", this.isDisruptor());
        compoundTag.putInt("DisruptorLevel", this.getDisruptorLevel());
        compoundTag.putBoolean("IsRapidFire", this.isRapidFire());
        compoundTag.putBoolean("IsFreezing", this.isFreezing());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setLaserDamage(compoundTag.getFloat("Damage"));
        this.setDisruptor(compoundTag.getBoolean("IsDisruptor"));
        this.setDisruptorLevel(compoundTag.getInt("DisruptorLevel"));
        this.setRapidFire(compoundTag.getBoolean("IsRapidFire"));
        this.setFreezing(compoundTag.getBoolean("IsFreezing"));
    }

    public boolean isDisruptor() {
        return this.entityData.get(DISRUPTOR);
    }

    public void setDisruptor(boolean disruptor) {
        this.entityData.set(DISRUPTOR, disruptor);
    }

    public int getDisruptorLevel() {
        return this.entityData.get(DISRUPTOR_LEVEL);
    }

    public void setDisruptorLevel(int disruptorLevel) {
        this.entityData.set(DISRUPTOR_LEVEL, disruptorLevel);
    }

    public boolean isRapidFire() {
        return this.entityData.get(RAPID_FIRE);
    }

    public void setRapidFire(boolean rapidFire) {
        this.entityData.set(RAPID_FIRE, rapidFire);
    }

    public boolean isFreezing() {
        return this.entityData.get(FREEZING);
    }

    public void setFreezing(boolean freezing) {
        this.entityData.set(FREEZING, freezing);
    }

    public float getLaserDamage() {
        return this.entityData.get(DAMAGE);
    }

    public void setLaserDamage(float damage) {
        this.entityData.set(DAMAGE, damage);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isFreezing()) {
            this.level().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 0.0, 0.1, 0.0);
        } else {
            this.level().addParticle(OPParticles.LASER_BOLT_DUST.get(), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }
        if (tickCount > 160 || this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            if (!this.level().isClientSide) {
                this.level().broadcastEntityEvent(this, (byte) 3);
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSoundEvents.LASER_BOLT_IMPACT.get(), SoundSource.NEUTRAL, 1.5F, 1.0F + (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F);
                this.discard();
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        DamageSource damageSource = this.damageSources().source(OPDamageTypes.LASER_BOLT);

        if (!this.level().isClientSide) {
            this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), OPSoundEvents.LASER_BOLT_IMPACT.get(), SoundSource.NEUTRAL, 1.5F, 1.0F + (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F);
            this.level().broadcastEntityEvent(this, (byte) 3);
            if (this.isRapidFire()) {
                entity.hurt(damageSource, this.getLaserDamage());
                entity.invulnerableTime -= 5;
            } else {
                entity.hurt(damageSource, this.getLaserDamage());
            }
            if (this.isDisruptor()) {
                for (int i = 0; i < 2 + this.getDisruptorLevel(); i++) {
                    LaserBolt laserBolt = OPEntities.LASER_BOLT.get().create(level());
                    Vec3 vec3 = this.getDeltaMovement().normalize();
                    float f = -((float) Mth.atan2(vec3.x, vec3.z)) * 180.0F / (float) Math.PI;

                    float boltAngle = 360.0F / (this.getDisruptorLevel() + 2);

                    Vec3 vec31 = new Vec3(0, 0, 1.5F).yRot((float) -Math.toRadians(f + boltAngle - boltAngle * i));
                    laserBolt.setPos(entity.getEyePosition().add(vec31));
                    laserBolt.setDeltaMovement(vec31);
                    laserBolt.setOwner(this.getOwner());
                    laserBolt.setDisruptor(false);
                    if (this.isFreezing()) {
                        laserBolt.setFreezing(true);
                    }

                    float yRot = (float) (Mth.atan2(vec31.z, vec31.x) * (180F / Math.PI)) + 90F;
                    float xRot = (float) -(Mth.atan2(vec31.y, Math.sqrt(vec31.x * vec31.x + vec31.z * vec31.z)) * (180F / Math.PI));

                    laserBolt.setYRot(yRot);
                    laserBolt.setXRot(xRot);
                    level().addFreshEntity(laserBolt);
                }
                level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSoundEvents.BLASTER_SHOOT.get(), SoundSource.PLAYERS, 1.0F, (level.getRandom().nextFloat() * 0.5F + 0.8F));
            }
            if (this.isFreezing()) {
                entity.setTicksFrozen(Math.min(entity.getTicksRequiredToFreeze() * 4, entity.getTicksFrozen() + 120));
            }
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();

        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), OPSoundEvents.LASER_BOLT_IMPACT.get(), SoundSource.NEUTRAL, 1.5F, 1.0F + (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F);
            this.discard();
        }
    }

    @Override
    protected float getEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 0;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for (int i = 0; i < 8; i++) {
                if (this.isFreezing()) {
                    this.level().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), this.getDeltaMovement().scale(0.05D).x, 0.3, this.getDeltaMovement().scale(0.05D).z);
                } else {
                    this.level().addParticle(OPParticles.LASER_BOLT_DUST.get(), this.getX(), this.getY(), this.getZ(), this.getDeltaMovement().scale(0.05D).x, 0.3, this.getDeltaMovement().scale(0.05D).z);
                }
            }
        }
    }
}
