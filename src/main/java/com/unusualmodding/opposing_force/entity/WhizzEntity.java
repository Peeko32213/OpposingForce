package com.unusualmodding.opposing_force.entity;

import com.mojang.datafixers.DataFixUtils;
import com.unusualmodding.opposing_force.blocks.InfestedAmethyst;
import com.unusualmodding.opposing_force.registry.OPSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
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
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class WhizzEntity extends Monster {

    private static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.defineId(WhizzEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> CHARGE_COOLDOWN = SynchedEntityData.defineId(WhizzEntity.class, EntityDataSerializers.INT);

    @Nullable
    private WhizzWakeUpFriendsGoal friendsGoal;

    @Nullable
    private WhizzEntity leader;
    protected int swarmSize = 1;

    public final AnimationState flyAnimationState = new AnimationState();

    @Override
    protected @NotNull PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, pLevel) {
            public boolean isStableDestination(BlockPos pos) {
                return !level().getBlockState(pos.below(2)).isAir();
            }
        };
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(false);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    public WhizzEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
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
        this.goalSelector.addGoal(1, new WhizzChargeAttackGoal(this));
        this.goalSelector.addGoal(2, new WhizzWanderGoal(this));
        this.goalSelector.addGoal(3, new WhizzSwarmGoal(this));
        this.goalSelector.addGoal(4, this.friendsGoal);
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true, false));
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.6F;
    }

    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }

        if (this.getChargeCooldown() > 0) {
            this.setChargeCooldown(this.getChargeCooldown() - 1);
        }

        if (this.hasFollowers() && this.level().random.nextInt(200) == 1) {
            List<? extends WhizzEntity> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
            if (list.size() <= 1) {
                this.swarmSize = 1;
            }
        }
    }

    private void setupAnimationStates() {
        this.flyAnimationState.animateWhen(this.isAlive(), this.tickCount);
    }

    public boolean doHurtTarget(Entity entity) {
        if (super.doHurtTarget(entity)) {
            this.playSound(OPSounds.WHIZZ_ATTACK.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            return true;
        } else {
            return false;
        }
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isInvulnerableTo(pSource)) {
            return false;
        } else {
            if ((pSource.getEntity() != null || pSource.is(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH)) && this.friendsGoal != null) {
                this.friendsGoal.notifyHurt();
            }
            return super.hurt(pSource, pAmount);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CHARGING, false);
        this.entityData.define(CHARGE_COOLDOWN, 8 + random.nextInt(12 * 6));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Charging", this.isCharging());
        compoundTag.putInt("ChargeCooldown", this.getChargeCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setCharging(compoundTag.getBoolean("Charging"));
        this.setChargeCooldown(compoundTag.getInt("ChargeCooldown"));
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
        this.entityData.set(CHARGE_COOLDOWN, 8 + random.nextInt(12 * 6));
    }

    // swarming
    public int getMaxSwarmSize() {
        return 32;
    }

    public int getMaxSpawnClusterSize() {
        return this.getMaxSwarmSize();
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive();
    }

    public void startFollowing(WhizzEntity entity) {
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

    public void addFollowers(Stream<? extends WhizzEntity> entity) {
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
        public final WhizzEntity leader;
        public SwarmSpawnGroupData(WhizzEntity entity) {
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

    protected SoundEvent getHurtSound(DamageSource source) {
        return OPSounds.WHIZZ_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return OPSounds.WHIZZ_DEATH.get();
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    public static <T extends Mob> boolean canWhizzSpawn(EntityType<WhizzEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
        boolean isDeepDark = iServerWorld.getBiome(pos).is(Biomes.DEEP_DARK);
        boolean spawnBlock = iServerWorld.getBlockState(pos.below()).is(Blocks.AMETHYST_BLOCK);
        return reason == MobSpawnType.SPAWNER || spawnBlock || !iServerWorld.canSeeSky(pos) && pos.getY() <= 0 && checkUndergroundMonsterSpawnRules(entityType, iServerWorld, reason, pos, random) && !isDeepDark;
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
    private class WhizzWanderGoal extends Goal {

        private final WhizzEntity whizz;

        @Nullable
        private Direction selectedDirection;
        private boolean doMerge;

        public WhizzWanderGoal(WhizzEntity whizz) {
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
            vec32 = WhizzEntity.this.getViewVector(0.0F);

            Vec3 vec33 = HoverRandomPos.getPos(WhizzEntity.this, 8, 7, vec32.x, vec32.z, 1.5707964F, 3, 1);
            return vec33 != null ? vec33 : AirAndWaterRandomPos.getPos(WhizzEntity.this, 8, 4, -2, vec32.x, vec32.z, 1.5707963705062866);
        }
    }

    private static class WhizzSwarmGoal extends Goal {

        private final WhizzEntity whizz;
        private int timeToRecalcPath;
        private int nextStartTick;

        public WhizzSwarmGoal(WhizzEntity whizz) {
            this.whizz = whizz;
            this.nextStartTick = this.nextStartTick(whizz);
        }

        protected int nextStartTick(WhizzEntity whizz) {
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
                Predicate<WhizzEntity> predicate = (whizz) -> whizz.canBeFollowed() || !whizz.isFollower();
                List<? extends WhizzEntity> list = this.whizz.level().getEntitiesOfClass(this.whizz.getClass(), this.whizz.getBoundingBox().inflate(10.0D, 10.0D, 10.0D), predicate);
                WhizzEntity whizz1 = DataFixUtils.orElse(list.stream().filter(WhizzEntity::canBeFollowed).findAny(), this.whizz);
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

        protected final WhizzEntity whizz;
        private int attackTime = 0;
        private Vec3 chargeMotion = new Vec3(0,0,0);

        public WhizzChargeAttackGoal(WhizzEntity mob) {
            this.whizz = mob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return !this.whizz.isVehicle() && this.whizz.getTarget() != null && this.whizz.getTarget().isAlive();
        }

        public void start() {
            this.whizz.setCharging(false);
            this.attackTime = 0;
        }

        public void stop() {
            this.whizz.setCharging(false);
        }

        public void tick() {
            LivingEntity target = this.whizz.getTarget();
            if (target != null) {
                double distance = this.whizz.distanceToSqr(target.getX(), target.getY(), target.getZ());

                if (this.whizz.isCharging()) {
                    tickChargeAttack();
                } else {
                    if (distance <= 20 && this.whizz.getChargeCooldown() <= 0) {
                        this.whizz.setCharging(true);
                    }
                    else {
                        if (distance > 20) {
                            this.whizz.getNavigation().moveTo(target, 1.2D);
                        } else {
                            this.whizz.getNavigation().moveTo(target, 0.4D);
                        }
                        this.whizz.lookAt(Objects.requireNonNull(target), 30F, 30F);
                        this.whizz.getLookControl().setLookAt(target, 30F, 30F);
                    }
                }
            }
        }

        protected void tickChargeAttack() {
            this.attackTime++;
            this.whizz.getNavigation().stop();
            Entity target = this.whizz.getTarget();

            if (this.attackTime == 3) {
                Vec3 targetPos = target.position();
                double x = -(this.whizz.position().x - targetPos.x);
                double y = -(this.whizz.position().y - targetPos.y);
                double z = -(this.whizz.position().z - targetPos.z);
                this.chargeMotion = new Vec3(x, y, z).normalize();
                this.whizz.lookAt(Objects.requireNonNull(target), 360F, 30F);
                this.whizz.getLookControl().setLookAt(target, 30F, 30F);
            }

            if (this.attackTime > 3 && this.attackTime < 9) {
                this.whizz.setDeltaMovement(this.chargeMotion.x * 0.7, this.chargeMotion.y * 0.55, this.chargeMotion.z * 0.7);
                if (this.whizz.distanceTo(Objects.requireNonNull(target)) < 1.1F) {
                    this.whizz.doHurtTarget(target);
                    this.whizz.swing(InteractionHand.MAIN_HAND);
                }
            }

            if (this.attackTime >= 9) {
                this.attackTime = 0;
                this.whizz.setCharging(false);
                this.whizz.chargeCooldown();
            }
        }
    }

    private static class WhizzWakeUpFriendsGoal extends Goal {

        private final WhizzEntity whizz;
        private int lookForFriends;

        public WhizzWakeUpFriendsGoal(WhizzEntity whizz) {
            this.whizz = whizz;
        }

        public void notifyHurt() {
            if (this.lookForFriends == 0) {
                this.lookForFriends = this.adjustedTickDelay(10);
            }
        }

        public boolean canUse() {
            return this.lookForFriends > 0;
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
}
