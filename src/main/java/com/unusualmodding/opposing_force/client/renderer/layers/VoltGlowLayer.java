package com.unusualmodding.opposing_force.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.models.entity.VoltModel;
import com.unusualmodding.opposing_force.entity.Volt;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class VoltGlowLayer extends RenderLayer<Volt, VoltModel> {

    private final ResourceLocation texture;
    private final AlphaFunction<Volt> alphaFunction;
    private final DrawSelector<Volt, VoltModel> drawSelector;

    public VoltGlowLayer(RenderLayerParent<Volt, VoltModel> parent, ResourceLocation resourceLocation, AlphaFunction<Volt> alpha, DrawSelector<Volt, VoltModel> drawSelector) {
        super(parent);
        this.texture = resourceLocation;
        this.alphaFunction = alpha;
        this.drawSelector = drawSelector;
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int i, Volt entity, float v, float v1, float v2, float v3, float v4, float v5) {
        if (!entity.isInvisible()) {
            this.onlyDrawSelectedParts();
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(this.texture));
            this.getParentModel().renderToBuffer(poseStack, vertexconsumer, i, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, this.alphaFunction.apply(entity, v2, v3));
            this.resetDrawForAllParts();
        }
    }

    private void onlyDrawSelectedParts() {
        List<ModelPart> list = this.drawSelector.getPartsToDraw(this.getParentModel());
        this.getParentModel().root().getAllParts().forEach((modelPart) -> modelPart.skipDraw = true);
        list.forEach((modelPart) -> modelPart.skipDraw = false);
    }

    private void resetDrawForAllParts() {
        this.getParentModel().root().getAllParts().forEach((modelPart) -> {
            modelPart.skipDraw = false;
        });
    }

    @OnlyIn(Dist.CLIENT)
    public interface AlphaFunction<T extends Volt> {
        float apply(T entity, float p_234921_, float p_234922_);
    }

    @OnlyIn(Dist.CLIENT)
    public interface DrawSelector<T extends Volt, M extends EntityModel<T>> {
        List<ModelPart> getPartsToDraw(M model);
    }
}


