package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.ai.goal.RambleAttackGoal;
import com.unusualmodding.opposing_force.entity.ai.navigation.SmoothGroundPathNavigation;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Rambler extends Monster {

    private static final EntityDataAccessor<Boolean> FLAILING = SynchedEntityData.defineId(Rambler.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> FLAIL_COOLDOWN = SynchedEntityData.defineId(Rambler.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MIDDLE_SKULL = SynchedEntityData.defineId(Rambler.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> LEFT_SKULL = SynchedEntityData.defineId(Rambler.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> RIGHT_SKULL = SynchedEntityData.defineId(Rambler.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState jab1AnimationState = new AnimationState();
    public final AnimationState jab2AnimationState = new AnimationState();
    public final AnimationState jab3AnimationState = new AnimationState();
    public final AnimationState jab4AnimationState = new AnimationState();
    public final AnimationState jabRushAnimationState = new AnimationState();
    public final AnimationState rollStartAnimationState = new AnimationState();
    public final AnimationState rollAnimationState = new AnimationState();
    public final AnimationState rollEndAnimationState = new AnimationState();
    public final AnimationState flailStartAnimationState = new AnimationState();
    public final AnimationState flailAnimationState = new AnimationState();
    public final AnimationState flailEndAnimationState = new AnimationState();
    public final AnimationState recoverAnimationState = new AnimationState();

    public Rambler(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 15;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15F)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.2D)
                .add(Attributes.KNOCKBACK_RESISTANCE,1.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RambleAttackGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new FleeSunGoal(this, 1.2D));
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.2D, 1.2D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        return new SmoothGroundPathNavigation(this, level);
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.7F;
    }

    @Override
    public float getStepHeight() {
        if (this.isFlailing()) {
            return 1.0F;
        }
        return 0.6F;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FLAILING, false);
        this.entityData.define(FLAIL_COOLDOWN, 0);
        this.entityData.define(MIDDLE_SKULL, 0);
        this.entityData.define(LEFT_SKULL, 0);
        this.entityData.define(RIGHT_SKULL, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Flailing", this.isFlailing());
        compoundTag.putInt("FlailCooldown", this.getFlailCooldown());
        compoundTag.putInt("MiddleSkull", this.getMiddleSkull());
        compoundTag.putInt("LeftSkull", this.getLeftSkull());
        compoundTag.putInt("RightSkull", this.getRightSkull());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setFlailing(compoundTag.getBoolean("Flailing"));
        this.setFlailCooldown(compoundTag.getInt("FlailCooldown"));
        this.setMiddleSkull(compoundTag.getInt("MiddleSkull"));
        this.setLeftSkull(compoundTag.getInt("LeftSkull"));
        this.setRightSkull(compoundTag.getInt("RightSkull"));
    }

    public boolean isFlailing() {
        return this.entityData.get(FLAILING);
    }

    public void setFlailing(boolean flailing) {
        this.entityData.set(FLAILING, flailing);
    }

    public int getFlailCooldown() {
        return this.entityData.get(FLAIL_COOLDOWN);
    }

    public void setFlailCooldown(int cooldown) {
        this.entityData.set(FLAIL_COOLDOWN, cooldown);
    }

    public void flailCooldown() {
        this.entityData.set(FLAIL_COOLDOWN, 160);
    }

    public int getMiddleSkull() {
        return this.entityData.get(MIDDLE_SKULL);
    }

    public void setMiddleSkull(int skull) {
        this.entityData.set(MIDDLE_SKULL, skull);
    }

    public int getLeftSkull() {
        return this.entityData.get(LEFT_SKULL);
    }

    public void setLeftSkull(int skull) {
        this.entityData.set(LEFT_SKULL, skull);
    }

    public int getRightSkull() {
        return this.entityData.get(RIGHT_SKULL);
    }

    public void setRightSkull(int skull) {
        this.entityData.set(RIGHT_SKULL, skull);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getFlailCooldown() > 0) {
            this.setFlailCooldown(this.getFlailCooldown() - 1);
        }

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(!this.isFlailing() && this.getDeltaMovement().horizontalDistance() <= 1.0E-5F, this.tickCount);
        this.walkAnimationState.animateWhen(this.getDeltaMovement().horizontalDistance() > 1.0E-5F, this.tickCount);
        this.recoverAnimationState.animateWhen(!this.isFlailing() && this.getFlailCooldown() > 0, this.tickCount);
        this.flailAnimationState.animateWhen(this.isFlailing(), this.tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float f1 = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float f2 = Math.min(f1 * 12.0F, 1.0F);
        this.walkAnimation.update(f2, 0.4F);
    }

    public void hurtEntitiesAround(Vec3 center, float radius, boolean disablesShields) {
        AABB aabb = new AABB(center.subtract(radius, radius, radius), center.add(radius, radius, radius));
        DamageSource damageSource = this.damageSources().mobAttack(this);
        for (LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, aabb, EntitySelector.NO_CREATIVE_OR_SPECTATOR)) {
            if (!living.is(this) && !living.isAlliedTo(this) && living.getType() != this.getType() && living.distanceToSqr(center.x, center.y, center.z) <= radius * radius && !(living instanceof ArmorStand) && !living.isPassengerOfSameVehicle(this) && this.hasLineOfSight(living)) {
                if (living.isDamageSourceBlocked(damageSource) && disablesShields && living instanceof Player player) {
                    player.disableShield(true);
                }
                if (living.hurt(damageSource, (float) this.getAttribute(Attributes.ATTACK_DAMAGE).getValue())) {
                    living.knockback((float) this.getAttribute(Attributes.ATTACK_KNOCKBACK).getValue(), center.x - living.getX(), center.z - living.getZ());
                }
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float f) {
        if (this.isFlailing() || source.is(DamageTypeTags.IS_PROJECTILE)) {
            f *= 0.5F;
        }
        return super.hurt(source, f);
    }

    protected boolean isSunSensitive() {
        return true;
    }

    @Override
    public void aiStep() {
        if (this.isAlive()) {
            boolean flag = this.isSunSensitive() && this.isSunBurnTick();
            if (flag) {
                this.setSecondsOnFire(8);
            }
        }
        super.aiStep();
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int damage, boolean drops) {
        super.dropCustomDeathLoot(source, damage, drops);
        Entity entity = source.getEntity();
        int skullDrops = 1 + random.nextInt(3);
        if (entity instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                for (int i = 0; i < skullDrops; i++) {
                    creeper.increaseDroppedSkulls();
                    this.spawnAtLocation(OPItems.RAMBLE_SKULL.get());
                }
            }
        }
    }

    // sounds
    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.RAMBLER_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return OPSoundEvents.RAMBLER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return OPSoundEvents.RAMBLER_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.SKELETON_STEP, 0.15F, 0.85F);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);

        RamblerSkulls middle = RamblerSkulls.getRandom(this.getRandom());
        RamblerSkulls left = RamblerSkulls.getRandom(this.getRandom());
        RamblerSkulls right = RamblerSkulls.getRandom(this.getRandom());

        this.setMiddleSkull(middle.getSkull());
        this.setLeftSkull(left.getSkull());
        this.setRightSkull(right.getSkull());

        RandomSource random = level.getRandom();
        if (random.nextInt(100) == 0) {
            Rambler ramble = OPEntities.RAMBLER.get().create(this.level());
            if (ramble != null) {
                ramble.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                ramble.finalizeSpawn(level, difficulty, spawnType, null, null);
                ramble.startRiding(this);
            }
        } else if (random.nextInt(100) == 1) {
            Skeleton skeleton = EntityType.SKELETON.create(this.level());
            if (skeleton != null) {
                skeleton.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                skeleton.finalizeSpawn(level, difficulty, spawnType, null, null);
                skeleton.startRiding(this);
            }
        }
        return spawnData;
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getBbHeight() * 1.03F;
    }

    @Override
    public void rideTick() {
        super.rideTick();
        Entity entity = this.getControlledVehicle();
        if (entity instanceof PathfinderMob pathfindermob) {
            this.yBodyRot = pathfindermob.yBodyRot;
        }
    }

    public enum RamblerSkulls implements StringRepresentable, WeightedEntry {
        SKELETON(0,  "skeleton", 100),
        ANGRY(1, "angry", 100),
        CLASSIC(2, "classic", 100),
        EVIL(3, "evil", 100),
        GRIN(4, "grin", 10),
        SMILE(5, "smile", 100),
        STRANGE(6, "strange", 100),
        MUSICAL(7, "musical", 1),
        DWARVEN(8, "dwarven", 1),
        INDOMITABLE(9, "indomitable", 1),
        MAGMATIC(10, "magmatic", 1),
        CRUNDLY(11, "crundly", 1),
        IMPRISONED(12, "imprisoned", 1),
        NOSY(13, "nosy", 1),
        LEERING(14, "leering", 1),
        VALIANT(15, "valiant", 1);

        private final int skull;
        private final String name;
        private final Weight weight;

        RamblerSkulls(int skull, String name, int weight) {
            this.skull = skull;
            this.name = name;
            this.weight = Weight.of(weight);
        }

        public static RamblerSkulls getVariantId(int skulls) {
            for (RamblerSkulls skull : values()) {
                if (skull.skull == skulls) return skull;
            }
            return RamblerSkulls.SKELETON;
        }

        public static RamblerSkulls getRandom(RandomSource random) {
            int weight = 0;
            int skull = 0;
            for (RamblerSkulls skulls : RamblerSkulls.values()) {
                weight += skulls.getWeight().asInt();
            }
            for (RamblerSkulls skulls : RamblerSkulls.values()) {
                skull += skulls.getWeight().asInt();
                if (random.nextInt(weight) < skull) {
                    return skulls;
                }
            }
            return RamblerSkulls.SKELETON;
        }

        public int getSkull() {
            return this.skull;
        }

        @Override
        @NotNull
        public String getSerializedName() {
            return this.name;
        }

        @Override
        public @NotNull Weight getWeight() {
            return this.weight;
        }
    }
}
