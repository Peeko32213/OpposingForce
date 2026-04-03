package com.unusualmodding.opposing_force.entity.base;

import com.unusualmodding.opposing_force.entity.ai.control.OPBodyRotationControl;
import com.unusualmodding.opposing_force.entity.ai.control.OPLookControl;
import com.unusualmodding.opposing_force.entity.ai.control.OPMoveControl;
import com.unusualmodding.opposing_force.entity.ai.navigation.SmoothGroundPathNavigation;
import com.unusualmodding.opposing_force.utils.SmoothAnimationState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class OPMonster extends TameableMonster {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(OPMonster.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(OPMonster.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ELITE = SynchedEntityData.defineId(OPMonster.class, EntityDataSerializers.BOOLEAN);

    private float tailYaw;
    private float prevTailYaw;

    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();

    protected OPMonster(EntityType<? extends TameableMonster> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new OPMoveControl(this);
        this.lookControl = new OPLookControl(this);
    }

    // Navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new OPBodyRotationControl(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothGroundPathNavigation(this, level);
    }

    public boolean refuseToMove() {
        return false;
    }

    public boolean refuseToLook() {
        return false;
    }

    // Elite
    protected int getEliteSpawnChance() {
        return 50;
    }

    protected void setEliteStats(Mob entity) {
        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(entity.getAttributeBaseValue(Attributes.MAX_HEALTH) * 1.5F);
        entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(entity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) * 1.25F);
        entity.setHealth(entity.getMaxHealth());
    }

    @Override
    public void tick () {
        super.tick();
        this.tickTailYaw();
        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }
    }

    // Animation
    public void setupAnimationStates() {
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float pos = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float speed = Math.min(pos * 10.0F, 1.0F);
        this.walkAnimation.update(speed, 0.4F);
    }

    // Tail yaw
    public void tickTailYaw() {
        this.prevTailYaw = this.tailYaw;
        this.tailYaw += (-(this.yBodyRot - this.yBodyRotO) - this.tailYaw) * 0.15F;
    }

    public float getTailYaw(float partialTick) {
        return (prevTailYaw + (tailYaw - prevTailYaw) * partialTick);
    }

    // Data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(RUNNING, false);
        this.entityData.define(ELITE, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Elite", this.isElite());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setElite(compoundTag.getBoolean("Elite"));
    }

    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }

    public void setRunning(boolean running) {
        this.entityData.set(RUNNING, running);
    }

    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    public void setAttackState(int state) {
        this.entityData.set(ATTACK_STATE, state);
    }

    public boolean isElite() {
        return this.entityData.get(ELITE);
    }

    public void setElite(boolean elite) {
        this.entityData.set(ELITE, elite);
    }
}
