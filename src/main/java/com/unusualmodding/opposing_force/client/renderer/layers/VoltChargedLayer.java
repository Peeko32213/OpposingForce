package com.unusualmodding.opposing_force.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.VoltModel;
import com.unusualmodding.opposing_force.entity.Volt;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class VoltChargedLayer extends EnergySwirlLayer<Volt, VoltModel> {

    private static final ResourceLocation CHARGED = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/volt/charged.png");

    private final VoltModel model;

    public VoltChargedLayer(RenderLayerParent<Volt, VoltModel> parentModel, EntityModelSet modelSet) {
        super(parentModel);
        this.model = new VoltModel(modelSet.bakeLayer(OPModelLayers.VOLT_CHARGED));
    }

    @Override
    protected float xOffset(float offset) {
        return offset * 0.01F;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedlight, Volt volt, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (volt.isPowered()) {
            float f = (float) volt.tickCount + partialTicks;
            EntityModel<Volt> entitymodel = this.model();
            entitymodel.prepareMobModel(volt, limbSwing, limbSwingAmount, partialTicks);
            this.getParentModel().copyPropertiesTo(entitymodel);
            VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.energySwirl(this.getTextureLocation(), this.xOffset(f) % 1.0F, f * 0.01F % 1.0F));
            entitymodel.setupAnim(volt, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            entitymodel.renderToBuffer(poseStack, vertexconsumer, packedlight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation() {
        return CHARGED;
    }

    @Override
    protected @NotNull EntityModel<Volt> model() {
        return this.model;
    }
}
