package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
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
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class PaleSpider extends Monster {

    private static final EntityDataAccessor<Boolean> UPSIDE_DOWN = SynchedEntityData.defineId(PaleSpider.class, EntityDataSerializers.BOOLEAN);

    private boolean isUpsideDownNavigator;
    private boolean jumpingUp = false;
    private int upwardsFallingTicks = 0;

    public final AnimationState idleAnimationState = new AnimationState();

    public PaleSpider(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        switchNavigator(true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PaleSpiderAttackGoal(this));
        this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(3, new PaleSpiderRandomStrollGoal(this));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, LivingEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new PaleSpiderNearestAttackableTargetGoal<>(this, Player.class, true));
    }

    private void switchNavigator(boolean onGround) {
        if (onGround) {
            this.moveControl = new MoveControl(this);
            this.navigation = new GroundPathNavigation(this, level());
            this.isUpsideDownNavigator = false;
        } else {
            this.moveControl = new PaleSpiderMoveControl(this);
            this.navigation = new PaleSpiderPathNavigation(this, level());
            this.isUpsideDownNavigator = true;
        }
    }

    public boolean isInvulnerableTo(DamageSource source) {
        return super.isInvulnerableTo(source) || source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypes.IN_WALL);
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return entityDimensions.height * 0.65F;
    }

    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {

            BlockPos abovePos = this.getPositionAbove();
            BlockState aboveState = level().getBlockState(abovePos);
            BlockState belowState = level().getBlockState(this.getBlockPosBelowThatAffectsMyMovement());
            BlockPos worldHeight = level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, this.blockPosition());
            boolean validAboveState = aboveState.isFaceSturdy(level(), abovePos, Direction.DOWN);
            boolean validBelowState = belowState.isFaceSturdy(level(), this.getBlockPosBelowThatAffectsMyMovement(), Direction.UP);
            LivingEntity target = this.getTarget();

            if (jumpingUp && this.getY() > worldHeight.getY()) {
                jumpingUp = false;
            }
            if (this.onGround() && (target == null || !target.isAlive()) && random.nextInt(300) == 0 && this.onGround() && !this.isUpsideDown() && this.getY() + 2 < worldHeight.getY() || jumpingUp) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, 2F, 0));
                jumpingUp = true;
            }
            if (this.isUpsideDown()) {
                jumpingUp = false;
                this.setNoGravity(!this.onGround());
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.91F, 1F, 0.91F));
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
                if (!this.isUpsideDownNavigator) switchNavigator(false);
            } else {
                if (this.isUpsideDownNavigator) switchNavigator(true);
            }

            if (this.level().isClientSide()) {
                this.setupAnimationStates();
            }
        }
    }

    private void setupAnimationStates() {
        this.idleAnimationState.animateWhen(this.isAlive(), this.tickCount);
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
            for (Entity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.5D))) {
                if (!isAlliedTo(entity) && entity != this) {
                    entity.hurt(this.damageSources().mobAttack(this), 2.0F + random.nextFloat() * 5F);
                }
            }
        }
    }

    public static BlockPos getLowestPos(LevelAccessor world, BlockPos pos) {
        while (!world.getBlockState(pos).isFaceSturdy(world, pos, Direction.DOWN) && pos.getY() < 320) {
            pos = pos.above();
        }
        return pos;
    }

    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.PALE_SPIDER_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return OPSoundEvents.PALE_SPIDER_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return OPSoundEvents.PALE_SPIDER_DEATH.get();
    }

    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.SPIDER_STEP, 0.15F, 1.2F);
    }

    protected void playBlockFallSound() {
        this.onLand();
        super.playBlockFallSound();
    }

    public void makeStuckInBlock(BlockState blockState, Vec3 vec3) {
        if (!blockState.is(Blocks.COBWEB)) {
            super.makeStuckInBlock(blockState, vec3);
        }
    }

    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    public boolean canBeAffected(MobEffectInstance p_33809_) {
        if (p_33809_.getEffect() == MobEffects.POISON) {
            MobEffectEvent.Applicable event = new MobEffectEvent.Applicable(this, p_33809_);
            MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == Event.Result.ALLOW;
        } else {
            return super.canBeAffected(p_33809_);
        }
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return checkPaleSpiderSpawnRules(entityType, level, spawnType, pos, random);
    }

    public static boolean checkPaleSpiderSpawnRules(EntityType<? extends Monster> entityType, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() <= 48 && (random.nextInt(10) == 0 || pos.getY() <= 0) && level.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawnNoSkylight(level, pos, random) && checkMobSpawnRules(entityType, level, spawnType, pos, random);
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

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compoundTag) {
        RandomSource randomsource = level.getRandom();

        if (spawnData == null) {
            spawnData = new Spider.SpiderEffectsGroupData();
            if (level.getDifficulty() == Difficulty.HARD && randomsource.nextFloat() < 0.1F * difficulty.getSpecialMultiplier()) {
                ((Spider.SpiderEffectsGroupData) spawnData).setRandomEffect(randomsource);
            }
        }

        if (spawnData instanceof Spider.SpiderEffectsGroupData spiderEffectsGroupData) {
            MobEffect mobeffect = spiderEffectsGroupData.effect;
            if (mobeffect != null) {
                this.addEffect(new MobEffectInstance(mobeffect, -1));
            }
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnData, compoundTag);
    }

    // goals
    private class PaleSpiderRandomStrollGoal extends RandomStrollGoal {

        protected final PaleSpider spider;

        public PaleSpiderRandomStrollGoal(PaleSpider spider) {
            super(spider, 1D, 50);
            this.spider = spider;
        }

        @Nullable
        protected Vec3 getPosition() {
            if (this.spider.isUpsideDown()) {
                for (int i = 0; i < 15; i++) {
                    Random rand = new Random();
                    BlockPos randPos = this.spider.blockPosition().offset(rand.nextInt(16) - 8, -2, rand.nextInt(16) - 8);
                    BlockPos lowestPos = PaleSpider.getLowestPos(level(), randPos);
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
            if (this.spider.isUpsideDown()) {
                double d0 = this.spider.getX() - wantedX;
                double d2 = this.spider.getZ() - wantedZ;
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
            if (this.spider.isUpsideDown()) {
                this.mob.getMoveControl().setWantedPosition(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier * 0.7F);
            } else {
                this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
            }
        }
    }

    private class PaleSpiderAttackGoal extends Goal {

        protected final PaleSpider spider;
        private int attackCooldown = 0;

        public PaleSpiderAttackGoal(PaleSpider spider) {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            this.spider = spider;
        }

        @Override
        public boolean canUse() {
            return this.spider.getTarget() != null;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.spider.getTarget();
            if (this.attackCooldown > 0) {
                this.attackCooldown--;
            }
            if (target != null) {
                double distance = this.spider.distanceTo(target);
                if (this.spider.isUpsideDown()) {
                    double d0 = this.spider.getX() - target.getX();
                    double d2 = this.spider.getZ() - target.getZ();
                    double xzDistSqr = d0 * d0 + d2 * d2;
                    BlockPos ceilingPos = new BlockPos((int) target.getX(), (int) (this.spider.getY() - 3 - random.nextInt(3)), (int) target.getZ());
                    BlockPos lowestPos = PaleSpider.getLowestPos(level(), ceilingPos);
                    this.spider.getMoveControl().setWantedPosition(lowestPos.getX() + 0.5F, ceilingPos.getY(), lowestPos.getZ() + 0.5F, 1.1D);
                    if (xzDistSqr < 2.5F) {
                        this.spider.setUpsideDown(false);
                    }
                } else {
                    if (this.spider.onGround()) {
                        this.spider.getNavigation().moveTo(target, 1.2D);
                    }
                }
                if (distance < 1.6D && this.attackCooldown == 0) {
                    this.spider.doHurtTarget(target);
                    this.spider.swing(InteractionHand.MAIN_HAND);
                    this.attackCooldown = 24;
                }
            }
        }
    }

    private static class PaleSpiderNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

        public PaleSpiderNearestAttackableTargetGoal(PaleSpider paleSpider, Class mob, boolean canSee) {
            super(paleSpider, mob, canSee);
        }

        protected AABB getTargetSearchArea(double targetDistance) {
            AABB bb = this.mob.getBoundingBox().inflate(targetDistance, targetDistance, targetDistance);
            return new AABB(bb.minX, 0, bb.minZ, bb.maxX, 256, bb.maxZ);
        }
    }

    private static class PaleSpiderMoveControl extends MoveControl {

        private final PaleSpider spider;

        public PaleSpiderMoveControl(PaleSpider spider) {
            super(spider);
            this.spider = spider;
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 vector3d = new Vec3(this.wantedX - this.spider.getX(), this.wantedY - this.spider.getY(), this.wantedZ - this.spider.getZ());
                double length = vector3d.length();
                if (length < this.spider.getBoundingBox().getSize()) {
                    this.operation = MoveControl.Operation.WAIT;
                    this.spider.setDeltaMovement(this.spider.getDeltaMovement().scale(0.5D));
                } else {
                    this.spider.setDeltaMovement(this.spider.getDeltaMovement().add(vector3d.scale(this.speedModifier * 1.0D * 0.05D / length)));
                    if (this.spider.getTarget() == null) {
                        Vec3 vector3d1 = this.spider.getDeltaMovement();
                        this.spider.setYRot(-((float) Mth.atan2(vector3d1.x, vector3d1.z)) * Mth.RAD_TO_DEG);
                    } else {
                        double d2 = this.spider.getTarget().getX() - this.spider.getX();
                        double d1 = this.spider.getTarget().getZ() - this.spider.getZ();
                        this.spider.setYRot(-((float) Mth.atan2(d2, d1)) * Mth.RAD_TO_DEG);
                    }
                    this.spider.yBodyRot = this.spider.getYRot();
                }
            } else if (this.operation == Operation.STRAFE) {
                this.operation = Operation.WAIT;
            }
        }
    }

    private static class PaleSpiderPathNavigation extends GroundPathNavigation {

        private final PaleSpider spider;

        public PaleSpiderPathNavigation(PaleSpider spider, Level world) {
            super(spider, world);
            this.spider = spider;
        }

        public void tick() {
            this.tick++;
        }

        public boolean moveTo(double x, double y, double z, double speedIn) {
            this.spider.getMoveControl().setWantedPosition(x, y, z, speedIn);
            return true;
        }

        public boolean moveTo(Entity entityIn, double speedIn) {
            this.spider.getMoveControl().setWantedPosition(entityIn.getX(), entityIn.getY(), entityIn.getZ(), speedIn);
            return true;
        }
    }
}