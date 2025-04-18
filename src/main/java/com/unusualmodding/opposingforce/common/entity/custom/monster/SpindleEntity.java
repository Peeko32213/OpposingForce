package com.unusualmodding.opposingforce.common.entity.custom.monster;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.unusualmodding.opposingforce.common.entity.state.EntityAction;
import com.unusualmodding.opposingforce.common.entity.state.StateHelper;
import com.unusualmodding.opposingforce.common.entity.state.WeightedState;
import com.unusualmodding.opposingforce.common.entity.custom.base.AbstractMonster;
import com.unusualmodding.opposingforce.common.entity.custom.ai.goal.SmartNearestTargetGoal;
import com.unusualmodding.opposingforce.common.entity.custom.base.EnhancedMonsterEntity;
import com.unusualmodding.opposingforce.common.entity.util.helper.HitboxHelper;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.List;

public class SpindleEntity extends EnhancedMonsterEntity implements GeoAnimatable, GeoEntity {

    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(SpindleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(SpindleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(SpindleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_4_AC = SynchedEntityData.defineId(SpindleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_5_AC = SynchedEntityData.defineId(SpindleEntity.class, EntityDataSerializers.BOOLEAN);

    private static final RawAnimation IDLE_1_BURP = RawAnimation.begin().thenLoop("animation.spindle.idle_1_burp");
    private static final RawAnimation IDLE_2_STRETCH = RawAnimation.begin().thenLoop("animation.spindle.idle_2_stretch");
    private static final RawAnimation IDLE_3_BLINK = RawAnimation.begin().thenLoop("animation.spindle.idle_3_blink");
    private static final RawAnimation IDLE_4_LOOK_AROUND = RawAnimation.begin().thenLoop("animation.spindle.idle_4_look_around");
    private static final RawAnimation IDLE_5_SHARPEN_CLAWS = RawAnimation.begin().thenLoop("animation.spindle.idle_5_sharpen_claws");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.spindle.walk");
    private static final RawAnimation LEFT_STAB = RawAnimation.begin().thenPlay("animation.spindle.left_stab");

    private static final EntityAction SPINDLE_IDLE_1_ACTION = new EntityAction(0, (e) -> {
        return;
    }, 1);

    private static final StateHelper SPINDLE_IDLE_1_STATE =
            StateHelper.Builder.state(IDLE_1_AC, "idle_1_burp")
                    .playTime(70)
                    .stopTime(100)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(SPINDLE_IDLE_1_ACTION)
                    .build();

    private static final EntityAction SPINDLE_IDLE_2_ACTION = new EntityAction(0, (e) -> {
        return;
    }, 1);

    private static final StateHelper SPINDLE_IDLE_2_STATE =
            StateHelper.Builder.state(IDLE_2_AC, "idle_2_stretch")
                    .playTime(70)
                    .stopTime(100)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(SPINDLE_IDLE_2_ACTION)
                    .build();

    private static final EntityAction SPINDLE_IDLE_3_ACTION = new EntityAction(0, (e) -> {
        return;
    }, 1);


    private static final StateHelper SPINDLE_IDLE_3_STATE =
            StateHelper.Builder.state(IDLE_3_AC, "idle_3_blink")
                    .playTime(70)
                    .stopTime(100)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(SPINDLE_IDLE_3_ACTION)
                    .build();

    private static final EntityAction SPINDLE_IDLE_4_ACTION = new EntityAction(0, (e) -> {
        return;
    }, 1);


    private static final StateHelper SPINDLE_IDLE_4_STATE =
            StateHelper.Builder.state(IDLE_4_AC, "idle_4_look_around")
                    .playTime(70)
                    .stopTime(100)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(SPINDLE_IDLE_3_ACTION)
                    .build();

    private static final EntityAction SPINDLE_IDLE_5_ACTION = new EntityAction(0, (e) -> {
        return;
    }, 1);


    private static final StateHelper SPINDLE_IDLE_5_STATE =
            StateHelper.Builder.state(IDLE_5_AC, "idle_5_sharpen_claws")
                    .playTime(70)
                    .stopTime(100)
                    .affectsAI(true)
                    .affectedFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK))
                    .entityAction(SPINDLE_IDLE_3_ACTION)
                    .build();


    public SpindleEntity(EntityType<? extends EnhancedMonsterEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.16D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ARMOR, 6.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(1, new SmartNearestTargetGoal(this, Player.class, true));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IDLE_1_AC, false);
        this.entityData.define(IDLE_2_AC, false);
        this.entityData.define(IDLE_3_AC, false);
        this.entityData.define(IDLE_4_AC, false);
        this.entityData.define(IDLE_5_AC, false);
    }

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return ImmutableMap.of(
                SPINDLE_IDLE_1_STATE.getName(), SPINDLE_IDLE_1_STATE,
                SPINDLE_IDLE_2_STATE.getName(), SPINDLE_IDLE_2_STATE,
                SPINDLE_IDLE_3_STATE.getName(), SPINDLE_IDLE_3_STATE,
                SPINDLE_IDLE_4_STATE.getName(), SPINDLE_IDLE_4_STATE,
                SPINDLE_IDLE_5_STATE.getName(), SPINDLE_IDLE_5_STATE
        );    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return ImmutableList.of(
                WeightedState.of(SPINDLE_IDLE_1_STATE, 55),
                WeightedState.of(SPINDLE_IDLE_2_STATE, 45),
                WeightedState.of(SPINDLE_IDLE_3_STATE, 30),
                WeightedState.of(SPINDLE_IDLE_4_STATE, 15),
                WeightedState.of(SPINDLE_IDLE_5_STATE, 10)
        );
    }

    protected <E extends SpindleEntity> PlayState controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6 && !getBooleanState(IDLE_2_AC) && !getBooleanState(IDLE_3_AC) && !getBooleanState(IDLE_4_AC) && !getBooleanState(IDLE_5_AC)) {
            event.setAndContinue(WALK);
            return PlayState.CONTINUE;
        }
        if (getBooleanState(IDLE_2_AC)) {
            return event.setAndContinue(IDLE_2_STRETCH);
        }
        if (getBooleanState(IDLE_3_AC)) {
            return event.setAndContinue(IDLE_3_BLINK);
        }
        if (getBooleanState(IDLE_4_AC)) {
            return event.setAndContinue(IDLE_4_LOOK_AROUND);
        }
        if (getBooleanState(IDLE_5_AC)) {
            return event.setAndContinue(IDLE_5_SHARPEN_CLAWS);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::controller));
    }
}
