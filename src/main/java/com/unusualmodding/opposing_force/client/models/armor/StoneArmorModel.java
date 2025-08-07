package com.unusualmodding.opposing_force.client.models.armor;

import com.unusualmodding.opposing_force.client.models.armor.base.OPArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class StoneArmorModel extends OPArmorModel {

	public StoneArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createArmorLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0.0F), 0.0F);
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

		head.addOrReplaceChild("helmet", CubeListBuilder.create()
				.texOffs(51, 85).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.505F))
				.texOffs(0, 109).addBox(-6.0F, -9.0F, -5.5F, 12.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(90, 118).addBox(-1.0F, -12.0F, 2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(94, 81).addBox(-2.0F, -14.0F, -2.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(110, 109).addBox(-2.0F, -12.0F, 4.0F, 4.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		body.addOrReplaceChild("chestplate", CubeListBuilder.create().texOffs(0, 12).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.5F))
				.texOffs(66, 103).addBox(-4.0F, 0.0F, -4.5F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.6F)), PartPose.ZERO);

		right_legging.addOrReplaceChild("right_legging", CubeListBuilder.create()
				.texOffs(0, 59).addBox(-3.375F, -2.0F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.5045F)), PartPose.ZERO);
		left_legging.addOrReplaceChild("left_legging", CubeListBuilder.create()
				.texOffs(48, 55).addBox(-1.625F, -2.0F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.504F)), PartPose.ZERO);

		right_foot.addOrReplaceChild("right_boot", CubeListBuilder.create()
				.texOffs(36, 70).addBox(-3.375F, 10.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.5045F)), PartPose.ZERO);
		left_foot.addOrReplaceChild("left_boot", CubeListBuilder.create()
				.texOffs(68, 65).addBox(-1.625F, 10.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.504F)), PartPose.ZERO);

		left_arm.addOrReplaceChild("left_armplate", CubeListBuilder.create()
				.texOffs(68, 55).mirror().addBox(-1.0F, 6.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.51F)).mirror(false)
				.texOffs(62, 46).mirror().addBox(-1.6F, 2.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(34, 47).mirror().addBox(-1.6F, 3.0F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(68, 0).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(44, 0).mirror().addBox(0.4F, -6.0F, -3.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(24, 55).mirror().addBox(-1.6F, -5.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.ZERO);
		right_arm.addOrReplaceChild("right_armplate", CubeListBuilder.create()
				.texOffs(68, 55).addBox(-4.0F, 6.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.51F))
				.texOffs(62, 46).addBox(-4.4F, 2.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(34, 47).addBox(-5.4F, 3.0F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(68, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(44, 0).addBox(-5.4F, -6.0F, -3.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(24, 55).addBox(-4.4F, -5.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}
