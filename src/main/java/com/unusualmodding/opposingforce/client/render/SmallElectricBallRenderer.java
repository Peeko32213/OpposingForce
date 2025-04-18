package com.unusualmodding.opposingforce.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.unusualmodding.opposingforce.client.model.SmallElectricBallModel;
import com.unusualmodding.opposingforce.common.entity.projectile.SmallElectricBall;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SmallElectricBallRenderer extends GeoEntityRenderer<SmallElectricBall> {

    public SmallElectricBallRenderer(EntityRendererProvider.Context context) {
        super(context, new SmallElectricBallModel());
    }

    public RenderType getRenderType(SmallElectricBall animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public void render(SmallElectricBall pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        // Prepare the matrix stack for transformations
        pMatrixStack.pushPose();

        // Scale the entity based on its size
        // Orient the entity to face the camera
        pMatrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        // Get the current pose (translation, rotation, scale) from the pose stack
        PoseStack.Pose currentPose = pMatrixStack.last();
        Matrix4f matrix4f = currentPose.pose();  // The position transformation matrix
        Matrix3f matrix3f = currentPose.normal();  // The normal transformation matrix

        // Pop the matrix stack to revert transformations
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }



    protected int getBlockLightLevel(SmallElectricBall p_174146_, BlockPos p_174147_) {
        return Math.max(15, super.getBlockLightLevel(p_174146_, p_174147_));
    }



}
