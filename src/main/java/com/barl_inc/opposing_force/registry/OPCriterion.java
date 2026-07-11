package com.barl_inc.opposing_force.registry;

import com.barl_inc.opposing_force.OpposingForce;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID)
public class OPCriterion {

    public static final OPCriteriaTriggers TAME_SLUG = CriteriaTriggers.register(new OPCriteriaTriggers("tame_slug"));

    public static final OPCriteriaTriggers PARRY_WARDEN_WITH_LASER_BLADE = CriteriaTriggers.register(new OPCriteriaTriggers("parry_warden_with_laser_blade"));


}
