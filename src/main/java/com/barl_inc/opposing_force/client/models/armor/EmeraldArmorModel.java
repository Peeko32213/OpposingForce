package com.barl_inc.opposing_force.client.models.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class EmeraldArmorModel extends HumanoidModel<LivingEntity> {

	public EmeraldArmorModel(ModelPart root) {
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
                .texOffs(62, 77).addBox(-4.0F, -8.0F, -4.5F, 8.0F, 5.0F, 9.0F, new CubeDeformation(0.6F))
                .texOffs(62, 61).addBox(-5.0F, -8.0F, -4.5F, 10.0F, 7.0F, 9.0F, new CubeDeformation(0.55F))
                .texOffs(71, 99).addBox(-1.0F, -8.5F, -6.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.ZERO);

        body.addOrReplaceChild("chestplate", CubeListBuilder.create()
                .texOffs(86, 92).addBox(-4.0F, 0.5F, -2.5F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.6F))
                .texOffs(96, 77).addBox(-4.0F, 4.0F, -2.5F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.52F)), PartPose.ZERO);

        rightArm.addOrReplaceChild("right_shoulder_pad", CubeListBuilder.create()
                .texOffs(62, 120).addBox(-3.475F, -2.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.5F))
                .texOffs(102, 107).addBox(-5.0F, 1.0F, -3.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        leftArm.addOrReplaceChild("left_shoulder_pad", CubeListBuilder.create()
                .texOffs(78, 121).addBox(-0.525F, -2.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.5F))
                .texOffs(58, 112).addBox(-2.0F, 1.0F, -3.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        rightLeg.addOrReplaceChild("right_leggings", CubeListBuilder.create()
                .texOffs(18, 104).addBox(-2.5F, 0.45F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.502F)), PartPose.ZERO);

        leftLeg.addOrReplaceChild("left_leggings", CubeListBuilder.create()
                .texOffs(40, 104).addBox(-1.5F, 0.45F, -2.5F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

        leftLeg.addOrReplaceChild("left_boot", CubeListBuilder.create()
                .texOffs(112, 91).addBox(-2.0F, 7.525F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

        rightLeg.addOrReplaceChild("right_boot", CubeListBuilder.create()
                .texOffs(104, 115).addBox(-2.0F, 7.525F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.501F)), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 128, 128);
	}
}