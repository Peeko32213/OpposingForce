package com.unusualmodding.opposing_force.entity.projectile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;

import static com.unusualmodding.opposing_force.OpposingForce.modPrefix;

/**
 * Represents an entity with an animated texture that can be synchronized across clients.
 * This entity supports animations with different speeds, counts, and time intervals.
 */
public abstract class AnimatedTextureEntity extends AbstractHurtingProjectile {
    // EntityDataAccessors to sync animation states
    public static final EntityDataAccessor<Integer> ANIMATION_COUNT = SynchedEntityData.defineId(AnimatedTextureEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> ANIMATION_SPEED = SynchedEntityData.defineId(AnimatedTextureEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> ANIMATION_TIME = SynchedEntityData.defineId(AnimatedTextureEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Boolean> ANIMATION_TIME_DONE = SynchedEntityData.defineId(AnimatedTextureEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> ANIMATION_COUNT_DONE = SynchedEntityData.defineId(AnimatedTextureEntity.class, EntityDataSerializers.BOOLEAN);

    //example of what the texture loc shoud look like e.g. it should end with _ and the animation will add .png and the animation count e.g. 0,1,2,3 etc
    private static final ResourceLocation TEXTURE_LOCATION = modPrefix("textures/entity/dimensionalgate_");

    /**
     * Constructs the animated texture entity.
     *
     * @param pEntityType The entity type.
     * @param pLevel      The world in which the entity exists.
     */
    protected AnimatedTextureEntity(EntityType<? extends AbstractHurtingProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public AnimatedTextureEntity(EntityType<? extends AbstractHurtingProjectile> pEntityType, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public AnimatedTextureEntity(EntityType<? extends AbstractHurtingProjectile> pEntityType, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
        super(pEntityType, pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }
    /**
     * Handles the animation logic by returning the correct texture location based on the current animation count.
     *
     * @param resourceLocation The base texture location.
     * @param count            The total animation frame count.
     * @param speed            The speed at which the animation progresses.
     * @param timer            The duration for which each frame lasts.
     * @return The {@link ResourceLocation} of the animated texture.
     */
    public ResourceLocation animate(ResourceLocation resourceLocation, int count, float speed, int timer) {
        if (getAnimationSpeed() == 0) {
            setAnimationSpeed(speed);
        }
        if (getAnimationTime() <= 0) {
            setAnimationTime(timer);
            setAnimationCountDone(false);
            setAnimationCount(getAnimationCount() - 1);
        }
        if (getAnimationCount() == 0 && getAnimationTime() <= 0) {
            setAnimationCount(count);
        }
        if (!getAnimationTimeDone()) {
            setAnimationTimeDone(true);
        }
        if (getAnimationCountDone()) {
            setAnimationCount(count);
            setAnimationCountDone(false);
        }
        if (getAnimationCount() < 0) {
            setAnimationCountDone(true);
            return new ResourceLocation(resourceLocation.toString() + 0 + ".png");
        }
        setAnimationTime(getAnimationTime() - getAnimationSpeed());
        return new ResourceLocation(resourceLocation.toString() + getAnimationCount() + ".png");
    }

    /**
     * Resets the animation state to its initial values.
     */
    public void resetAnimation() {
        setAnimationTime(0);
        setAnimationCount(0);
        setAnimationSpeed(0);
        setAnimationTimeDone(false);
        setAnimationCountDone(false);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(ANIMATION_COUNT, 0);
        this.getEntityData().define(ANIMATION_SPEED, 0f);
        this.getEntityData().define(ANIMATION_TIME, 0f);
        this.getEntityData().define(ANIMATION_TIME_DONE, false);
        this.getEntityData().define(ANIMATION_COUNT_DONE, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("animation_count", this.getAnimationCount());
        pCompound.putFloat("animation_speed", this.getAnimationSpeed());
        pCompound.putFloat("animation_time", this.getAnimationTime());
        pCompound.putBoolean("animation_time_done", this.getAnimationTimeDone());
        pCompound.putBoolean("animation_count_done", this.getAnimationCountDone());
        super.addAdditionalSaveData(pCompound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        setAnimationCount(pCompound.getInt("animation_count"));
        setAnimationSpeed(pCompound.getFloat("animation_speed"));
        setAnimationTime(pCompound.getFloat("animation_time"));
        setAnimationCountDone(pCompound.getBoolean("animation_count_done"));
        setAnimationTimeDone(pCompound.getBoolean("animation_time_done"));
        super.readAdditionalSaveData(pCompound);
    }

    public void setAnimationTimeDone(boolean animDone) {
        this.entityData.set(ANIMATION_TIME_DONE, animDone);
    }

    public boolean getAnimationTimeDone() {
        return this.entityData.get(ANIMATION_TIME_DONE);
    }

    public void setAnimationCountDone(boolean animDone) {
        this.entityData.set(ANIMATION_COUNT_DONE, animDone);
    }

    public boolean getAnimationCountDone() {
        return this.entityData.get(ANIMATION_COUNT_DONE);
    }

    public void setAnimationTime(float time) {
        this.entityData.set(ANIMATION_TIME, time);
    }

    public float getAnimationTime() {
        return this.entityData.get(ANIMATION_TIME);
    }

    public void setAnimationSpeed(float speed) {
        this.entityData.set(ANIMATION_SPEED, speed);
    }

    public float getAnimationSpeed() {
        return this.entityData.get(ANIMATION_SPEED);
    }

    public int getAnimationCount() {
        return this.entityData.get(ANIMATION_COUNT);
    }

    public void setAnimationCount(int animationCount) {
        this.entityData.set(ANIMATION_COUNT, animationCount);
    }

    /**
     * @return The size of the entity.
     */
    public  abstract float size();

    /**
     * @return The {@link ResourceLocation} for the texture of this entity.
     */
    public abstract ResourceLocation getTextureLocation();

    /**
     * @return The total number of textures used in the animation.
     */
    public abstract int getTextureCount();

    /**
     * @return The speed at which the animation progresses.
     */
    public abstract float animationSpeed();

    /**
     * @return The duration of the animation time.
     */
    public abstract int animationTime();
}
