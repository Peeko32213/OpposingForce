package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class OPThrownItemRenderer<T extends ThrowableItemProjectile> extends EntityRenderer<T> {

    private final ItemRenderer itemRenderer;
    private final boolean fullBright;

    public OPThrownItemRenderer(EntityRendererProvider.Context context, boolean fullBright) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.fullBright = fullBright;
    }

    public OPThrownItemRenderer(EntityRendererProvider.Context context) {
        this(context, false);
    }

    @Override
    protected int getBlockLightLevel(@NotNull T entity, @NotNull BlockPos pos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(entity, pos);
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        if (entity.tickCount < 2 && this.entityRenderDispatcher.camera.getPosition().distanceToSqr(entity.position()) < 7) {
            return;
        }
        poseStack.pushPose();
        poseStack.translate(0, entity.getBbHeight() / 2, 0);
        poseStack.scale(0.8F, 0.8F, 0.8F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        this.itemRenderer.renderStatic(entity.getItem(), ItemDisplayContext.NONE, packedLight, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), entity.getId());
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T bomb) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}