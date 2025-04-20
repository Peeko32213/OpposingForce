package com.unusualmodding.opposingforce.client.particles.type;

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
            Codec.INT.fieldOf("range").forGetter(data -> data.range),
            Codec.INT.fieldOf("sections").forGetter(data -> data.sections),
            Codec.FLOAT.fieldOf("size").forGetter(data -> data.size),
            Codec.FLOAT.fieldOf("parallel_noise").forGetter(data -> data.parallelNoise),
            Codec.FLOAT.fieldOf("spread_factor").forGetter(data -> data.spreadFactor),
            Codec.FLOAT.fieldOf("branch_initiation_factor").forGetter(data -> data.branchInitiationFactor),
            Codec.FLOAT.fieldOf("branch_continuation_factor").forGetter(data -> data.branchContinuationFactor),
            Codec.FLOAT.fieldOf("closeness").forGetter(data -> data.closeness),
            VECTOR4F_CODEC.fieldOf("color").forGetter(data -> data.color),
            TargetType.CODEC.fieldOf("target_type").forGetter(data -> data.targetType),
            Codec.INT.fieldOf("entity_id").forGetter(data -> data.entitySourceId),
            Vec3.CODEC.fieldOf("target_pos").forGetter(data -> data.targetPos)
            ).apply(instance, (type, range, sections, size, parallelNoise, spreadFactor, bif, bcf, closeness, color, ttype, id, pos) ->
            new Data((ParticleType<Data>) BuiltInRegistries.PARTICLE_TYPE.get(new ResourceLocation(type)),
                    range, sections, size, parallelNoise, spreadFactor, bif, bcf, closeness, color,ttype,id,pos)));

    public LightningBallParticleType(boolean alwaysShow) {
        super(alwaysShow, Data.DESERIALIZER);
    }

    @Override
    public Codec<Data> codec() {
        return CODEC;
    }

    public static class Data implements ParticleOptions {
        public final ParticleType<Data> particleType;
        public final int range;
        public final int sections;
        public final float size;
        public final float parallelNoise;
        public final float spreadFactor;
        public final float branchInitiationFactor;
        public final float branchContinuationFactor;
        public final float closeness;
        public final org.joml.Vector4f color;
        public final TargetType targetType;
        public final int entitySourceId; // use -1 for unused
        public final Vec3 targetPos;     // use Vec3.ZERO or something invalid if unused

        public Data(ParticleType<Data> particleType) {
            this(particleType, 1, 6, 0.13f, 0.3f, 0.125f, 0.25f, 0.66f, 0.15f, new Vector4f(0.05f, 0.5f, 0.9f, 0.75f), TargetType.RANDOM, -1, Vec3.ZERO);
        }

        public Data(ParticleType<Data> particleType, int range, int sections, float size, float parallelNoise,
                    float spreadFactor, float branchInitiationFactor, float branchContinuationFactor,
                    float closeness, Vector4f color,
                    TargetType targetType, int entitySourceId, Vec3 targetPos) {
            this.particleType = particleType;
            this.range = range;
            this.sections = sections;
            this.size = size;
            this.parallelNoise = parallelNoise;
            this.spreadFactor = spreadFactor;
            this.branchInitiationFactor = branchInitiationFactor;
            this.branchContinuationFactor = branchContinuationFactor;
            this.closeness = closeness;
            this.color = color;
            this.targetType = targetType;
            this.entitySourceId = entitySourceId;
            this.targetPos = targetPos;
        }

        @Override
        public ParticleType<?> getType() {
            return particleType;
        }

        @Override
        public void writeToNetwork(FriendlyByteBuf buffer) {
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
            buffer.writeEnum(targetType);
            buffer.writeVarInt(entitySourceId);
            buffer.writeDouble(targetPos.x);
            buffer.writeDouble(targetPos.y);
            buffer.writeDouble(targetPos.z);

        }

        @Override
        public String writeToString() {
            return String.format(Locale.ROOT, "%s %d %d %.2f %.2f %.2f %.2f %.2f %.2f %.2f %.2f %.2f %.2f",
                    BuiltInRegistries.PARTICLE_TYPE.getKey(getType()),
                    range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness,
                    color.x(), color.y(), color.z(), color.w());
        }


        public Data range(int range) {
            return new Data(particleType, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, targetType, entitySourceId, targetPos);
        }

        public Data sections(int sections) {
            return new Data(particleType, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, targetType, entitySourceId, targetPos);
        }

        public Data size(float size) {
            return new Data(particleType, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, targetType, entitySourceId, targetPos);
        }

        public Data parallelNoise(float value) {
            return new Data(particleType, range, sections, size, value, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, targetType, entitySourceId, targetPos);
        }

        public Data spreadFactor(float value) {
            return new Data(particleType, range, sections, size, parallelNoise, value,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, targetType, entitySourceId, targetPos);
        }

        public Data branchInitiationFactor(float value) {
            return new Data(particleType, range, sections, size, parallelNoise, spreadFactor,
                    value, branchContinuationFactor, closeness, color, targetType, entitySourceId, targetPos);
        }

        public Data branchContinuationFactor(float value) {
            return new Data(particleType, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, value, closeness, color, targetType, entitySourceId, targetPos);
        }

        public Data closeness(float value) {
            return new Data(particleType, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, value, color, targetType, entitySourceId, targetPos);
        }

        public Data color(Vector4f color) {
            return new Data(particleType, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, targetType, entitySourceId, targetPos);
        }

        public Data color(float r, float g, float b, float a) {
            return color(new Vector4f(r, g, b, a));
        }


        public Data targetRandom() {
            return new Data(particleType, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, TargetType.RANDOM, -1, Vec3.ZERO);
        }

        public Data targetEntity(int entityId) {
            return new Data(particleType, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, TargetType.ENTITY, entityId, Vec3.ZERO);
        }

        public Data targetPosition(Vec3 pos) {
            return new Data(particleType, range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color, TargetType.POSITION, -1, pos);
        }


        public static final ParticleOptions.Deserializer<Data> DESERIALIZER = new ParticleOptions.Deserializer<>() {
            public Data fromCommand(ParticleType<Data> type, StringReader reader) throws CommandSyntaxException {
                reader.expect(' ');
                int range = reader.readInt();
                reader.expect(' ');
                int sections = reader.readInt();
                reader.expect(' ');
                float size = reader.readFloat();
                reader.expect(' ');
                float parallelNoise = reader.readFloat();
                reader.expect(' ');
                float spreadFactor = reader.readFloat();
                reader.expect(' ');
                float bif = reader.readFloat();
                reader.expect(' ');
                float bcf = reader.readFloat();
                reader.expect(' ');
                float closeness = reader.readFloat();
                reader.expect(' ');
                float r = reader.readFloat();
                reader.expect(' ');
                float g = reader.readFloat();
                reader.expect(' ');
                float b = reader.readFloat();
                reader.expect(' ');
                float a = reader.readFloat();
                reader.expect(' ');
                String targetTypeStr = reader.readString();
                TargetType targetType = TargetType.byName(targetTypeStr);

                int entityId = -1;
                Vec3 targetPos = Vec3.ZERO;

                if (targetType == TargetType.ENTITY) {
                    reader.expect(' ');
                    entityId = reader.readInt();
                } else if (targetType == TargetType.POSITION) {
                    reader.expect(' ');
                    double x = reader.readDouble();
                    reader.expect(' ');
                    double y = reader.readDouble();
                    reader.expect(' ');
                    double z = reader.readDouble();
                    targetPos = new Vec3(x, y, z);
                }

                return new Data(type, range, sections, size, parallelNoise, spreadFactor,
                        bif, bcf, closeness, new Vector4f(r, g, b, a), targetType, entityId, targetPos);
            }

            public Data fromNetwork(ParticleType<Data> type, FriendlyByteBuf buffer) {
                return new Data(type,
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
                        buffer.readEnum(TargetType.class),
                        buffer.readVarInt(),
                        new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()));
            }
        };
    }


    public enum TargetType implements StringRepresentable {
        RANDOM,
        ENTITY,
        POSITION;

        public static final Codec<TargetType> CODEC = StringRepresentable.fromEnum(TargetType::values);

        public static TargetType byName(String name) {
            return TargetType.valueOf(name.toLowerCase());
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        public static TargetType fromId(int id) {
            return TargetType.values()[id];
        }
    }

}

