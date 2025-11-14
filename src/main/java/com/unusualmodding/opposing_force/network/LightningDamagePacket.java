package com.unusualmodding.opposing_force.network;

import com.unusualmodding.opposing_force.utils.DamageUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LightningDamagePacket {

    private final int targetId, senderId;
    private final float damage;
    private final Vec3 knockback;

    public LightningDamagePacket(int senderId, int targetId, float damage, Vec3 knockback) {
        this.targetId = targetId;
        this.senderId = senderId;
        this.damage = damage;
        this.knockback = knockback;
    }

    public LightningDamagePacket(FriendlyByteBuf buf) {
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

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Level level = context.getSender().level();
            Entity target = level.getEntity(targetId);
            if (target == null) return;
            Entity attacker = this.senderId >= 0 ? level.getEntity(this.senderId) : null;

            if (target instanceof LivingEntity && target.isAlive()) {
                DamageSource source = attacker == null ? DamageUtils.genericDamage(level) : attacker.damageSources().source(DamageTypes.LIGHTNING_BOLT, attacker, target);
                DamageUtils.safelyDealDamage(source, target, damage);
                target.setDeltaMovement(knockback);
            }
            context.setPacketHandled(true);
        });
    }
}