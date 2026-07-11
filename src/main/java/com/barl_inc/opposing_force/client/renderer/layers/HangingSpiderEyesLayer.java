package com.barl_inc.opposing_force.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.client.models.entity.HangingSpiderModel;
import com.barl_inc.opposing_force.entity.HangingSpider;
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
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class HangingSpiderEyesLayer extends RenderLayer<HangingSpider, HangingSpiderModel> {

    private static final ResourceLocation EYES = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/hanging_spider/hanging_spider_eyes.png");

    private final AlphaFunction<HangingSpider> alphaFunction;
    private final DrawSelector<HangingSpider, HangingSpiderModel> drawSelector;

    public HangingSpiderEyesLayer(RenderLayerParent<HangingSpider, HangingSpiderModel> parent, AlphaFunction<HangingSpider> alpha, DrawSelector<HangingSpider, HangingSpiderModel> drawSelector) {
        super(parent);
        this.alphaFunction = alpha;
        this.drawSelector = drawSelector;
    }

    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int i, HangingSpider entity, float v, float v1, float v2, float v3, float v4, float v5) {
        if (!entity.isInvisible()) {
            this.onlyDrawSelectedParts();
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityTranslucentEmissive(EYES));
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
        this.getParentModel().root().getAllParts().forEach((modelPart) -> modelPart.skipDraw = false);
    }

    @OnlyIn(Dist.CLIENT)
    public interface AlphaFunction<T extends HangingSpider> {
        float apply(T entity, float f, float v);
    }

    @OnlyIn(Dist.CLIENT)
    public interface DrawSelector<T extends HangingSpider, M extends EntityModel<T>> {
        List<ModelPart> getPartsToDraw(M model);
    }
}