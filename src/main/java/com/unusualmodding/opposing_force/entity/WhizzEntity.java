package com.unusualmodding.opposing_force.entity;

import com.mojang.datafixers.DataFixUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class WhizzEntity extends Monster {

    @Nullable
    private WhizzEntity leader;
    protected int swarmSize = 1;

    public final AnimationState flyAnimationState = new AnimationState();

    @Override
    protected @NotNull PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, pLevel) {
            public boolean isStableDestination(BlockPos pPos) {
                return !level().getBlockState(pPos.below()).isAir();
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
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.FLYING_SPEED, 1.1F).add(Attributes.MOVEMENT_SPEED, 0.5F).add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(3, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WhizzWanderGoal());
        this.goalSelector.addGoal(8, new WhizzSwarmGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.5F;
    }

    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
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

    // Swarming
    public int getMaxSwarmSize() {
        return 32;
    }

    public int getMaxSpawnClusterSize() {
        return this.getMaxSwarmSize();
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive();
    }

    public WhizzEntity startFollowing(WhizzEntity entity) {
        this.leader = entity;
        entity.addFollower();
        return entity;
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

    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    public boolean isFlying() {
        return true;
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

    // Goals
    private class WhizzWanderGoal extends Goal {

        public WhizzWanderGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return WhizzEntity.this.getNavigation().isDone() && WhizzEntity.this.getRandom().nextInt(10) == 0;
        }

        public boolean canContinueToUse() {
            return WhizzEntity.this.getNavigation().isInProgress();
        }

        public void start() {
            Vec3 vec3 = this.findPos();
            if (vec3 != null) {
                WhizzEntity.this.getNavigation().moveTo(WhizzEntity.this.getNavigation().createPath(BlockPos.containing(vec3), 1), 1.0);
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
                WhizzEntity schoolingWaterAnimal = DataFixUtils.orElse(list.stream().filter(WhizzEntity::canBeFollowed).findAny(), this.whizz);
                schoolingWaterAnimal.addFollowers(list.stream().filter((whizz) -> !whizz.isFollower()));
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
}
