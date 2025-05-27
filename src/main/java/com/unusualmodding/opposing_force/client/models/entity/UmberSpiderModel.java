package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.UmberSpiderAnimations;
import com.unusualmodding.opposing_force.entity.UmberSpiderEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class UmberSpiderModel<T extends UmberSpiderEntity> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart Body;
	private final ModelPart EyeLayer;
	private final ModelPart CenterHorns;
	private final ModelPart SideHorns1;
	private final ModelPart SideHorns2;
	private final ModelPart Mandible1;
	private final ModelPart Mandible2;
	private final ModelPart Tail;
	private final ModelPart TailPart1;
	private final ModelPart TailPart2;
	private final ModelPart TailPart3;
	private final ModelPart TailPart4;
	private final ModelPart TailPart5;
	private final ModelPart TailPart6;
	private final ModelPart LimbCluster1;
	private final ModelPart ArmBone1;
	private final ModelPart Arm1;
	private final ModelPart LegBone4;
	private final ModelPart Leg4;
	private final ModelPart LegBone2;
	private final ModelPart Leg2;
	private final ModelPart LegBone3;
	private final ModelPart Leg3;
	private final ModelPart LimbCluster2;
	private final ModelPart ArmBone2;
	private final ModelPart Arm2;
	private final ModelPart LegBone5;
	private final ModelPart Leg5;
	private final ModelPart LegBone6;
	private final ModelPart Leg6;
	private final ModelPart LegBone7;
	private final ModelPart Leg7;

	public UmberSpiderModel(ModelPart root) {
		this.root = root.getChild("root");
		this.Body = this.root.getChild("Body");
		this.EyeLayer = this.Body.getChild("EyeLayer");
		this.CenterHorns = this.Body.getChild("CenterHorns");
		this.SideHorns1 = this.Body.getChild("SideHorns1");
		this.SideHorns2 = this.Body.getChild("SideHorns2");
		this.Mandible1 = this.Body.getChild("Mandible1");
		this.Mandible2 = this.Body.getChild("Mandible2");
		this.Tail = this.Body.getChild("Tail");
		this.TailPart1 = this.Tail.getChild("TailPart1");
		this.TailPart2 = this.TailPart1.getChild("TailPart2");
		this.TailPart3 = this.TailPart2.getChild("TailPart3");
		this.TailPart4 = this.TailPart3.getChild("TailPart4");
		this.TailPart5 = this.TailPart4.getChild("TailPart5");
		this.TailPart6 = this.TailPart5.getChild("TailPart6");
		this.LimbCluster1 = this.root.getChild("LimbCluster1");
		this.ArmBone1 = this.LimbCluster1.getChild("ArmBone1");
		this.Arm1 = this.ArmBone1.getChild("Arm1");
		this.LegBone4 = this.LimbCluster1.getChild("LegBone4");
		this.Leg4 = this.LegBone4.getChild("Leg4");
		this.LegBone2 = this.LimbCluster1.getChild("LegBone2");
		this.Leg2 = this.LegBone2.getChild("Leg2");
		this.LegBone3 = this.LimbCluster1.getChild("LegBone3");
		this.Leg3 = this.LegBone3.getChild("Leg3");
		this.LimbCluster2 = this.root.getChild("LimbCluster2");
		this.ArmBone2 = this.LimbCluster2.getChild("ArmBone2");
		this.Arm2 = this.ArmBone2.getChild("Arm2");
		this.LegBone5 = this.LimbCluster2.getChild("LegBone5");
		this.Leg5 = this.LegBone5.getChild("Leg5");
		this.LegBone6 = this.LimbCluster2.getChild("LegBone6");
		this.Leg6 = this.LegBone6.getChild("Leg6");
		this.LegBone7 = this.LimbCluster2.getChild("LegBone7");
		this.Leg7 = this.LegBone7.getChild("Leg7");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition Body = root.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 33).addBox(-7.0F, -4.0F, -5.0F, 15.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 41).addBox(-6.0F, -3.0F, -1.0F, 13.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 16).addBox(-7.0F, -4.0F, 0.0F, 15.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -3.0F, -1.0F, 0.1745F, 0.0F, 0.0F));
		PartDefinition EyeLayer = Body.addOrReplaceChild("EyeLayer", CubeListBuilder.create().texOffs(0, 53).addBox(-7.0F, -4.0F, 5.0F, 15.0F, 4.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, -10.0F));
		PartDefinition CenterHorns = Body.addOrReplaceChild("CenterHorns", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, 0.0F, 0.0F, 19.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.1309F, 0.0F, 0.0F));
		PartDefinition SideHorns1 = Body.addOrReplaceChild("SideHorns1", CubeListBuilder.create().texOffs(33, 33).addBox(-3.0F, 0.0F, 0.0F, 7.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -4.0F, 0.0F, 0.5498F, 0.0F, 0.0F));
		PartDefinition SideHorns2 = Body.addOrReplaceChild("SideHorns2", CubeListBuilder.create().texOffs(33, 33).mirror().addBox(-4.0F, 0.0F, 0.0F, 7.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.0F, -4.0F, 0.0F, 0.5498F, 0.0F, 0.0F));
		PartDefinition Mandible1 = Body.addOrReplaceChild("Mandible1", CubeListBuilder.create().texOffs(10, 0).addBox(-0.75F, 0.0F, -7.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 11).addBox(-0.75F, -5.0F, -9.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(43, 16).addBox(-2.0F, -3.0F, -5.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -2.0F, -5.0F, -0.2182F, 0.0F, 0.0F));
		PartDefinition Mandible2 = Body.addOrReplaceChild("Mandible2", CubeListBuilder.create().texOffs(10, 0).mirror().addBox(0.75F, 0.0F, -7.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 11).mirror().addBox(0.75F, -5.0F, -9.0F, 0.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(43, 16).mirror().addBox(-1.0F, -3.0F, -5.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -2.0F, -5.0F, -0.2182F, 0.0F, 0.0F));
		PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(25, 33).addBox(-1.0F, -2.0F, -0.25F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(8, 4).addBox(-1.0F, -12.0F, 10.75F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -1.0F, 13.25F));
		PartDefinition TailPart1 = Tail.addOrReplaceChild("TailPart1", CubeListBuilder.create().texOffs(2, 0).addBox(-0.5F, 0.0F, -6.0F, 1.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 10.75F));
		PartDefinition TailPart2 = TailPart1.addOrReplaceChild("TailPart2", CubeListBuilder.create().texOffs(8, 20).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -6.0F));
		PartDefinition TailPart3 = TailPart2.addOrReplaceChild("TailPart3", CubeListBuilder.create().texOffs(9, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));
		PartDefinition TailPart4 = TailPart3.addOrReplaceChild("TailPart4", CubeListBuilder.create().texOffs(10, 20).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));
		PartDefinition TailPart5 = TailPart4.addOrReplaceChild("TailPart5", CubeListBuilder.create().texOffs(4, 0).addBox(-0.5F, 0.0F, -2.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));
		PartDefinition TailPart6 = TailPart5.addOrReplaceChild("TailPart6", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));
		PartDefinition LimbCluster1 = root.addOrReplaceChild("LimbCluster1", CubeListBuilder.create(), PartPose.offset(-5.0F, 0.0F, -1.0F));
		PartDefinition ArmBone1 = LimbCluster1.addOrReplaceChild("ArmBone1", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 0.0F, -2.0F, 0.0F, 0.4363F, 0.0F));
		PartDefinition Arm1 = ArmBone1.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(0, 20).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 45).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
		PartDefinition LegBone4 = LimbCluster1.addOrReplaceChild("LegBone4", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -2.0F, 1.0F, 0.0F, -0.48F, 0.0F));
		PartDefinition Leg4 = LegBone4.addOrReplaceChild("Leg4", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(42, 42).addBox(-6.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.2217F));
		PartDefinition LegBone2 = LimbCluster1.addOrReplaceChild("LegBone2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -2.0F, 1.0F, 0.0F, 0.4363F, 0.0F));
		PartDefinition Leg2 = LegBone2.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(42, 42).addBox(-6.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.2217F));
		PartDefinition LegBone3 = LimbCluster1.addOrReplaceChild("LegBone3", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, -2.0F, 3.0F, 0.0F, 0.9163F, 0.0F));
		PartDefinition Leg3 = LegBone3.addOrReplaceChild("Leg3", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(42, 42).addBox(-6.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.2217F));
		PartDefinition LimbCluster2 = root.addOrReplaceChild("LimbCluster2", CubeListBuilder.create(), PartPose.offset(5.0F, 0.0F, -1.0F));
		PartDefinition ArmBone2 = LimbCluster2.addOrReplaceChild("ArmBone2", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 0.0F, -2.0F, 0.0F, -0.4363F, 0.0F));
		PartDefinition Arm2 = ArmBone2.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(0, 20).mirror().addBox(-1.0F, 0.0F, -6.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 45).mirror().addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));
		PartDefinition LegBone5 = LimbCluster2.addOrReplaceChild("LegBone5", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -2.0F, 1.0F, 0.0F, 0.48F, 0.0F));
		PartDefinition Leg5 = LegBone5.addOrReplaceChild("Leg5", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(4.0F, 0.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(42, 42).mirror().addBox(0.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 1.0F, 0.0F, 0.0F, 0.0F, -1.2217F));
		PartDefinition LegBone6 = LimbCluster2.addOrReplaceChild("LegBone6", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -2.0F, 1.0F, 0.0F, -0.4363F, 0.0F));
		PartDefinition Leg6 = LegBone6.addOrReplaceChild("Leg6", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(4.0F, 0.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(42, 42).mirror().addBox(0.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 1.0F, 0.0F, 0.0F, 0.0F, -1.2217F));
		PartDefinition LegBone7 = LimbCluster2.addOrReplaceChild("LegBone7", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -2.0F, 3.0F, 0.0F, -0.9163F, 0.0F));
		PartDefinition Leg7 = LegBone7.addOrReplaceChild("Leg7", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(4.0F, 0.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(42, 42).mirror().addBox(0.0F, -2.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 1.0F, 0.0F, 0.0F, 0.0F, -1.2217F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(UmberSpiderEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(UmberSpiderAnimations.SCURRY, limbSwing, limbSwingAmount, 2, 4);
		this.animate(entity.idleAnimationState, UmberSpiderAnimations.IDLE, ageInTicks, 1);
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