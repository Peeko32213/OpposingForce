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
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class DicerLaserRenderer extends EntityRenderer<DicerLaser> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/dicer_laser.png");

    private static final float TEXTURE_WIDTH = 256;
    private static final float TEXTURE_HEIGHT = 32;
    private static final float START_RADIUS = 0.7f;
    private static final float BEAM_RADIUS = 0.9f;

    public DicerLaserRenderer(EntityRendererProvider.Context mgr) {
        super(mgr);
    }

    @Override
    public ResourceLocation getTextureLocation(DicerLaser entity) {
        return DicerLaserRenderer.TEXTURE;
    }

    @Override
    public void render(DicerLaser laser, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        double collidePosX = laser.prevCollidePosX + (laser.collidePosX - laser.prevCollidePosX) * delta;
        double collidePosY = laser.prevCollidePosY + (laser.collidePosY - laser.prevCollidePosY) * delta;
        double collidePosZ = laser.prevCollidePosZ + (laser.collidePosZ - laser.prevCollidePosZ) * delta;
        double posX = laser.xo + (laser.getX() - laser.xo) * delta;
        double posY = laser.yo + (laser.getY() - laser.yo) * delta;
        double posZ = laser.zo + (laser.getZ() - laser.zo) * delta;
        float yaw = laser.prevYaw + (laser.renderYaw - laser.prevYaw) * delta;
        float pitch = laser.prevPitch + (laser.renderPitch - laser.prevPitch) * delta;

        float length = (float) Math.sqrt(Math.pow(collidePosX - posX, 2) + Math.pow(collidePosY - posY, 2) + Math.pow(collidePosZ - posZ, 2));
        int frame = Mth.floor((laser.appear.getTimer() - 1 + delta) * 2);
        if (frame < 0) {
            frame = 6;
        }
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(OPRenderTypes.glowingEffect(getTextureLocation(laser)));

        renderStart(frame, matrixStackIn, ivertexbuilder, packedLightIn);
        renderBeam(length, 180f / (float) Math.PI * yaw, 180f / (float) Math.PI * pitch, frame, matrixStackIn, ivertexbuilder, packedLightIn);

        matrixStackIn.pushPose();
        matrixStackIn.translate(collidePosX - posX, collidePosY - posY, collidePosZ - posZ);
        renderEnd(frame, laser.blockSide, matrixStackIn, ivertexbuilder, packedLightIn);
        matrixStackIn.popPose();
    }

    private void renderFlatQuad(int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        float minU = 0 + 16F / TEXTURE_WIDTH * frame;
        float minV = 0;
        float maxU = minU + 16F / TEXTURE_WIDTH;
        float maxV = minV + 16F / TEXTURE_HEIGHT;
        PoseStack.Pose matrixstack$entry = matrixStackIn.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();
        drawVertex(matrix4f, matrix3f, builder, -START_RADIUS, -START_RADIUS, 0, minU, minV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, -START_RADIUS, START_RADIUS, 0, minU, maxV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, START_RADIUS, START_RADIUS, 0, maxU, maxV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, START_RADIUS, -START_RADIUS, 0, maxU, minV, 1, packedLightIn);
    }

    private void renderStart(int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        matrixStackIn.pushPose();
        Quaternionf quat = this.entityRenderDispatcher.cameraOrientation();
        matrixStackIn.mulPose(quat);
        renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
        matrixStackIn.popPose();
    }

    private void renderEnd(int frame, Direction side, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        matrixStackIn.pushPose();
        Quaternionf quat = this.entityRenderDispatcher.cameraOrientation();
        matrixStackIn.mulPose(quat);
        renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
        matrixStackIn.popPose();
        if (side == null) {
            return;
        }
        matrixStackIn.pushPose();
        Quaternionf sideQuat = side.getRotation();
        sideQuat.mul(quatFromRotationXYZ(90, 0, 0, true));
        matrixStackIn.mulPose(sideQuat);
        matrixStackIn.translate(0, 0, -0.01f);
        renderFlatQuad(frame, matrixStackIn, builder, packedLightIn);
        matrixStackIn.popPose();
    }

    private void drawBeam(float length, int frame, PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        float minU = 0;
        float minV = 16 / TEXTURE_HEIGHT + 1 / TEXTURE_HEIGHT * frame;
        float maxU = minU + 20 / TEXTURE_WIDTH;
        float maxV = minV + 1 / TEXTURE_HEIGHT;
        float offset = 0;

        PoseStack.Pose matrixstack$entry = matrixStackIn.last();
        Matrix4f matrix4f = matrixstack$entry.pose();
        Matrix3f matrix3f = matrixstack$entry.normal();

        drawVertex(matrix4f, matrix3f, builder, -BEAM_RADIUS, offset, 0, minU, minV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, -BEAM_RADIUS, length, 0, minU, maxV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, BEAM_RADIUS, length, 0, maxU, maxV, 1, packedLightIn);
        drawVertex(matrix4f, matrix3f, builder, BEAM_RADIUS, offset, 0, maxU, minV, 1, packedLightIn);
    }

    private void renderBeam(float length, float yaw, float pitch, int frame,  PoseStack matrixStackIn, VertexConsumer builder, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(quatFromRotationXYZ(90, 0, 0, true));
        matrixStackIn.mulPose(quatFromRotationXYZ(0, 0, yaw - 90f, true));
        matrixStackIn.mulPose(quatFromRotationXYZ(-pitch, 0, 0, true));
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(quatFromRotationXYZ(0, Minecraft.getInstance().gameRenderer.getMainCamera().getXRot() + 90, 0, true));

        drawBeam(length, frame, matrixStackIn, builder, packedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.mulPose(quatFromRotationXYZ(0, -Minecraft.getInstance().gameRenderer.getMainCamera().getXRot() - 90, 0, true));
        drawBeam(length, frame, matrixStackIn, builder, packedLightIn);
        matrixStackIn.popPose();
        matrixStackIn.popPose();
    }

    public void drawVertex(Matrix4f matrix, Matrix3f normals, VertexConsumer vertexBuilder, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, float alpha, int packedLightIn) {
        vertexBuilder.vertex(matrix, offsetX, offsetY, offsetZ).color(1, 1, 1, 1 * alpha).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normals, 0.0F, 1.0F, 0.0F).endVertex();
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

