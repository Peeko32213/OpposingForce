package com.barl_inc.opposing_force.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.barl_inc.opposing_force.client.models.entity.VoltModel;
import com.barl_inc.opposing_force.entity.Volt;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.barl_inc.opposing_force.OpposingForce.modPrefix;

@OnlyIn(Dist.CLIENT)
public class VoltGlowLayer extends RenderLayer<Volt, VoltModel> {

    private static final RenderType GLOW = RenderType.eyes(modPrefix("textures/entity/volt/volt_glow.png"));
    private static final RenderType QUASAR_GLOW = RenderType.eyes(modPrefix("textures/entity/volt/quasar_volt_glow.png"));

    public VoltGlowLayer(RenderLayerParent<Volt, VoltModel> parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, Volt volt, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!volt.isElite()) {
            VertexConsumer vertexConsumer = bufferSource.getBuffer(GLOW);
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, 0xF00000, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        else if (!volt.isInvisible() && volt.isElite()) {
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
            this.getParentModel().renderToBuffer(poseStack, bufferSource.getBuffer(QUASAR_GLOW), 0xF000F0, OverlayTexture.NO_OVERLAY, r, g, b, 1.0F);
        }
    }
}


