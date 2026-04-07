package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.DicerAnimations;
import com.unusualmodding.opposing_force.client.models.entity.base.OPModel;
import com.unusualmodding.opposing_force.entity.Dicer;
import com.unusualmodding.opposing_force.entity.utils.OPPoses;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class DicerModel extends OPModel<Dicer> {

    private final ModelPart root;
    private final ModelPart spin_control;
    private final ModelPart body_main;
    private final ModelPart hips;
    private final ModelPart waist;
    private final ModelPart head;
    private final ModelPart visor;
    private final ModelPart chest;
    private final ModelPart left_arm_joint;
    private final ModelPart left_arm;
    private final ModelPart left_finger1;
    private final ModelPart left_finger2;
    private final ModelPart right_arm_joint;
    private final ModelPart right_arm;
    private final ModelPart right_finger1;
    private final ModelPart right_finger2;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;
    private final ModelPart tail4;
    private final ModelPart leg_control;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

	public DicerModel(ModelPart root) {
        this.root = root.getChild("root");
        this.spin_control = this.root.getChild("spin_control");
        this.body_main = this.spin_control.getChild("body_main");
        this.hips = this.body_main.getChild("hips");
        this.waist = this.hips.getChild("waist");
        this.head = this.waist.getChild("head");
        this.visor = this.head.getChild("visor");
        this.chest = this.waist.getChild("chest");
        this.left_arm_joint = this.chest.getChild("left_arm_joint");
        this.left_arm = this.left_arm_joint.getChild("left_arm");
        this.left_finger1 = this.left_arm.getChild("left_finger1");
        this.left_finger2 = this.left_arm.getChild("left_finger2");
        this.right_arm_joint = this.chest.getChild("right_arm_joint");
        this.right_arm = this.right_arm_joint.getChild("right_arm");
        this.right_finger1 = this.right_arm.getChild("right_finger1");
        this.right_finger2 = this.right_arm.getChild("right_finger2");
        this.tail1 = this.waist.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.tail3 = this.tail2.getChild("tail3");
        this.tail4 = this.tail3.getChild("tail4");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg = this.leg_control.getChild("left_leg");
        this.right_leg = this.leg_control.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition spin_control = root.addOrReplaceChild("spin_control", CubeListBuilder.create(), PartPose.offset(0.0F, -21.0F, 0.0F));

        PartDefinition body_main = spin_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hips = body_main.addOrReplaceChild("hips", CubeListBuilder.create().texOffs(60, 46).addBox(-4.0F, -2.0F, -2.5F, 8.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition waist = hips.addOrReplaceChild("waist", CubeListBuilder.create().texOffs(48, 62).addBox(-2.0F, -12.0F, -1.5F, 4.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(34, 62).addBox(0.0F, -10.0F, 1.5F, 0.0F, 10.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition sail_r1 = waist.addOrReplaceChild("sail_r1", CubeListBuilder.create().texOffs(20, 62).mirror().addBox(0.0F, -2.0F, 0.0F, 0.0F, 10.0F, 7.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -8.0F, 1.5F, 0.0F, -0.4363F, 0.0F));

        PartDefinition sail_r2 = waist.addOrReplaceChild("sail_r2", CubeListBuilder.create().texOffs(20, 62).addBox(0.0F, -2.0F, 0.0F, 0.0F, 10.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(2.0F, -8.0F, 1.5F, 0.0F, 0.4363F, 0.0F));

        PartDefinition head = waist.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 37).addBox(-5.0F, -9.0F, -3.5F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F))
                .texOffs(38, 21).addBox(-5.0F, -9.0F, -3.5F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 37).addBox(-2.0F, -13.0F, -4.5F, 2.0F, 10.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(63, 64).addBox(-2.0F, -2.0F, -7.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(72, 71).addBox(-2.0F, -5.0F, -7.5F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -11.0F, -0.5F));

        PartDefinition visor = head.addOrReplaceChild("visor", CubeListBuilder.create().texOffs(28, 53).addBox(-5.0F, -1.0F, -3.5F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -6.0F, -1.0F));

        PartDefinition chest = waist.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(60, 37).addBox(-4.0F, -4.0F, -2.5F, 8.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition left_arm_joint = chest.addOrReplaceChild("left_arm_joint", CubeListBuilder.create(), PartPose.offset(4.0F, -3.0F, 0.0F));

        PartDefinition left_arm = left_arm_joint.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(12, 59).addBox(0.0F, -1.0F, -1.0F, 2.0F, 24.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(62, 53).addBox(-1.0F, -1.0F, 0.0F, 9.0F, 8.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_finger1 = left_arm.addOrReplaceChild("left_finger1", CubeListBuilder.create().texOffs(56, 0).addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0025F)), PartPose.offset(2.0F, 23.0F, 0.0F));

        PartDefinition left_finger2 = left_arm.addOrReplaceChild("left_finger2", CubeListBuilder.create().texOffs(56, 0).addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition right_arm_joint = chest.addOrReplaceChild("right_arm_joint", CubeListBuilder.create(), PartPose.offset(-4.0F, -3.0F, 0.0F));

        PartDefinition right_arm = right_arm_joint.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(12, 59).mirror().addBox(-2.0F, -1.0F, -1.0F, 2.0F, 24.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(62, 53).mirror().addBox(-8.0F, -1.0F, 0.0F, 9.0F, 8.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_finger1 = right_arm.addOrReplaceChild("right_finger1", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-2.0F, 23.0F, 0.0F));

        PartDefinition right_finger2 = right_arm.addOrReplaceChild("right_finger2", CubeListBuilder.create().texOffs(56, 0).mirror().addBox(0.0F, 0.0F, -2.0F, 0.0F, 8.0F, 10.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition tail1 = waist.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(9, 30).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 7.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 2.5F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(7, 21).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 7.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(12, 12).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 9.0F));

        PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(9, 0).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 0.0F, 12.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 9.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg = leg_control.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 59).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 21.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(70, 18).addBox(1.5F, 0.0F, -1.5F, 3.0F, 10.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(1.5F, 0.0F, 0.0F));

        PartDefinition right_leg = leg_control.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 59).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 21.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(70, 18).mirror().addBox(-4.5F, 0.0F, -1.5F, 3.0F, 10.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-1.5F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Dicer entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.getPose() != OPPoses.CROSS_SLASHING.get() && entity.getPose() != OPPoses.LASERING.get()) {
			if (entity.isRunning()) this.animateWalk(DicerAnimations.RUN, limbSwing, limbSwingAmount, 1, 2);
			else this.animateWalk(DicerAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
		}

		this.animateIdleSmooth(entity.idleAnimationState, DicerAnimations.IDLE, ageInTicks, limbSwingAmount);
		this.animateSmooth(entity.slash1AnimationState, DicerAnimations.SLASH1, ageInTicks);
		this.animateSmooth(entity.slash2AnimationState, DicerAnimations.SLASH2, ageInTicks);
		this.animateSmooth(entity.crossSlashAnimationState, DicerAnimations.CROSSSLASH, ageInTicks);
        this.animateSmooth(entity.laserAnimationState, DicerAnimations.LASER, ageInTicks);

        this.head.xRot += headPitch * ((float) Math.PI / 180) - (headPitch * ((float) Math.PI / 180)) / 2;
        this.head.yRot += netHeadYaw * ((float) Math.PI / 180) - (netHeadYaw * ((float) Math.PI / 180)) / 2;

        float partialTicks = ageInTicks - entity.tickCount;
        float tailYaw = entity.getTailYaw(partialTicks);
        this.tail1.yRot = Mth.lerp(0.1F, this.tail1.yRot, tailYaw * 0.23F);
        this.tail2.yRot = Mth.lerp(0.2F, this.tail2.yRot, tailYaw * 0.23F);
        this.tail3.yRot = Mth.lerp(0.3F, this.tail3.yRot, tailYaw * 0.23F);
        this.tail4.yRot = Mth.lerp(0.4F, this.tail4.yRot, tailYaw * 0.23F);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

    @Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}