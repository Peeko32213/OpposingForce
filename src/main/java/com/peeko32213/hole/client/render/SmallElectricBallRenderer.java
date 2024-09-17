package com.peeko32213.hole.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.hole.client.model.SmallElectricBallModel;
import com.peeko32213.hole.client.model.TerrorModel;
import com.peeko32213.hole.common.entity.EntityTerror;
import com.peeko32213.hole.common.entity.projectile.EntitySmallElectricBall;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SmallElectricBallRenderer extends GeoEntityRenderer<EntitySmallElectricBall> {

    public SmallElectricBallRenderer(EntityRendererProvider.Context context) {
        super(context, new SmallElectricBallModel());
    }

    public RenderType getRenderType(EntitySmallElectricBall animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

}
