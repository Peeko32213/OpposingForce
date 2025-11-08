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
public class SlugBaronArmorModel extends HumanoidModel<LivingEntity> {

	public SlugBaronArmorModel(ModelPart root) {
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

        head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(56, 69).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.36F))
                .texOffs(56, 57).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.36F))
                .texOffs(56, 85).addBox(-5.0F, -3.95F, -5.0F, 10.0F, 4.0F, 5.0F, new CubeDeformation(0.36F))
                .texOffs(55, 121).addBox(5.35F, -6.35F, 0.35F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(55, 121).mirror().addBox(-5.35F, -6.35F, 0.35F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(88, 81).addBox(-1.0F, -11.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.36F))
                .texOffs(56, 94).addBox(-2.0F, -14.25F, -4.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.36F))
                .texOffs(96, 64).addBox(0.975F, -16.875F, -3.95F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.36F))
                .texOffs(96, 64).mirror().addBox(-1.975F, -16.875F, -3.95F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.36F)).mirror(false), PartPose.ZERO);

        body.addOrReplaceChild("chestplate", CubeListBuilder.create().texOffs(88, 69).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.39F))
                .texOffs(86, 85).addBox(-4.0F, 4.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.36F))
                .texOffs(96, 57).addBox(-4.0F, 9.0F, -2.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.39F)), PartPose.ZERO);

        rightArm.addOrReplaceChild("right_armplate", CubeListBuilder.create().texOffs(92, 113).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.4F))
                .texOffs(56, 104).addBox(-3.975F, -3.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.4F))
                .texOffs(112, 93).addBox(-2.975F, 7.5F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.41F))
                .texOffs(110, 81).addBox(-2.975F, 2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.395F)), PartPose.ZERO);

        leftArm.addOrReplaceChild("left_armplate", CubeListBuilder.create().texOffs(92, 113).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.4F)).mirror(false)
                .texOffs(56, 104).mirror().addBox(-1.025F, -3.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.4F)).mirror(false)
                .texOffs(112, 93).mirror().addBox(-1.025F, 7.5F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.41F)).mirror(false)
                .texOffs(110, 81).mirror().addBox(-1.025F, 2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.395F)).mirror(false), PartPose.ZERO);

        leftLeg.addOrReplaceChild("left_legging", CubeListBuilder.create()
                .texOffs(76, 113).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.382F))
                .texOffs(96, 97).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.361F)), PartPose.ZERO);

        leftLeg.addOrReplaceChild("left_boot", CubeListBuilder.create()
                .texOffs(112, 107).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.39F)), PartPose.ZERO);

        rightLeg.addOrReplaceChild("right_legging", CubeListBuilder.create()
                .texOffs(112, 64).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.381F))
                .texOffs(80, 97).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.36F)), PartPose.ZERO);

        rightLeg.addOrReplaceChild("right_boot", CubeListBuilder.create()
                .texOffs(112, 100).addBox(-2.0F, 9.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.391F)), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 128, 128);
	}
}