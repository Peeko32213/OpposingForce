package com.unusualmodding.opposing_force.client.models.mob_heads;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.renderer.OPRenderTypes;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DicerHeadModel extends SkullModel {

    private static final ResourceLocation VISOR_TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/dicer/dicer_visor.png");

    private final ModelPart rootVisor;
    protected final ModelPart headVisor;

    public DicerHeadModel(ModelPart root, ModelPart visor) {
        super(root);
        this.rootVisor = visor;
        this.headVisor = visor.getChild("head");
    }

    public static MeshDefinition createDicerHeadModel() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.3F))
                .texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -3.0F, -6.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 22).addBox(-1.0F, -12.0F, -5.0F, 2.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(25, 9).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        return meshdefinition;
    }

    public static MeshDefinition createDicerVisorModel() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.3F))
                .texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, -3.0F, -6.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 22).addBox(-1.0F, -12.0F, -5.0F, 2.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(25, 9).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        return meshdefinition;
    }

    public static LayerDefinition createDicerHeadLayer() {
        MeshDefinition meshdefinition = createDicerHeadModel();
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public static LayerDefinition createDicerVisorLayer() {
        MeshDefinition meshdefinition = createDicerVisorModel();
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(float animationTickCount, float p_103812_, float p_103813_) {
        super.setupAnim(animationTickCount, p_103812_, p_103813_);
        this.headVisor.yRot = p_103812_ * ((float) Math.PI / 180F);
        this.headVisor.xRot = p_103813_ * ((float) Math.PI / 180F);
    }

    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, MultiBufferSource multiBufferSource, int p_103817_, int p_103818_, float p_103819_, float p_103820_, float p_103821_, float p_103822_) {
        super.renderToBuffer(poseStack, vertexConsumer, p_103817_, p_103818_, p_103819_, p_103820_, p_103821_, p_103822_);
        VertexConsumer eyesVertexConsumer = multiBufferSource.getBuffer(OPRenderTypes.eyesZOffset(VISOR_TEXTURE));
        this.rootVisor.render(poseStack, eyesVertexConsumer, p_103817_, p_103818_, p_103819_, p_103820_, p_103821_, p_103822_);
    }
}