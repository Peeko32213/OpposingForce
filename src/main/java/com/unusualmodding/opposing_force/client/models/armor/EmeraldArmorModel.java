package com.unusualmodding.opposing_force.client.models.armor;

import com.unusualmodding.opposing_force.client.models.armor.base.OPArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class EmeraldArmorModel extends OPArmorModel {

	public EmeraldArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createArmorLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0), 0);
		PartDefinition root = createHumanoidModel(meshdefinition);

		PartDefinition head = root.getChild("head");
		PartDefinition body = root.getChild("body");
		PartDefinition leggings = root.getChild("leggings");
		PartDefinition right_legging = root.getChild("right_legging");
		PartDefinition right_foot = root.getChild("right_foot");
		PartDefinition right_arm = root.getChild("right_arm");
		PartDefinition left_legging = root.getChild("left_legging");
		PartDefinition left_foot = root.getChild("left_foot");
		PartDefinition left_arm = root.getChild("left_arm");

		head.addOrReplaceChild("mask", CubeListBuilder.create()
				.texOffs(0, 16).addBox(-4.0F, -8.0F, -4.5F, 8.0F, 5.0F, 9.0F, new CubeDeformation(0.51F))
				.texOffs(0, 0).addBox(-5.0F, -8.0F, -4.5F, 10.0F, 7.0F, 9.0F, new CubeDeformation(0.512F))
				.texOffs(9, 39).addBox(-1.0F, -9.0F, -5.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.51F)), PartPose.ZERO);

		body.addOrReplaceChild("chestplate", CubeListBuilder.create()
				.texOffs(24, 30).addBox(-4.0F, 0.5F, -2.5F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.6F))
				.texOffs(34, 16).addBox(-4.0F, 4.0F, -2.5F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.51F)), PartPose.ZERO);

		right_arm.addOrReplaceChild("right_shoulder_pad", CubeListBuilder.create()
				.texOffs(0, 54).addBox(-3.475F, -2.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(42, 43).addBox(-4.475F, 2.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.601F)), PartPose.ZERO);
		left_arm.addOrReplaceChild("left_shoulder_pad", CubeListBuilder.create()
				.texOffs(16, 55).addBox(-0.525F, -2.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(0, 48).addBox(-0.525F, 2.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.601F)), PartPose.ZERO);

		right_legging.addOrReplaceChild("right_leggings", CubeListBuilder.create()
				.texOffs(38, 0).addBox(-2.5F, 0.45F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.502F)), PartPose.ZERO);
		left_legging.addOrReplaceChild("left_leggings", CubeListBuilder.create()
				.texOffs(24, 43).addBox(-1.5F, 0.45F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

		left_foot.addOrReplaceChild("left_boot", CubeListBuilder.create()
				.texOffs(50, 29).addBox(-2.0F, 7.525F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
		right_foot.addOrReplaceChild("right_boot", CubeListBuilder.create()
				.texOffs(42, 49).addBox(-2.0F, 7.525F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.501F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}