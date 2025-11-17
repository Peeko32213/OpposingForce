package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.OpposingForceConfig;
import com.unusualmodding.opposing_force.entity.ai.goal.volt.VoltLeapGoal;
import com.unusualmodding.opposing_force.entity.ai.goal.volt.VoltShootGoal;
import com.unusualmodding.opposing_force.entity.ai.goal.volt.VoltShootInWaterGoal;
import com.unusualmodding.opposing_force.entity.ai.navigation.SmoothGroundPathNavigation;
import com.unusualmodding.opposing_force.entity.utils.AttackState;
import com.unusualmodding.opposing_force.entity.utils.EliteVariant;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.*;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class Volt extends Monster implements AttackState, PowerableMob, EliteVariant {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Volt.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CHARGED = SynchedEntityData.defineId(Volt.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> QUASAR = SynchedEntityData.defineId(Volt.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SWIMMING = SynchedEntityData.defineId(Volt.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDimensions FISH_IN_WATER_DIMENSIONS = EntityDimensions.scalable(1.1F, 0.5F);

    private boolean wasOnGround;
    public int leapCooldown = 20 * 2 + this.getRandom().nextInt(10 * 2);

    public boolean isLandNavigator;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState shootAnimationState = new AnimationState();
    public final AnimationState shootWaterAnimationState = new AnimationState();
    public final AnimationState twitch1AnimationState = new AnimationState();
    public final AnimationState twitch2AnimationState = new AnimationState();
    public final AnimationState jumpAnimationState = new AnimationState();
    public final AnimationState fallingAnimationState = new AnimationState();
    public final AnimationState landingAnimationState = new AnimationState();
    public final AnimationState swimmingAnimationState = new AnimationState();

    private int jumpTicks;
    private int fallingTicks;
    private int landingTicks;
    private int shootingTicks;

    private final byte TWITCH1 = 68;
    private final byte TWITCH2 = 69;

    public Volt(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 0.0F);
        this.switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.21F)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new VoltLeapGoal(this));
        this.goalSelector.addGoal(2, new VoltShootGoal(this));
        this.goalSelector.addGoal(2, new VoltShootInWaterGoal(this));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0F, 10));
        this.goalSelector.addGoal(3, new VoltRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new VoltBodyRotationControl(this);
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveControl = new MoveControl(this);
            this.navigation = new SmoothGroundPathNavigation(this, level());
            this.lookControl = new LookControl(this);
            this.isLandNavigator = true;
        } else {
            this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.2F, 0.1F, false);
            this.navigation = new AmphibiousPathNavigation(this, level());
            this.lookControl = new SmoothSwimmingLookControl(this, 10);
            this.isLandNavigator = false;
        }
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
                this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.4 * this.getSpeed(), 0.0));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, LevelReader level) {
        if (level.getFluidState(pos).is(FluidTags.WATER)) {
            return 10.0F;
        } else {
            return level.getPathfindingCostFromLightLevels(pos);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(CHARGED, false);
        this.entityData.define(QUASAR, false);
        this.entityData.define(SWIMMING, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AttackState", this.getAttackState());
        if (this.entityData.get(CHARGED)) {
            compoundTag.putBoolean("Charged", true);
        }
        compoundTag.putBoolean("Quasar", this.isElite());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAttackState(compoundTag.getInt("AttackState"));
        this.entityData.set(CHARGED, compoundTag.getBoolean("Charged"));
        this.setElite(compoundTag.getBoolean("Quasar"));
    }

    @Override
    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    @Override
    public void setAttackState(int attack) {
        this.entityData.set(ATTACK_STATE, attack);
    }

    @Override
    public boolean isPowered() {
        return this.entityData.get(CHARGED);
    }

    @Override
    public boolean isElite() {
        return this.entityData.get(QUASAR);
    }

    @Override
    public void setElite(boolean elite) {
        this.entityData.set(QUASAR, elite);
    }

    public boolean isVoltSwimming() {
        return this.entityData.get(SWIMMING);
    }

    public void setVoltSwimming(boolean swimming) {
        this.entityData.set(SWIMMING, swimming);
    }

    @Override
    public void thunderHit(@NotNull ServerLevel level, @NotNull LightningBolt lightning) {
        this.entityData.set(CHARGED, true);
        this.heal(this.getMaxHealth());
    }

    @Override
    protected void dropFromLootTable(@NotNull DamageSource source, boolean drops) {
        if (this.isElite()) {
            for (int i = 0; i < 2; i++) {
                super.dropFromLootTable(source, drops);
            }
        } else {
            super.dropFromLootTable(source, drops);
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float amount) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            return super.hurt(damageSource, amount);
        }
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return this.isVoltSwimming() ? FISH_IN_WATER_DIMENSIONS.scale(this.getScale()) : super.getDimensions(pose);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.leapCooldown > 0) {
            this.leapCooldown--;
        }

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        if (this.onGround() && !this.wasOnGround) {
            this.playSound(OPSoundEvents.VOLT_SQUISH.get(), 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
        }

        this.setVoltSwimming(this.isInWater());

        this.wasOnGround = this.onGround();

        final boolean canLandNavigate = !this.isInWater();
        if (!canLandNavigate && this.isLandNavigator) {
            switchNavigator(false);
        }
        if (canLandNavigate && !this.isLandNavigator) {
            switchNavigator(true);
        }

        if (shootingTicks > 0) shootingTicks--;
        if (jumpTicks > 0) jumpTicks--;
        if (fallingTicks > 0) fallingTicks--;
        if (landingTicks > 0) landingTicks--;
        if (shootingTicks == 0 && this.getPose() == OPPoses.SHOOTING.get()) this.setPose(Pose.STANDING);
        if (jumpTicks == 0 && this.getPose() == Pose.LONG_JUMPING) this.setPose(Pose.FALL_FLYING);
        if (this.getPose() == Pose.FALL_FLYING) {
            if (fallingTicks == 0) this.setPose(Pose.STANDING);
            if (this.onGround()) this.setPose(OPPoses.LANDING.get());
        }
        if (landingTicks == 0 && this.getPose() == OPPoses.LANDING.get()) this.setPose(Pose.STANDING);

        if (this.isPowered()) {
            if (this.tickCount % 100 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.heal(2);
            }
        }

        if (this.getPose() == Pose.STANDING) {
            if (this.random.nextInt(504) == 0 && !this.twitch2AnimationState.isStarted()) {
                this.level().broadcastEntityEvent(this, this.TWITCH1);
            }
            if (this.random.nextInt(505) == 0 && !this.twitch1AnimationState.isStarted()) {
                this.level().broadcastEntityEvent(this, this.TWITCH2);
            }
        }
    }

    private void setupAnimationStates() {
        if (shootingTicks == 0 && (this.shootAnimationState.isStarted() || this.shootWaterAnimationState.isStarted())) {
            this.shootAnimationState.stop();
            this.shootWaterAnimationState.stop();
        }
        if (jumpTicks == 0 && this.jumpAnimationState.isStarted()) this.jumpAnimationState.stop();
        if (fallingTicks == 0 && this.fallingAnimationState.isStarted()) this.fallingAnimationState.stop();
        if (landingTicks == 0 && this.landingAnimationState.isStarted()) this.landingAnimationState.stop();
        this.idleAnimationState.animateWhen(this.getDeltaMovement().horizontalDistance() <= 1.0E-5F && !this.isInWater() && this.getPose() == Pose.STANDING, this.tickCount);
        this.swimmingAnimationState.animateWhen(this.isInWater() && this.getPose() == Pose.STANDING, this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entityDataAccessor) {
        if (SWIMMING.equals(entityDataAccessor)) {
            this.refreshDimensions();
        }
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == Pose.FALL_FLYING) {
                this.jumpAnimationState.stop();
                this.fallingTicks = 100;
                this.fallingAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.LONG_JUMPING) {
                this.fallingAnimationState.stop();
                this.jumpTicks = 10;
                this.jumpAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == OPPoses.LANDING.get()) {
                this.landingTicks = 10;
                this.landingAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == OPPoses.SHOOTING.get()) {
                this.shootingTicks = 20;
                if (this.isInWater()) this.shootWaterAnimationState.start(this.tickCount);
                else this.shootAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.STANDING) {
                this.shootWaterAnimationState.stop();
                this.shootAnimationState.stop();
                this.jumpAnimationState.stop();
                this.fallingAnimationState.stop();
                this.landingAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
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
    public void handleEntityEvent(byte id) {
        if (id == this.TWITCH1) this.twitch1AnimationState.start(this.tickCount);
        else if (id == this.TWITCH2) this.twitch2AnimationState.start(this.tickCount);
        else super.handleEntityEvent(id);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.VOLT_IDLE.get();
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource source) {
        return OPSoundEvents.VOLT_HURT.get();
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return OPSoundEvents.VOLT_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(OPSoundEvents.VOLT_SQUISH.get(), 0.1F, 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypeTags.IS_FALL) || source.is(OPDamageTypes.ELECTRIC) || source.is(OPDamageTypes.ELECTRIFIED);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public void setEliteStats(Mob entity) {
        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(entity.getAttributeBaseValue(Attributes.MAX_HEALTH) * 1.5F);
        entity.setHealth(entity.getMaxHealth());
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

    @SuppressWarnings("unused")
    public static boolean canVoltSpawn(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (level.getLevel().isThundering() && OpposingForceConfig.VOLT_SPAWNS_DURING_STORM.get()) return checkMonsterSpawnRules(entityType, level, spawnType, pos, random);
        return checkVoltSpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean checkVoltSpawnRules(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return (pos.getY() <= OpposingForceConfig.VOLT_SPAWN_HEIGHT.get() && level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawnNoSkylight(level, pos, random) && checkMobSpawnRules(entityType, level, spawnType, pos, random));
    }

    public static boolean isDarkEnoughToSpawnNoSkylight(ServerLevelAccessor level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.SKY, pos) > 0 && !level.getLevel().isThundering()) {
            return false;
        } else {
            DimensionType dimension = level.dimensionType();
            int lightLimit = dimension.monsterSpawnBlockLightLimit();
            if (lightLimit < 15 && level.getBrightness(LightLayer.BLOCK, pos) > lightLimit) {
                return false;
            } else {
                int lightTest = level.getLevel().isThundering() ? level.getMaxLocalRawBrightness(pos, 10) : level.getMaxLocalRawBrightness(pos);
                return lightTest <= dimension.monsterSpawnLightTest().sample(random);
            }
        }
    }

    private static class VoltRandomStrollGoal extends RandomStrollGoal {

        private final Volt entity;

        public VoltRandomStrollGoal(Volt entity, double speedModifier) {
            super(entity, speedModifier);
            this.entity = entity;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.entity.isLandNavigator && !entity.isInWater();
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && this.entity.isLandNavigator && !entity.isInWater();
        }
    }

    @SuppressWarnings("unused, FieldCanBeLocal")
    private static class VoltBodyRotationControl extends BodyRotationControl {

        private final Mob mob;
        private int headStableTime;
        private float lastStableYHeadRot;

        public VoltBodyRotationControl(Mob mob) {
            super(mob);
            this.mob = mob;
        }

        // just so it faces its target more reliably when not moving
        @Override
        public void clientTick() {
            this.mob.yBodyRot = this.mob.getYRot();
            this.rotateHeadIfNecessary();
            this.lastStableYHeadRot = this.mob.yHeadRot;
            this.headStableTime = 0;
        }

        private void rotateHeadIfNecessary() {
            this.mob.yHeadRot = Mth.rotateIfNecessary(this.mob.yHeadRot, this.mob.yBodyRot, (float) this.mob.getMaxHeadYRot());
        }
    }
}
