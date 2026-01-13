package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.unusualmodding.opposing_force.entity.projectile.ThrownLaserBlade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("deprecation")
public class ThrownLaserBladeRenderer extends EntityRenderer<ThrownLaserBlade> {

    private final ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
    private final float scale;

    public ThrownLaserBladeRenderer(EntityRendererProvider.Context context, float entityScale) {
        super(context);
        this.scale = entityScale;
    }

    public ThrownLaserBladeRenderer(EntityRendererProvider.Context context) {
        this(context, 1.0F);
    }

    @Override
    public void render(ThrownLaserBlade entity, float entityYaw, float partialTicks, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        float scale = 2.0F;
        poseStack.scale(this.scale, this.scale, this.scale);
        poseStack.scale(scale, 0.85F, scale);
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.counterclockwise ? -90 : 90));
        poseStack.translate(0, 0, -0.25F);
        poseStack.mulPose(Axis.ZP.rotation((entity.tickCount + partialTicks) * (entity.counterclockwise ? -1.25F : 1.25F)));
        this.itemRenderer.renderStatic(entity.getItem(), ItemDisplayContext.NONE, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), entity.getId());
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ThrownLaserBlade thrownLaserBlade) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}