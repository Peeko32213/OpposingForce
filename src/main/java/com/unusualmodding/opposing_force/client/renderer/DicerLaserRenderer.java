package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.projectile.DicerLaser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class DicerLaserRenderer extends EntityRenderer<DicerLaser> {

    private static final ResourceLocation LASER = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/dicer/laser.png");
    private static final ResourceLocation ARCH_LASER = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/dicer/arch_laser.png");
    private static final ResourceLocation GIGAN_LASER = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/dicer/gigan_laser.png");

    private static final float TEXTURE_WIDTH = 256;
    private static final float TEXTURE_HEIGHT = 32;
    private static final float START_RADIUS = 0.7F;
    private static final float BEAM_RADIUS = 0.9F;

    public DicerLaserRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull DicerLaser entity) {
//        if (entity.caster.getName().getString().contains("gigan")) {
//            return GIGAN_LASER;
//        }
        return entity.isFiery() ? ARCH_LASER : LASER;
    }

    @Override
    public void render(DicerLaser laser, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        double collidePosX = laser.prevCollidePosX + (laser.collidePosX - laser.prevCollidePosX) * partialTicks;
        double collidePosY = laser.prevCollidePosY + (laser.collidePosY - laser.prevCollidePosY) * partialTicks;
        double collidePosZ = laser.prevCollidePosZ + (laser.collidePosZ - laser.prevCollidePosZ) * partialTicks;
        double posX = laser.xo + (laser.getX() - laser.xo) * partialTicks;
        double posY = laser.yo + (laser.getY() - laser.yo) * partialTicks;
        double posZ = laser.zo + (laser.getZ() - laser.zo) * partialTicks;
        float yaw = laser.prevYaw + (laser.renderYaw - laser.prevYaw) * partialTicks;
        float pitch = laser.prevPitch + (laser.renderPitch - laser.prevPitch) * partialTicks;

        float length = (float) Math.sqrt(Math.pow(collidePosX - posX, 2) + Math.pow(collidePosY - posY, 2) + Math.pow(collidePosZ - posZ, 2));
        int frame = Mth.floor((laser.appear.getTimer() - 1 + partialTicks) * 2);
        if (frame < 0) {
            frame = 6;
        }
        VertexConsumer vertexConsumer = bufferSource.getBuffer(OPRenderTypes.glowingEffect(getTextureLocation(laser)));

        renderStart(frame, poseStack, vertexConsumer, packedLight);
        renderBeam(length, 180F / (float) Math.PI * yaw, 180F / (float) Math.PI * pitch, frame, poseStack, vertexConsumer, packedLight);

        poseStack.pushPose();
        poseStack.translate(collidePosX - posX, collidePosY - posY, collidePosZ - posZ);
        renderEnd(frame, laser.blockSide, poseStack, vertexConsumer, packedLight);
        poseStack.popPose();
    }

    private void renderFlatQuad(int frame, PoseStack poseStack, VertexConsumer consumer, int packedLight) {
        float minU = 0 + 16F / TEXTURE_WIDTH * frame;
        float minV = 0;
        float maxU = minU + 16F / TEXTURE_WIDTH;
        float maxV = minV + 16F / TEXTURE_HEIGHT;
        PoseStack.Pose matrixstack$entry = poseStack.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        drawVertex(matrix4f, matrix3f, consumer, -START_RADIUS, -START_RADIUS, 0, minU, minV, 1, packedLight);
        drawVertex(matrix4f, matrix3f, consumer, -START_RADIUS, START_RADIUS, 0, minU, maxV, 1, packedLight);
        drawVertex(matrix4f, matrix3f, consumer, START_RADIUS, START_RADIUS, 0, maxU, maxV, 1, packedLight);
        drawVertex(matrix4f, matrix3f, consumer, START_RADIUS, -START_RADIUS, 0, maxU, minV, 1, packedLight);
    }

    private void renderStart(int frame, PoseStack poseStack, VertexConsumer consumer, int packedLight) {
        poseStack.pushPose();
        Quaternionf quat = this.entityRenderDispatcher.cameraOrientation();
        poseStack.mulPose(quat);
        renderFlatQuad(frame, poseStack, consumer, packedLight);
        poseStack.popPose();
    }

    private void renderEnd(int frame, Direction direction, PoseStack poseStack, VertexConsumer consumer, int packedLight) {
        poseStack.pushPose();
        Quaternionf quat = this.entityRenderDispatcher.cameraOrientation();
        poseStack.mulPose(quat);
        renderFlatQuad(frame, poseStack, consumer, packedLight);
        poseStack.popPose();
        if (direction == null) {
            return;
        }
        poseStack.pushPose();
        Quaternionf sideQuat = direction.getRotation();
        sideQuat.mul(quatFromRotationXYZ(90, 0, 0, true));
        poseStack.mulPose(sideQuat);
        poseStack.translate(0, 0, -0.01F);
        renderFlatQuad(frame, poseStack, consumer, packedLight);
        poseStack.popPose();
    }

    private void drawBeam(float length, int frame, PoseStack poseStack, VertexConsumer consumer, int packedLight) {
        float minU = 0;
        float minV = 16 / TEXTURE_HEIGHT + 1 / TEXTURE_HEIGHT * frame;
        float maxU = minU + 20 / TEXTURE_WIDTH;
        float maxV = minV + 1 / TEXTURE_HEIGHT;
        float offset = 0;

        PoseStack.Pose last = poseStack.last();
        Matrix4f matrix4f = last.pose();
        Matrix3f matrix3f = last.normal();

        drawVertex(matrix4f, matrix3f, consumer, -BEAM_RADIUS, offset, 0, minU, minV, 1, packedLight);
        drawVertex(matrix4f, matrix3f, consumer, -BEAM_RADIUS, length, 0, minU, maxV, 1, packedLight);
        drawVertex(matrix4f, matrix3f, consumer, BEAM_RADIUS, length, 0, maxU, maxV, 1, packedLight);
        drawVertex(matrix4f, matrix3f, consumer, BEAM_RADIUS, offset, 0, maxU, minV, 1, packedLight);
    }

    private void renderBeam(float length, float yaw, float pitch, int frame, PoseStack poseStack, VertexConsumer consumer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(quatFromRotationXYZ(90, 0, 0, true));
        poseStack.mulPose(quatFromRotationXYZ(0, 0, yaw - 90f, true));
        poseStack.mulPose(quatFromRotationXYZ(-pitch, 0, 0, true));
        poseStack.pushPose();
        poseStack.mulPose(quatFromRotationXYZ(0, Minecraft.getInstance().gameRenderer.getMainCamera().getXRot() + 90, 0, true));

        drawBeam(length, frame, poseStack, consumer, packedLight);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.mulPose(quatFromRotationXYZ(0, -Minecraft.getInstance().gameRenderer.getMainCamera().getXRot() - 90, 0, true));
        drawBeam(length, frame, poseStack, consumer, packedLight);
        poseStack.popPose();
        poseStack.popPose();
    }

    public void drawVertex(Matrix4f matrix, Matrix3f normals, VertexConsumer consumer, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, float alpha, int packedLight) {
        consumer.vertex(matrix, offsetX, offsetY, offsetZ).color(1, 1, 1, 1 * alpha).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(normals, 0.0F, 1.0F, 0.0F).endVertex();
    }

    public static Quaternionf quatFromRotationXYZ(float x, float y, float z, boolean degrees) {
        if (degrees) {
            x *= ((float) Math.PI / 180F);
            y *= ((float) Math.PI / 180F);
            z *= ((float) Math.PI / 180F);
        }
        return (new Quaternionf()).rotationXYZ(x, y, z);
    }
}

