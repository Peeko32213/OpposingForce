package com.unusualmodding.opposing_force.client.renderer.layers;

import com.unusualmodding.opposing_force.client.models.entity.FrowzyModel;
import com.unusualmodding.opposing_force.entity.Frowzy;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@OnlyIn(Dist.CLIENT)
public class FrowzyItemInHandLayer<T extends Frowzy, M extends FrowzyModel<T>> extends RenderLayer<T, M> {

    private final ItemInHandRenderer itemInHandRenderer;

    public FrowzyItemInHandLayer(RenderLayerParent<T, M> parent, ItemInHandRenderer itemRenderer) {
        super(parent);
        this.itemInHandRenderer = itemRenderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Frowzy frowzy, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean flag = frowzy.getMainArm() == HumanoidArm.RIGHT;
        ItemStack itemstack = flag ? frowzy.getOffhandItem() : frowzy.getMainHandItem();
        ItemStack itemstack1 = flag ? frowzy.getMainHandItem() : frowzy.getOffhandItem();
        if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
            poseStack.pushPose();
            if (this.getParentModel().young) {
                poseStack.translate(0.0D, 0.75D, 0.0D);
                poseStack.scale(0.5F, 0.5F, 0.5F);
            }

            this.renderArmWithItem(frowzy, itemstack1, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, buffer, packedLight);
            this.renderArmWithItem(frowzy, itemstack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, poseStack, buffer, packedLight);
            poseStack.popPose();
        }
    }

    protected void renderArmWithItem(Frowzy frowzy, ItemStack stack, ItemDisplayContext transformType, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            this.getParentModel().translateToHand(arm, poseStack);
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            boolean flag = arm == HumanoidArm.LEFT;
            poseStack.translate(((flag ? -0.1F : 0.1F) / 16.0F), 0.05F, -1);
            this.itemInHandRenderer.renderItem(frowzy, stack, transformType, flag, poseStack, buffer, packedLight);
            poseStack.popPose();
        }
    }
}
