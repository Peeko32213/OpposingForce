package com.unusualmodding.opposing_force.network;

import com.unusualmodding.opposing_force.entity.ai.util.DamageUtil;
import com.unusualmodding.opposing_force.interfaces.CreeperExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ElectricChargeDamageC2SPacket {
    private final int targetId, senderId;
    private final float damage;
    private final Vec3 knockback;

    public ElectricChargeDamageC2SPacket(int senderId, int targetId, float damage, Vec3 knockback) {
        this.targetId = targetId;
        this.senderId = senderId;
        this.damage = damage;
        this.knockback = knockback;
    }

    public ElectricChargeDamageC2SPacket(FriendlyByteBuf buf) {
        this.targetId = buf.readVarInt();
        this.senderId = buf.readVarInt();
        this.damage = buf.readFloat();
        this.knockback = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarInt(targetId);
        buf.writeVarInt(senderId);
        buf.writeFloat(damage);
        buf.writeDouble(knockback.x);
        buf.writeDouble(knockback.y);
        buf.writeDouble(knockback.z);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Level level = context.getSender().level();
            Entity target = level.getEntity(targetId);
            if (target == null) return;
            Entity attacker = this.senderId >= 0 ? level.getEntity(this.senderId) : null;

            if (target instanceof LivingEntity entity && target.isAlive()) {

                if(entity instanceof Creeper creeper && level instanceof ServerLevel serverLevel) {
                    LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
                    ((CreeperExtension)creeper).opposingForce$setCharged(true);
                    return;
                }
                DamageSource source = attacker == null ? DamageUtil.genericDamage(level) : attacker.damageSources().source(DamageTypes.LIGHTNING_BOLT, attacker, target);
                DamageUtil.safelyDealDamage(source, target, damage);
                target.setDeltaMovement(knockback);
            }
            context.setPacketHandled(true);
        });
        return true;
    }
}

