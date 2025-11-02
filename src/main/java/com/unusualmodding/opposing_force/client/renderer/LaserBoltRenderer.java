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
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LaserBoltRenderer extends EntityRenderer<LaserBolt> {

    private static final ResourceLocation OUTER = new ResourceLocation(OpposingForce.MOD_ID,"textures/entity/projectiles/laser_bolt_outer.png");
    private static final ResourceLocation INNER = new ResourceLocation(OpposingForce.MOD_ID,"textures/entity/projectiles/laser_bolt_inner.png");

    private final LaserBoltModel model;

    public LaserBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new LaserBoltModel(context.bakeLayer(OPModelLayers.LASER_BOLT));
    }

    @Override
    public void render(@NotNull LaserBolt laserBolt, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        VertexConsumer VertexConsumer = buffer.getBuffer(OPRenderTypes.glowingEyes(this.getTextureLocation(laserBolt)));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        float yrot = Mth.rotLerp(partialTicks, laserBolt.yRotO, laserBolt.getYRot());
        float xrot = Mth.lerp(partialTicks, laserBolt.xRotO, laserBolt.getXRot());
        this.model.setupRotation(yrot, xrot);
        this.model.renderToBuffer(poseStack, VertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        VertexConsumer VertexConsumer2 = buffer.getBuffer(OPRenderTypes.glowingEyes(getOuterTextureLocation(laserBolt)));
        this.model.renderToBuffer(poseStack, VertexConsumer2, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 0.5F);
        poseStack.popPose();
        super.render(laserBolt, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    protected int getBlockLightLevel(@NotNull LaserBolt laserBolt, @NotNull BlockPos pos) {
        return 15;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull LaserBolt entity) {
        return INNER;
    }

    public ResourceLocation getOuterTextureLocation(LaserBolt entity) {
        return OUTER;
    }
}
