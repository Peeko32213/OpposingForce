package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.unusualmodding.opposing_force.entity.HauntedTool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class HauntedToolRenderer extends EntityRenderer<HauntedTool> {

    public HauntedToolRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    public void render(HauntedTool entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource source, int i) {
        ItemStack itemStack = entity.getItemStack();
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        BakedModel bakedModel = renderer.getModel(itemStack, entity.level(), null, 0);

        float ageInTicks = entity.tickCount + partialTicks;
        float strikeProgress = entity.getStrikeTime(partialTicks);

        poseStack.pushPose();
        poseStack.translate(0, 0.05F + Math.sin(ageInTicks * 0.1F) * 0.15F, 0);

        if (entity.isAttacking()) {
            poseStack.mulPose(Axis.XP.rotationDegrees(ageInTicks * -4.0F * 8));
        }

        poseStack.mulPose(Axis.YN.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 180.0F));
        poseStack.mulPose(Axis.XN.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));

//        poseStack.translate(0, strikeProgress * 0.1F, strikeProgress * 0.2F);
//        poseStack.mulPose(Axis.XN.rotationDegrees(strikeProgress * 90F));

        Minecraft.getInstance().getItemRenderer().render(itemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false, poseStack, source, i, OverlayTexture.NO_OVERLAY, bakedModel);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, source, i);
    }

    @Override
    public ResourceLocation getTextureLocation(HauntedTool entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
