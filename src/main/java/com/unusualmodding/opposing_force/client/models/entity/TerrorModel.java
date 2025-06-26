package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.TerrorAnimations;
import com.unusualmodding.opposing_force.entity.Terror;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class TerrorModel<T extends Terror> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart swim_control;
	private final ModelPart Body;
	private final ModelPart Arm1;
	private final ModelPart Mid1;
	private final ModelPart Hand1;
	private final ModelPart Arm2;
	private final ModelPart Mid2;
	private final ModelPart Hand2;
	private final ModelPart LowerJaw;
	private final ModelPart TopFin1;
	private final ModelPart tailRot;
	private final ModelPart BackBody;
	private final ModelPart TopFin2;
	private final ModelPart BottomFin1;
	private final ModelPart tailFinRot;
	private final ModelPart Tail;
	private final ModelPart TailTip;

	public TerrorModel(ModelPart root) {
		this.root = root.getChild("root");
		this.swim_control = this.root.getChild("swim_control");
		this.Body = this.swim_control.getChild("Body");
		this.Arm1 = this.Body.getChild("Arm1");
		this.Mid1 = this.Arm1.getChild("Mid1");
		this.Hand1 = this.Mid1.getChild("Hand1");
		this.Arm2 = this.Body.getChild("Arm2");
		this.Mid2 = this.Arm2.getChild("Mid2");
		this.Hand2 = this.Mid2.getChild("Hand2");
		this.LowerJaw = this.Body.getChild("LowerJaw");
		this.TopFin1 = this.Body.getChild("TopFin1");
		this.tailRot = this.Body.getChild("tailRot");
		this.BackBody = this.tailRot.getChild("BackBody");
		this.TopFin2 = this.BackBody.getChild("TopFin2");
		this.BottomFin1 = this.BackBody.getChild("BottomFin1");
		this.tailFinRot = this.BackBody.getChild("tailFinRot");
		this.Tail = this.tailFinRot.getChild("Tail");
		this.TailTip = this.Tail.getChild("TailTip");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(-0.5F, -10.95F, 4.0F));
		PartDefinition Body = swim_control.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 34).addBox(6.0F, 3.95F, -17.0F, 0.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(0, 34).mirror().addBox(-5.0F, 3.95F, -17.0F, 0.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(54, 0).addBox(-4.0F, -2.05F, -4.0F, 9.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-5.0F, -3.05F, -17.0F, 11.0F, 7.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(56, 51).addBox(4.0F, -2.05F, -20.0F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(56, 51).mirror().addBox(-6.0F, -2.05F, -20.0F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(38, 0).addBox(-5.0F, 3.95F, -17.0F, 11.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));
		PartDefinition Arm1 = Body.addOrReplaceChild("Arm1", CubeListBuilder.create().texOffs(0, 28).addBox(0.0F, -1.0F, -1.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 7.95F, 0.0F));
		PartDefinition Mid1 = Arm1.addOrReplaceChild("Mid1", CubeListBuilder.create().texOffs(38, 4).addBox(-1.05F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 2.0F, 5.0F));
		PartDefinition Hand1 = Mid1.addOrReplaceChild("Hand1", CubeListBuilder.create().texOffs(0, 98).addBox(-1.05F, 2.0F, -11.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 68).addBox(-1.05F, 0.0F, -11.0F, 3.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -1.0F));
		PartDefinition Arm2 = Body.addOrReplaceChild("Arm2", CubeListBuilder.create().texOffs(0, 28).mirror().addBox(-2.0F, -1.0F, -1.0F, 2.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, 7.95F, 0.0F));
		PartDefinition Mid2 = Arm2.addOrReplaceChild("Mid2", CubeListBuilder.create().texOffs(38, 4).mirror().addBox(-0.95F, -1.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 2.0F, 5.0F));
		PartDefinition Hand2 = Mid2.addOrReplaceChild("Hand2", CubeListBuilder.create().texOffs(0, 98).mirror().addBox(-1.95F, 2.0F, -11.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 68).mirror().addBox(-1.95F, 0.0F, -11.0F, 3.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 3.0F, -1.0F));
		PartDefinition LowerJaw = Body.addOrReplaceChild("LowerJaw", CubeListBuilder.create().texOffs(0, 36).addBox(4.5F, 4.0F, -12.05F, 0.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(22, 18).addBox(4.525F, -2.0F, -4.05F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(22, 18).mirror().addBox(-6.525F, -2.0F, -4.05F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 36).mirror().addBox(-6.5F, 4.0F, -12.05F, 0.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(22, 23).addBox(-6.5F, 6.0F, -12.05F, 11.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-2.0F, 4.0F, -16.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-1.0F, 2.0F, -18.0F, 0.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(38, 2).addBox(-6.5F, 4.0F, -11.95F, 11.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 2.95F, -5.05F));
		PartDefinition TopFin1 = Body.addOrReplaceChild("TopFin1", CubeListBuilder.create().texOffs(10, 23).addBox(0.0F, -6.05F, -2.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -2.0F, 2.0F));
		PartDefinition tailRot = Body.addOrReplaceChild("tailRot", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 6.0F));
		PartDefinition BackBody = tailRot.addOrReplaceChild("BackBody", CubeListBuilder.create().texOffs(25, 38).addBox(-1.0F, -0.05F, 0.0F, 3.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition TopFin2 = BackBody.addOrReplaceChild("TopFin2", CubeListBuilder.create().texOffs(0, 14).addBox(0.0F, -5.0F, -2.0F, 0.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -0.05F, 2.0F));
		PartDefinition BottomFin1 = BackBody.addOrReplaceChild("BottomFin1", CubeListBuilder.create().texOffs(22, 26).addBox(0.5F, -0.05F, -1.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 3.0F));
		PartDefinition tailFinRot = BackBody.addOrReplaceChild("tailFinRot", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 12.0F));
		PartDefinition Tail = tailFinRot.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(0, 51).addBox(0.0F, -2.0F, 0.0F, 1.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition TailTip = Tail.addOrReplaceChild("TailTip", CubeListBuilder.create().texOffs(0, 23).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 4.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -1.0F, 10.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Terror entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.isInWaterOrBubble()) {
			this.animateWalk(TerrorAnimations.SWIM, limbSwing, limbSwingAmount, 2, 4);
		}
		this.animate(entity.flopAnimationState, TerrorAnimations.SWIM, ageInTicks, 1);

		this.animate(entity.idleAnimationState, TerrorAnimations.IDLE, ageInTicks, 1);
		this.animate(entity.attackAnimationState, TerrorAnimations.ATTACK, ageInTicks, 1);

		if (entity.isInWaterOrBubble()){
			this.swim_control.xRot = headPitch * (Mth.DEG_TO_RAD);
			this.swim_control.zRot = netHeadYaw * ((Mth.DEG_TO_RAD) / 2);

			this.tailRot.yRot = -(entity.tilt * (Mth.DEG_TO_RAD) / 4);
			this.tailFinRot.yRot = -(entity.tilt * (Mth.DEG_TO_RAD) / 4);
			this.tailRot.xRot = -(headPitch * (Mth.DEG_TO_RAD) / 8);
			this.tailFinRot.xRot = -(headPitch * (Mth.DEG_TO_RAD) / 8);
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public ModelPart root() {
		return this.root;
	}
}