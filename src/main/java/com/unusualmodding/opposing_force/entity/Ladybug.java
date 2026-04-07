package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.ai.goal.LadybugAttackGoal;
import com.unusualmodding.opposing_force.entity.ai.goal.LadybugFlightGoal;
import com.unusualmodding.opposing_force.entity.ai.navigation.NoSpinFlyingPathNavigation;
import com.unusualmodding.opposing_force.entity.base.OPMonster;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class Ladybug extends OPMonster implements FlyingAnimal {

    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(Ladybug.class, EntityDataSerializers.BOOLEAN);

    public boolean isLandNavigator;
    public int flightTicks = 0;
    public int groundTicks = 0;
    public boolean landingFlag;
    public int flightCooldown = 0;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();
    public final AnimationState bashAnimationState = new AnimationState();
    public final AnimationState airBashAnimationState = new AnimationState();

    private int bashTicks;

    public Ladybug(EntityType<? extends OPMonster> entityType, Level level) {
        super(entityType, level);
        this.switchNavigator(false);
        this.setMaxUpStep(1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.FLYING_SPEED, 1.25D)
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 7.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LadybugFlightGoal(this));
        this.goalSelector.addGoal(2, new LadybugAttackGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D) {
            @Override
            public boolean canUse() {
                return super.canUse() && !Ladybug.this.isFlying();
            }
        });
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = this.createNavigation(this.level());
            this.isLandNavigator = true;
        } else {
            this.moveControl = new FlyingMoveControl(this, 20, true);
            NoSpinFlyingPathNavigation navigation = new NoSpinFlyingPathNavigation(this, this.level()) {
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

    public void strongKnockback(Entity entity, double horizontalStrength, double verticalStrength) {
        double x = entity.getX() - this.getX();
        double y = entity.getZ() - this.getZ();
        double scale = Math.max(x * x + y * y, 0.001D);
        entity.push(x / scale * horizontalStrength, verticalStrength, y / scale * horizontalStrength);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source.is(DamageTypeTags.IS_PROJECTILE)) {
            amount *= 0.6F;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.bashTicks > 0) bashTicks--;
        if (this.bashTicks == 0 && this.getPose() == OPPoses.ATTACKING.get()) this.setPose(Pose.STANDING);

        this.tickFlight();
    }

    @Override
    public void setupAnimationStates() {
        if (this.bashTicks == 0 && (this.bashAnimationState.isStarted() || this.airBashAnimationState.isStarted())) {
            this.bashAnimationState.stop();
            this.airBashAnimationState.stop();
        }
        this.idleAnimationState.animateWhen(!this.isFlying() && this.getPose() != OPPoses.ATTACKING.get(), this.tickCount);
        this.flyingAnimationState.animateWhen(this.isFlying() && this.getPose() != OPPoses.ATTACKING.get(), this.tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float f1 = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float f2 = Math.min(f1 * 12.0F, 1.0F);
        this.walkAnimation.update(f2, 0.4F);
    }

    public void tickFlight() {
        if (this.isFlying()) {
            this.flightTicks++;
            this.setNoGravity(true);
            if (this.isLandNavigator) switchNavigator(false);
            if (groundTicks > 0) this.setFlying(false);
        } else {
            this.flightTicks = 0;
            this.setNoGravity(false);
            if (!this.isLandNavigator) switchNavigator(true);
        }
        if (groundTicks > 0) groundTicks--;

        if (!level().isClientSide) {
            if (isFlying() && this.isAlive() && !this.isVehicle()) {
                if (landingFlag) this.setDeltaMovement(this.getDeltaMovement().add(0, -0.08D, 0));
                if ((horizontalCollision || this.isInWaterOrBubble()) && !landingFlag) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0, 0.05D, 0));
                }
            }
            if (this.isFlying() && (flightTicks > 40 || flightCooldown > 0) && this.onGround()) this.setFlying(false);
        }

        if (flightCooldown > 0) flightCooldown--;
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == OPPoses.ATTACKING.get()) {
                this.bashTicks = 30;
                if (this.isFlying()) this.airBashAnimationState.start(this.tickCount);
                else this.bashAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.STANDING) {
                this.bashAnimationState.stop();
                this.airBashAnimationState.stop();
            }
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

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
        RandomSource random = level.getRandom();
        if (random.nextInt(this.getEliteSpawnChance()) == 0) {
            this.setElite(true);
            this.setEliteStats(this);
        }
        return spawnData;
    }
}
