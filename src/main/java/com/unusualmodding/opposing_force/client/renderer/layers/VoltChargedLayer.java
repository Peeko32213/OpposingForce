package com.unusualmodding.opposing_force.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.VoltModel;
import com.unusualmodding.opposing_force.client.renderer.OPRenderTypes;
import com.unusualmodding.opposing_force.entity.Volt;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class VoltChargedLayer extends RenderLayer<Volt, VoltModel> {

    private static final ResourceLocation CHARGED = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/volt/charged.png");
    private static final ResourceLocation CHARGED_QUASAR = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/volt/charged_quasar.png");

    private final VoltModel model;

    public VoltChargedLayer(RenderLayerParent<Volt, VoltModel> parentModel, EntityModelSet modelSet) {
        super(parentModel);
        this.model = new VoltModel(modelSet.bakeLayer(OPModelLayers.VOLT_CHARGED));
    }

    protected float xOffset(float offset) {
        return offset * 0.01F;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedlight, Volt volt, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
//        VertexConsumer vertexconsumer = bufferSource.getBuffer(OPRenderTypes.specialGlint(volt.isElite() ? CHARGED_QUASAR : CHARGED, false, true));
        float f = (float) volt.tickCount + partialTicks;
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.energySwirl(volt.isElite() ? CHARGED_QUASAR : CHARGED, this.xOffset(f) % 1.0F, f * 0.01F % 1.0F));
        EntityModel<Volt> entitymodel = this.model();
        entitymodel.prepareMobModel(volt, limbSwing, limbSwingAmount, partialTicks);
        this.getParentModel().copyPropertiesTo(entitymodel);
        entitymodel.setupAnim(volt, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (volt.isPowered()) {
            if (volt.isElite()) {
                int i = volt.tickCount / 25 + volt.getId();
                int length = DyeColor.values().length;
                int i1 = i % length;
                int i2 = (i + 1) % length;
                float time = ((float) (volt.tickCount % 25) + partialTicks) / 25.0F;
                float[] colorArray = Sheep.getColorArray(DyeColor.byId(i1));
                float[] colorArray1 = Sheep.getColorArray(DyeColor.byId(i2));
                float r = colorArray[0] * (1.0F - time) + colorArray1[0] * time;
                float g = colorArray[1] * (1.0F - time) + colorArray1[1] * time;
                float b = colorArray[2] * (1.0F - time) + colorArray1[2] * time;
                entitymodel.renderToBuffer(poseStack, vertexconsumer, 0xF000F0, OverlayTexture.NO_OVERLAY, r, g, b, 1.0F);
            } else {

                entitymodel.renderToBuffer(poseStack, vertexconsumer, 0xF000F0, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    protected @NotNull EntityModel<Volt> model() {
        return this.model;
    }
}
