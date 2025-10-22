package com.unusualmodding.opposing_force.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.RamblerModel;
import com.unusualmodding.opposing_force.entity.Rambler;
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
public class RamblerSkullLayer extends RenderLayer<Rambler, RamblerModel> {

    private static final RenderType SKULL1 = RenderType.entityCutoutNoCull(new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/rambler/rambler_skull1.png"));

    public RamblerSkullLayer(RenderLayerParent<Rambler, RamblerModel> parentModel) {
        super(parentModel);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, MultiBufferSource multiBufferSource, int i, @NotNull Rambler entity, float f, float g, float h, float j, float k, float l) {
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(SKULL1);
        this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 0xF00000, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
