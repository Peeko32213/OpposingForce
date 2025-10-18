package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.DicerAnimations;
import com.unusualmodding.opposing_force.entity.Dicer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DicerModel extends HierarchicalModel<Dicer> {

	private final ModelPart root;
	private final ModelPart Body;
	private final ModelPart Neck;
	private final ModelPart Head;
	private final ModelPart Arm1;
	private final ModelPart Claw1;
	private final ModelPart Claw2;
	private final ModelPart Arm2;
	private final ModelPart Claw3;
	private final ModelPart Claw4;
	private final ModelPart BodyLower;
	private final ModelPart TailStart;
	private final ModelPart TailMid;
	private final ModelPart TailBack;
	private final ModelPart TailSpike;
	private final ModelPart LegControl;
	private final ModelPart Leg1;
	private final ModelPart Leg2;

	public DicerModel(ModelPart root) {
		this.root = root.getChild("root");
		this.Body = this.root.getChild("Body");
		this.Neck = this.Body.getChild("Neck");
		this.Head = this.Neck.getChild("Head");
		this.Arm1 = this.Body.getChild("Arm1");
		this.Claw1 = this.Arm1.getChild("Claw1");
		this.Claw2 = this.Arm1.getChild("Claw2");
		this.Arm2 = this.Body.getChild("Arm2");
		this.Claw3 = this.Arm2.getChild("Claw3");
		this.Claw4 = this.Arm2.getChild("Claw4");
		this.BodyLower = this.Body.getChild("BodyLower");
		this.TailStart = this.BodyLower.getChild("TailStart");
		this.TailMid = this.TailStart.getChild("TailMid");
		this.TailBack = this.TailMid.getChild("TailBack");
		this.TailSpike = this.TailBack.getChild("TailSpike");
		this.LegControl = this.root.getChild("LegControl");
		this.Leg1 = this.LegControl.getChild("Leg1");
		this.Leg2 = this.LegControl.getChild("Leg2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 1.0F));

		PartDefinition Body = root.addOrReplaceChild("Body", CubeListBuilder.create()
				.texOffs(12, 38).addBox(-4.0F, -4.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).addBox(-2.0F, -4.0F, 2.0F, 0.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).mirror().addBox(2.0F, -4.0F, 2.0F, 0.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -19.0F, 0.0F));

		PartDefinition Neck = Body.addOrReplaceChild("Neck", CubeListBuilder.create()
				.texOffs(38, 1).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition Head = Neck.addOrReplaceChild("Head", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.3F)).texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-1.0F, -3.0F, -6.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(24, 22).addBox(-1.0F, -12.0F, -5.0F, 2.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(25, 9).addBox(-5.0F, -6.0F, -5.0F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition Arm1 = Body.addOrReplaceChild("Arm1", CubeListBuilder.create()
				.texOffs(44, 38).addBox(-4.0F, 0.0F, -1.0F, 4.0F, 18.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 30).addBox(-1.0F, 0.0F, -1.0F, 0.0F, 18.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(36, 38).addBox(-2.0F, 0.0F, -2.0F, 2.0F, 18.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.025F, -4.0F, 0.0F));

		PartDefinition Arm2 = Body.addOrReplaceChild("Arm2", CubeListBuilder.create()
				.texOffs(44, 38).mirror().addBox(0.0F, 0.0F, -1.0F, 4.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 30).mirror().addBox(1.0F, 0.0F, -1.0F, 0.0F, 18.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(36, 38).mirror().addBox(0.0F, 0.0F, -2.0F, 2.0F, 18.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.05F, -4.0F, 0.0F));

		PartDefinition Claw1 = Arm1.addOrReplaceChild("Claw1", CubeListBuilder.create()
				.texOffs(66, -10).addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 18.0F, -1.0F));

		PartDefinition Claw2 = Arm1.addOrReplaceChild("Claw2", CubeListBuilder.create()
				.texOffs(66, -10).addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, -1.0F));

		PartDefinition Claw3 = Arm2.addOrReplaceChild("Claw3", CubeListBuilder.create()
				.texOffs(66, -10).mirror().addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 18.0F, -1.0F));

		PartDefinition Claw4 = Arm2.addOrReplaceChild("Claw4", CubeListBuilder.create()
				.texOffs(66, -10).mirror().addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 18.0F, -1.0F));

		PartDefinition BodyLower = Body.addOrReplaceChild("BodyLower", CubeListBuilder.create()
				.texOffs(50, 0).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 2.0F));

		PartDefinition TailStart = BodyLower.addOrReplaceChild("TailStart", CubeListBuilder.create()
				.texOffs(0, 22).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(26, 18).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition TailMid = TailStart.addOrReplaceChild("TailMid", CubeListBuilder.create()
				.texOffs(0, 20).addBox(0.0F, -1.0F, 1.0F, 0.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(19, 18).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 12.0F));

		PartDefinition TailBack = TailMid.addOrReplaceChild("TailBack", CubeListBuilder.create()
				.texOffs(21, 5).addBox(-2.5F, 0.0F, 0.0F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 13.0F));

		PartDefinition TailSpike = TailBack.addOrReplaceChild("TailSpike", CubeListBuilder.create()
				.texOffs(19, 0).addBox(-4.5F, 0.0F, 0.0F, 9.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

		PartDefinition LegControl = root.addOrReplaceChild("LegControl", CubeListBuilder.create(), PartPose.offset(0.0F, -14.0F, 0.0F));

		PartDefinition Leg1 = LegControl.addOrReplaceChild("Leg1", CubeListBuilder.create()
				.texOffs(40, 18).addBox(-3.0F, 0.0F, 0.0F, 2.0F, 14.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(48, 18).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		PartDefinition Leg2 = LegControl.addOrReplaceChild("Leg2", CubeListBuilder.create()
				.texOffs(40, 18).mirror().addBox(1.0F, 0.0F, 0.0F, 2.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(48, 18).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Dicer entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.getAttackState() == 0) {
			if (entity.isRunning()) {
				this.animateWalk(DicerAnimations.RUN, limbSwing, limbSwingAmount, 1, 1);
			}
			this.animateWalk(DicerAnimations.WALK, limbSwing, limbSwingAmount, 4.5F, 8);
		}

		this.animate(entity.idleAnimationState, DicerAnimations.IDLE, ageInTicks);
		this.animate(entity.slice1AnimationState, DicerAnimations.CLAW1, ageInTicks);
		this.animate(entity.slice2AnimationState, DicerAnimations.CLAW2, ageInTicks);
		this.animate(entity.crossSliceAnimationState, DicerAnimations.CROSS_SLASH, ageInTicks);
		this.animate(entity.laserAnimationState, DicerAnimations.LASER, ageInTicks);

		this.Head.xRot += headPitch * ((float) Math.PI / 180) - (headPitch * ((float) Math.PI / 180)) / 2;
		this.Head.yRot += netHeadYaw * ((float) Math.PI / 180) - (netHeadYaw * ((float) Math.PI / 180)) / 2;
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