package com.unusualmodding.opposing_force.client.renderer.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.TerrorModel;
import com.unusualmodding.opposing_force.entity.Terror;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TerrorGlowLayer<T extends Terror, M extends TerrorModel<T>> extends RenderLayer<T, M> {

    private static final RenderType GLOW_TEXTURE = RenderType.entityTranslucentEmissive(new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/terror/terror_glow.png"));

    public TerrorGlowLayer(RenderLayerParent<T, M> parentModel) {
        super(parentModel);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T entity, float f, float g, float h, float j, float k, float l) {
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(GLOW_TEXTURE);
        this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 0xF00000, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.5F);
    }
}


