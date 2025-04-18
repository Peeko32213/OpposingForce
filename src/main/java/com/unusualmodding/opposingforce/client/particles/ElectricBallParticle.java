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
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector4f;

public class ElectricBallParticle extends Particle {

    private LightningRender lightningRender = new LightningRender();

    public ElectricBallParticle(ClientLevel world, double x, double y, double z, double xd, double yd, double zd) {
        super(world, x, y, z);
        this.setSize(3.0F, 3.0F);
        this.x = x;
        this.y = y;
        this.z = z;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        Vec3 lightningTo = findLightningToPos(world, x, y, z, 2 + random.nextInt(2));
        Vec3 to = lightningTo.subtract(x, y, z);
        this.lifetime = (int) Math.ceil(to.length());
        int sections = 8 * this.lifetime;
        boolean lightBlue = random.nextBoolean();
        LightningBoltData.BoltRenderInfo boltData = new LightningBoltData.BoltRenderInfo(0.3F, 0.125F, 0.25F, 0.66F, new Vector4f(lightBlue ? 0.05F : 0.15F, 0.5F, lightBlue ? 0.9F : 0.75F, 0.75F), 0.15F);
        LightningBoltData bolt = new LightningBoltData(boltData, Vec3.ZERO, to, sections)
                .size(0.05F + random.nextFloat() * 0.08F)
                .lifespan(this.lifetime + 1)
                .spawn(LightningBoltData.SpawnFunction.CONSECUTIVE);
        lightningRender.update(this, bolt, 1.0F);
    }

    public boolean shouldCull() {
        return false;
    }

    private Vec3 findLightningToPos(ClientLevel world, double x, double y, double z, int range) {
        Vec3 vec3 = new Vec3(x, y, z);
        for (int i = 0; i < 10; i++) {
            Vec3 vec31 = vec3.add(random.nextFloat() * range - range / 2F, random.nextFloat() * range - range / 2F, random.nextFloat() * range - range / 2F);
            if (canSeeBlock(vec3, vec31)) {
                return vec31;
            }
        }
        return vec3;
    }

    private boolean canSeeBlock(Vec3 from, Vec3 to) {
        BlockHitResult result = this.level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null));
        return Vec3.atCenterOf(result.getBlockPos()).distanceTo(to) < 3.0F;
    }


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

    public void render(VertexConsumer consumer, Camera camera, float partialTick) {
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        Vec3 cameraPos = camera.getPosition();
        float x = (float) (Mth.lerp(partialTick, this.xo, this.x));
        float y = (float) (Mth.lerp(partialTick, this.yo, this.y));
        float z = (float) (Mth.lerp(partialTick, this.zo, this.z));
        PoseStack posestack = new PoseStack();
        posestack.pushPose();
        posestack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        posestack.translate(x, y, z);
        lightningRender.render(partialTick, posestack, multibuffersource$buffersource);
        multibuffersource$buffersource.endBatch();
        posestack.popPose();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    @OnlyIn(Dist.CLIENT)
    public static class ElectricOrbFactory implements ParticleProvider<SimpleParticleType> {

        public ElectricOrbFactory() {
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ElectricBallParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}