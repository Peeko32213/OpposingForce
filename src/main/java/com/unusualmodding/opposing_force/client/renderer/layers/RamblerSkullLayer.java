package com.unusualmodding.opposing_force.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.models.entity.RamblerModel;
import com.unusualmodding.opposing_force.entity.Rambler;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static com.unusualmodding.opposing_force.OpposingForce.modPrefix;

@OnlyIn(Dist.CLIENT)
public class RamblerSkullLayer extends RenderLayer<Rambler, RamblerModel> {

    private final RamblerModel model;

    public RamblerSkullLayer(RenderLayerParent<Rambler, RamblerModel> parentModel) {
        super(parentModel);
        this.model = parentModel.getModel();
    }

    @Override
    public void render(@NotNull PoseStack poseStack, MultiBufferSource multiBufferSource, int i, @NotNull Rambler rambler, float f, float g, float h, float j, float k, float l) {
        if (!rambler.isInvisible()) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(rambler, f, g, h);
            this.model.setupAnim(rambler, f, g, j, k, l);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(this.getSkullTextureLocation(rambler)));
            this.model.renderToBuffer(poseStack, vertexConsumer, i, getOverlayCoords(rambler, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public ResourceLocation getSkullTextureLocation(Rambler rambler) {
        return modPrefix("textures/entity/rambler/skulls/rambler_skull1.png");
    }

    public static int getOverlayCoords(Rambler rambler, float f) {
        return OverlayTexture.pack(OverlayTexture.u(f), OverlayTexture.v(rambler.hurtTime > 0 || rambler.deathTime > 0));
    }
}
