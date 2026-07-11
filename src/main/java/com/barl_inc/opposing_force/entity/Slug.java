package com.barl_inc.opposing_force.entity;

import com.barl_inc.opposing_force.entity.ai.goal.*;
import com.google.common.annotations.VisibleForTesting;
import com.unusualmodding.opposing_force.entity.ai.goal.*;
import com.barl_inc.opposing_force.entity.ai.navigation.SmoothGroundPathNavigation;
import com.barl_inc.opposing_force.entity.base.SummonableMonster;
import com.barl_inc.opposing_force.entity.utils.EliteVariant;
import com.barl_inc.opposing_force.entity.utils.OPPoses;
import com.barl_inc.opposing_force.registry.OPCriterion;
import com.barl_inc.opposing_force.registry.OPMobEffects;
import com.barl_inc.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

@SuppressWarnings("deprecation")
public class Slug extends SummonableMonster implements EliteVariant {

    private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(Slug.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_GROWABLE_SIZE = SynchedEntityData.defineId(Slug.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TAME_ATTEMPTS = SynchedEntityData.defineId(Slug.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> LAUNCHED = SynchedEntityData.defineId(Slug.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> VILE = SynchedEntityData.defineId(Slug.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState launchStartAnimationState = new AnimationState();
    public final AnimationState launchedAnimationState = new AnimationState();

    private int launchedTicks = 0;

    public Slug(EntityType<? extends SummonableMonster> entityType, Level level) {
        super(entityType, level);
        this.fixupDimensions();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.14F)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.02D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MonsterSitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new SlugAttackGoal(this));
        this.goalSelector.addGoal(3, new MonsterFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new MonsterOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new MonsterOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 1, false, false, this::hasInfestation));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true, this::isInfestationSlug));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothGroundPathNavigation(this, level);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if (super.doHurtTarget(entity)) {
            int i = 0;
            Collection<MobEffectInstance> collection = this.getActiveEffects();
            if (this.level().getDifficulty() == Difficulty.NORMAL) {
                i = 5;
            } else if (this.level().getDifficulty() == Difficulty.HARD) {
                i = 10;
            }
            if (i > 0 && entity instanceof LivingEntity livingEntity) {
                if (!collection.isEmpty()) {
                    for (MobEffectInstance mobeffectinstance : collection) {
                        livingEntity.addEffect(new MobEffectInstance(mobeffectinstance.getEffect(), i * 20, 0), this);
                    }
                    this.removeAllEffects();
                }
                if (this.isElite()) {
                    livingEntity.addEffect(new MobEffectInstance(OPMobEffects.SLUG_INFESTATION.get(), i * 40, 0), this);
                }
            }
            this.playSound(OPSoundEvents.SLUG_ATTACK.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == OPMobEffects.SLUG_INFESTATION.get() || (effect.getEffect() == MobEffects.POISON && this.isElite())) {
            MobEffectEvent.Applicable event = new MobEffectEvent.Applicable(this, effect);
            MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == Event.Result.ALLOW;
        } else {
            return super.canBeAffected(effect);
        }
    }

    @Override
    public float maxUpStep() {
        if (this.getSlugSize() > 3) {
            return 1.0F;
        }
        return 0.6F;
    }

    @Override
    protected void dropFromLootTable(@NotNull DamageSource source, boolean drops) {
        int extraEggs = this.isElite() ? this.getSlugSize() : this.getSlugSize() / 2;
        if (this.getSlugSize() > 3 && !this.isFromSummon()) {
            for (int i = 0; i < extraEggs; i++) {
                super.dropFromLootTable(source, drops);
            }
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.getSlugSize() > 8;
    }

    @Override
    public boolean isPushable() {
        return this.getSlugSize() <= 8;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SIZE, 1);
        this.entityData.define(MAX_GROWABLE_SIZE, 0);
        this.entityData.define(TAME_ATTEMPTS, 0);
        this.entityData.define(LAUNCHED, false);
        this.entityData.define(VILE, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Size", this.getSlugSize() - 1);
        compoundTag.putInt("MaxGrowableSize", this.getMaxGrowableSlugSize());
        compoundTag.putInt("TameAttempts", this.getTameAttempts());
        compoundTag.putBoolean("Vile", this.isElite());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        this.setSlugSize(compoundTag.getInt("Size") + 1);
        super.readAdditionalSaveData(compoundTag);
        this.setMaxGrowableSlugSize(compoundTag.getInt("MaxGrowableSize"));
        this.setTameAttempts(compoundTag.getInt("TameAttempts"));
        this.setElite(compoundTag.getBoolean("Vile"));
    }

    @Override
    public boolean isElite() {
        return this.entityData.get(VILE);
    }

    @Override
    public void setElite(boolean elite) {
        this.entityData.set(VILE, elite);
    }

    public void setTameAttempts(int i) {
        this.entityData.set(TAME_ATTEMPTS, i);
    }

    public int getTameAttempts() {
        return this.entityData.get(TAME_ATTEMPTS);
    }

    @VisibleForTesting
    public void setSlugSize(int size) {
        int maxSize = Mth.clamp(size, 1, 127);
        this.entityData.set(SIZE, maxSize);
        this.reapplyPosition();
        this.refreshDimensions();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8 + this.getSlugSize() * 4);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(2 + this.getSlugSize() * 0.5F);
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.02 + this.getSlugSize() * 0.02);
        this.setHealth(this.getMaxHealth());
        this.xpReward = maxSize;
    }

