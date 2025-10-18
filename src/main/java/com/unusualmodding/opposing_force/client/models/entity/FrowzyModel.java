package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.FrowzyAnimations;
import com.unusualmodding.opposing_force.entity.Frowzy;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class FrowzyModel extends HierarchicalModel<Frowzy> implements ArmedModel, HeadedModel {

    private final ModelPart root;
    private final ModelPart upper_body;
    private final ModelPart head;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public FrowzyModel(ModelPart root) {
        this.root = root.getChild("root");
        this.upper_body = this.root.getChild("upper_body");
        this.head = this.upper_body.getChild("head");
        this.left_arm = this.upper_body.getChild("left_arm");
        this.right_arm = this.upper_body.getChild("right_arm");
        this.left_leg = this.root.getChild("left_leg");
        this.right_leg = this.root.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition upper_body = root.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 29).addBox(-3.0F, -7.0F, -1.5F, 6.0F, 5.0F, 3.0F, new CubeDeformation(0.25F))
                .texOffs(32, 0).addBox(-3.0F, -7.0F, -1.0F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.0F, 0.0F));

        PartDefinition head = upper_body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(2, 19).addBox(-3.5F, -5.5F, -3.75F, 7.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 0.0F));

        PartDefinition left_arm = upper_body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(34, 15).addBox(0.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.2F))
                .texOffs(18, 29).addBox(0.0F, -1.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -6.0F, 0.0F));

        PartDefinition right_arm = upper_body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(34, 15).mirror().addBox(-2.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false)
                .texOffs(18, 29).mirror().addBox(-2.0F, -1.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, -6.0F, 0.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 9).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.2F))
                .texOffs(26, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -17.0F, 0.0F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(32, 9).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.2F)).mirror(false)
                .texOffs(26, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.0F, -17.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

	@Override
	public void setupAnim(Frowzy entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(FrowzyAnimations.WALK, limbSwing, limbSwingAmount, 1, 2);
		this.animate(entity.idleAnimationState, FrowzyAnimations.IDLE, ageInTicks);
		this.animate(entity.attackAnimationState, FrowzyAnimations.ATTACK, ageInTicks);

		if (this.young) {
			this.applyStatic(FrowzyAnimations.BABY_TRANSFORM);
		}

		this.head.xRot += headPitch * ((float) Math.PI / 180) - (headPitch * ((float) Math.PI / 180)) / 2;
		this.head.yRot += netHeadYaw * ((float) Math.PI / 180) - (netHeadYaw * ((float) Math.PI / 180)) / 2;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
		if (this.young) {
			float babyScale = 0.5F;
			float bodyYOffset = 24.0F;
			poseStack.pushPose();
			poseStack.scale(babyScale, babyScale, babyScale);
			poseStack.translate(0.0F, bodyYOffset / 16.0F, 0.0F);
			this.root().render(poseStack, vertexConsumer, i, j, f, g, h, k);
			poseStack.popPose();
		} else {
			this.root().render(poseStack, vertexConsumer, i, j, f, g, h, k);
		}
	}

    @Override
	public ModelPart root() {
		return this.root;
	}

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
        this.upper_body.translateAndRotate(poseStack);
        if (arm == HumanoidArm.RIGHT) {
            this.right_arm.translateAndRotate(poseStack);
        } else {
            this.left_arm.translateAndRotate(poseStack);
        }
        poseStack.translate(0.0F, 0.25F, 0.025F);
    }

    public void translateToHead(PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
        this.upper_body.translateAndRotate(poseStack);
        this.head.translateAndRotate(poseStack);
        poseStack.translate(0.0F, -0.025F, 0.025F);
    }

    @Override
    public ModelPart getHead() {
        return this.head;
    }
}