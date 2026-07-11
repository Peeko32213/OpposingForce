package com.barl_inc.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.barl_inc.opposing_force.entity.projectile.UmberDagger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("deprecation")
public class UmberDaggerRenderer extends EntityRenderer<UmberDagger> {

    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
    private final float scale;

    public UmberDaggerRenderer(EntityRendererProvider.Context context, float entityScale) {
        super(context);
        this.scale = entityScale;
    }

    public UmberDaggerRenderer(EntityRendererProvider.Context context) {
        this(context, 1.0F);
    }

    @Override
    public void render(UmberDagger entity, float entityYaw, float partialTicks, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLightIn) {
        poseStack.pushPose();
        float scale = 1.5F;
        poseStack.scale(this.scale, this.scale, this.scale);
        poseStack.scale(scale, scale, scale);

        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-135.0F));

        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.translate(0.0F, -0.175F, 0.0F);

        this.itemRenderer.renderStatic(entity.getItem(), ItemDisplayContext.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), entity.getId());
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLightIn);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull UmberDagger umberDagger) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
