package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.skyvern.SkyvernBodyModel;
import com.unusualmodding.opposing_force.client.models.entity.skyvern.SkyvernTailModel;
import com.unusualmodding.opposing_force.entity.Skyvern;
import com.unusualmodding.opposing_force.entity.SkyvernSegment;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SkyvernSegmentRenderer extends EntityRenderer<SkyvernSegment> {

    private static final ResourceLocation CLOUDY = OpposingForce.modPrefix("textures/entity/skyvern/cloudy.png");
    private static final ResourceLocation AZURE = OpposingForce.modPrefix("textures/entity/skyvern/azure.png");
    private static final ResourceLocation THUNDER = OpposingForce.modPrefix("textures/entity/skyvern/thunder.png");

    private final SkyvernBodyModel BODY;
    private final SkyvernTailModel TAIL;

    public SkyvernSegmentRenderer(EntityRendererProvider.Context context) {
        super(context);
        BODY = new SkyvernBodyModel(context.bakeLayer(OPModelLayers.SKYVERN_BODY));
        TAIL = new SkyvernTailModel(context.bakeLayer(OPModelLayers.SKYVERN_TAIL));
    }

    @Override
    public boolean shouldRender(@NotNull SkyvernSegment entity, @NotNull Frustum camera, double x, double y, double z) {
        if (super.shouldRender(entity, camera, x, y, z)) {
            return true;
        } else {
            Entity nextWorm = entity.getFrontEntity();
            if (nextWorm != null) {
                Vec3 vec3 = entity.position();
                Vec3 vec31 = nextWorm.position();
                return camera.isVisible(new AABB(vec31.x, vec31.y, vec31.z, vec3.x, vec3.y, vec3.z));
            }
            return false;
        }
    }

    @Override
    public void render(SkyvernSegment entity, float entityYaw, float partialTicks, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        Entity frontAttachedEntity = entity.getFrontEntity();
        Entity backEntity = entity.getBackEntity();
        float yRotLerp = Mth.lerp(partialTicks, entity.yRotO, entity.getYRot());
        float xRotLerp = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());

        poseStack.pushPose();
        poseStack.translate(0.0D, 1F, 0.0D);
        poseStack.mulPose(Axis.YN.rotationDegrees(yRotLerp));
        poseStack.mulPose(Axis.XP.rotationDegrees(xRotLerp + 180));
        poseStack.translate(0.0D, -0.5F, 0.0D);

        Entity head = entity.getHeadEntity();
        if (head instanceof Skyvern skyvern) {
            if (LivingEntityRenderer.isEntityUpsideDown(skyvern)) {
                poseStack.translate(0.0F, entity.getBbHeight() + 1.25F, 0.0F);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            }
        }

        if (backEntity == null) {
            TAIL.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTicks, 0.0F, 0.0F);
            VertexConsumer ivertexbuilder = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
            TAIL.renderToBuffer(poseStack, ivertexbuilder, packedLight, getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            BODY.setupAnim(entity, 0.0F, 0.0F, entity.tickCount + partialTicks, 0.0F, 0.0F);
            VertexConsumer ivertexbuilder = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
            BODY.renderToBuffer(poseStack, ivertexbuilder, packedLight, getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
        poseStack.popPose();

        if (frontAttachedEntity != null) {
            Vec3 centeredHeight = new Vec3(0, entity.getBbHeight() * 0.5F, 0);
            Vec3 from = new Vec3(0F, 0, -0.9F).xRot((float) Math.toRadians(180 - xRotLerp)).yRot(-(float) Math.toRadians(yRotLerp)).add(centeredHeight);
            poseStack.pushPose();
            poseStack.translate(from.x, from.y, from.z);
            poseStack.popPose();
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(SkyvernSegment entity) {
        Entity head = entity.getHeadEntity();
        if (head instanceof Skyvern skyvern) {
            return switch (skyvern.getVariant()) {
                case CLOUDY -> CLOUDY;
                case AZURE -> AZURE;
                case THUNDER -> THUNDER;
            };
        }
        return CLOUDY;
    }

    public static int getOverlayCoords(SkyvernSegment segmentEntity, float f) {
        return OverlayTexture.pack(OverlayTexture.u(f), OverlayTexture.v(segmentEntity.renderHurtFlag));
    }
}
