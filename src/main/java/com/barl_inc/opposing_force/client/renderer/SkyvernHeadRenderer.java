package com.barl_inc.opposing_force.client.renderer;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.client.models.entity.skyvern.SkyvernModel;
import com.barl_inc.opposing_force.entity.Skyvern;
import com.barl_inc.opposing_force.registry.OPModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SkyvernHeadRenderer extends MobRenderer<Skyvern, SkyvernModel> {

    private static final ResourceLocation CLOUDY = OpposingForce.location("textures/entity/skyvern/cloudy.png");
    private static final ResourceLocation AZURE = OpposingForce.location("textures/entity/skyvern/azure.png");
    private static final ResourceLocation THUNDER = OpposingForce.location("textures/entity/skyvern/thunder.png");

    public SkyvernHeadRenderer(EntityRendererProvider.Context context) {
        super(context, new SkyvernModel(context.bakeLayer(OPModelLayers.SKYVERN)), 0.5F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Skyvern entity) {
        return switch (entity.getVariant()) {
            case CLOUDY -> CLOUDY;
            case AZURE -> AZURE;
            case THUNDER -> THUNDER;
        };
    }

    @Override
    protected float getFlipDegrees(@NotNull Skyvern entity) {
        return 0.0F;
    }

    @Override
    protected void setupRotations(@NotNull Skyvern entity, @NotNull PoseStack poseStack, float bob, float yaw, float partialTicks) {
        if (this.isShaking(entity)) {
            yaw += (float)(Math.cos((double) entity.tickCount * 3.25D) * Math.PI * (double) 0.4F);
        }

        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - yaw));
        poseStack.translate(0F, 1F, 0);
        poseStack.mulPose(Axis.XP.rotationDegrees(-entity.getViewXRot(partialTicks)));
        poseStack.translate(0F, -1F, 0);

        if (isEntityUpsideDown(entity)) {
            poseStack.translate(0.0F, entity.getBbHeight() + 0.1F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }
    }
}
