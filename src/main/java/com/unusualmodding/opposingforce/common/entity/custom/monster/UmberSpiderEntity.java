package com.unusualmodding.opposingforce.common.entity.custom.monster;

import com.google.common.collect.ImmutableMap;
import com.unusualmodding.opposingforce.common.entity.custom.base.EnhancedMonsterEntity;
import com.unusualmodding.opposingforce.common.entity.state.StateHelper;
import com.unusualmodding.opposingforce.common.entity.state.WeightedState;
import com.unusualmodding.opposingforce.common.entity.util.helper.SmartBodyHelper;
import com.unusualmodding.opposingforce.common.entity.util.navigator.SmartClimbPathNavigator;
import com.unusualmodding.opposingforce.core.registry.OPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class UmberSpiderEntity extends EnhancedMonsterEntity implements GeoAnimatable, GeoEntity {

    public static final int LIGHT_THRESHOLD = 7;
    private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.defineId(UmberSpiderEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(UmberSpiderEntity.class, EntityDataSerializers.BOOLEAN);

    private int leapCooldown = 0;
    private int fleeLightFor = 0;
    private Vec3 fleeFromPosition;

    private static final RawAnimation UMBER_SCURRY = RawAnimation.begin().thenLoop("animation.umber_spider.scurry");
    private static final RawAnimation UMBER_IDLE = RawAnimation.begin().thenLoop("animation.umber_spider.idle");
    private static final RawAnimation UMBER_ATTACK = RawAnimation.begin().thenPlay("animation.umber_spider.attack");

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.5F;
        helper.bodyLagStill = 0.25F;
        return helper;
    }

    protected PathNavigation createNavigation(Level pLevel) {
        return new SmartClimbPathNavigator(this, pLevel);
    }

    public UmberSpiderEntity(EntityType<? extends EnhancedMonsterEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 36.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28F)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
        ;
    }

    public float getStepHeight() {
        if (this.isRunning()) {
            return 1.25F;
        }
        return 0.6F;
    }

    public static <T extends Mob> boolean canSecondTierSpawn(EntityType<UmberSpiderEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
        boolean isDeepDark = iServerWorld.getBiome(pos).is(Biomes.DEEP_DARK);
        return reason == MobSpawnType.SPAWNER || !iServerWorld.canSeeSky(pos) && pos.getY() <= 0 && checkUndergroundMonsterSpawnRules(entityType, iServerWorld, reason, pos, random) && !isDeepDark;
    }

    public static boolean checkUndergroundMonsterSpawnRules(EntityType<? extends Monster> monster, ServerLevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource p_219018_) {
        return level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawnNoSkylight(level, pos, p_219018_) && checkMobSpawnRules(monster, level, reason, pos, p_219018_);
    }

    public static boolean isDarkEnoughToSpawnNoSkylight(ServerLevelAccessor level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.SKY, pos) > 0) {
            return false;
        } else {
            DimensionType dimension = level.dimensionType();
            int i = dimension.monsterSpawnBlockLightLimit();
            if (i < 15 && level.getBrightness(LightLayer.BLOCK, pos) > i) {
                return false;
            } else {
                int j = level.getLevel().isThundering() ? level.getMaxLocalRawBrightness(pos, 10) : level.getMaxLocalRawBrightness(pos);
                return j <= dimension.monsterSpawnLightTest().sample(random);
            }
        }
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1D) {
            public boolean canUse() {
                return UmberSpiderEntity.this.level().getBrightness(LightLayer.BLOCK, UmberSpiderEntity.this.blockPosition()) <= LIGHT_THRESHOLD && super.canUse();
            }
        });
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this) {
            public boolean canUse() {
                return UmberSpiderEntity.this.level().getBrightness(LightLayer.BLOCK, UmberSpiderEntity.this.blockPosition()) <= LIGHT_THRESHOLD && super.canUse();
            }
        });
        this.goalSelector.addGoal(1, new UmberSpiderAttackGoal());
        this.goalSelector.addGoal(7, new UmberSpiderFearLightGoal());
        this.goalSelector.addGoal(5, new UmberSpiderPanicGoal());
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    protected SoundEvent getAmbientSound() {
        return OPSounds.UMBER_SPIDER_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return OPSounds.UMBER_SPIDER_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return OPSounds.UMBER_SPIDER_DEATH.get();
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

//    public boolean doHurtTarget(Entity pEntity) {
//        boolean flag = this.isLightSensitive() && this.isLightBurnTick();
//        if (super.doHurtTarget(pEntity)) {
//            if (pEntity instanceof LivingEntity) {
//                int i = 0;
//                if (this.level().getDifficulty() == Difficulty.NORMAL) {
//                    i = 7;
//                } else if (this.level().getDifficulty() == Difficulty.HARD) {
//                    i = 15;
//                }
//                if (i > 0) {
//                    ((LivingEntity)pEntity).addEffect(new MobEffectInstance(MobEffects.WITHER, i * 20, 0), this);
//                }
//            }
//            this.performAttack();
//            return true;
//        }
//        if (flag){
//            this.performAttack();
//        }
//        return false;
//    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CLIMBING, (byte) 0);
        this.entityData.define(ATTACKING, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }

        BlockPos blockPos = this.blockPosition();
        RandomSource randomSource = this.getRandom();
        BlockPos pos = blockPos.offset(randomSource.nextInt(20) - 10, randomSource.nextInt(6) - 3, randomSource.nextInt(20) - 10);

        if (this.level().getBrightness(LightLayer.BLOCK, this.blockPosition()) > LIGHT_THRESHOLD || this.isOnFire()) {
            this.fleeFromPosition = Vec3.atBottomCenterOf(pos);
        }

        if (fleeLightFor > 0) {
            fleeLightFor--;
        }
    }

    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean canBeAffected(MobEffectInstance pPotioneffect) {
        if (pPotioneffect.getEffect() == MobEffects.POISON) {
            MobEffectEvent.Applicable event = new MobEffectEvent.Applicable(this, pPotioneffect);
            MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == Event.Result.ALLOW;
        } else {
            return super.canBeAffected(pPotioneffect);
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

    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypes.IN_WALL);
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

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<UmberSpiderEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<UmberSpiderEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        controllers.add(attack);
    }

    protected <E extends UmberSpiderEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && !this.isInWater()) {
            if (this.isRunning()) {
                event.setAndContinue(UMBER_SCURRY);
                event.getController().setAnimationSpeed(2.2D);
            } else {
                event.setAndContinue(UMBER_SCURRY);
                event.getController().setAnimationSpeed(1.2D);
            }
            return PlayState.CONTINUE;
        } else {
            event.setAndContinue(UMBER_IDLE);
        }
        return PlayState.CONTINUE;
    }

    protected <E extends UmberSpiderEntity> PlayState attackPredicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        int attackState = this.getAttackState();
        if (attackState == 21) {
            event.setAndContinue(UMBER_ATTACK);
            return PlayState.CONTINUE;
        }
        else if (attackState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        RandomSource randomsource = pLevel.getRandom();

//        if (randomsource.nextInt(100) >= 0) {
//            RambleEntity ramble = OPEntities.RAMBLE.get().create(this.level());
//            if (ramble != null) {
//                ramble.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
//                ramble.finalizeSpawn(pLevel, pDifficulty, pReason, null, null);
//                ramble.startRiding(this);
//            }
//        }

        if (pSpawnData == null) {
            pSpawnData = new UmberSpiderEntity.UmberSpiderEffectsGroupData();
            if (pLevel.getDifficulty() == Difficulty.HARD && randomsource.nextFloat() < 0.1F * pDifficulty.getSpecialMultiplier()) {
                ((UmberSpiderEntity.UmberSpiderEffectsGroupData)pSpawnData).setRandomEffect(randomsource);
            }
        }

        if (pSpawnData instanceof UmberSpiderEntity.UmberSpiderEffectsGroupData spider$spidereffectsgroupdata) {
            MobEffect mobeffect = spider$spidereffectsgroupdata.effect;
            if (mobeffect != null) {
                this.addEffect(new MobEffectInstance(mobeffect, -1));
            }
        }

        return pSpawnData;
    }

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return null;
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return List.of();
    }

    public static class UmberSpiderEffectsGroupData implements SpawnGroupData {
        @Nullable
        public MobEffect effect;

        public void setRandomEffect(RandomSource pRandom) {
            int i = pRandom.nextInt(6);
            if (i <= 1) {
                this.effect = MobEffects.DAMAGE_BOOST;
            } else if (i == 2) {
                this.effect = MobEffects.REGENERATION;
            } else if (i == 3) {
                this.effect = MobEffects.DAMAGE_RESISTANCE;
            } else if (i == 4) {
                this.effect = MobEffects.ABSORPTION;
            } else if (i == 5) {
                this.effect = MobEffects.JUMP;
            }
        }
    }

    // Goals
    private class UmberSpiderAttackGoal extends Goal {

        public UmberSpiderAttackGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            return UmberSpiderEntity.this.getTarget() != null && UmberSpiderEntity.this.getTarget().isAlive() && UmberSpiderEntity.this.fleeLightFor <= 0 && UmberSpiderEntity.this.getTarget().level().getBrightness(LightLayer.BLOCK, UmberSpiderEntity.this.getTarget().blockPosition()) <= LIGHT_THRESHOLD && !UmberSpiderEntity.this.isOnFire();
        }

        public void start() {
            UmberSpiderEntity.this.setAttackState(0);
            UmberSpiderEntity.this.attackTick = 0;
        }

        public void stop() {
            UmberSpiderEntity.this.setRunning(false);
            UmberSpiderEntity.this.setAttacking(false);
            UmberSpiderEntity.this.setAttackState(0);
        }

        public void tick() {
            LivingEntity target = UmberSpiderEntity.this.getTarget();
            if (target != null) {
                UmberSpiderEntity.this.setAttacking(true);
                UmberSpiderEntity.this.setRunning(true);

                UmberSpiderEntity.this.lookAt(UmberSpiderEntity.this.getTarget(), 30F, 30F);
                UmberSpiderEntity.this.getLookControl().setLookAt(UmberSpiderEntity.this.getTarget(), 30F, 30F);

                double distance = UmberSpiderEntity.this.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int attackState = UmberSpiderEntity.this.getAttackState();

                if (attackState == 21) {
                    tickBiteAttack();
                    UmberSpiderEntity.this.getNavigation().moveTo(target, 1.3F);
                } else {
                    UmberSpiderEntity.this.getNavigation().moveTo(target, 1.5F);
                    UmberSpiderEntity.this.leapCooldown = Math.max(UmberSpiderEntity.this.leapCooldown - 1, 0);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }

        protected void checkForCloseRangeAttack (double distance){
            if (distance <= 4.45D) {
                UmberSpiderEntity.this.setAttackState(21);
            }
            else if (distance >= 11 && distance <= 24 && UmberSpiderEntity.this.onGround() && UmberSpiderEntity.this.leapCooldown <= 0) {
                this.leap();
            }
        }

        protected void tickBiteAttack () {
            UmberSpiderEntity.this.attackTick++;

            if(UmberSpiderEntity.this.attackTick == 7) {
                if (UmberSpiderEntity.this.distanceTo(UmberSpiderEntity.this.getTarget()) < 2.44f) {
                    UmberSpiderEntity.this.doHurtTarget(UmberSpiderEntity.this.getTarget());
                }
            }
            if(UmberSpiderEntity.this.attackTick >= 9) {
                UmberSpiderEntity.this.attackTick = 0;
                UmberSpiderEntity.this.setAttackState(0);
            }
        }

        public void leap() {
            Vec3 diff = new Vec3(UmberSpiderEntity.this.getTarget().getX() - UmberSpiderEntity.this.getX(), (UmberSpiderEntity.this.getTarget().getY() - UmberSpiderEntity.this.getY()) + 0.5, UmberSpiderEntity.this.getTarget().getZ() - UmberSpiderEntity.this.getZ());
            Vec3 vel = diff.multiply(0.5D,0.5D, 0.5D).add(0.5D,0.5D,0.5D).normalize();
            UmberSpiderEntity.this.setDeltaMovement(vel);
            UmberSpiderEntity.this.leapCooldown = UmberSpiderEntity.this.getRandom().nextInt(5) + 7;
        }
    }

    private class UmberSpiderFearLightGoal extends Goal {

        public UmberSpiderFearLightGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return (UmberSpiderEntity.this.level().getBrightness(LightLayer.BLOCK, UmberSpiderEntity.this.blockPosition()) > LIGHT_THRESHOLD || UmberSpiderEntity.this.isOnFire()) && UmberSpiderEntity.this.fleeFromPosition != null;
        }

        public void stop() {
            UmberSpiderEntity.this.fleeFromPosition = null;
            UmberSpiderEntity.this.setRunning(false);
            UmberSpiderEntity.this.fleeLightFor = 50;
        }

        public void tick() {
            UmberSpiderEntity.this.fleeLightFor = 50;

            UmberSpiderEntity.this.setRunning(true);
            UmberSpiderEntity.this.setAttacking(false);

            if (UmberSpiderEntity.this.getNavigation().isDone()) {
                Vec3 vec3 = LandRandomPos.getPosAway(UmberSpiderEntity.this, 16, 8, UmberSpiderEntity.this.fleeFromPosition);
                if (vec3 != null) {
                    UmberSpiderEntity.this.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 1.5F);
                }
            }
        }
    }

    private class UmberSpiderPanicGoal extends Goal {

        protected double posX;
        protected double posY;
        protected double posZ;

        public UmberSpiderPanicGoal() {
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
            return UmberSpiderEntity.this.getLastHurtByMob() != null && UmberSpiderEntity.this.fleeLightFor <= 0 && !UmberSpiderEntity.this.isAttacking() && !UmberSpiderEntity.this.isOnFire();
        }

        protected boolean findRandomPosition() {
            Vec3 vec3 = LandRandomPos.getPos(UmberSpiderEntity.this, 16, 8);
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
            UmberSpiderEntity.this.getNavigation().moveTo(this.posX, this.posY, this.posZ, 1.5F);
            UmberSpiderEntity.this.setRunning(true);
            UmberSpiderEntity.this.setAttacking(false);
        }

        public void stop() {
            UmberSpiderEntity.this.setRunning(false);
        }

        public boolean canContinueToUse() {
            return !UmberSpiderEntity.this.getNavigation().isDone();
        }
    }
}
