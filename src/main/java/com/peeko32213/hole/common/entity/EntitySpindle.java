package com.peeko32213.hole.common.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.peeko32213.hole.common.entity.state.EntityAction;
import com.peeko32213.hole.common.entity.state.StateHelper;
import com.peeko32213.hole.common.entity.state.WeightedState;
import com.peeko32213.hole.common.entity.util.AbstractMonster;
import com.peeko32213.hole.common.entity.util.SmartNearestTargetGoal;
import com.peeko32213.hole.common.entity.util.StatedAbstractMonster;
import com.peeko32213.hole.common.entity.util.helper.HitboxHelper;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
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

public class EntitySpindle extends StatedAbstractMonster implements GeoAnimatable, GeoEntity {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Boolean> IDLE_1_AC = SynchedEntityData.defineId(EntitySpindle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_2_AC = SynchedEntityData.defineId(EntitySpindle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_3_AC = SynchedEntityData.defineId(EntitySpindle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_4_AC = SynchedEntityData.defineId(EntitySpindle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IDLE_5_AC = SynchedEntityData.defineId(EntitySpindle.class, EntityDataSerializers.BOOLEAN);


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


    public EntitySpindle(EntityType<? extends AbstractMonster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 180.0)
                .add(Attributes.MOVEMENT_SPEED, (double) 0.16F)
                .add(Attributes.ATTACK_DAMAGE, 18.0F)
                .add(Attributes.ARMOR, 20.0F)
                .add(Attributes.ARMOR_TOUGHNESS, 5.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(1, new EntitySpindle.SpindleMeleeAttackGoal(this,  1.8F, true));
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
        );    }


    static class SpindleMeleeAttackGoal extends Goal {

        protected final EntitySpindle mob;
        private final double speedModifier;
        private final boolean followingTargetEvenIfNotSeen;
        private Path path;
        private double pathedTargetX;
        private double pathedTargetY;
        private double pathedTargetZ;
        private int ticksUntilNextPathRecalculation;
        private int ticksUntilNextAttack;
        private long lastCanUseCheck;
        private int failedPathFindingPenalty = 0;
        private boolean canPenalize = false;
        private int animTime = 0;
        Vec3 slashOffSet = new Vec3(0, -0.3, 2);


        public SpindleMeleeAttackGoal(EntitySpindle p_i1636_1_, double p_i1636_2_, boolean p_i1636_4_) {
            this.mob = p_i1636_1_;
            this.speedModifier = p_i1636_2_;
            this.followingTargetEvenIfNotSeen = p_i1636_4_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            long i = this.mob.level().getGameTime();

            if (i - this.lastCanUseCheck < 20L) {
                return false;
            } else {
                this.lastCanUseCheck = i;
                LivingEntity livingentity = this.mob.getTarget();
                if (livingentity == null) {
                    return false;
                } else if (!livingentity.isAlive()) {
                    return false;
                } else {
                    if (canPenalize) {
                        if (--this.ticksUntilNextPathRecalculation <= 0) {
                            this.path = this.mob.getNavigation().createPath(livingentity, 0);
                            this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                            return this.path != null;
                        } else {
                            return true;
                        }
                    }
                    this.path = this.mob.getNavigation().createPath(livingentity, 0);
                    if (this.path != null) {
                        return true;
                    } else {
                        return this.getAttackReachSqr(livingentity) >= this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                    }
                }
            }

        }

        public boolean canContinueToUse() {

            LivingEntity livingentity = this.mob.getTarget();

            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else if (!this.followingTargetEvenIfNotSeen) {
                return !this.mob.getNavigation().isDone();
            } else if (!this.mob.isWithinRestriction(livingentity.blockPosition())) {
                return false;
            } else {
                return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player) livingentity).isCreative();
            }

        }

        public void start() {
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
            this.mob.setAggressive(true);
            this.ticksUntilNextPathRecalculation = 0;
            this.ticksUntilNextAttack = 0;
            this.animTime = 0;
            this.mob.setAnimationState(0);

        }

        public void stop() {
            LivingEntity livingentity = this.mob.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                this.mob.setTarget((LivingEntity) null);
            }
            this.mob.setAnimationState(0);
            this.mob.setAggressive(false);
        }

        public void tick() {


            LivingEntity target = this.mob.getTarget();
            double distance = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            double reach = this.getAttackReachSqr(target);
            int animState = this.mob.getAnimationState();
            Vec3 aim = this.mob.getLookAngle();
            Vec2 aim2d = new Vec2((float) (aim.x / (1 - Math.abs(aim.y))), (float) (aim.z / (1 - Math.abs(aim.y))));


            switch (animState) {
                case 21 -> tickDiceAttack();
                default -> {
                    this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.ticksUntilNextAttack = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                    this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
                    this.doMovement(target, distance);
                    this.checkForCloseRangeAttack(distance, reach);
                }
            }

        }

        protected void doMovement (LivingEntity livingentity, Double d0){


            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);


            if ((this.followingTargetEvenIfNotSeen || this.mob.getSensing().hasLineOfSight(livingentity)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || livingentity.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
                this.pathedTargetX = livingentity.getX();
                this.pathedTargetY = livingentity.getY();
                this.pathedTargetZ = livingentity.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.mob.getRandom().nextInt(7);
                if (this.canPenalize) {
                    this.ticksUntilNextPathRecalculation += failedPathFindingPenalty;
                    if (this.mob.getNavigation().getPath() != null) {
                        Node finalPathPoint = this.mob.getNavigation().getPath().getEndNode();
                        if (finalPathPoint != null && livingentity.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                            failedPathFindingPenalty = 0;
                        else
                            failedPathFindingPenalty += 10;
                    } else {
                        failedPathFindingPenalty += 10;
                    }
                }
                if (d0 > 1024.0D) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (d0 > 256.0D) {
                    this.ticksUntilNextPathRecalculation += 5;
                }

                if (!this.mob.getNavigation().moveTo(livingentity, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }
            }

        }


        protected void checkForCloseRangeAttack ( double distance, double reach){
            if (distance <= reach && this.ticksUntilNextAttack <= 0) {


                int r = this.mob.getRandom().nextInt(2048);
                if (r <= 800) {
                    this.mob.setAnimationState(21);
                }
            }
        }


        protected boolean getRangeCheck () {

            return
                    this.mob.distanceToSqr(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ())
                            <=
                            1.3F * this.getAttackReachSqr(this.mob.getTarget());

        }



        protected void tickDiceAttack () {
            animTime++;
            if(animTime==9) {
                performDiceAttack();
            }
            if(animTime>=12) {
                animTime=0;
                if (this.getRangeCheck()) {
                    this.mob.setAnimationState(22);
                }else {
                    this.mob.setAnimationState(0);
                    this.resetAttackCooldown();
                    this.ticksUntilNextPathRecalculation = 0;
                }
            }
        }

        Vec3 diceOffset = new Vec3(1, 1, 1);
        Vec3 tailOffset = new Vec3(2, 2, 2);

        protected void performDiceAttack () {
            HitboxHelper.PivotedPolyHitCheck(this.mob, this.slashOffSet, 5.5f, 1f, 5.5f, (ServerLevel)this.mob.level(), 10f, mob.damageSources().mobAttack(mob), 3f, false);
        }


        protected void resetAttackCooldown () {
            this.ticksUntilNextAttack = 0;
        }

        protected boolean isTimeToAttack () {
            return this.ticksUntilNextAttack <= 0;
        }

        protected int getTicksUntilNextAttack () {
            return this.ticksUntilNextAttack;
        }

        protected int getAttackInterval () {
            return 5;
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 1.3F + p_25556_.getBbWidth());
        }
    }

    protected <E extends EntitySpindle> PlayState controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        int animState = this.getAnimationState();
        {
            switch (animState) {
                case 21:
                    event.setAndContinue(LEFT_STAB);
                    break;
                default:
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
                    return event.setAndContinue(IDLE_1_BURP);

            }
            return PlayState.CONTINUE;
        }
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::controller));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }


}
