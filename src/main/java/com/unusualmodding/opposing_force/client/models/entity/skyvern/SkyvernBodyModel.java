package com.unusualmodding.opposing_force.client.models.entity.skyvern;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.SkyvernAnimations;
import com.unusualmodding.opposing_force.entity.SkyvernSegment;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class SkyvernBodyModel extends HierarchicalModel<SkyvernSegment> {

    private final ModelPart root;
    private final ModelPart roll_control;
    private final ModelPart segment;
    private final ModelPart left_arm;
    private final ModelPart left_elbow;
    private final ModelPart left_hand;
    private final ModelPart right_arm;
    private final ModelPart right_elbow;
    private final ModelPart right_hand;

	public SkyvernBodyModel(ModelPart root) {
        this.root = root.getChild("root");
        this.roll_control = this.root.getChild("roll_control");
        this.segment = this.roll_control.getChild("segment");
        this.left_arm = this.segment.getChild("left_arm");
        this.left_elbow = this.left_arm.getChild("left_elbow");
        this.left_hand = this.left_elbow.getChild("left_hand");
        this.right_arm = this.segment.getChild("right_arm");
        this.right_elbow = this.right_arm.getChild("right_elbow");
        this.right_hand = this.right_elbow.getChild("right_hand");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition roll_control = root.addOrReplaceChild("roll_control", CubeListBuilder.create(), PartPose.offset(0.0F, -6.5F, 0.0F));

        PartDefinition segment = roll_control.addOrReplaceChild("segment", CubeListBuilder.create().texOffs(128, 131).addBox(-6.5F, -6.5F, -6.5F, 13.0F, 13.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(152, 0).addBox(0.0F, -10.5F, -6.5F, 0.0F, 4.0F, 12.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, -0.5F));

        PartDefinition left_arm = segment.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(92, 148).addBox(0.5F, -2.75F, -0.75F, 5.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 4.25F, -2.75F));

        PartDefinition left_elbow = left_arm.addOrReplaceChild("left_elbow", CubeListBuilder.create().texOffs(128, 157).addBox(-1.5F, -1.75F, -0.75F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 2.0F, 12.0F));

        PartDefinition left_hand = left_elbow.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(128, 46).addBox(-5.1667F, 0.25F, -1.75F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(140, 157).addBox(-1.1667F, 2.25F, -1.75F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(46, 141).addBox(1.8333F, 2.25F, -1.75F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(66, 141).addBox(-5.1667F, 0.25F, 0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(54, 141).addBox(1.8333F, 6.25F, 0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(60, 141).addBox(-1.1667F, 9.25F, 0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.3333F, 11.0F, 1.0F));

        PartDefinition right_arm = segment.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(92, 148).mirror().addBox(-5.5F, -2.75F, -0.75F, 5.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-6.0F, 4.25F, -2.75F));

        PartDefinition right_elbow = right_arm.addOrReplaceChild("right_elbow", CubeListBuilder.create().texOffs(128, 157).mirror().addBox(-1.5F, -1.75F, -0.75F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-3.0F, 2.0F, 12.0F));

        PartDefinition right_hand = right_elbow.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(128, 46).mirror().addBox(-3.8333F, 0.25F, -1.75F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(140, 157).mirror().addBox(-0.8333F, 2.25F, -1.75F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(46, 141).mirror().addBox(-3.8333F, 2.25F, -1.75F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(66, 141).mirror().addBox(4.1667F, 0.25F, 0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(54, 141).mirror().addBox(-3.8333F, 6.25F, 0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(60, 141).mirror().addBox(-0.8333F, 9.25F, 0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.3333F, 11.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(SkyvernSegment entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.flying1AnimationState, SkyvernAnimations.BODY_FLY1, ageInTicks);
        this.animate(entity.flying2AnimationState, SkyvernAnimations.BODY_FLY2, ageInTicks);
        this.animate(entity.attackStartAnimationState, SkyvernAnimations.BODY_ATTACK_START, ageInTicks);
        this.animate(entity.attackingAnimationState, SkyvernAnimations.BODY_ATTACK, ageInTicks);
        this.animate(entity.attackEndAnimationState, SkyvernAnimations.BODY_ATTACK_END, ageInTicks);
        this.animate(entity.roll1AnimationState, SkyvernAnimations.BODY_LOOP1, ageInTicks);
        if (entity.hasArms()) {
            this.right_arm.visible = true;
            this.left_arm.visible = true;
        } else {
            this.right_arm.visible = false;
            this.left_arm.visible = false;
        }
	}

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root().render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}