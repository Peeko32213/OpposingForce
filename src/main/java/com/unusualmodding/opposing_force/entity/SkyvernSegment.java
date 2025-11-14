package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SkyvernSegment extends Entity {

    private static final EntityDataAccessor<Optional<UUID>> HEAD_ENTITY_UUID = SynchedEntityData.defineId(SkyvernSegment.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> HEAD_ENTITY_ID = SynchedEntityData.defineId(SkyvernSegment.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> FRONT_ENTITY_UUID = SynchedEntityData.defineId(SkyvernSegment.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> FRONT_ENTITY_ID = SynchedEntityData.defineId(SkyvernSegment.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Optional<UUID>> BACK_ENTITY_UUID = SynchedEntityData.defineId(SkyvernSegment.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> BACK_ENTITY_ID = SynchedEntityData.defineId(SkyvernSegment.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(SkyvernSegment.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_ARMS = SynchedEntityData.defineId(SkyvernSegment.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> OFFSET_ARMS = SynchedEntityData.defineId(SkyvernSegment.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Vector3f> TARGET_POS = SynchedEntityData.defineId(SkyvernSegment.class, EntityDataSerializers.VECTOR3);

    public boolean renderHurtFlag = false;

    public final AnimationState flying1AnimationState = new AnimationState();
    public final AnimationState flying2AnimationState = new AnimationState();
    public final AnimationState attackStartAnimationState = new AnimationState();
    public final AnimationState attackingAnimationState = new AnimationState();
    public final AnimationState attackEndAnimationState = new AnimationState();
    public final AnimationState roll1AnimationState = new AnimationState();

    private int attackStartTicks;
    private int attackEndTicks;

    private int lSteps;
    private double lx;
    private double ly;
    private double lz;
    private double lyr;
    private double lxr;
    private double lxd;
    private double lyd;
    private double lzd;

    public SkyvernSegment(EntityType entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(HEAD_ENTITY_UUID, Optional.empty());
        this.entityData.define(HEAD_ENTITY_ID, -1);
        this.entityData.define(FRONT_ENTITY_UUID, Optional.empty());
        this.entityData.define(FRONT_ENTITY_ID, -1);
        this.entityData.define(BACK_ENTITY_UUID, Optional.empty());
        this.entityData.define(BACK_ENTITY_ID, -1);
        this.entityData.define(INDEX, 0);
        this.entityData.define(HAS_ARMS, false);
        this.entityData.define(OFFSET_ARMS, false);
        this.entityData.define(TARGET_POS, new Vector3f((float) this.getX(), (float) this.getY(), (float) this.getZ()));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        if (this.getHeadUUID() != null) {
            compoundTag.putUUID("HeadUUID", this.getHeadUUID());
        }
        if (this.getFrontEntityUUID() != null) {
            compoundTag.putUUID("FrontUUID", this.getFrontEntityUUID());
        }
        if (this.getBackEntityUUID() != null) {
            compoundTag.putUUID("BackUUID", this.getBackEntityUUID());
        }
        compoundTag.putInt("Index", this.getIndex());
        compoundTag.putBoolean("HasArms", this.hasArms());
        compoundTag.putBoolean("HasOffsetArms", this.hasOffsetArms());
        compoundTag.putFloat("TargetPosX", this.getTargetPos().x);
        compoundTag.putFloat("TargetPosY", this.getTargetPos().y);
        compoundTag.putFloat("TargetPosZ", this.getTargetPos().z);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.hasUUID("HeadUUID")) {
            this.setHeadUUID(compoundTag.getUUID("HeadUUID"));
        }
        if (compoundTag.hasUUID("FrontUUID")) {
            this.setFrontEntityUUID(compoundTag.getUUID("FrontUUID"));
        }
        if (compoundTag.hasUUID("BackUUID")) {
            this.setBackEntityUUID(compoundTag.getUUID("BackUUID"));
        }
        this.setIndex(compoundTag.getInt("Index"));
        this.setHasArms(compoundTag.getBoolean("HasArms"));
        this.setHasOffsetArms(compoundTag.getBoolean("HasOffsetArms"));
        this.setTargetPos(new Vector3f(
                compoundTag.getFloat("TargetPosX"),
                compoundTag.getFloat("TargetPosY"),
                compoundTag.getFloat("TargetPosZ")
        ));
    }

    public Vector3f getTargetPos() {
        return this.entityData.get(TARGET_POS);
    }

    public void setTargetPos(Vector3f vec) {
        this.entityData.set(TARGET_POS, vec);
    }

    public boolean hasArms() {
        return this.entityData.get(HAS_ARMS);
    }

    public void setHasArms(boolean hasArms) {
        this.entityData.set(HAS_ARMS, hasArms);
    }

    public boolean hasOffsetArms() {
        return this.entityData.get(OFFSET_ARMS);
    }

    public void setHasOffsetArms(boolean offsetArms) {
        this.entityData.set(OFFSET_ARMS, offsetArms);
    }

    @Nullable
    public UUID getBackEntityUUID() {
        return this.entityData.get(BACK_ENTITY_UUID).orElse(null);
    }

    public void setBackEntityUUID(@Nullable UUID uniqueId) {
        this.entityData.set(BACK_ENTITY_UUID, Optional.ofNullable(uniqueId));
    }

    @Nullable
    public UUID getHeadUUID() {
        return this.entityData.get(HEAD_ENTITY_UUID).orElse(null);
    }

    public void setHeadUUID(@Nullable UUID uniqueId) {
        this.entityData.set(HEAD_ENTITY_UUID, Optional.ofNullable(uniqueId));
    }

    @Nullable
    public UUID getFrontEntityUUID() {
        return this.entityData.get(FRONT_ENTITY_UUID).orElse(null);
    }

    public void setFrontEntityUUID(@Nullable UUID uniqueId) {
        this.entityData.set(FRONT_ENTITY_UUID, Optional.ofNullable(uniqueId));
    }

    public Entity getHeadEntity() {
        if (!level().isClientSide) {
            UUID id = getHeadUUID();
            return id == null ? null : ((ServerLevel) level()).getEntity(id);
        } else {
            int id = this.entityData.get(HEAD_ENTITY_ID);
            return id == -1 ? null : level().getEntity(id);
        }
    }

    public Entity getFrontEntity() {
        if (!level().isClientSide) {
            UUID id = getFrontEntityUUID();
            return id == null ? null : ((ServerLevel) level()).getEntity(id);
        } else {
            int id = this.entityData.get(FRONT_ENTITY_ID);
            return id == -1 ? null : level().getEntity(id);
        }
    }

    public Entity getBackEntity() {
        if (!level().isClientSide) {
            UUID id = getBackEntityUUID();
            return id == null ? null : ((ServerLevel) level()).getEntity(id);
        } else {
            int id = this.entityData.get(BACK_ENTITY_ID);
            return id == -1 ? null : level().getEntity(id);
        }
    }

    public int getIndex() {
        return this.entityData.get(INDEX);
    }

    public void setIndex(int i) {
        this.entityData.set(INDEX, i);
    }

    public static void createSkyvernSegments(Skyvern skyvern, int count) {
        SkyvernSegment prev = null;
        for (int i = 0; i < count; i++) {
            SkyvernSegment segment = new SkyvernSegment(OPEntities.SKYVERN_SEGMENT.get(), skyvern.level());
            segment.setHeadUUID(skyvern.getUUID());
            segment.setFrontEntityUUID(prev == null ? skyvern.getUUID() : prev.getUUID());
            if (prev != null) {
                prev.setBackEntityUUID(segment.getUUID());
            }
            segment.setIndex(i);
            segment.setPos(segment.getIdealPosition(prev == null ? skyvern : prev));
            if (i >= 2 && i < count - 3 && (i - 2) % 4 == 0) {
                segment.setHasArms(true);
                if (skyvern.level().getRandom().nextBoolean()) {
                    segment.setHasOffsetArms(true);
                }
            }
            skyvern.level().addFreshEntity(segment);
            prev = segment;
        }
    }

    public Vec3 getIdealPosition(@Nullable Entity parent) {
        Entity head = getHeadEntity();
        Entity front = parent == null ? getFrontEntity() : parent;
        if (front != null) {
            float backStretch = -0.66F;
            if (head != null) {
                float headDelta = Mth.clamp((float) head.getDeltaMovement().length(), 0.0F, 1.0F);
                if (front == head) {
                    backStretch -= 0.15F;
                }
                backStretch *= 1.0F - headDelta * 0.3F;
            }
            Vec3 offsetFromParent = new Vec3(0.0F, 0.0F, backStretch).xRot(-(float) Math.toRadians(front.xRotO)).yRot(-(float) Math.toRadians(front.yRotO));
            return front.position().add(offsetFromParent);
        } else {
            return this.position();
        }
    }

    @Override
    public boolean isPickable() {
        Entity head = this.getHeadEntity();
        return head != null && head.isPickable();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity head = this.getHeadEntity();
        if (!this.isInvulnerableTo(source) && head != null) {
            head.hurt(source, amount);
        }
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return super.isInvulnerableTo(damageSource) || damageSource.is(DamageTypes.IN_WALL) || damageSource.is(DamageTypes.FALL);
    }

    @Override
    public void tick() {
        super.tick();
        Entity head = getHeadEntity();
        Entity front = getFrontEntity();
        Entity back = getBackEntity();
        if (level().isClientSide) {
            this.setupAnimationStates();
            if (head instanceof Skyvern skyvern) {
                this.renderHurtFlag = skyvern.hurtTime > 0 || skyvern.deathTime > 0;
            }
            if (this.lSteps > 0) {
                double x = this.getX() + (this.lx - this.getX()) / (double) this.lSteps;
                double y = this.getY() + (this.ly - this.getY()) / (double) this.lSteps;
                double z = this.getZ() + (this.lz - this.getZ()) / (double) this.lSteps;
                Vec3 vec3 = new Vec3(x, y, z);
                double lerpRot = Mth.wrapDegrees(this.lyr - (double) this.getYRot());
                this.setYRot(this.getYRot() + (float) lerpRot / (float) this.lSteps);
                this.setXRot(this.getXRot() + (float) (this.lxr - (double) this.getXRot()) / (float) this.lSteps);
                this.lSteps--;
                this.setTargetPos(vec3.toVector3f());
                this.setPos(new Vec3(getTargetPos().x(), getTargetPos().y(), getTargetPos().z()));
            } else {
                this.reapplyPosition();
            }
        }
        else {
            this.entityData.set(HEAD_ENTITY_ID, head != null ? head.getId() : -1);
            this.entityData.set(FRONT_ENTITY_ID, front != null ? front.getId() : -1);
            this.entityData.set(BACK_ENTITY_ID, back != null ? back.getId() : -1);

            if (front == null || head == null) {
                if (tickCount > 3) {
                    this.discard();
                }
            } else {
                float maxDistFromFront = 0.66F;
                Vec3 ideal = getIdealPosition(front);
                Vec3 distVec = ideal.subtract(this.position());
                float extraLength = (float) Math.max(distVec.length() - maxDistFromFront, 0.0F);
                Vec3 vec31 = distVec.length() > 1.0F ? distVec.normalize().scale(1.0F + extraLength) : distVec;
                Vec3 vec32 = this.position().add(vec31.scale(0.66F));
                this.setTargetPos(vec32.toVector3f());
                this.setPos(new Vec3(getTargetPos().x(), getTargetPos().y(), getTargetPos().z()));
                Vec3 frontsBack = front.position().add(new Vec3(0.0F, 0.0F, 0.66F).xRot(-(float) Math.toRadians(front.getXRot())).yRot(-(float) Math.toRadians(front.getYRot())));
                double x = frontsBack.x - this.getX();
                double y = frontsBack.y - this.getY();
                double z = frontsBack.z - this.getZ();
                double sqrt = Math.sqrt(x * x + z * z);
                float xRot = Mth.wrapDegrees((float) (-(Mth.atan2(y, sqrt) * (double) (180F / (float) Math.PI))));
                float yRot = Mth.wrapDegrees((float) (Mth.atan2(z, x) * (double) (180F / (float) Math.PI)) - 90);
                this.setXRot(Mth.approachDegrees(this.getXRot(), xRot, 6));
                this.setYRot(Mth.approachDegrees(this.getYRot(), yRot, 8));
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.9F));
//                this.reapplyPosition();
            }
        }
        this.pushEntities();

        if (head != null) {
            this.setPose(head.getPose());
        }

        if (attackStartTicks > 0) attackStartTicks--;
        if (attackEndTicks > 0) attackEndTicks--;
        if (attackStartTicks == 0 && this.getPose() == OPPoses.ATTACK_START.get()) this.setPose(OPPoses.ATTACKING.get());
        if (attackEndTicks == 0 && this.getPose() == OPPoses.ATTACK_END.get()) this.setPose(Pose.STANDING);
    }

    public void setupAnimationStates() {
        if (attackStartTicks == 0 && this.attackStartAnimationState.isStarted()) this.attackStartAnimationState.stop();
        if (attackEndTicks == 0 && this.attackEndAnimationState.isStarted()) this.attackEndAnimationState.stop();
        this.flying1AnimationState.animateWhen(!this.hasOffsetArms() && this.getPose() == Pose.STANDING, this.tickCount);
        this.flying2AnimationState.animateWhen(this.hasOffsetArms() && this.getPose() == Pose.STANDING, this.tickCount);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_POSE.equals(entityDataAccessor)) {
            if (this.getPose() == OPPoses.ATTACK_START.get()) {
                this.flying1AnimationState.stop();
                this.flying2AnimationState.stop();
                this.attackStartAnimationState.start(this.tickCount);
                this.attackStartTicks = 10;
            }
            else if (this.getPose() == OPPoses.ATTACKING.get()) {
                this.flying1AnimationState.stop();
                this.flying2AnimationState.stop();
                this.attackStartAnimationState.stop();
                this.attackingAnimationState.start(this.tickCount);
            }
            else if (this.getPose() == OPPoses.ATTACK_END.get()) {
                this.flying1AnimationState.stop();
                this.flying2AnimationState.stop();
                this.attackingAnimationState.stop();
                this.attackEndAnimationState.start(this.tickCount);
                this.attackEndTicks = 10;
            }
            else if (this.getPose() == OPPoses.ROLLING.get()) {
                this.flying1AnimationState.stop();
                this.flying2AnimationState.stop();
                this.attackingAnimationState.stop();
                this.attackEndAnimationState.stop();
                this.attackStartAnimationState.stop();
                this.roll1AnimationState.start(this.tickCount);
            }
            else if (this.getPose() == Pose.STANDING) {
                this.attackStartAnimationState.stop();
                this.attackingAnimationState.stop();
                this.attackEndAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(entityDataAccessor);
    }

    private void pushEntities() {
        if (this.level().isClientSide()) {
            this.level().getEntities(EntityTypeTest.forClass(Player.class), this.getBoundingBox(), EntitySelector.pushableBy(this)).forEach(this::push);
        } else {
            List<Entity> list = this.level().getEntities(this, this.getBoundingBox(), EntitySelector.pushableBy(this));
            if (!list.isEmpty()) {
                int i = this.level().getGameRules().getInt(GameRules.RULE_MAX_ENTITY_CRAMMING);
                if (i > 0 && list.size() > i - 1 && this.random.nextInt(4) == 0) {
                    int j = 0;
                    for (Entity entity : list) {
                        if (!entity.isPassenger()) {
                            ++j;
                        }
                    }
                    if (j > i - 1) {
                        this.hurt(this.damageSources().cramming(), 6.0F);
                    }
                }
                for (Entity entity : list) {
                    this.push(entity);
                }
            }
        }
    }

    @Override
    public void push(@NotNull Entity entity) {
        if (!this.isPassengerOfSameVehicle(entity) && !(entity instanceof SkyvernSegment)) {
            if (!entity.noPhysics && !this.noPhysics) {
                double d0 = entity.getX() - this.getX();
                double d1 = entity.getZ() - this.getZ();
                double d2 = Mth.absMax(d0, d1);
                if (d2 >= (double) 0.01F) {
                    d2 = Math.sqrt(d2);
                    d0 /= d2;
                    d1 /= d2;
                    double d3 = 1.0D / d2;
                    if (d3 > 1.0D) {
                        d3 = 1.0D;
                    }
                    d0 *= d3;
                    d1 *= d3;
                    d0 *= 0.05F;
                    d1 *= 0.05F;
                    if (!entity.isVehicle() && entity.isPushable()) {
                        entity.push(d0, 0.0D, d1);
                    }
                }
            }
        }
    }

    @Override
    public void lerpTo(double x, double y, double z, float yr, float xr, int steps, boolean b) {
        this.lx = x;
        this.ly = y;
        this.lz = z;
        this.lyr = yr;
        this.lxr = xr;
        this.lSteps = steps;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    @Override
    public void lerpMotion(double lerpX, double lerpY, double lerpZ) {
        this.lxd = lerpX;
        this.lyd = lerpY;
        this.lzd = lerpZ;
        this.setDeltaMovement(this.lxd, this.lyd, this.lzd);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return Math.sqrt(distance) < 1024.0D;
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return super.getBoundingBoxForCulling().inflate(8.0F);
    }

    @Override
    public boolean shouldBeSaved() {
        return (this.getRemovalReason() == null || this.getRemovalReason().shouldSave()) && !this.isPassenger();
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(OPItems.SKYVERN_SPAWN_EGG.get());
    }
}