package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.ai.goal.TartAttackGoal;
import com.unusualmodding.opposing_force.entity.ai.navigation.SmoothGroundPathNavigation;
import com.unusualmodding.opposing_force.entity.utils.AttackState;
import com.unusualmodding.opposing_force.entity.utils.EliteVariant;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import com.unusualmodding.opposing_force.entity.utils.collisions.CustomCollisions;
import com.unusualmodding.opposing_force.entity.utils.collisions.CustomCollisionsMoveControl;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class Tart extends Monster implements AttackState, CustomCollisions, EliteVariant {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Tart.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> GREEN = SynchedEntityData.defineId(Tart.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState fallAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    private int attackTicks;

    public Tart(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new CustomCollisionsMoveControl(this);
        this.setMaxUpStep(1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TartAttackGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new TartPathNavigation(this, level);
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource source, int damage, boolean drops) {
        super.dropCustomDeathLoot(source, damage, drops);
        Entity entity = source.getEntity();
        if (entity instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                creeper.increaseDroppedSkulls();
                this.spawnAtLocation(OPItems.TART_HEAD.get());
            }
        }
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypes.IN_WALL);
    }

    @Override
    public boolean isColliding(@NotNull BlockPos pos, BlockState blockstate) {
        return !blockstate.is(BlockTags.LEAVES) && super.isColliding(pos, blockstate);
    }

    @Override
    protected @NotNull Vec3 collide(@NotNull Vec3 vec3) {
        return CustomCollisions.getAllowedMovementForEntity(this, vec3);
    }

    @Override
    public boolean canPassThrough(BlockPos mutablePos, BlockState blockstate, VoxelShape voxelshape) {
        return blockstate.is(BlockTags.LEAVES);
    }

    @Override
    public void makeStuckInBlock(BlockState blockstate, @NotNull Vec3 vec3) {
        if (!blockstate.is(BlockTags.LEAVES)) super.makeStuckInBlock(blockstate, vec3);
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

        if (attackTicks > 0) attackTicks--;
        if (attackTicks == 0 && this.getPose() == OPPoses.ATTACKING.get()) this.setPose(Pose.STANDING);

        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.getPose() == Pose.STANDING, this.tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float f1 = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float f2 = Math.min(f1 * 12.0F, 1.0F);
        this.walkAnimation.update(f2, 0.4F);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == OPPoses.ATTACKING.get()) {
                this.attackTicks = 40;
                this.attackAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.STANDING) {
                this.attackAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(GREEN, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Green", this.isElite());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setElite(compoundTag.getBoolean("Green"));
    }

    @Override
    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    @Override
    public void setAttackState(int attackState) {
        this.entityData.set(ATTACK_STATE, attackState);
    }

    @Override
    public boolean isElite() {
        return this.entityData.get(GREEN);
    }

    @Override
    public void setElite(boolean elite) {
        this.entityData.set(GREEN, elite);
    }

    @Override
    protected @NotNull SoundEvent getAmbientSound() {
        return OPSoundEvents.TART_IDLE.get();
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource source) {
        return OPSoundEvents.TART_HURT.get();
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return OPSoundEvents.TART_DEATH.get();
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
        RandomSource random = level.getRandom();
        if (random.nextInt(this.getEliteSpawnChance()) == 0) {
            this.setElite(true);
            this.setEliteStats(this);
        }
        return spawnData;
    }

    private static class TartNodeEvaluator extends WalkNodeEvaluator {

        @Override
        protected @NotNull BlockPathTypes evaluateBlockPathType(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockPathTypes pathTypes) {
            return pathTypes == BlockPathTypes.LEAVES ? BlockPathTypes.OPEN : super.evaluateBlockPathType(level, pos, pathTypes);
        }
    }

    private static class TartPathNavigation extends SmoothGroundPathNavigation {

        public TartPathNavigation(Mob mob, Level level) {
            super(mob, level);
        }

        @Override
        protected @NotNull PathFinder createPathFinder(int i) {
            this.nodeEvaluator = new TartNodeEvaluator();
            return new PathFinder(this.nodeEvaluator, i);
        }
    }
}
