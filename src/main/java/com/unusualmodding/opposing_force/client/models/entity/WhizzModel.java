package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.WhizzAnimations;
import com.unusualmodding.opposing_force.entity.Whizz;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class WhizzModel<T extends Whizz> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart Body;
	private final ModelPart SmallWing1;
	private final ModelPart SmallWingBone1;
	private final ModelPart SmallWing2;
	private final ModelPart SmallWingBone2;
	private final ModelPart LargeWing1;
	private final ModelPart LargeWingBone1;
	private final ModelPart LargeWing2;
	private final ModelPart LargeWingBone2;
	private final ModelPart Leg1;
	private final ModelPart Leg3;
	private final ModelPart Leg2;
	private final ModelPart Leg4;
	private final ModelPart Jaw1;
	private final ModelPart JawBone1;
	private final ModelPart Jaw2;
	private final ModelPart JawBone2;

	public WhizzModel(ModelPart root) {
		this.root = root.getChild("root");
		this.Body = this.root.getChild("Body");
		this.SmallWing1 = this.Body.getChild("SmallWing1");
		this.SmallWingBone1 = this.SmallWing1.getChild("SmallWingBone1");
		this.SmallWing2 = this.Body.getChild("SmallWing2");
		this.SmallWingBone2 = this.SmallWing2.getChild("SmallWingBone2");
		this.LargeWing1 = this.Body.getChild("LargeWing1");
		this.LargeWingBone1 = this.LargeWing1.getChild("LargeWingBone1");
		this.LargeWing2 = this.Body.getChild("LargeWing2");
		this.LargeWingBone2 = this.LargeWing2.getChild("LargeWingBone2");
		this.Leg1 = this.Body.getChild("Leg1");
		this.Leg3 = this.Body.getChild("Leg3");
		this.Leg2 = this.Body.getChild("Leg2");
		this.Leg4 = this.Body.getChild("Leg4");
		this.Jaw1 = this.Body.getChild("Jaw1");
		this.JawBone1 = this.Jaw1.getChild("JawBone1");
		this.Jaw2 = this.Body.getChild("Jaw2");
		this.JawBone2 = this.Jaw2.getChild("JawBone2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition Body = root.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 9).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 1.0F));
		PartDefinition SmallWing1 = Body.addOrReplaceChild("SmallWing1", CubeListBuilder.create(), PartPose.offsetAndRotation(1.75F, -1.75F, 1.0F, 0.0F, 0.0F, -0.48F));
		PartDefinition SmallWingBone1 = SmallWing1.addOrReplaceChild("SmallWingBone1", CubeListBuilder.create().texOffs(16, 16).addBox(0.0F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.829F, 0.0F, 0.0F));
		PartDefinition SmallWing2 = Body.addOrReplaceChild("SmallWing2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.75F, -1.75F, 1.0F, 0.0F, 0.0F, 0.48F));
		PartDefinition SmallWingBone2 = SmallWing2.addOrReplaceChild("SmallWingBone2", CubeListBuilder.create().texOffs(16, 16).mirror().addBox(-5.0F, -5.0F, 0.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.829F, 0.0F, 0.0F));
		PartDefinition LargeWing1 = Body.addOrReplaceChild("LargeWing1", CubeListBuilder.create(), PartPose.offsetAndRotation(1.5F, -1.75F, -1.0F, 0.0F, 0.0F, 0.48F));
		PartDefinition LargeWingBone1 = LargeWing1.addOrReplaceChild("LargeWingBone1", CubeListBuilder.create().texOffs(15, 0).addBox(0.0F, -9.0F, 0.0F, 9.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));
		PartDefinition LargeWing2 = Body.addOrReplaceChild("LargeWing2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, -1.75F, -1.0F, 0.0F, 0.0F, -0.48F));
		PartDefinition LargeWingBone2 = LargeWing2.addOrReplaceChild("LargeWingBone2", CubeListBuilder.create().texOffs(15, 0).mirror().addBox(-9.0F, -9.0F, 0.0F, 9.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));
		PartDefinition Leg1 = Body.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 2.0F, -1.0F));
		PartDefinition Leg3 = Body.addOrReplaceChild("Leg3", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 2.0F, -1.0F));
		PartDefinition Leg2 = Body.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 2.0F, 2.0F));
		PartDefinition Leg4 = Body.addOrReplaceChild("Leg4", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 2.0F, 2.0F));
		PartDefinition Jaw1 = Body.addOrReplaceChild("Jaw1", CubeListBuilder.create(), PartPose.offsetAndRotation(1.25F, 1.0F, -1.5F, 0.6545F, 0.0F, 0.0F));
		PartDefinition JawBone1 = Jaw1.addOrReplaceChild("JawBone1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -9.0F, 3.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Jaw2 = Body.addOrReplaceChild("Jaw2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.25F, 1.0F, -1.5F, 0.6545F, 0.0F, 0.0F));
		PartDefinition JawBone2 = Jaw2.addOrReplaceChild("JawBone2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-3.0F, 0.0F, -9.0F, 3.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Whizz entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.flyAnimationState, WhizzAnimations.FLY, ageInTicks, 1.0F + (limbSwingAmount * 1.4F));
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}