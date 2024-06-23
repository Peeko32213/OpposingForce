package com.peeko32213.hole.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.peeko32213.hole.Hole;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.SpiderEyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class HoleSpiderEyeLayer <T extends LivingEntity & GeoEntity> extends GeoRenderLayer<T> {
    private final String loc;

    public HoleSpiderEyeLayer(String loc, GeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
        this.loc = loc;
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType eyes = RenderType.eyes(new ResourceLocation(Hole.MODID, "textures/entity/eyes/" + loc + ".png"));
        VertexConsumer vertexConsumer = bufferSource.getBuffer(eyes);
        ResourceLocation modelLoc = new ResourceLocation(Hole.MODID, "geo/entity/" + loc + ".geo.json");

        this.getRenderer().reRender(this.getGeoModel().getBakedModel(modelLoc), poseStack, bufferSource, animatable, eyes, vertexConsumer, partialTick, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

}
