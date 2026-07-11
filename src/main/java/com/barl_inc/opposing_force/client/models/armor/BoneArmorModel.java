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
public class BoneArmorModel extends HumanoidModel<LivingEntity> {

	public BoneArmorModel(ModelPart root) {
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

        head.addOrReplaceChild("skull", CubeListBuilder.create()
                .texOffs(58, 68).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.7F))
                .texOffs(58, 84).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.55F)), PartPose.ZERO);

        body.addOrReplaceChild("ribcage", CubeListBuilder.create()
                .texOffs(58, 100).addBox(-5.0F, -0.3F, -2.5F, 10.0F, 6.0F, 5.0F, new CubeDeformation(0.5F))
                .texOffs(90, 68).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

        rightArm.addOrReplaceChild("right_pauldron", CubeListBuilder.create()
                .texOffs(90, 84).addBox(-5.0F, -5.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(112, 116).addBox(-6.25F, -6.25F, 2.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(112, 116).addBox(-6.5F, -6.25F, -2.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(90, 96).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.51F)), PartPose.ZERO);

        leftArm.addOrReplaceChild("left_pauldron", CubeListBuilder.create()
                .texOffs(58, 111).addBox(-1.0F, -3.0F, -3.0F, 5.0F, 4.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(80, 112).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.51F)), PartPose.ZERO);

        rightLeg.addOrReplaceChild("right_legging", CubeListBuilder.create()
                .texOffs(106, 96).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

        rightLeg.addOrReplaceChild("right_boot", CubeListBuilder.create()
                .texOffs(58, 121).addBox(-2.1F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.6F)), PartPose.ZERO);

        leftLeg.addOrReplaceChild("left_legging", CubeListBuilder.create()
                .texOffs(96, 112).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.51F)), PartPose.ZERO);

        leftLeg.addOrReplaceChild("left_boot", CubeListBuilder.create()
                .texOffs(112, 109).addBox(-1.85F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.61F)), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 128, 128);
	}
}