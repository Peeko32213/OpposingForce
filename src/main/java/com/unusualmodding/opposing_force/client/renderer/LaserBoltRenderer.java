package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.LaserBoltModel;
import com.unusualmodding.opposing_force.entity.projectile.LaserBolt;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LaserBoltRenderer extends EntityRenderer<LaserBolt> {

    private static final ResourceLocation OUTER_RED = new ResourceLocation(OpposingForce.MOD_ID,"textures/entity/projectiles/laser_bolt_outer_red.png");
    private static final ResourceLocation INNER_RED = new ResourceLocation(OpposingForce.MOD_ID,"textures/entity/projectiles/laser_bolt_inner_red.png");
    private static final ResourceLocation OUTER_ICE = new ResourceLocation(OpposingForce.MOD_ID,"textures/entity/projectiles/laser_bolt_outer_ice.png");
    private static final ResourceLocation INNER_ICE = new ResourceLocation(OpposingForce.MOD_ID,"textures/entity/projectiles/laser_bolt_inner_ice.png");

    private final LaserBoltModel model;

    public LaserBoltRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
        this.model = new LaserBoltModel(renderManagerIn.bakeLayer(OPModelLayers.LASER_BOLT));
    }

    @Override
    public void render(LaserBolt laserBolt, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        VertexConsumer VertexConsumer = buffer.getBuffer(OPRenderTypes.glowingEyes(this.getTextureLocation(laserBolt)));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        float f = Mth.rotLerp(partialTicks, laserBolt.yRotO, laserBolt.getYRot());
        float f1 = Mth.lerp(partialTicks, laserBolt.xRotO, laserBolt.getXRot());
        this.model.setupRotation(f, f1);
        this.model.renderToBuffer(poseStack, VertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        VertexConsumer VertexConsumer2 = buffer.getBuffer(OPRenderTypes.glowingEyes(getOuterTextureLocation(laserBolt)));
        this.model.renderToBuffer(poseStack, VertexConsumer2, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.5F);
        poseStack.popPose();
        super.render(laserBolt, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    protected int getBlockLightLevel(LaserBolt entityIn, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(LaserBolt entity) {
        if (entity.isFreezing()) {
            return INNER_ICE;
        }
        return INNER_RED;
    }

    public ResourceLocation getOuterTextureLocation(LaserBolt entity) {
        if (entity.isFreezing()) {
            return OUTER_ICE;
        }
        return OUTER_RED;
    }
}
