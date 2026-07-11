package com.barl_inc.opposing_force.client.models.entity.skyvern;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.barl_inc.opposing_force.client.animations.SkyvernAnimations;
import com.barl_inc.opposing_force.client.models.entity.base.OPModel;
import com.barl_inc.opposing_force.entity.SkyvernSegment;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class SkyvernTailModel extends OPModel<SkyvernSegment> {

    private final ModelPart root;
    private final ModelPart roll_control;
    private final ModelPart tail;
    private final ModelPart tail_tip;
    private final ModelPart tail_fin;

	public SkyvernTailModel(ModelPart root) {
        this.root = root.getChild("root");
        this.roll_control = this.root.getChild("roll_control");
        this.tail = this.roll_control.getChild("tail");
        this.tail_tip = this.tail.getChild("tail_tip");
        this.tail_fin = this.tail_tip.getChild("tail_fin");
	}

	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition roll_control = root.addOrReplaceChild("roll_control", CubeListBuilder.create(), PartPose.offset(0.0F, -6.5F, 0.0F));

        PartDefinition tail = roll_control.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -4.0F, 0.0F, 7.0F, 8.0F, 42.0F, new CubeDeformation(0.0F))
                .texOffs(92, 50).addBox(0.0F, -9.0F, 0.0F, 0.0F, 5.0F, 42.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 2.5F, -7.0F));

        PartDefinition tail_tip = tail.addOrReplaceChild("tail_tip", CubeListBuilder.create().texOffs(0, 50).addBox(-1.5F, -1.8333F, 0.6667F, 3.0F, 4.0F, 43.0F, new CubeDeformation(0.0F))
                .texOffs(128, 97).addBox(0.0F, -4.8333F, 13.6667F, 0.0F, 10.0F, 24.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 1.8333F, 41.3333F));

        PartDefinition tail_fin = tail_tip.addOrReplaceChild("tail_fin", CubeListBuilder.create().texOffs(98, 0).addBox(1.0F, -6.0F, 0.0F, 0.0F, 11.0F, 27.0F, new CubeDeformation(0.0025F)), PartPose.offset(-1.0F, 0.1667F, 37.6667F));

        return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(SkyvernSegment entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animateSmooth(entity.fly1AnimationState, SkyvernAnimations.TAIL_FLY, ageInTicks);
        this.animateSmooth(entity.attackAnimationState, SkyvernAnimations.TAIL_ATTACK, ageInTicks);
        this.animate(entity.rollAnimationState, SkyvernAnimations.TAIL_LOOP1, ageInTicks);
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