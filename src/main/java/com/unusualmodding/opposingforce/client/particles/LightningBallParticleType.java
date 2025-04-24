package com.unusualmodding.opposingforce.client.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LightningBallParticleType extends ParticleType<LightningBallParticleType.Data> {

    public static final Codec<Vector4f> VECTOR4F_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("r").forGetter(v -> v.x()),
            Codec.FLOAT.fieldOf("g").forGetter(v -> v.y()),
            Codec.FLOAT.fieldOf("b").forGetter(v -> v.z()),
            Codec.FLOAT.fieldOf("a").forGetter(v -> v.w())
    ).apply(instance, Vector4f::new));

    public static final Codec<Data> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("particle_type").forGetter(data -> BuiltInRegistries.PARTICLE_TYPE.getKey(data.particleType).toString()),
            Codec.INT.fieldOf("sender_id").forGetter(data -> data.senderId),
            Codec.INT.fieldOf("range").forGetter(data -> data.range),
            Codec.INT.fieldOf("sections").forGetter(data -> data.sections),
            Codec.FLOAT.fieldOf("size").forGetter(data -> data.size),
            Codec.FLOAT.fieldOf("parallel_noise").forGetter(data -> data.parallelNoise),
            Codec.FLOAT.fieldOf("spread_factor").forGetter(data -> data.spreadFactor),
            Codec.FLOAT.fieldOf("branch_initiation_factor").forGetter(data -> data.branchInitiationFactor),
            Codec.FLOAT.fieldOf("branch_continuation_factor").forGetter(data -> data.branchContinuationFactor),
            Codec.FLOAT.fieldOf("closeness").forGetter(data -> data.closeness),
            VECTOR4F_CODEC.fieldOf("color").forGetter(data -> data.color),
            LightningTarget.CODEC.fieldOf("target").forGetter(data -> data.target)
    ).apply(instance, (type, id,range, sections, size, pn, sf, bif, bcf, close, color, target) ->
            new Data((ParticleType<Data>) BuiltInRegistries.PARTICLE_TYPE.get(new ResourceLocation(type)),id,
                    range, sections, size, pn, sf, bif, bcf, close, color, target)));

    public LightningBallParticleType(boolean alwaysShow) {
        super(alwaysShow, Data.DESERIALIZER);
    }

    @Override
    public Codec<Data> codec() {
        return CODEC;
    }


    public static class Data implements ParticleOptions {
        public final ParticleType<Data> particleType;
        public final int range, sections, senderId;
        public final float size, parallelNoise, spreadFactor, branchInitiationFactor, branchContinuationFactor, closeness;
        public final Vector4f color;
        public final LightningTarget target;

        public Data(ParticleType<Data> type) {
            this(type, -1,1, 6, 0.13f, 0.3f, 0.125f, 0.25f, 0.66f, 0.15f, new Vector4f(0.0f, 0.0f, 0.0f, 0.75f), new LightningTarget(TargetType.RANDOM, -1, Vec3.ZERO, List.of()));
        }

        public Data(ParticleType<Data> type, int senderId ,int range, int sections, float size, float pn, float sf, float bif, float bcf, float closeness, Vector4f color, LightningTarget target) {
            this.senderId = senderId;
            this.particleType = type;
            this.range = range;
            this.sections = sections;
            this.size = size;
            this.parallelNoise = pn;
            this.spreadFactor = sf;
            this.branchInitiationFactor = bif;
            this.branchContinuationFactor = bcf;
            this.closeness = closeness;
            this.color = color;
            this.target = target;
        }

        @Override
        public ParticleType<?> getType() {
            return particleType;
        }

        @Override
        public void writeToNetwork(FriendlyByteBuf buffer) {
            buffer.writeVarInt(senderId);
            buffer.writeVarInt(range);
            buffer.writeVarInt(sections);
            buffer.writeFloat(size);
            buffer.writeFloat(parallelNoise);
            buffer.writeFloat(spreadFactor);
            buffer.writeFloat(branchInitiationFactor);
            buffer.writeFloat(branchContinuationFactor);
            buffer.writeFloat(closeness);
            buffer.writeFloat(color.x());
            buffer.writeFloat(color.y());
            buffer.writeFloat(color.z());
            buffer.writeFloat(color.w());
            target.write(buffer);
        }

        public Data range(int range) {
            return new Data(particleType, senderId, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, target);
        }

        public Data sections(int sections) {
            return new Data(particleType,senderId, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, target);
        }

        public Data size(float size) {
            return new Data(particleType,senderId, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, target);
        }

        public Data parallelNoise(float value) {
            return new Data(particleType,senderId, range, sections, size, value, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, target);
        }

        public Data spreadFactor(float value) {
            return new Data(particleType,senderId, range, sections, size, parallelNoise, value,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, target);
        }

        public Data branchInitiationFactor(float value) {
            return new Data(particleType,senderId, range, sections, size, parallelNoise, spreadFactor,
                    value, branchContinuationFactor, closeness, color, target);
        }

        public Data branchContinuationFactor(float value) {
            return new Data(particleType,senderId, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, value, closeness, color, target);
        }

        public Data closeness(float value) {
            return new Data(particleType,senderId, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, value, color, target);
        }

        public Data color(Vector4f color) {
            return new Data(particleType,senderId, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, target);
        }

        public Data color(float r, float g, float b, float a) {
            return color(new Vector4f(r, g, b, a));
        }

        public Data senderId(int senderId) {
            return new Data(particleType,senderId, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, target);
        }

        public Data targetRandom() {
            return new Data(particleType,senderId, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color,
                    new LightningTarget(TargetType.RANDOM, -1, Vec3.ZERO, List.of()));
        }

        public Data targetEntity(int entityId) {
            return new Data(particleType,senderId, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color,
                    new LightningTarget(TargetType.ENTITY, entityId, Vec3.ZERO, List.of()));
        }

        public Data targetPosition(Vec3 pos) {
            return new Data(particleType,senderId, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color,
                    new LightningTarget(TargetType.POSITION, -1, pos, List.of()));
        }

        public Data withChainTargets(List<Integer> ids) {
            return new Data(particleType,senderId, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color,
                    new LightningTarget(target.type(), target.entityId(), target.position(), ids));
        }

        @Override
        public String writeToString() {
            return "lightning_ball_particle";
        }

        public static final Deserializer<Data> DESERIALIZER = new Deserializer<>() {
            public Data fromCommand(ParticleType<Data> type, StringReader reader) throws CommandSyntaxException {
                throw new UnsupportedOperationException("Use JSON/Network for this particle.");
            }

            public Data fromNetwork(ParticleType<Data> type, FriendlyByteBuf buffer) {
                return new Data(type,
                        buffer.readVarInt(),
                        buffer.readVarInt(),
                        buffer.readVarInt(),
                        buffer.readFloat(),
                        buffer.readFloat(),
                        buffer.readFloat(),
                        buffer.readFloat(),
                        buffer.readFloat(),
                        buffer.readFloat(),
                        new Vector4f(
                                buffer.readFloat(),
                                buffer.readFloat(),
                                buffer.readFloat(),
                                buffer.readFloat()
                        ),
                        LightningTarget.read(buffer)
                );
            }
        };
    }

    public enum TargetType implements StringRepresentable {
        RANDOM, ENTITY, POSITION, CHAIN;

        public static final Codec<TargetType> CODEC = StringRepresentable.fromEnum(TargetType::values);

        public static TargetType byName(String name) {
            return TargetType.valueOf(name.toUpperCase(Locale.ROOT));
        }

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }

        public static TargetType fromId(int id) {
            return values()[id];
        }
    }
}
