package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Objects;

public class Ramble extends Monster {

    private static final EntityDataAccessor<Boolean> FLAILING = SynchedEntityData.defineId(Ramble.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> FLAIL_COOLDOWN = SynchedEntityData.defineId(Ramble.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flailAnimationState = new AnimationState();
    public final AnimationState cooldownAnimationState = new AnimationState();

    public Ramble(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.13F)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.3D)
                .add(Attributes.ARMOR,8.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE,1.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RambleAttackGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FleeSunGoal(this, 1.2D));
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.2D, 1.2D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.7F;
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
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Flailing", this.isFlailing());
        compoundTag.putInt("FlailCooldown", this.getFlailCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setFlailing(compoundTag.getBoolean("Flailing"));
        this.setFlailCooldown(compoundTag.getInt("FlailCooldown"));
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
        this.entityData.set(FLAIL_COOLDOWN, 80);
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
        this.idleAnimationState.animateWhen(this.isAlive() && !this.isFlailing() && this.getFlailCooldown() <= 0, this.tickCount);
        this.cooldownAnimationState.animateWhen(this.isAlive() && !this.isFlailing() && this.getFlailCooldown() > 0, this.tickCount);
        this.flailAnimationState.animateWhen(this.isAlive() && this.isFlailing(), this.tickCount);
    }

    public boolean hurtEntitiesAround(Vec3 center, float radius, boolean disablesShields) {
        AABB aabb = new AABB(center.subtract(radius, radius, radius), center.add(radius, radius, radius));
        boolean flag = false;
        DamageSource damageSource = this.damageSources().mobAttack(this);
        for (LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, aabb, EntitySelector.NO_CREATIVE_OR_SPECTATOR)) {
            if (!living.is(this) && !living.isAlliedTo(this) && living.getType() != this.getType() && living.distanceToSqr(center.x, center.y, center.z) <= radius * radius && !(living instanceof ArmorStand) && !living.isPassengerOfSameVehicle(this) && this.hasLineOfSight(living)) {
                if (living.isDamageSourceBlocked(damageSource) && disablesShields && living instanceof Player player) {
                    player.disableShield(true);
                }
                if (living.hurt(damageSource, (float) this.getAttribute(Attributes.ATTACK_DAMAGE).getValue())) {
                    flag = true;
                    living.knockback((float) this.getAttribute(Attributes.ATTACK_KNOCKBACK).getValue(), center.x - living.getX(), center.z - living.getZ());
                }
            }
        }
        return flag;
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
                    this.spawnAtLocation(Items.SKELETON_SKULL);
                }
            }
        }
    }

    // sounds
    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.RAMBLE_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return OPSoundEvents.RAMBLE_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return OPSoundEvents.RAMBLE_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.SKELETON_STEP, 0.15F, 0.85F);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<Ramble> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return checkMonsterSpawnRules(entityType, level, spawnType, pos, random);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        RandomSource randomsource = pLevel.getRandom();

        if (randomsource.nextInt(100) == 0) {
            Ramble ramble = OPEntities.RAMBLE.get().create(this.level());
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
        return this.getBbHeight() * 1.03F;
    }

    public void rideTick() {
        super.rideTick();
        Entity entity = this.getControlledVehicle();
        if (entity instanceof PathfinderMob pathfindermob) {
            this.yBodyRot = pathfindermob.yBodyRot;
        }
    }

    // Goals
    private static class RambleAttackGoal extends Goal {

        private final Ramble ramble;
        private int attackTime = 0;

        public RambleAttackGoal(Ramble ramble) {
            this.ramble = ramble;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            return !this.ramble.isVehicle() && this.ramble.getTarget() != null && this.ramble.getTarget().isAlive();
        }

        public void start() {
            this.ramble.setFlailing(false);
            this.attackTime = 0;
        }

        public void stop() {
            this.ramble.setFlailing(false);
        }

        public void tick() {
            LivingEntity target = this.ramble.getTarget();
            if (target != null) {
                this.ramble.lookAt(Objects.requireNonNull(target), 30F, 30F);
                this.ramble.getLookControl().setLookAt(target, 30F, 30F);

                double distance = this.ramble.distanceToSqr(target.getX(), target.getY(), target.getZ());

                if (this.ramble.isFlailing()) {
                    tickFlailAttack();
                    this.ramble.getNavigation().moveTo(target, 2.1D);
                } else {
                    if (this.ramble.getFlailCooldown() <= 0) {
                        this.ramble.getNavigation().moveTo(target, 1.7D);
                        if (distance <= 22) {
                            this.ramble.setFlailing(true);
                        }
                    } else {
                        this.ramble.getNavigation().stop();
                    }
                }
            }
        }

        protected void tickFlailAttack () {
            this.attackTime++;
            Vec3 pos = this.ramble.position();

            if (this.attackTime >= 3) {
                this.ramble.hurtEntitiesAround(pos, 2.9F, true);
                if ((this.ramble.tickCount / 2) % 2 == 0) {
                    this.ramble.playSound(OPSoundEvents.RAMBLE_ATTACK.get(), 1.0F, 1.0F / (this.ramble.getRandom().nextFloat() * 0.4F + 0.8F));
                }
            }
            if (this.attackTime >= 60) {
                this.attackTime = 0;
                this.ramble.flailCooldown();
                this.ramble.setFlailing(false);
            }
        }
    }
}
