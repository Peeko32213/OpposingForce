package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.network.LightningDamagePacket;
import com.unusualmodding.opposing_force.network.LightningSyncPacket;
import com.unusualmodding.opposing_force.network.MountedEntityKeyPacket;
import com.unusualmodding.opposing_force.network.ParticlePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
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
        network.registerMessage(id(), ParticlePacket.class, ParticlePacket::encode, ParticlePacket::new, ParticlePacket.Handler::onMessage);
    }

    public static <MSG> void sendPacketToServer(MSG packet) {
        CHANNEL.sendToServer(packet);
    }

    public static <MSG> void sendPacketToPlayer(MSG packet, ServerPlayer player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static <MSG> void sendPacketToClients(MSG packet) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static <MSG> void sendPacketToTrackingChunk(MSG packet, Level level, BlockPos pos) {
        CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), packet);
    }

    public static <MSG> void sendPacketToTrackingEntity(MSG packet, Entity entity) {
        CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), packet);
    }

    public static <MSG> void sendPacketToAll(MSG packet) {
        for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            sendPacketNonLocal(packet, player);
        }
    }

    public static <MSG> void sendPacketNonLocal(MSG packet, ServerPlayer player) {
        CHANNEL.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}