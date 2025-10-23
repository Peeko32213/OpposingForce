package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.RamblerAnimations;
import com.unusualmodding.opposing_force.client.animations.RamblerIdleAnimations;
import com.unusualmodding.opposing_force.entity.Rambler;
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
public class RamblerModel extends HierarchicalModel<Rambler> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart roll_control;
    private final ModelPart body;
    private final ModelPart crown;
    public final ModelPart middle_skull;
    public final ModelPart left_skull;
    public final ModelPart right_skull;
    private final ModelPart left_front_top_arm;
    private final ModelPart left_front_bottom_arm;
    private final ModelPart left_back_top_arm;
    private final ModelPart left_back_bottom_arm;
    private final ModelPart right_front_top_arm;
    private final ModelPart right_front_bottom_arm;
    private final ModelPart right_back_top_arm;
    private final ModelPart right_back_bottom_arm;
    private final ModelPart leg_control;
    private final ModelPart left_front_leg;
    private final ModelPart right_front_leg;
    private final ModelPart left_back_leg;
    private final ModelPart right_back_leg;

	public RamblerModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.roll_control = this.body_main.getChild("roll_control");
        this.body = this.roll_control.getChild("body");
        this.crown = this.body.getChild("crown");
        this.middle_skull = this.body.getChild("middle_skull");
        this.left_skull = this.body.getChild("left_skull");
        this.right_skull = this.body.getChild("right_skull");
        this.left_front_top_arm = this.body.getChild("left_front_top_arm");
        this.left_front_bottom_arm = this.body.getChild("left_front_bottom_arm");
        this.left_back_top_arm = this.body.getChild("left_back_top_arm");
        this.left_back_bottom_arm = this.body.getChild("left_back_bottom_arm");
        this.right_front_top_arm = this.body.getChild("right_front_top_arm");
        this.right_front_bottom_arm = this.body.getChild("right_front_bottom_arm");
        this.right_back_top_arm = this.body.getChild("right_back_top_arm");
        this.right_back_bottom_arm = this.body.getChild("right_back_bottom_arm");
        this.leg_control = this.roll_control.getChild("leg_control");
        this.left_front_leg = this.leg_control.getChild("left_front_leg");
        this.right_front_leg = this.leg_control.getChild("right_front_leg");
        this.left_back_leg = this.leg_control.getChild("left_back_leg");
        this.right_back_leg = this.leg_control.getChild("right_back_leg");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition roll_control = body_main.addOrReplaceChild("roll_control", CubeListBuilder.create(), PartPose.offset(0.0F, -15.0F, 0.0F));

        PartDefinition body = roll_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(2, 0).addBox(-15.0F, -30.0F, -15.0F, 30.0F, 25.0F, 30.0F, new CubeDeformation(0.0F))
                .texOffs(0, 64).addBox(-4.0F, -30.0F, 7.0F, 8.0F, 26.0F, 8.0F, new CubeDeformation(0.01F))
                .texOffs(79, 55).addBox(-4.0F, -30.0F, -4.0F, 8.0F, 8.0F, 11.0F, new CubeDeformation(0.01F))
                .texOffs(99, 82).addBox(0.0F, -33.0F, 5.0F, 0.0F, 29.0F, 13.0F, new CubeDeformation(0.0025F))
                .texOffs(8, 94).addBox(-15.0F, -4.0F, -15.0F, 30.0F, 4.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition crown = body.addOrReplaceChild("crown", CubeListBuilder.create().texOffs(40, 64).addBox(-3.0F, -7.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -30.0F, 0.0F));

        PartDefinition middle_skull = body.addOrReplaceChild("middle_skull", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -24.0F, -15.0F));

        PartDefinition left_skull = body.addOrReplaceChild("left_skull", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -12.0F, -15.0F));

        PartDefinition right_skull = body.addOrReplaceChild("right_skull", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -16.0F, -15.0F));

        PartDefinition left_front_top_arm = body.addOrReplaceChild("left_front_top_arm", CubeListBuilder.create().texOffs(72, 64).addBox(16.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 60).addBox(0.0F, -1.0F, -1.0F, 34.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(15.0F, -29.0F, -10.0F));

        PartDefinition left_front_bottom_arm = body.addOrReplaceChild("left_front_bottom_arm", CubeListBuilder.create().texOffs(72, 62).addBox(-1.0F, 15.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F))
                .texOffs(120, 58).addBox(0.0F, -1.0F, -1.0F, 2.0F, 34.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(15.0F, -26.0F, -10.0F));

        PartDefinition left_back_top_arm = body.addOrReplaceChild("left_back_top_arm", CubeListBuilder.create().texOffs(72, 64).addBox(16.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 60).addBox(0.0F, -1.0F, -1.0F, 34.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(15.0F, -29.0F, 10.0F));

        PartDefinition left_back_bottom_arm = body.addOrReplaceChild("left_back_bottom_arm", CubeListBuilder.create().texOffs(72, 62).addBox(-1.0F, 15.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F))
                .texOffs(120, 58).addBox(0.0F, -1.0F, -1.0F, 2.0F, 34.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(15.0F, -26.0F, 10.0F));

        PartDefinition right_front_top_arm = body.addOrReplaceChild("right_front_top_arm", CubeListBuilder.create().texOffs(72, 64).mirror().addBox(-18.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(0, 60).mirror().addBox(-34.0F, -1.0F, -1.0F, 34.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-15.0F, -29.0F, -10.0F));

        PartDefinition right_front_bottom_arm = body.addOrReplaceChild("right_front_bottom_arm", CubeListBuilder.create().texOffs(72, 62).mirror().addBox(-3.0F, 15.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(120, 58).mirror().addBox(-2.0F, -1.0F, -1.0F, 2.0F, 34.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-15.0F, -26.0F, -10.0F));

        PartDefinition right_back_top_arm = body.addOrReplaceChild("right_back_top_arm", CubeListBuilder.create().texOffs(72, 64).mirror().addBox(-18.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(0, 60).mirror().addBox(-34.0F, -1.0F, -1.0F, 34.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-15.0F, -29.0F, 10.0F));

        PartDefinition right_back_bottom_arm = body.addOrReplaceChild("right_back_bottom_arm", CubeListBuilder.create().texOffs(72, 62).mirror().addBox(-3.0F, 15.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(120, 58).mirror().addBox(-2.0F, -1.0F, -1.0F, 2.0F, 34.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-15.0F, -26.0F, 10.0F));

        PartDefinition leg_control = roll_control.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition left_front_leg = leg_control.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(64, 64).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(72, 60).addBox(-2.0F, 3.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(4.0F, 0.0F, -4.0F));

        PartDefinition right_front_leg = leg_control.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(64, 64).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(72, 60).mirror().addBox(-2.0F, 3.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-4.0F, 0.0F, -4.0F));

        PartDefinition left_back_leg = leg_control.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(64, 64).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(72, 60).addBox(-2.0F, 3.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(4.0F, 0.0F, 4.0F));

        PartDefinition right_back_leg = leg_control.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(64, 64).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(72, 60).mirror().addBox(-2.0F, 3.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-4.0F, 0.0F, 4.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Rambler entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.walkAnimationState, RamblerAnimations.WALK, ageInTicks, 0.75F + (Mth.clamp(limbSwingAmount, 0.25F, 1.0F) * 1.25F));
        this.animate(entity.idleAnimationState, RamblerIdleAnimations.IDLE, ageInTicks);
		this.animate(entity.recoverAnimationState, RamblerAnimations.RECOVER, ageInTicks);
        this.animate(entity.flailStartAnimationState, RamblerAnimations.FLAIL_START, ageInTicks);
        this.animate(entity.flailAnimationState, RamblerAnimations.FLAIL, ageInTicks);
        this.animate(entity.flailEndAnimationState, RamblerAnimations.FLAIL_END, ageInTicks);
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