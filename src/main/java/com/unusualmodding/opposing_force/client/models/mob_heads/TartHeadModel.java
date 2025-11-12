package com.unusualmodding.opposing_force.client.models.mob_heads;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TartHeadModel extends MobHeadModelBase {

	private final ModelPart root;
	private final ModelPart head;

	public TartHeadModel(ModelPart root) {
		this.root = root;
		this.head = this.root.getChild("head");
	}

	public static LayerDefinition createHeadLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.5F, -7.0F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-1.0F, -10.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        partdefinition.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(6, 14).addBox(-3.0F, -2.9F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -7.85F, 0.0F, -0.829F, -0.6981F, 0.6109F));

        return LayerDefinition.create(meshdefinition, 32, 32);
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