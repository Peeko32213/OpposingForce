package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.VoltModel;
import com.unusualmodding.opposing_force.entity.VoltEntity;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class VoltRenderer extends MobRenderer<VoltEntity, VoltModel<VoltEntity>> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID,"textures/entity/volt.png");

    public VoltRenderer(EntityRendererProvider.Context context) {
        super(context, new VoltModel<>(context.bakeLayer(OPModelLayers.VOLT_LAYER)), 0.5F);
    }

    protected void scale(VoltEntity entity, PoseStack poseStack, float partialTicks) {
        poseStack.scale(0.999F, 0.999F, 0.999F);
        poseStack.translate(0.0F, 0.05F, 0.0F);
        float squish = Mth.lerp(partialTicks, entity.oSquish, entity.squish) / (0.5F + 1.0F);
        float scale = 1.0F / (squish + 1.0F);
        poseStack.scale(scale, 1.0F / scale, scale);
    }

    @Override
    public ResourceLocation getTextureLocation(VoltEntity entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderType getRenderType(VoltEntity entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }
}
