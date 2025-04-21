package com.unusualmodding.opposingforce.client.particles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class ElectricBallParticle extends Particle {

    private final LightningRender lightningRender = new LightningRender();

    public ElectricBallParticle(LightningBallParticleType.Data data, ClientLevel world, double x, double y, double z, double xd, double yd, double zd) {
        super(world, x, y, z);
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

    private Vec3 resolveTarget(LightningBallParticleType.Data data, ClientLevel level, Vec3 from) {
        return switch (data.targetType) {
            case ENTITY -> {
                Entity entity = level.getEntity(data.entitySourceId);
                yield (entity != null) ? entity.position().add(0, entity.getBbHeight() * 0.5, 0) : from;
            }
            case POSITION -> canSeeBlock(from, data.targetPos) ? data.targetPos : from;
            case RANDOM -> findRandomLightningTarget(level, from, data.range + random.nextInt(2));
        };
    }

    private Vec3 findRandomLightningTarget(ClientLevel world, Vec3 origin, int range) {
        for (int i = 0; i < 10; i++) {
            Vec3 candidate = origin.add(
                    random.nextFloat() * range - range / 2F,
                    random.nextFloat() * range - range / 2F,
                    random.nextFloat() * range - range / 2F
            );
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
            this.remove();
        } else {
            this.move(this.xd, this.yd, this.zd);
            this.yd -= this.gravity;
        }
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTick) {
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
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    public static class ElectricOrbFactory implements ParticleProvider<LightningBallParticleType.Data> {
        public ElectricOrbFactory() {}

        @Override
        public Particle createParticle(LightningBallParticleType.Data data, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new ElectricBallParticle(data, level, x, y, z, xd, yd, zd);
        }
    }
}