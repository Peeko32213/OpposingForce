package com.unusualmodding.opposingforce.common.entity;

import com.unusualmodding.opposingforce.common.entity.util.AbstractMonster;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
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
import java.util.Optional;
import java.util.UUID;

public class FireSlimeEntity extends AbstractMonster implements GeoAnimatable, GeoEntity {
    private static final EntityDataAccessor<Boolean> DESPAWN_SOON = SynchedEntityData.defineId(FireSlimeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LAUNCHED = SynchedEntityData.defineId(FireSlimeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Optional<UUID>> PARENT_UUID = SynchedEntityData.defineId(FireSlimeEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final RawAnimation MOVE = RawAnimation.begin().thenLoop("animation.fireslime.idle");
    private int ricochetCount = 0;
    private int despawnTimer = 0;

    public FireSlimeEntity(EntityType<? extends FireSlimeEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.fixupDimensions();
        this.moveControl = new FireSlimeEntity.FireSlimeMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0)
                .add(Attributes.MOVEMENT_SPEED, (double)0.19F)
                .add(Attributes.ATTACK_DAMAGE, 2.0F);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FireSlimeEntity.FireSlimeFloatGoal(this));
        this.goalSelector.addGoal(2, new FireSlimeEntity.FireSlimeAttackGoal(this));
        this.goalSelector.addGoal(3, new FireSlimeEntity.FireSlimeRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new FireSlimeEntity.FireSlimeKeepOnJumpingGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_289461_) -> {
            return Math.abs(p_289461_.getY() - this.getY()) <= 4.0D;
        }));
    }

    public void playerTouch(Player pEntity) {
        if (this.isDealsDamage()) {
            this.dealDamage(pEntity);
        }

    }

    protected void dealDamage(LivingEntity pLivingEntity) {
        if (this.isAlive()) {
            if (this.distanceToSqr(pLivingEntity) < 0.6D && this.hasLineOfSight(pLivingEntity) && pLivingEntity.hurt(this.damageSources().mobAttack(this), this.getAttackDamage())) {
                this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.doEnchantDamageEffects(this, pLivingEntity);
            }
        }

    }

    protected boolean isDealsDamage() {
        return this.isEffectiveAi();
    }

    protected float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    protected void jumpFromGround() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x, (double)this.getJumpPower(), vec3.z);
        this.hasImpulse = true;
    }

    protected int getJumpDelay() {
        return this.random.nextInt(10) + 5;
    }

    protected boolean doPlayJumpSound() {
        return true;
    }

    protected SoundEvent getJumpSound() {
        return SoundEvents.SLIME_JUMP;
    }

    float getSoundPitch() {
        return ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
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

    private void onImpact(HitResult raytraceresult) {
        HitResult.Type raytraceresult$type = raytraceresult.getType();
        if (raytraceresult$type == HitResult.Type.ENTITY) {
            this.onEntityHit((EntityHitResult) raytraceresult);
        } else if (raytraceresult$type == HitResult.Type.BLOCK) {
            BlockHitResult traceResult = (BlockHitResult) raytraceresult;
            BlockState blockstate = this.level().getBlockState(traceResult.getBlockPos());
            if (!blockstate.getBlockSupportShape(this.level(), traceResult.getBlockPos()).isEmpty()) {
                Direction face = traceResult.getDirection();
                Vec3 prevMotion = this.getDeltaMovement();
                double motionX = prevMotion.x();
                double motionY = prevMotion.y();
                double motionZ = prevMotion.z();
                switch (face) {
                    case EAST, WEST -> motionX = -motionX;
                    case SOUTH, NORTH -> motionZ = -motionZ;
                    default -> motionY = -motionY;
                }
                this.setDeltaMovement(motionX, motionY, motionZ);
                if (this.tickCount > 200 || ricochetCount > 20) {
                    this.entityData.set(LAUNCHED, false);
                } else {
                    ricochetCount++;
                }
            }
        }
    }

    protected boolean canHitEntity(Entity p_230298_1_) {
        return !p_230298_1_.isSpectator() && !(p_230298_1_ instanceof GuzzlerEntity);
    }

    public void tick() {
        float f = 1.0F;
        if (entityData.get(LAUNCHED)) {
            this.yBodyRot = this.getYRot();
            HitResult raytraceresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (raytraceresult != null && raytraceresult.getType() != HitResult.Type.MISS) {
                this.onImpact(raytraceresult);
            }
            f = 0.1F;
        }
        super.tick();
        final boolean liquid = this.isInWater() || this.isInLava();
        if (this.onGround() && !this.isInWater() && !this.isInLava()) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0F - 1.0F) * 0.2F, 0.5D, (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F));
            this.setYRot( this.random.nextFloat() * 360.0F);
            this.setOnGround(false);
            this.hasImpulse = true;
        }
        this.setNoGravity(false);
        if (isDespawnSoon()) {
            despawnTimer++;
            if (despawnTimer > 80) {
                despawnTimer = 0;
                this.spawnAnim();
                this.remove(RemovalReason.DISCARDED);
            }
        }
    }

    public Entity getParent() {
        UUID id = getParentId();
        if (id != null && !this.level().isClientSide) {
            return ((ServerLevel) level()).getEntity(id);
        }
        return null;
    }

    protected void damageShieldFor(Player holder, float damage) {
        if (holder.getUseItem().canPerformAction(ToolActions.SHIELD_BLOCK)) {
            if (!this.level().isClientSide) {
                holder.awardStat(Stats.ITEM_USED.get(holder.getUseItem().getItem()));
            }

            if (damage >= 3.0F) {
                int i = 1 + Mth.floor(damage);
                InteractionHand hand = holder.getUsedItemHand();
                holder.getUseItem().hurtAndBreak(i, holder, (p_213833_1_) -> {
                    p_213833_1_.broadcastBreakEvent(hand);
                    net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(holder, holder.getUseItem(), hand);
                });
                if (holder.getUseItem().isEmpty()) {
                    if (hand == InteractionHand.MAIN_HAND) {
                        holder.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    } else {
                        holder.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    }
                    holder.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + this.level().random.nextFloat() * 0.4F);
                }
            }

        }
    }

    private void onEntityHit(EntityHitResult raytraceresult) {
        Entity entity = this.getParent();
        if (entity instanceof LivingEntity && !this.level().isClientSide && raytraceresult.getEntity() instanceof LivingEntity target) {
            if(!target.isBlocking()){
                target.hurt(damageSources().mobProjectile(this, (LivingEntity)entity), 3.0F);
                target.knockback(0.7F, entity.getX() - this.getX(), entity.getZ() - this.getZ());
            }else{
                if (this.getTarget() instanceof Player) {
                    this.damageShieldFor(((Player) this.getTarget()), 3.0F);
                }
            }
            this.entityData.set(LAUNCHED, false);
        }
    }

    static class FireSlimeFloatGoal extends Goal {
        private final FireSlimeEntity slime;

        public FireSlimeFloatGoal(FireSlimeEntity pSlime) {
            this.slime = pSlime;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            pSlime.getNavigation().setCanFloat(true);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return (this.slime.isInWater() || this.slime.isInLava()) && this.slime.getMoveControl() instanceof FireSlimeEntity.FireSlimeMoveControl;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.slime.getRandom().nextFloat() < 0.8F) {
                this.slime.getJumpControl().jump();
            }

            MoveControl movecontrol = this.slime.getMoveControl();
            if (movecontrol instanceof FireSlimeEntity.FireSlimeMoveControl slime$slimemovecontrol) {
                slime$slimemovecontrol.setWantedMovement(1.2D);
            }

        }
    }

    static class FireSlimeKeepOnJumpingGoal extends Goal {
        private final FireSlimeEntity slime;

        public FireSlimeKeepOnJumpingGoal(FireSlimeEntity pSlime) {
            this.slime = pSlime;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return !this.slime.isPassenger();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            MoveControl movecontrol = this.slime.getMoveControl();
            if (movecontrol instanceof FireSlimeEntity.FireSlimeMoveControl slime$slimemovecontrol) {
                slime$slimemovecontrol.setWantedMovement(1.0D);
            }

        }
    }

    static class FireSlimeAttackGoal extends Goal {
        private final FireSlimeEntity slime;
        private int growTiredTimer;

        public FireSlimeAttackGoal(FireSlimeEntity pSlime) {
            this.slime = pSlime;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = this.slime.getTarget();
            if (livingentity == null) {
                return false;
            } else {
                return !this.slime.canAttack(livingentity) ? false : this.slime.getMoveControl() instanceof FireSlimeEntity.FireSlimeMoveControl;
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.growTiredTimer = reducedTickDelay(300);
            super.start();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
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

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = this.slime.getTarget();
            if (livingentity != null) {
                this.slime.lookAt(livingentity, 10.0F, 10.0F);
            }

            MoveControl movecontrol = this.slime.getMoveControl();
            if (movecontrol instanceof FireSlimeEntity.FireSlimeMoveControl slime$slimemovecontrol) {
                slime$slimemovecontrol.setDirection(this.slime.getYRot(), this.slime.isDealsDamage());
            }

        }
    }

    private static class FireSlimeRandomDirectionGoal extends Goal {
        private final FireSlimeEntity slime;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public FireSlimeRandomDirectionGoal(FireSlimeEntity pSlime) {
            this.slime = pSlime;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return this.slime.getTarget() == null && (this.slime.onGround() || this.slime.isInWater() || this.slime.isInLava() || this.slime.hasEffect(MobEffects.LEVITATION)) && this.slime.getMoveControl() instanceof FireSlimeEntity.FireSlimeMoveControl;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.slime.getRandom().nextInt(60));
                this.chosenDegrees = (float)this.slime.getRandom().nextInt(360);
            }

            MoveControl movecontrol = this.slime.getMoveControl();
            if (movecontrol instanceof FireSlimeEntity.FireSlimeMoveControl slime$slimemovecontrol) {
                slime$slimemovecontrol.setDirection(this.chosenDegrees, false);
            }

        }
    }

    private static class FireSlimeMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final FireSlimeEntity fireSlime;
        private boolean isAggressive;

        public FireSlimeMoveControl(FireSlimeEntity fireSlime) {
            super(fireSlime);
            this.fireSlime = fireSlime;
            this.yRot = 180.0F * fireSlime.getYRot() / 3.1415927F;
        }

        public void setDirection(float f, boolean bl) {
            this.yRot = f;
            this.isAggressive = bl;
        }

        public void setWantedMovement(double d) {
            this.speedModifier = d;
            this.operation = Operation.MOVE_TO;
        }

        public void tick() {
            float n;
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
             {
                if (this.operation != Operation.MOVE_TO) {
                    this.mob.setZza(0.0F);
                }
                else {
                    this.operation = Operation.WAIT;
                    if (this.mob.onGround()) {
                        this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                        if (this.jumpDelay-- <= 0) {
                            this.jumpDelay = this.fireSlime.getJumpDelay();
                            if (this.isAggressive) {
                                this.jumpDelay /= 3;
                            }

                            this.fireSlime.getJumpControl().jump();
                            if (this.fireSlime.doPlayJumpSound()) {
                                this.fireSlime.playSound(this.fireSlime.getJumpSound(), this.fireSlime.getSoundVolume(), this.fireSlime.getSoundPitch());
                            }
                        } else {
                            this.fireSlime.xxa = 0.0F;
                            this.fireSlime.zza = 0.0F;
                            this.mob.setSpeed(0.0F);
                        }
                    } else {
                        this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    }
                }
            }
        }
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

    protected <E extends FireSlimeEntity> PlayState controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {
        return  event.setAndContinue(MOVE);
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
