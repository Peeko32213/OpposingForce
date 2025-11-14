package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.network.LightningDamagePacket;
import com.unusualmodding.opposing_force.network.LightningSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;

public class OPNetwork {

    private static SimpleChannel INSTANCE;

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

        INSTANCE = network;

        network.messageBuilder(LightningSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LightningSyncPacket::new)
                .encoder(LightningSyncPacket::toBytes)
                .consumerMainThread(LightningSyncPacket::handle)
                .add();

        network.messageBuilder(LightningDamagePacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LightningDamagePacket::new)
                .encoder(LightningDamagePacket::toBytes)
                .consumerMainThread(LightningDamagePacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    public static <MSG> void sendToAll(MSG message) {
        for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            sendNonLocal(message, player);
        }
    }

    public static <MSG> void sendNonLocal(MSG msg, ServerPlayer player) {
        INSTANCE.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}