package com.peeko32213.hole.common.entity;

import com.peeko32213.hole.common.entity.projectile.EntitySmallElectricBall;
import com.peeko32213.hole.common.entity.util.AbstractMonster;
import com.peeko32213.hole.common.entity.util.SmartNearestTargetGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;

public class EntityVolt extends AbstractMonster implements GeoAnimatable, GeoEntity {
    private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING = SynchedEntityData.defineId(EntityVolt.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.volt.idle");
    private static final RawAnimation IDLE_JAW = RawAnimation.begin().thenLoop("animation.volt.idle_jaw");
    private static final RawAnimation MOVE = RawAnimation.begin().thenLoop("animation.volt.move");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenLoop("animation.volt.attack");

    public EntityVolt(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.MOVEMENT_SPEED, (double)0.1F)
                .add(Attributes.ATTACK_DAMAGE, 8.0F);
    }

    public static <T extends Mob> boolean canFirstTierSpawn(EntityType<EntityVolt> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {

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

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(1, new SmartNearestTargetGoal(this, Player.class, true));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(3, new EntityVolt.VoltShootElectricBall(this));
        this.goalSelector.addGoal(3, new EntityVolt.VoltLookGoal(this));

    }

    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypeTags.IS_DROWNING) ;
    }

    public boolean isCharging() {
        return this.entityData.get(DATA_IS_CHARGING);
    }

    public void setCharging(boolean pCharging) {
        this.entityData.set(DATA_IS_CHARGING, pCharging);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_CHARGING, false);
    }

    public static <T extends Mob> boolean canSecondTierSpawn(EntityType<EntityVolt> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
        boolean isDeepDark = iServerWorld.getBiome(pos).is(Biomes.DEEP_DARK);
        return reason == MobSpawnType.SPAWNER || !iServerWorld.canSeeSky(pos) && pos.getY() <= 0 && checkUndergroundMonsterSpawnRules(entityType, iServerWorld, reason, pos, random) && !isDeepDark;
    }


    static class VoltLookGoal extends Goal {
        private final EntityVolt ghast;

        public VoltLookGoal(EntityVolt pGhast) {
            this.ghast = pGhast;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return true;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.ghast.getTarget() == null) {
                Vec3 vec3 = this.ghast.getDeltaMovement();
                this.ghast.setYRot(-((float) Mth.atan2(vec3.x, vec3.z)) * (180F / (float)Math.PI));
                this.ghast.yBodyRot = this.ghast.getYRot();
            } else {
                LivingEntity livingentity = this.ghast.getTarget();
                double d0 = 64.0D;
                if (livingentity.distanceToSqr(this.ghast) < 4096.0D) {
                    double d1 = livingentity.getX() - this.ghast.getX();
                    double d2 = livingentity.getZ() - this.ghast.getZ();
                    this.ghast.setYRot(-((float)Mth.atan2(d1, d2)) * (180F / (float)Math.PI));
                    this.ghast.yBodyRot = this.ghast.getYRot();
                }
            }

        }
    }

    static class VoltShootElectricBall extends Goal {
        private final EntityVolt ghast;
        public int chargeTime;

        public VoltShootElectricBall(EntityVolt pGhast) {
            this.ghast = pGhast;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return this.ghast.getTarget() != null;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.chargeTime = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            this.ghast.setCharging(false);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = this.ghast.getTarget();
            if (livingentity != null) {
                double d0 = 64.0D;
                if (livingentity.distanceToSqr(this.ghast) < 4096.0D && this.ghast.hasLineOfSight(livingentity)) {
                    Level level = this.ghast.level();
                    ++this.chargeTime;
                    if (this.chargeTime == 5 && !this.ghast.isSilent()) {
                        level.levelEvent((Player)null, 1015, this.ghast.blockPosition(), 0);
                    }

                    if (this.chargeTime == 10) {
                        double d1 = 4.0D;
                        Vec3 vec3 = this.ghast.getViewVector(1.0F);
                        double d2 = livingentity.getX() - (this.ghast.getX() + vec3.x * 4.0D);
                        double d3 = livingentity.getY(0.5D) - (0.5D + this.ghast.getY(0.5D));
                        double d4 = livingentity.getZ() - (this.ghast.getZ() + vec3.z * 4.0D);
                        if (!this.ghast.isSilent()) {
                            level.levelEvent((Player)null, 1016, this.ghast.blockPosition(), 0);
                        }

                        EntitySmallElectricBall largefireball = new EntitySmallElectricBall(level, this.ghast, d2, d3, d4);
                        largefireball.setPos(this.ghast.getX() + vec3.x * 0.0D, this.ghast.getY(0.5D) + 0.5D, largefireball.getZ() + vec3.z * 0.0D);
                        level.addFreshEntity(largefireball);
                        this.chargeTime = -20;
                    }
                } else if (this.chargeTime > 0) {
                    --this.chargeTime;
                }

                this.ghast.setCharging(this.chargeTime > 5);
            }
        }
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if(this.isCharging()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();

            }
            pTravelVector = Vec3.ZERO;
            super.travel(pTravelVector);
        }
        else{
            super.travel(pTravelVector);
        }
    }

    private boolean isStillEnough() {
        return this.getDeltaMovement().horizontalDistance() < 0.05;
    }


    protected <E extends EntityVolt> PlayState controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        {
            if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {

                event.setAndContinue(MOVE);
                return PlayState.CONTINUE;
            }

            if (this.isCharging()){
                event.setAndContinue(ATTACK);
                return PlayState.CONTINUE;
            }

            if (playingAnimation()) {
                return PlayState.CONTINUE;
            } else if (isStillEnough() && getRandomAnimationNumber() == 0) {
                int rand = getRandomAnimationNumber();
                if (rand < 33) {
                    setAnimationTimer(150);
                    return event.setAndContinue(IDLE);
                }
                if (rand < 66) {
                    setAnimationTimer(300);
                    return event.setAndContinue(IDLE_JAW);
                }
            }
            return PlayState.CONTINUE;
        }
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::controller));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }
}
