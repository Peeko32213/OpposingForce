package com.unusualmodding.opposing_force.entity.misc;

import com.unusualmodding.opposing_force.client.utils.ControlledAnimation;
import com.unusualmodding.opposing_force.entity.Dicer;
import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DicerLaser extends Entity {

    public LivingEntity caster;
    public double endPosX, endPosY, endPosZ;
    public double collidePosX, collidePosY, collidePosZ;
    public double prevCollidePosX, prevCollidePosY, prevCollidePosZ;
    public float renderYaw, renderPitch;
    public ControlledAnimation appear = new ControlledAnimation(3);

    public boolean on = true;

    public Direction blockSide = null;

    private static final EntityDataAccessor<Float> YAW = SynchedEntityData.defineId(DicerLaser.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> PITCH = SynchedEntityData.defineId(DicerLaser.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(DicerLaser.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_PLAYER = SynchedEntityData.defineId(DicerLaser.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> CASTER = SynchedEntityData.defineId(DicerLaser.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(DicerLaser.class, EntityDataSerializers.FLOAT);

    public float prevYaw;
    public float prevPitch;

    public DicerLaser(EntityType<? extends DicerLaser> type, Level world) {
        super(type, world);
        this.noCulling = true;
    }

    public DicerLaser(EntityType<? extends DicerLaser> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch, int duration, int damage) {
        this(type, world);
        this.caster = caster;
        this.setYaw(yaw);
        this.setPitch(pitch);
        this.setDuration(duration);
        this.setDamage(damage);
        this.setPos(x, y, z);
        this.calculateEndPos();
        if (!world.isClientSide) {
            this.setCasterID(caster.getId());
        }
    }

    @Override
    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    @Override
    public void tick() {
        super.tick();

        prevCollidePosX = collidePosX;
        prevCollidePosY = collidePosY;
        prevCollidePosZ = collidePosZ;
        prevYaw = renderYaw;
        prevPitch = renderPitch;

        xo = getX();
        yo = getY();
        zo = getZ();

        if (tickCount == 1 && level().isClientSide) {
            caster = (LivingEntity) level().getEntity(getCasterID());
        }

        if (!level().isClientSide) {
            if (caster instanceof Dicer) {
                this.updateWithDicer();
            }
        }
        if (caster != null) {
            renderYaw = (float) ((caster.yHeadRot + 90.0d) * Math.PI / 180.0d);
            renderPitch = (float) (-caster.getXRot() * Math.PI / 180.0d);
        }

        if (!on && appear.getTimer() == 0) {
            this.discard();
        }
        if (on && tickCount > 20) {
            appear.increaseTimer();
        } else {
            appear.decreaseTimer();
        }

        if (caster != null && !caster.isAlive()) discard() ;

        if (tickCount > 20) {
            this.calculateEndPos();
            List<LivingEntity> entities = raytraceEntities(level(), new Vec3(getX(), getY(), getZ()), new Vec3(endPosX, endPosY, endPosZ), false, true, true).entities;
            if (blockSide != null) {
                spawnExplosionParticles(2);
            }
            if (!level().isClientSide) {
                for (LivingEntity target : entities) {
                    if (caster != null) {
                        if (!this.caster.isAlliedTo(target) && target != caster) {
                            target.hurt(OPDamageTypes.laser(this.level(), this, caster), this.getDamage());
                        }
                    }
                }
            }
        }
        if (tickCount - 20 > getDuration()) {
            on = false;
        }
    }

    private void spawnExplosionParticles(int amount) {
        for (int i = 0; i < amount; i++) {
            final float velocity = 0.1F;
            float yaw = (float) (random.nextFloat() * 2 * Math.PI);
            float motionY = random.nextFloat() * 0.08F;
            float motionX = velocity * Mth.cos(yaw);
            float motionZ = velocity * Mth.sin(yaw);
            level().addParticle(ParticleTypes.FLAME, collidePosX, collidePosY + 0.1, collidePosZ, motionX, motionY, motionZ);
        }
        for (int i = 0; i < amount / 2; i++) {
            level().addParticle(ParticleTypes.LAVA, collidePosX, collidePosY + 0.1, collidePosZ, 0, 0, 0);
        }
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(YAW, 0F);
        this.entityData.define(PITCH, 0F);
        this.entityData.define(DURATION, 0);
        this.entityData.define(DAMAGE, 0F);
        this.entityData.define(HAS_PLAYER, false);
        this.entityData.define(CASTER, -1);
    }

    public float getYaw() {
        return this.entityData.get(YAW);
    }

    public void setYaw(float yaw) {
        this.entityData.set(YAW, yaw);
    }

    public float getPitch() {
        return this.entityData.get(PITCH);
    }

    public void setPitch(float pitch) {
        this.entityData.set(PITCH, pitch);
    }

    public int getDuration() {
        return this.entityData.get(DURATION);
    }

    public void setDuration(int duration) {
        this.entityData.set(DURATION, duration);
    }

    public float getDamage() {
        return this.entityData.get(DAMAGE);
    }

    public void setDamage(float damage) {
        this.entityData.set(DAMAGE, damage);
    }

    public int getCasterID() {
        return this.entityData.get(CASTER);
    }

    public void setCasterID(int id) {
        this.entityData.set(CASTER, id);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {}

    private void calculateEndPos() {
        double radius = 24;
        if (level().isClientSide()) {
            endPosX = getX() + radius * Math.cos(renderYaw) * Math.cos(renderPitch);
            endPosZ = getZ() + radius * Math.sin(renderYaw) * Math.cos(renderPitch);
            endPosY = getY() + radius * Math.sin(renderPitch);
        }
        else {
            endPosX = getX() + radius * Math.cos(getYaw()) * Math.cos(getPitch());
            endPosZ = getZ() + radius * Math.sin(getYaw()) * Math.cos(getPitch());
            endPosY = getY() + radius * Math.sin(getPitch());
        }
    }

    public LaserHitResult raytraceEntities(Level world, Vec3 from, Vec3 to, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
        LaserHitResult result = new LaserHitResult();
        result.setBlockHit(world.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)));
        if (result.blockHit != null) {
            Vec3 hitVec = result.blockHit.getLocation();
            collidePosX = hitVec.x;
            collidePosY = hitVec.y;
            collidePosZ = hitVec.z;
            blockSide = result.blockHit.getDirection();
        } else {
            collidePosX = endPosX;
            collidePosY = endPosY;
            collidePosZ = endPosZ;
            blockSide = null;
        }
        List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, new AABB(Math.min(getX(), collidePosX), Math.min(getY(), collidePosY), Math.min(getZ(), collidePosZ), Math.max(getX(), collidePosX), Math.max(getY(), collidePosY), Math.max(getZ(), collidePosZ)).inflate(1, 1, 1));
        for (LivingEntity entity : entities) {
            if (entity == caster) {
                continue;
            }
            float pad = entity.getPickRadius() + 0.1f;
            AABB aabb = entity.getBoundingBox().inflate(pad, pad, pad);
            Optional<Vec3> hit = aabb.clip(from, to);
            if (aabb.contains(from)) {
                result.addEntityHit(entity);
            } else if (hit.isPresent()) {
                result.addEntityHit(entity);
            }
        }
        return result;
    }

    @Override
    public void push(Entity entityIn) {
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 1024;
    }

    private void updateWithDicer() {
        this.setYaw((float) ((caster.yHeadRot + 90) * Math.PI / 180.0d));
        this.setPitch((float) (-caster.getXRot() * Math.PI / 180.0d));
        Vec3 vecOffset1 = new Vec3(0, 0.05, 0.15).yRot((float) Math.toRadians(-caster.getYRot()));
        Vec3 vecOffset2 = new Vec3(0.15, 0, 0).yRot(-getYaw()).xRot(getPitch());
        this.setPos(caster.getX() + vecOffset1.x() + vecOffset2.x(), caster.getY() + 1.8f + vecOffset1.y() + vecOffset2.y(), caster.getZ() + vecOffset1.z() + vecOffset2.z());
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
    }

    public static class LaserHitResult {
        private BlockHitResult blockHit;

        private final List<LivingEntity> entities = new ArrayList<>();

        public BlockHitResult getBlockHit() {
            return blockHit;
        }

        public void setBlockHit(HitResult rayTraceResult) {
            if (rayTraceResult.getType() == HitResult.Type.BLOCK) this.blockHit = (BlockHitResult) rayTraceResult;
        }

        public void addEntityHit(LivingEntity entity) {
            entities.add(entity);
        }
    }
}

