package com.barl_inc.opposing_force.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.barl_inc.opposing_force.client.models.entity.RamblerModel;
import com.barl_inc.opposing_force.entity.Rambler;
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

import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class RamblerSkullLayer extends RenderLayer<Rambler, RamblerModel> {

    private final DrawSelector<Rambler, RamblerModel> drawSelector;
    private final Function<Rambler, ResourceLocation> texture;

    public RamblerSkullLayer(RenderLayerParent<Rambler, RamblerModel> parentModel, DrawSelector<Rambler, RamblerModel> drawSelector, Function<Rambler, ResourceLocation> texture) {
        super(parentModel);
        this.drawSelector = drawSelector;
        this.texture = texture;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int i, @NotNull Rambler rambler, float f, float g, float h, float j, float k, float l) {
        if (!rambler.isInvisible()) {
            this.onlyDrawSelectedPart();
            VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entityTranslucent(texture.apply(rambler)));
            this.getParentModel().renderToBuffer(poseStack, vertexconsumer, i, LivingEntityRenderer.getOverlayCoords(rambler, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
            this.resetDrawForAllParts();
        }
    }

    private void onlyDrawSelectedPart() {
        ModelPart modelPart = this.drawSelector.getPartToDraw(this.getParentModel());
        this.getParentModel().root().getAllParts().forEach((modelPart1) -> modelPart1.skipDraw = true);
        modelPart.skipDraw = false;
    }

    private void resetDrawForAllParts() {
        this.getParentModel().root().getAllParts().forEach((modelPart) -> modelPart.skipDraw = false);
    }

    @OnlyIn(Dist.CLIENT)
    public interface DrawSelector<T extends Rambler, M extends EntityModel<T>> {
        ModelPart getPartToDraw(M model);
    }
}
