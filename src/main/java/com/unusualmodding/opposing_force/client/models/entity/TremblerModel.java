package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.TremblerAnimations;
import com.unusualmodding.opposing_force.entity.TremblerEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class TremblerModel<T extends TremblerEntity> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart Body;
	private final ModelPart Neck;
	private final ModelPart Head;
	private final ModelPart Jaw;
	private final ModelPart Tail;
	private final ModelPart Shell;
	private final ModelPart shell_rotation;

	public TremblerModel(ModelPart root) {
		this.root = root.getChild("root");
		this.Body = this.root.getChild("Body");
		this.Neck = this.Body.getChild("Neck");
		this.Head = this.Neck.getChild("Head");
		this.Jaw = this.Head.getChild("Jaw");
		this.Tail = this.Body.getChild("Tail");
		this.Shell = this.root.getChild("Shell");
		this.shell_rotation = this.Shell.getChild("shell_rotation");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -3.0F));
		PartDefinition Body = root.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.0F, -8.5F, 8.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 3.5F));
		PartDefinition Neck = Body.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(21, 45).addBox(-3.0F, -12.0F, -5.0F, 6.0F, 14.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 17).addBox(0.025F, -9.0F, -7.0F, 0.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 9).addBox(-3.0F, -15.0F, -1.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.025F, -4.5F));
		PartDefinition Head = Neck.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(33, 1).addBox(-5.0F, -2.0F, -6.0F, 5.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(11, 3).mirror().addBox(-5.025F, -4.0F, -7.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 0).mirror().addBox(-5.025F, -5.0F, -4.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 0).addBox(0.025F, -5.0F, -4.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(11, 3).addBox(0.025F, -4.0F, -7.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -14.0F, -2.0F));
		PartDefinition Jaw = Head.addOrReplaceChild("Jaw", CubeListBuilder.create().texOffs(31, 37).addBox(-6.0F, 0.0F, -7.0F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(0, 7).addBox(1.0F, -1.0F, -7.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(0, 7).mirror().addBox(-6.0F, -1.0F, -7.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 15).addBox(-6.0F, -1.0F, -7.0F, 7.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 43).addBox(-6.0F, -2.0F, 0.0F, 7.0F, 4.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offset(0.0F, 1.025F, -2.0F));
		PartDefinition Tail = Body.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(33, 3).addBox(0.5F, -1.0F, 0.0F, 0.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(-9, 55).addBox(-1.0F, 0.0F, 0.0F, 3.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(11, 0).addBox(-1.0F, -5.0F, 9.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 1.0F, 8.5F));
		PartDefinition Shell = root.addOrReplaceChild("Shell", CubeListBuilder.create(), PartPose.offset(3.0F, -3.0F, 0.0F));
		PartDefinition shell_rotation = Shell.addOrReplaceChild("shell_rotation", CubeListBuilder.create().texOffs(0, 19).addBox(-3.5F, -6.0F, -6.0F, 7.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -5.0F, 5.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(TremblerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(TremblerAnimations.SLIDE, limbSwing * 8, limbSwingAmount * 8, 2, 4);
		this.animate(entity.idleAnimationState, TremblerAnimations.IDLE, ageInTicks, 1);
		this.animate(entity.rollAnimationState, TremblerAnimations.ROLL, ageInTicks, 1);
		this.Head.xRot += headPitch * ((float) Math.PI / 180f) - (headPitch * ((float) Math.PI / 180f)) / 2;
		this.Head.yRot += netHeadYaw * ((float) Math.PI / 180f) - (netHeadYaw * ((float) Math.PI / 180f)) / 2;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public ModelPart root() {
		return this.root;
	}
}