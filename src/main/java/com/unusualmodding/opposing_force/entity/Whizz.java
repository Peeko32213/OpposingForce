package com.unusualmodding.opposing_force.entity;

import com.mojang.datafixers.DataFixUtils;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.blocks.InfestedAmethyst;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Whizz extends Monster implements OwnableEntity, Bucketable {

    private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(Whizz.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> CHARGE_COOLDOWN = SynchedEntityData.defineId(Whizz.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(Whizz.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Whizz.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Whizz.class, EntityDataSerializers.BOOLEAN);

    @Nullable
    private WhizzWakeUpFriendsGoal friendsGoal;

    @Nullable
    private Whizz leader;
    protected int swarmSize = 1;

    public final AnimationState flyAnimationState = new AnimationState();

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
            public boolean isStableDestination(BlockPos pos) {
                return !level().getBlockState(pos.below(2)).isAir();
            }
        };
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(false);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    public Whizz(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 6.0D).add(Attributes.FLYING_SPEED, 0.9F).add(Attributes.MOVEMENT_SPEED, 0.45F).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    protected void registerGoals() {
        this.friendsGoal = new WhizzWakeUpFriendsGoal(this);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new WhizzChargeAttackGoal(this));
        this.goalSelector.addGoal(3, this.friendsGoal);
        this.goalSelector.addGoal(4, new WhizzWanderGoal(this));
        this.goalSelector.addGoal(5, new WhizzSwarmGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(1, new WhizzOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new WhizzOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.6F;
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            this.setupAnimationStates();
            if (this.isAlive()) {
                OpposingForce.PROXY.playWorldSound(this, (byte) 2);
            }
        }

        if (this.getChargeCooldown() > 0) {
            this.setChargeCooldown(this.getChargeCooldown() - 1);
        }

        if (this.hasFollowers() && this.level().random.nextInt(200) == 1) {
            List<? extends Whizz> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
            if (list.size() <= 1) {
                this.swarmSize = 1;
            }
        }
    }

    private void setupAnimationStates() {
        this.flyAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        OpposingForce.PROXY.clearSoundCacheFor(this);
        super.remove(reason);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
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
    public boolean skipAttackInteraction(Entity entity) {
        if (entity instanceof Player player) {
            if (player.getMainHandItem().isCorrectToolForDrops(Blocks.AMETHYST_BLOCK.defaultBlockState()) && EnchantmentHelper.getTagEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem()) > 0) {
                if (!level().isClientSide && isAlive()) {
                    ItemStack itemStack = OPItems.CAPTURED_WHIZZ.get().getDefaultInstance();
                    if (!this.isTame()) {
                        this.tame(player);
                    }

                    level().playSound(null, getX(), getY(), getZ(), SoundEvents.AMETHYST_CLUSTER_BREAK, getSoundSource(), 1.0F, 0.8F);
                    if (level() instanceof ServerLevel) {
                        ((ServerLevel) level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.AMETHYST_CLUSTER.defaultBlockState()), getX(), getY() + getBbHeight() / 2.0D, getZ(), 16, getBbWidth() / 4.0F, getBbHeight() / 4.0F, getBbWidth() / 4.0F, 0.05D);
                    }

                    this.saveToBucketTag(itemStack);
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
        this.entityData.define(CHARGING, false);
        this.entityData.define(CHARGE_COOLDOWN, 8 + random.nextInt(8 * 4));
        this.entityData.define(OWNER_UUID, Optional.empty());
        this.entityData.define(DATA_FLAGS_ID, (byte) 0);
        this.entityData.define(FROM_BUCKET, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Charging", this.isCharging());
        compoundTag.putInt("ChargeCooldown", this.getChargeCooldown());
        if (this.getOwnerUUID() != null) {
            compoundTag.putUUID("Owner", this.getOwnerUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setCharging(compoundTag.getBoolean("Charging"));
        this.setChargeCooldown(compoundTag.getInt("ChargeCooldown"));

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
    public void saveToBucketTag(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        Bucketable.saveDefaultDataToBucketTag(this, itemStack);

        compoundTag.putFloat("Health", this.getHealth());
        if (this.getOwnerUUID() != null) {
            compoundTag.putUUID("Owner", this.getOwnerUUID());
        }

        if (this.hasCustomName()) {
            itemStack.setHoverName(this.getCustomName());
        }
    }

    @Override
    public void loadFromBucketTag(CompoundTag compoundTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, compoundTag);
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
    public ItemStack getBucketItemStack() {
        return new ItemStack(OPItems.CAPTURED_WHIZZ.get());
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.AMETHYST_BLOCK_RESONATE;
    }

    public boolean isCharging() {
        return this.entityData.get(CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(CHARGING, charging);
    }

    public int getChargeCooldown() {
        return this.entityData.get(CHARGE_COOLDOWN);
    }

    public void setChargeCooldown(int cooldown) {
        this.entityData.set(CHARGE_COOLDOWN, cooldown);
    }

    public void chargeCooldown() {
        this.entityData.set(CHARGE_COOLDOWN, 8 + random.nextInt(8 * 4));
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID pUuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(pUuid));
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.isTame();
    }

    @Override
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
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        return !this.isOwnedBy(entity) && super.canAttack(entity);
    }

    public boolean isOwnedBy(LivingEntity entity) {
        return entity == this.getOwner();
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

    @Override
    public boolean canBeLeashed(Player player) {
        return !this.isLeashed() && this.isTame();
    }

    public int getMaxSwarmSize() {
        return 32;
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
        ++this.swarmSize;
    }

    private void removeFollower() {
        --this.swarmSize;
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.swarmSize < this.getMaxSwarmSize();
    }

    public boolean hasFollowers() {
        return this.swarmSize > 1;
    }

    public boolean inRangeOfLeader() {
        return this.distanceToSqr(this.leader) <= 121.0D;
    }

    public void pathToLeader() {
        if (this.isFollower()) {
            this.getNavigation().moveTo(this.leader, 1.0D);
        }
    }

    public void addFollowers(Stream<? extends Whizz> entity) {
        entity.limit(this.getMaxSwarmSize() - this.swarmSize).filter((entity1) -> entity1 != this).forEach((entity2) -> {
            entity2.startFollowing(this);
        });
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag tag) {
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
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return OPSoundEvents.WHIZZ_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return OPSoundEvents.WHIZZ_DEATH.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    // goals
    private class WhizzWanderGoal extends Goal {

        private final Whizz whizz;

        @Nullable
        private Direction selectedDirection;
        private boolean doMerge;

        public WhizzWanderGoal(Whizz whizz) {
            this.whizz = whizz;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            if (this.whizz.getTarget() != null) {
                return false;
            } else if (!this.whizz.getNavigation().isDone()) {
                return false;
            } else {
                RandomSource randomsource = this.whizz.getRandom();
                if (ForgeEventFactory.getMobGriefingEvent(this.whizz.level(), this.whizz) && randomsource.nextInt(reducedTickDelay(10)) == 0) {
                    this.selectedDirection = Direction.getRandom(randomsource);
                    BlockPos blockpos = BlockPos.containing(this.whizz.getX(), this.whizz.getY() + (double) 0.5F, this.whizz.getZ()).relative(this.selectedDirection);
                    BlockState blockstate = this.whizz.level().getBlockState(blockpos);
                    if (InfestedAmethyst.isCompatibleHostBlock(blockstate)) {
                        this.doMerge = true;
                        return true;
                    }
                }
                this.doMerge = false;
                return this.whizz.getNavigation().isDone() && this.whizz.getRandom().nextInt(10) == 0;
            }
        }

        public boolean canContinueToUse() {
            return !this.doMerge && this.whizz.getNavigation().isInProgress();
        }

        public void start() {
            if (!this.doMerge) {
                Vec3 vec3 = this.findPos();
                if (vec3 != null) {
                    this.whizz.getNavigation().moveTo(this.whizz.getNavigation().createPath(BlockPos.containing(vec3), 1), 1.0);
                }
            } else {
                LevelAccessor levelaccessor = this.whizz.level();
                BlockPos blockpos = BlockPos.containing(this.whizz.getX(), this.whizz.getY() + (double) 0.5F, this.whizz.getZ()).relative(this.selectedDirection);
                BlockState blockstate = levelaccessor.getBlockState(blockpos);
                if (InfestedAmethyst.isCompatibleHostBlock(blockstate)) {
                    levelaccessor.setBlock(blockpos, InfestedAmethyst.infestedStateByHost(blockstate), 3);
                    this.whizz.spawnAnim();
                    this.whizz.discard();
                }
            }
        }

        public Vec3 findPos() {
            Vec3 vec32;
            vec32 = Whizz.this.getViewVector(0.0F);

            Vec3 vec33 = HoverRandomPos.getPos(Whizz.this, 8, 7, vec32.x, vec32.z, 1.5707964F, 3, 1);
            return vec33 != null ? vec33 : AirAndWaterRandomPos.getPos(Whizz.this, 8, 4, -2, vec32.x, vec32.z, 1.5707963705062866);
        }
    }

    private static class WhizzSwarmGoal extends Goal {

        private final Whizz whizz;
        private int timeToRecalcPath;
        private int nextStartTick;

        public WhizzSwarmGoal(Whizz whizz) {
            this.whizz = whizz;
            this.nextStartTick = this.nextStartTick(whizz);
        }

        protected int nextStartTick(Whizz whizz) {
            return reducedTickDelay(200 + whizz.getRandom().nextInt(200) % 20);
        }

        public boolean canUse() {
            if (this.whizz.hasFollowers()) {
                return false;
            } else if (this.whizz.isFollower()) {
                return true;
            } else if (this.nextStartTick > 0) {
                --this.nextStartTick;
                return false;
            } else {
                this.nextStartTick = this.nextStartTick(this.whizz);
                Predicate<Whizz> predicate = (whizz) -> whizz.canBeFollowed() || !whizz.isFollower();
                List<? extends Whizz> list = this.whizz.level().getEntitiesOfClass(this.whizz.getClass(), this.whizz.getBoundingBox().inflate(10.0D, 10.0D, 10.0D), predicate);
                Whizz whizz1 = DataFixUtils.orElse(list.stream().filter(Whizz::canBeFollowed).findAny(), this.whizz);
                whizz1.addFollowers(list.stream().filter((whizz) -> !whizz.isFollower()));
                return this.whizz.isFollower();
            }
        }

        public boolean canContinueToUse() {
            return this.whizz.isFollower() && this.whizz.inRangeOfLeader();
        }

        public void start() {
            this.timeToRecalcPath = 0;
        }

        public void stop() {
            this.whizz.stopFollowing();
        }

        public void tick() {
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                this.whizz.pathToLeader();
            }
        }
    }

    private static class WhizzChargeAttackGoal extends Goal {

        protected final Whizz whizz;
        private int attackTime = 0;
        float circlingTime = 0;
        float circleDistance = 5;
        float maxCirclingTime = 80;
        boolean clockwise = false;

        public WhizzChargeAttackGoal(Whizz mob) {
            this.whizz = mob;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return !this.whizz.isVehicle() && this.whizz.getTarget() != null && this.whizz.getTarget().isAlive();
        }

        @Override
        public void start() {
            this.whizz.setCharging(false);
            this.attackTime = 0;
            resetCircle();
        }

        @Override
        public void stop() {
            this.whizz.setCharging(false);
            resetCircle();
        }

        public void resetCircle() {
            this.circlingTime = 0;
            this.maxCirclingTime = 160 + this.whizz.random.nextInt(80);
            this.circleDistance = 5 + this.whizz.random.nextFloat() * 5;
            this.clockwise = this.whizz.random.nextBoolean();
        }

        @Override
        public void tick() {
            LivingEntity target = this.whizz.getTarget();
            if (target != null) {
                double distance = this.whizz.distanceToSqr(target.getX(), target.getY(), target.getZ());
                if (this.whizz.isCharging()) {
                    tickChargeAttack();
                } else {
                    if (distance <= 16 && this.whizz.getChargeCooldown() <= 0) {
                        this.whizz.setCharging(true);
                    } else {
                        if (distance > 16 && this.circlingTime < this.maxCirclingTime) {
                            circlingTime++;
                            BlockPos circlePos = getCirclePos(target);
                            if (circlePos != null){
                                this.whizz.getNavigation().moveTo(circlePos.getX() + 0.5D, circlePos.getY() + 0.5D, circlePos.getZ() + 0.5D, 1.0D);
                            }
                        } else {
                            this.whizz.getNavigation().moveTo(target, 0.75D);
                            this.whizz.lookAt(Objects.requireNonNull(target), 30F, 30F);
                            this.whizz.getLookControl().setLookAt(target, 30F, 30F);
                            resetCircle();
                        }
                    }
                }
            }
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        protected void tickChargeAttack() {
            this.attackTime++;
            this.whizz.getNavigation().stop();
            LivingEntity target = this.whizz.getTarget();

            if (this.attackTime == 5) {
                this.whizz.lookAt(Objects.requireNonNull(target), 360F, 360F);
                this.whizz.getLookControl().setLookAt(target, 360F, 360F);
                this.whizz.addDeltaMovement(this.whizz.getLookAngle().scale(2.0D).multiply(0.5D, 0.2D, 0.5D));
            }

            if (this.attackTime >= 5 && this.attackTime < 40) {
                if (this.whizz.distanceTo(Objects.requireNonNull(target)) < getAttackReachSqr(target)) {
                    this.whizz.doHurtTarget(target);
                    this.whizz.swing(InteractionHand.MAIN_HAND);
                }
            }

            if (this.attackTime >= 42) {
                this.attackTime = 0;
                this.whizz.setCharging(false);
                this.whizz.chargeCooldown();
                resetCircle();
            }
        }

        protected double getAttackReachSqr(LivingEntity target) {
            return this.whizz.getBbWidth() * 2.0F * this.whizz.getBbWidth() * 2.0F + target.getBbWidth();
        }

        public BlockPos getCirclePos(LivingEntity target) {
            float angle = (0.0174532925F * (clockwise ? -circlingTime : circlingTime));
            double extraX = circleDistance * Mth.sin((angle));
            double extraZ = circleDistance * Mth.cos(angle);
            BlockPos ground = fromCoords(target.getX() + 0.5F + extraX, this.whizz.getY(), target.getZ() + 0.5F + extraZ);
            return ground;
        }

        public static BlockPos fromCoords(double x, double y, double z){
            return new BlockPos((int) x, (int) y, (int) z);
        }
    }

    private static class WhizzWakeUpFriendsGoal extends Goal {

        private final Whizz whizz;
        private int lookForFriends;

        public WhizzWakeUpFriendsGoal(Whizz whizz) {
            this.whizz = whizz;
        }

        public void notifyHurt() {
            if (this.lookForFriends == 0) {
                this.lookForFriends = this.adjustedTickDelay(10);
            }
        }

        public boolean canUse() {
            return this.lookForFriends > 0 && !this.whizz.isTame();
        }

        public void tick() {
            --this.lookForFriends;
            if (this.lookForFriends <= 0) {
                Level level = this.whizz.level();
                RandomSource randomsource = this.whizz.getRandom();
                BlockPos blockpos = this.whizz.blockPosition();
                for (int i = 0; i <= 5 && i >= -5; i = (i <= 0 ? 1 : 0) - i) {
                    for (int j = 0; j <= 10 && j >= -10; j = (j <= 0 ? 1 : 0) - j) {
                        for (int k = 0; k <= 10 && k >= -10; k = (k <= 0 ? 1 : 0) - k) {
                            BlockPos blockpos1 = blockpos.offset(j, i, k);
                            BlockState blockstate = level.getBlockState(blockpos1);
                            Block block = blockstate.getBlock();
                            if (block instanceof InfestedAmethyst) {
                                if (ForgeEventFactory.getMobGriefingEvent(level, this.whizz)) {
                                    level.destroyBlock(blockpos1, true, this.whizz);
                                } else {
                                    level.setBlock(blockpos1, ((InfestedAmethyst) block).hostStateByInfested(level.getBlockState(blockpos1)), 3);
                                }
                                if (randomsource.nextBoolean()) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static class WhizzOwnerHurtTargetGoal extends TargetGoal {

        private final Whizz whizz;
        private LivingEntity ownerLastHurt;
        private int timestamp;

        public WhizzOwnerHurtTargetGoal(Whizz whizz) {
            super(whizz, false);
            this.whizz = whizz;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        public boolean canUse() {
            if (this.whizz.isTame()) {
                LivingEntity owner = this.whizz.getOwner();
                if (owner == null) {
                    return false;
                } else {
                    this.ownerLastHurt = owner.getLastHurtMob();
                    int hurtTime = owner.getLastHurtMobTimestamp();
                    return hurtTime != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT) && this.whizz.wantsToAttack(this.ownerLastHurt, owner);
                }
            } else {
                return false;
            }
        }

        public void start() {
            this.mob.setTarget(this.ownerLastHurt);
            LivingEntity owner = this.whizz.getOwner();
            if (owner != null) {
                this.timestamp = owner.getLastHurtMobTimestamp();
            }
            super.start();
        }
    }

    private static class WhizzOwnerHurtByTargetGoal extends TargetGoal {

        private final Whizz whizz;
        private LivingEntity ownerLastHurtBy;
        private int timestamp;

        public WhizzOwnerHurtByTargetGoal(Whizz whizz) {
            super(whizz, false);
            this.whizz = whizz;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        public boolean canUse() {
            if (this.whizz.isTame()) {
                LivingEntity owner = this.whizz.getOwner();
                if (owner == null) {
                    return false;
                } else {
                    this.ownerLastHurtBy = owner.getLastHurtByMob();
                    int hurtTime = owner.getLastHurtByMobTimestamp();
                    return hurtTime != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT) && this.whizz.wantsToAttack(this.ownerLastHurtBy, owner);
                }
            } else {
                return false;
            }
        }

        public void start() {
            this.mob.setTarget(this.ownerLastHurtBy);
            LivingEntity owner = this.whizz.getOwner();
            if (owner != null) {
                this.timestamp = owner.getLastHurtByMobTimestamp();
            }
            super.start();
        }
    }
}
