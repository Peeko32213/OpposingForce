package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class Tomahawk extends AbstractArrow implements ItemSupplier {

    public Tomahawk(EntityType<? extends Tomahawk> type, Level level) {
        super(type, level);
    }

    public Tomahawk(Level level, LivingEntity shooter) {
        super(OPEntities.TOMAHAWK.get(), shooter, level);
    }

    public Tomahawk(Level pLevel, double pX, double pY, double pZ) {
        super(OPEntities.TOMAHAWK.get(), pX, pY, pZ, pLevel);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        Entity shooter = this.getOwner();

        float motion = (float) this.getDeltaMovement().length();
        int damage = Mth.ceil(Mth.clamp((double) motion * 0.75F * this.getBaseDamage(), 0.0D, 2.147483647E9D));

        DamageSource damagesource = OPDamageTypes.tomahawk(this.level(), this, shooter);
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
        }
        else {
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

    protected ItemStack getPickupItem() {
        return new ItemStack(OPItems.TOMAHAWK.get());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public ItemStack getItem() {
        return new ItemStack(OPItems.TOMAHAWK.get());
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.CHERRY_WOOD_BREAK;
    }

    @Override
    public double getBaseDamage() {
        return 5.0D;
    }

    @Override
    protected float getWaterInertia() {
        return 0.75F;
    }
}
