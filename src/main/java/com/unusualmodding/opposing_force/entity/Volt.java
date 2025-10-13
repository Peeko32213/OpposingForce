package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.ai.goal.VoltShootGoal;
import com.unusualmodding.opposing_force.entity.ai.goal.VoltLeapGoal;
import com.unusualmodding.opposing_force.entity.base.IAnimatedAttacker;
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

public class Volt extends Monster implements IAnimatedAttacker, PowerableMob {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Volt.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CHARGED = SynchedEntityData.defineId(Volt.class, EntityDataSerializers.BOOLEAN);

    public float targetSquish;
    public float squish;
    public float oSquish;
    private boolean wasOnGround;
    public int leapCooldown = 20 * 2 + this.getRandom().nextInt(10 * 2);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState shootAnimationState = new AnimationState();

    public Volt(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15F)
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
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AttackState", this.getAttackState());
        if (this.entityData.get(CHARGED)) {
            compoundTag.putBoolean("Charged", true);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAttackState(compoundTag.getInt("AttackState"));
        this.entityData.set(CHARGED, compoundTag.getBoolean("Charged"));
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
    public void thunderHit(ServerLevel level, LightningBolt lightning) {
        super.thunderHit(level, lightning);
        this.entityData.set(CHARGED, true);
        this.setRemainingFireTicks(0);
        this.heal(this.getMaxHealth());
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
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

        this.squish += (this.targetSquish - this.squish) * 0.5F;
        this.oSquish = this.squish;

        if (this.onGround() && !this.wasOnGround) {
            this.playSound(OPSoundEvents.VOLT_SQUISH.get(), 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.targetSquish = -0.5F;
        } else if (!this.onGround() && this.wasOnGround) {
            this.targetSquish = 1.0F;
        }

        this.wasOnGround = this.onGround();
        this.decreaseSquish();

        if (this.isPowered()) {
            if (this.tickCount % 100 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.heal(2);
            }
        }

        super.tick();
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
        this.shootAnimationState.animateWhen(this.getAttackState() == 1, this.tickCount);
    }

    protected void decreaseSquish() {
        this.targetSquish *= 0.6F;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.VOLT_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return OPSoundEvents.VOLT_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
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
    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypeTags.IS_FALL);
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        if (level.getLevel().isThundering()) return checkMonsterSpawnRules(entityType, level, spawnType, pos, random);
        return checkVoltSpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean checkVoltSpawnRules(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return (pos.getY() <= 16 && (random.nextInt(10) == 0 || pos.getY() <= 0) && level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawnNoSkylight(level, pos, random) && checkMobSpawnRules(entityType, level, spawnType, pos, random));
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
