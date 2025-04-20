package com.unusualmodding.opposingforce.core.data.server;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.core.registry.OPBiomeModifiers;
import com.unusualmodding.opposingforce.core.registry.OPDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, OPBiomeModifiers::bootstrap)
            .add(Registries.DAMAGE_TYPE, OPDamageTypes::bootstrap)
            ;


    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,BUILDER, Set.of("minecraft", OpposingForce.MODID));
    }
}
