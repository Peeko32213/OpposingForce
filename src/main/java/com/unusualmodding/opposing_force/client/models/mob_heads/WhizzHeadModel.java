package com.unusualmodding.opposing_force.client.models.mob_heads;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class WhizzHeadModel extends MobHeadModelBase {

    private final ModelPart root;
    private final ModelPart right_mandible;
    private final ModelPart left_mandible;

	public WhizzHeadModel(ModelPart root) {
        this.root = root.getChild("root");
        this.right_mandible = this.root.getChild("right_mandible");
        this.left_mandible = this.root.getChild("left_mandible");
	}

	public static LayerDefinition createHeadLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create()
                .texOffs(0, 12).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        root.addOrReplaceChild("right_mandible", CubeListBuilder.create()
                .texOffs(32, 10).mirror().addBox(-3.0F, 0.0F, -8.5F, 4.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -0.5F, -3.5F, 0.6109F, 0.0F, 0.0F));

        root.addOrReplaceChild("left_mandible", CubeListBuilder.create()
                .texOffs(32, 10).addBox(-1.0F, 0.0F, -8.5F, 4.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.0F, -0.5F, -3.5F, 0.6109F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(float limbSwing, float headY, float headX) {
		this.root.yRot = headY * ((float) Math.PI / 180F);
		this.root.xRot = headX * ((float) Math.PI / 180F);
        this.right_mandible.yRot = (float) (Math.sin((limbSwing * 0.7F))) * 0.45F;
        this.left_mandible.yRot = (float) (-Math.sin((limbSwing * 0.7F))) * 0.45F;
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}