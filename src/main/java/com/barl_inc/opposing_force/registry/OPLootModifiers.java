package com.barl_inc.opposing_force.registry;

import com.mojang.serialization.Codec;
import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.loot.modifier.AddLootTableModifier;
import net.neoforged.common.loot.IGlobalLootModifier;
import net.neoforged.registries.DeferredRegister;
import net.neoforged.registries.ForgeRegistries;
import net.neoforged.registries.RegistryObject;

public class OPLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, OpposingForce.MOD_ID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_LOOT_TABLE = LOOT_MODIFIERS.register("add_loot_table", AddLootTableModifier.CODEC);
}
