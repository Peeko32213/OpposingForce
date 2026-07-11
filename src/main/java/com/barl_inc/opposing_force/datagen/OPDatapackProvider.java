package com.barl_inc.opposing_force.datagen;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.registry.OPDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class OPDatapackProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, OPBiomeModifierProvider::bootstrap)
            .add(Registries.DAMAGE_TYPE, OPDamageTypes::bootstrap);

    public OPDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,BUILDER, Set.of(OpposingForce.MOD_ID));
    }
}
