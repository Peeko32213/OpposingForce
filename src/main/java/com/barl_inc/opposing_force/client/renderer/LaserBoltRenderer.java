package com.barl_inc.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.client.models.entity.LaserBoltModel;
import com.barl_inc.opposing_force.entity.projectile.LaserBolt;
import com.barl_inc.opposing_force.items.BlasterItem;
import com.barl_inc.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class LaserBoltRenderer extends EntityRenderer<LaserBolt> {

    private static final ResourceLocation OUTER = OpposingForce.modPrefix("textures/entity/projectiles/laser_bolt_outer.png");
    private static final ResourceLocation INNER = OpposingForce.modPrefix("textures/entity/projectiles/laser_bolt_inner.png");
    private static final ResourceLocation TRAIL_TEXTURE = OpposingForce.modPrefix("textures/particle/trail.png");

    private final LaserBoltModel model;

    public LaserBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new LaserBoltModel(context.bakeLayer(OPModelLayers.LASER_BOLT));
    }

    @Override
    public void render(@NotNull LaserBolt laserBolt, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        float red = 1;
        float green = 0;
        float blue = 0;
        ItemStack blaster = laserBolt.getItem();
        if (blaster.getItem() instanceof BlasterItem blasterItem) {
            int decimal = blasterItem.getLaserColor();
            red = (float) ((decimal & 16711680) >> 16) / 255.0F;
            green = (float) ((decimal & '\uff00') >> 8) / 255.0F;
            blue = (float) ((decimal & 255)) / 255.0F;
        }

        poseStack.pushPose();
        VertexConsumer innerTexture = buffer.getBuffer(OPRenderTypes.laserBolt(this.getTextureLocation(laserBolt)));
        poseStack.scale(-1.0F, -1.0F, 1.0F);
        float yRot = Mth.rotLerp(partialTicks, laserBolt.yRotO, laserBolt.getYRot());
        float xRot = Mth.lerp(partialTicks, laserBolt.xRotO, laserBolt.getXRot());
        this.model.setupRotation(yRot, xRot);
        this.model.renderToBuffer(poseStack, innerTexture, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        VertexConsumer outerTexture = buffer.getBuffer(OPRenderTypes.laserBolt(this.getOuterTextureLocation(laserBolt)));
        this.model.renderToBuffer(poseStack, outerTexture, packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 0.25F);
        poseStack.popPose();
        if (laserBolt.hasTrail()) {
            double x = Mth.lerp(partialTicks, laserBolt.xOld, laserBolt.getX());
            double y = Mth.lerp(partialTicks, laserBolt.yOld, laserBolt.getY());
            double z = Mth.lerp(partialTicks, laserBolt.zOld, laserBolt.getZ());
            poseStack.pushPose();
            poseStack.translate(-x, -y, -z);
            this.renderTrail(laserBolt, partialTicks, poseStack, buffer, red, green, blue, 0.75F, packedLight);
            poseStack.popPose();
        }
        super.render(laserBolt, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private void renderTrail(LaserBolt laserBolt, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, float red, float green, float blue, float alpha, int packedLight) {
        int samples = 0;
        int sampleSize = 1;
        float trailHeight = 0.07F;
        float trailZRot = 0;
        Vec3 topAngleVec = new Vec3(0, trailHeight, 0).zRot(trailZRot);
        Vec3 bottomAngleVec = new Vec3(0, -trailHeight, 0).zRot(trailZRot);
        Vec3 drawFrom = laserBolt.getTrailPosition(0, partialTicks);
        VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityTranslucentEmissive(TRAIL_TEXTURE));
        while (samples < sampleSize) {
            Vec3 draw2 = laserBolt.getTrailPosition(samples + 2, partialTicks);
            float u1 = samples / (float) sampleSize;
            float u2 = u1 + 1 / (float) sampleSize;
            Vec3 draw1 = drawFrom;
            PoseStack.Pose lastPose = poseStack.last();
            Matrix4f matrix4f = lastPose.pose();
            Matrix3f matrix3f = lastPose.normal();
            vertexconsumer.vertex(matrix4f, (float) draw1.x + (float) bottomAngleVec.x, (float) draw1.y + (float) bottomAngleVec.y, (float) draw1.z + (float) bottomAngleVec.z).color(red, green, blue, alpha).uv(u1, 1F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            vertexconsumer.vertex(matrix4f, (float) draw2.x + (float) bottomAngleVec.x, (float) draw2.y + (float) bottomAngleVec.y, (float) draw2.z + (float) bottomAngleVec.z).color(red, green, blue, alpha).uv(u2, 1F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            vertexconsumer.vertex(matrix4f, (float) draw2.x + (float) topAngleVec.x, (float) draw2.y + (float) topAngleVec.y, (float) draw2.z + (float) topAngleVec.z).color(red, green, blue, alpha).uv(u2, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            vertexconsumer.vertex(matrix4f, (float) draw1.x + (float) topAngleVec.x, (float) draw1.y + (float) topAngleVec.y, (float) draw1.z + (float) topAngleVec.z).color(red, green, blue, alpha).uv(u1, 0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            samples++;
            drawFrom = draw2;
        }
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
