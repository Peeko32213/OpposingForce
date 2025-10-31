package com.unusualmodding.opposing_force.client.models.item;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;

public abstract class ItemModelBase extends Model {
    public ItemModelBase() {
        super(RenderType::entityCutoutNoCull);
    }

    public abstract void setupAnim(float x, float y, float z);
}
