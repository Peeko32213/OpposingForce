package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.entity.PaleSpiderEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class PaleSpiderModel<T extends PaleSpiderEntity> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart Body;
	private final ModelPart Thorax;
	private final ModelPart Head;
	private final ModelPart Fang1;
	private final ModelPart Fang2;
	private final ModelPart Eyes;
	private final ModelPart LegCluster1;
	private final ModelPart LegReal1;
	private final ModelPart Leg1;
	private final ModelPart LegReal2;
	private final ModelPart Leg2;
	private final ModelPart LegReal3;
	private final ModelPart Leg3;
	private final ModelPart LegReal4;
	private final ModelPart Leg4;
	private final ModelPart LegCluster2;
	private final ModelPart LegReal5;
	private final ModelPart Leg5;
	private final ModelPart LegReal6;
	private final ModelPart Leg6;
	private final ModelPart LegReal7;
	private final ModelPart Leg7;
	private final ModelPart LegReal8;
	private final ModelPart Leg8;

	public PaleSpiderModel(ModelPart root) {
		this.root = root.getChild("root");
		this.Body = this.root.getChild("Body");
		this.Thorax = this.Body.getChild("Thorax");
		this.Head = this.Body.getChild("Head");
		this.Fang1 = this.Head.getChild("Fang1");
		this.Fang2 = this.Head.getChild("Fang2");
		this.Eyes = this.Head.getChild("Eyes");
		this.LegCluster1 = this.root.getChild("LegCluster1");
		this.LegReal1 = this.LegCluster1.getChild("LegReal1");
		this.Leg1 = this.LegReal1.getChild("Leg1");
		this.LegReal2 = this.LegCluster1.getChild("LegReal2");
		this.Leg2 = this.LegReal2.getChild("Leg2");
		this.LegReal3 = this.LegCluster1.getChild("LegReal3");
		this.Leg3 = this.LegReal3.getChild("Leg3");
		this.LegReal4 = this.LegCluster1.getChild("LegReal4");
		this.Leg4 = this.LegReal4.getChild("Leg4");
		this.LegCluster2 = this.root.getChild("LegCluster2");
		this.LegReal5 = this.LegCluster2.getChild("LegReal5");
		this.Leg5 = this.LegReal5.getChild("Leg5");
		this.LegReal6 = this.LegCluster2.getChild("LegReal6");
		this.Leg6 = this.LegReal6.getChild("Leg6");
		this.LegReal7 = this.LegCluster2.getChild("LegReal7");
		this.Leg7 = this.LegReal7.getChild("Leg7");
		this.LegReal8 = this.LegCluster2.getChild("LegReal8");
		this.Leg8 = this.LegReal8.getChild("Leg8");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));
		PartDefinition Body = root.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(19, 16).addBox(-2.5F, -3.0F, -2.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Thorax = Body.addOrReplaceChild("Thorax", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -5.0F, 0.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(19, 0).addBox(-2.5F, -1.0F, 7.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));
		PartDefinition Head = Body.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 16).addBox(-3.5F, -3.0F, -5.0F, 7.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -2.0F));
		PartDefinition Fang1 = Head.addOrReplaceChild("Fang1", CubeListBuilder.create().texOffs(21, 2).addBox(-1.0F, 0.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(24, 21).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, -5.0F));
		PartDefinition Fang2 = Head.addOrReplaceChild("Fang2", CubeListBuilder.create().texOffs(21, 2).mirror().addBox(-1.0F, 0.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(24, 21).mirror().addBox(-1.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 0.0F, -5.0F));
		PartDefinition Eyes = Head.addOrReplaceChild("Eyes", CubeListBuilder.create().texOffs(0, 24).addBox(-3.5F, -3.0F, -5.0F, 7.0F, 5.0F, 5.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition LegCluster1 = root.addOrReplaceChild("LegCluster1", CubeListBuilder.create(), PartPose.offset(-2.0F, 0.0F, -1.0F));
		PartDefinition LegReal1 = LegCluster1.addOrReplaceChild("LegReal1", CubeListBuilder.create().texOffs(0, 14).addBox(-15.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.975F, -0.5F, 0.0F, -0.6545F, -0.3491F));
		PartDefinition Leg1 = LegReal1.addOrReplaceChild("Leg1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition LegReal2 = LegCluster1.addOrReplaceChild("LegReal2", CubeListBuilder.create().texOffs(0, 14).addBox(-15.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -1.0F, -0.5F, 0.0F, -0.2182F, -0.3491F));
		PartDefinition Leg2 = LegReal2.addOrReplaceChild("Leg2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition LegReal3 = LegCluster1.addOrReplaceChild("LegReal3", CubeListBuilder.create().texOffs(0, 14).addBox(-15.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -1.0F, 0.5F, 0.0F, 0.2182F, -0.3491F));
		PartDefinition Leg3 = LegReal3.addOrReplaceChild("Leg3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition LegReal4 = LegCluster1.addOrReplaceChild("LegReal4", CubeListBuilder.create().texOffs(0, 14).addBox(-15.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -0.975F, 0.5F, 0.0F, 0.6545F, -0.3491F));
		PartDefinition Leg4 = LegReal4.addOrReplaceChild("Leg4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition LegCluster2 = root.addOrReplaceChild("LegCluster2", CubeListBuilder.create(), PartPose.offset(2.0F, 0.0F, -1.0F));
		PartDefinition LegReal5 = LegCluster2.addOrReplaceChild("LegReal5", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(0.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -0.975F, -0.5F, 0.0F, 0.6545F, 0.3491F));
		PartDefinition Leg5 = LegReal5.addOrReplaceChild("Leg5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition LegReal6 = LegCluster2.addOrReplaceChild("LegReal6", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(0.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -1.0F, -0.5F, 0.0F, 0.2182F, 0.3491F));
		PartDefinition Leg6 = LegReal6.addOrReplaceChild("Leg6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition LegReal7 = LegCluster2.addOrReplaceChild("LegReal7", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(0.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -1.0F, 0.5F, 0.0F, -0.2182F, 0.3491F));
		PartDefinition Leg7 = LegReal7.addOrReplaceChild("Leg7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition LegReal8 = LegCluster2.addOrReplaceChild("LegReal8", CubeListBuilder.create().texOffs(0, 14).mirror().addBox(0.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, -0.975F, 0.5F, 0.0F, -0.6545F, 0.3491F));
		PartDefinition Leg8 = LegReal8.addOrReplaceChild("Leg8", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(PaleSpiderEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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