package com.unusualmodding.opposingforce.common.message;

import com.unusualmodding.opposingforce.client.particles.type.LightningBallParticleType;
import com.unusualmodding.opposingforce.core.registry.OPParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.joml.Vector4f;

import java.util.function.Supplier;

public class ElectricBallSyncS2CPacket {

    private final float blockX, blockY, blockZ;
    private final float attackX, attackY, attackZ;

    private final int range;
    private final int sections;
    private final float size;
    private final float parallelNoise;
    private final float spreadFactor;
    private final float branchInitiationFactor;
    private final float branchContinuationFactor;
    private final float closeness;
    private final Vector4f color;


    public ElectricBallSyncS2CPacket(float blockX, float blockY, float blockZ,
                                     float attackX, float attackY, float attackZ,
                                     int range, int sections, float size,
                                     float parallelNoise, float spreadFactor,
                                     float branchInitiationFactor, float branchContinuationFactor,
                                     float closeness, Vector4f color) {
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
        this.attackX = attackX;
        this.attackY = attackY;
        this.attackZ = attackZ;
        this.range = range;
        this.sections = sections;
        this.size = size;
        this.parallelNoise = parallelNoise;
        this.spreadFactor = spreadFactor;
        this.branchInitiationFactor = branchInitiationFactor;
        this.branchContinuationFactor = branchContinuationFactor;
        this.closeness = closeness;
        this.color = color;
    }

    public ElectricBallSyncS2CPacket(FriendlyByteBuf buf) {
        this.blockX = buf.readFloat();
        this.blockY = buf.readFloat();
        this.blockZ = buf.readFloat();
        this.attackX = buf.readFloat();
        this.attackY = buf.readFloat();
        this.attackZ = buf.readFloat();
        this.range = buf.readVarInt();
        this.sections = buf.readVarInt();
        this.size = buf.readFloat();
        this.parallelNoise = buf.readFloat();
        this.spreadFactor = buf.readFloat();
        this.branchInitiationFactor = buf.readFloat();
        this.branchContinuationFactor = buf.readFloat();
        this.closeness = buf.readFloat();
        this.color = new Vector4f(
                buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(blockX);
        buf.writeFloat(blockY);
        buf.writeFloat(blockZ);
        buf.writeFloat(attackX);
        buf.writeFloat(attackY);
        buf.writeFloat(attackZ);
        buf.writeVarInt(range);
        buf.writeVarInt(sections);
        buf.writeFloat(size);
        buf.writeFloat(parallelNoise);
        buf.writeFloat(spreadFactor);
        buf.writeFloat(branchInitiationFactor);
        buf.writeFloat(branchContinuationFactor);
        buf.writeFloat(closeness);
        buf.writeFloat(color.x());
        buf.writeFloat(color.y());
        buf.writeFloat(color.z());
        buf.writeFloat(color.w());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null) {
                LightningBallParticleType.Data data = new LightningBallParticleType.Data(OPParticles.ELECTRIC_ORB.get())
                        .range(range)
                        .sections(sections)
                        .size(size)
                        .parallelNoise(parallelNoise)
                        .spreadFactor(spreadFactor)
                        .branchInitiationFactor(branchInitiationFactor)
                        .branchContinuationFactor(branchContinuationFactor)
                        .closeness(closeness)
                        .color(color);

                level.addParticle(data, true, blockX, blockY, blockZ, attackX, attackY, attackZ);
            }
        });
        return true;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private float blockX, blockY, blockZ;
        private float attackX, attackY, attackZ;

        private int range = 1;
        private int sections = 6;
        private float size = 0.13f;
        private float parallelNoise = 0.3f;
        private float spreadFactor = 0.125f;
        private float branchInitiationFactor = 0.25f;
        private float branchContinuationFactor = 0.66f;
        private float closeness = 0.15f;
        private Vector4f color = new Vector4f(0.1f, 0.8f, 0.1f, 0.75f);

        private boolean large = false;

        public Builder pos(double x, double y, double z) {
            this.blockX = (float) x;
            this.blockY = (float) y;
            this.blockZ = (float) z;
            return this;
        }

        public Builder direction(double dx, double dy, double dz) {
            this.attackX = (float) dx;
            this.attackY = (float) dy;
            this.attackZ = (float) dz;
            return this;
        }

        public Builder range(int range) {
            this.range = range;
            return this;
        }

        public Builder sections(int sections) {
            this.sections = sections;
            return this;
        }

        public Builder size(float size) {
            this.size = size;
            return this;
        }

        public Builder parallelNoise(float value) {
            this.parallelNoise = value;
            return this;
        }

        public Builder spreadFactor(float value) {
            this.spreadFactor = value;
            return this;
        }

        public Builder branchInitiationFactor(float value) {
            this.branchInitiationFactor = value;
            return this;
        }

        public Builder branchContinuationFactor(float value) {
            this.branchContinuationFactor = value;
            return this;
        }

        public Builder closeness(float value) {
            this.closeness = value;
            return this;
        }

        public Builder color(float r, float g, float b, float a) {
            this.color = new Vector4f(r, g, b, a);
            return this;
        }

        public Builder color(Vector4f vec) {
            this.color = vec;
            return this;
        }


        public ElectricBallSyncS2CPacket build() {
            return new ElectricBallSyncS2CPacket(blockX, blockY, blockZ, attackX, attackY, attackZ,
                    range, sections, size, parallelNoise, spreadFactor,
                    branchInitiationFactor, branchContinuationFactor, closeness, color);
        }
    }
}
