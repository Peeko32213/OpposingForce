package com.unusualmodding.opposing_force.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static com.unusualmodding.opposing_force.OpposingForce.MOD_ID;

public class OPWorldGen {

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_BLACKCAP = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_blackcap"));
    public static ResourceKey<PlacedFeature> PATCH_BLACKCAP = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_blackcap"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_BLUE_TRUMPET = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_blue_trumpet"));
    public static ResourceKey<PlacedFeature> PATCH_BLUE_TRUMPET = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_blue_trumpet"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_CAP_OF_EYE = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_cap_of_eye"));
    public static ResourceKey<PlacedFeature> PATCH_CAP_OF_EYE = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_cap_of_eye"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_CAVE_PATTY = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_cave_patty"));
    public static ResourceKey<PlacedFeature> PATCH_CAVE_PATTY = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_cave_patty"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_CHICKEN_OF_THE_CAVES = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_chicken_of_the_caves"));
    public static ResourceKey<PlacedFeature> PATCH_CHICKEN_OF_THE_CAVES = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_chicken_of_the_caves"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_COPPER_ENOKI = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_copper_enoki"));
    public static ResourceKey<PlacedFeature> PATCH_COPPER_ENOKI = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_copper_enoki"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_CREAM_CAP = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_cream_cap"));
    public static ResourceKey<PlacedFeature> PATCH_CREAM_CAP = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_cream_cap"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_GREEN_FUNK = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_green_funk"));
    public static ResourceKey<PlacedFeature> PATCH_GREEN_FUNK = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_green_funk"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_LIME_NUB = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_lime_nub"));
    public static ResourceKey<PlacedFeature> PATCH_LIME_NUB = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_lime_nub"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_POP_CAP = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_pop_cap"));
    public static ResourceKey<PlacedFeature> PATCH_POP_CAP = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_pop_cap"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_POWDER_GNOME = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_powder_gnome"));
    public static ResourceKey<PlacedFeature> PATCH_POWDER_GNOME = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_powder_gnome"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_PRINCESS_JELLY = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_princess_jelly"));
    public static ResourceKey<PlacedFeature> PATCH_PRINCESS_JELLY = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_princess_jelly"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_PURPLE_KNOB = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_purple_knob"));
    public static ResourceKey<PlacedFeature> PATCH_PURPLE_KNOB = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_purple_knob"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_QUEEN_IN_PURPLE = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_purple_queen"));
    public static ResourceKey<PlacedFeature> PATCH_QUEEN_IN_PURPLE = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_purple_queen"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_RAINCAP = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_raincap"));
    public static ResourceKey<PlacedFeature> PATCH_RAINCAP = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_raincap"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_SLATESHROOM = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_slateshroom"));
    public static ResourceKey<PlacedFeature> PATCH_SLATESHROOM = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_slateshroom"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_SLIPPERY_TOP = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_slippery_top"));
    public static ResourceKey<PlacedFeature> PATCH_SLIPPERY_TOP = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_slippery_top"));

    public static ResourceKey<ConfiguredFeature<?, ?>> FEATURE_PATCH_WHITECAP = ResourceKey.create(Registries.CONFIGURED_FEATURE,registerFeature("patch_whitecap"));
    public static ResourceKey<PlacedFeature> PATCH_WHITECAP = ResourceKey.create(Registries.PLACED_FEATURE, registerFeature("patch_whitecap"));


    public static void register() {

    }

    public static ResourceLocation registerFeature(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
