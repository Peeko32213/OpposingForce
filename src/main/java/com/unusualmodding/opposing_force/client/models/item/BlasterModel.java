package com.unusualmodding.opposing_force.client.models.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("FieldCanBeLocal, unused")
public class BlasterModel extends ItemModelBase {

	private final ModelPart blaster;
	private final ModelPart glow;

	public BlasterModel(ModelPart root) {
		this.blaster = root.getChild("blaster");
		this.glow = this.blaster.getChild("glow");
	}

	public static LayerDefinition createBlaster() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition blaster = partdefinition.addOrReplaceChild("blaster", CubeListBuilder.create().texOffs(0, 0).addBox(0.98F, -5.02F, 2.98F, 2.04F, 3.04F, 9.04F, new CubeDeformation(0.0F))
		.texOffs(8, 19).addBox(1.0F, -2.0F, 10.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 19).addBox(1.49F, -2.01F, 5.99F, 0.02F, 3.02F, 4.02F, new CubeDeformation(0.0F))
		.texOffs(18, 12).addBox(1.5F, -3.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(16, 19).addBox(0.5F, -4.0F, -4.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 19).addBox(0.5F, -4.0F, -2.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 0).addBox(1.99F, -7.01F, 2.99F, 0.02F, 2.02F, 3.02F, new CubeDeformation(0.0F))
		.texOffs(22, 5).addBox(1.99F, -7.01F, 7.99F, 0.02F, 2.02F, 3.02F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 24.0F, -4.0F));

		PartDefinition glow = blaster.addOrReplaceChild("glow", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, 3.0F, -2.0F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -8.0F, 4.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	public void setupAnim(float limbSwing, float y, float x) {
        blaster.xRot += (limbSwing * 0.3F) * (float) Math.toRadians(-20);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.blaster.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}