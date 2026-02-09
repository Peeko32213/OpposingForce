package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.LadybugAnimations;
import com.unusualmodding.opposing_force.client.models.entity.base.OPModel;
import com.unusualmodding.opposing_force.entity.Ladybug;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class LadybugModel extends OPModel<Ladybug> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart shell;
    private final ModelPart shell_1;
    private final ModelPart shell_2;
    private final ModelPart wing_1_bone;
    private final ModelPart wing_1;
    private final ModelPart wing_2_bone;
    private final ModelPart wing_2;
    private final ModelPart head;
    private final ModelPart left_leg_cluster;
    private final ModelPart leg_bone_1;
    private final ModelPart leg_1;
    private final ModelPart leg_bone_2;
    private final ModelPart leg_2;
    private final ModelPart leg_bone_3;
    private final ModelPart leg_3;
    private final ModelPart right_leg_cluster;
    private final ModelPart leg_bone_4;
    private final ModelPart leg_4;
    private final ModelPart leg_bone_5;
    private final ModelPart leg_5;
    private final ModelPart leg_bone_6;
    private final ModelPart leg_6;

	public LadybugModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.shell = this.body.getChild("shell");
        this.shell_1 = this.shell.getChild("shell_1");
        this.shell_2 = this.shell.getChild("shell_2");
        this.wing_1_bone = this.body.getChild("wing_1_bone");
        this.wing_1 = this.wing_1_bone.getChild("wing_1");
        this.wing_2_bone = this.body.getChild("wing_2_bone");
        this.wing_2 = this.wing_2_bone.getChild("wing_2");
        this.head = this.root.getChild("head");
        this.left_leg_cluster = this.root.getChild("left_leg_cluster");
        this.leg_bone_1 = this.left_leg_cluster.getChild("leg_bone_1");
        this.leg_1 = this.leg_bone_1.getChild("leg_1");
        this.leg_bone_2 = this.left_leg_cluster.getChild("leg_bone_2");
        this.leg_2 = this.leg_bone_2.getChild("leg_2");
        this.leg_bone_3 = this.left_leg_cluster.getChild("leg_bone_3");
        this.leg_3 = this.leg_bone_3.getChild("leg_3");
        this.right_leg_cluster = this.root.getChild("right_leg_cluster");
        this.leg_bone_4 = this.right_leg_cluster.getChild("leg_bone_4");
        this.leg_4 = this.leg_bone_4.getChild("leg_4");
        this.leg_bone_5 = this.right_leg_cluster.getChild("leg_bone_5");
        this.leg_5 = this.leg_bone_5.getChild("leg_5");
        this.leg_bone_6 = this.right_leg_cluster.getChild("leg_bone_6");
        this.leg_6 = this.leg_bone_6.getChild("leg_6");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, -3.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -11.0F, -1.0F, 16.0F, 13.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, -4.0F));

        PartDefinition shell = body.addOrReplaceChild("shell", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 2.0F));

        PartDefinition shell_1 = shell.addOrReplaceChild("shell_1", CubeListBuilder.create().texOffs(0, 29).addBox(-1.0F, -13.0F, 0.0F, 8.0F, 13.0F, 16.0F, new CubeDeformation(0.4F)), PartPose.offset(1.25F, 2.25F, -3.0F));

        PartDefinition shell_2 = shell.addOrReplaceChild("shell_2", CubeListBuilder.create().texOffs(48, 29).addBox(-7.275F, -13.0F, 0.0F, 8.0F, 13.0F, 16.0F, new CubeDeformation(0.4F)), PartPose.offset(-1.25F, 2.25F, -3.0F));

        PartDefinition wing_1_bone = body.addOrReplaceChild("wing_1_bone", CubeListBuilder.create(), PartPose.offset(1.5F, -11.0F, 0.0F));

        PartDefinition wing_1 = wing_1_bone.addOrReplaceChild("wing_1", CubeListBuilder.create().texOffs(0, 58).addBox(-1.0F, 0.0F, -1.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition wing_2_bone = body.addOrReplaceChild("wing_2_bone", CubeListBuilder.create(), PartPose.offset(-1.5F, -11.0F, 0.0F));

        PartDefinition wing_2 = wing_2_bone.addOrReplaceChild("wing_2", CubeListBuilder.create().texOffs(32, 58).addBox(-6.775F, 0.0F, -1.0F, 8.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(64, 6).addBox(2.0F, -2.0F, -6.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(64, 0).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(64, 10).addBox(-7.0F, -2.0F, -6.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -5.0F));

        PartDefinition left_leg_cluster = root.addOrReplaceChild("left_leg_cluster", CubeListBuilder.create(), PartPose.offset(8.0F, 5.0F, -2.0F));

        PartDefinition leg_bone_1 = left_leg_cluster.addOrReplaceChild("leg_bone_1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -2.5F, 0.6851F, 0.4795F, 1.0397F));

        PartDefinition leg_1 = leg_bone_1.addOrReplaceChild("leg_1", CubeListBuilder.create().texOffs(64, 14).addBox(0.0F, 0.0F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_bone_2 = left_leg_cluster.addOrReplaceChild("leg_bone_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -0.5F, 0.0F, 0.0F, 0.7418F));

        PartDefinition leg_2 = leg_bone_2.addOrReplaceChild("leg_2", CubeListBuilder.create().texOffs(64, 14).addBox(0.0F, 0.0F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_bone_3 = left_leg_cluster.addOrReplaceChild("leg_bone_3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, 1.5F, 0.0F, 0.0F, 0.7418F));

        PartDefinition leg_3 = leg_bone_3.addOrReplaceChild("leg_3", CubeListBuilder.create().texOffs(64, 14).addBox(0.0F, 0.0F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.1745F, 0.0F));

        PartDefinition right_leg_cluster = root.addOrReplaceChild("right_leg_cluster", CubeListBuilder.create(), PartPose.offset(-8.0F, 5.0F, -2.0F));

        PartDefinition leg_bone_4 = right_leg_cluster.addOrReplaceChild("leg_bone_4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -2.5F, 0.6851F, -0.4795F, -1.0397F));

        PartDefinition leg_4 = leg_bone_4.addOrReplaceChild("leg_4", CubeListBuilder.create().texOffs(64, 14).mirror().addBox(-4.0F, 0.0F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_bone_5 = right_leg_cluster.addOrReplaceChild("leg_bone_5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, -0.5F, 0.0F, 0.0F, -0.7418F));

        PartDefinition leg_5 = leg_bone_5.addOrReplaceChild("leg_5", CubeListBuilder.create().texOffs(64, 14).mirror().addBox(-4.0F, 0.0F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg_bone_6 = right_leg_cluster.addOrReplaceChild("leg_bone_6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -1.0F, 1.5F, 0.0F, 0.0F, -0.7418F));

        PartDefinition leg_6 = leg_bone_6.addOrReplaceChild("leg_6", CubeListBuilder.create().texOffs(64, 14).mirror().addBox(-4.0F, 0.0F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.1745F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Ladybug entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        if (!entity.isFlying()) {
            this.animateWalk(LadybugAnimations.WALK, limbSwing, limbSwingAmount, 2.5F, 5);
        }
        this.animateIdle(entity.idleAnimationState, LadybugAnimations.IDLE, ageInTicks, 1, limbSwingAmount * 4);
        this.animate(entity.flyingAnimationState, LadybugAnimations.FLYING, ageInTicks);
        this.animate(entity.bashAnimationState, LadybugAnimations.BASH_GROUND, ageInTicks);
        this.animate(entity.airBashAnimationState, LadybugAnimations.BASH_FLY, ageInTicks);
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