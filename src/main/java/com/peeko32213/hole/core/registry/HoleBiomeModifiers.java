package com.peeko32213.hole.core.registry;

import com.peeko32213.hole.Hole;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = Hole.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HoleBiomeModifiers {

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        addSpawn(context, "dicer", HoleTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(HoleEntities.DICER.get(), 25, 1, 2));
        addSpawn(context, "pale_spider", HoleTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(HoleEntities.PALE_SPIDER.get(), 50, 3, 5));
        addSpawn(context, "ramble", HoleTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(HoleEntities.RAMBLE.get(), 10, 1, 2));
        addSpawn(context, "trembler", HoleTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(HoleEntities.TREMBLER.get(), 25, 1, 2));
        addSpawn(context, "umber_spider", HoleTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(HoleEntities.UMBER_SPIDER.get(), 50, 3, 5));
        addSpawn(context, "terror", HoleTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(HoleEntities.TERROR.get(), 5, 1, 2));
        addSpawn(context, "hopper", HoleTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(HoleEntities.HOPPER.get(), 50, 3, 5));
        addSpawn(context, "volt", HoleTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(HoleEntities.VOLT.get(), 25, 2, 3));

    }



    private static void addSpawn(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, MobSpawnSettings.SpawnerData... spawns) {
        register(context, "add_spawn/" + name, () -> new ForgeBiomeModifiers.AddSpawnsBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), List.of(spawns)));
    }

    private static void register(BootstapContext<BiomeModifier> context, String name, Supplier<? extends BiomeModifier> modifier) {
        context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Hole.MODID, name)), modifier.get());
    }

    @SafeVarargs
    private static HolderSet<PlacedFeature> featureSet(BootstapContext<?> context, ResourceKey<PlacedFeature>... features) {
        return HolderSet.direct(Stream.of(features).map(placedFeatureKey -> context.lookup(Registries.PLACED_FEATURE).getOrThrow(placedFeatureKey)).collect(Collectors.toList()));
    }

}
