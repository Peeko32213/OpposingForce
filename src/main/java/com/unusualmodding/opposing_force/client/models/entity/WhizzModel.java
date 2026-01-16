package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.WhizzAnimations;
import com.unusualmodding.opposing_force.client.models.entity.base.OPModel;
import com.unusualmodding.opposing_force.entity.Whizz;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class WhizzModel extends OPModel<Whizz> {

    private final ModelPart root;
    private final ModelPart left_mandible;
    private final ModelPart right_mandible;
    private final ModelPart left_wing1;
    private final ModelPart right_wing1;
    private final ModelPart left_wing2;
    private final ModelPart right_wing2;

	public WhizzModel(ModelPart root) {
        this.root = root.getChild("root");
        this.left_mandible = this.root.getChild("left_mandible");
        this.right_mandible = this.root.getChild("right_mandible");
        this.left_wing1 = this.root.getChild("left_wing1");
        this.right_wing1 = this.root.getChild("right_wing1");
        this.left_wing2 = this.root.getChild("left_wing2");
        this.right_wing2 = this.root.getChild("right_wing2");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(0, 12).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-4.0F, 3.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 18.0F, 0.0F));

        PartDefinition left_mandible = root.addOrReplaceChild("left_mandible", CubeListBuilder.create().texOffs(32, 10).addBox(-1.0F, 0.0F, -8.5F, 4.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(4.0F, 2.5F, -3.5F, 0.6109F, 0.0F, 0.0F));

        PartDefinition right_mandible = root.addOrReplaceChild("right_mandible", CubeListBuilder.create().texOffs(32, 10).mirror().addBox(-3.0F, 0.0F, -8.5F, 4.0F, 0.0F, 9.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 2.5F, -3.5F, 0.6109F, 0.0F, 0.0F));

        PartDefinition left_wing1 = root.addOrReplaceChild("left_wing1", CubeListBuilder.create().texOffs(32, 19).addBox(0.0F, -9.0F, 0.0F, 9.0F, 9.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.5F, -2.5F, -2.0F, 0.4363F, 0.0F, 0.4363F));

        PartDefinition right_wing1 = root.addOrReplaceChild("right_wing1", CubeListBuilder.create().texOffs(32, 19).mirror().addBox(-9.0F, -9.0F, 0.0F, 9.0F, 9.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-3.5F, -2.5F, -2.0F, 0.4363F, 0.0F, -0.4363F));

        PartDefinition left_wing2 = root.addOrReplaceChild("left_wing2", CubeListBuilder.create().texOffs(32, 28).addBox(0.0F, -7.0F, 0.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(3.5F, -2.5F, 2.0F, -0.7854F, 0.0F, -0.4363F));

        PartDefinition right_wing2 = root.addOrReplaceChild("right_wing2", CubeListBuilder.create().texOffs(32, 28).mirror().addBox(-7.0F, -7.0F, 0.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-3.5F, -2.5F, 2.0F, -0.7854F, 0.0F, 0.4363F));

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Whizz entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.flyingAnimationState, WhizzAnimations.FLY, ageInTicks);
        this.animate(entity.attackAnimationState, WhizzAnimations.ATTACK, ageInTicks);
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