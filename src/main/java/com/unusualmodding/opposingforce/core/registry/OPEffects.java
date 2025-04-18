package com.unusualmodding.opposingforce.core.registry;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.common.effect.ElectrifiedEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OPEffects {
    public static final DeferredRegister<MobEffect> EFFECT_DEF_REG = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, OpposingForce.MODID);


    public static final RegistryObject<MobEffect> ELECTRIFIED = EFFECT_DEF_REG.register("electrified", ()-> new ElectrifiedEffect());

}
