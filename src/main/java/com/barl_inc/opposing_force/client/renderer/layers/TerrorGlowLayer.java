package com.barl_inc.opposing_force.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.client.models.entity.TerrorModel;
import com.barl_inc.opposing_force.entity.Terror;
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
public class TerrorGlowLayer extends RenderLayer<Terror, TerrorModel> {

    private static final RenderType TERROR_GLOW = RenderType.eyes(new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/terror/terror_glow.png"));
    private static final RenderType ANTEDILUVIAN_TERROR_GLOW = RenderType.eyes(new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/terror/antediluvian_terror_glow.png"));

    public TerrorGlowLayer(RenderLayerParent<Terror, TerrorModel> parentModel) {
        super(parentModel);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, MultiBufferSource multiBufferSource, int i, Terror entity, float f, float g, float h, float j, float k, float l) {
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(entity.isElite() ? ANTEDILUVIAN_TERROR_GLOW : TERROR_GLOW);
        this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 0xF00000, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}


