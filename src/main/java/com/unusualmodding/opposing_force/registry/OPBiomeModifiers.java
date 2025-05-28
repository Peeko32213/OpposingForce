package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.tags.OPTags;
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

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OPBiomeModifiers {

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        addSpawn(context, "dicer", OPTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(OPEntities.DICER.get(), 25, 1, 2));
        addSpawn(context, "pale_spider", OPTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(OPEntities.PALE_SPIDER.get(), 50, 3, 5));
        addSpawn(context, "ramble", OPTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(OPEntities.RAMBLE.get(), 10, 1, 2));
        addSpawn(context, "trembler", OPTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(OPEntities.TREMBLER.get(), 25, 1, 2));
        addSpawn(context, "umber_spider", OPTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(OPEntities.UMBER_SPIDER.get(), 50, 3, 5));
        addSpawn(context, "terror", OPTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(OPEntities.TERROR.get(), 5, 1, 2));
        addSpawn(context, "hopper", OPTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(OPEntities.BOUNCER.get(), 50, 3, 5));
        addSpawn(context, "volt", OPTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(OPEntities.VOLT.get(), 25, 2, 3));
        addSpawn(context, "wizz", OPTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(OPEntities.WHIZZ.get(), 50, 4, 10));
        addSpawn(context, "frowzy", OPTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(OPEntities.FROWZY.get(), 50, 2, 4));
        addSpawn(context, "guzzler", OPTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(OPEntities.GUZZLER.get(), 5, 1, 1));
        addSpawn(context, "slug", OPTags.WITH_DEFAULT_MONSTER_SPAWNS, new MobSpawnSettings.SpawnerData(OPEntities.SLUG.get(), 50, 1, 5));
    }

    private static void addSpawn(BootstapContext<BiomeModifier> context, String name, TagKey<Biome> biomes, MobSpawnSettings.SpawnerData... spawns) {
        register(context, "add_spawn/" + name, () -> new ForgeBiomeModifiers.AddSpawnsBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomes), List.of(spawns)));
    }

    private static void register(BootstapContext<BiomeModifier> context, String name, Supplier<? extends BiomeModifier> modifier) {
        context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(OpposingForce.MOD_ID, name)), modifier.get());
    }

    @SafeVarargs
    private static HolderSet<PlacedFeature> featureSet(BootstapContext<?> context, ResourceKey<PlacedFeature>... features) {
        return HolderSet.direct(Stream.of(features).map(placedFeatureKey -> context.lookup(Registries.PLACED_FEATURE).getOrThrow(placedFeatureKey)).collect(Collectors.toList()));
    }

}
