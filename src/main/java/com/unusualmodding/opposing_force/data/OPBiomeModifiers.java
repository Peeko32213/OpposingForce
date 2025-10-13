package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.tags.OPBiomeTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.*;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OPBiomeModifiers {

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        addSpawn(context, "dicer", OPBiomeTags.HAS_DICER, new MobSpawnSettings.SpawnerData(OPEntities.DICER.get(), 5, 1, 1));
        addSpawn(context, "frowzy", OPBiomeTags.HAS_FROWZY, new MobSpawnSettings.SpawnerData(OPEntities.FROWZY.get(), 50, 4, 4));
        addSpawn(context, "guzzler", OPBiomeTags.HAS_GUZZLER, new MobSpawnSettings.SpawnerData(OPEntities.GUZZLER.get(), 15, 1, 1));
        addSpawn(context, "pale_spider", OPBiomeTags.HAS_PALE_SPIDER, new MobSpawnSettings.SpawnerData(OPEntities.PALE_SPIDER.get(), 60, 4, 4));
        addSpawn(context, "ramble", OPBiomeTags.HAS_RAMBLE, new MobSpawnSettings.SpawnerData(OPEntities.RAMBLE.get(), 20, 1, 1));
        addSpawn(context, "slug", OPBiomeTags.HAS_SLUG, new MobSpawnSettings.SpawnerData(OPEntities.SLUG.get(), 50, 2, 2));
        addSpawn(context, "terror", OPBiomeTags.HAS_TERROR, new MobSpawnSettings.SpawnerData(OPEntities.TERROR.get(), 20, 1, 1));
        addSpawn(context, "trembler", OPBiomeTags.HAS_TREMBLER, new MobSpawnSettings.SpawnerData(OPEntities.TREMBLER.get(), 25, 1, 1));
        addSpawn(context, "umber_spider", OPBiomeTags.HAS_UMBER_SPIDER, new MobSpawnSettings.SpawnerData(OPEntities.UMBER_SPIDER.get(), 80, 4, 4));
        addSpawn(context, "volt", OPBiomeTags.HAS_VOLT, new MobSpawnSettings.SpawnerData(OPEntities.VOLT.get(), 10, 2, 2));
        removeFeature(context, "amethyst_geode.json", BiomeTags.IS_OVERWORLD, LOCAL_MODIFICATIONS, CavePlacements.AMETHYST_GEODE);
    }

    private static void addSpawn(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, MobSpawnSettings.SpawnerData... spawns) {
        register(context, "add_spawn/" + name, () -> new AddSpawnsBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), List.of(spawns)));
    }

    @SafeVarargs
    private static void addFeature(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, GenerationStep.Decoration step, ResourceKey<PlacedFeature>... features) {
        register(context, "add_feature/" + name, () -> new AddFeaturesBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), featureSet(context, features), step));
    }

    @SafeVarargs
    private static void removeFeature(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, GenerationStep.Decoration step, ResourceKey<PlacedFeature>... features) {
        register(context, "remove_feature/" + name, () -> new RemoveFeaturesBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), featureSet(context, features), Set.of(step)));
    }

    private static void register(BootstapContext<BiomeModifier> context, String name, Supplier<? extends BiomeModifier> modifier) {
        context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(OpposingForce.MOD_ID, name)), modifier.get());
    }

    @SafeVarargs
    private static HolderSet<PlacedFeature> featureSet(BootstapContext<?> context, ResourceKey<PlacedFeature>... features) {
        return HolderSet.direct(Stream.of(features).map(placedFeatureKey -> context.lookup(Registries.PLACED_FEATURE).getOrThrow(placedFeatureKey)).collect(Collectors.toList()));
    }
}
