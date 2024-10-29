package com.peeko32213.hole.core.datagen;

import com.peeko32213.hole.Hole;
import com.peeko32213.hole.core.registry.HoleBiomeModifiers;
import com.peeko32213.hole.core.registry.HoleDamageTypes;
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
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, HoleBiomeModifiers::bootstrap)
            .add(Registries.DAMAGE_TYPE, HoleDamageTypes::bootstrap)
            ;


    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries,BUILDER, Set.of("minecraft", Hole.MODID));
    }
}
