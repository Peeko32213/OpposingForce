package com.unusualmodding.opposingforce.common.entity;

import com.unusualmodding.opposingforce.common.entity.util.AbstractMonster;
import com.unusualmodding.opposingforce.common.entity.util.SmartNearestTargetGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.pathfinder.Path;
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
import java.util.List;

public class TremblerEntity extends AbstractMonster implements GeoAnimatable, GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Integer> CHARGE_COOLDOWN_TICKS = SynchedEntityData.defineId(TremblerEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_TARGET = SynchedEntityData.defineId(TremblerEntity.class, EntityDataSerializers.BOOLEAN);

    private int stunnedTick;
    private boolean canBePushed = true;

    private static final RawAnimation IDLE_BREATH = RawAnimation.begin().thenLoop("animation.trembler.idle_breath");
    private static final RawAnimation IDLE_WOBBLE = RawAnimation.begin().thenLoop("animation.trembler.idle_wobble");
    private static final RawAnimation IDLE_SHAKE = RawAnimation.begin().thenLoop("animation.trembler.idle_shake");
    private static final RawAnimation MOVE = RawAnimation.begin().thenLoop("animation.trembler.move");
    private static final RawAnimation ROLL_ENTER = RawAnimation.begin().thenLoop("animation.trembler.roll_enter");
    private static final RawAnimation ROLLING = RawAnimation.begin().thenLoop("animation.trembler.rolling");

    public TremblerEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setMaxUpStep(1.0f);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.ARMOR, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.09D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    public static <T extends Mob> boolean canFirstTierSpawn(EntityType<TremblerEntity> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, RandomSource random) {
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


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new TrembleMeleeAttackGoal(this, 1.5D, false));
        this.goalSelector.addGoal(2, new TremblePrepareChargeGoal(this));
        this.goalSelector.addGoal(3, new TrembleChargeGoal(this, 2.5F));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.9D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(1, new SmartNearestTargetGoal(this, Player.class, true));
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean shouldHurt;
        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float knockback = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (shouldHurt = target.hurt(this.damageSources().mobAttack(this), damage)) {
            if (knockback > 0.0f && target instanceof LivingEntity) {
                ((LivingEntity) target).knockback(knockback * 0.5f, Mth.sin(this.getYRot() * ((float) Math.PI / 180)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }
            this.doEnchantDamageEffects(this, target);
            this.setLastHurtMob(target);
        }
        return shouldHurt;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CHARGE_COOLDOWN_TICKS, 0);
        this.entityData.define(HAS_TARGET, false);

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("StunTick", this.stunnedTick);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.stunnedTick = compound.getInt("StunTick");
    }

    private void attack(LivingEntity entity) {
        entity.hurt(this.damageSources().mobAttack(this), 8.0F);
    }

    public void setChargeCooldownTicks(int ticks) {
        this.entityData.set(CHARGE_COOLDOWN_TICKS, ticks);
    }

    public int getChargeCooldownTicks() {
        return this.entityData.get(CHARGE_COOLDOWN_TICKS);
    }

    public boolean hasChargeCooldown() {
        return this.entityData.get(CHARGE_COOLDOWN_TICKS) > 0;
    }

    public void resetChargeCooldownTicks() {
        this.entityData.set(CHARGE_COOLDOWN_TICKS, 20);
    }

    public void setHasTarget(boolean hasTarget) {
        this.entityData.set(HAS_TARGET, hasTarget);
    }

    public boolean hasTarget() {
        return this.entityData.get(HAS_TARGET);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.isAlive()) {
            return;
        }
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.isImmobile() ? 0.0 : 0.2);
        if (this.stunnedTick > 0) {
            --this.stunnedTick;
            this.stunEffect();
        }
    }

    private void stunEffect() {
        if (this.random.nextInt(6) == 0) {
            double d = this.getX() - (double) this.getBbWidth() * Math.sin(this.yBodyRot * ((float) Math.PI / 180)) + (this.random.nextDouble() * 0.6 - 0.3);
            double e = this.getY() + (double) this.getBbHeight() - 0.3;
            double f = this.getZ() + (double) this.getBbWidth() * Math.cos(this.yBodyRot * ((float) Math.PI / 180)) + (this.random.nextDouble() * 0.6 - 0.3);
            level().addParticle(ParticleTypes.CRIT, true, this.getX(), this.getEyeY() + 0.5F, this.getZ(), 0, 0, 0);
        }
    }

    @Override
    protected boolean isImmobile() {
        return super.isImmobile() || this.stunnedTick > 0;
    }

    @Override
    protected void blockedByShield(LivingEntity defender) {
        this.stunnedTick = 60;
        this.resetChargeCooldownTicks();
        this.getNavigation().stop();
        this.level().broadcastEntityEvent(this, (byte) 39);
        defender.push(this);
        defender.hurtMarked = true;
    }


    @Override
    public void handleEntityEvent(byte id) {
        if (id == 39) {
            this.stunnedTick = 60;
        }
        super.handleEntityEvent(id);
    }

    @Override
    public void customServerAiStep() {
        if (this.getMoveControl().hasWanted()) {
            this.setSprinting(this.getMoveControl().getSpeedModifier() >= 1.5D);
        } else {
            this.setSprinting(false);
        }
        super.customServerAiStep();
    }

    private boolean isWithinYRange(LivingEntity target) {
        if (target == null) {
            return false;
        }
        return Math.abs(target.getY() - this.getY()) < 3;
    }

    @Override
    public boolean isPushable() {
        return this.canBePushed;
    }

    @Override
    public float getVoicePitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f;
    }

    protected <E extends TremblerEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        if (this.stunnedTick > 0) {
            event.setAndContinue(IDLE_WOBBLE);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        } else if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            if (this.isSprinting()) {
                event.setAndContinue(ROLLING);
                return PlayState.CONTINUE;
            } else {
                event.setAndContinue(MOVE);
                event.getController().setAnimationSpeed(2.0F);
            }
        }
        else if (this.hasChargeCooldown() && this.hasTarget()) {
            event.setAndContinue(IDLE_SHAKE);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        } else {
            event.setAndContinue(IDLE_BREATH);
            event.getController().setAnimationSpeed(1.0F);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

    static class TremblePrepareChargeGoal extends Goal {
        protected final TremblerEntity tremble;

        public TremblePrepareChargeGoal(TremblerEntity tremble) {
            this.tremble = tremble;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.tremble.getTarget();
            if (target == null || !target.isAlive() || this.tremble.stunnedTick > 0 || !this.tremble.isWithinYRange(target)) {
                this.tremble.resetChargeCooldownTicks();
                return false;
            }
            return target instanceof Player && tremble.hasChargeCooldown();
        }

        @Override
        public void start() {
            LivingEntity target = this.tremble.getTarget();
            if (target == null) {
                return;
            }
            this.tremble.setHasTarget(true);
            this.tremble.resetChargeCooldownTicks();
            this.tremble.canBePushed = false;
        }

        @Override
        public void stop() {
            this.tremble.setHasTarget(false);
            this.tremble.canBePushed = true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.tremble.getTarget();
            if (target == null) {
                return;
            }
            this.tremble.getLookControl().setLookAt(target);
            this.tremble.setChargeCooldownTicks(Math.max(0, this.tremble.getChargeCooldownTicks() - 1));
        }
    }

    static class TrembleChargeGoal extends Goal {
        protected final TremblerEntity mob;
        private final double speedModifier;
        private Path path;
        private Vec3 chargeDirection;

        public TrembleChargeGoal(TremblerEntity pathfinderMob, double speedModifier) {
            this.mob = pathfinderMob;
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            this.chargeDirection = Vec3.ZERO;
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.mob.getTarget();
            if (target == null || !target.isAlive() || this.mob.hasChargeCooldown() || this.mob.stunnedTick > 0) {
                return false;
            }
            this.path = this.mob.getNavigation().createPath(target, 0);
            return target instanceof Player && this.path != null;
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = this.mob.getTarget();
            if (target == null || !target.isAlive() || this.mob.hasChargeCooldown() || this.mob.stunnedTick > 0) {
                return false;
            }
            return !this.mob.getNavigation().isDone();
        }

        @Override
        public void start() {
            BlockPos blockPosition = this.mob.blockPosition();
            BlockPos target = this.path.getTarget();
            this.chargeDirection = new Vec3(blockPosition.getX() - target.getX(), 0.0, blockPosition.getZ() - target.getZ()).normalize();
            this.mob.getNavigation().moveTo(this.path, this.speedModifier);
            this.mob.setAggressive(true);
        }

        @Override
        public void stop() {
            this.mob.resetChargeCooldownTicks();
            this.mob.getNavigation().stop();
        }


        @Override
        public void tick() {
            this.mob.getLookControl().setLookAt(Vec3.atCenterOf(this.path.getTarget()));
            if (this.mob.horizontalCollision && this.mob.onGround()) {
                this.mob.jumpFromGround();
            }
            this.tryToHurt();
        }

        protected void tryToHurt() {
            List<LivingEntity> nearbyEntities = this.mob.level().getNearbyEntities(LivingEntity.class, TargetingConditions.forCombat(), this.mob, this.mob.getBoundingBox());
            if (!nearbyEntities.isEmpty()) {
                LivingEntity livingEntity = nearbyEntities.get(0);
                if (!(livingEntity instanceof TremblerEntity)) {
                    livingEntity.hurt(this.mob.damageSources().mobAttack(this.mob), (float) this.mob.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    float speed = Mth.clamp(this.mob.getSpeed() * 1.65f, 0.2f, 3.0f);
                    float shieldBlockModifier = livingEntity.isDamageSourceBlocked(this.mob.damageSources().mobAttack(this.mob)) ? 0.5f : 1.0f;
                    livingEntity.knockback(shieldBlockModifier * speed * 2.0D, this.chargeDirection.x(), this.chargeDirection.z());
                    double knockbackResistance = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().add(0.0, 0.4f * knockbackResistance, 0.0));
                    this.mob.swing(InteractionHand.MAIN_HAND);
                    if (livingEntity.equals(this.mob.getTarget())) {
                        this.stop();
                    }
                }
            }
        }
    }

    static class TrembleMeleeAttackGoal extends MeleeAttackGoal {

        public TrembleMeleeAttackGoal(PathfinderMob pathfinderMob, double speedModifier, boolean followEvenIfNotSeen) {
            super(pathfinderMob, speedModifier, followEvenIfNotSeen);
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.mob.getTarget();
            if (target instanceof Player) {
                return false;
            }
            return super.canUse();
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
            double d0 = this.getAttackReachSqr(enemy);
            if (distToEnemySqr <= d0 && this.getTicksUntilNextAttack() <= 0) {
                this.resetAttackCooldown();
                ((TremblerEntity) this.mob).attack(enemy);
            }
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return (double) (this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.9F + p_25556_.getBbWidth());
        }

    }


}
