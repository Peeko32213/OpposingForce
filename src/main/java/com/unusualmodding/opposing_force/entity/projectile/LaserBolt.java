package com.unusualmodding.opposing_force.entity.projectile;

import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;

public class LaserBolt extends FrictionlessProjectile {

    private static final EntityDataAccessor<Integer> DISRUPTOR_LEVEL = SynchedEntityData.defineId(LaserBolt.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DISRUPTOR = SynchedEntityData.defineId(LaserBolt.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RAPID_FIRE = SynchedEntityData.defineId(LaserBolt.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(LaserBolt.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ItemStack> ITEM_STACK = SynchedEntityData.defineId(LaserBolt.class, EntityDataSerializers.ITEM_STACK);

    protected RandomSource randomSource = level.getRandom();
    private final Vec3[] trailPositions = new Vec3[64];
    private int trailPointer = -1;

    public LaserBolt(EntityType<? extends FrictionlessProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public LaserBolt(LivingEntity entity, Level level, double x, double y, double z) {
        super(OPEntities.LASER_BOLT.get(), level, entity, x, y, z);
        this.setOwner(entity);
        this.moveTo(x, y, z, this.getYRot(), this.getXRot());
        this.reapplyPosition();
    }

    @Override
    public void recreateFromPacket(@NotNull ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DAMAGE, 4.0F);
        this.getEntityData().define(DISRUPTOR, false);
        this.getEntityData().define(DISRUPTOR_LEVEL, 0);
        this.getEntityData().define(RAPID_FIRE, false);
        this.getEntityData().define(ITEM_STACK, ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat("Damage", this.getLaserDamage());
        compoundTag.putBoolean("IsDisruptor", this.isDisruptor());
        compoundTag.putInt("DisruptorLevel", this.getDisruptorLevel());
        compoundTag.putBoolean("IsRapidFire", this.isRapidFire());
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            compoundTag.put("Item", itemstack.save(new CompoundTag()));
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.setLaserDamage(compoundTag.getFloat("Damage"));
        this.setDisruptor(compoundTag.getBoolean("IsDisruptor"));
        this.setDisruptorLevel(compoundTag.getInt("DisruptorLevel"));
        this.setRapidFire(compoundTag.getBoolean("IsRapidFire"));
        ItemStack itemstack = ItemStack.of(compoundTag.getCompound("Item"));
        this.setItem(itemstack);
    }

    public boolean isDisruptor() {
        return this.entityData.get(DISRUPTOR);
    }

    public void setDisruptor(boolean disruptor) {
        this.entityData.set(DISRUPTOR, disruptor);
    }

    public int getDisruptorLevel() {
        return this.entityData.get(DISRUPTOR_LEVEL);
    }

    public void setDisruptorLevel(int disruptorLevel) {
        this.entityData.set(DISRUPTOR_LEVEL, disruptorLevel);
    }

    public boolean isRapidFire() {
        return this.entityData.get(RAPID_FIRE);
    }

    public void setRapidFire(boolean rapidFire) {
        this.entityData.set(RAPID_FIRE, rapidFire);
    }

    public float getLaserDamage() {
        return this.entityData.get(DAMAGE);
    }

    public void setLaserDamage(float damage) {
        this.entityData.set(DAMAGE, damage);
    }

    public void setItem(ItemStack stack) {
        if (!stack.is(this.getDefaultItem()) || stack.hasTag()) {
            this.getEntityData().set(ITEM_STACK, stack.copyWithCount(1));
        }
    }

    protected Item getDefaultItem() {
        return OPItems.BLASTER.get();
    }

    protected ItemStack getItemRaw() {
        return this.getEntityData().get(ITEM_STACK);
    }

    @Override
    public @NotNull ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }

    @Override
    public void tick() {
        super.tick();

        float red = 1;
        float green = 0;
        float blue = 0;

        CompoundTag compoundTag = this.getItem().getTagElement("blasterColor");
        if (compoundTag != null && compoundTag.contains("color", 99) && compoundTag.getInt("color") != -1) {
            int decimal = compoundTag.getInt("color");
            red = (float) ((decimal & 16711680) >> 16) / 255.0F;
            green = (float) ((decimal & '\uff00') >> 8) / 255.0F;
            blue = (float) ((decimal & 255)) / 255.0F;
        }

        this.level().addParticle(OPParticles.LASER_BOLT_DUST.get(), this.getX(), this.getY() + 0.225F, this.getZ(), red, green, blue);

        if (tickCount > 160 || this.getBlockY() > this.level().getMaxBuildHeight() + 30) {
            if (!this.level().isClientSide) {
                this.level().broadcastEntityEvent(this, (byte) 3);
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSoundEvents.LASER_BOLT_IMPACT.get(), SoundSource.NEUTRAL, 1.5F, 1.0F + (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F);
                this.discard();
            }
        }

        this.tickTrail();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        DamageSource damageSource = this.damageSources().source(OPDamageTypes.LASER_BOLT);

        if (!this.level().isClientSide) {
            this.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), OPSoundEvents.LASER_BOLT_IMPACT.get(), SoundSource.NEUTRAL, 1.5F, 1.0F + (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F);
            this.level().broadcastEntityEvent(this, (byte) 3);
            entity.hurt(damageSource, this.getLaserDamage());
            entity.invulnerableTime = 0;
            if (this.isDisruptor()) {
                for (int i = 0; i < 2 + this.getDisruptorLevel(); i++) {
                    LaserBolt laserBolt = OPEntities.LASER_BOLT.get().create(level());
                    Vec3 vec3 = this.getDeltaMovement().normalize();
                    float f = -((float) Mth.atan2(vec3.x, vec3.z)) * 180.0F / (float) Math.PI;
                    float boltAngle = 360.0F / (this.getDisruptorLevel() + 2);
                    Vec3 vec31 = new Vec3(0, 0, 1.5F).yRot((float) -Math.toRadians(f + boltAngle - boltAngle * i));
                    laserBolt.setPos(entity.getEyePosition().add(vec31));
                    laserBolt.setDeltaMovement(vec31);
                    if (this.getOwner() != null) {
                        laserBolt.setOwner(this.getOwner());
                        if (this.getOwner() instanceof Player player) {
                            laserBolt.setItem(player.getItemInHand(player.getUsedItemHand()));
                        }
                    }
                    laserBolt.setDisruptor(false);
                    float yRot = (float) (Mth.atan2(vec31.z, vec31.x) * (180F / Math.PI)) + 90F;
                    float xRot = (float) -(Mth.atan2(vec31.y, Math.sqrt(vec31.x * vec31.x + vec31.z * vec31.z)) * (180F / Math.PI));
                    laserBolt.setYRot(yRot);
                    laserBolt.setXRot(xRot);
                    level().addFreshEntity(laserBolt);
                }
                level().playSound(null, this.getX(), this.getY(), this.getZ(), OPSoundEvents.BLASTER_SHOOT.get(), SoundSource.PLAYERS, 1.0F, (level.getRandom().nextFloat() * 0.5F + 0.8F));
            }
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();

        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), OPSoundEvents.LASER_BOLT_IMPACT.get(), SoundSource.NEUTRAL, 1.5F, 1.0F + (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F);
            this.discard();
        }
    }

    @Override
    protected float getEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
        return 0;
    }

    private void tickTrail() {
        Vec3 trailAt = this.position().add(0, this.getBbHeight() / 2F, 0);
        if (trailPointer == -1) {
            Vec3 backAt = trailAt;
            for (int i = 0; i < trailPositions.length; i++) {
                trailPositions[i] = backAt;
            }
        }
        if (++this.trailPointer == this.trailPositions.length) {
            this.trailPointer = 0;
        }
        this.trailPositions[this.trailPointer] = trailAt;
    }

    public Vec3 getTrailPosition(int pointer, float partialTick) {
        if (this.isRemoved()) {
            partialTick = 1.0F;
        }
        int i = this.trailPointer - pointer & 63;
        int j = this.trailPointer - pointer - 1 & 63;
        Vec3 d0 = this.trailPositions[j];
        Vec3 d1 = this.trailPositions[i].subtract(d0);
        return d0.add(d1.scale(partialTick));
    }

    public boolean hasTrail() {
        return trailPointer != -1;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            float red = 1;
            float green = 0;
            float blue = 0;
            CompoundTag compoundTag = this.getItem().getTagElement("blasterColor");
            if (compoundTag != null && compoundTag.contains("color", 99) && compoundTag.getInt("color") != -1) {
                int decimal = compoundTag.getInt("color");
                red = (float) ((decimal & 16711680) >> 16) / 255.0F;
                green = (float) ((decimal & '\uff00') >> 8) / 255.0F;
                blue = (float) ((decimal & 255)) / 255.0F;
            }

            for (int i = 0; i < 8; i++) {
                this.level().addParticle(OPParticles.LASER_BOLT_DUST.get(), this.getX(), this.getY(), this.getZ(), red, green, blue);
            }
        }
    }
}
