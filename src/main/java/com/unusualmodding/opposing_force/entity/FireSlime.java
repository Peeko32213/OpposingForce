package com.unusualmodding.opposing_force.entity;

import com.google.common.collect.Lists;
import com.unusualmodding.opposing_force.entity.ai.goal.AttackGoal;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BooleanSupplier;

public class FireSlime extends Monster {

    private static final EntityDataAccessor<Boolean> DESPAWN = SynchedEntityData.defineId(FireSlime.class, EntityDataSerializers.BOOLEAN);
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
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.6F).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FireSlimeFloatGoal(this));
        this.goalSelector.addGoal(1, new FireSlimeAttackGoal(this));
        this.goalSelector.addGoal(2, new FireSlimeRandomDirectionGoal(this));
        this.goalSelector.addGoal(3, new FireSlimeKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new FireSlimeCopyTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (entity) -> Math.abs(entity.getY() - this.getY()) <= (double) 4.0F));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    protected void dealDamage() {
        if (this.isAlive()) {
            LivingEntity target = this.getTarget();
            if (target != null) {
                if (this.distanceToSqr(target) < 1.1F && this.hasLineOfSight(target) && target.hurt(this.damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE))) {
                    this.playSound(OPSoundEvents.FIRE_SLIME_ATTACK.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    if (this.random.nextBoolean()) {
                        target.setSecondsOnFire(3);
                    }
                    this.doEnchantDamageEffects(this, target);
                }
            }
        }
    }

    protected boolean dealsDamage() {
        return this.isEffectiveAi();
    }

    @Override
    public int getMaxFallDistance() {
        return 10;
    }

    @Override
    protected void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, this.getJumpPower(), vec3.z);
        this.hasImpulse = true;
    }

    private void jumpInLiquidInternal(BooleanSupplier isLava, Runnable onSuper) {
        if (isLava.getAsBoolean()) {
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(vec3.x, 0.22F * 0.05F, vec3.z);
            this.hasImpulse = true;
        } else {
            onSuper.run();
        }
    }

    @Override
    public void jumpInFluid(FluidType type) {
        this.jumpInLiquidInternal(() -> type == ForgeMod.LAVA_TYPE.get(), () -> super.jumpInFluid(type));
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    public void tick() {
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

        if (this.tickCount % 4 == 0 && this.isAlive()) {
            float f = this.random.nextFloat() * ((float) Math.PI * 2F);
            float f1 = this.random.nextFloat() * 0.5F + 0.5F;
            float f2 = Mth.sin(f) * 0.75F * f1;
            float f3 = Mth.cos(f) * 0.75F * f1;
            this.level().addParticle(ParticleTypes.FLAME, this.getX() + (double) f2, this.getY() + 0.3D, this.getZ() + (double) f3, 0.0D, 0.0D, 0.0D);
        }

        if (this.shouldDespawn()) {
            this.despawnTimer++;
            if (this.despawnTimer > 160) {
                this.navigation.stop();
                if (this.getTarget() != null) {
                    this.setTarget(null);
                }
                this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
                if (this.tickCount % 2 == 0) {
                    double d0 = this.random.nextGaussian() * 0.02D;
                    double d1 = this.random.nextGaussian() * 0.02D;
                    double d2 = this.random.nextGaussian() * 0.02D;
                    this.level().addParticle(ParticleTypes.LAVA, this.getX(), this.getRandomY() + 0.5D - d1 * 10.0D, this.getZ(), d0, d1, d2);
                }
            }
            if (this.despawnTimer > 200) {
                this.despawnTimer = 0;
                this.playSound(OPSoundEvents.FIRE_SLIME_POP.get(), this.getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
                this.spawnAnim();
                this.remove(RemovalReason.DISCARDED);
            }
        }
        super.tick();
    }

    protected void decreaseSquish() {
        this.targetSquish *= 0.6F;
    }

    protected int getJumpDelay() {
        return this.random.nextInt(20) + 14;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return OPSoundEvents.FIRE_SLIME_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return OPSoundEvents.FIRE_SLIME_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return OPSoundEvents.FIRE_SLIME_DEATH.get();
    }

    protected SoundEvent getSquishSound() {
        return OPSoundEvents.FIRE_SLIME_SQUISH.get();
    }

    protected SoundEvent getJumpSound() {
        return OPSoundEvents.FIRE_SLIME_JUMP.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 360;
    }

    @Nullable
    public UUID getParentId() {
        return this.entityData.get(PARENT_UUID).orElse(null);
    }

    public void setParentId(@Nullable UUID uniqueId) {
        this.entityData.set(PARENT_UUID, Optional.ofNullable(uniqueId));
    }

    public boolean shouldDespawn() {
        return this.entityData.get(DESPAWN);
    }

    public void setShouldDespawn(boolean despawn) {
        this.entityData.set(DESPAWN, despawn);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PARENT_UUID, Optional.empty());
        this.entityData.define(DESPAWN, false);
        this.entityData.define(LAUNCHED, false);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.getParentId() != null) {
            compoundTag.putUUID("ParentUUID", this.getParentId());
        }
        compoundTag.putBoolean("ShouldDespawn", this.shouldDespawn());
        compoundTag.putBoolean("wasOnGround", this.wasOnGround);
        compoundTag.putInt("despawnTimer", this.despawnTimer);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setShouldDespawn(compoundTag.getBoolean("ShouldDespawn"));
        this.wasOnGround = compoundTag.getBoolean("wasOnGround");
        this.despawnTimer = compoundTag.getInt("despawnTimer");
    }

    public Entity getParent() {
        UUID id = getParentId();
        if (id != null && !this.level().isClientSide) {
            return ((ServerLevel) level()).getEntity(id);
        }
        return null;
    }

    public void shoot(double x, double y, double z, float scale, float speed) {
        Vec3 vec3 = (new Vec3(x, y, z)).normalize().add(this.random.nextGaussian() * 0.008D * (double) speed, this.random.nextGaussian() * 0.008D * (double) speed, this.random.nextGaussian() * 0.008D * (double) speed).scale(scale);
        this.setDeltaMovement(vec3);
        float horizontalDistanceSqr = (float) vec3.horizontalDistanceSqr();
        this.setYRot( (float) (Mth.atan2(vec3.x, vec3.z) * 55D));
        this.setXRot((float) (Mth.atan2(vec3.y, horizontalDistanceSqr) * 55D));
        this.xRotO = this.getXRot();
        this.yBodyRot = getYRot();
        this.yHeadRot = getYRot();
        this.yHeadRotO = getYRot();
        this.yRotO = getYRot();
        this.setShouldDespawn(true);
        this.entityData.set(LAUNCHED, true);
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(Items.BLAZE_POWDER.asItem()) && this.shouldDespawn()) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            this.gameEvent(GameEvent.ENTITY_INTERACT);
            this.setShouldDespawn(false);
            this.despawnTimer = 0;
            this.playSound(SoundEvents.BLAZE_BURN, this.getSoundVolume(), this.getVoicePitch());
            this.level().broadcastEntityEvent(this, (byte) 39);
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 39) {
            for (int i = 0; i < 15; i++) {
                float f = this.random.nextFloat() * ((float) Math.PI * 2F);
                float f1 = this.random.nextFloat() * 0.5F + 0.5F;
                float f2 = Mth.sin(f) * 0.81F * f1;
                float f3 = Mth.cos(f) * 0.81F * f1;
                this.level().addParticle(ParticleTypes.FLAME, this.getX() + (double) f2, this.getY() + 0.43D, this.getZ() + (double) f3, 0.0D, 0.0D, 0.0D);
            }
        }
        super.handleEntityEvent(id);
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
                    this.mob.setSpeed((float) (1.25F * this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
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
                fireSlimeMoveControl.setWantedMovement(4.0F);
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
                fireSlimeMoveControl.setWantedMovement(1.0F);
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
                this.slime.dealDamage();
            }
            MoveControl movecontrol = this.slime.getMoveControl();
            if (movecontrol instanceof FireSlimeMoveControl fireSlimeMoveControl) {
                fireSlimeMoveControl.setDirection(this.slime.getYRot(), this.slime.dealsDamage());
            }
        }
    }

    public static class FireSlimeCopyTargetGoal extends TargetGoal {

        private final FireSlime slime;

        public FireSlimeCopyTargetGoal(FireSlime slime) {
            super(slime, false);
            this.slime = slime;
        }

        public boolean canUse() {
            return this.slime.getParent() instanceof Mob parent && parent.getTarget() != null;
        }

        public void start() {
            var target = ((Mob) this.slime.getParent()).getTarget();
            this.slime.setTarget(target);
            this.slime.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET, target, 200L);
            super.start();
        }
    }
}
