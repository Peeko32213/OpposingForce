package com.unusualmodding.opposing_force.client.models.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class BlasterModel extends HierarchicalItemModel {

    private ModelPart root;
    private ModelPart glow;

	public BlasterModel(ModelPart root) {
		this(RenderType::entityCutoutNoCull);
		this.root = root.getChild("root");
        this.glow = this.root.getChild("glow");
    }

	protected BlasterModel(Function<ResourceLocation, RenderType> renderType) {
		super(renderType);
	}

	public static LayerDefinition createItemLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create()
                .texOffs(0, 10).addBox(1.5F, -8.0F, 6.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 5).addBox(1.5F, -2.0F, 5.0F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.5F, -6.0F, 3.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(8, 15).addBox(1.0F, -3.0F, 8.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 23.0F, -6.0F));

        root.addOrReplaceChild("glow", CubeListBuilder.create()
                .texOffs(18, 10).addBox(0.0F, -5.0F, 1.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0025F))
                .texOffs(0, 18).addBox(0.0F, -5.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0025F))
                .texOffs(14, 15).addBox(0.5F, -4.5F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 10).addBox(1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.5F, -6.0F, 3.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0025F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int overlay, float r, float g, float b, float a) {
		this.root.render(poseStack, vertexConsumer, packedLight, overlay, r, g, b, a);
    }

	@Override
	public void setupAnim(Entity entity, ItemStack stack, float ageInTicks) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
	}

    @Override
	public ModelPart root() {
		return this.root;
	}
}