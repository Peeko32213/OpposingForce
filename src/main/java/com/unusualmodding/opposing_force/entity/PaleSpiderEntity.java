package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.ai.util.CeilingMoveController;
import com.unusualmodding.opposing_force.entity.ai.navigation.DirectPathNavigator;
import com.unusualmodding.opposing_force.entity.ai.util.OPMath;
import com.unusualmodding.opposing_force.entity.ai.goal.SmartNearestTargetGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class PaleSpiderEntity extends Spider implements GeoAnimatable, GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation SCURRY = RawAnimation.begin().thenLoop("animation.pale_spider.scurry");
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.pale_spider.idle");
    private static final RawAnimation BITE = RawAnimation.begin().thenLoop("animation.pale_spider.bite");
    private static final RawAnimation SCURRY_UPSIDE_DOWN = RawAnimation.begin().thenLoop("animation.pale_spider.scurry_upside_down");
    private static final RawAnimation IDLE_UPSIDE_DOWN = RawAnimation.begin().thenLoop("animation.pale_spider.idle_upside_down");
    private static final RawAnimation BITE_UPSIDE_DOWN = RawAnimation.begin().thenLoop("animation.pale_spider.bite_upside_down");

    private static final EntityDataAccessor<Boolean> UPSIDE_DOWN = SynchedEntityData.defineId(PaleSpiderEntity.class, EntityDataSerializers.BOOLEAN);

    public float prevUpsideDownProgress;
    public float upsideDownProgress;
    private boolean jumpingUp = false;
    private int upwardsFallingTicks = 0;
    private boolean isUpsideDownNavigator;
    private boolean prevOnGround = false;
    private int attackCooldown = 0;

    public PaleSpiderEntity(EntityType<? extends Spider> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, (double)0.25F)
                .add(Attributes.ATTACK_DAMAGE, (double)2.0F);

    }

    public static BlockPos getLowestPos(LevelAccessor world, BlockPos pos) {
        while (!world.getBlockState(pos).isFaceSturdy(world, pos, Direction.DOWN) && pos.getY() < 320) {
            pos = pos.above();
        }
        return pos;
    }

    public static <T extends Mob> boolean canFirstTierSpawn(EntityType<PaleSpiderEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {

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
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new DroppingMeleeGoal());
        this.goalSelector.addGoal(2, new WanderStrollUpsideDown());
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, LivingEntity.class, 30F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, PaleSpiderEntity.class));
        this.targetSelector.addGoal(1, new SmartNearestTargetGoal(this, Player.class, true) {
            protected AABB getTargetSearchArea(double targetDistance) {
                AABB bb = this.mob.getBoundingBox().inflate(targetDistance, targetDistance, targetDistance);
               return new AABB(bb.minX, 0, bb.minZ, bb.maxX, 32, bb.maxZ);
            }
        });
    }


    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypes.IN_WALL);
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        super.checkFallDamage(y, onGroundIn, state, pos);
    }

    protected void playBlockFallSound() {
        this.onLand();
        super.playBlockFallSound();
    }

    private void switchNavigator(boolean rightsideUp) {
        if (rightsideUp) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isUpsideDownNavigator = false;
        } else {
            this.moveControl = new CeilingMoveController(this, 1.1F, false);
            this.navigation = new DirectPathNavigator(this, level());
            this.isUpsideDownNavigator = true;
        }
    }

    public void tick() {
        super.tick();
        prevUpsideDownProgress = upsideDownProgress;
        if (this.isUpsideDown() && upsideDownProgress < 5F) {
            upsideDownProgress++;
        }
        if (!this.isUpsideDown() && upsideDownProgress > 0F) {
            upsideDownProgress--;
        }
        if (!this.level().isClientSide) {
            BlockPos abovePos = this.getPositionAbove();
            BlockState aboveState = level().getBlockState(abovePos);
            BlockState belowState = level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement());
            BlockPos worldHeight = level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, this.blockPosition());
            boolean validAboveState = aboveState.isFaceSturdy(level(), abovePos, Direction.DOWN);
            boolean validBelowState = belowState.isFaceSturdy(level(), this.getBlockPosBelowThatAffectsMyMovement(), Direction.UP);
            LivingEntity attackTarget = this.getTarget();
            if (jumpingUp && this.getY() > worldHeight.getY()) {
                jumpingUp = false;
            }
            if ((this.onGround() && (attackTarget == null || attackTarget != null && !attackTarget.isAlive()) && random.nextInt(300) == 0 && this.onGround() && !this.isUpsideDown() && this.getY() + 2 < worldHeight.getY()|| jumpingUp)) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, 2F, 0));
                jumpingUp = true;
            }
            if (this.isUpsideDown()) {
                jumpingUp = false;
                this.setNoGravity(!this.onGround());
                final float f = 0.91F;
                this.setDeltaMovement(this.getDeltaMovement().multiply(f, 1F, f));
                if (!this.verticalCollision) {
                    if (this.onGround() || validBelowState || upwardsFallingTicks > 5) {
                        this.setUpsideDown(false);
                        upwardsFallingTicks = 0;
                    } else {
                        if (!validAboveState) {
                            upwardsFallingTicks++;
                        }
                        this.setDeltaMovement(this.getDeltaMovement().add(0, 0.2F, 0));
                    }
                } else {
                    upwardsFallingTicks = 0;
                }
                if (this.horizontalCollision) {
                    upwardsFallingTicks = 0;
                    this.setDeltaMovement(this.getDeltaMovement().add(0, -0.3F, 0));
                }
                if (this.isInWall() && level().isEmptyBlock(this.getBlockPosBelowThatAffectsMyMovement())) {
                    this.setPos(this.getX(), this.getY() - 1, this.getZ());
                }
            } else {
                this.setNoGravity(false);
                if (validAboveState) {
                    this.setUpsideDown(true);
                }
            }

            if (this.isUpsideDown()) {
                if (!this.isUpsideDownNavigator)
                    switchNavigator(false);
            } else {
                if (this.isUpsideDownNavigator)
                    switchNavigator(true);
            }
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(UPSIDE_DOWN, false);
    }

    public boolean isUpsideDown() {
        return this.entityData.get(UPSIDE_DOWN);
    }

    public void setUpsideDown(boolean upsideDown) {
        this.entityData.set(UPSIDE_DOWN, upsideDown);
    }

    protected BlockPos getPositionAbove() {
        return new BlockPos((int) this.position().x, (int) (this.getBoundingBox().maxY + 0.5000001D), (int) this.position().z);
    }

    private void onLand() {
        if (!this.level().isClientSide) {
            level().broadcastEntityEvent(this, (byte) 39);
            for (Entity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.1D))) {
                if (!isAlliedTo(entity) && !(entity instanceof PaleSpiderEntity) && entity != this) {
                    entity.hurt(this.damageSources().mobAttack(this), 2.0F + random.nextFloat() * 5F);
                    launch(entity, true);
                }
            }
        }
    }

    private void launch(Entity e, boolean huge) {
        if (e.onGround()) {
            final double d0 = e.getX() - this.getX();
            final double d1 = e.getZ() - this.getZ();
            final double d2 = Math.max(d0 * d0 + d1 * d1, 0.001D);
            final float f = 0.5F;
            e.push(d0 / d2 * f, huge ? 0.5D : 0.2F, d1 / d2 * f);
        }
    }

    public void handleEntityEvent(byte id) {
        if (id == 39) {
            spawnGroundEffects();
        } else {
            super.handleEntityEvent(id);

        }
    }

    public void spawnGroundEffects() {
        float radius = 2.3F;
        if (this.level().isClientSide) {
            for (int i1 = 0; i1 < 20 + random.nextInt(12); i1++) {
                double motionX = getRandom().nextGaussian() * 0.07D;
                double motionY = getRandom().nextGaussian() * 0.07D;
                double motionZ = getRandom().nextGaussian() * 0.07D;
                float angle = (OPMath.STARTING_ANGLE * this.yBodyRot) + i1;
                double extraX = radius * Mth.sin(Mth.PI + angle);
                double extraY = 0.8F;
                double extraZ = radius * Mth.cos(angle);
                BlockPos ground = getGroundPosition(new BlockPos(Mth.floor(this.getX() + extraX), (int) this.getY(), Mth.floor(this.getZ() + extraZ)));
                BlockState state = this.level().getBlockState(ground);
                if (!state.isAir()) {
                    level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), true, this.getX() + extraX, ground.getY() + extraY, this.getZ() + extraZ, motionX, motionY, motionZ);
                }
            }
        }
    }

    private BlockPos getGroundPosition(BlockPos in) {
        BlockPos position = new BlockPos(in.getX(), (int) this.getY(), in.getZ());
        while (position.getY() > 2 && level().isEmptyBlock(position) && level().getFluidState(position).isEmpty()) {
            position = position.below();
        }
        return position;
    }


    class WanderStrollUpsideDown extends RandomStrollGoal {

        public WanderStrollUpsideDown() {
            super(PaleSpiderEntity.this, 1D, 50);
        }

        @Nullable
        protected Vec3 getPosition() {
            if (PaleSpiderEntity.this.isUpsideDown()) {
                for (int i = 0; i < 15; i++) {
                    Random rand = new Random();
                    BlockPos randPos = PaleSpiderEntity.this.blockPosition().offset(rand.nextInt(16) - 8, -2, rand.nextInt(16) - 8);
                    BlockPos lowestPos = PaleSpiderEntity.getLowestPos(level(), randPos);
                    if (level().getBlockState(lowestPos).isFaceSturdy(level(), lowestPos, Direction.DOWN)) {
                        return Vec3.atCenterOf(lowestPos);
                    }
                }
                return null;
            } else {
                return super.getPosition();
            }
        }

        public boolean canUse() {
            return super.canUse();
        }

        public boolean canContinueToUse() {
            if (PaleSpiderEntity.this.isUpsideDown()) {
                double d0 = PaleSpiderEntity.this.getX() - wantedX;
                double d2 = PaleSpiderEntity.this.getZ() - wantedZ;
                double d4 = d0 * d0 + d2 * d2;
                return d4 > 4;
            } else {
                return super.canContinueToUse();
            }
        }

        public void stop() {
            super.stop();
            this.wantedX = 0;
            this.wantedY = 0;
            this.wantedZ = 0;
        }

        public void start() {
            if (PaleSpiderEntity.this.isUpsideDown()) {
                this.mob.getMoveControl().setWantedPosition(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier * 0.7F);
            } else {
                this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
            }
        }

    }

    private class DroppingMeleeGoal extends Goal {
        private boolean prevOnGround = false;

        public DroppingMeleeGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return PaleSpiderEntity.this.getTarget() != null;
        }


        @Override
        public void tick() {
            LivingEntity target = PaleSpiderEntity.this.getTarget();
            if (attackCooldown > 0) {
                attackCooldown--;
            }
            if (target != null) {
                double dist = PaleSpiderEntity.this.distanceTo(target);
                if (PaleSpiderEntity.this.isUpsideDown()) {
                    double d0 = PaleSpiderEntity.this.getX() - target.getX();
                    double d2 = PaleSpiderEntity.this.getZ() - target.getZ();
                    double xzDistSqr = d0 * d0 + d2 * d2;
                    BlockPos ceilingPos = new BlockPos((int) target.getX(), (int) (PaleSpiderEntity.this.getY() - 3 - random.nextInt(3)), (int) target.getZ());
                    BlockPos lowestPos = PaleSpiderEntity.getLowestPos(level(), ceilingPos);
                    PaleSpiderEntity.this.getMoveControl().setWantedPosition(lowestPos.getX() + 0.5F, ceilingPos.getY(), lowestPos.getZ() + 0.5F, 1.1D);
                    if (xzDistSqr < 12.5F) {
                        PaleSpiderEntity.this.setUpsideDown(false);
                    }
                } else {
                    if (PaleSpiderEntity.this.onGround()) {
                        PaleSpiderEntity.this.getNavigation().moveTo(target, 1.15D);
                    }
                }
                if (dist < 1.8D) {
                    if (attackCooldown == 0) {
                        PaleSpiderEntity.this.doHurtTarget(target);
                        attackCooldown = 20;
                        PaleSpiderEntity.this.swinging = true;
                    }
                }
            }
        }

    }

        protected <E extends PaleSpiderEntity> PlayState controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && !this.isUpsideDown()) {
            event.setAndContinue(SCURRY);
            event.getController().setAnimationSpeed(1.8D);
            return PlayState.CONTINUE;
        }
        else if (!(event.getLimbSwingAmount() > -0.06F && event.getLimbSwingAmount() < 0.06F) && this.isUpsideDown() & !this.isInWaterOrBubble() && !this.isClimbing()){
            event.setAndContinue(SCURRY_UPSIDE_DOWN);
            event.getController().setAnimationSpeed(1.8D);
            return PlayState.CONTINUE;
        }
        else if (this.isUpsideDown() & !this.isInWaterOrBubble() && !this.isClimbing()){
            event.setAndContinue(IDLE_UPSIDE_DOWN);
            return PlayState.CONTINUE;
        }
        return  event.setAndContinue(IDLE);
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        pSpawnData = super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
        RandomSource randomsource = pLevel.getRandom();

        if (pSpawnData == null) {
            pSpawnData = new PaleSpiderEntity.PaleSpiderEffectsGroupData();
            if (pLevel.getDifficulty() == Difficulty.HARD && randomsource.nextFloat() < 0.1F * pDifficulty.getSpecialMultiplier()) {
                ((PaleSpiderEntity.PaleSpiderEffectsGroupData)pSpawnData).setRandomEffect(randomsource);
            }
        }

        if (pSpawnData instanceof PaleSpiderEntity.PaleSpiderEffectsGroupData spider$spidereffectsgroupdata) {
            MobEffect mobeffect = spider$spidereffectsgroupdata.effect;
            if (mobeffect != null) {
                this.addEffect(new MobEffectInstance(mobeffect, -1));
            }
        }

        return pSpawnData;
    }

    public static class PaleSpiderEffectsGroupData implements SpawnGroupData {
        @Nullable
        public MobEffect effect;

        public void setRandomEffect(RandomSource pRandom) {
            int i = pRandom.nextInt(4);
            if (i <= 1) {
                this.effect = MobEffects.JUMP;
            } else if (i <= 2) {
                this.effect = MobEffects.MOVEMENT_SPEED;
            } else if (i <= 3) {
                this.effect = MobEffects.FIRE_RESISTANCE;
            }

        }
    }

    protected <E extends PaleSpiderEntity> PlayState attackController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationController.State.PAUSED) && attackCooldown == 0) {
            return event.setAndContinue(BITE);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::controller));
        controllers.add(new AnimationController<>(this, "Attack", 5, this::attackController));
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