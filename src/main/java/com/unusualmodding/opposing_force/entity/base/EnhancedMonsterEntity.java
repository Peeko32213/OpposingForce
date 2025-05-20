package com.unusualmodding.opposing_force.entity.base;

import com.unusualmodding.opposing_force.entity.ai.state.IStateAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

public  abstract class EnhancedMonsterEntity extends Monster implements IStateAction, GeoAnimatable, GeoEntity {

    private static final EntityDataAccessor<Boolean> PERFORMING_ACTION = SynchedEntityData.defineId(EnhancedMonsterEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(EnhancedMonsterEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(EnhancedMonsterEntity.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public int attackTick = 0;
    public float attackDamage = (float) EnhancedMonsterEntity.this.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
    public float attackKnockback = (float) EnhancedMonsterEntity.this.getAttribute(Attributes.ATTACK_KNOCKBACK).getValue();

    protected EnhancedMonsterEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PERFORMING_ACTION, false);
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(RUNNING, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("attackState", this.getAttackState());
        compound.putInt("attackTick", attackTick);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setAttackState(compound.getInt("attackState"));
        this.attackTick = compound.getInt("attackTick");
    }

    public boolean getBooleanState(EntityDataAccessor<Boolean> pKey) {
        return this.entityData.get(pKey);
    }
    public void setBooleanState(EntityDataAccessor<Boolean> pKey, boolean state) {
        this.entityData.set(pKey, state);
    }

    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }
    public void setAttackState(int attack) {
        this.entityData.set(ATTACK_STATE, attack);
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public float getAttackKnockback() {
        return attackKnockback;
    }

    @Override
    public boolean isAlliedTo(Entity pEntity) {
        return pEntity.is(this);
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.getPerformingAction()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            pTravelVector = Vec3.ZERO;
        }
        super.travel(pTravelVector);
    }

    public boolean isStillEnough() {
        return this.getDeltaMovement().horizontalDistance() < 0.05;
    }

    // Set running
    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }
    public void setRunning(boolean bool) {
        this.entityData.set(RUNNING, bool);
    }

    public boolean getPerformingAction() {
        return this.entityData.get(PERFORMING_ACTION);
    }
    public void setPerformingAction(boolean action) {
        this.entityData.set(PERFORMING_ACTION, action);
    }

    @Override
    public boolean getAction() {
        return getPerformingAction();
    }

    @Override
    public void setAction(boolean action) {
        setPerformingAction(action);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }
}
