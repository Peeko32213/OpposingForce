package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.TremblerAnimations;
import com.unusualmodding.opposing_force.entity.Trembler;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class TremblerModel extends HierarchicalModel<Trembler> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tail;
    private final ModelPart shell;
    private final ModelPart shell_rotation;

	public TremblerModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tail = this.body.getChild("tail");
        this.shell = this.root.getChild("shell");
        this.shell_rotation = this.shell.getChild("shell_rotation");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -3.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -1.0F, -8.5F, 8.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 3.5F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(21, 45).addBox(-3.0F, -12.0F, -5.0F, 6.0F, 14.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 17).addBox(0.025F, -9.0F, -7.0F, 0.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 9).addBox(-3.0F, -15.0F, -1.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.025F, -4.5F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(33, 1).addBox(-5.0F, -2.0F, -6.0F, 5.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(11, 3).mirror().addBox(-5.025F, -4.0F, -7.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).mirror().addBox(-5.025F, -5.0F, -4.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).addBox(0.025F, -5.0F, -4.0F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(11, 3).addBox(0.025F, -4.0F, -7.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -14.0F, -2.0F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(31, 37).addBox(-6.0F, 0.0F, -7.0F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(1.0F, -1.0F, -7.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).mirror().addBox(-6.0F, -1.0F, -7.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 15).addBox(-6.0F, -1.0F, -7.0F, 7.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-6.0F, -2.0F, 0.0F, 7.0F, 4.0F, 3.0F, new CubeDeformation(-0.025F)), PartPose.offset(0.0F, 1.025F, -2.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(33, 3).addBox(0.5F, -1.0F, 0.0F, 0.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(-9, 55).addBox(-1.0F, 0.0F, 0.0F, 3.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(11, 0).addBox(-1.0F, -5.0F, 9.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 1.0F, 8.5F));

        PartDefinition shell = root.addOrReplaceChild("shell", CubeListBuilder.create(), PartPose.offset(3.0F, -3.0F, 0.0F));

        PartDefinition shell_rotation = shell.addOrReplaceChild("shell_rotation", CubeListBuilder.create().texOffs(0, 19).addBox(-3.5F, -6.0F, -6.0F, 7.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -5.0F, 5.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Trembler entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(TremblerAnimations.SLIDE, limbSwing, limbSwingAmount, 4, 8);
		this.animate(entity.idleAnimationState, TremblerAnimations.IDLE, ageInTicks);
		this.animate(entity.rollAnimationState, TremblerAnimations.ROLL, ageInTicks);
		this.animate(entity.stunnedAnimationState, TremblerAnimations.STUNNED, ageInTicks);

		if (entity.getStunnedTicks() <= 0) {
			this.head.xRot += headPitch * ((float) Math.PI / 180f) - (headPitch * ((float) Math.PI / 180f)) / 2;
			this.head.yRot += netHeadYaw * ((float) Math.PI / 180f) - (netHeadYaw * ((float) Math.PI / 180f)) / 2;
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public ModelPart root() {
		return this.root;
	}
}