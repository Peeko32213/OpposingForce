package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.misc.DicerLaser;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Objects;

public class Dicer extends Monster {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Dicer.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(Dicer.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> LASER_COOLDOWN = SynchedEntityData.defineId(Dicer.class, EntityDataSerializers.INT);

    @OnlyIn(Dist.CLIENT)
    public Vec3[] headPos;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState sliceAnimationState = new AnimationState();
    public final AnimationState laserAnimationState = new AnimationState();

    public Dicer(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        if (level.isClientSide) {
            this.headPos = new Vec3[] {
                    new Vec3(0, 0, 0)
            };
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 32.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.18D)
            .add(Attributes.ATTACK_DAMAGE, 8.0D)
            .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new DicerAttackGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public void tick() {
        super.tick();

        if (this.getLaserCooldown() > 0) {
            this.setLaserCooldown(this.getLaserCooldown() - 1);
        }

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive() && this.getDeltaMovement().horizontalDistanceSqr() <= 1.0E-6, this.tickCount);
        this.sliceAnimationState.animateWhen(this.isAlive() && this.getAttackState() == 1, this.tickCount);
        this.laserAnimationState.animateWhen(this.isAlive() && this.getAttackState() == 2, this.tickCount);

        if (this.getAttackState() > 0) {
            this.idleAnimationState.stop();
        }
    }

    public boolean doHurtTarget(Entity entity) {
        if (super.doHurtTarget(entity)) {
            this.playSound(OPSoundEvents.DICER_ATTACK.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RUNNING, false);
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(LASER_COOLDOWN, 120);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Running", this.isRunning());
        compoundTag.putInt("AttackState", this.getAttackState());
        compoundTag.putInt("LaserCooldown", this.getLaserCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setRunning(compoundTag.getBoolean("Running"));
        this.setAttackState(compoundTag.getInt("AttackState"));
        this.setLaserCooldown(compoundTag.getInt("LaserCooldown"));
    }

    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }

    public void setRunning(boolean running) {
        this.entityData.set(RUNNING, running);
    }

    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    public void setAttackState(int state) {
        this.entityData.set(ATTACK_STATE, state);
    }

    public int getLaserCooldown() {
        return this.entityData.get(LASER_COOLDOWN);
    }

    public void setLaserCooldown(int cooldown) {
        this.entityData.set(LASER_COOLDOWN, cooldown);
    }

    public void laserCooldown() {
        this.entityData.set(LASER_COOLDOWN, 120);
    }

    // Sounds
    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.DICER_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return OPSoundEvents.DICER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return OPSoundEvents.DICER_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos p_28301_, @NotNull BlockState p_28302_) {
        this.playSound(SoundEvents.ZOMBIE_STEP, 0.1F, 1.3F);
    }

    // Spawn rules
    public static <T extends Mob> boolean canSpawn(EntityType<Dicer> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
        boolean isDeepDark = iServerWorld.getBiome(pos).is(Biomes.DEEP_DARK);
        return reason == MobSpawnType.SPAWNER || !iServerWorld.canSeeSky(pos) && pos.getY() <= 0 && checkUndergroundMonsterSpawnRules(entityType, iServerWorld, reason, pos, random) && !isDeepDark;
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

    // goals
    private class DicerAttackGoal extends Goal {

        private int attackTime = 0;
        protected final Dicer dicer;

        public DicerAttackGoal(Dicer dicer) {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            this.dicer = dicer;
        }

        public boolean canUse() {
            return this.dicer.getTarget() != null && this.dicer.getTarget().isAlive();
        }

        public void start() {
            this.dicer.setRunning(true);
            this.dicer.setAttackState(0);
            this.attackTime = 0;
        }

        public void stop() {
            this.dicer.setRunning(false);
            this.dicer.setAttackState(0);
        }

        public void tick() {
            LivingEntity target = this.dicer.getTarget();
            if (target != null) {
                double distance = this.dicer.distanceToSqr(target.getX(), target.getY(), target.getZ());
                int attackState = this.dicer.getAttackState();

                switch (attackState) {
                    case 1 -> tickSliceAttack();
                    case 2 -> tickLaserAttack();
                    default -> {
                        this.dicer.getNavigation().moveTo(target, 2.0D);
                        this.dicer.lookAt(target, 30F, 30F);
                        this.dicer.getLookControl().setLookAt(target, 30F, 30F);
                        this.checkAttackRange(distance);
                    }
                }
            }
        }

        protected void checkAttackRange(double distance) {
            if (distance <= 5) {
                this.dicer.setAttackState(1);
            }
            if (distance > 6 && distance < 80 && this.dicer.getLaserCooldown() == 0) {
                this.dicer.setAttackState(2);
            }
        }

        public boolean requiresUpdateEveryTick() {
            return this.dicer.getAttackState() > 0;
        }

        protected void tickSliceAttack() {
            this.attackTime++;
            this.dicer.getNavigation().stop();
            if (this.attackTime == 11) {
                if (this.dicer.distanceTo(Objects.requireNonNull(this.dicer.getTarget())) < 2.7F) {
                    this.dicer.doHurtTarget(this.dicer.getTarget());
                    this.dicer.swing(InteractionHand.MAIN_HAND);
                }
            }
            if (this.attackTime >= 21) {
                this.attackTime = 0;
                this.dicer.setAttackState(0);
            }
        }

        protected void tickLaserAttack() {
            this.attackTime++;
            this.dicer.getNavigation().stop();
            LivingEntity target = this.dicer.getTarget();

            if (this.attackTime == 1) {
                DicerLaser laser = new DicerLaser(OPEntities.DICER_LASER.get(), Dicer.this.level(), Dicer.this, Dicer.this.getX() + 0.8F * Math.sin(-Dicer.this.getYRot() * Math.PI / 180), Dicer.this.getY() + 1.4F, Dicer.this.getZ() + 0.8F * Math.cos(-Dicer.this.getYRot() * Math.PI / 180), (float) ((Dicer.this.yHeadRot + 90) * Math.PI / 180), (float) (-Dicer.this.getXRot() * Math.PI / 180), 21, 3);
                this.dicer.level().addFreshEntity(laser);
            }

            if (this.attackTime == 13) {
                if (target != null) {
                    this.dicer.playSound(OPSoundEvents.DICER_LASER.get(), 2.0F, 1.0F / (this.dicer.getRandom().nextFloat() * 0.4F + 0.8F));
                    this.dicer.lookAt(target, 360F, 360F);
                    this.dicer.getLookControl().setLookAt(target, 30F, 30F);
                }
            }

            if (this.attackTime >= 13 && this.attackTime <= 41) {
                if (target != null) {
                    this.dicer.getLookControl().setLookAt(target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(), 1.5F, 80);
                }
            }
            if (this.attackTime > 50) {
                this.attackTime = 0;
                this.dicer.setAttackState(0);
                this.dicer.laserCooldown();
            }
        }
    }
}
