package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.registry.OPEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

public class FireSlime extends Monster {

    private static final EntityDataAccessor<Boolean> DESPAWN_SOON = SynchedEntityData.defineId(FireSlime.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LAUNCHED = SynchedEntityData.defineId(FireSlime.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<UUID>> PARENT_UUID = SynchedEntityData.defineId(FireSlime.class, EntityDataSerializers.OPTIONAL_UUID);

    private int despawnTimer = 0;

    public float targetSquish;
    public float squish;
    public float oSquish;
    private boolean wasOnGround;

    public FireSlime(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FireSlimeMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.2F).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FireSlimeFloatGoal(this));
        this.goalSelector.addGoal(2, new FireSlimeAttackGoal(this));
        this.goalSelector.addGoal(3, new FireSlimeRandomDirectionGoal(this));
        this.goalSelector.addGoal(4, new FireSlimeKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (entity) -> Math.abs(entity.getY() - this.getY()) <= (double) 4.0F));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    public void playerTouch(Player player) {
        if (this.dealsDamage()) {
            this.dealDamage(player);
        }
    }

    protected void dealDamage(LivingEntity livingEntity) {
        if (this.isAlive()) {
            if (this.distanceToSqr(livingEntity) < 1.0F && this.hasLineOfSight(livingEntity) && livingEntity.hurt(this.damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE))) {
                this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                if (this.random.nextBoolean()) {
                    livingEntity.setSecondsOnFire(3);
                }
                this.doEnchantDamageEffects(this, livingEntity);
            }
        }
    }

    protected boolean dealsDamage() {
        return this.isEffectiveAi();
    }

    protected void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, this.getJumpPower(), vec3.z);
        this.hasImpulse = true;
    }

    public void tick() {
        super.tick();

        this.squish += (this.targetSquish - this.squish) * 0.5F;
        this.oSquish = this.squish;
        if (this.onGround() && !this.wasOnGround) {
            this.playSound(this.getSquishSound(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            this.targetSquish = -0.5F;
        } else if (!this.onGround() && this.wasOnGround) {
            this.targetSquish = 1.0F;
        }
        this.wasOnGround = this.onGround();
        this.decreaseSquish();

        if (isDespawnSoon()) {
            despawnTimer++;
            if (despawnTimer > 100) {
                despawnTimer = 0;
                this.spawnAnim();
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    protected void decreaseSquish() {
        this.targetSquish *= 0.6F;
    }

    protected int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    protected SoundEvent getHurtSound(DamageSource p_33631_) {
        return SoundEvents.SLIME_HURT_SMALL;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH_SMALL;
    }

    protected SoundEvent getSquishSound() {
        return SoundEvents.SLIME_SQUISH_SMALL;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.SLIME_JUMP_SMALL;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PARENT_UUID, Optional.empty());
        this.entityData.define(DESPAWN_SOON, false);
        this.entityData.define(LAUNCHED, false);
    }

    @Nullable
    public UUID getParentId() {
        return this.entityData.get(PARENT_UUID).orElse(null);
    }

    public void setParentId(@Nullable UUID uniqueId) {
        this.entityData.set(PARENT_UUID, Optional.ofNullable(uniqueId));
    }

    public boolean isDespawnSoon() {
        return this.entityData.get(DESPAWN_SOON);
    }

    public void setDespawnSoon(boolean despawnSoon) {
        this.entityData.set(DESPAWN_SOON, despawnSoon);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.getParentId() != null) {
            compound.putUUID("ParentUUID", this.getParentId());
        }
        compound.putBoolean("DespawnSoon", this.isDespawnSoon());
    }

    protected boolean canHitEntity(Entity entity) {
        return !entity.isSpectator() && !(entity instanceof Guzzler);
    }

    public Entity getParent() {
        UUID id = getParentId();
        if (id != null && !this.level().isClientSide) {
            return ((ServerLevel) level()).getEntity(id);
        }
        return null;
    }

    public void shoot(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_) {
        Vec3 lvt_9_1_ = (new Vec3(p_70186_1_, p_70186_3_, p_70186_5_)).normalize().add(this.random.nextGaussian() * 0.007499999832361937D * (double) p_70186_8_, this.random.nextGaussian() * 0.007499999832361937D * (double) p_70186_8_, this.random.nextGaussian() * 0.007499999832361937D * (double) p_70186_8_).scale(p_70186_7_);
        this.setDeltaMovement(lvt_9_1_);
        float lvt_10_1_ = (float) lvt_9_1_.horizontalDistanceSqr();
        this.setYRot( (float) (Mth.atan2(lvt_9_1_.x, lvt_9_1_.z) * 57.2957763671875D));
        this.setXRot((float) (Mth.atan2(lvt_9_1_.y, lvt_10_1_) * 57.2957763671875D));
        this.xRotO = this.getXRot();
        this.yBodyRot = getYRot();
        this.yHeadRot = getYRot();
        this.yHeadRotO = getYRot();
        this.yRotO = getYRot();
        this.setDespawnSoon(true);
        this.entityData.set(LAUNCHED, true);
    }

    private static class FireSlimeMoveControl extends MoveControl {

        private final FireSlime slime;
        private float yRot;
        private int jumpDelay;
        private boolean isAggressive;

        public FireSlimeMoveControl(FireSlime slime) {
            super(slime);
            this.slime = slime;
            this.yRot = 180.0F * slime.getYRot() / (float)Math.PI;
        }

        public void setDirection(float yRot, boolean isAggressive) {
            this.yRot = yRot;
            this.isAggressive = isAggressive;
        }

        public void setWantedMovement(double speedModifier) {
            this.speedModifier = speedModifier;
            this.operation = Operation.MOVE_TO;
        }

        public void tick() {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (this.operation != Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = Operation.WAIT;
                if (this.mob.onGround()) {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.slime.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }
                        this.slime.getJumpControl().jump();
                        this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), (this.slime.random.nextFloat() - this.slime.random.nextFloat()) * 0.2F + 1.0F);
                    } else {
                        this.slime.xxa = 0.0F;
                        this.slime.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else {
                    this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }
            }
        }
    }

    private static class FireSlimeRandomDirectionGoal extends Goal {

        private final FireSlime slime;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public FireSlimeRandomDirectionGoal(FireSlime slime) {
            this.slime = slime;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        public boolean canUse() {
            return this.slime.getTarget() == null && (this.slime.onGround() || this.slime.isInWater() || this.slime.isInLava() || this.slime.hasEffect(MobEffects.LEVITATION)) && this.slime.getMoveControl() instanceof FireSlimeMoveControl;
        }

        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(20 + this.slime.getRandom().nextInt(30));
                this.chosenDegrees = (float) this.slime.getRandom().nextInt(360);
            }

            MoveControl movecontrol = this.slime.getMoveControl();
            if (movecontrol instanceof FireSlimeMoveControl fireSlimeMoveControl) {
                fireSlimeMoveControl.setDirection(this.chosenDegrees, false);
            }
        }
    }

    static class FireSlimeFloatGoal extends Goal {

        private final FireSlime slime;

        public FireSlimeFloatGoal(FireSlime slime) {
            this.slime = slime;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
            slime.getNavigation().setCanFloat(true);
        }

        public boolean canUse() {
            return (this.slime.isInWater() || this.slime.isInLava()) && this.slime.getMoveControl() instanceof FireSlimeMoveControl;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            if (this.slime.getRandom().nextFloat() < 0.8F) {
                this.slime.getJumpControl().jump();
            }
            MoveControl movecontrol = this.slime.getMoveControl();
            if (movecontrol instanceof FireSlimeMoveControl fireSlimeMoveControl) {
                fireSlimeMoveControl.setWantedMovement(1.2);
            }
        }
    }

    static class FireSlimeKeepOnJumpingGoal extends Goal {

        private final FireSlime slime;

        public FireSlimeKeepOnJumpingGoal(FireSlime slime) {
            this.slime = slime;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        public boolean canUse() {
            return !this.slime.isPassenger();
        }

        public void tick() {
            MoveControl movecontrol = this.slime.getMoveControl();
            if (movecontrol instanceof FireSlimeMoveControl fireSlimeMoveControl) {
                fireSlimeMoveControl.setWantedMovement(1.5F);
            }
        }
    }

    static class FireSlimeAttackGoal extends Goal {

        private final FireSlime slime;
        private int growTiredTimer;

        public FireSlimeAttackGoal(FireSlime slime) {
            this.slime = slime;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity livingentity = this.slime.getTarget();
            if (livingentity == null) {
                return false;
            } else {
                return this.slime.canAttack(livingentity) && this.slime.getMoveControl() instanceof FireSlimeMoveControl;
            }
        }

        public void start() {
            this.growTiredTimer = reducedTickDelay(300);
            super.start();
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = this.slime.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!this.slime.canAttack(livingentity)) {
                return false;
            } else {
                return --this.growTiredTimer > 0;
            }
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingentity = this.slime.getTarget();
            if (livingentity != null) {
                this.slime.lookAt(livingentity, 10.0F, 10.0F);
            }
            MoveControl movecontrol = this.slime.getMoveControl();
            if (movecontrol instanceof FireSlimeMoveControl fireSlimeMoveControl) {
                fireSlimeMoveControl.setDirection(this.slime.getYRot(), this.slime.dealsDamage());
            }
        }
    }
}
