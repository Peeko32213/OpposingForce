package com.barl_inc.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.barl_inc.opposing_force.client.animations.TartAnimations;
import com.barl_inc.opposing_force.client.models.entity.base.OPModel;
import com.barl_inc.opposing_force.entity.Tart;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class TartModel extends OPModel<Tart> {

    private final ModelPart root;
    private final ModelPart body_main;
    private final ModelPart body;
    private final ModelPart stem;
    private final ModelPart leaf;
    private final ModelPart leg_control;
    private final ModelPart leg1;
    private final ModelPart leg2;

	public TartModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body_main = this.root.getChild("body_main");
        this.body = this.body_main.getChild("body");
        this.stem = this.body.getChild("stem");
        this.leaf = this.stem.getChild("leaf");
        this.leg_control = this.body_main.getChild("leg_control");
        this.leg1 = this.leg_control.getChild("leg1");
        this.leg2 = this.leg_control.getChild("leg2");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body_main = root.addOrReplaceChild("body_main", CubeListBuilder.create(), PartPose.offset(0.0F, -3.1F, 0.0F));

        PartDefinition body = body_main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, 0.0F));

        PartDefinition stem = body.addOrReplaceChild("stem", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -3.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, 0.0F));

        PartDefinition leaf = stem.addOrReplaceChild("leaf", CubeListBuilder.create(), PartPose.offset(0.5F, -0.75F, 0.0F));

        PartDefinition cube_r1 = leaf.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(6, 14).addBox(-3.0F, -3.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.829F, -0.6981F, 0.6109F));

        PartDefinition leg_control = body_main.addOrReplaceChild("leg_control", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leg1 = leg_control.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(12, 14).addBox(-0.5F, 3.0F, -2.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 16).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 0.0F, 0.0F));

        PartDefinition leg2 = leg_control.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(12, 14).mirror().addBox(-0.5F, 3.0F, -2.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(12, 16).mirror().addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.5F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(Tart entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animateWalk(TartAnimations.WALK, limbSwing, limbSwingAmount, 4, 8);
		this.animateIdle(entity.idleAnimationState, TartAnimations.IDLE, ageInTicks, 1, limbSwingAmount * 4);
        this.animate(entity.attackAnimationState, TartAnimations.ATTACK, ageInTicks);
        this.animate(entity.sitAnimationState, TartAnimations.SIT, ageInTicks);
        this.animate(entity.fallAnimationState, TartAnimations.FALL, ageInTicks);
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