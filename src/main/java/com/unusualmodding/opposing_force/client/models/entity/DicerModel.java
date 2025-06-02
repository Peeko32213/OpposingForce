package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.entity.DicerEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DicerModel<T extends DicerEntity> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart neck;
	private final ModelPart head;
	private final ModelPart leftArm;
	private final ModelPart leftClaw1;
	private final ModelPart leftClaw2;
	private final ModelPart rightArm;
	private final ModelPart rightClaw1;
	private final ModelPart rightClaw2;
	private final ModelPart lowerBody;
	private final ModelPart tailStart;
	private final ModelPart tailMid;
	private final ModelPart tailBack;
	private final ModelPart tailSpike;
	private final ModelPart legControl;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	public DicerModel(ModelPart root) {
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.neck = this.body.getChild("neck");
		this.head = this.neck.getChild("head");
		this.leftArm = this.body.getChild("leftArm");
		this.leftClaw1 = this.leftArm.getChild("leftClaw1");
		this.leftClaw2 = this.leftArm.getChild("leftClaw2");
		this.rightArm = this.body.getChild("rightArm");
		this.rightClaw1 = this.rightArm.getChild("rightClaw1");
		this.rightClaw2 = this.rightArm.getChild("rightClaw2");
		this.lowerBody = this.body.getChild("lowerBody");
		this.tailStart = this.lowerBody.getChild("tailStart");
		this.tailMid = this.tailStart.getChild("tailMid");
		this.tailBack = this.tailMid.getChild("tailBack");
		this.tailSpike = this.tailBack.getChild("tailSpike");
		this.legControl = this.root.getChild("legControl");
		this.leftLeg = this.legControl.getChild("leftLeg");
		this.rightLeg = this.legControl.getChild("rightLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 1.0F));
		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(12, 38).addBox(-4.0F, -4.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 12).addBox(-2.0F, -4.0F, 2.0F, 0.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 12).mirror().addBox(2.0F, -4.0F, 2.0F, 0.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -19.0F, 0.0F));
		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(38, 1).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));
		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.3F)).texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-1.0F, -3.0F, -6.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(24, 22).addBox(-1.0F, -12.0F, -5.0F, 2.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(25, 9).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));
		PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(44, 38).addBox(-4.0F, 0.0F, -1.0F, 4.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 30).addBox(-1.0F, 0.0F, -1.0F, 0.0F, 18.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(36, 38).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 18.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.025F, -4.0F, 0.0F));
		PartDefinition leftClaw1 = leftArm.addOrReplaceChild("leftClaw1", CubeListBuilder.create().texOffs(66, -10).addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 18.0F, -1.0F));
		PartDefinition leftClaw2 = leftArm.addOrReplaceChild("leftClaw2", CubeListBuilder.create().texOffs(66, -10).addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, -1.0F));
		PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(44, 38).mirror().addBox(0.0F, 0.0F, -1.0F, 4.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 30).mirror().addBox(1.0F, 0.0F, -1.0F, 0.0F, 18.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(36, 38).mirror().addBox(0.0F, 0.0F, -2.0F, 2.0F, 18.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.05F, -4.0F, 0.0F));
		PartDefinition rightClaw1 = rightArm.addOrReplaceChild("rightClaw1", CubeListBuilder.create().texOffs(66, -10).mirror().addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 18.0F, -1.0F));
		PartDefinition rightClaw2 = rightArm.addOrReplaceChild("rightClaw2", CubeListBuilder.create().texOffs(66, -10).mirror().addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 18.0F, -1.0F));
		PartDefinition lowerBody = body.addOrReplaceChild("lowerBody", CubeListBuilder.create().texOffs(50, 0).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 2.0F));
		PartDefinition tailStart = lowerBody.addOrReplaceChild("tailStart", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(26, 18).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));
		PartDefinition tailMid = tailStart.addOrReplaceChild("tailMid", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -1.0F, 1.0F, 0.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(19, 18).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));
		PartDefinition tailBack = tailMid.addOrReplaceChild("tailBack", CubeListBuilder.create().texOffs(21, 5).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 13.0F));
		PartDefinition tailSpike = tailBack.addOrReplaceChild("tailSpike", CubeListBuilder.create().texOffs(19, 0).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));
		PartDefinition legControl = root.addOrReplaceChild("legControl", CubeListBuilder.create(), PartPose.offset(0.0F, -14.0F, 0.0F));
		PartDefinition leftLeg = legControl.addOrReplaceChild("leftLeg", CubeListBuilder.create().texOffs(40, 18).addBox(-3.0F, 0.0F, 0.0F, 2.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(48, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 0.0F));
		PartDefinition rightLeg = legControl.addOrReplaceChild("rightLeg", CubeListBuilder.create().texOffs(40, 18).mirror().addBox(1.0F, 0.0F, 0.0F, 2.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(48, 18).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(DicerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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