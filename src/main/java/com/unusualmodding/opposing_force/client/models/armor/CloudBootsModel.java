package com.unusualmodding.opposing_force.client.models.armor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;

public class CloudBootsModel extends HumanoidModel {

	public ModelPart right_wing;
	public ModelPart left_wing;

	public CloudBootsModel(ModelPart root) {
        super(root);
        this.right_wing = root.getChild("right_leg").getChild("right_wing");
		this.left_wing = root.getChild("left_leg").getChild("left_wing");
	}

	public static LayerDefinition createArmorLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0.0F), 0.0F);
		PartDefinition root = meshdefinition.getRoot();

		PartDefinition rightBoot = root.getChild("right_leg");
		PartDefinition leftBoot = root.getChild("left_leg");

		rightBoot.addOrReplaceChild("right_boot", CubeListBuilder.create()
				.texOffs(18, 0).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(0, 0).addBox(-3.5F, 8.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.ZERO);
		leftBoot.addOrReplaceChild("left_boot", CubeListBuilder.create()
				.texOffs(0, 8).addBox(-2.5F, 8.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.01F))
				.texOffs(34, 0).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

		rightBoot.addOrReplaceChild("right_wing", CubeListBuilder.create()
				.texOffs(28, 3).addBox(0.0F, -5.0F, 0.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(0F, 11.0F, 0F));
		leftBoot.addOrReplaceChild("left_wing", CubeListBuilder.create()
				.texOffs(28, 3).mirror().addBox(0.0F, -5.0F, 0.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(0F, 11.0F, 0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public CloudBootsModel withAnimations(LivingEntity entity) {
		float partialTicks = Minecraft.getInstance().getFrameTime();
		float ageInTicks  = entity.tickCount + partialTicks;

		float fly = Mth.sin(ageInTicks * 0.2F) * 0.3F;

		this.right_wing.x = -2.5F;
		this.left_wing.x = 2.5F;
		this.right_wing.z = 2.25F;
		this.left_wing.z = 2.25F;

		if (entity instanceof ArmorStand) {
			this.right_wing.yRot = 0.0F;
			this.left_wing.yRot = 0.0F;
		} else {
			this.right_wing.yRot = -fly;
			this.left_wing.yRot = fly;
		}
		return this;
	}
}