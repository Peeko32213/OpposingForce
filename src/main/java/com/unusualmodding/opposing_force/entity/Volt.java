package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.OpposingForceConfig;
import com.unusualmodding.opposing_force.entity.ai.goal.VoltShootGoal;
import com.unusualmodding.opposing_force.entity.ai.goal.VoltLeapGoal;
import com.unusualmodding.opposing_force.entity.utils.AttackState;
import com.unusualmodding.opposing_force.entity.utils.EliteVariant;
import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class Volt extends Monster implements AttackState, PowerableMob, EliteVariant {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Volt.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CHARGED = SynchedEntityData.defineId(Volt.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> QUASAR = SynchedEntityData.defineId(Volt.class, EntityDataSerializers.BOOLEAN);

    private boolean wasOnGround;
    public int leapCooldown = 20 * 2 + this.getRandom().nextInt(10 * 2);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState shootAnimationState = new AnimationState();
    public final AnimationState twitch1AnimationState = new AnimationState();
    public final AnimationState twitch2AnimationState = new AnimationState();
    public final AnimationState jumpAnimationState = new AnimationState();
    public final AnimationState jumpFallAnimationState = new AnimationState();
    public final AnimationState jumpLandAnimationState = new AnimationState();

    public Volt(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.21F)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new VoltLeapGoal(this));
        this.goalSelector.addGoal(2, new VoltShootGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(CHARGED, false);
        this.entityData.define(QUASAR, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AttackState", this.getAttackState());
        if (this.entityData.get(CHARGED)) {
            compoundTag.putBoolean("Charged", true);
        }
        compoundTag.putBoolean("Quasar", this.isElite());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAttackState(compoundTag.getInt("AttackState"));
        this.entityData.set(CHARGED, compoundTag.getBoolean("Charged"));
        this.setElite(compoundTag.getBoolean("Quasar"));
    }

    @Override
    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    @Override
    public void setAttackState(int attack) {
        this.entityData.set(ATTACK_STATE, attack);
    }

    @Override
    public boolean isPowered() {
        return this.entityData.get(CHARGED);
    }

    @Override
    public boolean isElite() {
        return this.entityData.get(QUASAR);
    }

    @Override
    public void setElite(boolean elite) {
        this.entityData.set(QUASAR, elite);
    }

    @Override
    public void thunderHit(@NotNull ServerLevel level, @NotNull LightningBolt lightning) {
        super.thunderHit(level, lightning);
        this.entityData.set(CHARGED, true);
        this.setRemainingFireTicks(0);
        this.heal(this.getMaxHealth());
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
    public boolean hurt(@NotNull DamageSource damageSource, float amount) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            if (damageSource.is(OPDamageTypes.ELECTRIC) || damageSource.is(OPDamageTypes.ELECTRIFIED)) {
                this.heal(2.0F);
                return false;
            }
            return super.hurt(damageSource, amount);
        }
    }

    @Override
    public void tick() {
        if (this.leapCooldown > 0) {
            this.leapCooldown--;
        }

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        if (this.onGround() && !this.wasOnGround) {
            this.playSound(OPSoundEvents.VOLT_SQUISH.get(), 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
        }

        this.wasOnGround = this.onGround();

        if (this.isPowered()) {
            if (this.tickCount % 100 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.heal(2);
            }
        }

        super.tick();
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.getDeltaMovement().horizontalDistance() <= 1.0E-5F, this.tickCount);
        this.shootAnimationState.animateWhen(this.getAttackState() == 1, this.tickCount);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.VOLT_IDLE.get();
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource source) {
        return OPSoundEvents.VOLT_HURT.get();
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return OPSoundEvents.VOLT_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(OPSoundEvents.VOLT_SQUISH.get(), 0.1F, 1.0F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypeTags.IS_FALL);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public void setEliteStats(Mob entity) {
        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(entity.getAttributeBaseValue(Attributes.MAX_HEALTH) * 1.5F);
        entity.setHealth(entity.getMaxHealth());
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
        RandomSource random = level.getRandom();
        if (random.nextInt(this.getEliteSpawnChance()) == 0) {
            this.setElite(true);
            this.setEliteStats(this);
        }
        return spawnData;
    }

    @SuppressWarnings("unused")
    public static boolean canVoltSpawn(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (level.getLevel().isThundering() && OpposingForceConfig.VOLT_SPAWNS_DURING_STORM.get()) return checkMonsterSpawnRules(entityType, level, spawnType, pos, random);
        return checkVoltSpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean checkVoltSpawnRules(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return (pos.getY() <= OpposingForceConfig.VOLT_SPAWN_HEIGHT.get() && level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawnNoSkylight(level, pos, random) && checkMobSpawnRules(entityType, level, spawnType, pos, random));
    }

    public static boolean isDarkEnoughToSpawnNoSkylight(ServerLevelAccessor level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.SKY, pos) > 0 && !level.getLevel().isThundering()) {
            return false;
        } else {
            DimensionType dimension = level.dimensionType();
            int lightLimit = dimension.monsterSpawnBlockLightLimit();
            if (lightLimit < 15 && level.getBrightness(LightLayer.BLOCK, pos) > lightLimit) {
                return false;
            } else {
                int lightTest = level.getLevel().isThundering() ? level.getMaxLocalRawBrightness(pos, 10) : level.getMaxLocalRawBrightness(pos);
                return lightTest <= dimension.monsterSpawnLightTest().sample(random);
            }
        }
    }
}
