package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.projectile.ElectricBall;
import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPSounds;
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
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class VoltEntity extends Monster {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(VoltEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> LEAP_COOLDOWN = SynchedEntityData.defineId(VoltEntity.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState shootAnimationState = new AnimationState();

    public VoltEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 16.0D).add(Attributes.MOVEMENT_SPEED, 0.11F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new VoltAttackGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(LEAP_COOLDOWN, 4 + random.nextInt(16 * 2));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AttackState", this.getAttackState());
        compoundTag.putInt("LeapCooldown", this.getLeapCooldown());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAttackState(compoundTag.getInt("AttackState"));
        this.setLeapCooldown(compoundTag.getInt("LeapCooldown"));
    }

    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }
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

    public void tick() {
        if (this.getLeapCooldown() > 0) {
            this.setLeapCooldown(this.getLeapCooldown() - 1);
        }

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
        super.tick();
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
        this.shootAnimationState.animateWhen(this.getAttackState() == 1, this.tickCount);
    }

    public static <T extends Mob> boolean canFirstTierSpawn(EntityType<VoltEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
        boolean isDeepDark = iServerWorld.getBiome(pos).is(Biomes.DEEP_DARK);
        return reason == MobSpawnType.SPAWNER || !iServerWorld.canSeeSky(pos) && pos.getY() <= 30 && checkUndergroundMonsterSpawnRules(entityType, iServerWorld, reason, pos, random) && !isDeepDark;
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

    // Sounds
    protected SoundEvent getAmbientSound() {
        return OPSounds.VOLT_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return OPSounds.VOLT_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return OPSounds.VOLT_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.SLIME_SQUISH_SMALL, 0.2F, 0.8F);
    }

    @Override
    public float getSoundVolume() {
        return 0.7F;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypeTags.IS_DROWNING) || source.is(OPDamageTypes.ELECTRIFIED);
    }

    public static <T extends Mob> boolean canSecondTierSpawn(EntityType<VoltEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
        boolean isDeepDark = iServerWorld.getBiome(pos).is(Biomes.DEEP_DARK);
        return reason == MobSpawnType.SPAWNER || !iServerWorld.canSeeSky(pos) && pos.getY() <= 0 && checkUndergroundMonsterSpawnRules(entityType, iServerWorld, reason, pos, random) && !isDeepDark;
    }

    // goals
    private static class VoltAttackGoal extends Goal {

        protected final VoltEntity volt;
        private int attackTime = 0;

        public VoltAttackGoal(VoltEntity mob) {
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
                        this.checkForCloseRangeAttack(distance);
                        this.volt.getMoveControl().strafe(random ? 0.3F : -0.3F, random ? 0.3F : -0.3F);
                    }
                }
            }
        }

        protected void checkForCloseRangeAttack(double distance) {
            if (this.volt.onGround()) {
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
                ElectricBall projectile = new ElectricBall(this.volt, this.volt.level(), this.volt.position().x(), this.volt.getEyePosition().y(), this.volt.position().z());
                double tx = target.getX() - this.volt.getX();
                double ty = target.getY() + target.getEyeHeight() - 1.1D - projectile.getY();
                double tz = target.getZ() - this.volt.getZ();
                float heightOffset = Mth.sqrt((float) (tx * tx + tz * tz)) * 0.01F;
                projectile.shoot(tx, ty + heightOffset, tz, 0.6F, 1.0F);
                this.volt.playSound(OPSounds.VOLT_SHOOT.get(), 1.0F, 1.0F / (this.volt.getRandom().nextFloat() * 0.4F + 0.8F));
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
