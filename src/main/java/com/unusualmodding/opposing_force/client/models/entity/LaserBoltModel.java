package com.unusualmodding.opposing_force.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.entity.projectile.LaserBolt;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class LaserBoltModel extends HierarchicalModel<LaserBolt> {

	private final ModelPart root;

	public LaserBoltModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static LayerDefinition createProjectileLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.ZERO);

		root.addOrReplaceChild("outer", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -3.0F, -4.5F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		root.addOrReplaceChild("inner", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -2.5F, -4.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(LaserBolt entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	public void setupRotation(float yRot, float xRot) {
		this.root.yRot = yRot * ((float) Math.PI / 180F);
		this.root.xRot = xRot * ((float) Math.PI / 180F);
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