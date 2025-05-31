package com.unusualmodding.opposing_force.entity;

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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Objects;

public class SlugEntity extends Monster {

    private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(SlugEntity.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();

    public SlugEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.14F)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.02D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new SlugAttackGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, LivingEntity.class, 6.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public float maxUpStep() {
        if (this.getSlugSize() > 3) {
            return 1.0F;
        }
        return 0.6F;
    }

    @Override
    public int getExperienceReward() {
        return this.getSlugSize() * 2 + 2;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SIZE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Size", this.getSlugSize());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSlugSize(compoundTag.getInt("Size"));
    }

    public void setSlugSize(int size) {
        this.entityData.set(SIZE, Mth.clamp(size, 0, 128));
    }

    public int getSlugSize() {
        return this.entityData.get(SIZE);
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.44F;
    }

    private void updateSlugAttributes() {
        this.refreshDimensions();
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2 + this.getSlugSize());
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8 + this.getSlugSize() * 4);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.02 + this.getSlugSize() * 0.02);
        this.setHealth((float) this.getAttribute(Attributes.MAX_HEALTH).getBaseValue());
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (SIZE.equals(pKey)) {
            this.updateSlugAttributes();
        }
        super.onSyncedDataUpdated(pKey);
    }

    public EntityDimensions getDimensions(Pose pPose) {
        int size = this.getSlugSize();
        EntityDimensions dimensions = super.getDimensions(pPose);
        float scale = (dimensions.width + 0.1F * (float) size) / dimensions.width;
        return dimensions.scale(scale);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }

        this.yBodyRot = Mth.approachDegrees(this.yBodyRotO, yBodyRot, getMaxHeadYRot());
    }

    public int getMaxHeadXRot() {
        return 2;
    }

    public int getMaxHeadYRot() {
        return 4;
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return OPSounds.SLUG_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return OPSounds.SLUG_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(OPSounds.SLUG_SLIDE.get(), 0.15F, this.getVoicePitch());
    }

    public float getVoicePitch() {
        if (this.getSlugSize() < 3) {
            return 1.3F;
        } else if (this.getSlugSize() < 7) {
            return 1.0F;
        } else if (this.getSlugSize() < 15) {
            return 0.7F;
        } else if (this.getSlugSize() < 30) {
            return 0.5F;
        } else {
            return 0.3F;
        }
    }

    public static <T extends Mob> boolean canFirstTierSpawn(EntityType<SlugEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
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

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        RandomSource randomsource = worldIn.getRandom();

        if (randomsource.nextInt(100) == 0) {
            this.setSlugSize(8 + randomsource.nextInt(32));
        } else {
            this.setSlugSize(randomsource.nextInt(8));
        }

        return spawnDataIn;
    }

    // goals
    private static class SlugAttackGoal extends MeleeAttackGoal {

        SlugEntity slug;

        public SlugAttackGoal(SlugEntity slug) {
            super(slug, 1.22, false);
            this.slug = slug;
        }

        @Override
        protected double getAttackReachSqr(LivingEntity pAttackTarget) {
            return this.mob.getBbWidth() * 1.16F * this.mob.getBbWidth() * 0.94F + pAttackTarget.getBbWidth();
        }
    }
}
