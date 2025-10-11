package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.GuzzlerAnimations;
import com.unusualmodding.opposing_force.entity.Guzzler;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class GuzzlerModel extends HierarchicalModel<Guzzler> {

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart jaw;
	private final ModelPart left_arm;
	private final ModelPart right_arm;
	private final ModelPart right_foot;
	private final ModelPart left_foot;

	public GuzzlerModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.jaw = this.body.getChild("jaw");
		this.left_arm = this.body.getChild("left_arm");
		this.right_arm = this.body.getChild("right_arm");
		this.right_foot = this.root.getChild("right_foot");
		this.left_foot = this.root.getChild("left_foot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -3.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-19.5F, -30.0F, -7.0F, 39.0F, 30.0F, 21.0F, new CubeDeformation(0.0F))
				.texOffs(43, 77).addBox(-19.5F, -35.0F, -7.0F, 39.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(96, 63).addBox(19.475F, -35.0F, -7.0F, 0.0F, 5.0F, 21.0F, new CubeDeformation(0.0F))
				.texOffs(96, 63).mirror().addBox(-19.475F, -35.0F, -7.0F, 0.0F, 5.0F, 21.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(43, 82).addBox(-19.5F, -32.0F, 14.0F, 39.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 137).addBox(-19.5F, -32.0F, 13.825F, 39.0F, 2.0F, 0.0F, new CubeDeformation(0.025F))
				.texOffs(96, 110).addBox(-2.5F, -32.0F, 10.0F, 5.0F, 36.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(96, 89).addBox(-2.5F, -1.0F, 15.0F, 5.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
				.texOffs(76, 74).addBox(13.0F, -37.0F, 14.0F, 0.0F, 41.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(76, 74).mirror().addBox(-13.0F, -37.0F, 14.0F, 0.0F, 41.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).addBox(19.5F, -30.0F, 13.0F, 9.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).mirror().addBox(-28.5F, -30.0F, 13.0F, 9.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition jaw = body.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 77).addBox(-1.5F, -5.075F, -32.0F, 5.0F, 5.0F, 33.0F, new CubeDeformation(0.0F))
				.texOffs(99, 0).addBox(-12.5F, 0.0F, -23.0F, 27.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 51).addBox(-18.5F, -3.0F, -23.0F, 39.0F, 3.0F, 23.0F, new CubeDeformation(0.0F))
				.texOffs(0, 7).addBox(11.0F, -5.0F, -24.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 7).mirror().addBox(-12.0F, -5.0F, -24.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 94).addBox(14.0F, -9.0F, -21.0F, 0.0F, 6.0F, 21.0F, new CubeDeformation(0.0F))
				.texOffs(0, 94).mirror().addBox(-12.0F, -9.0F, -21.0F, 0.0F, 6.0F, 21.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, -32.0F, 14.0F));

		PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(12, 9).addBox(-5.0F, 3.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(9, 7).addBox(-7.0F, 2.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 13).addBox(-6.0F, -2.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, -23.0F, -7.0F, 0.0F, -0.5672F, 0.0F));

		PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(12, 9).mirror().addBox(3.0F, 3.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(9, 7).mirror().addBox(5.0F, 2.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 13).mirror().addBox(0.0F, -2.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-18.0F, -23.0F, -7.0F, 0.0F, 0.7418F, 0.0F));

		PartDefinition right_foot = root.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(0, 164).mirror().addBox(-5.0F, -6.0F, -3.0F, 9.0F, 10.0F, 15.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(10, 200).mirror().addBox(-5.0F, 4.0F, 7.0F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(12, 213).mirror().addBox(-5.0F, 7.0F, 3.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-17.0F, -8.0F, -5.0F));

		PartDefinition left_foot = root.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(0, 164).addBox(-4.0F, -6.0F, -3.0F, 9.0F, 10.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(10, 200).addBox(-4.0F, 4.0F, 7.0F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(12, 213).addBox(-4.0F, 7.0F, 3.0F, 9.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(17.0F, -8.0F, -5.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Guzzler entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(GuzzlerAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
		this.animate(entity.idleAnimationState, GuzzlerAnimations.IDLE, ageInTicks);
		this.animate(entity.spewAnimationState, GuzzlerAnimations.SPIT, ageInTicks);
		this.animate(entity.stompAnimationState, GuzzlerAnimations.FAT_SLAM, ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}