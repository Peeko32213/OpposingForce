package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID)
public class OPCriterion {

    public static final OPCriteriaTriggers TAME_SLUG = CriteriaTriggers.register(new OPCriteriaTriggers("tame_slug"));

}
