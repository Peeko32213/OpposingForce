package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.ai.goal.LadybugFlightGoal;
import com.unusualmodding.opposing_force.entity.ai.navigation.SmoothGroundPathNavigation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Ladybug extends Monster implements FlyingAnimal {

    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(Ladybug.class, EntityDataSerializers.BOOLEAN);

    public boolean isLandNavigator;
    public int flightTicks = 0;
    public int groundTicks = 0;
    public boolean landingFlag;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();

    public Ladybug(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FLYING_SPEED, 1.1D)
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LadybugFlightGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D) {
            @Override
            public boolean canUse() {
                return super.canUse() && !Ladybug.this.isFlying();
            }
        });
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new SmoothGroundPathNavigation(this, level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new FlyingMoveControl(this, 20, true);
            FlyingPathNavigation navigation = new FlyingPathNavigation(this, level()) {
                @Override
                public boolean isStableDestination(BlockPos pos) {
                    return !level().getBlockState(pos.below()).isAir();
                }
            };
            navigation.setCanOpenDoors(false);
            navigation.setCanFloat(false);
            navigation.setCanPassDoors(true);
            this.navigation = navigation;
            this.isLandNavigator = false;
        }
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos blockPos, @NotNull LevelReader level) {
        return this.isFlying() ? (level.getBlockState(blockPos).isAir() ? 10.0F : 0.0F) : super.getWalkTargetValue(blockPos, level);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource damageSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }

        this.tickFlight();
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.getDeltaMovement().horizontalDistance() <= 1.0E-5F && !this.isFlying() && this.getPose() == Pose.STANDING, this.tickCount);
        this.flyingAnimationState.animateWhen(this.isFlying(), this.tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float f1 = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float f2 = Math.min(f1 * 10.0F, 1.0F);
        this.walkAnimation.update(f2, 0.4F);
    }

    public void tickFlight() {
        if (this.isFlying()) {
            flightTicks++;
            this.setNoGravity(true);
            if (this.isLandNavigator) {
                switchNavigator(false);
            }
            if (groundTicks > 0) {
                this.setFlying(false);
            }
        } else {
            flightTicks = 0;
            this.setNoGravity(false);
            if (!this.isLandNavigator) {
                switchNavigator(true);
            }
        }
        if (groundTicks > 0) {
            groundTicks--;
        }

        if (!level().isClientSide) {
            if (isFlying() && this.isAlive() && !this.isVehicle()) {
                if (landingFlag) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0, -0.08D, 0));
                }
                if ((horizontalCollision || this.isInWaterOrBubble()) && !landingFlag) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0, 0.05D, 0));
                }
            }
            if (this.isFlying() && flightTicks > 40 && this.onGround()) {
                this.setFlying(false);
            }
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
//            if (this.getPose() == Pose.FALL_FLYING) {
//                this.flyingAnimationState.start(this.tickCount);
//            }
//            else if (this.getPose() == Pose.STANDING) {
//                this.flyingAnimationState.stop();
//            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FLYING, false);
    }

    @Override
    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(FLYING, flying);
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SPIDER_AMBIENT;
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.SPIDER_HURT;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }

    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockState) {
        if (!this.isFlying()) {
            this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.0F);
        }
    }
}
