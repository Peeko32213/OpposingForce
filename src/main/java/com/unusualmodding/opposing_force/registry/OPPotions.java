package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OPPotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, OpposingForce.MOD_ID);

    public static final RegistryObject<Potion> GLOOM_TOXIN_POTION = POTIONS.register("gloom_toxin", () -> new Potion(new MobEffectInstance(OPEffects.GLOOM_TOXIN.get(), 900)));
    public static final RegistryObject<Potion> LONG_GLOOM_TOXIN_POTION = POTIONS.register("long_gloom_toxin", () -> new Potion(new MobEffectInstance(OPEffects.GLOOM_TOXIN.get(), 1800)));
    public static final RegistryObject<Potion> STRONG_GLOOM_TOXIN_POTION = POTIONS.register("strong_gloom_toxin", () -> new Potion(new MobEffectInstance(OPEffects.GLOOM_TOXIN.get(), 420, 1)));

    public static final RegistryObject<Potion> SLUG_INFESTATION_POTION = POTIONS.register("slug_infestation", () -> new Potion(new MobEffectInstance(OPEffects.SLUG_INFESTATION.get(), 2400)));
}
