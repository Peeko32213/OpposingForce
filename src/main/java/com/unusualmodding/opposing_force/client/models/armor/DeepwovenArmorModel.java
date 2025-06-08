package com.unusualmodding.opposing_force.client.models.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DeepwovenArmorModel extends HumanoidModel {

	public DeepwovenArmorModel(ModelPart root) {
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
				.texOffs(0, 16).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.01F))
				.texOffs(1, 9).addBox(-3.0F, -3.25F, -4.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.1F))
				.texOffs(40, 16).addBox(-2.0F, -16.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F))
				.texOffs(0, 0).addBox(-7.0F, -9.0F, -7.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(4, 54).addBox(-7.0F, -10.0F, -7.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(4, 54).addBox(-7.0F, -10.0F, 7.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(4, 40).addBox(7.0F, -10.0F, -7.0F, 0.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(4, 40).addBox(-7.0F, -10.0F, -7.0F, 0.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		body.addOrReplaceChild("chestplate", CubeListBuilder.create().texOffs(32, 29).addBox(-5.0F, -0.3F, -2.5F, 10.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(32, 40).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		rightArm.addOrReplaceChild("right_sleeve", CubeListBuilder.create().texOffs(16, 36).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(16, 44).addBox(-3.0F, 4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(0, 29).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		leftArm.addOrReplaceChild("left_sleeve", CubeListBuilder.create().texOffs(16, 36).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(16, 44).addBox(-1.0F, 4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(0, 29).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		leftLeg.addOrReplaceChild("left_pants", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(16, 29).mirror().addBox(-1.55F, 0.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(42, 7).addBox(-1.85F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		rightLeg.addOrReplaceChild("right_pants", CubeListBuilder.create().texOffs(16, 29).addBox(-2.45F, 0.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.01F))
				.texOffs(42, 7).mirror().addBox(-2.1F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.24F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
}