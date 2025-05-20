package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.unusualmodding.opposing_force.client.models.WhizzModel;
import com.unusualmodding.opposing_force.entity.WhizzEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class WhizzRenderer extends PlainGeoRenderer<WhizzEntity> {

    public WhizzRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WhizzModel());
    }

    @Override
    protected void applyRotations(WhizzEntity animatable, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTick) {
        super.applyRotations(animatable, poseStack, ageInTicks, rotationYaw, partialTick);
        if (animatable.isFlying() && !animatable.onGround()){
            poseStack.mulPose(Axis.ZP.rotationDegrees(animatable.currentRoll * 360 / 4));
        }
    }
}