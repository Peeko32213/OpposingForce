package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.base.IAnimatedAttacker;
import com.unusualmodding.opposing_force.registry.OPEffects;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Objects;

public class UmberSpider extends Monster implements IAnimatedAttacker {

    private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.defineId(UmberSpider.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(UmberSpider.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(UmberSpider.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> LEAP_COOLDOWN = SynchedEntityData.defineId(UmberSpider.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> LIGHT_THRESHOLD = SynchedEntityData.defineId(UmberSpider.class, EntityDataSerializers.INT);

    private int fleeLightFor = 0;
    private Vec3 fleeFromPosition;

    public final AnimationState idleAnimationState = new AnimationState();

    protected PathNavigation createNavigation(Level level) {
        return new WallClimberNavigation(this, level);
    }

    public UmberSpider(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new UmberSpiderRandomStrollGoal(this));
        this.goalSelector.addGoal(3, new UmberSpiderAttackGoal(this));
        this.goalSelector.addGoal(4, new UmberSpiderPanicGoal(this));
        this.goalSelector.addGoal(5, new UmberSpiderFearLightGoal(this));
        this.goalSelector.addGoal(6, new UmberSpiderRandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return entityDimensions.height * 0.65F;
    }

    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.UMBER_SPIDER_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return OPSoundEvents.UMBER_SPIDER_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return OPSoundEvents.UMBER_SPIDER_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.1F, 0.8F);
    }

    public double getPassengersRidingOffset() {
        return this.getBbHeight() * 0.5F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 180;
    }

    public boolean doHurtTarget(Entity entity) {
        if (super.doHurtTarget(entity)) {
            if (entity instanceof LivingEntity) {
                int i = 0;
                if (this.level().getDifficulty() == Difficulty.NORMAL) {
                    i = 5;
                } else if (this.level().getDifficulty() == Difficulty.HARD) {
                    i = 10;
                }
                if (i > 0) {
                    ((LivingEntity) entity).addEffect(new MobEffectInstance(OPEffects.GLOOM_TOXIN.get(), i * 20, 0), this);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CLIMBING, (byte) 0);
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(ATTACKING, false);
        this.entityData.define(LEAP_COOLDOWN, 3 + random.nextInt(8 * 2));
        this.entityData.define(LIGHT_THRESHOLD, 7);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AttackState", this.getAttackState());
        compoundTag.putBoolean("Attacking", this.isAttacking());
        compoundTag.putInt("LeapCooldown", this.getLeapCooldown());
        compoundTag.putInt("LightThreshold", this.getLightThreshold());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAttackState(compoundTag.getInt("AttackState"));
        this.setAttacking(compoundTag.getBoolean("Attacking"));
        this.setLeapCooldown(compoundTag.getInt("LeapCooldown"));
        this.setLightThreshold(compoundTag.getInt("LightThreshold"));
    }

    @Override
    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    @Override
    public void setAttackState(int attack) {
        this.entityData.set(ATTACK_STATE, attack);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public int getLeapCooldown() {
        return this.entityData.get(LEAP_COOLDOWN);
    }

    public void setLeapCooldown(int cooldown) {
        this.entityData.set(LEAP_COOLDOWN, cooldown);
    }

    public void leapCooldown() {
        this.entityData.set(LEAP_COOLDOWN, 3 + random.nextInt(8 * 2));
    }

    public int getLightThreshold() {
        return this.entityData.get(LIGHT_THRESHOLD);
    }

    public void setLightThreshold(int threshold) {
        this.entityData.set(LIGHT_THRESHOLD, threshold);
    }

    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        if (!this.level().isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }

        if (this.getLeapCooldown() > 0) {
            this.setLeapCooldown(this.getLeapCooldown() - 1);
        }

        BlockPos blockPos = this.blockPosition();
        RandomSource randomSource = this.getRandom();
        BlockPos pos = blockPos.offset(randomSource.nextInt(20) - 10, randomSource.nextInt(6) - 3, randomSource.nextInt(20) - 10);

        if (this.level().getBrightness(LightLayer.BLOCK, this.blockPosition()) > this.getLightThreshold() || this.isOnFire()) {
            this.fleeFromPosition = Vec3.atBottomCenterOf(pos);
        }

        if (fleeLightFor > 0) {
            fleeLightFor--;
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON || effect.getEffect() == OPEffects.GLOOM_TOXIN.get()) {
            MobEffectEvent.Applicable event = new MobEffectEvent.Applicable(this, effect);
            MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == Event.Result.ALLOW;
        } else {
            return super.canBeAffected(effect);
        }
    }

    public boolean isClimbing() {
        return (this.entityData.get(CLIMBING) & 1) != 0;
    }

    public void setClimbing(boolean pClimbing) {
        byte climb = this.entityData.get(CLIMBING);
        if (pClimbing) {
            climb = (byte)(climb | 1);
        } else {
            climb = (byte)(climb & -2);
        }
        this.entityData.set(CLIMBING, climb);
    }

    public void makeStuckInBlock(BlockState pState, Vec3 pMotionMultiplier) {
        if (!pState.is(Blocks.COBWEB)) {
            super.makeStuckInBlock(pState, pMotionMultiplier);
        }
    }

    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    protected boolean isSunSensitive() {
        return true;
    }

    public void aiStep() {
        if (this.isAlive()) {
            boolean flag = this.isSunSensitive() && this.isSunBurnTick();
            if (flag) {
                this.setSecondsOnFire(8);
            }
        }
        super.aiStep();
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        RandomSource randomsource = pLevel.getRandom();

        if (randomsource.nextInt(100) == 0) {
            Skeleton skeleton = EntityType.SKELETON.create(this.level());
            if (skeleton != null) {
                skeleton.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                skeleton.finalizeSpawn(pLevel, pDifficulty, pReason, null, null);
                skeleton.startRiding(this);
            }
        }

        if (pSpawnData == null) {
            pSpawnData = new Spider.SpiderEffectsGroupData();
            if (pLevel.getDifficulty() == Difficulty.HARD && randomsource.nextFloat() < 0.1F * pDifficulty.getSpecialMultiplier()) {
                ((Spider.SpiderEffectsGroupData)pSpawnData).setRandomEffect(randomsource);
            }
        }

        if (pSpawnData instanceof Spider.SpiderEffectsGroupData spiderEffectsGroupData) {
            MobEffect mobeffect = spiderEffectsGroupData.effect;
            if (mobeffect != null) {
                this.addEffect(new MobEffectInstance(mobeffect, -1));
            }
        }

        return pSpawnData;
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<UmberSpider> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return checkUmberSpiderSpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean checkUmberSpiderSpawnRules(EntityType<UmberSpider> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() <= -24 && level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawnNoSkylight(level, pos, random) && checkMobSpawnRules(entityType, level, spawnType, pos, random);
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

    // goals
    private static class UmberSpiderAttackGoal extends Goal {

        private final UmberSpider umberSpider;
        private int attackTime = 0;

        public UmberSpiderAttackGoal(UmberSpider mob) {
            this.umberSpider = mob;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            return !this.umberSpider.isVehicle() && this.umberSpider.getTarget() != null && this.umberSpider.getTarget().isAlive() && this.umberSpider.fleeLightFor <= 0 && this.umberSpider.getTarget().level().getBrightness(LightLayer.BLOCK, this.umberSpider.getTarget().blockPosition()) <= this.umberSpider.getLightThreshold() && !this.umberSpider.isOnFire();
        }

        public void start() {
            this.umberSpider.setAttackState(0);
            this.attackTime = 0;
        }

        public void stop() {
            this.umberSpider.setAttacking(false);
            this.umberSpider.setAttackState(0);
        }

        public void tick() {
            LivingEntity target = this.umberSpider.getTarget();
            if (target != null) {
                this.umberSpider.setAttacking(true);

                this.umberSpider.lookAt(Objects.requireNonNull(target), 30F, 30F);
                this.umberSpider.getLookControl().setLookAt(target, 30F, 30F);

                double distance = this.umberSpider.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int attackState = this.umberSpider.getAttackState();

                this.umberSpider.getNavigation().moveTo(target, 1.2F);

                if (attackState == 1) {
                    tickAttack();
                } else {
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }

        protected void checkForCloseRangeAttack (double distance){
            if (distance <= 4.1D) {
                this.umberSpider.setAttackState(1);
            }
            else if (distance >= 11 && distance <= 24 && this.umberSpider.onGround() && this.umberSpider.getLeapCooldown() <= 0) {
                this.leap();
            }
        }

        protected void tickAttack() {
            this.attackTime++;

            if (this.attackTime == 4) {
                if (this.umberSpider.distanceTo(Objects.requireNonNull(this.umberSpider.getTarget())) < 2.2f) {
                    this.umberSpider.doHurtTarget(this.umberSpider.getTarget());
                    this.umberSpider.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (this.attackTime >= 9) {
                this.attackTime = 0;
                this.umberSpider.setAttackState(0);
            }
        }

        public void leap() {
            Vec3 vec3 = this.umberSpider.getDeltaMovement();
            Vec3 leapVec = new Vec3(Objects.requireNonNull(this.umberSpider.getTarget()).getX() - this.umberSpider.getX(), 0.0F, this.umberSpider.getTarget().getZ() - this.umberSpider.getZ());
            if (leapVec.lengthSqr() > 1.0E-7) {
                leapVec = leapVec.normalize().scale(0.4).add(vec3.scale(0.2));
            }
            this.umberSpider.setDeltaMovement(leapVec.x, 0.4D, leapVec.z);
            this.umberSpider.leapCooldown();
        }
    }

    private static class UmberSpiderFearLightGoal extends Goal {

        private final UmberSpider umberSpider;

        public UmberSpiderFearLightGoal(UmberSpider mob) {
            this.umberSpider = mob;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return !this.umberSpider.isVehicle() && (this.umberSpider.level().getBrightness(LightLayer.BLOCK, this.umberSpider.blockPosition()) > this.umberSpider.getLightThreshold() || this.umberSpider.isOnFire()) && this.umberSpider.fleeFromPosition != null;
        }

        public void stop() {
            this.umberSpider.fleeFromPosition = null;
            this.umberSpider.fleeLightFor = 50;
        }

        public void tick() {
            this.umberSpider.fleeLightFor = 50;
            this.umberSpider.setAttacking(false);

            if (this.umberSpider.getNavigation().isDone()) {
                Vec3 vec3 = LandRandomPos.getPosAway(this.umberSpider, 16, 8, this.umberSpider.fleeFromPosition);
                if (vec3 != null) {
                    this.umberSpider.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.5F);
                }
            }
        }
    }

    private static class UmberSpiderPanicGoal extends Goal {

        private final UmberSpider umberSpider;

        protected double posX;
        protected double posY;
        protected double posZ;

        public UmberSpiderPanicGoal(UmberSpider mob) {
            this.umberSpider = mob;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (!this.shouldPanic()) {
                return false;
            } else {
                return this.findRandomPosition();
            }
        }

        protected boolean shouldPanic() {
            return !this.umberSpider.isVehicle() && this.umberSpider.getLastHurtByMob() != null && this.umberSpider.fleeLightFor <= 0 && !this.umberSpider.isAttacking() && !this.umberSpider.isOnFire();
        }

        protected boolean findRandomPosition() {
            Vec3 vec3 = LandRandomPos.getPos(this.umberSpider, 8, 8);
            if (vec3 == null) {
                return false;
            } else {
                this.posX = vec3.x;
                this.posY = vec3.y;
                this.posZ = vec3.z;
                return true;
            }
        }

        public void start() {
            this.umberSpider.getNavigation().moveTo(this.posX, this.posY, this.posZ, 1.5F);
            this.umberSpider.setAttacking(false);
        }

        public void stop() {
            this.umberSpider.setAttacking(false);
        }

        public boolean canContinueToUse() {
            return !this.umberSpider.getNavigation().isDone();
        }
    }

    private static class UmberSpiderRandomLookAroundGoal extends RandomLookAroundGoal {

        private final UmberSpider umberSpider;

        public UmberSpiderRandomLookAroundGoal(UmberSpider mob) {
            super(mob);
            this.umberSpider = mob;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            return !this.umberSpider.isVehicle() && this.umberSpider.level().getBrightness(LightLayer.BLOCK, this.umberSpider.blockPosition()) <= this.umberSpider.getLightThreshold() && super.canUse();
        }
    }

    private static class UmberSpiderRandomStrollGoal extends WaterAvoidingRandomStrollGoal {

        private final UmberSpider umberSpider;

        public UmberSpiderRandomStrollGoal(UmberSpider mob) {
            super(mob, 1.0D, 0.001F);
            this.umberSpider = mob;
        }

        public boolean canUse() {
            return !this.umberSpider.isVehicle() && this.umberSpider.level().getBrightness(LightLayer.BLOCK, this.umberSpider.blockPosition()) <= this.umberSpider.getLightThreshold() && super.canUse();
        }
    }
}