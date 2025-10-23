package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.ai.goal.TerrorAttackGoal;
import com.unusualmodding.opposing_force.entity.ai.goal.TerrorRandomStrollGoal;
import com.unusualmodding.opposing_force.entity.ai.navigation.SmoothGroundPathNavigation;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Terror extends Monster {

    private static final EntityDataAccessor<Boolean> SAWING = SynchedEntityData.defineId(Terror.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_LEGS = SynchedEntityData.defineId(Terror.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(Terror.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> FLOP_TIME = SynchedEntityData.defineId(Terror.class, EntityDataSerializers.INT);

    private static final EntityDimensions FISH_OUT_OF_WATER_DIMENSIONS = EntityDimensions.scalable(1.3F, 1.7F);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();
    public final AnimationState cooldownAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState growLegsAnimationState = new AnimationState();
    public final AnimationState startSawingAnimationState = new AnimationState();
    public final AnimationState sawingAnimationState = new AnimationState();
    public final AnimationState retractLegsAnimationState = new AnimationState();

    public boolean isLandNavigator;
    private int growLegsTicks;
    private int retractLegsTicks;
    private int startSawingTicks;
    private int stopSawingTicks;

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    public Terror(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        this.switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 32.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new TerrorAttackGoal(this));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0F, 20));
        this.goalSelector.addGoal(3, new TerrorRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new SmoothGroundPathNavigation(this, level());
            this.lookControl = new LookControl(this);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new SmoothSwimmingMoveControl(this, 128, 10, 0.2F, 0.1F, false);
            this.navigation = new AmphibiousPathNavigation(this, level());
            this.lookControl = new SmoothSwimmingLookControl(this, 10);
            this.isLandNavigator = false;
        }
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        if (level.getFluidState(pos).is(FluidTags.WATER)) {
            return 10.0F;
        } else {
            return level.getPathfindingCostFromLightLevels(pos);
        }
    }

    @Override
    public float getStepHeight() {
        if (this.isInWater()) {
            return 1.0F;
        }
        return 0.6F;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions size) {
        return size.height * 0.7F;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.horizontalCollision) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.3 * this.getSpeed(), 0.0));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void tick () {
        super.tick();
        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }

        final boolean canLandNavigate = !this.isInWater() && this.hasLegs();
        if (!canLandNavigate && this.isLandNavigator) {
            switchNavigator(false);
        }
        if (canLandNavigate && !this.isLandNavigator) {
            switchNavigator(true);
        }

        // grow legs
        if (!this.hasLegs() && !this.isInWater() && this.onGround()) {
            if (this.getPose() != OPPoses.GROWING_LEGS.get() && this.getFlopTime() == 0) {
                this.setPose(OPPoses.GROWING_LEGS.get());
                this.growLegsTicks = 40;
            }
        }
        if (this.growLegsTicks > 0) {
            this.growLegsTicks--;
            if (this.growLegsTicks == 0 && this.getPose() == OPPoses.GROWING_LEGS.get()) {
                this.setPose(Pose.STANDING);
                this.setHasLegs(true);
                this.setFlopTime(20 + this.getRandom().nextInt(2 * 10));
            }
        }
        // retract legs
        if (this.hasLegs() && this.isInWater()) {
            if (this.getPose() != OPPoses.RETRACTING_LEGS.get()) {
                this.setPose(OPPoses.RETRACTING_LEGS.get());
                this.retractLegsTicks = 10;
            }
        }
        if (this.retractLegsTicks > 0) {
            this.retractLegsTicks--;
            if (this.retractLegsTicks == 0 && this.getPose() == OPPoses.RETRACTING_LEGS.get()) {
                this.setPose(Pose.STANDING);
                this.setHasLegs(false);
                this.setFlopTime(20 + this.getRandom().nextInt(2 * 10));
            }
        }

        if (this.getFlopTime() > 0 && this.onGround() && !this.isInWater()) this.setFlopTime(this.getFlopTime() - 1);

        if (startSawingTicks > 0) startSawingTicks--;
        if (stopSawingTicks > 0) stopSawingTicks--;
        if (startSawingTicks == 0 && this.getPose() == OPPoses.START_SAWING.get()) this.setPose(OPPoses.SAWING.get());
        if (stopSawingTicks == 0 && this.getPose() == OPPoses.RECOVERING.get()) this.setPose(Pose.STANDING);
    }

    private void setupAnimationStates() {
        if (growLegsTicks == 0 && this.growLegsAnimationState.isStarted()) this.growLegsAnimationState.stop();
        if (retractLegsTicks == 0 && this.retractLegsAnimationState.isStarted()) this.retractLegsAnimationState.stop();
        if (startSawingTicks == 0 && this.startSawingAnimationState.isStarted()) this.startSawingAnimationState.stop();
        if (stopSawingTicks == 0 && this.cooldownAnimationState.isStarted()) this.cooldownAnimationState.stop();
        this.idleAnimationState.animateWhen(!this.isInWaterOrBubble() && this.hasLegs() && this.getDeltaMovement().horizontalDistance() <= 1.0E-5F, this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble() && !this.hasLegs() && this.getPose() != OPPoses.GROWING_LEGS.get(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isInWaterOrBubble() && this.getPose() != OPPoses.RETRACTING_LEGS.get(), this.tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float f1 = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float f2 = Math.min(f1 * 10.0F, 1.0F);
        this.walkAnimation.update(f2, 0.4F);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (HAS_LEGS.equals(entityDataAccessor)) {
            this.refreshDimensions();
        }
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == OPPoses.GROWING_LEGS.get()) {
                this.flopAnimationState.stop();
                this.growLegsAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == OPPoses.RETRACTING_LEGS.get()) {
                this.swimAnimationState.stop();
                this.retractLegsAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == OPPoses.START_SAWING.get()) {
                this.startSawingTicks = 20;
                this.startSawingAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == OPPoses.SAWING.get()) {
                this.startSawingAnimationState.stop();
                this.sawingAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == OPPoses.RECOVERING.get()) {
                this.sawingAnimationState.stop();
                this.stopSawingTicks = 50;
                this.cooldownAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.STANDING) {
                this.growLegsAnimationState.stop();
                this.retractLegsAnimationState.stop();
                this.sawingAnimationState.stop();
                this.startSawingAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return this.hasLegs() ? FISH_OUT_OF_WATER_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose);
    }

    @Override
    public void refreshDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.refreshDimensions();
        this.setPos(x, y, z);
    }

    @Override
    public boolean isImmobile() {
        return super.isImmobile() || this.getPose() == OPPoses.GROWING_LEGS.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SAWING, false);
        this.entityData.define(HAS_LEGS, false);
        this.entityData.define(RUNNING, false);
        this.entityData.define(FLOP_TIME, 20 + this.getRandom().nextInt(2 * 10));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Sawing", this.isSawing());
        compoundTag.putBoolean("HasLegs", this.hasLegs());
        compoundTag.putInt("FlopTime", this.getFlopTime());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSawing(compoundTag.getBoolean("Sawing"));
        this.setHasLegs(compoundTag.getBoolean("HasLegs"));
        this.setFlopTime(compoundTag.getInt("FlopTime"));
    }

    public boolean isSawing() {
        return this.entityData.get(SAWING);
    }

    public void setSawing(boolean sawing) {
        this.entityData.set(SAWING, sawing);
    }

    public boolean hasLegs() {
        return this.entityData.get(HAS_LEGS);
    }

    public void setHasLegs(boolean hasLegs) {
        this.entityData.set(HAS_LEGS, hasLegs);
    }

    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }

    public void setRunning(boolean running) {
        this.entityData.set(RUNNING, running);
    }

    public int getFlopTime() {
        return this.entityData.get(FLOP_TIME);
    }

    public void setFlopTime(int flopTime) {
        this.entityData.set(FLOP_TIME, flopTime);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.TERROR_IDLE.get();
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return OPSoundEvents.TERROR_HURT.get();
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return OPSoundEvents.TERROR_DEATH.get();
    }

    protected SoundEvent getFlopSound() {
        return OPSoundEvents.TERROR_FLOP.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 160;
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return pos.getY() <= -16 && level.getRawBrightness(pos, 0) == 0 && level.getRandom().nextFloat() <= 0.2F && level.getBlockState(pos).is(Blocks.WATER);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) {
        return level.isUnobstructed(this);
    }
}
