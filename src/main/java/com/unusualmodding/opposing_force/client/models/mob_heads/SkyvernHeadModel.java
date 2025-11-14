package com.unusualmodding.opposing_force.client.models.mob_heads;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("unused, FieldCanBeLocal")
public class SkyvernHeadModel extends MobHeadModelBase {

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tongue;

    public SkyvernHeadModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head = this.root.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tongue = this.jaw.getChild("tongue");
    }

    public static LayerDefinition createHeadLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 141).addBox(-2.5F, -1.0F, -19.0F, 5.0F, 5.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(128, 38).addBox(-4.5F, -1.0F, -1.0F, 9.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(152, 16).addBox(1.5F, -3.0F, -5.0F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(152, 16).mirror().addBox(-5.5F, -3.0F, -5.0F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 97).addBox(-11.5F, -17.0F, 5.0F, 23.0F, 26.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(82, 97).addBox(11.49F, -19.0F, 3.0F, 0.0F, 28.0F, 23.0F, new CubeDeformation(0.0025F))
                .texOffs(82, 97).mirror().addBox(-11.49F, -19.0F, 3.0F, 0.0F, 28.0F, 23.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(71, 106).mirror().addBox(-11.5F, -17.0F, 23.0F, 23.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(71, 106).mirror().addBox(-11.5F, 9.0F, 23.0F, 23.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, -7.0F, -6.0F));

        head.addOrReplaceChild("brow_r1", CubeListBuilder.create()
                .texOffs(152, 27).mirror().addBox(0.0F, -4.0F, -5.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -3.0F, 1.0F, 0.0F, 0.0F, -0.5236F));

        head.addOrReplaceChild("brow_r2", CubeListBuilder.create()
                .texOffs(152, 27).addBox(0.0F, -4.0F, -5.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.5F, -3.0F, 1.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create()
                .texOffs(98, 38).addBox(-4.5F, 0.0F, -6.0F, 9.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(46, 148).addBox(-2.5F, 3.0F, -24.0F, 5.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 5.0F));

        jaw.addOrReplaceChild("tongue", CubeListBuilder.create()
                .texOffs(38, 148).addBox(-1.5F, 0.0F, -15.0F, 3.0F, 0.0F, 18.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 3.0F, -6.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(float limbSwing, float headY, float headX) {
        this.root.yRot = headY * ((float) Math.PI / 180F);
        this.root.xRot = headX * ((float) Math.PI / 180F);
        this.jaw.xRot = (float) (Math.sin(limbSwing * (float) Math.PI * 0.2F) + 1.0D) * 0.2F;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int i, int i1, float v, float v1, float v2, float v3) {
        this.root.render(poseStack, vertexConsumer, i, i1, v, v1, v2, v3);
    }
}