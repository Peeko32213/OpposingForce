package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.FrowzyAnimations;
import com.unusualmodding.opposing_force.entity.Frowzy;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class FrowzyModel<T extends Frowzy> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart Body;
	private final ModelPart Head;
	private final ModelPart Arm1;
	private final ModelPart Arm2;
	private final ModelPart Leg1;
	private final ModelPart Leg2;

	public FrowzyModel(ModelPart root) {
		this.root = root.getChild("root");
		this.Body = this.root.getChild("Body");
		this.Head = this.Body.getChild("Head");
		this.Arm1 = this.Body.getChild("Arm1");
		this.Arm2 = this.Body.getChild("Arm2");
		this.Leg1 = this.root.getChild("Leg1");
		this.Leg2 = this.root.getChild("Leg2");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 1.0F));

		PartDefinition Body = root.addOrReplaceChild("Body", CubeListBuilder.create()
				.texOffs(0, 16).addBox(-4.0F, -6.0F, -1.0F, 8.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(1, 39).addBox(-3.5F, -5.3F, -1.0F, 7.0F, 6.0F, 3.0F, new CubeDeformation(0.8F)), PartPose.offset(0.0F, 7.0F, -0.5F));

		PartDefinition Head = Body.addOrReplaceChild("Head", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-4.0F, -8.0F, -3.5F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(2, 49).addBox(-3.0F, -6.5F, -3.0F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition Arm1 = Body.addOrReplaceChild("Arm1", CubeListBuilder.create()
				.texOffs(30, 16).addBox(0.0F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(22, 16).addBox(0.025F, 1.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -6.0F, 0.5F));

		PartDefinition Arm2 = Body.addOrReplaceChild("Arm2", CubeListBuilder.create()
				.texOffs(22, 16).mirror().addBox(-2.05F, 1.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(30, 16).mirror().addBox(-3.0F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -6.0F, 0.5F));

		PartDefinition Leg1 = root.addOrReplaceChild("Leg1", CubeListBuilder.create()
				.texOffs(8, 31).addBox(-2.0F, 0.0F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(21, 35).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 7.0F, 0.0F));

		PartDefinition Leg2 = root.addOrReplaceChild("Leg2", CubeListBuilder.create()
				.texOffs(21, 35).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(8, 25).mirror().addBox(-2.0F, 0.0F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 7.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Frowzy entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(FrowzyAnimations.WADDLE, limbSwing, limbSwingAmount, 1, 2);
		this.animate(entity.idleAnimationState, FrowzyAnimations.IDLE, ageInTicks);
		this.animate(entity.attackAnimationState, FrowzyAnimations.CRASH_OUT, ageInTicks);

		this.applyStatic(FrowzyAnimations.ARMS_OVERLAY);

		if (this.young) {
			this.applyStatic(FrowzyAnimations.BABY_TRANSFORM);
		}

		this.Head.xRot += headPitch * ((float) Math.PI / 180) - (headPitch * ((float) Math.PI / 180)) / 2;
		this.Head.yRot += netHeadYaw * ((float) Math.PI / 180) - (netHeadYaw * ((float) Math.PI / 180)) / 2;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
		if (this.young) {
			float babyScale = 0.6f;
			float bodyYOffset = 16.0f;
			poseStack.pushPose();
			poseStack.scale(babyScale, babyScale, babyScale);
			poseStack.translate(0.0f, bodyYOffset / 16.0f, 0.0f);
			this.root().render(poseStack, vertexConsumer, i, j, f, g, h, k);
			poseStack.popPose();
		} else {
			this.root().render(poseStack, vertexConsumer, i, j, f, g, h, k);
		}
	}

	public ModelPart root() {
		return this.root;
	}

	public void translateToHand(HumanoidArm humanoidArm, PoseStack poseStack) {
		this.getArm(humanoidArm).translateAndRotate(poseStack);
	}

	protected ModelPart getArm(HumanoidArm humanoidArm) {
		return humanoidArm == HumanoidArm.LEFT ? this.Arm1 : this.Arm2;
	}
}