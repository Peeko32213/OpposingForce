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

@OnlyIn(Dist.CLIENT)
public class DicerHeadModel extends MobHeadModelBase {

    private static final ResourceLocation VISOR_TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/dicer/dicer_visor.png");

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

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.3F))
                .texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -3.0F, -6.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 22).addBox(-1.0F, -12.0F, -5.0F, 2.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        partdefinition.addOrReplaceChild("visor", CubeListBuilder.create()
                .texOffs(25, 9).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.ZERO);


        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(float limbSwing, float headY, float headX) {
        this.head.yRot = headY * ((float) Math.PI / 180F);
        this.head.xRot = headX * ((float) Math.PI / 180F);
        this.head.y = 0;

        this.visor.yRot = headY * ((float) Math.PI / 180F);
        this.visor.xRot = headX * ((float) Math.PI / 180F);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, float v, float v1, float v2, float v3) {
        this.root.render(poseStack, vertexConsumer, i, i1, v, v1, v2, v3);
    }

    public void renderVisorToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, MultiBufferSource multiBufferSource, int i, int i1, float r, float g, float b, float a) {
        VertexConsumer eyesVertexConsumer = multiBufferSource.getBuffer(OPRenderTypes.glowingZOffset(VISOR_TEXTURE));
        this.visor.render(poseStack, eyesVertexConsumer, i, i1, r, g, b, a);
    }
}