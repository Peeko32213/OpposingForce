package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.ai.goal.*;
import com.unusualmodding.opposing_force.entity.ai.goal.whizz.WhizzAttackGoal;
import com.unusualmodding.opposing_force.entity.ai.goal.whizz.WhizzSwarmGoal;
import com.unusualmodding.opposing_force.entity.ai.goal.whizz.WhizzWanderGoal;
import com.unusualmodding.opposing_force.entity.base.SummonableMonster;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import com.unusualmodding.opposing_force.registry.OPBlocks;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class Whizz extends SummonableMonster {

    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(Whizz.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CAPTURED = SynchedEntityData.defineId(Whizz.class, EntityDataSerializers.BOOLEAN);

    @Nullable
    private WakeUpInfestationsGoal friendsGoal;

    @Nullable
    private Whizz leader;
    protected int swarmSize = 1;

    private int limitedLifeTicks = 0;

    public final AnimationState flyingAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    private int attackTicks;

    public Whizz(EntityType<? extends SummonableMonster> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.FLYING_SPEED, 0.7F)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    protected void registerGoals() {
        this.friendsGoal = new WakeUpInfestationsGoal(this, OPBlocks.INFESTED_AMETHYST_BLOCK.get());
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MonsterSitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new WhizzAttackGoal(this));
        this.goalSelector.addGoal(3, new MonsterFollowOwnerGoal(this, 1.2D, 5.0F, 2.0F, true));
        this.goalSelector.addGoal(4, this.friendsGoal);
        this.goalSelector.addGoal(5, new WhizzWanderGoal(this));
        this.goalSelector.addGoal(6, new WhizzSwarmGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(1, new MonsterOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new MonsterOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.6F;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.isInSittingPose()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = Vec3.ZERO;
        }
        super.travel(travelVec);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
            @Override
            public boolean isStableDestination(BlockPos pos) {
                return !level().getBlockState(pos.below()).isAir();
            }
        };
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(false);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos blockPos, LevelReader level) {
        return level.getBlockState(blockPos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.onGround() && this.getDeltaMovement().y < 0.0 && this.getTarget() == null) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
        }

        if (this.level().isClientSide) {
            this.setupAnimationStates();
            if (this.isAlive()) {
                OpposingForce.PROXY.playWorldSound(this, (byte) 2);
            }
        }

        if (this.hasFollowers() && this.level().random.nextInt(200) == 1) {
            List<? extends Whizz> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
            if (list.size() <= 1) {
                this.swarmSize = 1;
            }
        }

        if (attackTicks > 0) attackTicks--;
        if (attackTicks == 0 && this.getPose() == OPPoses.ATTACKING.get()) this.setPose(Pose.STANDING);

        if (this.isFromSummon() && !this.isCaptured()) {
            this.limitedLifeTicks++;
            if (this.limitedLifeTicks > 200) {
                this.limitedLifeTicks = 180;
                this.hurt(this.damageSources().starve(), 2.0F);
            }
        }
    }

    private void setupAnimationStates() {
        if (attackTicks == 0 && this.attackAnimationState.isStarted()) this.attackAnimationState.stop();
        this.flyingAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == OPPoses.ATTACKING.get()) {
                this.attackTicks = 20;
                this.attackAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.STANDING) {
                this.attackAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        OpposingForce.PROXY.clearSoundCacheFor(this);
        super.remove(reason);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if (super.doHurtTarget(entity)) {
            this.playSound(OPSoundEvents.WHIZZ_ATTACK.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hurt(DamageSource source, float pAmount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if ((source.getEntity() != null || source.is(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH)) && this.friendsGoal != null) {
                this.friendsGoal.notifyHurt();
            }
            return super.hurt(source, pAmount);
        }
    }

    @Override
    public boolean skipAttackInteraction(@NotNull Entity entity) {
        if (entity instanceof Player player) {
            if (((player.getMainHandItem().isCorrectToolForDrops(Blocks.AMETHYST_BLOCK.defaultBlockState()) && EnchantmentHelper.getTagEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem()) > 0) || (this.isTame() && this.getOwner() == player)) && !player.isCrouching()) {
                if (!level().isClientSide && isAlive()) {
                    ItemStack itemStack = OPItems.CAPTURED_WHIZZ.get().getDefaultInstance();
                    this.setCaptured(true);
                    if (!this.isTame()) {
                        this.tame(player);
                    }

                    level().playSound(null, getX(), getY(), getZ(), SoundEvents.AMETHYST_CLUSTER_BREAK, getSoundSource(), 1.0F, 0.8F);
                    if (level() instanceof ServerLevel) {
                        ((ServerLevel) level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.AMETHYST_CLUSTER.defaultBlockState()), getX(), getY() + getBbHeight() / 2.0D, getZ(), 16, getBbWidth() / 4.0F, getBbHeight() / 4.0F, getBbWidth() / 4.0F, 0.05D);
                    }

                    this.saveData(itemStack);
                    spawnAtLocation(itemStack);
                    discard();
                }
                return true;
            }
        }
        return super.skipAttackInteraction(entity);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
        this.entityData.define(CAPTURED, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Attacking", this.isAttacking());
        compoundTag.putBoolean("Captured", this.isCaptured());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAttacking(compoundTag.getBoolean("Attacking"));
        this.setCaptured(compoundTag.getBoolean("Captured"));
    }

    public void saveData(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        if (this.isCaptured()) compoundTag.putBoolean("Captured", this.isCaptured());
        if (this.isNoAi()) compoundTag.putBoolean("NoAI", this.isNoAi());
        if (this.isSilent()) compoundTag.putBoolean("Silent", this.isSilent());
        if (this.isNoGravity()) compoundTag.putBoolean("NoGravity", this.isNoGravity());
        if (this.hasGlowingTag()) compoundTag.putBoolean("Glowing", this.hasGlowingTag());
        if (this.isInvulnerable()) compoundTag.putBoolean("Invulnerable", this.isInvulnerable());
        if (this.isFromSummon()) compoundTag.putBoolean("FromSummon", this.isFromSummon());

        compoundTag.putFloat("Health", this.getHealth());
        if (this.getOwnerUUID() != null) compoundTag.putUUID("Owner", this.getOwnerUUID());
        if (this.hasCustomName()) itemStack.setHoverName(this.getCustomName());
    }

    public void loadData(CompoundTag compoundTag) {
        if (compoundTag.contains("Captured")) this.setCaptured(compoundTag.getBoolean("Captured"));
        if (compoundTag.contains("NoAI")) this.setNoAi(compoundTag.getBoolean("NoAI"));
        if (compoundTag.contains("Silent")) this.setSilent(compoundTag.getBoolean("Silent"));
        if (compoundTag.contains("NoGravity")) this.setNoGravity(compoundTag.getBoolean("NoGravity"));
        if (compoundTag.contains("Glowing")) this.setGlowingTag(compoundTag.getBoolean("Glowing"));
        if (compoundTag.contains("Invulnerable")) this.setInvulnerable(compoundTag.getBoolean("Invulnerable"));
        if (compoundTag.contains("Health", 99)) this.setHealth(compoundTag.getFloat("Health"));
        if (compoundTag.contains("FromSummon")) this.setFromSummon(compoundTag.getBoolean("FromSummon"));

        UUID uuid;
        if (compoundTag.hasUUID("Owner")) {
            uuid = compoundTag.getUUID("Owner");
        } else {
            String owner = compoundTag.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), owner);
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

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource source, int damage, boolean drops) {
        super.dropCustomDeathLoot(source, damage, drops);
        Entity entity = source.getEntity();
        if (entity instanceof Creeper creeper) {
            if (creeper.canDropMobsSkull()) {
                creeper.increaseDroppedSkulls();
                this.spawnAtLocation(OPItems.WHIZZ_HEAD.get());
            }
        }
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isCaptured() {
        return this.entityData.get(CAPTURED);
    }

    public void setCaptured(boolean captured) {
        this.entityData.set(CAPTURED, captured);
    }

    public int getMaxSwarmSize() {
        return 8;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return this.getMaxSwarmSize();
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive();
    }

    public void startFollowing(Whizz entity) {
        this.leader = entity;
        entity.addFollower();
    }

    public void stopFollowing() {
        this.leader.removeFollower();
        this.leader = null;
    }

    private void addFollower() {
        this.swarmSize++;
    }

    private void removeFollower() {
        this.swarmSize--;
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.swarmSize < this.getMaxSwarmSize();
    }

    public boolean hasFollowers() {
        return this.swarmSize > 1;
    }

    public boolean inRangeOfLeader() {
        if (this.leader != null) {
            return this.distanceToSqr(this.leader) <= 121.0D;
        }
        return false;
    }

    public void pathToLeader() {
        if (this.isFollower()) {
            if (this.leader != null) {
                this.getNavigation().moveTo(this.leader, 1.0D);
            }
        }
    }

    public void addFollowers(Stream<? extends Whizz> entity) {
        entity.limit(this.getMaxSwarmSize() - this.swarmSize).filter((entity1) -> entity1 != this).forEach((entity2) -> {
            entity2.startFollowing(this);
        });
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
        super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData, tag);
        if (spawnGroupData == null) {
            spawnGroupData = new SwarmSpawnGroupData(this);
        } else {
            this.startFollowing(((SwarmSpawnGroupData)spawnGroupData).leader);
        }
        return spawnGroupData;
    }

    public static class SwarmSpawnGroupData implements SpawnGroupData {
        public final Whizz leader;
        public SwarmSpawnGroupData(Whizz entity) {
            this.leader = entity;
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, @NotNull DamageSource damageSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double pY, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource source) {
        return OPSoundEvents.WHIZZ_HURT.get();
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return OPSoundEvents.WHIZZ_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
    }
}
