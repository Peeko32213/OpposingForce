package com.unusualmodding.opposing_force.client.renderer.layers;

import com.unusualmodding.opposing_force.client.models.entity.FrowzyModel;
import com.unusualmodding.opposing_force.entity.Frowzy;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@OnlyIn(Dist.CLIENT)
public class FrowzyItemInHandLayer extends RenderLayer<Frowzy, FrowzyModel> {

    public FrowzyItemInHandLayer(RenderLayerParent<Frowzy, FrowzyModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, Frowzy frowzy, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemstack = frowzy.getItemBySlot(EquipmentSlot.MAINHAND);
        poseStack.pushPose();
        boolean left = frowzy.isLeftHanded();
        if (frowzy.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.translate(0.0D, 1.5D, 0D);
        }
        poseStack.pushPose();
        translateToHand(poseStack, left);
        poseStack.translate(0.1F, 0.5F, 0.125F);

        poseStack.mulPose(Axis.XP.rotationDegrees(-90F));
//        poseStack.mulPose(Axis.YP.rotationDegrees(110F));
        ItemInHandRenderer renderer = Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer();
        renderer.renderItem(frowzy, itemstack, left ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, false, poseStack, bufferSource, packedLight);
        poseStack.popPose();
        poseStack.popPose();
    }

    protected void translateToHand(PoseStack matrixStack, boolean left) {
        this.getParentModel().root.translateAndRotate(matrixStack);
        this.getParentModel().Body.translateAndRotate(matrixStack);
        if (left) {
            this.getParentModel().Arm1.translateAndRotate(matrixStack);
        } else {
            this.getParentModel().Arm2.translateAndRotate(matrixStack);
        }
    }
}
