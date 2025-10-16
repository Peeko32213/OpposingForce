package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.HangingSpiderAnimations;
import com.unusualmodding.opposing_force.entity.HangingSpider;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class HangingSpiderModel extends HierarchicalModel<HangingSpider> {

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart left_mandible;
    private final ModelPart right_mandible;
    private final ModelPart abdomen;
    private final ModelPart left_legs;
    private final ModelPart left_leg1;
    private final ModelPart left_leg2;
    private final ModelPart left_leg3;
    private final ModelPart left_leg4;
    private final ModelPart right_legs;
    private final ModelPart right_leg1;
    private final ModelPart right_leg2;
    private final ModelPart right_leg3;
    private final ModelPart right_leg4;

	public HangingSpiderModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head = this.root.getChild("head");
        this.left_mandible = this.head.getChild("left_mandible");
        this.right_mandible = this.head.getChild("right_mandible");
        this.abdomen = this.root.getChild("abdomen");
        this.left_legs = this.root.getChild("left_legs");
        this.left_leg1 = this.left_legs.getChild("left_leg1");
        this.left_leg2 = this.left_legs.getChild("left_leg2");
        this.left_leg3 = this.left_legs.getChild("left_leg3");
        this.left_leg4 = this.left_legs.getChild("left_leg4");
        this.right_legs = this.root.getChild("right_legs");
        this.right_leg1 = this.right_legs.getChild("right_leg1");
        this.right_leg2 = this.right_legs.getChild("right_leg2");
        this.right_leg3 = this.right_legs.getChild("right_leg3");
        this.right_leg4 = this.right_legs.getChild("right_leg4");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(40, 2).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, -1.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 20).addBox(-5.0F, -3.0F, -6.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(24, 20).addBox(-5.0F, -3.0F, -6.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.2F)), PartPose.offset(2.0F, 0.0F, -1.0F));

        PartDefinition left_mandible = head.addOrReplaceChild("left_mandible", CubeListBuilder.create().texOffs(24, 33).addBox(0.0F, -1.0F, -8.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(-2.0F, -1.0F, -8.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.02F)), PartPose.offset(0.5F, 1.0F, -6.0F));

        PartDefinition right_mandible = head.addOrReplaceChild("right_mandible", CubeListBuilder.create().texOffs(24, 33).mirror().addBox(-2.0F, -1.0F, -8.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 31).mirror().addBox(-2.0F, -1.0F, -8.0F, 4.0F, 2.0F, 8.0F, new CubeDeformation(0.02F)).mirror(false), PartPose.offset(-4.5F, 1.0F, -6.0F));

        PartDefinition abdomen = root.addOrReplaceChild("abdomen", CubeListBuilder.create().texOffs(40, 0).addBox(-3.0F, -3.0F, 10.0F, 6.0F, 0.0F, 2.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 0).addBox(-5.0F, -8.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_legs = root.addOrReplaceChild("left_legs", CubeListBuilder.create(), PartPose.offset(3.0F, 1.0F, -4.0F));

        PartDefinition left_leg1 = left_legs.addOrReplaceChild("left_leg1", CubeListBuilder.create().texOffs(24, 31).addBox(0.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, 0.6545F, 0.3491F));

        PartDefinition left_leg2 = left_legs.addOrReplaceChild("left_leg2", CubeListBuilder.create().texOffs(24, 31).addBox(0.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.2182F, 0.3491F));

        PartDefinition left_leg3 = left_legs.addOrReplaceChild("left_leg3", CubeListBuilder.create().texOffs(24, 31).addBox(0.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.5F, 0.0F, -0.2182F, 0.3491F));

        PartDefinition left_leg4 = left_legs.addOrReplaceChild("left_leg4", CubeListBuilder.create().texOffs(24, 31).addBox(0.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.5F, 0.0F, -0.6545F, 0.3491F));

        PartDefinition right_legs = root.addOrReplaceChild("right_legs", CubeListBuilder.create(), PartPose.offset(-3.0F, 1.0F, -4.0F));

        PartDefinition right_leg1 = right_legs.addOrReplaceChild("right_leg1", CubeListBuilder.create().texOffs(24, 31).mirror().addBox(-15.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -1.5F, 0.0F, -0.6545F, -0.3491F));

        PartDefinition right_leg2 = right_legs.addOrReplaceChild("right_leg2", CubeListBuilder.create().texOffs(24, 31).mirror().addBox(-15.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, -0.2182F, -0.3491F));

        PartDefinition right_leg3 = right_legs.addOrReplaceChild("right_leg3", CubeListBuilder.create().texOffs(24, 31).mirror().addBox(-15.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.5F, 0.0F, 0.2182F, -0.3491F));

        PartDefinition right_leg4 = right_legs.addOrReplaceChild("right_leg4", CubeListBuilder.create().texOffs(24, 31).mirror().addBox(-15.0F, 0.0F, -0.5F, 15.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 1.5F, 0.0F, 0.6545F, -0.3491F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(HangingSpider entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        if (!(entity.isGoingUp() || entity.isGoingDown())) {
            this.animateWalk(HangingSpiderAnimations.SCURRY, limbSwing, limbSwingAmount, 2, 4);
        }
		this.animate(entity.idleAnimationState, HangingSpiderAnimations.IDLE, ageInTicks);
        this.animate(entity.goingUpAnimationState, HangingSpiderAnimations.GOING_UP, ageInTicks);
        this.animate(entity.goingDownAnimationState, HangingSpiderAnimations.GOING_DOWN, ageInTicks);
        this.animate(entity.biteAnimationState, HangingSpiderAnimations.BITE, ageInTicks);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

    @Override
	public ModelPart root() {
		return this.root;
	}
}