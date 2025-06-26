package com.unusualmodding.opposing_force.registry;

import com.google.gson.JsonObject;
import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class OPCriteriaTriggers extends SimpleCriterionTrigger<OPCriteriaTriggers.TriggerInstance> {

    private final ResourceLocation CRITERIA;

    public OPCriteriaTriggers(String name) {
        CRITERIA = new ResourceLocation(OpposingForce.MOD_ID, name);
    }

    @Override
    public ResourceLocation getId() {
        return CRITERIA;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, conditions -> true);
    }

    @Override
    protected TriggerInstance createInstance(JsonObject object, ContextAwarePredicate predicate, DeserializationContext context) {
        return new OPCriteriaTriggers.TriggerInstance(CRITERIA, predicate);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ResourceLocation id, ContextAwarePredicate predicate) {
            super(id, predicate);
        }
    }
}