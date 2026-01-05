package com.unusualmodding.opposing_force.network;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.utils.KeybindUsingMount;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MountedEntityKeyPacket {

    public int mountId;
    public int playerId;
    public int type;

    public MountedEntityKeyPacket(int mountId, int playerId, int type) {
        this.mountId = mountId;
        this.playerId = playerId;
        this.type = type;
    }

    public MountedEntityKeyPacket() {
    }

    public static MountedEntityKeyPacket read(FriendlyByteBuf buf) {
        return new MountedEntityKeyPacket(buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void write(MountedEntityKeyPacket message, FriendlyByteBuf buf) {
        buf.writeInt(message.mountId);
        buf.writeInt(message.playerId);
        buf.writeInt(message.type);
    }

    public static void handle(MountedEntityKeyPacket message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Player playerSided = context.get().getSender();
            if (context.get().getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                playerSided = OpposingForce.PROXY.getClientSidePlayer();
            }
            Entity parent = playerSided.level().getEntity(message.mountId);
            Entity keyPresser = playerSided.level().getEntity(message.playerId);
            if (keyPresser != null && parent instanceof KeybindUsingMount mount && keyPresser instanceof Player && keyPresser.isPassengerOfSameVehicle(parent)) {
                mount.onKeyPacket(keyPresser, message.type);
            }
        });
        context.get().setPacketHandled(true);
    }
}