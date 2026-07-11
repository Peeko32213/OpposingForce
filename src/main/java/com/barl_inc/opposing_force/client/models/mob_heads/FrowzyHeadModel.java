package com.barl_inc.opposing_force.client.models.mob_heads;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class FrowzyHeadModel extends MobHeadModelBase {

	private final ModelPart root;
	private final ModelPart head;

	public FrowzyHeadModel(ModelPart root) {
		this.root = root;
		this.head = this.root.getChild("head");
	}

	public static LayerDefinition createHeadLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(2, 19).addBox(-3.5F, -5.5F, -3.75F, 7.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(float limbSwing, float headY, float headX) {
		this.head.yRot = headY * ((float) Math.PI / 180F);
		this.head.xRot = headX * ((float) Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}