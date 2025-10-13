package com.unusualmodding.opposing_force.entity.projectile;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public abstract class ThrowableProjectile extends AbstractArrow implements ItemSupplier {

    public ThrowableProjectile(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    public ThrowableProjectile(EntityType<? extends ThrowableProjectile> type, Level level, LivingEntity shooter) {
        super(type, shooter, level);
    }

    public ThrowableProjectile(EntityType<? extends ThrowableProjectile> type, Level level, double x, double y, double z) {
        super(type, x, y, z, level);
    }

    protected DamageSource getDamageSource(Entity shooter) {
        return this.damageSources().arrow(this, shooter);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        Entity shooter = this.getOwner();

        float motion = (float) this.getDeltaMovement().length();
        int damage = Mth.ceil(Mth.clamp((double) motion * 0.75F * this.getBaseDamage(), 0.0D, 2.147483647E9D));

        DamageSource damagesource = this.getDamageSource(shooter);
        if (shooter instanceof LivingEntity living) {
            living.setLastHurtMob(target);
        }
        boolean isEnderman = target.getType() == EntityType.ENDERMAN;
        if (this.isOnFire() && !isEnderman) {
            target.setSecondsOnFire(5);
        }
        if (target.hurt(damagesource, (float) damage)) {
            if (isEnderman) return;
            if (target instanceof LivingEntity livingTarget) {
                if (!this.level().isClientSide() && shooter instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingTarget, shooter);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) shooter, livingTarget);
                }
                this.doPostHurtEffects(livingTarget);
                if (livingTarget != shooter && livingTarget instanceof Player && shooter instanceof ServerPlayer && !this.isSilent()) {
                    ((ServerPlayer) shooter).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }
            }
            this.playSound(this.getDefaultHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        } else {
            target.setRemainingFireTicks(target.getRemainingFireTicks());
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
            this.setYRot(this.getYRot() + 180.0F);
            this.yRotO += 180.0F;
            if (!this.level().isClientSide() && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
                this.discard();
            }
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected boolean tryPickup(Player player) {
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public ItemStack getItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.CHERRY_WOOD_BREAK;
    }

    @Override
    public double getBaseDamage() {
        return 0.0D;
    }

    @Override
    protected float getWaterInertia() {
        return 0.75F;
    }
}
