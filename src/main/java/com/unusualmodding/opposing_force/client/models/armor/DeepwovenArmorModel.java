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
public class DeepwovenArmorModel extends HumanoidModel<LivingEntity> {

	public DeepwovenArmorModel(ModelPart root) {
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

		head.addOrReplaceChild("hat", CubeListBuilder.create()
				.texOffs(42, 115).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.01F))
				.texOffs(54, 103).addBox(-3.0F, -3.25F, -4.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.1F))
				.texOffs(42, 103).addBox(-2.0F, -16.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F))
				.texOffs(72, 101).addBox(-7.0F, -9.0F, -7.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(42, 114).addBox(-7.0F, -10.0F, -7.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(42, 114).addBox(-7.0F, -10.0F, 7.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(42, 100).addBox(7.0F, -10.0F, -7.0F, 0.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(42, 100).addBox(-7.0F, -10.0F, -7.0F, 0.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		body.addOrReplaceChild("tunic", CubeListBuilder.create()
				.texOffs(58, 74).addBox(-5.0F, -0.3F, -2.5F, 10.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(74, 85).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.ZERO);

		rightArm.addOrReplaceChild("right_sleeve", CubeListBuilder.create()
				.texOffs(58, 85).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.4F)).mirror(false)
				.texOffs(58, 93).addBox(-3.0F, 4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.4F))
				.texOffs(42, 80).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.32F)), PartPose.ZERO);

		leftArm.addOrReplaceChild("left_sleeve", CubeListBuilder.create()
				.texOffs(58, 85).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.4F))
				.texOffs(58, 93).addBox(-1.0F, 4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.4F))
				.texOffs(42, 80).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.32F)).mirror(false), PartPose.ZERO);

		leftLeg.addOrReplaceChild("left_pants", CubeListBuilder.create()
				.texOffs(42, 67).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(42, 96).mirror().addBox(-1.55F, 0.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.ZERO);

		rightLeg.addOrReplaceChild("right_pants", CubeListBuilder.create()
				.texOffs(42, 96).addBox(-2.45F, 0.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(42, 67).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.ZERO);

		rightLeg.addOrReplaceChild("right_boot", CubeListBuilder.create()
				.texOffs(58, 67).mirror().addBox(-2.1F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.ZERO);

		rightLeg.addOrReplaceChild("left_boot", CubeListBuilder.create()
				.texOffs(58, 67).addBox(-1.85F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}