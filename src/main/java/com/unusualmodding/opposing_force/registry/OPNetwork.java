package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.network.LightningDamagePacket;
import com.unusualmodding.opposing_force.network.LightningSyncPacket;
import com.unusualmodding.opposing_force.network.MountedEntityKeyPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;

public class OPNetwork {

    private static SimpleChannel CHANNEL;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void registerNetwork() {
        SimpleChannel network = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(OpposingForce.MOD_ID, "network"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        CHANNEL = network;

        network.messageBuilder(LightningSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT).decoder(LightningSyncPacket::new).encoder(LightningSyncPacket::toBytes).consumerMainThread(LightningSyncPacket::handle).add();
        network.messageBuilder(LightningDamagePacket.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(LightningDamagePacket::new).encoder(LightningDamagePacket::toBytes).consumerMainThread(LightningDamagePacket::handle).add();
        network.registerMessage(id(), MountedEntityKeyPacket.class, MountedEntityKeyPacket::write, MountedEntityKeyPacket::read, MountedEntityKeyPacket::handle);
    }

    public static <MSG> void sendPacketToServer(MSG message) {
        CHANNEL.sendToServer(message);
    }

    public static <MSG> void sendPacketToPlayer(MSG message, ServerPlayer player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendPacketToClients(MSG message) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), message);
    }

    public static <MSG> void sendPacketToAll(MSG message) {
        for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            sendPacketNonLocal(message, player);
        }
    }

    public static <MSG> void sendPacketNonLocal(MSG msg, ServerPlayer player) {
        CHANNEL.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}