package com.unusualmodding.opposing_force.entity;

import com.google.common.collect.Sets;
import com.unusualmodding.opposing_force.registry.OPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
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
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Set;

public class Guzzler extends Monster {

    private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING = SynchedEntityData.defineId(Guzzler.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> IS_SHOOTING = SynchedEntityData.defineId(Guzzler.class, EntityDataSerializers.INT);

    public Guzzler(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.11D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.ARMOR, 12.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new GuzzlerShootingGoal(this, 0.5F, 30, 16));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public void travel(Vec3 travelVector) {
        if (this.isCharging()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVector = Vec3.ZERO;
            super.travel(travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
        Vec3[] avector3d = new Vec3[]{getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot()), getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() - 22.5F), getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() + 22.5F), getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() - 45.0F), getCollisionHorizontalEscapeVector(this.getBbWidth(), livingEntity.getBbWidth(), livingEntity.getYRot() + 45.0F)};
        Set<BlockPos> set = Sets.newLinkedHashSet();
        double d0 = this.getBoundingBox().maxY;
        double d1 = this.getBoundingBox().minY - 0.5D;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();

        for (Vec3 vector3d : avector3d) {
            blockpos$mutable.set(this.getX() + vector3d.x, d0, this.getZ() + vector3d.z);

            for (double d2 = d0; d2 > d1; --d2) {
                set.add(blockpos$mutable.immutable());
                blockpos$mutable.move(Direction.DOWN);
            }
        }
        for (BlockPos blockpos : set) {
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
        return new Vec3(this.getX(), this.getBoundingBox().maxY, this.getZ());
    }

    public boolean isOnFire() {
        return false;
    }

    public void tick() {
        super.tick();
        this.checkInsideBlocks();
        if (getShootCooldown() > 0) {
            setShootCooldown(getShootCooldown() - 1);
        }
        if (this.getTarget() != null && getShootCooldown() == 0) {
            FireSlime pole = OPEntities.FIRE_SLIME.get().create(level());
            pole.setParentId(this.getUUID());
            pole.setPos(this.getX(), this.getEyeY(), this.getZ());
            final double d0 = this.getTarget().getEyeY() - (double) .1F;
            final double d1 = this.getTarget().getX() - this.getX();
            final double d2 = d0 - pole.getY();
            final double d3 = this.getTarget().getZ() - this.getZ();
            final float f3 = Mth.sqrt((float) (d1 * d1 + d2 * d2 + d3 * d3)) * 0.2F;
            this.gameEvent(GameEvent.PROJECTILE_SHOOT);
            this.playSound(SoundEvents.CROSSBOW_LOADING_END, 2F, 1F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            pole.shoot(d1, d2 + (double) f3, d3, 2F, 0F);
            pole.setYRot(this.getYRot() % 360.0F);
            pole.setXRot(Mth.clamp(this.getYRot(), -90.0F, 90.0F) % 360.0F);
            if (!this.level().isClientSide) {
                this.level().addFreshEntity(pole);
            }
            setShootCooldown(30 + this.getRandom().nextInt(10));

        }
    }

    public boolean shouldShoot() {
        return true;
    }

    public boolean isCharging() {
        return this.entityData.get(DATA_IS_CHARGING);
    }

    public void setCharging(boolean pCharging) {
        this.entityData.set(DATA_IS_CHARGING, pCharging);
    }

    public boolean isShooting() {
        return this.entityData.get(IS_SHOOTING) <= 0;
    }

    public int getShootCooldown() {
        return this.entityData.get(IS_SHOOTING);
    }

    public void setShootCooldown(int shooting) {
        this.entityData.set(IS_SHOOTING, shooting);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_CHARGING, false);
        this.entityData.define(IS_SHOOTING, 0);
    }

    public static boolean canSpawn(EntityType<Guzzler> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
        boolean isDeepDark = iServerWorld.getBiome(pos).is(Biomes.DEEP_DARK);
        return reason == MobSpawnType.SPAWNER || !iServerWorld.canSeeSky(pos) && pos.getY() <= 0 && checkUndergroundMonsterSpawnRules(entityType, iServerWorld, reason, pos, random) && !isDeepDark;
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

    public static class GuzzlerShootingGoal extends Goal {
        private final Guzzler entity;
        private final double moveSpeedAmp;
        private int attackCooldown;
        private final float maxAttackDistance;
        private int attackTime = -1;
        private int seeTime;
        private boolean strafingClockwise;
        private boolean strafingBackwards;
        private int strafingTime = -1;
        private int animationCooldown = 0;
        public int chargeTime;

        public GuzzlerShootingGoal(Guzzler mob, double moveSpeedAmpIn, int attackCooldownIn, float maxAttackDistanceIn) {
            this.entity = mob;
            this.moveSpeedAmp = moveSpeedAmpIn;
            this.attackCooldown = attackCooldownIn;
            this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public void setAttackCooldown(int attackCooldownIn) {
            this.attackCooldown = attackCooldownIn;
        }

        public boolean canUse() {
            return this.entity.getTarget() == null ? false : this.isBowInMainhand();
        }

        protected boolean isBowInMainhand() {
            return this.entity.shouldShoot();
        }

        public boolean canContinueToUse() {
            return (this.canUse() || !this.entity.getNavigation().isDone()) && this.isBowInMainhand();
        }

        public void start() {
            super.start();
            this.entity.setAggressive(true);
            this.chargeTime = 0;
        }


        public void stop() {
            super.stop();
            this.entity.setAggressive(false);
            this.entity.setCharging(false);
            this.seeTime = 0;
            this.attackTime = -1;
            this.entity.stopUsingItem();
            this.entity.setCharging(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = this.entity.getTarget();
            if (animationCooldown > 0) {
                animationCooldown--;
            }
            if (livingentity != null) {
                double d0 = this.entity.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                boolean flag = this.entity.hasLineOfSight(livingentity);
                boolean flag1 = this.seeTime > 0;
                if (flag != flag1) {
                    this.seeTime = 0;
                }

                if (flag) {
                    ++this.seeTime;
                    ++this.chargeTime;
                } else {
                    --this.seeTime;
                    --this.chargeTime;
                }

                if (!(d0 > (double) this.maxAttackDistance) && this.seeTime >= 20) {
                    this.entity.getNavigation().stop();
                    ++this.strafingTime;
                } else {
                    this.entity.getNavigation().moveTo(livingentity, this.moveSpeedAmp);
                    this.strafingTime = -1;
                }

                if (this.strafingTime >= 20) {
                    if ((double) this.entity.getRandom().nextFloat() < 0.3D) {
                        this.strafingClockwise = !this.strafingClockwise;
                    }

                    if ((double) this.entity.getRandom().nextFloat() < 0.3D) {
                        this.strafingBackwards = !this.strafingBackwards;
                    }

                    this.strafingTime = 0;
                }

                if (this.strafingTime > -1) {
                    if (d0 > (double) (this.maxAttackDistance * 0.75F)) {
                        this.strafingBackwards = false;
                    } else if (d0 < (double) (this.maxAttackDistance * 0.25F)) {
                        this.strafingBackwards = true;
                    }

                    this.entity.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                    this.entity.lookAt(livingentity, 30.0F, 30.0F);
                } else {
                    this.entity.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
                }
                if (!flag && this.seeTime < -60) {
                    this.entity.stopUsingItem();
                } else if (flag && this.chargeTime == 10) {
                    this.attackTime = this.attackCooldown;
                    this.chargeTime = -20;
                } else if (this.chargeTime > 0) {
                    --this.chargeTime;
                }
                this.entity.setCharging(this.chargeTime > 5);
            }
        }
    }
}
