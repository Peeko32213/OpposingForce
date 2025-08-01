package com.unusualmodding.opposing_force.client.models.armor;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class StoneArmorModel extends HumanoidModel {
	public StoneArmorModel(ModelPart root) {
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
					.texOffs(51, 85).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.505F))
					.texOffs(0, 109).addBox(-6.0F, -9.0F, -5.5F, 12.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
					.texOffs(90, 118).addBox(-1.0F, -12.0F, 2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
					.texOffs(94, 81).addBox(-2.0F, -14.0F, -2.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
					.texOffs(110, 109).addBox(-2.0F, -12.0F, 4.0F, 4.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		body.addOrReplaceChild("chestplate", CubeListBuilder.create()
					.texOffs(0, 12).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.5F))
					.texOffs(66, 103).addBox(-4.0F, 0.0F, -4.5F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.6F)), PartPose.ZERO);

		rightArm.addOrReplaceChild("right_sleeve", CubeListBuilder.create()
					.texOffs(68, 55).addBox(-4.0F, 6.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.51F))
					.texOffs(62, 46).addBox(-4.4F, 2.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
					.texOffs(34, 47).addBox(-5.4F, 3.0F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
					.texOffs(68, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
					.texOffs(44, 0).addBox(-5.4F, -6.0F, -3.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
					.texOffs(24, 55).addBox(-4.4F, -5.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		leftArm.addOrReplaceChild("left_sleeve", CubeListBuilder.create()
					.texOffs(68, 55).mirror().addBox(-1.0F, 6.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.51F)).mirror(false)
					.texOffs(62, 46).mirror().addBox(-1.6F, 2.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
					.texOffs(34, 47).mirror().addBox(-1.6F, 3.0F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
					.texOffs(68, 0).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
					.texOffs(44, 0).mirror().addBox(0.4F, -6.0F, -3.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
					.texOffs(24, 55).mirror().addBox(-1.6F, -5.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.ZERO);

		leftLeg.addOrReplaceChild("left_pants", CubeListBuilder.create()
					.texOffs(48, 55).addBox(-1.625F, -2.0F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.504F))
					.texOffs(68, 65).addBox(-1.625F, 10.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.504F)), PartPose.ZERO);


		rightLeg.addOrReplaceChild("right_pants", CubeListBuilder.create()
					.texOffs(0, 59).addBox(-3.375F, -2.0F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.5045F))
					.texOffs(36, 70).addBox(-3.375F, 10.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.5045F)), PartPose.ZERO);

			return LayerDefinition.create(meshdefinition, 128, 128);
		}
	}
