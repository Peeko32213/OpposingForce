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
public class LeapingLeggingsModel extends HumanoidModel<LivingEntity> {

	public LeapingLeggingsModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createArmorLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(new CubeDeformation(0), 0);
		PartDefinition root = meshdefinition.getRoot();
		PartDefinition body = root.getChild("body");
		PartDefinition leftLeg = root.getChild("left_leg");
		PartDefinition rightLeg = root.getChild("right_leg");

        body.addOrReplaceChild("belt", CubeListBuilder.create().texOffs(96, 107).addBox(-4.0F, 7.0F, -2.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.65F)), PartPose.ZERO);

        rightLeg.addOrReplaceChild("right_legging", CubeListBuilder.create().texOffs(112, 115).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.51F)), PartPose.ZERO);

        leftLeg.addOrReplaceChild("left_legging", CubeListBuilder.create().texOffs(96, 115).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.52F)), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 128, 128);
	}
}