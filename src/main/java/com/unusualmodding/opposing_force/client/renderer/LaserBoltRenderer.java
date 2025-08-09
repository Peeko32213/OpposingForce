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
import org.joml.Quaternionf;

@OnlyIn(Dist.CLIENT)
public class LaserBoltRenderer extends EntityRenderer<LaserBolt> {

    private static final ResourceLocation OUTER_TEXTURES = new ResourceLocation(OpposingForce.MOD_ID,"textures/entity/projectiles/laser_bolt_outer.png");
    private static final ResourceLocation INNER_TEXTURES = new ResourceLocation(OpposingForce.MOD_ID,"textures/entity/projectiles/laser_bolt_inner.png");

    private final LaserBoltModel model;

    public LaserBoltRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn);
        this.model = new LaserBoltModel(renderManagerIn.bakeLayer(OPModelLayers.LASER_BOLT));
    }

    @Override
    public void render(LaserBolt laserBolt, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        VertexConsumer VertexConsumer = buffer.getBuffer(OPRenderTypes.glowingEyes(this.getTextureLocation(laserBolt)));
        if (laserBolt.isLightspeed()) {
            poseStack.scale(-2.0F, -2.0F, 2.0F);
        } else {
            poseStack.scale(-1.0F, -1.0F, 1.0F);
        }
        float f = Mth.rotLerp(partialTicks, laserBolt.yRotO, laserBolt.getYRot());
        float f1 = Mth.lerp(partialTicks, laserBolt.xRotO, laserBolt.getXRot());
        this.model.setupRotation(f, f1);
        this.model.renderToBuffer(poseStack, VertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        VertexConsumer VertexConsumer2 = buffer.getBuffer(OPRenderTypes.glowingEyes(OUTER_TEXTURES));
        this.model.renderToBuffer(poseStack, VertexConsumer2, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.4F);
        poseStack.popPose();
        super.render(laserBolt, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    protected int getBlockLightLevel(LaserBolt entityIn, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(LaserBolt entity) {
        return INNER_TEXTURES;
    }
}
