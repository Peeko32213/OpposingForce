package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.unusualmodding.opposing_force.entity.projectile.AbstractBomb;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public abstract class AbstractBombRenderer<T extends AbstractBomb> extends EntityRenderer<T> {

    public AbstractBombRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(T bomb, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight) {
        Vec3 directionTowardsCamera = this.entityRenderDispatcher.camera.getPosition().subtract(bomb.getPosition(partialTicks)).normalize();
        Vec3 dir = directionTowardsCamera.scale(8 / 16.0F);
        float bombFuse = bomb.getFuse(partialTicks);
        float bombFlash = 1F - 0.5F * (1F + Mth.sin((bomb.tickCount + partialTicks) * (bombFuse / 2)));
        int light = LightTexture.pack(Math.max((int) (bombFlash * 16), bomb.level().getBrightness(LightLayer.BLOCK, bomb.blockPosition())), bomb.level().getBrightness(LightLayer.SKY, bomb.blockPosition()));
        int overlay = OverlayTexture.pack(OverlayTexture.u(bombFlash), 10);

        poseStack.pushPose();

        poseStack.translate(dir.x(), dir.y(), dir.z());
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.ZP.rotation(bomb.getSpin(partialTicks)));
        poseStack.translate(0, -bomb.getBbHeight() / 2F, 0);

        poseStack.translate(0, bomb.getBbHeight() * 0.5F, 0);
        float size = 1;
        size *= (float) (1 + Mth.sqrt(bombFlash) * 0.2);
        size *= 0.6F;
        poseStack.scale(size, size, size);

        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(bomb)));
        vertex(vertexConsumer, matrix4f, matrix3f, light, 0.0F, 0, 0, 1, overlay);
        vertex(vertexConsumer, matrix4f, matrix3f, light, 1.0F, 0, 1, 1, overlay);
        vertex(vertexConsumer, matrix4f, matrix3f, light, 1.0F, 1, 1, 0, overlay);
        vertex(vertexConsumer, matrix4f, matrix3f, light, 0.0F, 1, 0, 0, overlay);

        poseStack.popPose();
        super.render(bomb, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int i, float x, int y, int u, int v, int overlay) {
        vertexConsumer.vertex(matrix4f, x - 0.5F, (float) y - 0.25F, 0.0F).color(1F, 1F, 1F, 1F).uv((float) u, (float) v).overlayCoords(overlay).uv2(i).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }
}