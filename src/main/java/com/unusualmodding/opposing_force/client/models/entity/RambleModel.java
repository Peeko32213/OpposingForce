package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.entity.RambleEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class RambleModel<T extends RambleEntity> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart Body;
	private final ModelPart FrontArm1;
	private final ModelPart FrontArm2;
	private final ModelPart LeftArm1;
	private final ModelPart LeftArm2;
	private final ModelPart RightArm1;
	private final ModelPart RightArm2;
	private final ModelPart BackArm1;
	private final ModelPart BackArm2;
	private final ModelPart FrontHead;
	private final ModelPart LeftHead;
	private final ModelPart RightHead;
	private final ModelPart BackHead;
	private final ModelPart OuterLeg1;
	private final ModelPart Leg1;
	private final ModelPart Fingers1;
	private final ModelPart OuterLeg2;
	private final ModelPart Leg2;
	private final ModelPart Fingers2;
	private final ModelPart OuterLeg3;
	private final ModelPart Leg3;
	private final ModelPart Fingers3;
	private final ModelPart OuterLeg4;
	private final ModelPart Leg4;
	private final ModelPart Fingers4;

	public RambleModel(ModelPart root) {
		this.root = root.getChild("root");
		this.Body = this.root.getChild("Body");
		this.FrontArm1 = this.Body.getChild("FrontArm1");
		this.FrontArm2 = this.Body.getChild("FrontArm2");
		this.LeftArm1 = this.Body.getChild("LeftArm1");
		this.LeftArm2 = this.Body.getChild("LeftArm2");
		this.RightArm1 = this.Body.getChild("RightArm1");
		this.RightArm2 = this.Body.getChild("RightArm2");
		this.BackArm1 = this.Body.getChild("BackArm1");
		this.BackArm2 = this.Body.getChild("BackArm2");
		this.FrontHead = this.Body.getChild("FrontHead");
		this.LeftHead = this.Body.getChild("LeftHead");
		this.RightHead = this.Body.getChild("RightHead");
		this.BackHead = this.Body.getChild("BackHead");
		this.OuterLeg1 = this.root.getChild("OuterLeg1");
		this.Leg1 = this.OuterLeg1.getChild("Leg1");
		this.Fingers1 = this.Leg1.getChild("Fingers1");
		this.OuterLeg2 = this.root.getChild("OuterLeg2");
		this.Leg2 = this.OuterLeg2.getChild("Leg2");
		this.Fingers2 = this.Leg2.getChild("Fingers2");
		this.OuterLeg3 = this.root.getChild("OuterLeg3");
		this.Leg3 = this.OuterLeg3.getChild("Leg3");
		this.Fingers3 = this.Leg3.getChild("Fingers3");
		this.OuterLeg4 = this.root.getChild("OuterLeg4");
		this.Leg4 = this.OuterLeg4.getChild("Leg4");
		this.Fingers4 = this.Leg4.getChild("Fingers4");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition Body = root.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -21.05F, -10.0F, 20.0F, 21.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(96, 70).addBox(-4.0F, -26.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 0.0F));
		PartDefinition FrontArm1 = Body.addOrReplaceChild("FrontArm1", CubeListBuilder.create().texOffs(2, 80).addBox(-1.0F, 14.0F, -16.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(112, 51).addBox(-2.5F, 16.0F, -19.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -19.0F, -10.0F));
		PartDefinition FrontArm2 = Body.addOrReplaceChild("FrontArm2", CubeListBuilder.create().texOffs(2, 80).mirror().addBox(-1.0F, 14.0F, -16.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 0).mirror().addBox(-1.0F, 0.0F, -2.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(112, 51).addBox(-2.5F, 16.0F, -19.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -19.0F, -10.0F));
		PartDefinition LeftArm1 = Body.addOrReplaceChild("LeftArm1", CubeListBuilder.create().texOffs(40, 110).addBox(2.0F, 14.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(0.0F, 0.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(110, 56).addBox(18.0F, 16.0F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -19.0F, -6.0F));
		PartDefinition LeftArm2 = Body.addOrReplaceChild("LeftArm2", CubeListBuilder.create().texOffs(40, 110).addBox(2.0F, 14.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(0.0F, 0.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(110, 56).addBox(18.0F, 16.0F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -19.0F, 6.0F));
		PartDefinition RightArm1 = Body.addOrReplaceChild("RightArm1", CubeListBuilder.create().texOffs(40, 110).mirror().addBox(-18.0F, 14.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 0).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(110, 56).mirror().addBox(-21.0F, 16.0F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-10.0F, -19.0F, -6.0F));
		PartDefinition RightArm2 = Body.addOrReplaceChild("RightArm2", CubeListBuilder.create().texOffs(40, 110).mirror().addBox(-18.0F, 14.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 0).mirror().addBox(-2.0F, 0.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(110, 56).mirror().addBox(-21.0F, 16.0F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-10.0F, -19.0F, 6.0F));
		PartDefinition BackArm1 = Body.addOrReplaceChild("BackArm1", CubeListBuilder.create().texOffs(78, 98).addBox(-1.0F, 14.0F, 2.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(112, 63).addBox(-2.5F, 16.0F, 16.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -19.0F, 10.0F));
		PartDefinition BackArm2 = Body.addOrReplaceChild("BackArm2", CubeListBuilder.create().texOffs(78, 98).mirror().addBox(-1.0F, 14.0F, 2.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 0).mirror().addBox(-1.0F, 0.0F, 0.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(112, 63).addBox(-2.5F, 16.0F, 16.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -19.0F, 10.0F));
		PartDefinition FrontHead = Body.addOrReplaceChild("FrontHead", CubeListBuilder.create().texOffs(64, 70).addBox(-4.0F, -6.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -10.05F));
		PartDefinition LeftHead = Body.addOrReplaceChild("LeftHead", CubeListBuilder.create().texOffs(60, 0).addBox(0.0F, -6.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(10.025F, -4.0F, 0.0F));
		PartDefinition RightHead = Body.addOrReplaceChild("RightHead", CubeListBuilder.create().texOffs(60, 0).mirror().addBox(-8.0F, -6.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-10.025F, -4.0F, 0.0F));
		PartDefinition BackHead = Body.addOrReplaceChild("BackHead", CubeListBuilder.create().texOffs(54, 41).addBox(-4.0F, -6.0F, 0.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 10.1F));
		PartDefinition OuterLeg1 = root.addOrReplaceChild("OuterLeg1", CubeListBuilder.create(), PartPose.offset(7.0F, -16.0F, -7.0F));
		PartDefinition Leg1 = OuterLeg1.addOrReplaceChild("Leg1", CubeListBuilder.create().texOffs(0, 41).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Fingers1 = Leg1.addOrReplaceChild("Fingers1", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 16.0F, -1.0F, -0.3927F, 0.0F, -0.3927F));
		PartDefinition Fingers1_r1 = Fingers1.addOrReplaceChild("Fingers1_r1", CubeListBuilder.create().texOffs(108, 5).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 1.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition OuterLeg2 = root.addOrReplaceChild("OuterLeg2", CubeListBuilder.create(), PartPose.offset(-7.0F, -16.0F, -7.0F));
		PartDefinition Leg2 = OuterLeg2.addOrReplaceChild("Leg2", CubeListBuilder.create().texOffs(0, 41).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Fingers2 = Leg2.addOrReplaceChild("Fingers2", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 16.0F, 0.0F, -0.3927F, 0.0F, 0.3927F));
		PartDefinition Fingers2_r1 = Fingers2.addOrReplaceChild("Fingers2_r1", CubeListBuilder.create().texOffs(108, 5).mirror().addBox(-2.5F, 0.0F, -6.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		PartDefinition OuterLeg3 = root.addOrReplaceChild("OuterLeg3", CubeListBuilder.create(), PartPose.offset(7.0F, -16.0F, 7.0F));
		PartDefinition Leg3 = OuterLeg3.addOrReplaceChild("Leg3", CubeListBuilder.create().texOffs(0, 41).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Fingers3 = Leg3.addOrReplaceChild("Fingers3", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 16.0F, 0.0F, 0.3927F, 0.0F, -0.3927F));
		PartDefinition Fingers3_r1 = Fingers3.addOrReplaceChild("Fingers3_r1", CubeListBuilder.create().texOffs(108, 12).addBox(-2.5F, 0.0F, 1.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		PartDefinition OuterLeg4 = root.addOrReplaceChild("OuterLeg4", CubeListBuilder.create(), PartPose.offset(-7.0F, -16.0F, 7.0F));
		PartDefinition Leg4 = OuterLeg4.addOrReplaceChild("Leg4", CubeListBuilder.create().texOffs(0, 41).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Fingers4 = Leg4.addOrReplaceChild("Fingers4", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 16.0F, 0.0F, 0.3927F, 0.0F, 0.3927F));
		PartDefinition Fingers4_r1 = Fingers4.addOrReplaceChild("Fingers4_r1", CubeListBuilder.create().texOffs(108, 12).mirror().addBox(-2.5F, 0.0F, 1.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(RambleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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