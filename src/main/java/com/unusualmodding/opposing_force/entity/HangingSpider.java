package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.ai.goal.*;
import com.unusualmodding.opposing_force.entity.ai.navigation.*;
import com.unusualmodding.opposing_force.entity.base.IAnimatedAttacker;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import org.joml.Vector3f;

public class HangingSpider extends Spider implements IAnimatedAttacker {

    private static final EntityDataAccessor<Boolean> UPSIDE_DOWN = SynchedEntityData.defineId(HangingSpider.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Vector3f> CURRENT_WEB_POS = SynchedEntityData.defineId(HangingSpider.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> TARGET_POS = SynchedEntityData.defineId(HangingSpider.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Boolean> IS_WEB_OUT = SynchedEntityData.defineId(HangingSpider.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> GOING_UP = SynchedEntityData.defineId(HangingSpider.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> GOING_DOWN = SynchedEntityData.defineId(HangingSpider.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(HangingSpider.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> GOING_UP_COOLDOWN = SynchedEntityData.defineId(HangingSpider.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> GOING_DOWN_COOLDOWN = SynchedEntityData.defineId(HangingSpider.class, EntityDataSerializers.INT);

    private boolean isUpsideDownNavigator;
    protected Vector3f webTarget;

    public final AnimationState idleAnimationState = new AnimationState();

    public HangingSpider(EntityType<? extends Spider> entityType, Level level) {
        super(entityType, level);
        switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new HangingSpiderSpinWebUpGoal(this));
        this.goalSelector.addGoal(1, new HangingSpiderSpinWebDownGoal(this));
        this.goalSelector.addGoal(2, new HangingSpiderAttackGoal(this));
        this.goalSelector.addGoal(3, new HangingSpiderRandomStrollGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, LivingEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new HangingSpiderNearestAttackableTargetGoal<>(this, Player.class, true));
    }

    private void switchNavigator(boolean onGround) {
        if (onGround) {
            this.moveControl = new MoveControl(this);
            this.navigation = new SmoothGroundPathNavigation(this, level());
            this.isUpsideDownNavigator = false;
        } else {
            this.moveControl = new HangingSpiderMoveControl(this);
            this.navigation = new HangingSpiderPathNavigation(this, level());
            this.isUpsideDownNavigator = true;
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypes.IN_WALL);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return entityDimensions.height * 0.65F;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tickCount > 2) {
            updateWeb();
        }

        if (this.onGround() && !this.isAggressive()) {
            if (this.getGoingUpCooldown() > 0) {
                this.setGoingUpCooldown(this.getGoingUpCooldown() - 1);
            }
        }

        if (this.isUpsideDown()) {
            if (this.getGoingDownCooldown() > 0) {
                this.setGoingDownCooldown(this.getGoingDownCooldown() - 1);
            }
        }

        if (!this.level().isClientSide) {
            this.setUpsideDown(verticalCollision && getDeltaMovement().y >= 0);

            if (this.isUpsideDown()) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.7F, 1.0F, 0.7F));
                this.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(-0.08D);
            } else if ((!this.isUpsideDown() && !this.isGoingUp() && !this.isGoingDown() && this.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).getValue() < 0) || this.onGround()) {
                this.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.08D);
            }

            if (this.isUpsideDown()) {
                if (!this.isUpsideDownNavigator) {
                    this.switchNavigator(false);
                }
            } else {
                if (this.isUpsideDownNavigator) {
                    this.switchNavigator(true);
                }
            }
        }

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    private void updateWeb() {
        if (webTarget != null) {
            Vector3f target = new Vector3f(webTarget.x - (float) this.getX(), webTarget.y - (float) this.getY(), webTarget.z - (float) this.getZ());
            float lerp = Mth.clamp(this.getCurrentWebPos().distance(target) * 0.3F, 0.05F, 1.0F);
            this.setCurrentWebPos(new Vector3f(Mth.lerp(lerp, this.getCurrentWebPos().x, target.x), Mth.lerp(lerp, this.getCurrentWebPos().y, target.y), Mth.lerp(lerp, this.getCurrentWebPos().z, target.z)));
        } else {
            webTarget = new Vector3f((float) this.getX(), (float) (this.getY() + getWebOffset()), (float) this.getZ());
        }
        if (this.getCurrentWebPos().distance(new Vector3f(0,getWebOffset(), 0)) > 40 || !this.isWebOut()) {
            deactivateWeb();
        }
        this.setWebTarget(this.getTargetPos());
    }

    public void activateWeb(Vector3f target) {
        Vector3f origin = new Vector3f(0, this.getWebOffset(), 0);
        this.setCurrentWebPos(origin);
        this.setTargetPos(target);
        this.setWebTarget(target);
        this.setWebOut(true);
    }

    public void deactivateWeb() {
        Vector3f fallback = new Vector3f((float)this.getX(), (float)(this.getY() + this.getWebOffset()), (float)this.getZ());
        this.setTargetPos(fallback);
        this.setWebTarget(fallback);
        this.setWebOut(false);
    }

    public float getWebOffset() {
        return 0.35F;
    }

    public void setWebTarget(Vector3f webTarget) {
        this.webTarget = webTarget;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(UPSIDE_DOWN, false);
        this.entityData.define(CURRENT_WEB_POS, new Vector3f((float) this.getX(), (float) (this.getY() + getWebOffset()), (float) this.getZ()));
        this.entityData.define(TARGET_POS, new Vector3f((float) this.getX(), (float) this.getY() + getWebOffset(), (float) this.getZ()));
        this.entityData.define(IS_WEB_OUT, false);
        this.entityData.define(GOING_UP, false);
        this.entityData.define(GOING_DOWN, false);
        this.entityData.define(GOING_UP_COOLDOWN, 0);
        this.entityData.define(GOING_DOWN_COOLDOWN, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("UpsideDown", this.isUpsideDown());
        compoundTag.putFloat("CurrentWebPosX", this.getCurrentWebPos().x);
        compoundTag.putFloat("CurrentWebPosY", this.getCurrentWebPos().y);
        compoundTag.putFloat("CurrentWebPosZ", this.getCurrentWebPos().z);
        compoundTag.putFloat("TargetPosX", this.getTargetPos().x);
        compoundTag.putFloat("TargetPosY", this.getTargetPos().y);
        compoundTag.putFloat("TargetPosZ", this.getTargetPos().z);
        compoundTag.putBoolean("IsWebOut", this.isWebOut());
        compoundTag.putBoolean("GoingUp", this.isGoingUp());
        compoundTag.putBoolean("GoingDown", this.isGoingDown());
        compoundTag.putInt("GoingUpCooldown", this.getGoingUpCooldown());
        compoundTag.putInt("GoingDownCooldown", this.getGoingDownCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setUpsideDown(compoundTag.getBoolean("UpsideDown"));
        this.setCurrentWebPos( new Vector3f(
                compoundTag.getFloat("CurrentWebPosX"),
                compoundTag.getFloat("CurrentWebPosY"),
                compoundTag.getFloat("CurrentWebPosZ")
        ));
        this.setTargetPos( new Vector3f(
                compoundTag.getFloat("TargetPosX"),
                compoundTag.getFloat("TargetPosY"),
                compoundTag.getFloat("TargetPosZ")
        ));
        this.setWebOut(compoundTag.getBoolean("IsWebOut"));
        this.setGoingUp(compoundTag.getBoolean("GoingUp"));
        this.setGoingDown(compoundTag.getBoolean("GoingDown"));
        this.setGoingUpCooldown(compoundTag.getInt("GoingUpCooldown"));
        this.setGoingDownCooldown(compoundTag.getInt("GoingDownCooldown"));
    }

    @Override
    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    @Override
    public void setAttackState(int attackState) {
        this.entityData.set(ATTACK_STATE, attackState);
    }

    public int getGoingUpCooldown() {
        return this.entityData.get(GOING_UP_COOLDOWN);
    }

    public void setGoingUpCooldown(int cooldown) {
        this.entityData.set(GOING_UP_COOLDOWN, cooldown);
    }

    public int getGoingDownCooldown() {
        return this.entityData.get(GOING_DOWN_COOLDOWN);
    }

    public void setGoingDownCooldown(int cooldown) {
        this.entityData.set(GOING_DOWN_COOLDOWN, cooldown);
    }

    public boolean isGoingUp() {
        return this.entityData.get(GOING_UP);
    }

    public void setGoingUp(boolean up) {
        this.entityData.set(GOING_UP, up);
    }

    public boolean isGoingDown() {
        return this.entityData.get(GOING_DOWN);
    }

    public void setGoingDown(boolean down) {
        this.entityData.set(GOING_DOWN, down);
    }

    public boolean isUpsideDown() {
        return this.entityData.get(UPSIDE_DOWN);
    }

    public void setUpsideDown(boolean upsideDown) {
        this.entityData.set(UPSIDE_DOWN, upsideDown);
    }

    public Vector3f getCurrentWebPos() {
        return this.entityData.get(CURRENT_WEB_POS);
    }

    public void setCurrentWebPos(Vector3f webPos) {
        this.entityData.set(CURRENT_WEB_POS, webPos);
    }

    public Vector3f getTargetPos() {
        return this.entityData.get(TARGET_POS);
    }

    public void setTargetPos(Vector3f vec) {
        this.entityData.set(TARGET_POS, vec);
    }

    public boolean isWebOut() {
        return this.entityData.get(IS_WEB_OUT);
    }
    public void setWebOut(boolean webOut) {
        this.entityData.set(IS_WEB_OUT, webOut);
    }

    public static BlockPos getLowestPos(LevelAccessor world, BlockPos pos) {
        while (!world.getBlockState(pos).isFaceSturdy(world, pos, Direction.DOWN) && pos.getY() < 320) {
            pos = pos.above();
        }
        return pos;
    }

    public BlockHitResult raycastFloorOrCeiling(Vec3 origin, boolean floor) {
        BlockHitResult clip = this.level().clip(new ClipContext(
                origin.add(0, this.getBbHeight(), 0),
                floor ? origin.subtract(0, -50.0, 0) : origin.add(0, 50.0, 0),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                this
        ));

        if (clip.getBlockPos().getY() < this.blockPosition().getY() + (floor ? -10 : 10)) return BlockHitResult.miss(origin, this.getDirection(), BlockPos.containing(origin));
        return clip;
    }

    public BlockHitResult searchForNearbyBlock(Vec3 center, double radius, boolean floor) {
        for (int angle = 0; angle < 360; angle += 15) {
            double radians = Math.toRadians(angle);
            double offsetX = Math.cos(radians) * radius;
            double offsetZ = Math.sin(radians) * radius;
            Vec3 searchPos = center.add(offsetX, 0, offsetZ);
            BlockHitResult result = raycastFloorOrCeiling(searchPos, floor);
            if (result.getType() == HitResult.Type.BLOCK) {
                return result;
            }
        }
        return BlockHitResult.miss(center, this.getDirection(), BlockPos.containing(center));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.PALE_SPIDER_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return OPSoundEvents.PALE_SPIDER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return OPSoundEvents.PALE_SPIDER_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.2F);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return checkPaleSpiderSpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean checkPaleSpiderSpawnRules(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() <= 48 && (random.nextInt(10) == 0 || pos.getY() <= 0) && level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawnNoSkylight(level, pos, random) && checkMobSpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean isDarkEnoughToSpawnNoSkylight(ServerLevelAccessor level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.SKY, pos) > 0) {
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
}