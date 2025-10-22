package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.TerrorAnimations;
import com.unusualmodding.opposing_force.entity.Terror;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class TerrorModel extends HierarchicalModel<Terror> {

    private final ModelPart root;
    private final ModelPart swim_control;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart saw;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart dorsal1;
    private final ModelPart tail1;
    private final ModelPart dorsal2;
    private final ModelPart anal;
    private final ModelPart tail2;
    private final ModelPart leg_control;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart left_leg3;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;
    private final ModelPart right_leg3;

	public TerrorModel(ModelPart root) {
        this.root = root.getChild("root");
        this.swim_control = this.root.getChild("swim_control");
        this.body_main = this.swim_control.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.head = this.body.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.saw = this.jaw.getChild("saw");
        this.left_arm = this.body.getChild("left_arm");
        this.right_arm = this.body.getChild("right_arm");
        this.dorsal1 = this.body.getChild("dorsal1");
        this.tail1 = this.body.getChild("tail1");
        this.dorsal2 = this.tail1.getChild("dorsal2");
        this.anal = this.tail1.getChild("anal");
        this.tail2 = this.tail1.getChild("tail2");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg1 = this.leg_control.getChild("left_leg1");
        this.left_leg2 = this.left_leg1.getChild("left_leg2");
        this.left_leg3 = this.left_leg2.getChild("left_leg3");
        this.right_leg1 = this.leg_control.getChild("right_leg1");
        this.right_leg2 = this.right_leg1.getChild("right_leg2");
        this.right_leg3 = this.right_leg2.getChild("right_leg3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition swim_control = root.addOrReplaceChild("swim_control", CubeListBuilder.create(), PartPose.offset(0.0F, -19.0F, 0.0F));

        PartDefinition body_main = swim_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -1.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 87).addBox(-4.5F, -12.0F, -5.5F, 9.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.5F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(41, 62).addBox(-5.5F, -4.0F, -13.0F, 11.0F, 6.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(77, 24).addBox(-5.5F, 2.0F, -13.0F, 11.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, -5.5F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(35, 21).addBox(4.0F, 0.0F, -4.0F, 2.0F, 12.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(35, 21).mirror().addBox(-6.0F, 0.0F, -4.0F, 2.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(45, 39).addBox(-6.0F, 12.0F, -14.0F, 12.0F, 3.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 25).mirror().addBox(-6.0F, 10.0F, -14.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 16).addBox(3.0F, 10.0F, -14.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -4.0F));

        PartDefinition saw = jaw.addOrReplaceChild("saw", CubeListBuilder.create().texOffs(70, 71).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 72).addBox(-1.0F, -6.0F, -6.0F, 2.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.5F, -12.5F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(77, 29).addBox(0.0F, 0.0F, -1.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(4.5F, -5.0F, -2.5F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(77, 29).mirror().addBox(-8.0F, 0.0F, -1.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-4.5F, -5.0F, -2.5F));

        PartDefinition dorsal1 = body.addOrReplaceChild("dorsal1", CubeListBuilder.create().texOffs(55, 21).addBox(0.0F, -6.0F, -1.0F, 0.0F, 6.0F, 11.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -12.0F, 1.5F));

        PartDefinition tail1 = body.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 16).addBox(-1.5F, -3.5F, -2.0F, 3.0F, 5.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.5F, 5.5F));

        PartDefinition dorsal2 = tail1.addOrReplaceChild("dorsal2", CubeListBuilder.create().texOffs(0, -11).addBox(0.0F, -6.0F, -10.0F, 0.0F, 6.0F, 20.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -3.5F, 11.0F));

        PartDefinition anal = tail1.addOrReplaceChild("anal", CubeListBuilder.create().texOffs(0, 41).addBox(0.0F, 0.0F, -10.0F, 0.0F, 6.0F, 20.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 1.5F, 11.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, -35).addBox(0.0F, -5.5F, -3.0F, 0.0F, 9.0F, 35.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 22.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.5F));

        PartDefinition left_leg1 = leg_control.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(75, 1).addBox(-2.0F, -2.0F, -3.5F, 5.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 0.0F, 0.0F));

        PartDefinition left_leg2 = left_leg1.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(26, 70).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 4.0F, 3.5F));

        PartDefinition left_leg3 = left_leg2.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(75, 15).addBox(-3.0F, 0.0F, -4.5F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(10, 52).addBox(1.0F, 0.0F, -7.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(20, 52).addBox(1.0F, 0.0F, -9.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 52).addBox(-3.0F, 0.0F, -7.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(20, 52).addBox(-3.0F, 0.0F, -9.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.5F));

        PartDefinition right_leg1 = leg_control.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(75, 1).mirror().addBox(-3.0F, -2.0F, -3.5F, 5.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.5F, 0.0F, 0.0F));

        PartDefinition right_leg2 = right_leg1.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(26, 70).mirror().addBox(-1.5F, -1.0F, -1.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 4.0F, 3.5F));

        PartDefinition right_leg3 = right_leg2.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(75, 15).mirror().addBox(-3.0F, 0.0F, -4.5F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(10, 52).mirror().addBox(-3.0F, 0.0F, -7.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(20, 52).mirror().addBox(-3.0F, 0.0F, -9.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(10, 52).mirror().addBox(1.0F, 0.0F, -7.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(20, 52).mirror().addBox(1.0F, 0.0F, -9.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 8.0F, 0.5F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Terror entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (entity.hasLegs() && !entity.isInWater()) {
            if (entity.isRunning()) {
                this.animateWalk(TerrorAnimations.RUN, limbSwing, limbSwingAmount, 1, 2);
            } else {
                this.animateWalk(TerrorAnimations.WALK, limbSwing, limbSwingAmount, 1, 2);
            }
		}
        this.animate(entity.swimAnimationState, TerrorAnimations.SWIM, ageInTicks, 0.75F + (Mth.clamp(limbSwingAmount, 0.25F, 1.0F) * 1.25F));
        this.animate(entity.flopAnimationState, TerrorAnimations.FLOP, ageInTicks);
        this.animate(entity.idleAnimationState, TerrorAnimations.IDLE, ageInTicks);
        this.animate(entity.growLegsAnimationState, TerrorAnimations.GROW_LEGS, ageInTicks);
        this.animate(entity.startSawingAnimationState, TerrorAnimations.START_SAWING, ageInTicks);
        this.animate(entity.sawingAnimationState, TerrorAnimations.SAWING, ageInTicks);
        this.animate(entity.retractLegsAnimationState, TerrorAnimations.RETRACT_LEGS, ageInTicks);

        if (entity.isInWaterOrBubble()) {
            this.swim_control.xRot = headPitch * (Mth.DEG_TO_RAD);
        }
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