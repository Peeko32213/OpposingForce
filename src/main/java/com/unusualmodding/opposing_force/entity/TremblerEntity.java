package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.registry.OPSounds;
import com.unusualmodding.opposing_force.registry.tags.OPDamageTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Objects;

public class TremblerEntity extends Monster {

    private static final EntityDataAccessor<Boolean> ROLLING = SynchedEntityData.defineId(TremblerEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> ROLL_COOLDOWN = SynchedEntityData.defineId(TremblerEntity.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState rollAnimationState = new AnimationState();

    public TremblerEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.ARMOR, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.13F)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TremblerRollAttackGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)));
    }

    @Override
    public float maxUpStep() {
        if (this.isRolling()) {
            return 1.0F;
        }
        return 0.6F;
    }

    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        if (this.getRollCooldown() > 0) {
            this.setRollCooldown(this.getRollCooldown() - 1);
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
        this.rollAnimationState.animateWhen(this.isRolling() && this.isAlive(), this.tickCount);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean shouldHurt;
        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float knockback = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (shouldHurt = target.hurt(this.damageSources().mobAttack(this), damage)) {
            if (knockback > 0 && target instanceof LivingEntity) {
                ((LivingEntity) target).knockback(knockback * 0.5F, Mth.sin(this.getYRot() * ((float) Math.PI / 180)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }
            this.doEnchantDamageEffects(this, target);
            this.setLastHurtMob(target);
        }
        return shouldHurt;
    }

    public boolean hurt(DamageSource damageSource, float pAmount) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            if (!damageSource.is(OPDamageTypeTags.DAMAGES_ROLLING_TREMBLER) && this.isRolling()) {
                this.playSound(OPSounds.TREMBLER_BLOCK.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
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
        this.entityData.define(ROLL_COOLDOWN, 12 + random.nextInt(16 * 8));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Rolling", this.isRolling());
        compoundTag.putInt("RollCooldown", this.getRollCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setRolling(compoundTag.getBoolean("Rolling"));
        this.setRollCooldown(compoundTag.getInt("RollCooldown"));
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
        this.entityData.set(ROLL_COOLDOWN, 12 + random.nextInt(16 * 8));
    }

    public static boolean canFirstTierSpawn(EntityType<TremblerEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
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

    private static class TremblerRollAttackGoal extends Goal {

        protected final TremblerEntity trembler;
        private int attackTime = 0;
        private Vec3 rollMotion = new Vec3(0,0,0);

        public TremblerRollAttackGoal(TremblerEntity trembler) {
            this.trembler = trembler;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return trembler.getTarget() != null && trembler.getTarget().isAlive();
        }

        public void start() {
            this.trembler.setRolling(false);
            this.attackTime = 0;
        }

        public void stop() {
            this.trembler.setRolling(false);
        }

        public boolean requiresUpdateEveryTick() {
            return this.trembler.isRolling();
        }

        public void tick() {
            LivingEntity target = this.trembler.getTarget();
            if (target != null) {
                double distance = this.trembler.distanceToSqr(target.getX(), target.getY(), target.getZ());

                if (this.trembler.isRolling()) {
                    tickChargeAttack();
                } else {
                    if (distance <= 60 && this.trembler.getRollCooldown() <= 0) {
                        this.trembler.setRolling(true);
                    }
                    else {
                        if (distance < 13) {
                            this.trembler.getNavigation().moveTo(target, 0.6D);
                        } else {
                            this.trembler.getNavigation().moveTo(target, 1.4D);
                        }
                        this.trembler.lookAt(Objects.requireNonNull(target), 30F, 30F);
                        this.trembler.getLookControl().setLookAt(target, 30F, 30F);
                    }
                }
            }
        }

        protected void tickChargeAttack() {
            this.attackTime++;
            this.trembler.getNavigation().stop();
            Entity target = this.trembler.getTarget();

            if (this.attackTime == 12) {
                Vec3 targetPos = target.position();
                double x = -(this.trembler.position().x - targetPos.x);
                double z = -(this.trembler.position().z - targetPos.z);
                this.rollMotion = new Vec3(x, this.trembler.getDeltaMovement().y, z).normalize();
                this.trembler.lookAt(Objects.requireNonNull(target), 360F, 30F);
                this.trembler.getLookControl().setLookAt(target, 30F, 30F);
            }

            if (this.attackTime > 12 && this.attackTime < 48 + this.trembler.getRandom().nextInt(4)) {
                this.trembler.setDeltaMovement(this.rollMotion.x * 0.52, this.trembler.getDeltaMovement().y, this.rollMotion.z * 0.52);
                if (this.trembler.distanceTo(Objects.requireNonNull(target)) < 1.1F) {
                    this.trembler.doHurtTarget(target);
                }
            }

            if (this.attackTime >= 69) {
                this.attackTime = 0;
                this.trembler.setRolling(false);
                this.trembler.rollCooldown();
            }
        }
    }
}
