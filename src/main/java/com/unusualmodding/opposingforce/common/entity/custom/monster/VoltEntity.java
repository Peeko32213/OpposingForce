package com.unusualmodding.opposingforce.common.entity.custom.monster;

import com.google.common.collect.ImmutableMap;
import com.unusualmodding.opposingforce.common.entity.custom.ai.goal.attack.VoltAttackGoal;
import com.unusualmodding.opposingforce.common.entity.custom.base.EnhancedMonsterEntity;
import com.unusualmodding.opposingforce.common.entity.custom.ai.goal.SmartNearestTargetGoal;
import com.unusualmodding.opposingforce.common.entity.state.StateHelper;
import com.unusualmodding.opposingforce.common.entity.state.WeightedState;
import com.unusualmodding.opposingforce.common.entity.util.helper.SmartBodyHelper;
import com.unusualmodding.opposingforce.common.entity.util.navigator.SmoothGroundNavigation;
import com.unusualmodding.opposingforce.core.registry.OPSounds;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;
import java.util.List;

public class VoltEntity extends EnhancedMonsterEntity {

    private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING = SynchedEntityData.defineId(VoltEntity.class, EntityDataSerializers.BOOLEAN);

    private static final RawAnimation VOLT_IDLE = RawAnimation.begin().thenLoop("animation.volt.idle");
    private static final RawAnimation VOLT_IDLE_JAW = RawAnimation.begin().thenPlay("animation.volt.idle_jaw");
    private static final RawAnimation VOLT_WALK = RawAnimation.begin().thenLoop("animation.volt.move");
    private static final RawAnimation VOLT_SHOOT = RawAnimation.begin().thenPlay("animation.volt.attack");

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.4F;
        helper.bodyLagStill = 0.25F;
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public VoltEntity(EntityType<? extends EnhancedMonsterEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1F)
                .add(Attributes.ATTACK_DAMAGE, 8.0F);
    }

    public static <T extends Mob> boolean canFirstTierSpawn(EntityType<VoltEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {

        boolean isDeepDark = iServerWorld.getBiome(pos).is(Biomes.DEEP_DARK);
        return reason == MobSpawnType.SPAWNER || !iServerWorld.canSeeSky(pos) && pos.getY() <= 30 && checkUndergroundMonsterSpawnRules(entityType, iServerWorld, reason, pos, random) && !isDeepDark;
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
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new VoltAttackGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new SmartNearestTargetGoal(this, Player.class, true));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    // Sounds
    protected SoundEvent getAmbientSound() {
        return OPSounds.VOLT_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return OPSounds.VOLT_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return OPSounds.VOLT_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(SoundEvents.SLIME_SQUISH_SMALL, 0.2F, 0.8F);
    }

    @Override
    public float getSoundVolume() {
        return 0.6F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypeTags.IS_DROWNING) ;
    }

    public boolean isCharging() {
        return this.entityData.get(DATA_IS_CHARGING);
    }

    public void setCharging(boolean pCharging) {
        this.entityData.set(DATA_IS_CHARGING, pCharging);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_CHARGING, false);
    }

    public static <T extends Mob> boolean canSecondTierSpawn(EntityType<VoltEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
        boolean isDeepDark = iServerWorld.getBiome(pos).is(Biomes.DEEP_DARK);
        return reason == MobSpawnType.SPAWNER || !iServerWorld.canSeeSky(pos) && pos.getY() <= 0 && checkUndergroundMonsterSpawnRules(entityType, iServerWorld, reason, pos, random) && !isDeepDark;
    }

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return null;
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return List.of();
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if(this.isCharging()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();

            }
            pTravelVector = Vec3.ZERO;
            super.travel(pTravelVector);
        }
        else{
            super.travel(pTravelVector);
        }
    }

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<VoltEntity> event) {
        VoltEntity volt = event.getAnimatable();
        if (volt.level().isClientSide) {
            if (event.getKeyframeData().getSound().equals("volt_shoot")) {
                volt.level().playLocalSound(volt.getX(), volt.getY(), volt.getZ(), OPSounds.VOLT_SHOOT.get(), volt.getSoundSource(), 0.5F, volt.getVoicePitch(), false);
            }
        }
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<VoltEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<VoltEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);
    }

    protected <E extends VoltEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (!this.isCharging()) {
            if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
                event.setAndContinue(VOLT_WALK);
            } else {
                event.setAndContinue(VOLT_IDLE);
            }
        }
        return PlayState.CONTINUE;
    }

    // Attack animations
    protected <E extends VoltEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int attackState = this.getAttackState();
        if (attackState == 21) {
            event.setAndContinue(VOLT_SHOOT);
            return PlayState.CONTINUE;
        }
        else if (attackState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }
}
