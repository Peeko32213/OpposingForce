package com.unusualmodding.opposing_force.client.particles.lightning;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.network.LightningDamagePacket;
import com.unusualmodding.opposing_force.registry.OPNetwork;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class LightningParticle extends Particle {

    private final LightningRenderer lightningRender = new LightningRenderer();
    private final LightningParticleType.Data data;
    private final Vec3 lightningOrigin;

    public LightningParticle(LightningParticleType.Data data, ClientLevel world, double x, double y, double z, double xd, double yd, double zd) {
        super(world, x, y, z);
        this.data = data;
        this.setSize(3.0F, 3.0F);
        this.x = x;
        this.y = y + 0.5;
        this.z = z;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;

        Vec3 from = new Vec3(x, y, z);
        Vec3 lightningTo = resolveTarget(data, world, from);
        Vec3 to = lightningTo.subtract(from);
        this.lightningOrigin = lightningTo;
        this.lifetime = (int) Math.ceil(to.length());
        int sections = data.sections * this.lifetime;

        LightningBoltData.BoltRenderInfo boltData = new LightningBoltData.BoltRenderInfo(
                data.parallelNoise,
                data.spreadFactor,
                data.branchInitiationFactor,
                data.branchContinuationFactor,
                data.color,
                data.closeness
        );

        LightningBoltData bolt = new LightningBoltData(boltData, Vec3.ZERO, to, sections)
                .size(data.size + random.nextFloat() * 0.1F)
                .lifespan(this.lifetime + 1)
                .spawn(LightningBoltData.SpawnFunction.CONSECUTIVE);

        lightningRender.update(this, bolt, 1.0F);
    }

    private Vec3 resolveTarget(LightningParticleType.Data data, ClientLevel level, Vec3 from) {
        LightningTarget target = data.target;
        return switch (target.type()) {
            case ENTITY, CHAIN -> {
                Entity entity = level.getEntity(target.entityId());
                if (entity != null) {
                    double offX = (random.nextDouble() - 0.5) * entity.getBbWidth();
                    double offY = random.nextDouble() * entity.getBbHeight();
                    double offZ = (random.nextDouble() - 0.5) * entity.getBbWidth();
                    yield entity.position().add(offX, offY, offZ);
                } else {
                    yield from;
                }
            }
            case POSITION -> canSeeBlock(from, target.position()) ? target.position() : from;
            default -> findRandomLightningTarget(level, from, data.range + random.nextInt(2));
        };
    }

    private Vec3 findRandomLightningTarget(ClientLevel world, Vec3 origin, int range) {
        for (int i = 0; i < 10; i++) {
            Vec3 candidate = origin.add(random.nextFloat() * range - range / 2F, random.nextFloat() * range - range / 2F, random.nextFloat() * range - range / 2F);
            if (canSeeBlock(origin, candidate)) {
                return candidate;
            }
        }
        return origin;
    }

    private boolean canSeeBlock(Vec3 from, Vec3 to) {
        BlockHitResult result = this.level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null));
        return Vec3.atCenterOf(result.getBlockPos()).distanceTo(to) < 3.0F;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        if (this.age++ >= this.lifetime) {
            if (data.target.type() == LightningParticleType.TargetType.CHAIN) {
                spawnNextChainParticle();
            }
            this.remove();
        } else {
            this.move(this.xd, this.yd, this.zd);
            this.yd -= this.gravity;
        }
    }

    private void spawnNextChainParticle() {
        LightningTarget target = data.target;
        if (target.chainTargets().isEmpty()) return;

        Vec3 origin = lightningOrigin;
        List<Integer> sorted = target.chainTargets().stream()
                .map(level::getEntity)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingDouble(e -> e.position().distanceToSqr(origin)))
                .map(Entity::getId)
                .toList();

        if (sorted.isEmpty()) return;

        int nextId = sorted.get(0);
        List<Integer> remaining = sorted.subList(1, sorted.size());

        Entity nextEntity = level.getEntity(nextId);
        if (nextEntity != null) {
            LightningDamagePacket damagePacket = new LightningDamagePacket(data.senderId, nextId, 5, new Vec3(0,0,0));
            OPNetwork.sendToServer(damagePacket);
            LightningParticleType.Data newData = getData(nextEntity.getId(), remaining);

            Minecraft.getInstance().level.addParticle(newData, true, lightningOrigin.x, lightningOrigin.y, lightningOrigin.z, 0, 0, 0);
        }
    }

    private LightningParticleType.@NotNull Data getData(int nextId, List<Integer> remaining) {
        LightningTarget newTarget = new LightningTarget(LightningParticleType.TargetType.CHAIN, nextId, Vec3.ZERO, remaining);
        return new LightningParticleType.Data(
                data.particleType,
                data.senderId,
                data.range,
                data.sections,
                data.size,
                data.parallelNoise,
                data.spreadFactor,
                data.branchInitiationFactor,
                data.branchContinuationFactor,
                data.closeness,
                data.color,
                newTarget
        );
    }

    @Override
    public void render(@NotNull VertexConsumer consumer, Camera camera, float partialTick) {
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        Vec3 camPos = camera.getPosition();
        float x = (float) Mth.lerp(partialTick, this.xo, this.x);
        float y = (float) Mth.lerp(partialTick, this.yo, this.y);
        float z = (float) Mth.lerp(partialTick, this.zo, this.z);

        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        poseStack.translate(x - camPos.x, y - camPos.y, z - camPos.z);
        lightningRender.render(partialTick, poseStack, bufferSource);
        bufferSource.endBatch();
        poseStack.popPose();
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<LightningParticleType.Data> {
        public Factory() {
        }

        @Override
        public Particle createParticle(LightningParticleType.@NotNull Data data, @NotNull ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new LightningParticle(data, level, x, y, z, xd, yd, zd);
        }
    }
}