package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.ai.goal.DicerAttackGoal;
import com.unusualmodding.opposing_force.entity.ai.goal.DicerLaserGoal;
import com.unusualmodding.opposing_force.entity.base.OPMonster;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import com.unusualmodding.opposing_force.utils.SmoothAnimationState;
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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class Dicer extends OPMonster {

    private static final EntityDataAccessor<Boolean> LASERING = SynchedEntityData.defineId(Dicer.class, EntityDataSerializers.BOOLEAN);

    public final SmoothAnimationState slash1AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState slash2AnimationState = new SmoothAnimationState(1.0F);
    public final SmoothAnimationState crossSlashAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState laserAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState tailSpinAnimationState = new SmoothAnimationState();

    public boolean slashAlt = false;

    public int laserCooldown = 100 + this.getRandom().nextInt(100);
    public int slashCooldown = 0;
    public int crossSlashCooldown = 0;
    public int tailSpinCooldown = 0;

    public Dicer(EntityType<? extends OPMonster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 10;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 36.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.2D)
            .add(Attributes.ATTACK_DAMAGE, 8.0D)
            .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new DicerLaserGoal(this));
        this.goalSelector.addGoal(2, new DicerAttackGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public float getStepHeight() {
        if (this.getAttackState() == 3) {
            return 1.1F;
        }
        return 0.6F;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getPose() == Pose.STANDING) {
            if (laserCooldown > 0) laserCooldown--;
            if (slashCooldown > 0) slashCooldown--;
            if (tailSpinCooldown > 0) tailSpinCooldown--;
            if (crossSlashCooldown > 0) crossSlashCooldown--;
        }
    }

    @Override
    public void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.getPose() != OPPoses.LASERING.get() && this.getPose() != OPPoses.CROSS_SLASHING.get() && this.getPose() != OPPoses.TAIL_SPINNING.get(), this.tickCount);
        this.slash1AnimationState.animateWhen(this.getPose() == OPPoses.ATTACKING.get() && !slashAlt, this.tickCount);
        this.slash2AnimationState.animateWhen(this.getPose() == OPPoses.ATTACKING.get() && slashAlt, this.tickCount);
        this.crossSlashAnimationState.animateWhen(this.getPose() == OPPoses.CROSS_SLASHING.get(), this.tickCount);
        this.tailSpinAnimationState.animateWhen(this.getPose() == OPPoses.TAIL_SPINNING.get(), this.tickCount);
        this.laserAnimationState.animateWhen(this.getPose() == OPPoses.LASERING.get(), this.tickCount);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if (super.doHurtTarget(entity)) {
            if (this.isElite()) {
                entity.setSecondsOnFire(5);
            }
            this.playSound(OPSoundEvents.DICER_ATTACK.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LASERING, false);
    }

    public boolean isLasering() {
        return this.entityData.get(LASERING);
    }

    public void setLasering(boolean lasering) {
        this.entityData.set(LASERING, lasering);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.DICER_IDLE.get();
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource source) {
        return OPSoundEvents.DICER_HURT.get();
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return OPSoundEvents.DICER_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.1F, 1.3F);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @javax.annotation.Nullable SpawnGroupData spawnData, @javax.annotation.Nullable CompoundTag compoundTag) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
        RandomSource random = level.getRandom();
        if (random.nextInt(this.getEliteSpawnChance()) == 0) {
            this.setElite(true);
            this.setEliteStats(this);
        }
        return spawnData;
    }

    @Override
    protected void dropFromLootTable(@NotNull DamageSource source, boolean drops) {
        if (this.isElite()) {
            for (int i = 0; i < 2; i++) {
                super.dropFromLootTable(source, drops);
            }
        } else {
            super.dropFromLootTable(source, drops);
        }
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource damageSource, int amount, boolean drops) {
        super.dropCustomDeathLoot(damageSource, amount, drops);
        Entity entity = damageSource.getEntity();
        if (entity instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                ItemStack itemstack = new ItemStack(OPItems.DICER_HEAD.get());
                creeper.increaseDroppedSkulls();
                this.spawnAtLocation(itemstack);
            }
        }
    }

    public static boolean canDicerSpawn(EntityType<Dicer> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        //OPWorldData.get(level.getLevel()).hasNetherBeenEnteredBefore() &&
        return level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(level, pos, random) && checkMobSpawnRules(entityType, level, spawnType, pos, random);
    }
}
