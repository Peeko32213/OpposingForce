package com.unusualmodding.opposing_force.client.models.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.VoltAnimations;
import com.unusualmodding.opposing_force.entity.Volt;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class VoltModel<T extends Volt> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart Body;
	private final ModelPart Eye1;
	private final ModelPart EyePart1;
	private final ModelPart Eye2;
	private final ModelPart EyePart2;
	private final ModelPart BottomJaw;
	private final ModelPart MantaThing1;
	private final ModelPart MantaThing2;
	private final ModelPart Wing1;
	private final ModelPart WingMid1;
	private final ModelPart WingBottom1;
	private final ModelPart WingTip1;
	private final ModelPart Wing2;
	private final ModelPart WingMid2;
	private final ModelPart WingBottom2;
	private final ModelPart WingTip2;
	private final ModelPart Tail;
	private final ModelPart TailPart1;
	private final ModelPart TailPart2;
	private final ModelPart TailPart3;
	private final ModelPart TailPart4;
	private final ModelPart TailPart5;
	private final ModelPart TailPart6;
	private final List<ModelPart> pulsatingLayerModelParts;

	public VoltModel(ModelPart root) {
		this.root = root.getChild("root");
		this.Body = this.root.getChild("Body");
		this.Eye1 = this.Body.getChild("Eye1");
		this.EyePart1 = this.Eye1.getChild("EyePart1");
		this.Eye2 = this.Body.getChild("Eye2");
		this.EyePart2 = this.Eye2.getChild("EyePart2");
		this.BottomJaw = this.Body.getChild("BottomJaw");
		this.MantaThing1 = this.Body.getChild("MantaThing1");
		this.MantaThing2 = this.Body.getChild("MantaThing2");
		this.Wing1 = this.Body.getChild("Wing1");
		this.WingMid1 = this.Wing1.getChild("WingMid1");
		this.WingBottom1 = this.WingMid1.getChild("WingBottom1");
		this.WingTip1 = this.WingBottom1.getChild("WingTip1");
		this.Wing2 = this.Body.getChild("Wing2");
		this.WingMid2 = this.Wing2.getChild("WingMid2");
		this.WingBottom2 = this.WingMid2.getChild("WingBottom2");
		this.WingTip2 = this.WingBottom2.getChild("WingTip2");
		this.Tail = this.root.getChild("Tail");
		this.TailPart1 = this.Tail.getChild("TailPart1");
		this.TailPart2 = this.TailPart1.getChild("TailPart2");
		this.TailPart3 = this.TailPart2.getChild("TailPart3");
		this.TailPart4 = this.TailPart3.getChild("TailPart4");
		this.TailPart5 = this.TailPart4.getChild("TailPart5");
		this.TailPart6 = this.TailPart5.getChild("TailPart6");
		this.pulsatingLayerModelParts = ImmutableList.of(this.Body, this.Eye1, this.EyePart1, this.Eye2, this.EyePart2, this.Wing1, this.WingMid1, this.WingBottom1, this.WingTip1, this.Wing2, this.WingMid2, this.WingBottom2, this.WingTip2, this.MantaThing1, this.MantaThing2, this.TailPart1, this.TailPart2, this.TailPart3, this.TailPart6);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition Body = root.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, 0.0F, -7.0F, 15.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(22, 14).addBox(-5.5F, 0.975F, -6.0F, 11.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 14).addBox(-2.5F, 1.0F, -6.5F, 5.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(0, 21).addBox(-1.5F, 1.0F, -7.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 25).addBox(4.0F, -2.0F, -7.0F, 0.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(0, 25).mirror().addBox(-4.0F, -2.0F, -7.0F, 0.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -21.0F, -1.0F));
		PartDefinition Eye1 = Body.addOrReplaceChild("Eye1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, -7.0F));
		PartDefinition EyePart1 = Eye1.addOrReplaceChild("EyePart1", CubeListBuilder.create().texOffs(19, 14).addBox(-1.0F, 0.0F, 0.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -3.0F, 0.0F));
		PartDefinition Eye2 = Body.addOrReplaceChild("Eye2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-1.0F, -3.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 0.0F, -7.0F));
		PartDefinition EyePart2 = Eye2.addOrReplaceChild("EyePart2", CubeListBuilder.create().texOffs(19, 14).mirror().addBox(-2.0F, 0.0F, 0.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, -3.0F, 0.0F));
		PartDefinition BottomJaw = Body.addOrReplaceChild("BottomJaw", CubeListBuilder.create().texOffs(38, 24).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -5.0F));
		PartDefinition MantaThing1 = Body.addOrReplaceChild("MantaThing1", CubeListBuilder.create().texOffs(23, 29).addBox(-3.5F, 0.0F, 0.0F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 1.0F, -7.0F));
		PartDefinition MantaThing2 = Body.addOrReplaceChild("MantaThing2", CubeListBuilder.create().texOffs(23, 29).mirror().addBox(-1.5F, 0.0F, 0.0F, 5.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, 1.0F, -7.0F));
		PartDefinition Wing1 = Body.addOrReplaceChild("Wing1", CubeListBuilder.create().texOffs(0, 29).addBox(0.0F, 0.0F, -1.0F, 8.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, 0.0F, -6.0F));
		PartDefinition WingMid1 = Wing1.addOrReplaceChild("WingMid1", CubeListBuilder.create().texOffs(12, 31).addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.0F, 0.0F));
		PartDefinition WingBottom1 = WingMid1.addOrReplaceChild("WingBottom1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));
		PartDefinition WingTip1 = WingBottom1.addOrReplaceChild("WingTip1", CubeListBuilder.create().texOffs(32, 31).addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));
		PartDefinition Wing2 = Body.addOrReplaceChild("Wing2", CubeListBuilder.create().texOffs(0, 29).mirror().addBox(-8.0F, 0.0F, -1.0F, 8.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.5F, 0.0F, -6.0F));
		PartDefinition WingMid2 = Wing2.addOrReplaceChild("WingMid2", CubeListBuilder.create().texOffs(12, 31).mirror().addBox(0.0F, 0.0F, -1.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, 0.0F, 0.0F));
		PartDefinition WingBottom2 = WingMid2.addOrReplaceChild("WingBottom2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 6.0F, 0.0F));
		PartDefinition WingTip2 = WingBottom2.addOrReplaceChild("WingTip2", CubeListBuilder.create().texOffs(32, 31).mirror().addBox(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 0.0F, 0.0F));
		PartDefinition Tail = root.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(0, 38).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.0F, 3.5F));
		PartDefinition TailPart1 = Tail.addOrReplaceChild("TailPart1", CubeListBuilder.create().texOffs(34, 40).addBox(-1.0F, -6.0F, -2.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.01F)).texOffs(0, 8).addBox(0.0F, -10.0F, -4.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 6.0F));
		PartDefinition TailPart2 = TailPart1.addOrReplaceChild("TailPart2", CubeListBuilder.create().texOffs(38, 30).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 13).addBox(0.0F, -3.0F, 2.0F, 0.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 1.0F));
		PartDefinition TailPart3 = TailPart2.addOrReplaceChild("TailPart3", CubeListBuilder.create().texOffs(26, 38).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.01F)).texOffs(0, 2).addBox(0.0F, 0.0F, 2.0F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));
		PartDefinition TailPart4 = TailPart3.addOrReplaceChild("TailPart4", CubeListBuilder.create().texOffs(22, 24).addBox(-1.0F, -1.0F, -12.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));
		PartDefinition TailPart5 = TailPart4.addOrReplaceChild("TailPart5", CubeListBuilder.create().texOffs(43, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 1.0F, -11.0F));
		PartDefinition TailPart6 = TailPart5.addOrReplaceChild("TailPart6", CubeListBuilder.create().texOffs(0, 1).addBox(0.0F, -6.0F, 0.0F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 1.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Volt entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(VoltAnimations.SLIDE, limbSwing, limbSwingAmount, 4, 12);
		this.animate(entity.idleAnimationState, VoltAnimations.IDLE, ageInTicks, 1);
		this.animate(entity.shootAnimationState, VoltAnimations.SHOOT, ageInTicks, 1);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	public List<ModelPart> getPulsatingLayerModelParts() {
		return this.pulsatingLayerModelParts;
	}
}