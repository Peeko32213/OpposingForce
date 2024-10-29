package com.peeko32213.hole.common.entity.projectile;

import com.peeko32213.hole.core.registry.HoleDamageTypes;
import com.peeko32213.hole.core.registry.HoleEntities;
import com.peeko32213.hole.core.registry.HoleItems;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)

public class EntityThrownTomahawk extends AbstractArrow implements ItemSupplier {

    public EntityThrownTomahawk(EntityType<? extends EntityThrownTomahawk> type, Level worldIn) {
        super(type, worldIn);
    }

    public EntityThrownTomahawk(Level worldIn, double x, double y, double z) {
        super(HoleEntities.TOMAHAWK.get(), x, y, z, worldIn);
    }
    public EntityThrownTomahawk(PlayMessages.SpawnEntity spawnEntity, Level world) {
        this(HoleEntities.TOMAHAWK.get(), world);
    }

    public EntityThrownTomahawk(Level worldIn, LivingEntity shooter) {
        super(HoleEntities.TOMAHAWK.get(), shooter, worldIn);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        //SilverTool.causeMagicParticles(living, false);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        Entity shooter = this.getOwner();

        float motion = (float) this.getDeltaMovement().length();
        int damage = Mth.ceil(Mth.clamp((double) motion * 0.5F * this.getBaseDamage(), 0.0D, 2.147483647E9D));

        DamageSource damagesource = HoleDamageTypes.tomahawk(this.level(), this, shooter);
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

    protected ItemStack getPickupItem() {
        return new ItemStack(HoleItems.TOMAHAWK.get());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public ItemStack getItem() {
        return new ItemStack(HoleItems.TOMAHAWK.get());
    }

    @Override
    protected void tickDespawn() {
        ++this.life;
        if (this.life >= 6000) {
            this.discard();
        }
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.WOOD_BREAK;
    }

    @Override
    public double getBaseDamage() {
        return 2.0D;
    }
}
