package com.unusualmodding.opposing_force.client.renderer.layer;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.entity.VoltModel;
import com.unusualmodding.opposing_force.entity.Volt;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VoltChargedLayer extends EnergySwirlLayer<Volt, VoltModel<Volt>> {

    private static final ResourceLocation CHARGED_TEXTURE = new ResourceLocation(OpposingForce.MOD_ID, "textures/entity/volt/charged.png");

    private final VoltModel<Volt> model;

    public VoltChargedLayer(RenderLayerParent<Volt, VoltModel<Volt>> parentModel, EntityModelSet modelSet) {
        super(parentModel);
        this.model = new VoltModel<>(modelSet.bakeLayer(OPModelLayers.VOLT_CHARGED_LAYER));
    }

    protected float xOffset(float offset) {
        return offset * 0.01F;
    }

    protected ResourceLocation getTextureLocation() {
        return CHARGED_TEXTURE;
    }

    protected EntityModel<Volt> model() {
        return this.model;
    }
}
