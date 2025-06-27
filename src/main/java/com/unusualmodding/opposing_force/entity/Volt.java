package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.base.IAnimatedAttacker;
import com.unusualmodding.opposing_force.entity.projectile.ElectricCharge;
import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import com.unusualmodding.opposing_force.registry.tags.OPDamageTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Volt extends Monster implements IAnimatedAttacker, PowerableMob {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Volt.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> LEAP_COOLDOWN = SynchedEntityData.defineId(Volt.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> CHARGED = SynchedEntityData.defineId(Volt.class, EntityDataSerializers.BOOLEAN);

    public float targetSquish;
    public float squish;
    public float oSquish;
    private boolean wasOnGround;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState shootAnimationState = new AnimationState();

    public Volt(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 16.0D).add(Attributes.MOVEMENT_SPEED, 0.11F).add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new VoltAttackGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(LEAP_COOLDOWN, 4 + random.nextInt(16 * 2));
        this.entityData.define(CHARGED, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AttackState", this.getAttackState());
        compoundTag.putInt("LeapCooldown", this.getLeapCooldown());
        if (this.entityData.get(CHARGED)) {
            compoundTag.putBoolean("Charged", true);
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAttackState(compoundTag.getInt("AttackState"));
        this.setLeapCooldown(compoundTag.getInt("LeapCooldown"));
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

    public int getLeapCooldown() {
        return this.entityData.get(LEAP_COOLDOWN);
    }

    public void setLeapCooldown(int cooldown) {
        this.entityData.set(LEAP_COOLDOWN, cooldown);
    }

    public void leapCooldown() {
        this.entityData.set(LEAP_COOLDOWN, 4 + random.nextInt(16 * 2));
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
            if (damageSource.is(OPDamageTypes.ELECTRIFIED)) {
                this.heal(4.0F);
                return false;
            }
            return super.hurt(damageSource, amount);
        }
    }

    @Override
    public void tick() {
        if (this.getLeapCooldown() > 0) {
            this.setLeapCooldown(this.getLeapCooldown() - 1);
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
        return checkVoltSpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean checkVoltSpawnRules(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() <= 16 && (random.nextInt(10) == 0 || pos.getY() <= 0) && level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawnNoSkylight(level, pos, random) && checkMobSpawnRules(entityType, level, spawnType, pos, random);
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

    // goals
    private static class VoltAttackGoal extends Goal {

        protected final Volt volt;
        private int attackTime = 0;

        public VoltAttackGoal(Volt mob) {
            this.volt = mob;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            return this.volt.getTarget() != null && this.volt.getTarget().isAlive();
        }

        public void start() {
            this.volt.setAggressive(true);
            this.volt.setAttackState(0);
            this.attackTime = 0;
        }

        public void stop() {
            this.volt.setAggressive(false);
            this.volt.setAttackState(0);
        }

        public void tick() {
            LivingEntity target = this.volt.getTarget();
            if (target != null) {

                this.volt.lookAt(this.volt.getTarget(), 30F, 30F);
                this.volt.getLookControl().setLookAt(this.volt.getTarget(), 30F, 30F);

                int attackState = this.volt.getAttackState();
                double distance = this.volt.distanceToSqr(target.getX(), target.getY(), target.getZ());
                boolean random = this.volt.getRandom().nextBoolean();

                switch (attackState) {
                    case 1 -> tickShootAttack();
                    case 2 -> tickLeap();
                    default -> {
                        this.checkForAttack(distance);
                        this.volt.getMoveControl().strafe(random ? 0.3F : -0.3F, random ? 0.3F : -0.3F);
                    }
                }
            }
        }

        protected void checkForAttack(double distance) {
            if (this.volt.onGround() || this.volt.isInWaterOrBubble()) {
                this.volt.setAttackState(1);
            }
            if (this.volt.getLeapCooldown() <= 0 && distance <= 30) {
                this.volt.setAttackState(2);
            }
        }

        protected void tickShootAttack() {
            this.attackTime++;
            LivingEntity target = this.volt.getTarget();
            this.volt.getNavigation().stop();

            if (this.attackTime == 8) {
                ElectricCharge projectile = new ElectricCharge(this.volt, this.volt.level(), this.volt.position().x(), this.volt.getEyePosition().y(), this.volt.position().z());
                double tx = target.getX() - this.volt.getX();
                double ty = target.getY() + target.getEyeHeight() - 1.1D - projectile.getY();
                double tz = target.getZ() - this.volt.getZ();
                float heightOffset = Mth.sqrt((float) (tx * tx + tz * tz)) * 0.01F;
                projectile.shoot(tx, ty + heightOffset, tz, 0.6F, 1.0F);
                this.volt.playSound(OPSoundEvents.VOLT_SHOOT.get(), 2.0F, 1.0F / (this.volt.getRandom().nextFloat() * 0.4F + 0.8F));
                if (this.volt.isPowered()) {
                    projectile.setChargeScale(2.5F);
                }
                this.volt.level().addFreshEntity(projectile);
            }
            if (this.attackTime >= 20) {
                this.attackTime = 0;
                this.volt.setAttackState(0);
            }
        }

        public void tickLeap() {
            this.attackTime++;
            LivingEntity target = this.volt.getTarget();
            this.volt.getNavigation().stop();
            float targetAngle = -1;
            float leapYaw = (float) Math.toRadians(targetAngle + 90 + this.volt.getRandom().nextFloat() * 150 - 75);
            if (this.volt.onGround()) {
                float speed = 1.25F;
                this.volt.playSound(OPSoundEvents.VOLT_SQUISH.get(), 0.2F, 1.0F);
                Vec3 m = this.volt.getDeltaMovement().add(speed * Math.cos(leapYaw), 0, speed * Math.sin(leapYaw));
                this.volt.setDeltaMovement(m.x, 0.7, m.z);
            }
            if (target != null) this.volt.getLookControl().setLookAt(target, 30, 30);
            if (this.attackTime >= 3) {
                this.attackTime = 0;
                this.volt.setAttackState(0);
                this.volt.leapCooldown();
            }
        }
    }
}
