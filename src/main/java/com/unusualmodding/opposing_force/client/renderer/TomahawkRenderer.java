package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.unusualmodding.opposing_force.entity.projectile.Tomahawk;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TomahawkRenderer extends EntityRenderer<Tomahawk> {

    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
    private final float scale;

    public TomahawkRenderer(EntityRendererProvider.Context context, float entityScale) {
        super(context);
        this.scale = entityScale;
    }

    public TomahawkRenderer(EntityRendererProvider.Context context) {
        this(context, 1.0F);
    }

    @Override
    public void render(Tomahawk entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25D)) {
            poseStack.pushPose();

            float tomahawkScale = 1.5F;
            poseStack.scale(this.scale, this.scale, this.scale);
            poseStack.scale(tomahawkScale, tomahawkScale, tomahawkScale);

            if (!entity.inGround) {
                poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
                poseStack.mulPose(Axis.ZP.rotationDegrees(-135.0F));

                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                poseStack.translate(0.0F, -0.175F, 0.0F);

                poseStack.mulPose(Axis.ZP.rotationDegrees(-(entity.tickCount + partialTicks) * -75 % 360));
            } else {
                poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));

                poseStack.mulPose(Axis.ZP.rotationDegrees(-140.0F));

                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                poseStack.translate(0.0F, -0.175F, 0.0F);
            }

            this.itemRenderer.renderStatic(entity.getItem(), ItemDisplayContext.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, poseStack, bufferIn, entity.level(), entity.getId());
            poseStack.popPose();
            super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Tomahawk tomahawk) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
