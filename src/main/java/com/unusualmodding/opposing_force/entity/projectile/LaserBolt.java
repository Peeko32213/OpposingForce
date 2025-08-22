package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.core.BlockPos;
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
import net.minecraftforge.network.PlayMessages;

public class LaserBolt extends AbstractFrictionlessProjectile {

    private static final EntityDataAccessor<Integer> DISRUPTOR_LEVEL = SynchedEntityData.defineId(LaserBolt.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DISRUPTOR = SynchedEntityData.defineId(LaserBolt.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RAPID_FIRE = SynchedEntityData.defineId(LaserBolt.class, EntityDataSerializers.BOOLEAN);

    protected RandomSource rand = level.getRandom();

    public LaserBolt(EntityType<? extends AbstractFrictionlessProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public LaserBolt(LivingEntity entity, Level level, double x, double y, double z) {
        super(OPEntities.LASER_BOLT.get(), level, entity, x, y, z);
        this.setOwner(entity);
        this.moveTo(x, y, z, this.getYRot(), this.getXRot());
        this.reapplyPosition();
    }

    public LaserBolt(Level level, double x, double y, double z, Vec3 movement) {
        super(OPEntities.LASER_BOLT.get(), x, y, z, movement, level);
    }

    public LaserBolt(PlayMessages.SpawnEntity spawnEntity, Level pLevel) {
        this(OPEntities.LASER_BOLT.get(), pLevel);
    }

    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DISRUPTOR, false);
        this.getEntityData().define(RAPID_FIRE, false);
        this.getEntityData().define(DISRUPTOR_LEVEL, 0);
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

    @Override
    public void tick() {
        super.tick();
        if (tickCount > 160 || this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            if (!this.level().isClientSide) {
                this.level().broadcastEntityEvent(this, (byte) 3);
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSoundEvents.LASER_BOLT_IMPACT.get(), SoundSource.NEUTRAL, 1.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
                this.discard();
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        DamageSource damageSource = this.damageSources().source(OPDamageTypes.LASER_BOLT);
        float damage;

        if (!this.level().isClientSide) {
            this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), OPSoundEvents.LASER_BOLT_IMPACT.get(), SoundSource.NEUTRAL, 1.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
            this.level().broadcastEntityEvent(this, (byte) 3);
            if (this.isRapidFire()) {
                damage = 3.0F;
                entity.hurt(damageSource, damage);
                entity.invulnerableTime -= 5;
            } else {
                damage = 5.0F;
                entity.hurt(damageSource, damage);
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

                    float yRot = (float) (Mth.atan2(vec31.z, vec31.x) * (180F / Math.PI)) + 90F;
                    float xRot = (float) -(Mth.atan2(vec31.y, Math.sqrt(vec31.x * vec31.x + vec31.z * vec31.z)) * (180F / Math.PI));

                    laserBolt.setYRot(yRot);
                    laserBolt.setXRot(xRot);
                    level().addFreshEntity(laserBolt);
                }
                level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSoundEvents.BLASTER_SHOOT.get(), SoundSource.PLAYERS, 1.0F, (level.getRandom().nextFloat() * 0.5F + 0.8F));
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
            this.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), OPSoundEvents.LASER_BOLT_IMPACT.get(), SoundSource.NEUTRAL, 1.5F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
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
            for (int i = 0; i < 9; ++i) {
                this.level().addParticle(OPParticles.LASER_BOLT_DUST.get(), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }
}
