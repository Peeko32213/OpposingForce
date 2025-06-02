package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.entity.Guzzler;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class GuzzlerModel<T extends Guzzler> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart Body;
	private final ModelPart Jaw;
	private final ModelPart Arm1;
	private final ModelPart Arm2;
	private final ModelPart Foot2;
	private final ModelPart Foot1;

	public GuzzlerModel(ModelPart root) {
		this.root = root.getChild("root");
		this.Body = this.root.getChild("Body");
		this.Jaw = this.Body.getChild("Jaw");
		this.Arm1 = this.Body.getChild("Arm1");
		this.Arm2 = this.Body.getChild("Arm2");
		this.Foot2 = this.root.getChild("Foot2");
		this.Foot1 = this.root.getChild("Foot1");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition Body = root.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-19.5F, -30.0F, -7.0F, 39.0F, 30.0F, 21.0F, new CubeDeformation(0.0F)).texOffs(43, 77).addBox(-19.5F, -35.0F, -7.0F, 39.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(96, 63).addBox(19.475F, -35.0F, -7.0F, 0.0F, 5.0F, 21.0F, new CubeDeformation(0.0F)).texOffs(96, 63).mirror().addBox(-19.475F, -35.0F, -7.0F, 0.0F, 5.0F, 21.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(43, 82).addBox(-19.5F, -32.0F, 14.0F, 39.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 137).addBox(-19.5F, -32.0F, 13.825F, 39.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(96, 110).addBox(-2.5F, -32.0F, 10.0F, 5.0F, 36.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(96, 89).addBox(-2.5F, -1.0F, 15.0F, 5.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(76, 74).addBox(13.0F, -37.0F, 14.0F, 0.0F, 41.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(76, 74).mirror().addBox(-13.0F, -37.0F, 14.0F, 0.0F, 41.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 0).addBox(19.5F, -30.0F, 13.0F, 9.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 0).mirror().addBox(-28.5F, -30.0F, 13.0F, 9.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -6.0F, 0.0F));
		PartDefinition Jaw = Body.addOrReplaceChild("Jaw", CubeListBuilder.create().texOffs(0, 77).addBox(-1.5F, -5.075F, -32.0F, 5.0F, 5.0F, 33.0F, new CubeDeformation(0.0F)).texOffs(99, 0).addBox(-12.5F, 0.0F, -23.0F, 27.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 51).addBox(-18.5F, -3.0F, -23.0F, 39.0F, 3.0F, 23.0F, new CubeDeformation(0.0F)).texOffs(0, 7).addBox(11.0F, -5.0F, -24.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 7).mirror().addBox(-12.0F, -5.0F, -24.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 94).addBox(14.0F, -9.0F, -21.0F, 0.0F, 6.0F, 21.0F, new CubeDeformation(0.0F)).texOffs(0, 94).mirror().addBox(-12.0F, -9.0F, -21.0F, 0.0F, 6.0F, 21.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, -32.0F, 14.0F));
		PartDefinition Arm1 = Body.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(12, 9).addBox(-5.0F, 3.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(9, 7).addBox(-7.0F, 2.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 13).addBox(-6.0F, -2.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -23.0F, -7.0F, 0.0F, -0.5672F, 0.0F));
		PartDefinition Arm2 = Body.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(12, 9).mirror().addBox(3.0F, 3.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(9, 7).mirror().addBox(5.0F, 2.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 13).mirror().addBox(0.0F, -2.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-18.0F, -23.0F, -7.0F, 0.0F, 0.7418F, 0.0F));
		PartDefinition Foot2 = root.addOrReplaceChild("Foot2", CubeListBuilder.create().texOffs(0, 164).mirror().addBox(-5.0F, -6.0F, -3.0F, 9.0F, 10.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(10, 200).mirror().addBox(-5.0F, 4.0F, 7.0F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(12, 213).mirror().addBox(-5.0F, 7.0F, 3.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-17.0F, -8.0F, -5.0F));
		PartDefinition Foot1 = root.addOrReplaceChild("Foot1", CubeListBuilder.create().texOffs(0, 164).addBox(-4.0F, -6.0F, -3.0F, 9.0F, 10.0F, 15.0F, new CubeDeformation(0.0F)).texOffs(10, 200).addBox(-4.0F, 4.0F, 7.0F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(12, 213).addBox(-4.0F, 7.0F, 3.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(17.0F, -8.0F, -5.0F));
		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Guzzler entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public ModelPart root() {
		return this.root;
	}
}