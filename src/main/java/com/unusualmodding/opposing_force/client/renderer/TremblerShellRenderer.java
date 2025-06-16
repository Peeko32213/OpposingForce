package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.TremblerShellModel;
import com.unusualmodding.opposing_force.entity.projectile.TremblerShell;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class TremblerShellRenderer <E extends TremblerShell> extends EntityRenderer<E> {

    protected final TremblerShellModel<E> model;

    private static final ResourceLocation TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/trembler.png");

    public TremblerShellRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.4F;
        this.model = new TremblerShellModel<>(context.bakeLayer(OPModelLayers.TREMBLER_SHELL_LAYER));
    }

    @Override
    public void render(E entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);

        float ageInTicks = partialTicks + entity.tickCount;

        poseStack.pushPose();
        poseStack.translate(0, 1.5F, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180));

        poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
        poseStack.mulPose(Axis.XP.rotationDegrees(-135.0F));

        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.translate(0.0F, -0.175F, 0.0F);

        poseStack.mulPose(Axis.XP.rotationDegrees(-(entity.tickCount + partialTicks) * -45 % 360));

        poseStack.translate(-0.5D, -0.5D, -0.5D);

        VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity)));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(E entity) {
        return TEXTURE;
    }
}
