package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.ai.goal.DicerAttackGoal;
import com.unusualmodding.opposing_force.entity.ai.goal.DicerLaserGoal;
import com.unusualmodding.opposing_force.entity.ai.navigation.SmoothGroundPathNavigation;
import com.unusualmodding.opposing_force.entity.utils.AttackState;
import com.unusualmodding.opposing_force.entity.utils.EliteVariant;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class Dicer extends Monster implements AttackState, EliteVariant {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Dicer.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(Dicer.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LASERING = SynchedEntityData.defineId(Dicer.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ARCH_DICER = SynchedEntityData.defineId(Dicer.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState slash1AnimationState = new AnimationState();
    public final AnimationState slash2AnimationState = new AnimationState();
    public final AnimationState crossSlashAnimationState = new AnimationState();
    public final AnimationState laserAnimationState = new AnimationState();
    public final AnimationState tailSpinAnimationState = new AnimationState();

    private int slashTicks;
    private int crossSlashTicks;
    private int tailSpinTicks;
    private int laserTicks;

    public int laserCooldown = 100 + this.getRandom().nextInt(100);

    public Dicer(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 15;
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
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothGroundPathNavigation(this, level);
    }

    @Override
    public float getStepHeight() {
        if (this.getAttackState() == 2) {
            return 1.0F;
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

        if (this.getPose() == Pose.STANDING) if (laserCooldown > 0) laserCooldown--;

        if (slashTicks > 0) slashTicks--;
        if (crossSlashTicks > 0) crossSlashTicks--;
        if (tailSpinTicks > 0) tailSpinTicks--;
        if (laserTicks > 0) laserTicks--;

        if (slashTicks == 0 && this.getPose() == OPPoses.SLASHING.get()) this.setPose(Pose.STANDING);
        if (crossSlashTicks == 0 && this.getPose() == OPPoses.CROSS_SLASHING.get()) this.setPose(Pose.STANDING);
        if (tailSpinTicks == 0 && this.getPose() == OPPoses.TAIL_SPINNING.get()) this.setPose(Pose.STANDING);
        if (laserTicks == 0 && this.getPose() == OPPoses.LASERING.get()) this.setPose(Pose.STANDING);

        if (this.level().isClientSide) this.setupAnimationStates();
    }

    private void setupAnimationStates() {
        if (slashTicks == 0 && (this.slash1AnimationState.isStarted() || this.slash2AnimationState.isStarted())) {
            this.slash1AnimationState.stop();
            this.slash2AnimationState.stop();
        }
        if (crossSlashTicks == 0 && this.crossSlashAnimationState.isStarted()) this.crossSlashAnimationState.stop();
        if (tailSpinTicks == 0 && this.tailSpinAnimationState.isStarted()) this.tailSpinAnimationState.stop();
        if (laserTicks == 0 && this.laserAnimationState.isStarted()) this.laserAnimationState.stop();
        this.idleAnimationState.animateWhen(this.getPose() != OPPoses.LASERING.get() && this.getPose() != OPPoses.CROSS_SLASHING.get() && this.getPose() != OPPoses.TAIL_SPINNING.get(), this.tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float f1 = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float f2 = Math.min(f1 * 8.0F, 1.0F);
        this.walkAnimation.update(f2, 0.4F);
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
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == OPPoses.SLASHING.get()) {
                this.slashTicks = 20;
                if (this.getRandom().nextBoolean()) {
                    this.slash2AnimationState.start(this.tickCount);
                } else {
                    this.slash1AnimationState.start(this.tickCount);
                }
            }
            else if (this.getPose() == OPPoses.CROSS_SLASHING.get()) {
                this.crossSlashTicks = 50;
                this.crossSlashAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == OPPoses.TAIL_SPINNING.get()) {
                this.tailSpinTicks = 20;
                this.tailSpinAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == OPPoses.LASERING.get()) {
                this.laserTicks = 100;
                this.laserAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.STANDING) {
                this.slash1AnimationState.stop();
                this.slash2AnimationState.stop();
                this.crossSlashAnimationState.stop();
                this.laserAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(RUNNING, false);
        this.entityData.define(LASERING, false);
        this.entityData.define(ARCH_DICER, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AttackState", this.getAttackState());
        compoundTag.putBoolean("Running", this.isRunning());
        compoundTag.putBoolean("Lasering", this.isLasering());
        compoundTag.putBoolean("ArchDicer", this.isElite());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAttackState(compoundTag.getInt("AttackState"));
        this.setRunning(compoundTag.getBoolean("Running"));
        this.setLasering(compoundTag.getBoolean("Lasering"));
        this.setElite(compoundTag.getBoolean("ArchDicer"));
    }

    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }

    public void setRunning(boolean running) {
        this.entityData.set(RUNNING, running);
    }

    @Override
    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    @Override
    public void setAttackState(int state) {
        this.entityData.set(ATTACK_STATE, state);
    }

    public boolean isLasering() {
        return this.entityData.get(LASERING);
    }

    public void setLasering(boolean lasering) {
        this.entityData.set(LASERING, lasering);
    }

    @Override
    public boolean isElite() {
        return this.entityData.get(ARCH_DICER);
    }

    @Override
    public void setElite(boolean elite) {
        this.entityData.set(ARCH_DICER, elite);
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
}
