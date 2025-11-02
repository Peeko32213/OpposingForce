package com.unusualmodding.opposing_force.entity.base;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public abstract class TameableMonster extends Monster implements OwnableEntity {

    private static final EntityDataAccessor<Byte> DATA_FLAGS = SynchedEntityData.defineId(TameableMonster.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(TameableMonster.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> COMMAND = SynchedEntityData.defineId(TameableMonster.class, EntityDataSerializers.INT);
    private boolean orderedToSit;

    protected TameableMonster(EntityType<? extends TameableMonster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, hand);
        InteractionResult type = super.mobInteract(player, hand);
        if (!interactionresult.consumesAction() && !type.consumesAction()) {
            if (this.isTame() && this.isOwnedBy(player)) {
                if (this.canOwnerCommand(player)) {
                    this.setCommand(this.getCommand() + 1);
                    if (this.getCommand() == 3) {
                        this.setCommand(0);
                    }
                    player.displayClientMessage(Component.translatable("entity.opposing_force.all.command_" + this.getCommand(), this.getName()), true);
                    boolean sit = this.getCommand() == 1;
                    this.setOrderedToSit(sit);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return type;
    }

    @Override
    public void travel(@NotNull Vec3 travelVec) {
        if (this.isInSittingPose()) {
            if (this.getNavigation().getPath() != null) {
                this.getNavigation().stop();
            }
            travelVec = Vec3.ZERO;
        }
        super.travel(travelVec);
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
        return (this.entityData.get(DATA_FLAGS) & 4) != 0;
    }

    public void setTame(boolean tamed) {
        byte b = this.entityData.get(DATA_FLAGS);
        if (tamed) {
            this.entityData.set(DATA_FLAGS, (byte) (b | 4));
        } else {
            this.entityData.set(DATA_FLAGS, (byte) (b & -5));
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
                if (entity instanceof TamableAnimal tamableAnimal) {
                    return tamableAnimal.isOwnedBy(livingentity);
                }
                else if (entity instanceof TameableMonster tameableMonster) {
                    return tameableMonster.isOwnedBy(livingentity);
                }
            }
            if (livingentity != null) {
                return livingentity.isAlliedTo(entity);
            }
        }
        return super.isAlliedTo(entity);
    }

    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        return true;
    }

    @Override
    public boolean isPreventingPlayerRest(@NotNull Player player) {
        return !this.isTame();
    }

    @Override
    public boolean canBeLeashed(@NotNull Player player) {
        return !this.isLeashed() && this.isTame();
    }

    protected void spawnTamingParticles(boolean tamed) {
        ParticleOptions particleoptions = ParticleTypes.HEART;
        if (!tamed) {
            particleoptions = ParticleTypes.SMOKE;
        }
        for (int i = 0; i < 7; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.level().addParticle(particleoptions, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    public void handleEntityEvent(byte id) {
        if (id == 7) {
            this.spawnTamingParticles(true);
        } else if (id == 6) {
            this.spawnTamingParticles(false);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OWNER_UUID, Optional.empty());
        this.entityData.define(DATA_FLAGS, (byte) 0);
        this.entityData.define(COMMAND, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Sitting", this.orderedToSit);
        compoundTag.putInt("Command", this.getCommand());
        if (this.getOwnerUUID() != null) {
            compoundTag.putUUID("Owner", this.getOwnerUUID());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.orderedToSit = compoundTag.getBoolean("Sitting");
        this.setInSittingPose(this.orderedToSit);
        this.setCommand(compoundTag.getInt("Command"));
        UUID uuid;
        if (compoundTag.hasUUID("Owner")) {
            uuid = compoundTag.getUUID("Owner");
        } else {
            String owner = compoundTag.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), owner);
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

    public boolean isInSittingPose() {
        return ((this.entityData.get(DATA_FLAGS) & 1) != 0) && !(this.isVehicle() || this.isPassenger());
    }

    public void setInSittingPose(boolean sittingPose) {
        byte flags = this.entityData.get(DATA_FLAGS);
        if (sittingPose) {
            this.entityData.set(DATA_FLAGS, (byte) (flags | 1));
        } else {
            this.entityData.set(DATA_FLAGS, (byte) (flags & -2));
        }
    }

    public boolean isOrderedToSit() {
        return this.orderedToSit;
    }

    public void setOrderedToSit(boolean orderedToSit) {
        this.orderedToSit = orderedToSit;
    }

    public int getCommand() {
        return this.entityData.get(COMMAND);
    }

    public void setCommand(int command) {
        this.entityData.set(COMMAND, command);
    }

    public boolean canOwnerCommand(Player owner) {
        return true;
    }
}
