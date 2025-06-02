package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.effects.ElectrifiedEffect;
import com.unusualmodding.opposing_force.effects.GloomToxinEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OPEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, OpposingForce.MOD_ID);

    public static final RegistryObject<MobEffect> ELECTRIFIED = MOB_EFFECTS.register("electrified", ElectrifiedEffect::new);
    public static final RegistryObject<MobEffect> GLOOM_TOXIN = MOB_EFFECTS.register("gloom_toxin", GloomToxinEffect::new);

}
