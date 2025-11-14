package com.unusualmodding.opposing_force.client.particles.lightning;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public record LightningTarget(LightningParticleType.TargetType type, int entityId, Vec3 position, List<Integer> chainTargets) {

    public static final Codec<LightningTarget> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            LightningParticleType.TargetType.CODEC.fieldOf("type").forGetter(LightningTarget::type),
            Codec.INT.fieldOf("target_entity_id").forGetter(LightningTarget::entityId),
            Vec3.CODEC.fieldOf("position").forGetter(LightningTarget::position),
            Codec.INT.listOf().fieldOf("chain_targets").forGetter(LightningTarget::chainTargets)
    ).apply(instance, LightningTarget::new));

    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(type);
        buf.writeVarInt(entityId);
        buf.writeDouble(position.x);
        buf.writeDouble(position.y);
        buf.writeDouble(position.z);
        buf.writeVarInt(chainTargets.size());
        for (int id : chainTargets) {
            buf.writeVarInt(id);
        }
    }

    public static LightningTarget read(FriendlyByteBuf buf) {
        LightningParticleType.TargetType type = buf.readEnum(LightningParticleType.TargetType.class);
        int entityId = buf.readVarInt();
        Vec3 pos = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        int chainSize = buf.readVarInt();
        List<Integer> chain = new ArrayList<>(chainSize);
        for (int i = 0; i < chainSize; i++) {
            chain.add(buf.readVarInt());
        }
        return new LightningTarget(type, entityId, pos, chain);
    }
}