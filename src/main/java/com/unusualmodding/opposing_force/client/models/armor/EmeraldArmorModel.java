package com.unusualmodding.opposing_force.client.models.armor;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class EmeraldArmorModel extends HumanoidModel {

	public EmeraldArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createArmorLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0.0F), 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.getChild("head");
		PartDefinition body = partdefinition.getChild("body");
		PartDefinition leftLeg = partdefinition.getChild("left_leg");
		PartDefinition rightLeg = partdefinition.getChild("right_leg");
		PartDefinition leftArm = partdefinition.getChild("left_arm");
		PartDefinition rightArm = partdefinition.getChild("right_arm");

		head.addOrReplaceChild("helmet", CubeListBuilder.create()
				.texOffs(84, 89).addBox(-4.0F, -8.0F, -4.5F, 8.0F, 5.0F, 9.0F, new CubeDeformation(0.51F))
				.texOffs(84, 73).addBox(-5.0F, -8.0F, -4.5F, 10.0F, 7.0F, 9.0F, new CubeDeformation(0.512F))
				.texOffs(93, 112).addBox(-1.0F, -9.0F, -5.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.51F)), PartPose.ZERO);


		body.addOrReplaceChild("chestplate", CubeListBuilder.create()
				.texOffs(24, 30).addBox(-4.0F, 0.5F, -2.5F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.6F)).mirror(false)
				.texOffs(34, 16).addBox(-4.0F, 4.0F, -2.5F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.51F)).mirror(false), PartPose.ZERO);


		rightArm.addOrReplaceChild("right_sleeve", CubeListBuilder.create()
				.texOffs(0, 54).addBox(-3.475F, -2.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(42, 43).addBox(-4.475F, 2.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.601F)).mirror(false), PartPose.ZERO);


		leftArm.addOrReplaceChild("left_sleeve", CubeListBuilder.create()
				.texOffs(16, 55).addBox(-0.525F, -2.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(0, 48).addBox(-0.525F, 2.0F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.601F)).mirror(false), PartPose.ZERO);


		leftLeg.addOrReplaceChild("left_pants", CubeListBuilder.create()
				.texOffs(24, 43).addBox(-1.5F, 0.45F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(50, 29).addBox(-2.0F, 7.525F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.ZERO);


		rightLeg.addOrReplaceChild("right_pants", CubeListBuilder.create()
				.texOffs(38, 0).addBox(-2.5F, 0.45F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.502F)).mirror(false)
				.texOffs(42, 49).addBox(-2.0F, 7.525F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.501F)).mirror(false), PartPose.ZERO);


		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}