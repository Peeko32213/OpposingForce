package com.unusualmodding.opposing_force.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.models.entity.DicerModel;
import com.unusualmodding.opposing_force.entity.Dicer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.unusualmodding.opposing_force.OpposingForce.modPrefix;

@OnlyIn(Dist.CLIENT)
public class DicerVisorLayer extends RenderLayer<Dicer, DicerModel> {

    private static final RenderType VISOR = RenderType.eyes(modPrefix("textures/entity/dicer/dicer_visor.png"));
    private static final RenderType VISOR_LASERING = RenderType.eyes(modPrefix("textures/entity/dicer/dicer_visor_lasering.png"));

    public DicerVisorLayer(RenderLayerParent<Dicer, DicerModel> parentModel) {
        super(parentModel);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, Dicer entity, float f, float g, float h, float j, float k, float l) {
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(entity.isLasering() ? VISOR_LASERING : VISOR);
        this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 0xF00000, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}


