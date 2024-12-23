package com.peeko32213.hole.common.entity.projectile;

import com.peeko32213.hole.core.registry.HoleDamageTypes;
import com.peeko32213.hole.core.registry.HoleItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class EntityAbstractElectricBall  extends AbstractHurtingProjectile implements ItemSupplier {
    private SoundEvent soundEvent;

    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(EntitySmallElectricBall.class, EntityDataSerializers.ITEM_STACK);

    public EntityAbstractElectricBall(EntityType<? extends EntitySmallElectricBall> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EntityAbstractElectricBall(EntityType<? extends EntitySmallElectricBall> pEntityType, double pX, double pY, double pZ, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }

    public EntityAbstractElectricBall(EntityType<? extends EntitySmallElectricBall> pEntityType, LivingEntity pShooter, double pOffsetX, double pOffsetY, double pOffsetZ, Level pLevel) {
        super(pEntityType, pShooter, pOffsetX, pOffsetY, pOffsetZ, pLevel);
    }


    public void setItem(ItemStack pStack) {
        if (!pStack.is(HoleItems.ELECTRIC_CHARGE.get()) || pStack.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, pStack.copyWithCount(1));
        }

    }
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity target = pResult.getEntity();
        Entity shooter = this.getOwner();
        double damage = this.getBaseDamage();

        DamageSource damagesource = HoleDamageTypes.electrified(this.level(), this, shooter);
        if (target.hurt(damagesource, (float) damage)) {

            if (target instanceof LivingEntity livingTarget) {

                if (!this.level().isClientSide() && shooter instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingTarget, shooter);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) shooter, livingTarget);
                }

                if (livingTarget != shooter && livingTarget instanceof Player && shooter instanceof ServerPlayer && !this.isSilent()) {
                    ((ServerPlayer) shooter).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }

            }

        }
    }

    public double getBaseDamage() {
        return 3.0D;
    }

    protected ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(HoleItems.ELECTRIC_CHARGE.get()) : itemstack;
    }

    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            pCompound.put("Item", itemstack.save(new CompoundTag()));
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        ItemStack itemstack = ItemStack.of(pCompound.getCompound("Item"));
        this.setItem(itemstack);
    }

    public void setSoundEvent(SoundEvent pSoundEvent) {
        this.soundEvent = pSoundEvent;
    }

}
