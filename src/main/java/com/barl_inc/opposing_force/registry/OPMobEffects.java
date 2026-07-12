package com.barl_inc.opposing_force.registry;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.effects.Electrified;
import com.barl_inc.opposing_force.effects.Energized;
import com.barl_inc.opposing_force.effects.GloomToxin;
import com.barl_inc.opposing_force.effects.SlugInfestation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OPMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, OpposingForce.MOD_ID);

    public static final DeferredHolder<MobEffect, ?> ELECTRIFIED = MOB_EFFECTS.register("electrified", Electrified::new);
    public static final DeferredHolder<MobEffect, ?> ENERGIZED = MOB_EFFECTS.register("energized", Energized::new);
    public static final DeferredHolder<MobEffect, ?> GLOOM_TOXIN = MOB_EFFECTS.register("gloom_toxin", GloomToxin::new);
    public static final DeferredHolder<MobEffect, ?> SLUG_INFESTATION = MOB_EFFECTS.register("slug_infestation", SlugInfestation::new);

}
