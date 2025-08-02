package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OPAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, OpposingForce.MOD_ID);

    public static final RegistryObject<Attribute> STEALTH = ATTRIBUTES.register("stealth", () -> new RangedAttribute("attribute.opposing_force.name.generic.stealth", 0.0D, 0.0D, 1.0D));
    public static final RegistryObject<Attribute> ELECTRIC_RESISTANCE = ATTRIBUTES.register("electric_resistance", () -> new RangedAttribute("attribute.opposing_force.name.generic.electric_resistance", 0.0D, 0.0D, 30.0D));

}
