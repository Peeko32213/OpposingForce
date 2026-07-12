package com.barl_inc.opposing_force.registry;

import com.barl_inc.opposing_force.OpposingForce;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraftforge.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OPCriterion {
    public static DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, OpposingForce.MOD_ID);

    public static final DeferredHolder<CriterionTrigger<?>, OPCriteriaTriggers> TAME_SLUG = TRIGGERS.register("tame_slug", () ->new OPCriteriaTriggers("tame_slug"));

    public static final DeferredHolder<CriterionTrigger<?>, OPCriteriaTriggers>  PARRY_WARDEN_WITH_LASER_BLADE = TRIGGERS.register("parry_warden_with_laser_blade", () -> new OPCriteriaTriggers("parry_warden_with_laser_blade"));


}
