package com.unusualmodding.opposing_force.entity;

import com.mojang.serialization.Codec;
import com.unusualmodding.opposing_force.entity.ai.goal.SkyvernChargeGoal;
import com.unusualmodding.opposing_force.entity.ai.goal.SkyvernFlightGoal;
import com.unusualmodding.opposing_force.entity.ai.goal.SkyvernRidingGoal;
import com.unusualmodding.opposing_force.entity.ai.navigation.SkyvernLookControl;
import com.unusualmodding.opposing_force.entity.ai.navigation.SkyvernMoveControl;
import com.unusualmodding.opposing_force.entity.ai.navigation.SkyvernPathNavigation;
import com.unusualmodding.opposing_force.entity.base.TameableMonster;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;
import java.util.function.IntFunction;

@SuppressWarnings("deprecation")
public class Skyvern extends TameableMonster implements FlyingAnimal, VariantHolder<Skyvern.SkyvernVariant> {

    private static final EntityDataAccessor<Float> TARGET_PITCH = SynchedEntityData.defineId(Skyvern.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Skyvern.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SEGMENTS = SynchedEntityData.defineId(Skyvern.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Skyvern.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> RIDING_SEGMENT_ID = SynchedEntityData.defineId(Skyvern.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> RIDING_SEGMENT_UUID = SynchedEntityData.defineId(Skyvern.class, EntityDataSerializers.OPTIONAL_UUID);

    private Player ridingPlayer;
    private int ridingTicks;

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
    }

    @Override
    protected void registerGoals() {
//        this.goalSelector.addGoal(0, new SkyvernRidingGoal(this));
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
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D)
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
    public void travel(@NotNull Vec3 travelVec) {
        if (this.isEffectiveAi() || this.isVehicle()) {
            this.moveRelative(this.getSpeed(), travelVec);
            Vec3 delta = this.getDeltaMovement();
            this.move(MoverType.SELF, delta);
            this.calculateEntityAnimation(false);
            this.setDeltaMovement(delta.scale(0.9D));
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
        this.entityData.define(RIDING_SEGMENT_ID, -1);
        this.entityData.define(RIDING_SEGMENT_UUID, Optional.empty());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("AttackState", this.getAttackState());
        compoundTag.putFloat("TargetPitch", this.getTargetPitch());
        compoundTag.putInt("Segments", this.getSegments());
        compoundTag.putInt("Variant", this.getVariant().id());
        if (this.getRidingSegmentUUID() != null) {
            compoundTag.putUUID("RidingSegmentUUID", this.getRidingSegmentUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        this.setAttackState(compoundTag.getInt("AttackState"));
        this.setTargetPitch(compoundTag.getFloat("TargetPitch"));
        this.setSegments(compoundTag.getInt("Segments"));
        this.setVariant(SkyvernVariant.byId(compoundTag.getInt("Variant")));
        if (compoundTag.hasUUID("RidingSegmentUUID")) {
            this.setRidingSegmentUUID(compoundTag.getUUID("RidingSegmentUUID"));
        }
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

    public Entity getRidingSegment() {
        if (!level().isClientSide) {
            final UUID id = this.getRidingSegmentUUID();
            return id == null ? null : ((ServerLevel) level()).getEntity(id);
        } else {
            int id = this.entityData.get(RIDING_SEGMENT_ID);
            return id == -1 ? null : level().getEntity(id);
        }
    }

    public void setRidingSegmentId(int id){
        this.entityData.set(RIDING_SEGMENT_ID, id);
    }

    @Nullable
    public UUID getRidingSegmentUUID() {
        return this.entityData.get(RIDING_SEGMENT_UUID).orElse(null);
    }

    public void setRidingSegmentUUID(@Nullable UUID uniqueId) {
        this.entityData.set(RIDING_SEGMENT_UUID, Optional.ofNullable(uniqueId));
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return 0.4F * dimensions.height;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SkyvernPathNavigation(this, level);
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public float getXRot() {
        return this.pitch;
    }

    @Override
    public boolean removeWhenFarAway(double distanceSq) {
        return distanceSq < 65536;
    }

    @Override
    public void tick() {
        super.tick();
        prevPitch = pitch;
        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.getYRot();
        if (!level().isClientSide) {
            this.setTargetPitch((float) (-(Mth.atan2(this.getDeltaMovement().y, this.getDeltaMovement().horizontalDistance()) * (180F / (float) Math.PI))));
        }
        this.pitch = Mth.approachDegrees(pitch, getTargetPitch(), 5);

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
            Entity ridingSegment = this.getRidingSegment();
            this.entityData.set(RIDING_SEGMENT_ID, ridingSegment == null ? -1 : ridingSegment.getId());
            if (random.nextInt(600) == 0) {
                this.roar();
            } else if (random.nextInt(700) == 0) {
                this.roll();
            }
        }

        if (ridingTicks > 0) ridingTicks--;
    }

    // Riding
    public Player getRidingPlayer() {
        return this.ridingPlayer;
    }

    protected void tickController(@NotNull Player player) {
        this.ridingPlayer = player;
    }

    public boolean isRidingMode() {
        return this.ridingTicks > 0;
    }

    @Override
    protected void removePassenger(@NotNull Entity passenger) {
        super.removePassenger(passenger);
        this.setRidingSegmentUUID(null);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
        InteractionResult type = super.mobInteract(player, hand);
        if (!interactionresult.consumesAction() && !type.consumesAction()) {
            if (this.isTame() && this.isOwnedBy(player)) {
                if (this.canOwnerCommand(player)) {
                    this.setCommand(this.getCommand() + 1);
                    if (this.getCommand() == 3) {
                        this.setCommand(0);
                    }
                    player.displayClientMessage(Component.translatable("entity.opposing_force.all.command_" + this.getCommand(), this.getName()), true);
                    boolean sit = this.getCommand() == 1;
                    this.setOrderedToSit(sit);
                    return InteractionResult.SUCCESS;
                } else if (this.canOwnerMount(player)) {
                    if (!level().isClientSide) {
                        Entity segment = this.getRidingSegment();
                        if (segment != null && player.startRiding(segment)) {
                            return InteractionResult.CONSUME;
                        }
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }

        if (!this.isTame() && (itemstack.is(Items.DEBUG_STICK))) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            this.gameEvent(GameEvent.ENTITY_INTERACT);
            this.tame(player);
            this.level().broadcastEntityEvent(this, (byte) 9);
            this.heal(this.getMaxHealth());
            return InteractionResult.SUCCESS;
        }
        return type;
    }

    public boolean canOwnerMount(Player player) {
        return true;
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
            this.playSound(SoundEvents.ENDER_DRAGON_AMBIENT, 3.0F, this.getVoicePitch());
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
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(5.0F * this.getSegments() + 10.0F);
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
    public int getHeadRotSpeed() {
        return 8;
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
        int segmentCount = 19 + level.getRandom().nextInt(6);
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
    public float getViewXRot(float partialTick) {
        return (prevPitch + (pitch - prevPitch) * partialTick);
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
        return SoundEvents.PHANTOM_AMBIENT;
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.ENDER_DRAGON_HURT;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return SoundEvents.ENDER_DRAGON_HURT;
    }

    @Override
    protected float getSoundVolume() {
        return super.getSoundVolume() * 2.0F;
    }
}