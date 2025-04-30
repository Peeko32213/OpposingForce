package com.unusualmodding.opposingforce.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.unusualmodding.opposingforce.client.model.WhizzModel;
import com.unusualmodding.opposingforce.common.entity.custom.monster.WhizzEntity;
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