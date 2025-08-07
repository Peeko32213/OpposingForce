package com.unusualmodding.opposing_force.client.models.armor;

import com.google.common.collect.ImmutableList;
import com.unusualmodding.opposing_force.client.models.armor.base.OPArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class CloudBootsModel extends OPArmorModel {

	public ModelPart right_wing;
	public ModelPart left_wing;

	public CloudBootsModel(ModelPart root) {
        super(root);
        this.right_wing = root.getChild("right_wing");
		this.left_wing = root.getChild("left_wing");
	}

	@Override
	public void copyFromDefault(HumanoidModel model) {
		super.copyFromDefault(model);
		this.right_wing.copyFrom(rightFoot);
		this.left_wing.copyFrom(leftFoot);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		if (slot == EquipmentSlot.CHEST) {
			return ImmutableList.of(body, leftArm, rightArm);
		} else if (slot == EquipmentSlot.LEGS) {
			return ImmutableList.of(leftLegging, rightLegging, leggings);
		} else if (slot == EquipmentSlot.FEET) {
			return ImmutableList.of(leftFoot, rightFoot, right_wing, left_wing);
		} else return ImmutableList.of();
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

		right_foot.addOrReplaceChild("right_boot", CubeListBuilder.create()
				.texOffs(0, 16).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.501F))
				.texOffs(0, 0).addBox(-3.5F, 8.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.ZERO);
		left_foot.addOrReplaceChild("left_boot", CubeListBuilder.create()
				.texOffs(0, 8).addBox(-2.5F, 8.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.01F))
				.texOffs(16, 16).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.502F)), PartPose.ZERO);

		PartDefinition right_wing = root.addOrReplaceChild("right_wing", new CubeListBuilder(), PartPose.ZERO);
		right_wing.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 22).addBox(0.0F, -5.0F, 0.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.525F, 11F, 2.25F));

		PartDefinition left_wing = root.addOrReplaceChild("left_wing", new CubeListBuilder(), PartPose.ZERO);
		left_wing.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(0.0F, -5.0F, 0.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.525F, 11F, 2.25F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		float partialTicks = Minecraft.getInstance().getDeltaFrameTime();
		float f = livingEntity.tickCount + partialTicks;

		float fly = Mth.cos(f * 0.2F) * 0.1F;
		float fly2 = fly * 0.35F;

//		right_wing.yRot = 0.5672F + fly2;
//		left_wing.yRot = -0.5672F - fly2;
//
//		if (!livingEntity.onGround()) {
//			fly = (1 + Mth.sin(ageInTicks * 1.2F)) * 0.8F;
//			fly2 = fly;
//		}

//		this.leaf.xRot = (float) -(limbSwingAmount * Math.toRadians(80) + Mth.cos(limbSwing * 0.3F) * 0.2F * limbSwingAmount);

		super.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}
}