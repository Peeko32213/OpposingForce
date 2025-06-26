package com.unusualmodding.opposing_force.entity;

import com.google.common.annotations.VisibleForTesting;
import com.unusualmodding.opposing_force.criterion.OPCriterion;
import com.unusualmodding.opposing_force.registry.OPEffects;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.scores.Team;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

public class Slug extends Monster implements OwnableEntity {

    private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(Slug.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_GROWABLE_SIZE = SynchedEntityData.defineId(Slug.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Slug.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(Slug.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Boolean> FROM_INFESTATION = SynchedEntityData.defineId(Slug.class, EntityDataSerializers.BOOLEAN);

    private int limitedLifeTicks = 0;

    public final AnimationState idleAnimationState = new AnimationState();

    public Slug(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.fixupDimensions();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.14F)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.02D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new SlugAttackGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, LivingEntity.class, 6.0F));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true, this::isInfestationSlug));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new SlugOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new SlugOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 1, false, false, this::hasInfestation));
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (super.doHurtTarget(entity)) {
            this.playSound(OPSoundEvents.SLUG_ATTACK.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == OPEffects.SLUG_INFESTATION.get()) {
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
    protected void dropFromLootTable(DamageSource source, boolean drops) {
        int extraEggs = this.getSlugSize() / 3;
        if (this.getSlugSize() > 4) {
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
        this.entityData.define(SIZE, 0);
        this.entityData.define(MAX_GROWABLE_SIZE, 0);
        this.entityData.define(OWNER_UUID, Optional.empty());
        this.entityData.define(DATA_FLAGS_ID, (byte) 0);
        this.entityData.define(FROM_INFESTATION, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Size", this.getSlugSize() - 1);
        compoundTag.putInt("MaxGrowableSize", this.getMaxGrowableSlugSize());
        compoundTag.putBoolean("FromInfestation", this.isFromInfestation());
        compoundTag.putInt("LifeTicks", this.limitedLifeTicks);

        if (this.getOwnerUUID() != null) {
            compoundTag.putUUID("Owner", this.getOwnerUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        this.setSlugSize(compoundTag.getInt("Size") + 1);
        super.readAdditionalSaveData(compoundTag);
        this.setMaxGrowableSlugSize(compoundTag.getInt("MaxGrowableSize"));
        this.setFromInfestation(compoundTag.getBoolean("FromInfestation"));
        this.limitedLifeTicks = compoundTag.getInt("LifeTicks");

        UUID uuid;
        if (compoundTag.hasUUID("Owner")) {
            uuid = compoundTag.getUUID("Owner");
        } else {
            String s = compoundTag.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }

        if (uuid != null) {
            try {
                this.setOwnerUUID(uuid);
                this.setTame(true);
            } catch (Throwable var4) {
                this.setTame(false);
            }
        }
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

    @Override
    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID pUuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(pUuid));
    }

    public boolean isFromInfestation() {
        return this.entityData.get(FROM_INFESTATION);
    }

    public void setFromInfestation(boolean infestation) {
        this.entityData.set(FROM_INFESTATION, infestation);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.44F;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (SIZE.equals(key)) {
            this.refreshDimensions();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        int size = this.getSlugSize();
        EntityDimensions dimensions = super.getDimensions(pPose);
        float scale = (dimensions.width + 0.1F * (float) size) / dimensions.width;
        return dimensions.scale(scale);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isFromInfestation()) {
            this.limitedLifeTicks++;
            if (this.limitedLifeTicks > 600) {
                this.limitedLifeTicks = 580;
                this.hurt(this.damageSources().starve(), 1.0F);
            }
        }

        if (this.level().isClientSide()){
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.isTame();
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.isTame();
    }

    public boolean isTame() {
        return (this.entityData.get(DATA_FLAGS_ID) & 4) != 0;
    }

    public void setTame(boolean tamed) {
        byte b = this.entityData.get(DATA_FLAGS_ID);
        if (tamed) {
            this.entityData.set(DATA_FLAGS_ID, (byte) (b | 4));
        } else {
            this.entityData.set(DATA_FLAGS_ID, (byte) (b & -5));
        }
    }

    public void tame(Player player) {
        this.setTame(true);
        this.setOwnerUUID(player.getUUID());
        if (player instanceof ServerPlayer serverPlayer) OPCriterion.TAME_SLUG.trigger(serverPlayer);

        RandomSource randomsource = this.getRandom();
        if (randomsource.nextInt(100) == 0) {
            this.setMaxGrowableSlugSize(8 + randomsource.nextInt(32));
        } else {
            this.setMaxGrowableSlugSize(1 + randomsource.nextInt(7));
        }
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        return !this.isOwnedBy(entity) && super.canAttack(entity);
    }

    public boolean hasInfestation(LivingEntity entity) {
        return entity.hasEffect(OPEffects.SLUG_INFESTATION.get());
    }

    public boolean isInfestationSlug(LivingEntity entity) {
        return !this.isFromInfestation();
    }

    public boolean isOwnedBy(LivingEntity pEntity) {
        return pEntity == this.getOwner();
    }

    public boolean wantsToAttack(LivingEntity entity, LivingEntity owner) {
        return true;
    }

    public Team getTeam() {
        if (this.isTame()) {
            LivingEntity livingentity = this.getOwner();
            if (livingentity != null) {
                return livingentity.getTeam();
            }
        }
        return super.getTeam();
    }

    public boolean isAlliedTo(Entity entity) {
        if (this.isTame()) {
            LivingEntity livingentity = this.getOwner();
            if (entity == livingentity) {
                return true;
            }
            if (livingentity != null) {
                return livingentity.isAlliedTo(entity);
            }
        }
        return super.isAlliedTo(entity);
    }

    public boolean canBeLeashed(Player player) {
        return !this.isLeashed() && this.isTame();
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        int size = this.getSlugSize();
        if (isTame() && itemstack.is(Blocks.SLIME_BLOCK.asItem()) && this.getSlugSize() <= this.getMaxGrowableSlugSize()) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            this.gameEvent(GameEvent.ENTITY_INTERACT);
            this.setSlugSize(size + 1);
            this.playSound(OPSoundEvents.SLUG_EAT.get(), this.getSoundVolume(), this.getVoicePitch() / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.level().broadcastEntityEvent(this, (byte) 39);
            return InteractionResult.SUCCESS;
        }
        if (isTame() && itemstack.is(Items.SLIME_BALL) && this.getHealth() < this.getMaxHealth()) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            this.gameEvent(GameEvent.ENTITY_INTERACT);
            this.heal(4.0F);
            this.playSound(OPSoundEvents.SLUG_EAT.get(), this.getSoundVolume(), this.getVoicePitch() / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.level().broadcastEntityEvent(this, (byte) 40);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 39) {
            for (int i = 0; i < 5; i++) {
                level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(0.5F), this.getY(0.8F), this.getRandomZ(0.5F), 0.0D, 0.0D, 0.0D);
            }
        }
        if (id == 40) {
            for (int i = 0; i < 5; i++) {
                level().addParticle(ParticleTypes.HEART, this.getRandomX(0.5F), this.getY(0.8F), this.getRandomZ(0.5F), 0.0D, 0.0D, 0.0D);
            }
        }
        super.handleEntityEvent(id);
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return OPSoundEvents.SLUG_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return OPSoundEvents.SLUG_DEATH.get();
    }

    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(OPSoundEvents.SLUG_SLIDE.get(), 0.15F, this.getVoicePitch());
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

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        RandomSource random = level.getRandom();

        if (random.nextInt(100) == 0) {
            if (random.nextFloat() < 0.5F * difficulty.getSpecialMultiplier()) {
                this.setSlugSize(8 + random.nextInt(32));
            }
            this.setMaxGrowableSlugSize(8 + random.nextInt(32));
        } else {
            this.setSlugSize(random.nextInt(8));
            this.setMaxGrowableSlugSize(1 + random.nextInt(7));
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
    }

    // goals
    private static class SlugAttackGoal extends MeleeAttackGoal {

        protected final Slug slug;

        public SlugAttackGoal(Slug slug) {
            super(slug, 1.2D, false);
            this.slug = slug;
        }

        @Override
        protected double getAttackReachSqr(LivingEntity pAttackTarget) {
            return this.slug.getBbWidth() * 1.2F * this.slug.getBbWidth() * 1.2F + pAttackTarget.getBbWidth();
        }
    }

    private static class SlugOwnerHurtTargetGoal extends TargetGoal {

        private final Slug slug;
        private LivingEntity ownerLastHurt;
        private int timestamp;

        public SlugOwnerHurtTargetGoal(Slug slug) {
            super(slug, false);
            this.slug = slug;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        public boolean canUse() {
            if (this.slug.isTame()) {
                LivingEntity owner = this.slug.getOwner();
                if (owner == null) {
                    return false;
                } else {
                    this.ownerLastHurt = owner.getLastHurtMob();
                    int hurtTime = owner.getLastHurtMobTimestamp();
                    return hurtTime != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT) && this.slug.wantsToAttack(this.ownerLastHurt, owner);
                }
            } else {
                return false;
            }
        }

        public void start() {
            this.mob.setTarget(this.ownerLastHurt);
            LivingEntity owner = this.slug.getOwner();
            if (owner != null) {
                this.timestamp = owner.getLastHurtMobTimestamp();
            }
            super.start();
        }
    }

    private static class SlugOwnerHurtByTargetGoal extends TargetGoal {

        private final Slug slug;
        private LivingEntity ownerLastHurtBy;
        private int timestamp;

        public SlugOwnerHurtByTargetGoal(Slug slug) {
            super(slug, false);
            this.slug = slug;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        public boolean canUse() {
            if (this.slug.isTame()) {
                LivingEntity owner = this.slug.getOwner();
                if (owner == null) {
                    return false;
                } else {
                    this.ownerLastHurtBy = owner.getLastHurtByMob();
                    int $$1 = owner.getLastHurtByMobTimestamp();
                    return $$1 != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT) && this.slug.wantsToAttack(this.ownerLastHurtBy, owner);
                }
            } else {
                return false;
            }
        }

        public void start() {
            this.mob.setTarget(this.ownerLastHurtBy);
            LivingEntity owner = this.slug.getOwner();
            if (owner != null) {
                this.timestamp = owner.getLastHurtByMobTimestamp();
            }
            super.start();
        }
    }
}
