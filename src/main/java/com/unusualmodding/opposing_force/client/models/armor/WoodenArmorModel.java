package com.unusualmodding.opposing_force.client.models.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class WoodenArmorModel extends HumanoidModel<LivingEntity> {

	public WoodenArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createArmorLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0), 0);
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition head = root.getChild("head");
		PartDefinition body = root.getChild("body");
		PartDefinition leftLeg = root.getChild("left_leg");
		PartDefinition rightLeg = root.getChild("right_leg");
		PartDefinition leftArm = root.getChild("left_arm");
		PartDefinition rightArm = root.getChild("right_arm");

		head.addOrReplaceChild("mask", CubeListBuilder.create()
				.texOffs(62, 83).addBox(-4.0F, -6.0F, -4.5F, 8.0F, 5.0F, 9.0F, new CubeDeformation(0.51F))
				.texOffs(62, 70).addBox(-5.0F, -9.0F, -5.5F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(93, 71).addBox(-1.0F, -4.0F, -10.625F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		body.addOrReplaceChild("chestplate", CubeListBuilder.create()
				.texOffs(63, 119).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.53F))
				.texOffs(62, 97).addBox(-4.0F, 4.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

		rightArm.addOrReplaceChild("right_arm", CubeListBuilder.create()
				.texOffs(96, 83).addBox(-5.0F, -2.5F, -2.5F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.501F))
				.texOffs(62, 109).addBox(-3.0F, 7.5F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

		leftArm.addOrReplaceChild("left_arm", CubeListBuilder.create()
				.texOffs(96, 118).addBox(-1.0F, -2.5F, -2.5F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.501F))
				.texOffs(62, 109).mirror().addBox(-1.0F, 7.5F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.ZERO);

		leftLeg.addOrReplaceChild("left_pants", CubeListBuilder.create()
				.texOffs(112, 99).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.501F)).mirror(false), PartPose.ZERO);

		rightLeg.addOrReplaceChild("right_pants", CubeListBuilder.create()
				.texOffs(112, 99).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.502F)), PartPose.ZERO);

		rightLeg.addOrReplaceChild("right_boot", CubeListBuilder.create()
				.texOffs(80, 112).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.501F))
				.texOffs(96, 90).addBox(-2.975F, 9.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.501F)), PartPose.ZERO);

		leftLeg.addOrReplaceChild("left_boot", CubeListBuilder.create()
				.texOffs(86, 105).addBox(-2.025F, 9.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.502F))
				.texOffs(96, 112).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.502F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}