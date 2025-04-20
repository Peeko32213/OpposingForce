package com.unusualmodding.opposingforce.common.entity.custom.monster;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.unusualmodding.opposingforce.common.entity.custom.ai.goal.SmartNearestTargetGoal;
import com.unusualmodding.opposingforce.common.entity.custom.ai.goal.attack.DicerAttackGoal;
import com.unusualmodding.opposingforce.common.entity.custom.base.EnhancedMonsterEntity;
import com.unusualmodding.opposingforce.common.entity.state.EntityAction;
import com.unusualmodding.opposingforce.common.entity.state.RandomStateGoal;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;
import java.util.function.Predicate;

public class DicerEntity extends EnhancedMonsterEntity implements GeoAnimatable, GeoEntity {

    // Movement animations
    private static final RawAnimation DICER_WALK = RawAnimation.begin().thenLoop("animation.dicer.walk");
    private static final RawAnimation DICER_RUN = RawAnimation.begin().thenLoop("animation.dicer.run");
    private static final RawAnimation DICER_WALK_ALERT = RawAnimation.begin().thenLoop("animation.dicer.walk_alert");

    // Idle animations
    private static final RawAnimation DICER_IDLE = RawAnimation.begin().thenLoop("animation.dicer.idle");
    private static final RawAnimation DICER_IDLE_ALERT = RawAnimation.begin().thenLoop("animation.dicer.idle_alert");

    // Attack animations
    private static final RawAnimation DICER_TAIL_STAB1 = RawAnimation.begin().thenLoop("animation.dicer.tail_stab1");
    private static final RawAnimation DICER_TAIL_STAB2 = RawAnimation.begin().thenLoop("animation.dicer.tail_stab2");
    private static final RawAnimation DICER_CLAW = RawAnimation.begin().thenLoop("animation.dicer.claw");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> WALK_ALERT = SynchedEntityData.defineId(DicerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_ALERT = SynchedEntityData.defineId(DicerEntity.class, EntityDataSerializers.BOOLEAN);

    // Starting predicates
    private static final Predicate<LivingEntity> DICER_WALK_ALERT_PREDICATE = (e -> {
        if(e instanceof DicerEntity entity) {
            return !entity.isStillEnough() && !entity.isRunning();
        }
        return false;
    });

    private static final Predicate<LivingEntity> DICER_IDLE_ALERT_PREDICATE = (e -> {
        if(e instanceof DicerEntity entity) {
            return entity.isStillEnough() && !entity.isRunning();
        }
        return false;
    });

    // Idle actions
    private static final EntityAction DICER_WALK_ALERT_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper DICER_WALK_ALERT_STATE =
            StateHelper.Builder.state(WALK_ALERT, "dicer_alert_walk")
                    .playTime(320)
                    .stopTime(180)
                    .startingPredicate(DICER_WALK_ALERT_PREDICATE)
                    .entityAction(DICER_WALK_ALERT_ACTION)
                    .build();

    private static final EntityAction DICER_IDLE_ALERT_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper DICER_IDLE_ALERT_STATE =
            StateHelper.Builder.state(IDLE_ALERT, "dicer_alert_idle")
                    .playTime(320)
                    .stopTime(180)
                    .startingPredicate(DICER_IDLE_ALERT_PREDICATE)
                    .entityAction(DICER_IDLE_ALERT_ACTION)
                    .build();

    // States
    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                DICER_WALK_ALERT_STATE.getName(), DICER_WALK_ALERT_STATE,
                DICER_IDLE_ALERT_STATE.getName(), DICER_IDLE_ALERT_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(DICER_WALK_ALERT_STATE, 8),
                WeightedState.of(DICER_IDLE_ALERT_STATE, 8)
        );
    }

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.5F;
        helper.bodyLagStill = 0.25F;
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public DicerEntity(EntityType<? extends EnhancedMonsterEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 32.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.18D)
            .add(Attributes.ATTACK_DAMAGE, 8.0D)
            .add(Attributes.FOLLOW_RANGE,20.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new DicerAttackGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.targetSelector.addGoal(1, new SmartNearestTargetGoal(this, Player.class, true));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WALK_ALERT, false);
        this.entityData.define(IDLE_ALERT, false);
    }

    // Sounds
    protected SoundEvent getAmbientSound() {
        return OPSounds.DICER_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return OPSounds.DICER_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return OPSounds.DICER_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.1F, 1.3F);
    }

    @Override
    public float getSoundVolume() {
        return 0.75F;
    }

    // Spawn rules
    public static <T extends Mob> boolean canSecondTierSpawn(EntityType<DicerEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
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

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<DicerEntity> event) {
        DicerEntity dicer = event.getAnimatable();
        if (dicer.level().isClientSide) {
            if (event.getKeyframeData().getSound().equals("dicer_claw")) {
                dicer.level().playLocalSound(dicer.getX(), dicer.getY(), dicer.getZ(), OPSounds.DICER_ATTACK.get(), dicer.getSoundSource(), 0.25F, dicer.getVoicePitch() * 1.25F, false);
            }
            if (event.getKeyframeData().getSound().equals("dicer_tail_stab")) {
                dicer.level().playLocalSound(dicer.getX(), dicer.getY(), dicer.getZ(), OPSounds.DICER_ATTACK.get(), dicer.getSoundSource(), 0.25F, dicer.getVoicePitch(), false);
            }
        }
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<DicerEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<DicerEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);
    }

    protected <E extends DicerEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && this.getAttackState() == 0) {
            if (this.isRunning()) {
                event.setAndContinue(DICER_RUN);
            }
            else {
                if (this.getBooleanState(WALK_ALERT)) {
                    event.setAndContinue(DICER_WALK_ALERT);
                }
                else event.setAndContinue(DICER_WALK);
            }
            event.getController().setAnimationSpeed(1.0D);
        }
        else {
            if (this.getBooleanState(IDLE_ALERT)) {
                event.setAndContinue(DICER_IDLE_ALERT);
            }
            else event.setAndContinue(DICER_IDLE);
        }
        return PlayState.CONTINUE;
    }

    // Attack animations
    protected <E extends DicerEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int attackState = this.getAttackState();
        if (attackState == 21) {
            event.setAndContinue(DICER_TAIL_STAB1);
            return PlayState.CONTINUE;
        }
        else if (attackState == 22) {
            event.setAndContinue(DICER_TAIL_STAB2);
            return PlayState.CONTINUE;
        }
        else if (attackState == 23) {
            event.setAndContinue(DICER_CLAW);
            return PlayState.CONTINUE;
        }
        else if (attackState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }
}
