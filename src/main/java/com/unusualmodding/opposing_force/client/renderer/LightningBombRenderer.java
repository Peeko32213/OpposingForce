package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.unusualmodding.opposing_force.entity.projectile.LightningBomb;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import static com.unusualmodding.opposing_force.OpposingForce.modPrefix;

@OnlyIn(Dist.CLIENT)
public class LightningBombRenderer extends EntityRenderer<LightningBomb> {

    private static final ResourceLocation LIGHTNING_BOMB = modPrefix("textures/item/lightning_bomb.png");

    public LightningBombRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(LightningBomb entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
        poseStack.pushPose();
        float swelling = entity.getSwelling(partialTicks);
        float scale = 0.5F + swelling * 0.6F;

        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.translate(0, entity.getBbHeight() / 2F, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot()) - entity.getSpin(partialTicks)));
        poseStack.translate(0, -entity.getBbHeight() / 2F, 0);
        poseStack.scale(scale, scale, scale);

        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        VertexConsumer consumer1 = multiBufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(entity)));
        vertex(consumer1, matrix4f, matrix3f, packedLight, 0.0F, 0, 0, 1, 1F, entity, partialTicks);
        vertex(consumer1, matrix4f, matrix3f, packedLight, 1.0F, 0, 1, 1, 1F, entity, partialTicks);
        vertex(consumer1, matrix4f, matrix3f, packedLight, 1.0F, 1, 1, 0, 1F, entity, partialTicks);
        vertex(consumer1, matrix4f, matrix3f, packedLight, 0.0F, 1, 0, 0, 1F, entity, partialTicks);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int i, float x, int y, int u, int v, float alpha, LightningBomb entity, float partialTicks) {
        vertexConsumer.vertex(matrix4f, x - 0.5F, (float) y - 0.25F, 0.0F).color(1F, 1F, 1F, alpha).uv((float) u, (float) v).overlayCoords(OverlayTexture.pack(OverlayTexture.u(getSwellingProgress(entity, partialTicks)), 10)).uv2(i).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    protected static float getSwellingProgress(LightningBomb entity, float partialTicks) {
        float swelling = entity.getSwelling(partialTicks);
        return Mth.clamp(1F - 0.5F * (1F + Mth.sin((entity.tickCount + partialTicks) * (swelling / 2))), 0F, 1F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull LightningBomb lightningBomb) {
        return LIGHTNING_BOMB;
    }
}
