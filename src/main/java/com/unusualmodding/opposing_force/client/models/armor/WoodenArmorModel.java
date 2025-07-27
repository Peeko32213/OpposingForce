package com.unusualmodding.opposing_force.client.models.armor;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class WoodenArmorModel extends HumanoidModel {


	public WoodenArmorModel(ModelPart root) {
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
				.texOffs(5, 34).addBox(-4.0F, -6.0F, -4.5F, 8.0F, 5.0F, 9.0F, new CubeDeformation(0.51F))
				.texOffs(5, 21).addBox(-5.0F, -9.0F, -5.5F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(45, 21).addBox(-1.0F, -4.0F, -10.625F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.ZERO);


		body.addOrReplaceChild("chestplate", CubeListBuilder.create()
		.texOffs(1, 49).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.535F))
				.texOffs(46, 35).addBox(0.0F, 0.0F, -2.05F, 4.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 27).addBox(-4.0F, 4.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.52F)),  PartPose.ZERO);


		rightArm.addOrReplaceChild("right_sleeve", CubeListBuilder.create()
		.texOffs(34, 13).addBox(-5.0F, -2.5F, -2.5F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.501F)).mirror(false)
		.texOffs(0, 39).addBox(-3.0F, 7.5F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.5F)).mirror(false),  PartPose.ZERO);


		leftArm.addOrReplaceChild("left_sleeve", CubeListBuilder.create()
		.texOffs(34, 48).addBox(-1.0F, -2.5F, -2.5F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.501F)).mirror(false)
		.texOffs(0, 39).mirror().addBox(-1.0F, 7.5F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.5F)).mirror(false),  PartPose.ZERO);


		leftLeg.addOrReplaceChild("left_pants", CubeListBuilder.create()
				.texOffs(0, 0).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.501F)).mirror(false)
				.texOffs(24, 35).addBox(-2.025F, 9.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.502F)).mirror(false)
				.texOffs(34, 42).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.502F)).mirror(false), PartPose.ZERO);


		rightLeg.addOrReplaceChild("right_pants", CubeListBuilder.create()
				.texOffs(0, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.502F)).mirror(false)
				.texOffs(18, 42).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.501F)).mirror(false)
				.texOffs(34, 20).addBox(-2.975F, 9.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.501F)).mirror(false), PartPose.ZERO);


		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}