package com.unusualmodding.opposing_force.client.models.mob_heads;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DicerHeadModel extends OPMobModelBase {
    private final ModelPart head;

    public DicerHeadModel(ModelPart root) {
        this.head = root.getChild("Head");
    }

    public static LayerDefinition createHeadLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, 18.225F, 3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.3F))
                .texOffs(0, 16).addBox(-12.0F, 18.025F, 3.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(32, 16).addBox(-9.0F, 23.025F, 1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-9.0F, 14.025F, 2.0F, 2.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-13.0F, 20.025F, 2.0F, 10.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -2.0F, -7.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void setupAnim(float p_104188_, float p_104189_, float p_104190_) {
        this.head.yRot = p_104189_ * ((float)Math.PI / 180F);
        this.head.xRot = p_104190_ * ((float)Math.PI / 180F);
    }

    public void renderToBuffer(PoseStack p_104192_, VertexConsumer p_104193_, int p_104194_, int p_104195_, float p_104196_, float p_104197_, float p_104198_, float p_104199_) {
        p_104192_.pushPose();
        p_104192_.translate(0.0F, -0.374375F, 0.0F);
        p_104192_.scale(1.0F, 1.0F, 1.0F);
        this.head.render(p_104192_, p_104193_, p_104194_, p_104195_, p_104196_, p_104197_, p_104198_, p_104199_);
        p_104192_.popPose();
    }
}