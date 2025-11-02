package com.unusualmodding.opposing_force.entity.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public abstract class SummonableMonster extends TameableMonster {

    private static final EntityDataAccessor<Integer> LIFE_TICKS = SynchedEntityData.defineId(SummonableMonster.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FROM_SUMMON = SynchedEntityData.defineId(SummonableMonster.class, EntityDataSerializers.BOOLEAN);

    protected SummonableMonster(EntityType<? extends SummonableMonster> entityType, Level level) {
        super(entityType, level);
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

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LIFE_TICKS, -1);
        this.entityData.define(FROM_SUMMON, false);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("LifeTicks", this.getLifeTicks());
        compoundTag.putBoolean("FromSummon", this.isFromSummon());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setLifeTicks(compoundTag.getInt("LifeTicks"));
        this.setFromSummon(compoundTag.getBoolean("FromSummon"));
    }

    public int getLifeTicks() {
        return this.entityData.get(LIFE_TICKS);
    }

    public void setLifeTicks(int limitedTicks) {
        this.entityData.set(LIFE_TICKS, limitedTicks);
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
