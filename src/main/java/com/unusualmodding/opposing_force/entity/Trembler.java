package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.ai.goal.TremblerRollGoal;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import com.unusualmodding.opposing_force.registry.tags.OPDamageTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.Month;

public class Trembler extends Monster {

    private static final EntityDataAccessor<Boolean> ROLLING = SynchedEntityData.defineId(Trembler.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> ROLL_COOLDOWN = SynchedEntityData.defineId(Trembler.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> STUNNED_TICKS = SynchedEntityData.defineId(Trembler.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState rollAnimationState = new AnimationState();
    public final AnimationState stunnedAnimationState = new AnimationState();

    public Trembler(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new TremblerMoveControl();
        this.lookControl = new TremblerLookControl(this);
        this.xpReward = 10;
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new TremblerBodyRotationControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ARMOR, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15F)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new TremblerRollGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public float maxUpStep() {
        if (this.isRolling()) {
            return 1.1F;
        }
        return 0.6F;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        if (this.getStunnedTicks() <= 0) {
            if (this.getRollCooldown() > 0) {
                this.setRollCooldown(this.getRollCooldown() - 1);
            }
        }

        if (this.getStunnedTicks() > 0) {
            this.setSprinting(false);
            this.setStunnedTicks(this.getStunnedTicks() - 1);
            this.level().broadcastEntityEvent(this, (byte) 39);
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
        this.rollAnimationState.animateWhen(this.isRolling(), this.tickCount);
        this.stunnedAnimationState.animateWhen(this.getStunnedTicks() > 0, this.tickCount);
    }

    private void stunEffect() {
        if (this.random.nextInt(6) == 0) {
            double d = this.getX() - (double) this.getBbWidth() * Math.sin(this.yBodyRot * ((float) Math.PI / 180)) + (this.random.nextDouble() * 0.6 - 0.3);
            double e = this.getY() + (double) this.getBbHeight() - 0.3;
            double f = this.getZ() + (double) this.getBbWidth() * Math.cos(this.yBodyRot * ((float) Math.PI / 180)) + (this.random.nextDouble() * 0.6 - 0.3);
            this.level().addParticle(ParticleTypes.ENTITY_EFFECT, d, e, f, 0.5, 0.6, 0.5);
        }
    }

    @Override
    protected void blockedByShield(LivingEntity defender) {
        this.stunnedTicks();
        this.rollCooldown();
        this.setRolling(false);
        this.getNavigation().stop();
        defender.push(this);
        defender.hurtMarked = true;
        super.blockedByShield(defender);
    }

    @Override
    public boolean canDisableShield() {
        return true;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 39) {
            this.stunEffect();
        }
        super.handleEntityEvent(id);
    }

    public boolean isWithinYRange(LivingEntity target) {
        if (target == null) {
            return false;
        }
        return Math.abs(target.getY() - this.getY()) < 3;
    }

    @Override
    public boolean hurt(DamageSource damageSource, float pAmount) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            if (!damageSource.is(OPDamageTypeTags.DAMAGES_ROLLING_TREMBLER) && this.isRolling()) {
                this.playSound(OPSoundEvents.TREMBLER_BLOCK.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
                return false;
            }
            return super.hurt(damageSource, pAmount);
        }
    }

    @Override
    public boolean causeFallDamage(float f, float g, DamageSource damageSource) {
        return !this.isRolling();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ROLLING, false);
        this.entityData.define(ROLL_COOLDOWN, 60);
        this.entityData.define(STUNNED_TICKS, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Rolling", this.isRolling());
        compoundTag.putInt("RollCooldown", this.getRollCooldown());
        compoundTag.putInt("StunnedTicks", this.getStunnedTicks());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setRolling(compoundTag.getBoolean("Rolling"));
        this.setRollCooldown(compoundTag.getInt("RollCooldown"));
        this.setStunnedTicks(compoundTag.getInt("StunnedTicks"));
    }

    public boolean isRolling() {
        return this.entityData.get(ROLLING);
    }

    public void setRolling(boolean rolling) {
        this.entityData.set(ROLLING, rolling);
    }

    public int getRollCooldown() {
        return this.entityData.get(ROLL_COOLDOWN);
    }

    public void setRollCooldown(int cooldown) {
        this.entityData.set(ROLL_COOLDOWN, cooldown);
    }

    public void rollCooldown() {
        this.entityData.set(ROLL_COOLDOWN, 60);
    }

    public int getStunnedTicks() {
        return this.entityData.get(STUNNED_TICKS);
    }

    public void setStunnedTicks(int stunnedTicks) {
        this.entityData.set(STUNNED_TICKS, stunnedTicks);
    }

    public void stunnedTicks() {
        this.entityData.set(STUNNED_TICKS, 54);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        if ((this.random.nextInt(1000) == 0 && this.getName().getString().equalsIgnoreCase("valiant")) || (LocalDate.now().getMonth() == Month.APRIL && LocalDate.now().getDayOfMonth() == 1)) {
            return OPSoundEvents.TREMBLER_IDLE_FUNNY.get();
        }
        return OPSoundEvents.TREMBLER_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return OPSoundEvents.TREMBLER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return OPSoundEvents.TREMBLER_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        SoundType soundtype = state.getSoundType(this.level, pos, this);
        if (this.isRolling()) {
            this.playSound(SoundEvents.DRIPSTONE_BLOCK_STEP, 0.4F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 0.9F);
            this.playSound(soundtype.getStepSound(), 0.1F, soundtype.getPitch());
        } else {
            this.playSound(OPSoundEvents.SLUG_SLIDE.get(), 0.15F, 0.9F);
        }
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return checkTremblerSpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean checkTremblerSpawnRules(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() <= 0 && (random.nextInt(10) == 0 || pos.getY() <= 0) && level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawnNoSkylight(level, pos, random) && checkMobSpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean isDarkEnoughToSpawnNoSkylight(ServerLevelAccessor level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.SKY, pos) > 0) {
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

    private static class TremblerLookControl extends LookControl {

        protected final Trembler trembler;

        TremblerLookControl(Trembler trembler) {
            super(trembler);
            this.trembler = trembler;
        }

        @Override
        public void tick() {
            if (this.trembler.getStunnedTicks() <= 0) super.tick();
        }
    }

    private class TremblerBodyRotationControl extends BodyRotationControl {

        public TremblerBodyRotationControl(Trembler trembler) {
            super(trembler);
        }

        @Override
        public void clientTick() {
            if (Trembler.this.getStunnedTicks() <= 0) super.clientTick();
        }
    }

    private class TremblerMoveControl extends MoveControl {

        public TremblerMoveControl() {
            super(Trembler.this);
        }

        @Override
        public void tick() {
            if (Trembler.this.getStunnedTicks() <= 0) super.tick();
        }
    }
}
