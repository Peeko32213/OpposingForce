package com.unusualmodding.opposing_force.client.models.mob_heads;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;

public abstract class OPMobModelBase extends Model {
    public OPMobModelBase() {
        super(RenderType::entityTranslucent);
    }

    public abstract void setupAnim(float p_170950_, float p_170951_, float p_170952_);
}
