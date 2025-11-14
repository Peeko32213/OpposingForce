package com.unusualmodding.opposing_force.client.models.entity.skyvern;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.animations.SkyvernAnimations;
import com.unusualmodding.opposing_force.entity.Skyvern;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class SkyvernModel extends HierarchicalModel<Skyvern> {

    private final ModelPart root;
    private final ModelPart roll_control;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart tongue;
    private final ModelPart mane;

	public SkyvernModel(ModelPart root) {
        this.root = root.getChild("root");
        this.roll_control = this.root.getChild("roll_control");
        this.head = this.roll_control.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.tongue = this.jaw.getChild("tongue");
        this.mane = this.head.getChild("mane");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition roll_control = root.addOrReplaceChild("roll_control", CubeListBuilder.create(), PartPose.offset(0.0F, -6.5F, 0.0F));

        PartDefinition head = roll_control.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 141).addBox(-2.5F, -1.0F, -19.0F, 5.0F, 5.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(128, 38).addBox(-4.5F, -1.0F, -1.0F, 9.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(152, 16).addBox(1.5F, -3.0F, -5.0F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(152, 16).mirror().addBox(-5.5F, -3.0F, -5.0F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 4.5F, -6.0F));

        PartDefinition brow_r1 = head.addOrReplaceChild("brow_r1", CubeListBuilder.create().texOffs(152, 27).mirror().addBox(0.0F, -4.0F, -5.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offsetAndRotation(-1.5F, -3.0F, 1.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition brow_r2 = head.addOrReplaceChild("brow_r2", CubeListBuilder.create().texOffs(152, 27).addBox(0.0F, -4.0F, -5.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offsetAndRotation(1.5F, -3.0F, 1.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(98, 38).addBox(-4.5F, 0.0F, -6.0F, 9.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(46, 148).addBox(-2.5F, 3.0F, -24.0F, 5.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 5.0F));

        PartDefinition tongue = jaw.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(38, 148).addBox(-1.5F, 0.0F, -15.0F, 3.0F, 0.0F, 18.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 3.0F, -6.0F));

        PartDefinition mane = head.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(0, 97).addBox(-11.5F, -12.5051F, -7.8571F, 23.0F, 26.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(82, 97).addBox(11.49F, -14.5051F, -9.8571F, 0.0F, 28.0F, 23.0F, new CubeDeformation(0.0025F))
                .texOffs(82, 97).mirror().addBox(-11.49F, -14.5051F, -9.8571F, 0.0F, 28.0F, 23.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(71, 106).mirror().addBox(-11.5F, -12.5051F, 10.1429F, 23.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false)
                .texOffs(71, 106).mirror().addBox(-11.5F, 13.4949F, 10.1429F, 23.0F, 0.0F, 3.0F, new CubeDeformation(0.0025F)).mirror(false), PartPose.offset(0.0F, -4.4949F, 12.8571F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(Skyvern entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.flyingAnimationState, SkyvernAnimations.HEAD_FLY, ageInTicks);
        this.animate(entity.attackStartAnimationState, SkyvernAnimations.HEAD_ATTACK_START, ageInTicks);
        this.animate(entity.attackingAnimationState, SkyvernAnimations.HEAD_ATTACK, ageInTicks);
        this.animate(entity.attackEndAnimationState, SkyvernAnimations.HEAD_ATTACK_END, ageInTicks);
        this.animate(entity.roarAnimationState, SkyvernAnimations.ROAR, ageInTicks);
        this.animate(entity.roll1AnimationState, SkyvernAnimations.HEAD_LOOP1, ageInTicks);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, consumer, packedLight, packedOverlay);
    }

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}