package com.unusualmodding.opposingforce.common.entity.custom.monster;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.unusualmodding.opposingforce.common.entity.state.EntityAction;
import com.unusualmodding.opposingforce.common.entity.state.RandomStateGoal;
import com.unusualmodding.opposingforce.common.entity.state.StateHelper;
import com.unusualmodding.opposingforce.common.entity.state.WeightedState;
import com.unusualmodding.opposingforce.common.entity.custom.ai.goal.SmartNearestTargetGoal;
import com.unusualmodding.opposingforce.common.entity.custom.base.EnhancedMonsterEntity;
import com.unusualmodding.opposingforce.common.entity.util.helper.SmartBodyHelper;
import com.unusualmodding.opposingforce.common.entity.util.navigator.SmoothGroundNavigation;
import com.unusualmodding.opposingforce.core.registry.OPSounds;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class SpindleEntity extends EnhancedMonsterEntity implements GeoAnimatable, GeoEntity {

    // Idle animations
    private static final RawAnimation SPINDLE_IDLE = RawAnimation.begin().thenLoop("animation.spindle.idle_3_blink");
    private static final RawAnimation SPINDLE_BURP = RawAnimation.begin().thenPlay("animation.spindle.idle_1_burp");
    private static final RawAnimation SPINDLE_STRETCH = RawAnimation.begin().thenPlay("animation.spindle.idle_2_stretch");
    private static final RawAnimation SPINDLE_LOOK_AROUND = RawAnimation.begin().thenPlay("animation.spindle.idle_4_look_around");
    private static final RawAnimation SPINDLE_SHARPEN_CLAWS = RawAnimation.begin().thenPlay("animation.spindle.idle_5_sharpen_claws");

    // Movement animations
    private static final RawAnimation SPINDLE_WALK = RawAnimation.begin().thenLoop("animation.spindle.walk");

    // Attack animations
    private static final RawAnimation SPINDLE_LEFT_STAB = RawAnimation.begin().thenPlay("animation.spindle.left_stab");

    // Idle accessors
    private static final EntityDataAccessor<Boolean> BURP = SynchedEntityData.defineId(SpindleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> STRETCH = SynchedEntityData.defineId(SpindleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LOOK_AROUND = SynchedEntityData.defineId(SpindleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SHARPEN_CLAWS = SynchedEntityData.defineId(SpindleEntity.class, EntityDataSerializers.BOOLEAN);

    // Starting predicates
    private static final Predicate<LivingEntity> SPINDLE_IDLE_PREDICATE = (e -> {
        if(e instanceof SpindleEntity entity) {
            return entity.isStillEnough() && !entity.isRunning();
        }
        return false;
    });

    private static final EntityAction SPINDLE_BURP_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper SPINDLE_BURP_STATE =
            StateHelper.Builder.state(BURP, "spindle_burp")
                    .playTime(40)
                    .stopTime(120)
                    .startingPredicate(SPINDLE_IDLE_PREDICATE)
                    .entityAction(SPINDLE_BURP_ACTION)
                    .build();

    private static final EntityAction SPINDLE_STRETCH_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper SPINDLE_STRETCH_STATE =
            StateHelper.Builder.state(STRETCH, "spindle_stretch")
                    .playTime(40)
                    .stopTime(140)
                    .startingPredicate(SPINDLE_IDLE_PREDICATE)
                    .entityAction(SPINDLE_STRETCH_ACTION)
                    .build();

    private static final EntityAction SPINDLE_LOOK_AROUND_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper SPINDLE_LOOK_AROUND_STATE =
            StateHelper.Builder.state(LOOK_AROUND, "spindle_look_around")
                    .playTime(80)
                    .stopTime(200)
                    .startingPredicate(SPINDLE_IDLE_PREDICATE)
                    .entityAction(SPINDLE_LOOK_AROUND_ACTION)
                    .build();

    private static final EntityAction SPINDLE_SHARPEN_CLAWS_ACTION = new EntityAction(0, (e) -> {}, 1);
    private static final StateHelper SPINDLE_SHARPEN_CLAWS_STATE =
            StateHelper.Builder.state(SHARPEN_CLAWS, "spindle_sharpen_claws")
                    .playTime(40)
                    .stopTime(150)
                    .startingPredicate(SPINDLE_IDLE_PREDICATE)
                    .entityAction(SPINDLE_SHARPEN_CLAWS_ACTION)
                    .build();

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                SPINDLE_BURP_STATE.getName(), SPINDLE_BURP_STATE,
                SPINDLE_STRETCH_STATE.getName(), SPINDLE_STRETCH_STATE,
                SPINDLE_LOOK_AROUND_STATE.getName(), SPINDLE_LOOK_AROUND_STATE,
                SPINDLE_SHARPEN_CLAWS_STATE.getName(), SPINDLE_SHARPEN_CLAWS_STATE
        );
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(SPINDLE_BURP_STATE, 8),
                WeightedState.of(SPINDLE_STRETCH_STATE, 10),
                WeightedState.of(SPINDLE_LOOK_AROUND_STATE, 10),
                WeightedState.of(SPINDLE_SHARPEN_CLAWS_STATE, 7)
        );
    }

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.4F;
        helper.bodyLagStill = 0.2F;
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public SpindleEntity(EntityType<? extends EnhancedMonsterEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.22D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ARMOR, 6.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStateGoal<>(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.targetSelector.addGoal(1, new SmartNearestTargetGoal(this, Player.class, true));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BURP, false);
        this.entityData.define(STRETCH, false);
        this.entityData.define(LOOK_AROUND, false);
        this.entityData.define(SHARPEN_CLAWS, false);
    }

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<SpindleEntity> event) {
        SpindleEntity spindle = event.getAnimatable();
        if (spindle.level().isClientSide) {
        }
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<SpindleEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controllers.add(controller);

        AnimationController<SpindleEntity> attack = new AnimationController<>(this, "attackController", 5, this::attackPredicate);
        attack.setSoundKeyframeHandler(this::soundListener);
        controllers.add(attack);
    }

    protected <E extends SpindleEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && this.getAttackState() == 0) {
            if (this.isRunning()) {
                event.setAndContinue(SPINDLE_WALK);
                event.getController().setAnimationSpeed(2.0D);
            }
            else {
                event.setAndContinue(SPINDLE_WALK);
                event.getController().setAnimationSpeed(1.0D);
            }
            event.getController().setAnimationSpeed(1.0D);
        }
        else {
            if (this.getBooleanState(BURP)) {
                event.setAndContinue(SPINDLE_BURP);
            }
            if (this.getBooleanState(STRETCH)) {
                event.setAndContinue(SPINDLE_STRETCH);
            }
            if (this.getBooleanState(LOOK_AROUND)) {
                event.setAndContinue(SPINDLE_LOOK_AROUND);
            }
            if (this.getBooleanState(SHARPEN_CLAWS)) {
                event.setAndContinue(SPINDLE_SHARPEN_CLAWS);
            }
            else event.setAndContinue(SPINDLE_IDLE);
        }
        return PlayState.CONTINUE;
    }

    // Attack animations
    protected <E extends SpindleEntity> PlayState attackPredicate(final AnimationState<E> event) {
        int attackState = this.getAttackState();
        if (attackState == 21) {
            event.setAndContinue(SPINDLE_LEFT_STAB);
            return PlayState.CONTINUE;
        }
        else if (attackState == 0) {
            event.getController().forceAnimationReset();
            return PlayState.STOP;
        }
        else return PlayState.CONTINUE;
    }
}
