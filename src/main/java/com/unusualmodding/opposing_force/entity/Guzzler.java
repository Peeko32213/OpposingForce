package com.unusualmodding.opposing_force.entity;

import com.google.common.collect.Sets;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.Set;

public class Guzzler extends Monster {

    private static final EntityDataAccessor<Boolean> SPEWING = SynchedEntityData.defineId(Guzzler.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SPEW_COOLDOWN = SynchedEntityData.defineId(Guzzler.class, EntityDataSerializers.INT);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState spewAnimationState = new AnimationState();

    public Guzzler(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setMaxUpStep(1);
        this.xpReward = 10;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.13D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new GuzzlerSpitGoal(this, 0.5F, 22));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
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

        Vec3[] avector3d = new Vec3[] {
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

    public void tick() {
        super.tick();

        if (this.getSpewCooldown() > 0) {
            this.setSpewCooldown(this.getSpewCooldown() - 1);
        }

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
        this.spewAnimationState.animateWhen(this.isAlive() && this.isSpewing(), this.tickCount);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SPEWING, false);
        this.entityData.define(SPEW_COOLDOWN, 18 + random.nextInt(6 * 5));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Spewing", this.isSpewing());
        compoundTag.putInt("SpewCooldown", this.getSpewCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setSpewing(compoundTag.getBoolean("Spewing"));
        this.setSpewCooldown(compoundTag.getInt("SpewCooldown"));
    }

    public boolean isSpewing() {
        return this.entityData.get(SPEWING);
    }

    public void setSpewing(boolean spewing) {
        this.entityData.set(SPEWING, spewing);
    }

    public int getSpewCooldown() {
        return this.entityData.get(SPEW_COOLDOWN);
    }

    public void setSpewCooldown(int cooldown) {
        this.entityData.set(SPEW_COOLDOWN, cooldown);
    }

    public void spewCooldown() {
        this.entityData.set(SPEW_COOLDOWN, 18);
    }

    public boolean shouldSpew() {
        return true;
    }

    private void guzzleEffect() {
        float random = this.random.nextFloat() * ((float) Math.PI * 2F);
        float random1 = this.random.nextFloat() * 1.4F + 1.4F;
        float x = Mth.sin(random) * 0.75F * random1;
        float y = Mth.sin(random) * 0.1F * random1;
        float z = Mth.cos(random) * 0.75F * random1;
        this.level().addParticle(ParticleTypes.FLAME, this.getX() + (double) x, this.getEyeY() - (double) y, this.getZ() + (double) z, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 39) {
            this.guzzleEffect();
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

    private class GuzzlerSpitGoal extends Goal {

        private final Guzzler guzzler;
        private final double speed;
        private final float maxAttackDistance;
        private int seeTime;
        private boolean strafingClockwise;
        private boolean strafingBackwards;
        private int strafingTime = -1;
        private int attackTime = 0;

        public GuzzlerSpitGoal(Guzzler guzzler, double speed, float maxAttackDistanceIn) {
            this.guzzler = guzzler;
            this.speed = speed;
            this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return this.guzzler.getTarget() != null && this.isShooting();
        }

        protected boolean isShooting() {
            return this.guzzler.shouldSpew();
        }

        public boolean canContinueToUse() {
            return (this.canUse() || !this.guzzler.getNavigation().isDone()) && this.isShooting();
        }

        public void start() {
            super.start();
            this.guzzler.setAggressive(true);
            this.guzzler.setSpewing(false);
            this.attackTime = 0;
        }

        public void stop() {
            super.stop();
            this.guzzler.setAggressive(false);
            this.guzzler.setSpewing(false);
            this.attackTime = 0;
            this.seeTime = 0;
        }

        public void tick() {
            LivingEntity target = this.guzzler.getTarget();
            if (target != null) {
                double distance = this.guzzler.distanceToSqr(target.getX(), target.getY(), target.getZ());

                boolean flag = this.guzzler.hasLineOfSight(target);
                boolean flag1 = this.seeTime > 0;

                if (flag != flag1) {
                    this.seeTime = 0;
                }

                if (flag) {
                    ++this.seeTime;
                } else {
                    --this.seeTime;
                }

                if (!(distance > (double) this.maxAttackDistance) && this.seeTime >= 20) {
                    this.guzzler.getNavigation().stop();
                    ++this.strafingTime;
                } else {
                    this.guzzler.getNavigation().moveTo(target, this.speed);
                    this.strafingTime = -1;
                }

                if (this.strafingTime >= 20) {
                    if ((double) this.guzzler.getRandom().nextFloat() < 0.3D) {
                        this.strafingClockwise = !this.strafingClockwise;
                    }
                    if ((double) this.guzzler.getRandom().nextFloat() < 0.3D) {
                        this.strafingBackwards = !this.strafingBackwards;
                    }
                    this.strafingTime = 0;
                }

                if (this.strafingTime > -1) {
                    if (distance > (double) (this.maxAttackDistance * 0.75F)) {
                        this.strafingBackwards = false;
                    } else if (distance < (double) (this.maxAttackDistance * 0.25F)) {
                        this.strafingBackwards = true;
                    }

                    this.guzzler.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                    this.guzzler.lookAt(target, 30.0F, 30.0F);
                } else {
                    this.guzzler.getLookControl().setLookAt(target, 30.0F, 30.0F);
                }

                if (this.guzzler.isSpewing()) {
                    tickSpitAttack();
                } else {
                    if (this.guzzler.getSpewCooldown() <= 0) {
                        this.guzzler.setSpewing(true);
                    }
                }
            }
        }

        public boolean requiresUpdateEveryTick() {
            return this.guzzler.isSpewing();
        }

        protected void tickSpitAttack() {
            this.attackTime++;

            if (this.attackTime == 7) {
                FireSlime slime = OPEntities.FIRE_SLIME.get().create(level());
                slime.setParentId(this.guzzler.getUUID());
                slime.setPos(this.guzzler.getX(), this.guzzler.getEyeY(), this.guzzler.getZ());
                final double d0 = this.guzzler.getTarget().getEyeY() - (double) 1.1F;
                final double d1 = this.guzzler.getTarget().getX() - this.guzzler.getX();
                final double d2 = d0 - slime.getY();
                final double d3 = this.guzzler.getTarget().getZ() - this.guzzler.getZ();
                final float f3 = Mth.sqrt((float) (d1 * d1 + d2 * d2 + d3 * d3)) * 0.23F;
                this.guzzler.gameEvent(GameEvent.PROJECTILE_SHOOT);
                this.guzzler.playSound(OPSoundEvents.GUZZLER_SPEW.get(), 2.0F, 1.0F / (this.guzzler.getRandom().nextFloat() * 0.4F + 0.8F));
                slime.shoot(d1, d2 + (double) f3, d3, 1.5F, 0.0F);
                slime.setYRot(this.guzzler.getYRot() % 360.0F);
                slime.setXRot(Mth.clamp(this.guzzler.getYRot(), -90.0F, 90.0F) % 360.0F);
                if (!this.guzzler.level().isClientSide) {
                    this.guzzler.level().addFreshEntity(slime);
                }
            }

            if (this.attackTime > 3 && this.attackTime < 9) {
                this.guzzler.level().broadcastEntityEvent(this.guzzler, (byte) 39);
            }

            if (attackTime > 15) {
                this.attackTime = 0;
                this.guzzler.spewCooldown();
                this.guzzler.setSpewing(false);
            }
        }
    }
}
