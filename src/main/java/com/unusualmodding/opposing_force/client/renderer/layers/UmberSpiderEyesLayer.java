package com.unusualmodding.opposing_force.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.UmberSpiderModel;
import com.unusualmodding.opposing_force.entity.UmberSpider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class UmberSpiderEyesLayer extends RenderLayer<UmberSpider, UmberSpiderModel> {

    private static final RenderType EYES = RenderType.eyes(new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/umber_spider/umber_spider_eyes.png"));
    private static final RenderType ELITE_EYES = RenderType.eyes(new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/umber_spider/tenebrous_umber_spider_eyes.png"));

    public UmberSpiderEyesLayer(RenderLayerParent<UmberSpider, UmberSpiderModel> parentModel) {
        super(parentModel);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, @NotNull UmberSpider entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexconsumer = bufferSource.getBuffer(this.renderType(entity));
        this.getParentModel().renderToBuffer(poseStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public @NotNull RenderType renderType(UmberSpider entity) {
        return entity.isElite() ? ELITE_EYES : EYES;
    }
}