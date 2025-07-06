package com.unusualmodding.opposing_force.client.models.mob_heads;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;

public abstract class MobHeadModelBase extends Model {
    public MobHeadModelBase() {
        super(RenderType::entityTranslucent);
    }

    public abstract void setupAnim(float x, float y, float z);
}