    @Override
    public void refreshDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.refreshDimensions();
        this.setPos(x, y, z);
    }

    public int getSlugSize() {
        return this.entityData.get(SIZE);
    }

    public void setMaxGrowableSlugSize(int size) {
        this.entityData.set(MAX_GROWABLE_SIZE, Mth.clamp(size, 0, 128));
    }

    public int getMaxGrowableSlugSize() {
        return this.entityData.get(MAX_GROWABLE_SIZE);
    }

    public boolean wasLaunched() {
        return this.entityData.get(LAUNCHED);
    }

    public void setLaunched(boolean launched) {
        this.entityData.set(LAUNCHED, launched);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.44F;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        int size = this.getSlugSize();
        EntityDimensions dimensions = super.getDimensions(pose);
        float scale = (dimensions.width + 0.08F * (float) size) / dimensions.width;
        return dimensions.scale(scale);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isFromSummon()) {
            this.setLifeTicks(this.getLifeTicks() + 1);
            if (this.getLifeTicks() > this.getMaxLifeTicks()) {
                this.setLifeTicks(this.getMaxLifeTicks() - 20);
                this.hurt(this.damageSources().starve(), 2.0F);
            }
        }

        if (this.launchedTicks > 0) launchedTicks--;
        if ((this.launchedTicks == 0 || (this.launchedTicks < 10 && this.onGround())) && this.getPose() == OPPoses.LAUNCHED.get()) this.setPose(Pose.FALL_FLYING);
        if (this.getPose() == Pose.FALL_FLYING && this.onGround()) this.setPose(Pose.STANDING);

        if (this.wasLaunched()) {
            this.yBodyRot = this.getYRot();
            HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitResult.getType() != HitResult.Type.MISS) {
                if (hitResult.getType() == HitResult.Type.ENTITY) {
                    this.doHurtTarget(((EntityHitResult) hitResult).getEntity());
                    this.setLaunched(false);
                }
            }
            if (this.tickCount > 200) {
                this.setLaunched(false);
            }
        }

        if (this.level().isClientSide){
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.getPose() == Pose.STANDING, this.tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float f1 = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float f2 = Math.min(f1 * 10.0F, 1.0F);
        this.walkAnimation.update(f2, 0.4F);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entityDataAccessor) {
        if (SIZE.equals(entityDataAccessor)) {
            this.refreshDimensions();
            this.setYRot(this.yHeadRot);
            this.yBodyRot = this.yHeadRot;
        }
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == OPPoses.LAUNCHED.get()) {
                this.launchedTicks = 20;
                this.launchStartAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.FALL_FLYING) {
                this.launchedAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.STANDING) {
                this.launchStartAnimationState.stop();
                this.launchedAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    protected boolean canHitEntity(Entity entity) {
        return !entity.isSpectator() && !(entity instanceof Slug) && entity != this.getOwner();
    }

    @Override
    public void tame(Player player) {
        super.tame(player);
        if (player instanceof ServerPlayer serverPlayer) {
            OPCriterion.TAME_SLUG.trigger(serverPlayer);
        }

        RandomSource randomsource = this.getRandom();
        if (randomsource.nextInt(100) == 0) {
            this.setMaxGrowableSlugSize(this.getSlugSize() + 12);
        } else {
            this.setMaxGrowableSlugSize(this.getSlugSize() + 6);
        }
    }

    public boolean hasInfestation(LivingEntity entity) {
        return entity.hasEffect(OPMobEffects.SLUG_INFESTATION.get());
    }

    public boolean isInfestationSlug(LivingEntity entity) {
        return !this.isFromSummon();
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        int size = this.getSlugSize();
        int slimeNeeded = (1 + size) * 3;
        if (this.isTame() && itemstack.is(Blocks.SLIME_BLOCK.asItem()) && size <= this.getMaxGrowableSlugSize()) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            this.gameEvent(GameEvent.ENTITY_INTERACT);
            this.setSlugSize(size + 1);
            this.playSound(OPSoundEvents.SLUG_EAT.get(), this.getSoundVolume(), this.getVoicePitch() / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.level().broadcastEntityEvent(this, (byte) 8);
            return InteractionResult.SUCCESS;
        }
        if (itemstack.is(Items.SLIME_BALL)) {
            if (!this.isTame()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.gameEvent(GameEvent.ENTITY_INTERACT);
                this.setTameAttempts(this.getTameAttempts() + 1);
                this.level().broadcastEntityEvent(this, (byte) 6);
                if (this.getTameAttempts() > slimeNeeded) {
                    this.tame(player);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                    this.setTameAttempts(0);
                }
                this.playSound(OPSoundEvents.SLUG_EAT.get(), this.getSoundVolume(), this.getVoicePitch() / (this.getRandom().nextFloat() * 0.4F + 0.8F));
                return InteractionResult.SUCCESS;
            } else if (this.getHealth() < this.getMaxHealth() && this.isTame()) {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.gameEvent(GameEvent.ENTITY_INTERACT);
                this.heal(4.0F);
                this.playSound(OPSoundEvents.SLUG_EAT.get(), this.getSoundVolume(), this.getVoicePitch() / (this.getRandom().nextFloat() * 0.4F + 0.8F));
                this.level().broadcastEntityEvent(this, (byte) 7);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 8) {
            for (int i = 0; i < 7; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
            }
        }
        super.handleEntityEvent(id);
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return OPSoundEvents.SLUG_HURT.get();
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return OPSoundEvents.SLUG_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(OPSoundEvents.SLUG_SLIDE.get(), 0.15F, this.getVoicePitch());
    }

    @Override
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

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        RandomSource random = level.getRandom();
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);

        if (random.nextInt(this.getEliteSpawnChance()) == 0) {
            this.setElite(true);
        }

        if (this.isElite()) {
            if (random.nextInt(100) == 0) {
                if (random.nextFloat() < 0.5F * difficulty.getSpecialMultiplier()) {
                    this.setSlugSize(8 + random.nextInt(32));
                }
            } else {
                this.setSlugSize(4 + random.nextInt(8));
            }
        } else {
            if (random.nextInt(150) == 0) {
                if (random.nextFloat() < 0.5F * difficulty.getSpecialMultiplier()) {
                    this.setSlugSize(8 + random.nextInt(32));
                }
            } else if (random.nextInt(10) == 0) {
                this.setSlugSize(random.nextInt(8));
            } else {
                this.setSlugSize(random.nextInt(4));
            }
        }
        return spawnData;
    }
}
