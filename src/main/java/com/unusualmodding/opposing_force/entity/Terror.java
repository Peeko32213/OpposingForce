package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.base.IAnimatedAttacker;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
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

import java.util.EnumSet;
import java.util.Objects;

public class Terror extends Monster implements IAnimatedAttacker {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Terror.class, EntityDataSerializers.INT);

    public float prevTilt;
    public float tilt;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flopAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    public Terror(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 1.0F)
                .add(Attributes.ATTACK_DAMAGE, 7.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new TerrorAttackGoal(this));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0F, 1));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(pTravelVector);
        }
    }

    @Override
    public void tick () {
        super.tick();
        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }

        prevTilt = tilt;
        if (this.isInWater() && !this.onGround()) {
            final float v = Mth.degreesDifference(this.getYRot(), yRotO);
            if (Math.abs(v) > 1) {
                if (Math.abs(tilt) < 25) {
                    tilt -= Math.signum(v);
                }
            } else {
                if (Math.abs(tilt) > 0) {
                    final float tiltSign = Math.signum(tilt);
                    tilt -= tiltSign * 0.85F;
                    if (tilt * tiltSign < 0) {
                        tilt = 0;
                    }
                }
            }
        } else {
            tilt = 0;
        }

        if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.05F, 0.4F, (this.random.nextFloat() * 2.0F - 1.0F) * 0.05F));
            this.setYRot(this.random.nextFloat() * 360.0F);
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getVoicePitch());
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isInWaterOrBubble() && this.isAlive(), this.tickCount);
        this.flopAnimationState.animateWhen(this.isAlive(), this.tickCount);
        this.attackAnimationState.animateWhen(this.isAlive() && this.getAttackState() == 1, this.tickCount);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AttackState", this.getAttackState());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAttackState(compoundTag.getInt("AttackState"));
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
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5F;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getFluidState(pos).is(FluidTags.WATER) ? 10.0F + level.getPathfindingCostFromLightLevels(pos) : super.getWalkTargetValue(pos, level);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.TERROR_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return OPSoundEvents.TERROR_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
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
        return pos.getY() <= 20 && level.getRawBrightness(pos, 0) == 0 && level.getBlockState(pos).is(Blocks.WATER);
    }

    public boolean checkSpawnObstruction(LevelReader level) {
        return level.isUnobstructed(this);
    }

    // goals
    private static class TerrorAttackGoal extends Goal {

        private int attackTime = 0;
        protected final Terror terror;

        public TerrorAttackGoal(Terror terror) {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            this.terror = terror;
        }

        public boolean canUse() {
            return this.terror.getTarget() != null && this.terror.getTarget().isAlive();
        }

        public boolean canContinueToUse() {
            LivingEntity target = this.terror.getTarget();
            if (target == null) {
                return false;
            } else if (!target.isAlive()) {
                return false;
            } else if (!this.terror.isWithinRestriction(target.blockPosition())) {
                return false;
            } else {
                return !(target instanceof Player) || !target.isSpectator() && !((Player) target).isCreative() || !this.terror.getNavigation().isDone();
            }
        }

        public void start() {
            this.terror.setAggressive(true);
            this.terror.setAttackState(0);
            this.attackTime = 0;
        }

        public void stop() {
            LivingEntity target = this.terror.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
                this.terror.setTarget(null);
            }
            this.terror.setAggressive(false);
            this.terror.getNavigation().stop();
            this.terror.setAttackState(0);
        }

        public void tick() {
            LivingEntity target = this.terror.getTarget();
            if (target != null) {
                this.terror.lookAt(this.terror.getTarget(), 30F, 30F);
                this.terror.getLookControl().setLookAt(this.terror.getTarget(), 30F, 30F);
                double distance = this.terror.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int attackState = this.terror.getAttackState();

                if (attackState == 1) {
                    tickAttack();
                } else {
                    this.terror.getNavigation().moveTo(target, 1.45D);
                    if (distance <= 5) {
                        this.terror.setAttackState(1);
                    }
                }
            }
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        protected void tickAttack() {
            attackTime++;
            LivingEntity target = this.terror.getTarget();

            if (attackTime == 8) {
                if (this.terror.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                    this.terror.doHurtTarget(target);
                    this.terror.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (attackTime >= 20) {
                attackTime = 0;
                this.terror.setAttackState(0);
            }
        }

        protected double getAttackReachSqr(LivingEntity target) {
            return this.terror.getBbWidth() * 2.0F * this.terror.getBbWidth() * 2.0F + target.getBbWidth();
        }
    }
}
