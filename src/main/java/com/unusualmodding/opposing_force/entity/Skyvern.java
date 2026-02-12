package com.unusualmodding.opposing_force.entity;

import com.mojang.serialization.Codec;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.ai.control.SkyvernLookControl;
import com.unusualmodding.opposing_force.entity.ai.control.SkyvernMoveControl;
import com.unusualmodding.opposing_force.entity.ai.goal.skyvern.SkyvernChargeGoal;
import com.unusualmodding.opposing_force.entity.ai.goal.skyvern.SkyvernFlightGoal;
import com.unusualmodding.opposing_force.entity.ai.navigation.SmoothFlyingPathNavigation;
import com.unusualmodding.opposing_force.entity.base.TameableMonster;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.IntFunction;

@SuppressWarnings("deprecation")
public class Skyvern extends TameableMonster implements FlyingAnimal, VariantHolder<Skyvern.SkyvernVariant> {

    private static final EntityDataAccessor<Float> TARGET_PITCH = SynchedEntityData.defineId(Skyvern.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Skyvern.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SEGMENTS = SynchedEntityData.defineId(Skyvern.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Skyvern.class, EntityDataSerializers.INT);

    public final AnimationState flyingAnimationState = new AnimationState();
    public final AnimationState attackStartAnimationState = new AnimationState();
    public final AnimationState attackingAnimationState = new AnimationState();
    public final AnimationState attackEndAnimationState = new AnimationState();
    public final AnimationState roarAnimationState = new AnimationState();
    public final AnimationState roll1AnimationState = new AnimationState();

    private int attackStartTicks;
    private int attackEndTicks;
    private int roarTicks;
    private int rollTicks;

    private int roarCooldown = 0;
    private int rollCooldown = 0;

    private int lSteps;
    private double lx;
    private double ly;
    private double lz;
    private double lyr;
    private double lxr;
    private double lxd;
    private double lyd;
    private double lzd;
    private float prevPitch;
    private float pitch;

    public Skyvern(EntityType<? extends TameableMonster> type, Level level) {
        super(type, level);
        this.moveControl = new SkyvernMoveControl(this);
        this.lookControl = new SkyvernLookControl(this);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WALKABLE, -1.0F);
        this.xpReward = 20;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SkyvernChargeGoal(this));
        this.goalSelector.addGoal(2, new SkyvernFlightGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.9D)
                .add(Attributes.FLYING_SPEED, 0.9D)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 9.0D)
                .add(Attributes.FOLLOW_RANGE, 80.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean isFlying() {
        return true;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothFlyingPathNavigation(this, level, 1.0F);
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, @NotNull LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.isEffectiveAi() || this.isVehicle()) {
            this.moveRelative(this.getSpeed(), travelVec);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.calculateEntityAnimation(false);
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(travelVec);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(TARGET_PITCH, 0.0F);
        this.entityData.define(SEGMENTS, 0);
        this.entityData.define(VARIANT, SkyvernVariant.CLOUDY.id());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putFloat("TargetPitch", this.getTargetPitch());
        compoundTag.putInt("Segments", this.getSegments());
        compoundTag.putInt("Variant", this.getVariant().id());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        this.setTargetPitch(compoundTag.getFloat("TargetPitch"));
        this.setSegments(compoundTag.getInt("Segments"));
        this.setVariant(SkyvernVariant.byId(compoundTag.getInt("Variant")));
    }

    public void setAttackState(int attackState) {
        this.entityData.set(ATTACK_STATE, attackState);
    }

    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    public void setTargetPitch(float pitch) {
        this.entityData.set(TARGET_PITCH, pitch);
    }

    public float getTargetPitch() {
        return this.entityData.get(TARGET_PITCH);
    }

    public void setSegments(int segments) {
        this.entityData.set(SEGMENTS, segments);
    }

    public int getSegments() {
        return this.entityData.get(SEGMENTS);
    }

    @Override
    public @NotNull SkyvernVariant getVariant() {
        return SkyvernVariant.byId(this.entityData.get(VARIANT));
    }

    @Override
    public void setVariant(SkyvernVariant variant) {
        this.entityData.set(VARIANT, variant.id());
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return 0.4F * dimensions.height;
    }

    @Override
    public float getXRot() {
        return this.pitch;
    }

    @Override
    public boolean removeWhenFarAway(double distanceSqr) {
        return distanceSqr < 65536;
    }

    @Override
    public void tick() {
        super.tick();
        this.prevPitch = pitch;
        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.getYRot();
        if (!level().isClientSide) {
            this.setTargetPitch((float) (-(Mth.atan2(this.getDeltaMovement().y, this.getDeltaMovement().horizontalDistance()) * (180F / (float) Math.PI))));
        }
        this.pitch = Mth.approachDegrees(pitch, this.getTargetPitch(), 4);

        if (attackStartTicks > 0) attackStartTicks--;
        if (attackEndTicks > 0) attackEndTicks--;
        if (roarTicks > 0) roarTicks--;
        if (rollTicks > 0) rollTicks--;
        if (attackStartTicks == 0 && this.getPose() == OPPoses.ATTACK_START.get()) this.setPose(OPPoses.ATTACKING.get());
        if (attackEndTicks == 0 && this.getPose() == OPPoses.ATTACK_END.get()) this.setPose(Pose.STANDING);
        if (roarTicks == 0 && this.getPose() == Pose.ROARING) this.setPose(Pose.STANDING);
        if (rollTicks == 0 && this.getPose() == OPPoses.ROLLING.get()) this.setPose(Pose.STANDING);

        if (roarCooldown > 0) roarCooldown--;
        if (rollCooldown > 0) rollCooldown--;

        if (level().isClientSide) {
            this.setupAnimationStates();

            if (this.isAlive()) {
                OpposingForce.PROXY.playWorldSound(this, (byte) 7);
            }

            if (this.lSteps > 0) {
                double x = this.getX() + (this.lx - this.getX()) / (double) this.lSteps;
                double y = this.getY() + (this.ly - this.getY()) / (double) this.lSteps;
                double z = this.getZ() + (this.lz - this.getZ()) / (double) this.lSteps;
                this.setYRot(Mth.wrapDegrees((float) this.lyr));
                this.setXRot(this.getXRot() + (float) (this.lxr - (double) this.getXRot()) / (float) this.lSteps);
                this.lSteps--;
                this.setPos(x, y, z);
            } else {
                this.reapplyPosition();
            }
        } else {
            if (this.getRandom().nextInt(600) == 0) {
                this.roar();
            } else if (this.getRandom().nextInt(700) == 0) {
                this.roll();
            }
        }
    }

    // Animations
    public void setupAnimationStates() {
        if (attackStartTicks == 0 && this.attackStartAnimationState.isStarted()) this.attackStartAnimationState.stop();
        if (attackEndTicks == 0 && this.attackEndAnimationState.isStarted()) this.attackEndAnimationState.stop();
        this.flyingAnimationState.animateWhen(this.getPose() == Pose.STANDING, this.tickCount);
    }

    public void roar() {
        if (roarCooldown == 0 && this.getPose() == Pose.STANDING) {
            this.setPose(Pose.ROARING);
            this.playSound(OPSoundEvents.SKYVERN_ROAR.get(), 3.0F, 0.9F + this.getRandom().nextFloat() * 0.2F);
            this.roarTicks = 40;
            this.roarCooldown = 200 + random.nextInt(200);
        }
    }

    public void roll() {
        if (rollCooldown == 0 && this.getPose() == Pose.STANDING) {
            this.setPose(OPPoses.ROLLING.get());
            this.rollTicks = 40;
            this.rollCooldown = 300 + random.nextInt(300);
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entityDataAccessor) {
        if (SEGMENTS.equals(entityDataAccessor)) {
            this.refreshDimensions();
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(6.0F * this.getSegments() + 10.0F);
            this.heal(this.getMaxHealth());
        }
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == OPPoses.ATTACK_START.get()) {
                this.flyingAnimationState.stop();
                this.attackStartAnimationState.start(this.tickCount);
                this.attackStartTicks = 10;
            }
            else if (this.getPose() == OPPoses.ATTACKING.get()) {
                this.flyingAnimationState.stop();
                this.attackStartAnimationState.stop();
                this.attackingAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == OPPoses.ATTACK_END.get()) {
                this.flyingAnimationState.stop();
                this.attackingAnimationState.stop();
                this.attackEndAnimationState.start(this.tickCount);
                this.attackEndTicks = 10;
            }
            else if (this.getPose() == Pose.ROARING) {
                this.flyingAnimationState.stop();
                this.attackingAnimationState.stop();
                this.attackEndAnimationState.stop();
                this.attackStartAnimationState.stop();
                this.roarAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == OPPoses.ROLLING.get()) {
                this.flyingAnimationState.stop();
                this.attackingAnimationState.stop();
                this.attackEndAnimationState.stop();
                this.attackStartAnimationState.stop();
                this.roll1AnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.STANDING) {
                this.attackStartAnimationState.stop();
                this.attackingAnimationState.stop();
                this.attackEndAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
    }

    @Override
    public void lerpTo(double x, double y, double z, float yr, float xr, int steps, boolean b) {
        this.lx = x;
        this.ly = y;
        this.lz = z;
        this.lyr = yr;
        this.lxr = xr;
        this.lSteps = steps;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    @Override
    public void lerpMotion(double lerpX, double lerpY, double lerpZ) {
        this.lxd = lerpX;
        this.lyd = lerpY;
        this.lzd = lerpZ;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        OpposingForce.PROXY.clearSoundCacheFor(this);
        super.remove(removalReason);
    }

    public enum SkyvernVariant implements StringRepresentable {

        CLOUDY(0, "cloudy"),
        AZURE(1, "azure"),
        THUNDER(2, "thunder");

        private static final IntFunction<SkyvernVariant> BY_ID = ByIdMap.sparse(SkyvernVariant::id, values(), CLOUDY);
        public static final Codec<SkyvernVariant> CODEC = StringRepresentable.fromEnum(SkyvernVariant::values);

        final int id;
        private final String name;

        SkyvernVariant(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public @NotNull String getSerializedName() {
            return this.name;
        }

        public int id() {
            return this.id;
        }

        public static SkyvernVariant byId(int pId) {
            return BY_ID.apply(pId);
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        int segmentCount = 16 + level.getRandom().nextInt(16);
        SkyvernSegment.createSkyvernSegments(this, segmentCount);
        this.setSegments(segmentCount);
        SkyvernVariant skyvernVariant = getSkyvernVariant(level);
        if (spawnData instanceof SkyvernSpawnData) {
            skyvernVariant = ((SkyvernSpawnData) spawnData).variant;
        } else {
            spawnData = new SkyvernSpawnData(skyvernVariant);
        }
        this.setVariant(skyvernVariant);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
    }

    private static SkyvernVariant getSkyvernVariant(ServerLevelAccessor level) {
        if (level.getLevelData().isRaining() && !level.getLevelData().isThundering()) {
            return SkyvernVariant.AZURE;
        }
        else if (level.getLevelData().isThundering()) {
            return SkyvernVariant.THUNDER;
        }
        else {
            return SkyvernVariant.CLOUDY;
        }
    }

    public static class SkyvernSpawnData implements SpawnGroupData {
        public final SkyvernVariant variant;

        public SkyvernSpawnData(SkyvernVariant variant) {
            this.variant = variant;
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void push(@NotNull Entity entity) {
        if (!this.isPassengerOfSameVehicle(entity) && !(entity instanceof SkyvernSegment)) {
            if (!entity.noPhysics && !this.noPhysics) {
                double x = entity.getX() - this.getX();
                double z = entity.getZ() - this.getZ();
                double max = Mth.absMax(x, z);
                if (max >= (double) 0.01F) {
                    max = Math.sqrt(max);
                    x /= max;
                    z /= max;
                    double d3 = 1.0D / max;
                    if (d3 > 1.0D) {
                        d3 = 1.0D;
                    }
                    x *= d3;
                    z *= d3;
                    x *= 0.05F;
                    z *= 0.05F;
                    if (!entity.isVehicle() && entity.isPushable()) {
                        entity.push(x, 0.0D, z);
                    }
                }
            }
        }
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource damageSource) {
        return super.isInvulnerableTo(damageSource) || damageSource.is(DamageTypes.IN_WALL) || damageSource.is(DamageTypes.CACTUS) || damageSource.is(DamageTypes.DROWN) || damageSource.is(DamageTypes.FALL);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return Math.sqrt(distance) < 1024.0D;
    }

    @Override
    public float getViewXRot(float partialTicks) {
        return (prevPitch + (pitch - prevPitch) * partialTicks);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(6);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.getTarget() != null ? OPSoundEvents.SKYVERN_IDLE_HOSTILE.get() : OPSoundEvents.SKYVERN_IDLE.get();
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource source) {
        return OPSoundEvents.SKYVERN_HURT.get();
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return OPSoundEvents.SKYVERN_DEATH.get();
    }

    @Override
    protected float getSoundVolume() {
        return super.getSoundVolume() * 3.0F;
    }
}