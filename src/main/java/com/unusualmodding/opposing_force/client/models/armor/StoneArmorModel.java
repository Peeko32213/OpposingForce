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
public class StoneArmorModel extends HumanoidModel<LivingEntity> {

	public StoneArmorModel(ModelPart root) {
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
		root.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

		head.addOrReplaceChild("helmet", CubeListBuilder.create()
				.texOffs(76, 113).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.505F))
				.texOffs(32, 116).addBox(-6.0F, -9.0F, -5.5F, 12.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(108, 123).addBox(-1.0F, -12.0F, 2.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(104, 55).addBox(-2.0F, -14.0F, -2.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(116, 114).addBox(-2.0F, -12.0F, 4.0F, 4.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		body.addOrReplaceChild("chestplate", CubeListBuilder.create()
				.texOffs(25, 90).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 12.0F, 8.0F, new CubeDeformation(0.5F))
				.texOffs(48, 101).addBox(-4.0F, 0.0F, -4.5F, 8.0F, 6.0F, 9.0F, new CubeDeformation(0.6F)), PartPose.ZERO);

		rightLeg.addOrReplaceChild("right_legging", CubeListBuilder.create()
				.texOffs(108, 82).addBox(-3.375F, -2.0F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.5045F)), PartPose.ZERO);

		rightLeg.addOrReplaceChild("right_boot", CubeListBuilder.create()
				.texOffs(82, 106).addBox(-3.375F, 10.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.5045F)), PartPose.ZERO);

		leftLeg.addOrReplaceChild("left_legging", CubeListBuilder.create()
				.texOffs(22, 111).addBox(-1.625F, -2.0F, -2.5F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.504F)), PartPose.ZERO);

		leftLeg.addOrReplaceChild("left_boot", CubeListBuilder.create()
				.texOffs(108, 107).addBox(-1.625F, 10.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.504F)), PartPose.ZERO);

		leftArm.addOrReplaceChild("left_armplate", CubeListBuilder.create()
				.texOffs(108, 97).mirror().addBox(-1.0F, 6.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.51F)).mirror(false)
				.texOffs(85, 76).mirror().addBox(-1.6F, 2.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(80, 98).mirror().addBox(-1.6F, 3.0F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(112, 65).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(57, 89).mirror().addBox(0.4F, -6.0F, -3.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(84, 86).mirror().addBox(-1.6F, -5.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.ZERO);

		rightArm.addOrReplaceChild("right_armplate", CubeListBuilder.create().texOffs(108, 97).addBox(-4.0F, 6.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.51F))
				.texOffs(85, 76).addBox(-4.4F, 2.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(80, 98).addBox(-5.4F, 3.0F, -3.5F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(112, 65).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(57, 89).addBox(-5.4F, -6.0F, -3.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(84, 86).addBox(-4.4F, -5.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}
