package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.effects.Electrified;
import com.unusualmodding.opposing_force.effects.GloomToxin;
import com.unusualmodding.opposing_force.effects.SlugInfestation;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OPMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, OpposingForce.MOD_ID);

    public static final RegistryObject<MobEffect> ELECTRIFIED = MOB_EFFECTS.register("electrified", Electrified::new);
    public static final RegistryObject<MobEffect> GLOOM_TOXIN = MOB_EFFECTS.register("gloom_toxin", GloomToxin::new);
    public static final RegistryObject<MobEffect> SLUG_INFESTATION = MOB_EFFECTS.register("slug_infestation", SlugInfestation::new);

}
