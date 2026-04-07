package com.unusualmodding.opposing_force.entity.base;

import com.unusualmodding.opposing_force.entity.ai.control.OPBodyRotationControl;
import com.unusualmodding.opposing_force.entity.ai.control.OPLookControl;
import com.unusualmodding.opposing_force.entity.ai.control.OPMoveControl;
import com.unusualmodding.opposing_force.entity.ai.navigation.SmoothGroundPathNavigation;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import com.unusualmodding.opposing_force.utils.SmoothAnimationState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class OPMonster extends TameableMonster {

    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(OPMonster.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(OPMonster.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ELITE = SynchedEntityData.defineId(OPMonster.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> LIFE_TICKS = SynchedEntityData.defineId(OPMonster.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FROM_SUMMON = SynchedEntityData.defineId(OPMonster.class, EntityDataSerializers.BOOLEAN);

    private float tailYaw;
    private float prevTailYaw;

    public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();

    protected OPMonster(EntityType<? extends TameableMonster> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new OPMoveControl(this);
        this.lookControl = new OPLookControl(this);
    }

    // Navigation
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new OPBodyRotationControl(this);
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new SmoothGroundPathNavigation(this, level);
    }

    public boolean refuseToMove() {
        return false;
    }

    public boolean refuseToLook() {
        return false;
    }

    // Summon
    private float getSummonDamage() {
        if (this.getOwner() != null && this.getOwner().getAttributes().hasAttribute(OPAttributes.SUMMON_DAMAGE.get())) {
            return 1 + (float) this.getOwner().getAttributeValue(OPAttributes.SUMMON_DAMAGE.get());
        }
        else return 1;
    }

    private int getSummonDuration() {
        if (this.getOwner() != null && this.getOwner().getAttributes().hasAttribute(OPAttributes.SUMMON_DURATION.get())) {
            return (int) (1 + this.getOwner().getAttributeValue(OPAttributes.SUMMON_DURATION.get()));
        }
        else return 1;
    }

    @Override
    protected void dropFromLootTable(@NotNull DamageSource source, boolean drops) {
        if (!this.isFromSummon()) super.dropFromLootTable(source, drops);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE) + this.getSummonDamage();
        float knockback = (float) this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (entity instanceof LivingEntity livingEntity) {
            damage += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), livingEntity.getMobType());
            knockback += (float) EnchantmentHelper.getKnockbackBonus(this);
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            entity.setSecondsOnFire(i * 4);
        }

        boolean didHurt = entity.hurt(this.damageSources().mobAttack(this), damage);
        if (didHurt) {
            if (knockback > 0.0F && entity instanceof LivingEntity livingEntity) {
                livingEntity.knockback(knockback * 0.5F, Mth.sin(this.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(this.getYRot() * ((float) Math.PI / 180F)));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }
            if (entity instanceof Player player) {
                this.maybeDisableShield(player, this.getMainHandItem(), player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
            }
            this.doEnchantDamageEffects(this, entity);
            this.setLastHurtMob(entity);
        }

        return didHurt;
    }

    public void copyTarget(LivingEntity entity) {
        LivingEntity priorTarget = this.getTarget();
        if (priorTarget == null || !priorTarget.isAlive()) {
            LivingEntity target = null;
            if (entity.getLastHurtMob() != null) {
                target = entity.getLastHurtMob();
            } else if (entity.getLastHurtByMob() != null) {
                target = entity.getLastHurtByMob();
            }
            if (target != null && target.isAlive() && !target.isAlliedTo(entity) && !target.is(entity) && !target.isAlliedTo(this)) {
                this.setTarget(target);
            }
        }
    }

    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        Vec3 vec3 = (new Vec3(x, y, z)).normalize().add(this.random.triangle(0.0D, 0.0172275D * (double) inaccuracy), this.random.triangle(0.0D, 0.0172275D * (double) inaccuracy), this.random.triangle(0.0D, 0.0172275D * (double) inaccuracy)).scale((double) velocity);
        this.setDeltaMovement(vec3);
        double horizontalDistance = vec3.horizontalDistance();
        this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)));
        this.setXRot((float) (Mth.atan2(vec3.y, horizontalDistance) * (double) (180F / (float) Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    public void shootFromRotation(Entity shooter, float x, float y, float z, float pVelocity, float pInaccuracy) {
        float f = -Mth.sin(y * ((float) Math.PI / 180F)) * Mth.cos(x * ((float) Math.PI / 180F));
        float f1 = -Mth.sin((x + z) * ((float) Math.PI / 180F));
        float f2 = Mth.cos(y * ((float) Math.PI / 180F)) * Mth.cos(x * ((float) Math.PI / 180F));
        this.shoot(f, f1, f2, pVelocity, pInaccuracy);
        Vec3 vec3 = shooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, shooter.onGround() ? 0.0D : vec3.y, vec3.z));
    }

    // Elite
    protected int getEliteSpawnChance() {
        return 50;
    }

    protected void setEliteStats(Mob entity) {
        entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(entity.getAttributeBaseValue(Attributes.MAX_HEALTH) * 1.5F);
        entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(entity.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) * 1.25F);
        entity.setHealth(entity.getMaxHealth());
    }

    @Override
    public void tick () {
        super.tick();
        this.tickTailYaw();
        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }
    }

    // Animation
    public void setupAnimationStates() {
    }

    @Override
    public void calculateEntityAnimation(boolean flying) {
        float pos = (float) Mth.length(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        float speed = Math.min(pos * 10.0F, 1.0F);
        this.walkAnimation.update(speed, 0.4F);
    }

    // Tail yaw
    public void tickTailYaw() {
        this.prevTailYaw = this.tailYaw;
        this.tailYaw += (-(this.yBodyRot - this.yBodyRotO) - this.tailYaw) * 0.15F;
    }

    public float getTailYaw(float partialTick) {
        return (prevTailYaw + (tailYaw - prevTailYaw) * partialTick);
    }

    // Data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(RUNNING, false);
        this.entityData.define(ELITE, false);
        this.entityData.define(LIFE_TICKS, -1);
        this.entityData.define(FROM_SUMMON, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Elite", this.isElite());
        compoundTag.putInt("LifeTicks", this.getLifeTicks());
        compoundTag.putBoolean("FromSummon", this.isFromSummon());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setElite(compoundTag.getBoolean("Elite"));
        this.setLifeTicks(compoundTag.getInt("LifeTicks"));
        this.setFromSummon(compoundTag.getBoolean("FromSummon"));
    }

    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }

    public void setRunning(boolean running) {
        this.entityData.set(RUNNING, running);
    }

    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
    }

    public void setAttackState(int state) {
        this.entityData.set(ATTACK_STATE, state);
    }

    public boolean isElite() {
        return this.entityData.get(ELITE);
    }

    public void setElite(boolean elite) {
        this.entityData.set(ELITE, elite);
    }

    public int getLifeTicks() {
        return this.entityData.get(LIFE_TICKS);
    }

    public void setLifeTicks(int limitedTicks) {
        this.entityData.set(LIFE_TICKS, limitedTicks);
    }

    public int getMaxLifeTicks() {
        return 250 * this.getSummonDuration();
    }

    public boolean isFromSummon() {
        return this.entityData.get(FROM_SUMMON);
    }

    public void setFromSummon(boolean fromSummon) {
        this.entityData.set(FROM_SUMMON, fromSummon);
    }

    @Override
    public boolean canOwnerCommand(Player owner) {
        return !this.isFromSummon();
    }
}
