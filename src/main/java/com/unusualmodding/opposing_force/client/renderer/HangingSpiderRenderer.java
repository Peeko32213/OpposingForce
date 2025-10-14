package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.HangingSpiderModel;
import com.unusualmodding.opposing_force.client.renderer.layers.HangingSpiderEyesLayer;
import com.unusualmodding.opposing_force.entity.HangingSpider;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class HangingSpiderRenderer extends MobRenderer<HangingSpider, HangingSpiderModel> {

    private static final ResourceLocation HANGING_SPIDER = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/hanging_spider/hanging_spider.png");
    private static final ResourceLocation WEB = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/hanging_spider/hanging_spider_web.png");

    public HangingSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new HangingSpiderModel(context.bakeLayer(OPModelLayers.HANGING_SPIDER)), 0.5F);
        this.addLayer(new HangingSpiderEyesLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(HangingSpider entity) {
        return HANGING_SPIDER;
    }

    @Override
    protected @Nullable RenderType getRenderType(HangingSpider entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(HANGING_SPIDER);
    }

    @Override
    protected void setupRotations(HangingSpider entity, PoseStack matrixStack, float f, float g, float h) {
        super.setupRotations(entity, matrixStack, f, g, h);
        matrixStack.scale(0.75F, 0.75F, 0.75F);

        if (entity.isUpsideDown() && !entity.onGround()) {
            matrixStack.translate(0.0, 0.6, 0);
            matrixStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }
    }

    @Override
    public void render(HangingSpider entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        VertexConsumer builder = buffer.getBuffer(RenderType.entityCutoutNoCull(WEB));

        Vec3 base = new Vec3(0, entity.getWebOffset(), 0);
        Vec3 tip = new Vec3(entity.getCurrentWebPos());
        Vec3 direction = tip.subtract(base);
        double length = direction.length();

        if (!entity.isAlive()) return;

        Vec3 dirNorm = direction.normalize();
        Vec3 up = Math.abs(dirNorm.y) < 0.9 ? new Vec3(0, 1, 0) : new Vec3(1, 0, 0);
        Vec3 right = dirNorm.cross(up).normalize().scale(0.3);
        Vec3 forward = dirNorm.cross(right).normalize().scale(0.3);

        poseStack.pushPose();
        float vMax = (float) length;
        if (entity.isGoingUp() || entity.isGoingDown()) {
            drawWebQuad(poseStack, builder, base, tip, right, vMax, packedLight, 1, 1);
            drawWebQuad(poseStack, builder, base, tip, forward, vMax, packedLight, 1, 1);
        }
        poseStack.popPose();
    }

    private void drawWebQuad(PoseStack poseStack, VertexConsumer builder, Vec3 base, Vec3 tip, Vec3 side, float vMax, int packedLight, float baseOpacity, float tipOpacity) {
        PoseStack.Pose poseLast = poseStack.last();

        builder.vertex(poseLast.pose(), (float)(base.x + side.x), (float)(base.y + side.y), (float)(base.z + side.z))
                .color(1F, 1F, 1F, baseOpacity)
                .uv(0F, 0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(poseLast.normal(), 0, 1, 0)
                .endVertex();

        builder.vertex(poseLast.pose(), (float)(base.x - side.x), (float)(base.y - side.y), (float)(base.z - side.z))
                .color(1F, 1F, 1F, baseOpacity)
                .uv(1F, 0F)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(poseLast.normal(), 0, 1, 0)
                .endVertex();

        builder.vertex(poseLast.pose(), (float)(tip.x - side.x), (float)(tip.y - side.y), (float)(tip.z - side.z))
                .color(1F, 1F, 1F, tipOpacity)
                .uv(1F, vMax)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(poseLast.normal(), 0, 1, 0)
                .endVertex();

        builder.vertex(poseLast.pose(), (float)(tip.x + side.x), (float)(tip.y + side.y), (float)(tip.z + side.z))
                .color(1F, 1F, 1F, tipOpacity)
                .uv(0F, vMax)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(packedLight)
                .normal(poseLast.normal(), 0, 1, 0)
                .endVertex();
    }

    @Override
    public boolean shouldRender(HangingSpider hangingSpider, Frustum frustum, double v, double v1, double v2) {
        if (hangingSpider.isWebOut() && !hangingSpider.isUpsideDown()) return true;
        return super.shouldRender(hangingSpider, frustum, v, v1, v2);
    }
}
