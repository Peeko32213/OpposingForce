package com.unusualmodding.opposing_force.entity;

import com.google.common.collect.ImmutableMap;
import com.unusualmodding.opposing_force.entity.base.EnhancedMonsterEntity;
import com.unusualmodding.opposing_force.entity.ai.state.StateHelper;
import com.unusualmodding.opposing_force.entity.ai.state.WeightedState;
import com.unusualmodding.opposing_force.entity.ai.util.helper.SmartBodyHelper;
import com.unusualmodding.opposing_force.entity.ai.util.navigator.SmoothGroundNavigation;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPSounds;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class RambleEntity extends EnhancedMonsterEntity implements GeoAnimatable, GeoEntity {

    private static final EntityDataAccessor<Boolean> FLAILING = SynchedEntityData.defineId(RambleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FLEEING = SynchedEntityData.defineId(RambleEntity.class, EntityDataSerializers.BOOLEAN);

    private int flailCooldown = 0;

    private int fleeTicks = 0;
    private Vec3 fleeFromPosition;

    // Movement animations
    private static final RawAnimation RAMBLE_WALK = RawAnimation.begin().thenLoop("animation.ramble.walk");

    // Idle animations
    private static final RawAnimation RAMBLE_IDLE = RawAnimation.begin().thenLoop("animation.ramble.idle");

    // Attack animations
    private static final RawAnimation RAMBLE_FLAIL = RawAnimation.begin().thenLoop("animation.ramble.idle_flail");
    private static final RawAnimation RAMBLE_AGGRO = RawAnimation.begin().thenLoop("animation.ramble.aggro");

    // Body control / navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        SmartBodyHelper helper = new SmartBodyHelper(this);
        helper.bodyLagMoving = 0.35F;
        helper.bodyLagStill = 0.1F;
        return helper;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return new SmoothGroundNavigation(this, levelIn);
    }

    public RambleEntity(EntityType<? extends EnhancedMonsterEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.13F)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.ARMOR,8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE,1.0D);
    }

    public static <T extends Mob> boolean canSecondTierSpawn(EntityType<RambleEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
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

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        RandomSource randomsource = pLevel.getRandom();

        if (randomsource.nextInt(100) == 0) {
            RambleEntity ramble = OPEntities.RAMBLE.get().create(this.level());
            if (ramble != null) {
                ramble.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                ramble.finalizeSpawn(pLevel, pDifficulty, pReason, null, null);
                ramble.startRiding(this);
            }
        }
        else if (randomsource.nextInt(100) == 1) {
            Skeleton skeleton = EntityType.SKELETON.create(this.level());
            if (skeleton != null) {
                skeleton.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                skeleton.finalizeSpawn(pLevel, pDifficulty, pReason, null, null);
                skeleton.startRiding(this);
            }
        }
        return pSpawnData;
    }

    public double getPassengersRidingOffset() {
        return this.getBbHeight() * 0.97F;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new RambleAttackGoal());
        this.goalSelector.addGoal(3, new RamblePanicGoal());
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public float getStepHeight() {
        if (this.isRunning()) {
            return 1.25F;
        }
        return 0.6F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FLAILING, false);
        this.entityData.define(FLEEING, false);
    }

    public boolean isFlailing() {
        return this.entityData.get(FLAILING);
    }

    public void setFlailing(boolean flailing) {
        this.entityData.set(FLAILING, flailing);
    }

    public boolean isFleeing() {
        return this.entityData.get(FLEEING);
    }

    public void setFleeing(boolean fleeing) {
        this.entityData.set(FLEEING, fleeing);
    }

    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (fleeTicks > 0) {
                fleeTicks--;
            }
        }
        LivingEntity target = this.getTarget();
        if (target != null && target.isAlive() && !(target instanceof Player player && player.isCreative())) {
            if (this.getHealth() < this.getMaxHealth() * 0.3F) {
                int i = 80 + random.nextInt(40);
                this.fleeFromPosition = target.position();
                this.fleeTicks = i;
                if (target instanceof Mob mob) {
                    mob.setTarget(null);
                    mob.setLastHurtByMob(null);
                    mob.setLastHurtMob(null);
                }
            }
        }
    }

    public boolean hurtEntitiesAround(Vec3 center, float radius, float damageAmount, float knockbackAmount, boolean disablesShields) {
        AABB aabb = new AABB(center.subtract(radius, radius, radius), center.add(radius, radius, radius));
        boolean flag = false;
        DamageSource damageSource = this.damageSources().mobAttack(this);
        for(LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, aabb, EntitySelector.NO_CREATIVE_OR_SPECTATOR)){
            if(!living.is(this) && !living.isAlliedTo(this) && living.getType() != this.getType() && living.distanceToSqr(center.x, center.y, center.z) <= radius * radius && !(living instanceof ArmorStand) && !living.isPassengerOfSameVehicle(this)) {
                if(living.isDamageSourceBlocked(damageSource) && disablesShields && living instanceof Player player){
                    player.disableShield(true);
                }
                if(living.hurt(damageSource, damageAmount)){
                    flag = true;
                    living.knockback(knockbackAmount, center.x - living.getX(), center.z - living.getZ());
                }
            }
        }
        return flag;
    }

    public boolean hurt(DamageSource source, float f) {
        if (this.isFlailing() || source.is(DamageTypeTags.IS_PROJECTILE)) {
            f *= 0.5F;
        }
        return super.hurt(source, f);
    }

    // sounds
    protected SoundEvent getAmbientSound() {
        return OPSounds.RAMBLE_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return OPSounds.RAMBLE_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return OPSounds.RAMBLE_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.SKELETON_STEP, 0.15F, 0.85F);
    }

    // Animation sounds
    private void soundListener(SoundKeyframeEvent<RambleEntity> event) {
        RambleEntity ramble = event.getAnimatable();
        if (ramble.level().isClientSide) {
            if (event.getKeyframeData().getSound().equals("ramble_flail")) {
                ramble.level().playLocalSound(ramble.getX(), ramble.getY(), ramble.getZ(), SoundEvents.SKELETON_HURT, ramble.getSoundSource(), 0.5F, ramble.getVoicePitch() * 0.8F, false);
            }
            if (event.getKeyframeData().getSound().equals("ramble_panic")) {
                ramble.level().playLocalSound(ramble.getX(), ramble.getY(), ramble.getZ(), SoundEvents.SKELETON_HURT, ramble.getSoundSource(), 0.5F, ramble.getVoicePitch(), false);
            }
        }
    }

    // Animation control
    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        AnimationController<RambleEntity> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        controller.setSoundKeyframeHandler(this::soundListener);
        controllers.add(controller);
    }

    protected <E extends RambleEntity> PlayState predicate(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        int attackState = this.getAttackState();
        if (!this.isFlailing()) {
            if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && !this.isInWater()) {
                if (this.isFleeing()) {
                    event.setAndContinue(RAMBLE_WALK);
                    event.getController().setAnimationSpeed(3.0F);
                } else if (this.isRunning() && !this.isFleeing()) {
                    event.setAndContinue(RAMBLE_WALK);
                    event.getController().setAnimationSpeed(1.5F);
                } else {
                    event.setAndContinue(RAMBLE_WALK);
                    event.getController().setAnimationSpeed(1.0F);
                }
            } else {
                event.setAndContinue(RAMBLE_IDLE);
                event.getController().setAnimationSpeed(1.0F);
                return PlayState.CONTINUE;
            }
        } else {
            if (attackState == 21) {
                event.setAndContinue(RAMBLE_AGGRO);
                event.getController().setAnimationSpeed(1.0F);
                return PlayState.CONTINUE;
            } if (attackState == 22) {
                event.setAndContinue(RAMBLE_FLAIL);
                event.getController().setAnimationSpeed(1.25F);
                return PlayState.CONTINUE;
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public ImmutableMap<String, StateHelper> getStates() {
        return null;
    }

    @Override
    public List<WeightedState<StateHelper>> getWeightedStatesToPerform() {
        return List.of();
    }

    // Goals
    private class RambleAttackGoal extends Goal {

        public RambleAttackGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            return RambleEntity.this.getTarget() != null && RambleEntity.this.getTarget().isAlive() && RambleEntity.this.getHealth() >= RambleEntity.this.getMaxHealth() * 0.3F;
        }

        public void start() {
            RambleEntity.this.setRunning(true);
            RambleEntity.this.setAttackState(0);
            RambleEntity.this.attackTick = 0;
            RambleEntity.this.flailCooldown = 0;
        }

        public void stop() {
            RambleEntity.this.setRunning(false);
            RambleEntity.this.setFlailing(false);
            RambleEntity.this.setAttackState(0);
        }

        public void tick() {
            LivingEntity target = RambleEntity.this.getTarget();
            if (target != null) {

                RambleEntity.this.lookAt(RambleEntity.this.getTarget(), 30F, 30F);
                RambleEntity.this.getLookControl().setLookAt(RambleEntity.this.getTarget(), 30F, 30F);

                double distance = RambleEntity.this.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int attackState = RambleEntity.this.getAttackState();

                if (attackState == 21) {
                    tickFlailAttack();
                    RambleEntity.this.getNavigation().moveTo(target, 2F);
                } else {
                    RambleEntity.this.getNavigation().moveTo(target, 1.5F);
                    RambleEntity.this.flailCooldown = Math.max(RambleEntity.this.flailCooldown - 1, 0);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }

        protected void checkForCloseRangeAttack (double distance){
            if (distance <= 14 && RambleEntity.this.flailCooldown <= 0) {
                RambleEntity.this.setAttackState(21);
            }
        }

        protected void tickFlailAttack () {
            RambleEntity.this.attackTick++;
            Vec3 pos = RambleEntity.this.position();
            RambleEntity.this.setFlailing(true);

            if(RambleEntity.this.attackTick >= 3) {
                RambleEntity.this.hurtEntitiesAround(pos, 3.2F, RambleEntity.this.getAttackDamage(), RambleEntity.this.getAttackKnockback(), true);
            }
            if(RambleEntity.this.attackTick >= 60) {
                RambleEntity.this.attackTick = 0;
                RambleEntity.this.setAttackState(0);
                RambleEntity.this.flailCooldown = RambleEntity.this.getRandom().nextInt(15) + 10;
                RambleEntity.this.setFlailing(false);
            }
        }
    }

    private class RamblePanicGoal extends Goal {

        public RamblePanicGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return RambleEntity.this.fleeTicks > 0 && RambleEntity.this.fleeFromPosition != null && RambleEntity.this.getTarget() != null && RambleEntity.this.getTarget().isAlive();
        }

        public void start() {
            RambleEntity.this.setAttackState(0);
            RambleEntity.this.attackTick = 0;
            RambleEntity.this.flailCooldown = 0;
        }

        public void stop() {
            RambleEntity.this.fleeFromPosition = null;
            RambleEntity.this.setRunning(false);
            RambleEntity.this.setFlailing(false);
            RambleEntity.this.setFleeing(false);
            RambleEntity.this.setAttackState(0);
        }

        public void tick() {
            LivingEntity targetEntity = RambleEntity.this.getTarget();

            RambleEntity.this.setRunning(true);
            RambleEntity.this.setFleeing(true);
            if (RambleEntity.this.getNavigation().isDone()) {
                Vec3 vec3 = LandRandomPos.getPosAway(RambleEntity.this, 4, 4, RambleEntity.this.fleeFromPosition);
                if (vec3 != null) {
                    RambleEntity.this.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 2.5F);
                }
            }

            if (targetEntity != null) {

                double distance = RambleEntity.this.distanceToSqr(targetEntity.getX(), targetEntity.getY(), targetEntity.getZ());
                int attackState = RambleEntity.this.getAttackState();

                if (attackState == 22) {
                    tickFlailPanic();
                } else {
                    RambleEntity.this.flailCooldown = Math.max(RambleEntity.this.flailCooldown - 1, 0);
                    this.checkForCloseRangeAttack(distance);
                }
            }
        }

        protected void checkForCloseRangeAttack (double distance){
            if (distance <= 12 && RambleEntity.this.flailCooldown <= 0) {
                RambleEntity.this.setAttackState(22);
            }
        }

        protected void tickFlailPanic () {
            RambleEntity.this.attackTick++;
            RambleEntity.this.getNavigation().stop();
            Vec3 pos = RambleEntity.this.position();
            RambleEntity.this.setFlailing(true);

            if (RambleEntity.this.attackTick >= 3) {
                RambleEntity.this.hurtEntitiesAround(pos, 3.2F, RambleEntity.this.getAttackDamage(), RambleEntity.this.getAttackKnockback(), true);
            }
            if (RambleEntity.this.attackTick >= 40) {
                RambleEntity.this.attackTick = 0;
                RambleEntity.this.setAttackState(0);
                RambleEntity.this.flailCooldown = RambleEntity.this.getRandom().nextInt(25) + 15;
                RambleEntity.this.setFlailing(false);
            }
        }
    }
}
