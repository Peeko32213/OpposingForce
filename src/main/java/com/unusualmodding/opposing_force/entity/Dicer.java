package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.ai.goal.*;
import com.unusualmodding.opposing_force.entity.ai.navigation.SmoothGroundPathNavigation;
import com.unusualmodding.opposing_force.entity.base.IAnimatedAttacker;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Dicer extends Monster implements IAnimatedAttacker {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Dicer.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(Dicer.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> LASER_COOLDOWN = SynchedEntityData.defineId(Dicer.class, EntityDataSerializers.INT);

    @OnlyIn(Dist.CLIENT)
    public Vec3[] headPos;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState slice1AnimationState = new AnimationState();
    public final AnimationState slice2AnimationState = new AnimationState();
    public final AnimationState crossSliceAnimationState = new AnimationState();
    public final AnimationState laserAnimationState = new AnimationState();

    public Dicer(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 10;
        if (level.isClientSide) {
            this.headPos = new Vec3[] {new Vec3(0, 0, 0)};
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 32.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.18D)
            .add(Attributes.ATTACK_DAMAGE, 8.0D)
            .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new DicerAttackGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        return new SmoothGroundPathNavigation(this, level);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getLaserCooldown() > 0) {
            this.setLaserCooldown(this.getLaserCooldown() - 1);
        }

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        int attackState = this.getAttackState();
        this.idleAnimationState.animateWhen(this.getDeltaMovement().horizontalDistanceSqr() <= 1.0E-6, this.tickCount);
        this.crossSliceAnimationState.animateWhen(attackState == 2, this.tickCount);
        this.laserAnimationState.animateWhen(attackState == 3, this.tickCount);
        if (attackState > 0) {
            this.idleAnimationState.stop();
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (super.doHurtTarget(entity)) {
            this.playSound(OPSoundEvents.DICER_ATTACK.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (ATTACK_STATE.equals(entityDataAccessor)) {
            if (this.getAttackState() == 1) {
                if (this.getRandom().nextBoolean()) {
                    this.slice2AnimationState.start(this.tickCount);
                } else {
                    this.slice1AnimationState.start(this.tickCount);
                }
            } else if (this.getAttackState() == 0) {
                this.slice1AnimationState.stop();
                this.slice2AnimationState.stop();
                this.crossSliceAnimationState.stop();
                this.laserAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(RUNNING, false);
        this.entityData.define(LASER_COOLDOWN, 120);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AttackState", this.getAttackState());
        compoundTag.putBoolean("Running", this.isRunning());
        compoundTag.putInt("LaserCooldown", this.getLaserCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAttackState(compoundTag.getInt("AttackState"));
        this.setRunning(compoundTag.getBoolean("Running"));
        this.setLaserCooldown(compoundTag.getInt("LaserCooldown"));
    }

    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }

    public void setRunning(boolean running) {
        this.entityData.set(RUNNING, running);
    }

    public int getLaserCooldown() {
        return this.entityData.get(LASER_COOLDOWN);
    }

    public void setLaserCooldown(int cooldown) {
        this.entityData.set(LASER_COOLDOWN, cooldown);
    }

    public void laserCooldown() {
        this.entityData.set(LASER_COOLDOWN, 120);
    }

    @Override
    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    @Override
    public void setAttackState(int state) {
        this.entityData.set(ATTACK_STATE, state);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.DICER_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return OPSoundEvents.DICER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return OPSoundEvents.DICER_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.1F, 1.3F);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource damageSource, int amount, boolean drops) {
        super.dropCustomDeathLoot(damageSource, amount, drops);
        Entity entity = damageSource.getEntity();
        if (entity instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                ItemStack itemstack = new ItemStack(OPItems.DICER_HEAD.get());
                creeper.increaseDroppedSkulls();
                this.spawnAtLocation(itemstack);
            }
        }
    }
}
