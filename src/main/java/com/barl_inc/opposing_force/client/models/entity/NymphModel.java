package com.barl_inc.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.barl_inc.opposing_force.client.animations.NymphAnimations;
import com.barl_inc.opposing_force.entity.Nymph;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class NymphModel extends HierarchicalModel<Nymph> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body_swing_control;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart front_vines;
    private final ModelPart left_vines;
    private final ModelPart right_vines;
    private final ModelPart back_vines;
    private final ModelPart tangle1;
    private final ModelPart tangle2;
    private final ModelPart leg_control;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

	public NymphModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body_swing_control = this.body_main.getChild("body_swing_control");
        this.body = this.body_swing_control.getChild("body");
        this.head = this.body.getChild("head");
        this.front_vines = this.head.getChild("front_vines");
        this.left_vines = this.head.getChild("left_vines");
        this.right_vines = this.head.getChild("right_vines");
        this.back_vines = this.head.getChild("back_vines");
        this.tangle1 = this.body.getChild("tangle1");
        this.tangle2 = this.tangle1.getChild("tangle2");
        this.leg_control = this.body_main.getChild("leg_control");
        this.left_leg = this.leg_control.getChild("left_leg");
        this.right_leg = this.leg_control.getChild("right_leg");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -29.0F, 0.0F));

        PartDefinition body_swing_control = body_main.addOrReplaceChild("body_swing_control", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition body = body_swing_control.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 43).addBox(-4.0F, -39.0F, -2.0F, 8.0F, 39.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(52, 43).addBox(-5.0F, -11.0F, -5.0F, 10.0F, 11.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(28, 16).addBox(-6.0F, -12.0F, -6.0F, 12.0F, 15.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, 3.0F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, -39.0F, 0.0F));

        PartDefinition front_vines = head.addOrReplaceChild("front_vines", CubeListBuilder.create().texOffs(52, 64).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, -5.0F));

        PartDefinition left_vines = head.addOrReplaceChild("left_vines", CubeListBuilder.create().texOffs(52, 61).addBox(0.0F, 0.0F, -5.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0025F)), PartPose.offset(5.0F, 0.0F, 0.0F));

        PartDefinition right_vines = head.addOrReplaceChild("right_vines", CubeListBuilder.create().texOffs(52, 61).mirror().addBox(0.0F, 0.0F, -5.0F, 0.0F, 7.0F, 10.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(-5.0F, 0.0F, 0.0F));

        PartDefinition back_vines = head.addOrReplaceChild("back_vines", CubeListBuilder.create().texOffs(52, 64).mirror().addBox(-5.0F, 0.0F, 0.0F, 10.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition tangle1 = body.addOrReplaceChild("tangle1", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 44.0F, 6.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -39.0F, 0.0F));

        PartDefinition tangle2 = tangle1.addOrReplaceChild("tangle2", CubeListBuilder.create().texOffs(65, 0).addBox(-6.0F, -2.0F, -5.0F, 12.0F, 2.0F, 10.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 44.0F, 0.0F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 20.0F, 0.0F));

        PartDefinition left_leg = leg_control.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 66).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));

        PartDefinition right_leg = leg_control.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 66).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Nymph entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(NymphAnimations.WALK, limbSwing, limbSwingAmount, 2, 4);
		this.animate(entity.idleAnimationState, NymphAnimations.IDLE, ageInTicks);
        this.animate(entity.sighAnimationState, NymphAnimations.SIGH, ageInTicks);
        this.animate(entity.tilt1AnimationState, NymphAnimations.TILT1, ageInTicks);
        this.animate(entity.tilt2AnimationState, NymphAnimations.TILT2, ageInTicks);
        this.animate(entity.hideAnimationState, NymphAnimations.HIDE, ageInTicks);
        this.animate(entity.appearAnimationState, NymphAnimations.APPEAR, ageInTicks);
        this.animate(entity.swing1AnimationState, NymphAnimations.SWING1, ageInTicks);
        this.animate(entity.swing2AnimationState, NymphAnimations.SWING2, ageInTicks);
        this.animate(entity.summonVinesAnimationState, NymphAnimations.SUMMON, ageInTicks);
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