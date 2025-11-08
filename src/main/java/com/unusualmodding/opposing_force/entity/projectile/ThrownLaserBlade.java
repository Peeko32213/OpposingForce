package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.enchantments.ThrowingEnchantment;
import com.unusualmodding.opposing_force.registry.OPDamageTypes;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ThrownLaserBlade extends ThrowableItemProjectile {

    private ItemStack laserBladeItem = new ItemStack(OPItems.LASER_BLADE.get());

    private int returnTimer = 0;
    private boolean hasHitBlock = false;
    public boolean counterclockwise = false;

    public ThrownLaserBlade(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
    }

    public ThrownLaserBlade(Level level, double x, double y, double z) {
        super(OPEntities.LASER_BLADE.get(), x, y, z, level);
        this.noPhysics = true;
    }

    public void setData(Entity owner, int returnTimer, ItemStack laserBladeItem) {
        this.setOwner(owner);
        this.returnTimer = returnTimer;
        this.laserBladeItem = laserBladeItem.copy();
        this.counterclockwise = this.random.nextBoolean();
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putInt("ReturnTimer", this.returnTimer);
        compoundTag.putBoolean("Counterclockwise", this.counterclockwise);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.returnTimer = compoundTag.getInt("ReturnTimer");
        this.counterclockwise = compoundTag.getBoolean("Counterclockwise");
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return !entity.equals(this.getOwner()) && super.canHitEntity(entity);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        Entity target = result.getEntity();

        if (!this.level().isClientSide) {
            if (this.getOwner() instanceof LivingEntity owner) {
                var heldItem = owner.getMainHandItem();
                owner.setItemInHand(InteractionHand.MAIN_HAND, this.getItem());
                target.invulnerableTime = 0;
                boolean hit = target.hurt(OPDamageTypes.thrownLaserBlade(this.level(), this, this.getOwner()), 7);
                if (hit && target instanceof LivingEntity livingentity) {
                    ItemStack itemStack = this.getItem();
                    int fireAspect = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, itemStack);
                    if (fireAspect > 0) {
                        livingentity.setSecondsOnFire(fireAspect * 4);
                    }
                }
                owner.setItemInHand(InteractionHand.MAIN_HAND, heldItem);
                this.playSound(OPSoundEvents.LASER_BLADE_HIT.get(), 1.0F, 1.0F / (this.level().getRandom().nextFloat() * 0.4F + 0.8F));
            }
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.hasHitBlock) {
            this.playSound(OPSoundEvents.LASER_BLADE_HIT.get(), 1.0F, 1.0F / (this.level().getRandom().nextFloat() * 0.4F + 0.8F));
        }
        this.returnTimer = 0;
        this.hasHitBlock = true;
        if (this.getOwner() instanceof LivingEntity owner) {
            this.flyBack(owner, false);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.returnToOwner();
            var motion = getDeltaMovement();
            if (xRotO == 0.0F && yRotO == 0.0F) {
                setYRot((float) (Mth.atan2(motion.x, motion.z) * (double) (180F / (float) Math.PI)));
                yRotO = getYRot();
                xRotO = getXRot();
            }
        } else {
            OpposingForce.PROXY.playWorldSound(this, (byte) 4);
        }
    }

    public void shootFromRotation(Entity shooter, float rotationPitch, float rotationYaw, float pitchOffset, float velocity, float inaccuracy) {
        float x = -Mth.sin(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        float y = -Mth.sin((rotationPitch + pitchOffset) * ((float) Math.PI / 180F));
        float z = Mth.cos(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        this.shoot(x, y, z, velocity, inaccuracy);
        Vec3 vec3 = shooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, 0, vec3.z));
    }

    public void flyBack(Entity owner, boolean lerpMotion) {
        Vec3 ownerPos = owner.position().add(0, owner.getBbHeight() * 0.5F, 0);
        Vec3 returnMotion = ownerPos.subtract(position()).normalize().scale(2.25F);
        Vec3 motion = this.getDeltaMovement();
        float delta = 0.1F;
        double x = Mth.lerp(delta, motion.x, returnMotion.x);
        double y = Mth.lerp(delta, motion.y, returnMotion.y);
        double z = Mth.lerp(delta, motion.z, returnMotion.z);
        if (lerpMotion) this.setDeltaMovement(new Vec3(x, y, z));
        else this.setDeltaMovement(returnMotion);
    }

    private boolean isAcceptableReturnOwner() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayer) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    private void returnToOwner() {
        if (this.getOwner() == null || !this.getOwner().isAlive() || !this.getOwner().level().equals(level()) || distanceTo(this.getOwner()) > 1000F) {
            this.setDeltaMovement(Vec3.ZERO);
            return;
        }
        if (!this.isAcceptableReturnOwner()) {
            this.spawnAtLocation(this.getPickupItem(), 0.1F);
            this.discard();
        }
        else if (returnTimer == 0) {
            this.flyBack(this.getOwner(), true);
            if (this.getOwner() instanceof ServerPlayer player) {
                ThrowingEnchantment.addCooldown(player, this);
            }
        }
        if (returnTimer > 0) returnTimer--;
        this.updateRotation();
    }

    @Override
    public void remove(Entity.@NotNull RemovalReason reason) {
        OpposingForce.PROXY.clearSoundCacheFor(this);
        super.remove(reason);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return OPItems.LASER_BLADE.get();
    }

    protected ItemStack getPickupItem() {
        return this.laserBladeItem.copy();
    }

    @Override
    public void playerTouch(@NotNull Player player) {
        if (!this.level().isClientSide && (this.ownedBy(player) || this.getOwner() == null) && this.tickCount > 2) {
            if (!player.isCreative() && player.getInventory().add(this.getPickupItem())) {
                player.take(this, 1);
                this.discard();
            } else {
                this.discard();
            }
        }
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 4.0F;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }
}