package com.unusualmodding.opposingforce.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.unusualmodding.opposingforce.common.entity.custom.projectile.Tomahawk;
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
    public void render(Tomahawk entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25D)) {
            matrixStackIn.pushPose();

            float tomahawkScale = 1.5F;
            matrixStackIn.scale(this.scale, this.scale, this.scale);
            matrixStackIn.scale(tomahawkScale, tomahawkScale, tomahawkScale);

            if (!entity.inGround) {
                matrixStackIn.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
                matrixStackIn.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
                matrixStackIn.mulPose(Axis.ZP.rotationDegrees(-135.0F));

                matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F));
                matrixStackIn.translate(0.0F, -0.175F, 0.0F);

                matrixStackIn.mulPose(Axis.ZP.rotationDegrees(-(entity.tickCount + partialTicks) * -45 % 360));
            }
            else {
                matrixStackIn.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
                matrixStackIn.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
                matrixStackIn.mulPose(Axis.ZP.rotationDegrees(-135.0F));

                matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F));
                matrixStackIn.translate(0.0F, -0.175F, 0.0F);
            }
            this.itemRenderer.renderStatic(entity.getItem(), ItemDisplayContext.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, entity.level(), entity.getId());
            matrixStackIn.popPose();
            super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Tomahawk tomahawk) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
