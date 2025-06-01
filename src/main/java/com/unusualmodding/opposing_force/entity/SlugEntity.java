package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.registry.OPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

public class SlugEntity extends Monster implements OwnableEntity {

    private static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(SlugEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_GROWABLE_SIZE = SynchedEntityData.defineId(SlugEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(SlugEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(SlugEntity.class, EntityDataSerializers.OPTIONAL_UUID);

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
        this.targetSelector.addGoal(3, new SlugOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new SlugOwnerHurtTargetGoal(this));
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
        this.entityData.define(MAX_GROWABLE_SIZE, 0);
        this.entityData.define(OWNER_UUID, Optional.empty());
        this.entityData.define(DATA_FLAGS_ID, (byte) 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("Size", this.getSlugSize());
        compoundTag.putInt("MaxGrowableSize", this.getMaxGrowableSlugSize());
        if (this.getOwnerUUID() != null) {
            compoundTag.putUUID("Owner", this.getOwnerUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSlugSize(compoundTag.getInt("Size"));
        this.setMaxGrowableSlugSize(compoundTag.getInt("MaxGrowableSize"));

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

    public void setSlugSize(int size) {
        this.entityData.set(SIZE, Mth.clamp(size, 0, 128));
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

    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID pUuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(pUuid));
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

        RandomSource randomsource = this.getRandom();
        if (randomsource.nextInt(100) == 0) {
            this.setMaxGrowableSlugSize(8 + randomsource.nextInt(32));
        } else {
            this.setMaxGrowableSlugSize(1 + randomsource.nextInt(7));
        }
    }

    public boolean canAttack(LivingEntity entity) {
        return !this.isOwnedBy(entity) && super.canAttack(entity);
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

    public boolean isAlliedTo(Entity pEntity) {
        if (this.isTame()) {
            LivingEntity livingentity = this.getOwner();
            if (pEntity == livingentity) {
                return true;
            }
            if (livingentity != null) {
                return livingentity.isAlliedTo(pEntity);
            }
        }
        return super.isAlliedTo(pEntity);
    }

    public void die(DamageSource source) {
        Component deathMessage = this.getCombatTracker().getDeathMessage();
        super.die(source);
        if (this.dead && !this.level().isClientSide && this.level().getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof ServerPlayer) {
            this.getOwner().sendSystemMessage(deathMessage);
        }
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
            this.playSound(OPSounds.SLUG_EAT.get(), this.getSoundVolume(), this.getVoicePitch());
            this.level().broadcastEntityEvent(this, (byte) 39);
            return InteractionResult.SUCCESS;
        }
        if (isTame() && itemstack.is(Items.SLIME_BALL) && this.getHealth() < this.getMaxHealth()) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            this.gameEvent(GameEvent.ENTITY_INTERACT);
            this.heal(4.0F);
            this.playSound(OPSounds.SLUG_EAT.get(), this.getSoundVolume(), this.getVoicePitch());
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
            this.setMaxGrowableSlugSize(8 + randomsource.nextInt(32));
        } else {
            this.setSlugSize(randomsource.nextInt(8));
            this.setMaxGrowableSlugSize(1 + randomsource.nextInt(7));
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

    private static class SlugOwnerHurtTargetGoal extends TargetGoal {

        private final SlugEntity slug;
        private LivingEntity ownerLastHurt;
        private int timestamp;

        public SlugOwnerHurtTargetGoal(SlugEntity slug) {
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

        private final SlugEntity slug;
        private LivingEntity ownerLastHurtBy;
        private int timestamp;

        public SlugOwnerHurtByTargetGoal(SlugEntity slug) {
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
