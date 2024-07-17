package com.peeko32213.hole.common.entity.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class AbstractMonster extends Monster {

    private static final EntityDataAccessor<Integer> ANIM_TIMER = SynchedEntityData.defineId(AbstractMonster.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RANDOM_NUMBER = SynchedEntityData.defineId(AbstractMonster.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(AbstractMonster.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COMBAT_STATE = SynchedEntityData.defineId(AbstractMonster.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ENTITY_STATE = SynchedEntityData.defineId(AbstractMonster.class, EntityDataSerializers.INT);

    protected AbstractMonster(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIM_TIMER, 0);
        this.entityData.define(RANDOM_NUMBER,0);
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(COMBAT_STATE, 0);
        this.entityData.define(ENTITY_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("randomNr", this.getRandomNumber());
        compound.putInt("animTimer", this.getAnimationTimer());
    }

    public int getRandomAnimationNumber(int nr) {
        setRandomNumber(random.nextInt(nr));
        return getRandomNumber();
    }
    public int getRandomAnimationNumber() {
        setRandomNumber(random.nextInt(100));
        return getRandomNumber();
    }

    public int getAnimationState() {

        return this.entityData.get(ANIMATION_STATE);
    }

    public void setAnimationState(int anim) {

        this.entityData.set(ANIMATION_STATE, anim);
    }

    public int getCombatState() {

        return this.entityData.get(COMBAT_STATE);
    }

    public void setCombatState(int anim) {

        this.entityData.set(COMBAT_STATE, anim);
    }

    public int getEntityState() {

        return this.entityData.get(ENTITY_STATE);
    }

    public void setEntityState(int anim) {

        this.entityData.set(ENTITY_STATE, anim);
    }

    public int getRandomNumber() {
        return this.entityData.get(RANDOM_NUMBER);
    }

    public void setRandomNumber(int nr) {
        this.entityData.set(RANDOM_NUMBER,nr);
    }

    public boolean playingAnimation() {
        return getAnimationTimer() > 0;
    }

    public int getAnimationTimer() {
        return this.entityData.get(ANIM_TIMER);
    }

    public void setAnimationTimer(int time) {
        this.entityData.set(ANIM_TIMER,time);
    }

}
