package com.unusualmodding.opposing_force.entity;

import com.google.common.collect.Sets;
import com.unusualmodding.opposing_force.entity.ai.goal.GuzzlerAttackGoal;
import com.unusualmodding.opposing_force.entity.ai.navigation.SmoothGroundPathNavigation;
import com.unusualmodding.opposing_force.entity.base.IAnimatedAttacker;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import com.unusualmodding.opposing_force.utils.OPMath;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
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
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class Guzzler extends Monster implements IAnimatedAttacker {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(Guzzler.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState spewAnimationState = new AnimationState();
    public final AnimationState stompAnimationState = new AnimationState();

    public Guzzler(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setMaxUpStep(1);
        this.xpReward = 10;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.13D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new GuzzlerAttackGoal(this, 22));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        return new SmoothGroundPathNavigation(this, level);
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {

        Vec3[] avector3d = new Vec3[]{
                getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot()),
                getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() - 22.5F),
                getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() + 22.5F),
                getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() - 45.0F),
                getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() + 45.0F)
        };

        Set<BlockPos> set = Sets.newLinkedHashSet();
        double d0 = this.getBoundingBox().maxY;
        double d1 = this.getBoundingBox().minY - 0.5D;
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();

        for (Vec3 vector3d : avector3d) {
            blockPos.set(this.getX() + vector3d.x, d0, this.getZ() + vector3d.z);
            for (double d2 = d0; d2 > d1; --d2) {
                set.add(blockPos.immutable());
                blockPos.move(Direction.DOWN);
            }
        }

        for (BlockPos blockpos : set) {
            if (!this.level().getFluidState(blockpos).is(FluidTags.LAVA)) {
                double d3 = this.level().getBlockFloorHeight(blockpos);
                if (DismountHelper.isBlockFloorValid(d3)) {
                    Vec3 vector3d1 = Vec3.upFromBottomCenterOf(blockpos, d3);

                    for (Pose pose : livingEntity.getDismountPoses()) {
                        AABB axisalignedbb = livingEntity.getLocalBoundsForPose(pose);
                        if (DismountHelper.canDismountTo(this.level(), livingEntity, axisalignedbb.move(vector3d1))) {
                            livingEntity.setPose(pose);
                            return vector3d1;
                        }
                    }
                }
            }
        }
        return new Vec3(this.getX(), this.getBoundingBox().maxY, this.getZ());
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
        this.spewAnimationState.animateWhen(this.getAttackState() == 1, this.tickCount);
        this.stompAnimationState.animateWhen(this.getAttackState() == 2, this.tickCount);
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float f1 = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float f2 = Math.min(f1 * 10.0F, 1.0F);
        this.walkAnimation.update(f2, 0.4F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("AttackState", this.getAttackState());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setAttackState(compoundTag.getInt("AttackState"));
    }

    @Override
    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    @Override
    public void setAttackState(int attackState) {
        this.entityData.set(ATTACK_STATE, attackState);
    }

    private void guzzleEffect() {
        float random = this.random.nextFloat() * ((float) Math.PI * 2F);
        float random1 = this.random.nextFloat() * 1.4F + 1.4F;
        float x = Mth.sin(random) * 0.75F * random1;
        float y = Mth.sin(random) * 0.1F * random1;
        float z = Mth.cos(random) * 0.75F * random1;
        this.level().addParticle(ParticleTypes.FLAME, this.getX() + (double) x, this.getEyeY() - (double) y, this.getZ() + (double) z, 0.0D, 0.0D, 0.0D);
    }

    private void stompEffect() {
        Vec3 groundedVec = OPMath.getGroundBelowPosition(level(), new Vec3(this.getRandomX(1.5), this.getY() + 0.25F, this.getRandomZ(1.5)));
        BlockPos ground = BlockPos.containing(groundedVec.subtract(0, 0.5F, 0));
        BlockState state = this.level().getBlockState(ground);
        for (int i = 0; i <= (this.getRandom().nextInt(50) + 80); i++) {
            this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), true, this.getRandomX(1.5), this.getY() + 0.25F, this.getRandomZ(1.5), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 39) {
            this.guzzleEffect();
        }
        if (id == 40) {
            this.stompEffect();
        }
        super.handleEntityEvent(id);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.GUZZLER_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return OPSoundEvents.GUZZLER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return OPSoundEvents.GUZZLER_DEATH.get();
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(SoundEvents.POLAR_BEAR_STEP, 0.15F, 0.8F);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 140;
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return checkGuzzlerSpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean checkGuzzlerSpawnRules(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() <= -16 && (random.nextInt(10) == 0 || pos.getY() <= 0) && level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawnNoSkylight(level, pos, random) && checkMobSpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean isDarkEnoughToSpawnNoSkylight(ServerLevelAccessor level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.SKY, pos) > 0) {
            return false;
        } else {
            DimensionType dimension = level.dimensionType();
            int lightLimit = dimension.monsterSpawnBlockLightLimit();
            if (lightLimit < 15 && level.getBrightness(LightLayer.BLOCK, pos) > lightLimit) {
                return false;
            } else {
                int lightTest = level.getLevel().isThundering() ? level.getMaxLocalRawBrightness(pos, 10) : level.getMaxLocalRawBrightness(pos);
                return lightTest <= dimension.monsterSpawnLightTest().sample(random);
            }
        }
    }
}
