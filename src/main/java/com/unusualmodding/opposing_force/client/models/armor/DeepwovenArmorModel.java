package com.unusualmodding.opposing_force.client.models.armor;

import com.unusualmodding.opposing_force.client.models.armor.base.OPArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DeepwovenArmorModel extends OPArmorModel {

	public DeepwovenArmorModel(ModelPart root) {
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

		head.addOrReplaceChild("hat", CubeListBuilder.create()
				.texOffs(0, 48).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.01F))
				.texOffs(12, 36).addBox(-3.0F, -3.25F, -4.0F, 6.0F, 4.0F, 0.0F, new CubeDeformation(0.1F))
				.texOffs(0, 36).addBox(-2.0F, -16.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.3F))
				.texOffs(30, 34).addBox(-7.0F, -9.0F, -7.0F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(0, 47).addBox(-7.0F, -10.0F, -7.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 47).addBox(-7.0F, -10.0F, 7.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 33).addBox(7.0F, -10.0F, -7.0F, 0.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(0, 33).addBox(-7.0F, -10.0F, -7.0F, 0.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		body.addOrReplaceChild("tunic", CubeListBuilder.create()
				.texOffs(16, 7).addBox(-5.0F, -0.3F, -2.5F, 10.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(32, 18).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.ZERO);

		right_arm.addOrReplaceChild("right_sleeve", CubeListBuilder.create()
				.texOffs(16, 18).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.4F)).mirror(false)
				.texOffs(16, 26).addBox(-3.0F, 4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.4F))
				.texOffs(0, 13).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.32F)), PartPose.ZERO);

		left_arm.addOrReplaceChild("left_sleeve", CubeListBuilder.create()
				.texOffs(16, 18).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.4F))
				.texOffs(16, 26).addBox(-1.0F, 4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.4F))
				.texOffs(0, 13).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.32F)).mirror(false), PartPose.ZERO);

		left_legging.addOrReplaceChild("left_pants", CubeListBuilder.create()
				.texOffs(0, 0).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(0, 29).mirror().addBox(-1.55F, 0.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.ZERO);

		right_legging.addOrReplaceChild("right_pants", CubeListBuilder.create()
				.texOffs(0, 29).addBox(-2.45F, 0.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.ZERO);

		right_foot.addOrReplaceChild("right_boot", CubeListBuilder.create()
				.texOffs(16, 0).mirror().addBox(-2.1F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.ZERO);

		left_foot.addOrReplaceChild("left_boot", CubeListBuilder.create()
				.texOffs(16, 0).addBox(-1.85F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}