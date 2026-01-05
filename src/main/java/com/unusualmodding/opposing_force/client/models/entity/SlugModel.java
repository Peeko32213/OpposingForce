package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.SlugAnimations;
import com.unusualmodding.opposing_force.client.models.entity.base.OPModel;
import com.unusualmodding.opposing_force.entity.Slug;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class SlugModel extends OPModel<Slug> {

    private final ModelPart root;
    private final ModelPart rotate_control;
    private final ModelPart body_main;
    private final ModelPart head;
    private final ModelPart EyeStalk2;
    private final ModelPart EyeStalk1;
    private final ModelPart body;
    private final ModelPart body_inflate;
    private final ModelPart tail_control;
    private final ModelPart tail1;
    private final ModelPart tail2;
    private final ModelPart tail3;

	public SlugModel(ModelPart root) {
        this.root = root.getChild("root");
        this.rotate_control = this.root.getChild("rotate_control");
        this.body_main = this.rotate_control.getChild("body_main");
        this.head = this.body_main.getChild("head");
        this.EyeStalk2 = this.head.getChild("EyeStalk2");
        this.EyeStalk1 = this.head.getChild("EyeStalk1");
        this.body = this.body_main.getChild("body");
        this.body_inflate = this.body.getChild("body_inflate");
        this.tail_control = this.body.getChild("tail_control");
        this.tail1 = this.tail_control.getChild("tail1");
        this.tail2 = this.tail1.getChild("tail2");
        this.tail3 = this.tail2.getChild("tail3");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition rotate_control = root.addOrReplaceChild("rotate_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body_main = rotate_control.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition head = body_main.addOrReplaceChild("head", CubeListBuilder.create().texOffs(29, 0).addBox(-3.5F, -3.0F, -3.0F, 7.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(2.0F, 1.0F, -5.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).mirror().addBox(-5.0F, 1.0F, -5.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 3.0F, -5.0F));

        PartDefinition EyeStalk2 = head.addOrReplaceChild("EyeStalk2", CubeListBuilder.create().texOffs(18, 32).mirror().addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).mirror().addBox(-1.0F, -4.0F, -4.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-2.5F, -1.0F, -3.0F));

        PartDefinition EyeStalk1 = head.addOrReplaceChild("EyeStalk1", CubeListBuilder.create().texOffs(18, 32).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.0F, -4.0F, -4.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -1.0F, -3.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, -5.0F));

        PartDefinition body_inflate = body.addOrReplaceChild("body_inflate", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -10.0F, -5.0F, 9.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition tail_control = body.addOrReplaceChild("tail_control", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 11.0F));

        PartDefinition tail1 = tail_control.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 29).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(1.5F, -3.0F, 2.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).mirror().addBox(-1.5F, -3.0F, 2.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(19, 24).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 3.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(6, 3).addBox(1.5F, -2.0F, 3.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(6, 3).mirror().addBox(-1.5F, -2.0F, 3.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 4.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 21).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).addBox(1.5F, -2.0F, 2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 6).mirror().addBox(-1.5F, -2.0F, 2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 5.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Slug entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(SlugAnimations.SLIDE, limbSwing, limbSwingAmount, 4, 8);
		this.animateIdle(entity.idleAnimationState, SlugAnimations.IDLE, ageInTicks, 1, limbSwingAmount * 4);
        this.animate(entity.launchStartAnimationState, SlugAnimations.LAUNCH_START, ageInTicks);
        this.animate(entity.launchedAnimationState, SlugAnimations.LAUNCHING, ageInTicks);
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