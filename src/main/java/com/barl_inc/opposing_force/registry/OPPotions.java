package com.barl_inc.opposing_force.registry;

import com.barl_inc.opposing_force.OpposingForce;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class OPPotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(BuiltInRegistries.POTION, OpposingForce.MOD_ID);

    public static final DeferredHolder<Potion, ?> GLOOM_TOXIN_POTION = POTIONS.register("gloom_toxin", () -> new Potion(new MobEffectInstance(OPMobEffects.GLOOM_TOXIN.getDelegate(), 900)));
    public static final DeferredHolder<Potion, ?> LONG_GLOOM_TOXIN_POTION = POTIONS.register("long_gloom_toxin", () -> new Potion(new MobEffectInstance(OPMobEffects.GLOOM_TOXIN.getDelegate(), 1800)));
    public static final DeferredHolder<Potion, ?> STRONG_GLOOM_TOXIN_POTION = POTIONS.register("strong_gloom_toxin", () -> new Potion(new MobEffectInstance(OPMobEffects.GLOOM_TOXIN.getDelegate(), 420, 1)));

    public static final DeferredHolder<Potion, ?> SLUG_INFESTATION_POTION = POTIONS.register("slug_infestation", () -> new Potion(new MobEffectInstance(OPMobEffects.SLUG_INFESTATION.getDelegate(), 2400)));
}
