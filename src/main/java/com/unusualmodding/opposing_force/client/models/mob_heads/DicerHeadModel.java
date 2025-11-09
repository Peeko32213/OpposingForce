package com.unusualmodding.opposing_force.client.models.mob_heads;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.renderer.OPRenderTypes;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DicerHeadModel extends MobHeadModelBase {

    private static final ResourceLocation VISOR_TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/dicer/visor.png");

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart visor;

    public DicerHeadModel(ModelPart root) {
        this.root = root;
        this.head = this.root.getChild("head");
        this.visor = this.root.getChild("visor");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 37).addBox(-4.0F, -4.4167F, -1.8333F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F))
                .texOffs(72, 71).addBox(-1.0F, -0.4167F, -5.8333F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(63, 64).addBox(-1.0F, 2.5833F, -5.8333F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(38, 21).addBox(-4.0F, -4.4167F, -1.8333F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 37).addBox(-1.0F, -8.4167F, -2.8333F, 2.0F, 10.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("visor", CubeListBuilder.create().texOffs(28, 53).addBox(-5.0F, -2.4167F, -2.8333F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(float limbSwing, float headY, float headX) {
        this.head.yRot = headY * ((float) Math.PI / 180F);
        this.head.xRot = headX * ((float) Math.PI / 180F);
        this.head.y = -3.5F;

        this.visor.yRot = headY * ((float) Math.PI / 180F);
        this.visor.xRot = headX * ((float) Math.PI / 180F);
        this.visor.y = -3.5F;

    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int i, int i1, float v, float v1, float v2, float v3) {
        this.root.render(poseStack, vertexConsumer, i, i1, v, v1, v2, v3);
    }

    public void renderVisorToBuffer(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1, float r, float g, float b, float a) {
        VertexConsumer eyesVertexConsumer = multiBufferSource.getBuffer(OPRenderTypes.glowingZOffset(VISOR_TEXTURE));
        this.visor.render(poseStack, eyesVertexConsumer, i, i1, r, g, b, a);
    }
}