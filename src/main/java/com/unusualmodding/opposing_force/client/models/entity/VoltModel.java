package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.VoltAnimations;
import com.unusualmodding.opposing_force.entity.Volt;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class VoltModel extends HierarchicalModel<Volt> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart jowls;
    private final ModelPart left_crest;
    private final ModelPart right_crest;
    private final ModelPart jaw;
    private final ModelPart left_wing;
    private final ModelPart right_wing;
    private final ModelPart tail1;
    private final ModelPart tail_2;
    private final ModelPart tail3;
    private final ModelPart tail_tip;

	public VoltModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.jowls = this.body.getChild("jowls");
        this.left_crest = this.body.getChild("left_crest");
        this.right_crest = this.body.getChild("right_crest");
        this.jaw = this.body.getChild("jaw");
        this.left_wing = this.body.getChild("left_wing");
        this.right_wing = this.body.getChild("right_wing");
        this.tail1 = this.body_main.getChild("tail1");
        this.tail_2 = this.tail1.getChild("tail_2");
        this.tail3 = this.tail_2.getChild("tail3");
        this.tail_tip = this.tail3.getChild("tail_tip");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -17.0F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 15).addBox(-7.5F, -4.0F, -11.5F, 15.0F, 1.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(0, 60).mirror().addBox(-1.5F, -6.0F, -11.5F, 0.0F, 2.0F, 11.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(0, 60).addBox(1.5F, -6.0F, -11.5F, 0.0F, 2.0F, 11.0F, new CubeDeformation(0.0025F))
                .texOffs(28, 46).addBox(-1.5F, -3.0F, -11.5F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 31).addBox(-2.5F, -3.0F, -11.0F, 5.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(34, 31).addBox(-5.5F, -3.025F, -10.5F, 11.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition jowls = body.addOrReplaceChild("jowls", CubeListBuilder.create().texOffs(60, 15).addBox(-7.5F, 0.0F, -4.0F, 15.0F, 0.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -3.0F, -11.5F));

        PartDefinition left_crest = body.addOrReplaceChild("left_crest", CubeListBuilder.create().texOffs(62, 0).addBox(-0.5F, -3.0F, 1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -4.0F, -11.5F));

        PartDefinition right_crest = body.addOrReplaceChild("right_crest", CubeListBuilder.create().texOffs(62, 0).mirror().addBox(-2.5F, -3.0F, 1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, -4.0F, -11.5F));

        PartDefinition jaw = body.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(60, 53).addBox(-1.5F, -1.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -9.5F));

        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(0.0F, 0.0F, -1.0F, 16.0F, 0.0F, 15.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(7.5F, -3.0F, -10.5F));

        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, 0.0F, -1.0F, 16.0F, 0.0F, 15.0F, new CubeDeformation(0.01F)), PartPose.offset(-7.5F, -3.0F, -10.5F));

        PartDefinition tail1 = body_main.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(60, 19).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(60, 59).addBox(-1.0F, -6.0F, 4.0F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(37, 44).addBox(0.0F, -10.0F, 2.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.003F))
                .texOffs(62, 6).addBox(-1.0F, -6.0F, 7.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail_2 = tail1.addOrReplaceChild("tail_2", CubeListBuilder.create().texOffs(22, 60).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(52, 56).addBox(0.0F, -2.0F, 0.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -5.0F, 11.0F));

        PartDefinition tail3 = tail_2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 46).addBox(-1.0F, -1.0F, -12.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(30, 63).addBox(-1.0F, 1.0F, -12.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 14.0F, 0.0F));

        PartDefinition tail_tip = tail3.addOrReplaceChild("tail_tip", CubeListBuilder.create().texOffs(60, 41).addBox(0.0F, -6.0F, 0.0F, 0.0F, 6.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 8.0F, -10.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Volt entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        if (!entity.isInWater() && entity.getPose() == Pose.STANDING) {
            this.animateWalk(VoltAnimations.WALK, limbSwing, limbSwingAmount, 3, 6);
        }
        this.animate(entity.swimmingAnimationState, VoltAnimations.SWIM, ageInTicks, 0.7F + (Mth.clamp(limbSwingAmount, 0.4F, 1.0F) * 1.4F));
        this.animate(entity.idleAnimationState, VoltAnimations.IDLE, ageInTicks);
		this.animate(entity.shootAnimationState, VoltAnimations.SHOCK_LAND, ageInTicks);
        this.animate(entity.shootWaterAnimationState, VoltAnimations.SHOCK_SWIM, ageInTicks);
        this.animate(entity.jumpAnimationState, VoltAnimations.JUMP_START, ageInTicks);
        this.animate(entity.fallingAnimationState, VoltAnimations.JUMP_FALL, ageInTicks);
        this.animate(entity.landingAnimationState, VoltAnimations.JUMP_END, ageInTicks);
        this.animate(entity.twitch1AnimationState, VoltAnimations.TWITCH1, ageInTicks);
        this.animate(entity.twitch2AnimationState, VoltAnimations.TWITCH2, ageInTicks);

        if (entity.isInWaterOrBubble()) {
            this.root.xRot = headPitch * (Mth.DEG_TO_RAD) / 2;
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