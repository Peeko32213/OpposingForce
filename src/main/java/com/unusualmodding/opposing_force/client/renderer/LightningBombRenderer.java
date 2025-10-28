package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.unusualmodding.opposing_force.entity.projectile.LightningBomb;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
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
    public void render(LightningBomb bomb, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int packedLight) {
        Vec3 directionTowardsCamera = this.entityRenderDispatcher.camera.getPosition().subtract(bomb.getPosition(partialTicks)).normalize();
        Vec3 dir = directionTowardsCamera.scale(8 / 16.0F);
        float bombFuse = bomb.getFuse(partialTicks);
        float bombFlash = 1F - 0.5F * (1F + Mth.sin((bomb.tickCount + partialTicks) * (bombFuse / 2)));
        int overlay = OverlayTexture.pack(OverlayTexture.u(bombFlash), 10);

        poseStack.pushPose();

        poseStack.translate(dir.x(), dir.y(), dir.z());
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.ZP.rotation(bomb.getSpin(partialTicks)));
        poseStack.translate(0, -bomb.getBbHeight() / 2F, 0);

        PoseStack.Pose pose = poseStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(bomb)));

        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.0F, 0, 0, 1, overlay);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 1.0F, 0, 1, 1, overlay);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 1.0F, 1, 1, 0, overlay);
        vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.0F, 1, 0, 0, overlay);

        poseStack.popPose();
        super.render(bomb, entityYaw, partialTicks, poseStack, multiBufferSource, packedLight);
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int i, float x, int y, int u, int v, int overlay) {
        vertexConsumer.vertex(matrix4f, x - 0.5F, (float) y - 0.25F, 0.0F).color(1F, 1F, 1F, 1F).uv((float) u, (float) v).overlayCoords(overlay).uv2(i).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull LightningBomb lightningBomb) {
        return LIGHTNING_BOMB;
    }
}
