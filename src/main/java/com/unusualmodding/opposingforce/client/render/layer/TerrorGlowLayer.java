package com.unusualmodding.opposingforce.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.common.entity.custom.monster.TerrorEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class TerrorGlowLayer extends GeoRenderLayer<TerrorEntity> {
    private static final ResourceLocation OVERLAY = new ResourceLocation(OpposingForce.MODID, "textures/entity/eyes/terror.png");
    private static final ResourceLocation MODEL = new ResourceLocation(OpposingForce.MODID, "geo/terror.geo.json");

    public TerrorGlowLayer(GeoRenderer<TerrorEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, TerrorEntity entityLivingBaseIn, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType cameo = RenderType.entityCutout(OVERLAY);
        getRenderer().reRender(this.getGeoModel().getBakedModel(MODEL), poseStack, bufferSource, entityLivingBaseIn, renderType,
                bufferSource.getBuffer(cameo), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                1, 1, 1, 1);
    }

}


