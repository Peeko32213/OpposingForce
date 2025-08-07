package com.unusualmodding.opposing_force.client.models.armor;

import com.google.common.collect.ImmutableList;
import com.unusualmodding.opposing_force.client.models.armor.base.OPArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class WoodenArmorModel extends OPArmorModel {

	public ModelPart leaf;

	public WoodenArmorModel(ModelPart root) {
		super(root);
		this.leaf = root.getChild("leaf");
	}

	@Override
	public void copyFromDefault(HumanoidModel model) {
		super.copyFromDefault(model);
		this.leaf.copyFrom(model.body);
	}

	@Override
	protected Iterable<ModelPart> bodyParts() {
		if (slot == EquipmentSlot.CHEST) {
			return ImmutableList.of(body, leftArm, rightArm, leaf);
		} else if (slot == EquipmentSlot.LEGS) {
			return ImmutableList.of(leftLegging, rightLegging, leggings);
		} else if (slot == EquipmentSlot.FEET) {
			return ImmutableList.of(leftFoot, rightFoot);
		} else return ImmutableList.of();
	}

	public static LayerDefinition createArmorLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0), 0);
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

		head.addOrReplaceChild("mask", CubeListBuilder.create()
				.texOffs(0, 13).addBox(-4.0F, -6.0F, -4.5F, 8.0F, 5.0F, 9.0F, new CubeDeformation(0.51F))
				.texOffs(0, 0).addBox(-5.0F, -9.0F, -5.5F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(31, 1).addBox(-1.0F, -4.0F, -10.625F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		body.addOrReplaceChild("chestplate", CubeListBuilder.create()
				.texOffs(1, 49).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.53F))
				.texOffs(0, 27).addBox(-4.0F, 4.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

		right_arm.addOrReplaceChild("right_arm", CubeListBuilder.create()
				.texOffs(34, 13).addBox(-5.0F, -2.5F, -2.5F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.501F))
				.texOffs(0, 39).addBox(-3.0F, 7.5F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
		left_arm.addOrReplaceChild("left_arm", CubeListBuilder.create()
				.texOffs(34, 48).addBox(-1.0F, -2.5F, -2.5F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.501F))
				.texOffs(0, 39).mirror().addBox(-1.0F, 7.5F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.ZERO);

		left_legging.addOrReplaceChild("left_pants", CubeListBuilder.create()
				.texOffs(50, 29).mirror().addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.501F)).mirror(false), PartPose.ZERO);
		right_legging.addOrReplaceChild("right_pants", CubeListBuilder.create()
				.texOffs(50, 29).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.502F)), PartPose.ZERO);

		right_foot.addOrReplaceChild("right_boot", CubeListBuilder.create()
				.texOffs(18, 42).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.501F))
				.texOffs(34, 20).addBox(-2.975F, 9.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.501F)), PartPose.ZERO);

		left_foot.addOrReplaceChild("left_boot", CubeListBuilder.create()
				.texOffs(24, 35).addBox(-2.025F, 9.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.502F))
				.texOffs(34, 42).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.502F)), PartPose.ZERO);

		PartDefinition leaf = root.addOrReplaceChild("leaf", new CubeListBuilder(), PartPose.ZERO);
		leaf.addOrReplaceChild("leaf_cube", CubeListBuilder.create()
				.texOffs(41, 28).addBox(-2.0F, -12.5F, -2.7F, 4.0F, 7.0F, 0.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		float partialTicks = Minecraft.getInstance().getDeltaFrameTime();
		float f = livingEntity.tickCount + partialTicks;

//		this.leaf.xRot = (float) -(limbSwingAmount * Math.toRadians(80) + Mth.cos(limbSwing * 0.3F) * 0.2F * limbSwingAmount);

		super.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}
}