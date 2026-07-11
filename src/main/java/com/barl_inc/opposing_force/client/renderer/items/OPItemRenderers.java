package com.barl_inc.opposing_force.client.renderer.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.client.models.item.BlasterModel;
import com.barl_inc.opposing_force.registry.OPItems;
import com.barl_inc.opposing_force.registry.OPModelLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OPItemRenderers extends BlockEntityWithoutLevelRenderer {

    private static final BlasterModel BLASTER_MODEL = new BlasterModel(Minecraft.getInstance().getEntityModels().bakeLayer(OPModelLayers.BLASTER));

    private static final ResourceLocation BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster.png");
    private static final ResourceLocation BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow.png");
    private static final ResourceLocation WHITE_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_white.png");
    private static final ResourceLocation WHITE_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_white.png");
    private static final ResourceLocation LIGHT_GRAY_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_light_gray.png");
    private static final ResourceLocation LIGHT_GRAY_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_light_gray.png");
    private static final ResourceLocation GRAY_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_gray.png");
    private static final ResourceLocation GRAY_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_gray.png");
    private static final ResourceLocation BLACK_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_black.png");
    private static final ResourceLocation BLACK_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_black.png");
    private static final ResourceLocation BROWN_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_brown.png");
    private static final ResourceLocation BROWN_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_brown.png");
    private static final ResourceLocation RED_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_red.png");
    private static final ResourceLocation RED_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_red.png");
    private static final ResourceLocation ORANGE_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_orange.png");
    private static final ResourceLocation ORANGE_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_orange.png");
    private static final ResourceLocation YELLOW_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_yellow.png");
    private static final ResourceLocation YELLOW_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_yellow.png");
    private static final ResourceLocation LIME_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_lime.png");
    private static final ResourceLocation LIME_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_lime.png");
    private static final ResourceLocation GREEN_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_green.png");
    private static final ResourceLocation GREEN_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_green.png");
    private static final ResourceLocation CYAN_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_cyan.png");
    private static final ResourceLocation CYAN_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_cyan.png");
    private static final ResourceLocation LIGHT_BLUE_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_light_blue.png");
    private static final ResourceLocation LIGHT_BLUE_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_light_blue.png");
    private static final ResourceLocation BLUE_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_blue.png");
    private static final ResourceLocation BLUE_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_blue.png");
    private static final ResourceLocation PURPLE_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_purple.png");
    private static final ResourceLocation PURPLE_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_purple.png");
    private static final ResourceLocation MAGENTA_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_magenta.png");
    private static final ResourceLocation MAGENTA_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_magenta.png");
    private static final ResourceLocation PINK_BLASTER_TEXTURE = OpposingForce.modPrefix("textures/item/blaster_pink.png");
    private static final ResourceLocation PINK_BLASTER_TEXTURE_GLOW = OpposingForce.modPrefix("textures/item/blaster_glow_pink.png");

    public OPItemRenderers() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(@NotNull ItemStack stack, @NotNull ItemDisplayContext context, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int overlay) {
        float partialTicks = Minecraft.getInstance().getPartialTick();
        Player player = Minecraft.getInstance().player;
        Entity entity = getEntityHoldingStack(stack);
        int tickCount = player == null ? 0 : player.tickCount;
        float ageInTicks = player == null ? 0.0F : tickCount + partialTicks;

        if (stack.is(OPItems.BLASTER.get())) this.renderBlasterModel(BLASTER_TEXTURE, BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.WHITE_BLASTER.get())) this.renderBlasterModel(WHITE_BLASTER_TEXTURE, WHITE_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.LIGHT_GRAY_BLASTER.get())) this.renderBlasterModel(LIGHT_GRAY_BLASTER_TEXTURE, LIGHT_GRAY_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.GRAY_BLASTER.get())) this.renderBlasterModel(GRAY_BLASTER_TEXTURE, GRAY_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.BLACK_BLASTER.get())) this.renderBlasterModel(BLACK_BLASTER_TEXTURE, BLACK_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.BROWN_BLASTER.get())) this.renderBlasterModel(BROWN_BLASTER_TEXTURE, BROWN_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.RED_BLASTER.get())) this.renderBlasterModel(RED_BLASTER_TEXTURE, RED_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.ORANGE_BLASTER.get())) this.renderBlasterModel(ORANGE_BLASTER_TEXTURE, ORANGE_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.YELLOW_BLASTER.get())) this.renderBlasterModel(YELLOW_BLASTER_TEXTURE, YELLOW_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.LIME_BLASTER.get())) this.renderBlasterModel(LIME_BLASTER_TEXTURE, LIME_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.GREEN_BLASTER.get())) this.renderBlasterModel(GREEN_BLASTER_TEXTURE, GREEN_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.CYAN_BLASTER.get())) this.renderBlasterModel(CYAN_BLASTER_TEXTURE, CYAN_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.LIGHT_BLUE_BLASTER.get())) this.renderBlasterModel(LIGHT_BLUE_BLASTER_TEXTURE, LIGHT_BLUE_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.BLUE_BLASTER.get())) this.renderBlasterModel(BLUE_BLASTER_TEXTURE, BLUE_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.PURPLE_BLASTER.get())) this.renderBlasterModel(PURPLE_BLASTER_TEXTURE, PURPLE_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.MAGENTA_BLASTER.get())) this.renderBlasterModel(MAGENTA_BLASTER_TEXTURE, MAGENTA_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
        if (stack.is(OPItems.PINK_BLASTER.get())) this.renderBlasterModel(PINK_BLASTER_TEXTURE, PINK_BLASTER_TEXTURE_GLOW, poseStack, entity, stack, bufferSource, packedLight, overlay, ageInTicks);
    }

    private void renderBlasterModel(ResourceLocation texture, ResourceLocation glowTexture, PoseStack poseStack, Entity entity, ItemStack stack, MultiBufferSource bufferSource, int packedLight, int overlay, float ageInTicks) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-180.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.pushPose();
        poseStack.scale(1.0F, 1.0F, 1.0F);
        BLASTER_MODEL.setupAnim(entity, stack, ageInTicks);
        BLASTER_MODEL.renderToBuffer(poseStack, getVertexConsumerFoil(bufferSource, RenderType.entityCutoutNoCull(texture), texture, stack.hasFoil()), packedLight, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        BLASTER_MODEL.renderToBuffer(poseStack, getVertexConsumer(bufferSource, RenderType.entityTranslucentEmissive(glowTexture), glowTexture), 0xffffff, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
        poseStack.popPose();
    }

    public static Player getEntityHoldingStack(ItemStack stack) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.getMainHandItem() == stack) {
                return player;
            } else if (player.getOffhandItem() == stack) {
                return player;
            }
        }
        return null;
    }

    private static VertexConsumer getVertexConsumerFoil(MultiBufferSource bufferSource, RenderType renderType, ResourceLocation resourceLocation, boolean foil) {
        return ItemRenderer.getFoilBuffer(bufferSource, renderType, false, foil);
    }

    private static VertexConsumer getVertexConsumer(MultiBufferSource bufferSource, RenderType renderType, ResourceLocation resourceLocation) {
        return bufferSource.getBuffer(renderType);
    }
}
