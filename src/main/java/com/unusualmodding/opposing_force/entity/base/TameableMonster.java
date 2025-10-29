package com.unusualmodding.opposing_force.entity.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public abstract class TameableMonster extends Monster implements OwnableEntity {

    private static final EntityDataAccessor<Byte> TAME_FLAGS = SynchedEntityData.defineId(TameableMonster.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(TameableMonster.class, EntityDataSerializers.OPTIONAL_UUID);

    protected int limitedLifeTicks = 0;

    protected TameableMonster(EntityType<? extends TameableMonster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.isTame();
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.isTame();
    }

    public boolean isTame() {
        return (this.entityData.get(TAME_FLAGS) & 4) != 0;
    }

    public void setTame(boolean tamed) {
        byte b = this.entityData.get(TAME_FLAGS);
        if (tamed) {
            this.entityData.set(TAME_FLAGS, (byte) (b | 4));
        } else {
            this.entityData.set(TAME_FLAGS, (byte) (b & -5));
        }
    }

    public void tame(Player player) {
        this.setTame(true);
        this.setOwnerUUID(player.getUUID());
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity entity) {
        return !this.isOwnedBy(entity) && super.canAttack(entity);
    }

    public boolean isOwnedBy(LivingEntity entity) {
        return entity == this.getOwner();
    }

    @Override
    public Team getTeam() {
        if (this.isTame()) {
            LivingEntity livingentity = this.getOwner();
            if (livingentity != null) {
                return livingentity.getTeam();
            }
        }
        return super.getTeam();
    }

    @Override
    public boolean isAlliedTo(@NotNull Entity entity) {
        if (this.isTame()) {
            LivingEntity livingentity = this.getOwner();
            if (entity == livingentity) {
                return true;
            }
            if (livingentity != null) {
                return livingentity.isAlliedTo(entity);
            }
        }
        return super.isAlliedTo(entity);
    }

    @Override
    public boolean isPreventingPlayerRest(@NotNull Player player) {
        return !this.isTame();
    }

    @Override
    public boolean canBeLeashed(@NotNull Player player) {
        return !this.isLeashed() && this.isTame();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OWNER_UUID, Optional.empty());
        this.entityData.define(TAME_FLAGS, (byte) 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("LifeTicks", this.limitedLifeTicks);
        if (this.getOwnerUUID() != null) {
            compoundTag.putUUID("Owner", this.getOwnerUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.limitedLifeTicks = compoundTag.getInt("LifeTicks");

        UUID uuid;
        if (compoundTag.hasUUID("Owner")) {
            uuid = compoundTag.getUUID("Owner");
        } else {
            String s = compoundTag.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }

        if (uuid != null) {
            try {
                this.setOwnerUUID(uuid);
                this.setTame(true);
            } catch (Throwable var4) {
                this.setTame(false);
            }
        }
    }

    @Override
    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID pUuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(pUuid));
    }
}
