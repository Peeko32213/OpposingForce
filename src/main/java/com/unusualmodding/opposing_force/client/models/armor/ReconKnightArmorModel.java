package com.unusualmodding.opposing_force.client.models.armor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class ReconKnightArmorModel extends HumanoidModel<LivingEntity> {

    private final ModelPart cape;

    public ReconKnightArmorModel(ModelPart root) {
		super(root);
        this.cape = root.getChild("body").getChild("cape");
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

        head.addOrReplaceChild("helmet", CubeListBuilder.create()
                .texOffs(40, 90).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.36F))
                .texOffs(40, 78).addBox(-5.0F, -8.025F, -4.975F, 10.0F, 3.0F, 9.0F, new CubeDeformation(0.36F))
                .texOffs(72, 90).addBox(-4.5F, -2.025F, -4.975F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.36F))
                .texOffs(98, 101).addBox(3.6F, -7.0F, -1.5F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.36F))
                .texOffs(98, 101).mirror().addBox(-4.6F, -7.0F, -1.5F, 1.0F, 5.0F, 8.0F, new CubeDeformation(0.36F)).mirror(false), PartPose.ZERO);

        body.addOrReplaceChild("chestplate", CubeListBuilder.create()
                .texOffs(72, 101).addBox(-4.0F, 0.0F, -2.5F, 8.0F, 6.0F, 5.0F, new CubeDeformation(0.39F))
                .texOffs(40, 106).addBox(-4.0F, 9.0F, -2.5F, 8.0F, 3.0F, 5.0F, new CubeDeformation(0.39F))
                .texOffs(78, 68).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.36F)), PartPose.ZERO);

        body.addOrReplaceChild("cape", CubeListBuilder.create()
                .texOffs(40, 56).addBox(-7.0F, 0.0F, 0.0F, 14.0F, 21.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

        rightArm.addOrReplaceChild("right_pauldron", CubeListBuilder.create()
                .texOffs(98, 114).addBox(-4.05F, -2.0F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.36F))
                .texOffs(78, 84).addBox(-4.05F, -3.6F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.36F))
                .texOffs(102, 77).addBox(-4.05F, 5.0F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.36F))
                .texOffs(106, 58).addBox(-3.3F, -1.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.362F)), PartPose.ZERO);

        leftArm.addOrReplaceChild("left_pauldron", CubeListBuilder.create()
                .texOffs(106, 58).mirror().addBox(-0.7F, -1.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.362F)).mirror(false)
                .texOffs(102, 77).mirror().addBox(-0.95F, 5.0F, -2.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.36F)).mirror(false)
                .texOffs(78, 84).mirror().addBox(-0.95F, -3.6F, -2.5F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.36F)).mirror(false)
                .texOffs(98, 114).mirror().addBox(-0.95F, -2.0F, -2.5F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.36F)).mirror(false), PartPose.ZERO);

        leftLeg.addOrReplaceChild("left_legging", CubeListBuilder.create()
                .texOffs(112, 87).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.36F)).mirror(false), PartPose.ZERO);

        leftLeg.addOrReplaceChild("left_boot", CubeListBuilder.create()
                .texOffs(61, 117).mirror().addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.ZERO);

        rightLeg.addOrReplaceChild("right_legging", CubeListBuilder.create()
                .texOffs(82, 112).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.361F)), PartPose.ZERO);

        rightLeg.addOrReplaceChild("right_boot", CubeListBuilder.create()
                .texOffs(61, 117).addBox(-2.0F, 10.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.4001F)), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

    public ReconKnightArmorModel withAnimations(LivingEntity entity) {
        float partialTicks = Minecraft.getInstance().getFrameTime();
        if (entity instanceof AbstractClientPlayer player) {
            double capeX = Mth.lerp(partialTicks, player.xCloakO, player.xCloak) - Mth.lerp(partialTicks, player.xo, player.getX());
            double capeY = Mth.lerp(partialTicks, player.yCloakO, player.yCloak) - Mth.lerp(partialTicks, player.yo, player.getY());
            double capeZ = Mth.lerp(partialTicks, player.zCloakO, player.zCloak) - Mth.lerp(partialTicks, player.zo, player.getZ());

            float f = Mth.rotLerp(partialTicks, player.yBodyRotO, player.yBodyRot);
            double d3 = Mth.sin(f * ((float) Math.PI / 180F));
            double d4 = -Mth.cos(f * ((float) Math.PI / 180F));

            float f1 = (float) capeY * 10.0F;
            f1 = Mth.clamp(f1, -6.0F, 32.0F);

            float f2 = (float) (capeX * d3 + capeZ * d4) * 100.0F;
            f2 = Mth.clamp(f2, 0.0F, 150.0F);

            if (f2 < 0.0F) {
                f2 = 0.0F;
            }

            float f4 = Mth.lerp(partialTicks, player.oBob, player.bob);
            f1 += Mth.sin(Mth.lerp(partialTicks, player.walkDistO, player.walkDist) * 6.0F) * 32.0F * f4;
            if (player.isCrouching()) {
                f1 += 25.0F;
            }

            this.cape.xRot = (float) Math.toRadians(6.0F + f2 / 2.0F + f1);
        } else {
            this.cape.xRot = 0;
        }
        return this;
    }
}