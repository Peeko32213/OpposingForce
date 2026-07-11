package com.barl_inc.opposing_force.events;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;

@OnlyIn(Dist.CLIENT)
@Event.HasResult
public class PoseHandEvent extends Event {

    private final LivingEntity entity;
    private final HumanoidModel<?> model;
    private final boolean leftHand;

    public PoseHandEvent(LivingEntity entityIn, HumanoidModel<?> model, boolean left) {
        this.entity = entityIn;
        this.model = model;
        this.leftHand = left;
    }

    public Entity getEntity() {
        return entity;
    }

    public HumanoidModel<?> getModel() {
        return model;
    }

    public boolean isLeftHand() {
        return leftHand;
    }
}
