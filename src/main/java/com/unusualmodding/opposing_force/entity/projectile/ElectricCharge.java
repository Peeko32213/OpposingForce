package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.Volt;
import com.unusualmodding.opposing_force.utils.CreeperExtension;
import com.unusualmodding.opposing_force.network.ElectricChargeSyncS2CPacket;
import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class ElectricCharge extends FrictionlessProjectile {

    private static final EntityDataAccessor<Float> CHARGE_SCALE = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> MAX_BOUNCES = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> TARGET = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> BOUNCY = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> QUASAR = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> STATIC_ATTRACTION = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(ElectricCharge.class, EntityDataSerializers.FLOAT);

    private final RandomSource randomSource = level.getRandom();
    private int bounces = 0;

    public ElectricCharge(EntityType<? extends FrictionlessProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public ElectricCharge(LivingEntity entity, Level level, double x, double y, double z) {
        super(OPEntities.ELECTRIC_CHARGE.get(), level, entity, x, y, z);
        this.setOwner(entity);
    }

    public ElectricCharge(Level level, double x, double y, double z, Vec3 movement) {
        super(OPEntities.ELECTRIC_CHARGE.get(), x, y, z, movement, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(BOUNCY, false);
        this.getEntityData().define(QUASAR, false);
        this.getEntityData().define(STATIC_ATTRACTION, false);
        this.getEntityData().define(CHARGE_SCALE, 1.0F);
        this.getEntityData().define(MAX_BOUNCES, 0);
        this.getEntityData().define(TARGET, -1);
        this.getEntityData().define(DAMAGE, 3.0F);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat("Damage", this.getChargeDamage());
        compoundTag.putFloat("ChargeScale", this.getChargeScale());
        compoundTag.putInt("MaxBounces", this.getMaxBounces());
        compoundTag.putBoolean("Bouncy", this.isBouncy());
        compoundTag.putBoolean("Quasar", this.isQuasar());
        compoundTag.putBoolean("StaticAttraction", this.isStaticAttraction());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setChargeDamage(compoundTag.getFloat("Damage"));
        this.setChargeScale(compoundTag.getFloat("ChargeScale"));
        this.setMaxBounces(compoundTag.getInt("MaxBounces"));
        this.setBouncy(compoundTag.getBoolean("Bouncy"));
        this.setQuasar(compoundTag.getBoolean("Quasar"));
        this.setStaticAttraction(compoundTag.getBoolean("StaticAttraction"));
    }

    public float getChargeDamage() {
        return this.entityData.get(DAMAGE);
    }

    public void setChargeDamage(float damage) {
        this.entityData.set(DAMAGE, damage);
    }

    public float getChargeScale() {
        return this.entityData.get(CHARGE_SCALE);
    }

    public void setChargeScale(float scale) {
        this.entityData.set(CHARGE_SCALE, scale);
    }

    public int getMaxBounces() {
        return this.entityData.get(MAX_BOUNCES);
    }

    public void setMaxBounces(int bounces) {
        this.entityData.set(MAX_BOUNCES, bounces);
    }

    public boolean isBouncy() {
        return this.entityData.get(BOUNCY);
    }

    public void setBouncy(boolean bounce) {
        this.entityData.set(BOUNCY, bounce);
    }

    public boolean isQuasar() {
        return this.entityData.get(QUASAR);
    }

    public void setQuasar(boolean quasar) {
        this.entityData.set(QUASAR, quasar);
    }

    public boolean isStaticAttraction() {
        return this.entityData.get(STATIC_ATTRACTION);
    }

    public void setStaticAttraction(boolean staticAttraction) {
        this.entityData.set(STATIC_ATTRACTION, staticAttraction);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 pos = this.position();

        if (level().isClientSide && isAlive()) {
            OpposingForce.PROXY.playWorldSound(this, (byte) 1);
        }

        this.spawnElectricParticles(this, 1 + randomSource.nextInt(3), 0, 12);
        this.hurtEntitiesAround(pos, (this.getChargeScale()) + 1.25F, this.getChargeDamage());

        if (this.level().getBlockState(this.blockPosition().below(0)).is(Blocks.WATER)) {
            this.spawnElectricParticles(this, 7 + randomSource.nextInt(5), 0, 16);
            if (!this.level().isClientSide) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 2.5F, 1.0F + (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F);
                this.hurtEntitiesAround(pos, this.getChargeScale() + 4.0F, this.getChargeDamage() * 1.25F);
                this.discard();
            }
        }

        if (tickCount > (this.isQuasar() ? 50 : 200) || this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            this.spawnElectricParticles(this, 4 + randomSource.nextInt(3), 0, 12);
            if (!this.level().isClientSide) {
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 2.5F, 1.0F + (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F);
                this.discard();
            }
        }

        if (this.isQuasar()) {
            AABB pull = this.getBoundingBox().inflate(8 + this.getChargeScale(), 8 + this.getChargeScale(), 8 + this.getChargeScale());
            for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, pull)) {
                if (entity != getOwner() && !(entity instanceof Volt)) {
                    Vec3 center = pull.getCenter();
                    float distance = (float) center.distanceTo(entity.position());
                    if (distance > (this.getChargeScale() * 3)) {
                        continue;
                    }
                    float f = 1 - distance / (this.getChargeScale() * 3);
                    float scale = f * f * f * f * 0.18F;

                    Vec3 diff = center.subtract(entity.position()).scale(scale);
                    entity.push(diff.x, diff.y, diff.z);
                    entity.resetFallDistance();
                }
            }
        }

        if (this.isStaticAttraction()) {
            if (!this.level().isClientSide()) {
                this.updateTarget();
            }
            Entity target = getTarget();
            if (target != null) {
                Vec3 targetVec = getVectorToTarget(target).scale(0.5F);
                Vec3 courseVec = getMotionVec();

                double courseLength = courseVec.length();
                double targetLength = targetVec.length();
                double totalLength = Math.sqrt(courseLength * courseLength + targetLength * targetLength);

                double dotProduct = courseVec.dot(targetVec) / (courseLength * targetLength);

                if (dotProduct > 0.5) {
                    Vec3 newMotion = courseVec.scale(courseLength / totalLength).add(targetVec.scale(courseLength / totalLength));
                    if (tickCount % 8 == 0) {
                        this.setDeltaMovement(newMotion.add(0, 0, 0));
                    }
                } else if (!this.level().isClientSide()) {
                    this.setTarget(null);
                }
            }
        }
    }

    @Override
    public void remove(Entity.RemovalReason reason) {
        OpposingForce.PROXY.clearSoundCacheFor(this);
        super.remove(reason);
    }

    public void spawnElectricParticles(ElectricCharge charge, int range, float yHeight, float particleMax) {
        Vec3 movement = charge.getDeltaMovement();

        double x = charge.getX() + movement.x;
        double y = charge.getY() + movement.y + (yHeight - 0.35F);
        double z = charge.getZ() + movement.z;

        int lightningLength = (int) (range + charge.getChargeScale() / 1.25F);
        float size = 0.08F;

        for (int i = 0; i < particleMax; i++) {
            if (!this.level().isClientSide) {
                if (this.isStaticAttraction()) {
                    ElectricChargeSyncS2CPacket packet = ElectricChargeSyncS2CPacket.builder()
                            .pos(x, y, z)
                            .range(lightningLength)
                            .size(size)
                            .color(0.3F + (randomSource.nextFloat() / 8), 0.8F + (randomSource.nextFloat() / 8), 0.5F + (randomSource.nextFloat() / 8), 1F)
                            .build();
                    OPNetwork.sendToClients(packet);
                } else if (this.isQuasar()) {
                    ElectricChargeSyncS2CPacket packet = ElectricChargeSyncS2CPacket.builder()
                            .pos(x, y, z)
                            .range(lightningLength)
                            .size(size)
                            .color(0.1F + randomSource.nextFloat(), 0.1F + randomSource.nextFloat(), 0.1F + randomSource.nextFloat(), 1F)
                            .build();
                    OPNetwork.sendToClients(packet);
                } else {
                    ElectricChargeSyncS2CPacket packet = ElectricChargeSyncS2CPacket.builder()
                            .pos(x, y, z)
                            .range(lightningLength)
                            .size(size)
                            .color(0.3F + (randomSource.nextFloat() / 8), 0.5F + (randomSource.nextFloat() / 8), 0.8F + (randomSource.nextFloat() / 8), 1F)
                            .build();
                    OPNetwork.sendToClients(packet);
                }
            }
        }
    }

    public boolean hurtEntitiesAround(Vec3 center, float radius, float damageAmount) {
        AABB aabb = new AABB(center.subtract(radius, radius, radius), center.add(radius, radius, radius));
        Entity shooter = this.getOwner();
        boolean flag = false;
        for (LivingEntity living : level().getEntitiesOfClass(LivingEntity.class, aabb, EntitySelector.NO_CREATIVE_OR_SPECTATOR)) {
            DamageSource damageSource = this.damageSources().source(OPDamageTypes.ELECTRIC);
            if (this.hasLineOfSight(living) && !living.is(this) && !living.isAlliedTo(this) && living.getType() != this.getType() && living.distanceToSqr(center.x, center.y, center.z) <= radius * radius) {
                if (!living.is(shooter)) {
                    if (living.hurt(damageSource, damageAmount)) {
                        this.spawnElectricParticles(this, 4 + randomSource.nextInt(3), 0, 12);
                        living.addEffect(new MobEffectInstance(OPEffects.ELECTRIFIED.get(), 200), shooter);
                        this.playSound(OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), 1.5F, 1.0F + (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F);
                        flag = true;
                    }
                }
                if (living instanceof Creeper creeper) {
                    ((CreeperExtension) creeper).opposingForce$setCharged(true);
                }
            }
        }
        return flag;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();

        if (!this.level().isClientSide) {
            this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), SoundSource.NEUTRAL, 1.5F, 1.0F + (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F);
            if (entity instanceof Creeper creeper) {
                ((CreeperExtension) creeper).opposingForce$setCharged(true);
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();

        if (!this.level().isClientSide) {
            if (this.isBouncy()) {
                Direction hitDirection = result.getDirection();
                Vector3f surfaceNormal = hitDirection.step();
                Vec3 velocity = this.getDeltaMovement();
                Vec3 newVel = new Vec3(velocity.toVector3f().reflect(surfaceNormal));
                bounce(newVel);
            } else {
                this.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE.get(), SoundSource.NEUTRAL, 2.5F, 1.0F + (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F);
                this.spawnElectricParticles(this, 1 + randomSource.nextInt(3), 0.3F, 12);
                this.discard();
            }
        }
    }

    private void bounce(Vec3 newVel) {
        bounces++;
        float conservedEnergy = 0.9F;
        newVel = newVel.scale(conservedEnergy);
        this.setDeltaMovement(newVel);

        if (!level().isClientSide) {
            this.hasImpulse = true;
            if (bounces >= 0) {
                this.playSound(OPSoundEvents.ELECTRIC_CHARGE_ZAP.get(), 1.5F, 1.0F + (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F);
            }
            if (bounces > getMaxBounces()) {
                this.playSound(OPSoundEvents.ELECTRIC_CHARGE_DISSIPATE.get(), 2.5F, 1.0F + (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F);
                this.spawnElectricParticles(this, 1 + randomSource.nextInt(3), 0.3F, 12);
                this.discard();
            }
        }
    }

    private void updateTarget() {
        Entity target = getTarget();

        if (target != null && !target.isAlive()) {
            target = null;
            this.setTarget(null);
        }

        if (target == null) {
            AABB positionBB = this.getBoundingBox().inflate(6 + this.getChargeScale(), 6 + this.getChargeScale(), 6 + this.getChargeScale());

            AABB targetBB = positionBB;

            Vec3 courseVec = getMotionVec().scale(5).yRot((float) -Math.PI / 6.0F);
            targetBB = targetBB.minmax(positionBB.move(courseVec));

            courseVec = getMotionVec().scale(5).yRot((float) -Math.PI / 6.0F);
            targetBB = targetBB.minmax(positionBB.move(courseVec));

            targetBB = targetBB.inflate(0, 5 * 0.5, 0);

            double closestDot = -1.0;
            Entity closestTarget = null;

            List<LivingEntity> entityList = this.level().getEntitiesOfClass(LivingEntity.class, targetBB);
            List<LivingEntity> monsters = entityList.stream().filter(l -> l instanceof Monster).toList();

            if (!monsters.isEmpty()) {
                for (LivingEntity monster : monsters) {
                    if (((Monster) monster).getTarget() == this.getOwner()) {
                        setTarget(monster);
                        return;
                    }
                }
                for (LivingEntity monster : monsters) {
                    if (monster instanceof NeutralMob) continue;
                    if (monster.hasLineOfSight(this)) {
                        setTarget(monster);
                        return;
                    }
                }
            }

            for (LivingEntity living : entityList) {
                if (!living.hasLineOfSight(this)) continue;
                if (living == this.getOwner()) continue;
                if (getOwner() != null && living instanceof TamableAnimal animal && animal.getOwner() == this.getOwner()) continue;

                Vec3 motionVec = getMotionVec().normalize();
                Vec3 targetVec = getVectorToTarget(living).normalize();
                double dot = motionVec.dot(targetVec);

                if (dot > Math.max(closestDot, 0.5)) {
                    closestDot = dot;
                    closestTarget = living;
                }
            }

            if (closestTarget != null) {
                setTarget(closestTarget);
            }
        }
    }

    private Vec3 getMotionVec() {
        return new Vec3(this.getDeltaMovement().x(), this.getDeltaMovement().y(), this.getDeltaMovement().z());
    }

    private Vec3 getVectorToTarget(Entity target) {
        return new Vec3(target.getX() - this.getX(), (target.getY() + target.getEyeHeight()) - this.getY(), target.getZ() - this.getZ());
    }

    @Nullable
    private Entity getTarget() {
        return this.level().getEntity(this.getEntityData().get(TARGET));
    }

    private void setTarget(@Nullable Entity entity) {
        this.getEntityData().set(TARGET, entity == null ? -1 : entity.getId());
    }

    @Override
    protected float getEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 0;
    }
}