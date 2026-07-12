package com.barl_inc.opposing_force.registry;

import com.google.gson.JsonObject;
import com.barl_inc.opposing_force.OpposingForce;
import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.CriterionValidator;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class OPCriteriaTriggers extends SimpleCriterionTrigger<OPCriteriaTriggers.TriggerInstance> {

    private final ResourceLocation CRITERIA;

    public OPCriteriaTriggers(String name) {
        CRITERIA = ResourceLocation.fromNamespaceAndPath(OpposingForce.MOD_ID, name);
    }

    public @NotNull ResourceLocation getId() {
        return CRITERIA;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, conditions -> true);
    }


    @Override
    public Codec<TriggerInstance> codec() {
        return Codec.unit(new TriggerInstance());
    }

    public static class TriggerInstance implements SimpleCriterionTrigger.SimpleInstance {


        @Override
        public void validate(CriterionValidator p_312329_) {
            SimpleInstance.super.validate(p_312329_);
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return Optional.empty();
        }
    }
}