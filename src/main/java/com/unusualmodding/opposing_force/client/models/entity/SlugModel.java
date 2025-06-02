package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.SlugAnimations;
import com.unusualmodding.opposing_force.entity.Slug;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class SlugModel<T extends Slug> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart Body;
	private final ModelPart FrontBody;
	private final ModelPart Head;
	private final ModelPart EyeStalk2;
	private final ModelPart EyeStalk1;
	private final ModelPart MainBody;
	private final ModelPart SlugTail;
	private final ModelPart Front;
	private final ModelPart Mid;
	private final ModelPart Back;
	private final ModelPart BodyCube;

	public SlugModel(ModelPart root) {
		this.root = root.getChild("root");
		this.Body = this.root.getChild("Body");
		this.FrontBody = this.Body.getChild("FrontBody");
		this.Head = this.FrontBody.getChild("Head");
		this.EyeStalk2 = this.Head.getChild("EyeStalk2");
		this.EyeStalk1 = this.Head.getChild("EyeStalk1");
		this.MainBody = this.FrontBody.getChild("MainBody");
		this.SlugTail = this.MainBody.getChild("SlugTail");
		this.Front = this.SlugTail.getChild("Front");
		this.Mid = this.Front.getChild("Mid");
		this.Back = this.Mid.getChild("Back");
		this.BodyCube = this.MainBody.getChild("BodyCube");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition Body = root.addOrReplaceChild("Body", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition FrontBody = Body.addOrReplaceChild("FrontBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Head = FrontBody.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(29, 0).addBox(-3.5F, -3.0F, -3.0F, 7.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 36).addBox(2.0F, 1.0F, -5.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 36).mirror().addBox(-5.0F, 1.0F, -5.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -2.0F, -5.0F));
		PartDefinition EyeStalk2 = Head.addOrReplaceChild("EyeStalk2", CubeListBuilder.create().texOffs(18, 32).mirror().addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 0).mirror().addBox(-1.0F, -4.0F, -4.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, -1.0F, -3.0F));
		PartDefinition EyeStalk1 = Head.addOrReplaceChild("EyeStalk1", CubeListBuilder.create().texOffs(18, 32).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(0.0F, -4.0F, -4.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -1.0F, -3.0F));
		PartDefinition MainBody = FrontBody.addOrReplaceChild("MainBody", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition SlugTail = MainBody.addOrReplaceChild("SlugTail", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 6.0F));
		PartDefinition Front = SlugTail.addOrReplaceChild("Front", CubeListBuilder.create().texOffs(0, 29).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 2).addBox(1.5F, -3.0F, 2.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 2).mirror().addBox(-1.5F, -3.0F, 2.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Mid = Front.addOrReplaceChild("Mid", CubeListBuilder.create().texOffs(19, 24).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 3.0F, 5.0F, new CubeDeformation(0.01F)).texOffs(6, 3).addBox(1.5F, -2.0F, 3.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(6, 3).mirror().addBox(-1.5F, -2.0F, 3.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 4.0F));
		PartDefinition Back = Mid.addOrReplaceChild("Back", CubeListBuilder.create().texOffs(0, 21).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 6).addBox(1.5F, -2.0F, 2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 6).mirror().addBox(-1.5F, -2.0F, 2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 5.0F));
		PartDefinition BodyCube = MainBody.addOrReplaceChild("BodyCube", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -10.0F, -5.0F, 9.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Slug entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.animateWalk(SlugAnimations.SLIDE, limbSwing * 4, limbSwingAmount * 4, 2, 4);
		this.animate(entity.idleAnimationState, SlugAnimations.IDLE, ageInTicks, 1);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public ModelPart root() {
		return this.root;
	}
}