package com.unusualmodding.opposing_force.criterion;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPCriteriaTriggers;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID)
public class OPCriterion {

    public static final OPCriteriaTriggers TAME_SLUG = CriteriaTriggers.register(new OPCriteriaTriggers("tame_slug"));

}
