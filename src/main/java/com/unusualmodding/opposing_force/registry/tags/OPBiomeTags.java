package com.unusualmodding.opposing_force.registry.tags;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class OPBiomeTags {

    public static final TagKey<Biome> HAS_DICER = modBiomeTag("has_monster/dicer");
    public static final TagKey<Biome> HAS_FROWZY = modBiomeTag("has_monster/frowzy");
    public static final TagKey<Biome> HAS_GUZZLER = modBiomeTag("has_monster/guzzler");
    public static final TagKey<Biome> HAS_PALE_SPIDER = modBiomeTag("has_monster/pale_spider");
    public static final TagKey<Biome> HAS_RAMBLE = modBiomeTag("has_monster/ramble");
    public static final TagKey<Biome> HAS_SLUG = modBiomeTag("has_monster/slug");
    public static final TagKey<Biome> HAS_TERROR = modBiomeTag("has_monster/terror");
    public static final TagKey<Biome> HAS_TREMBLER = modBiomeTag("has_monster/trembler");
    public static final TagKey<Biome> HAS_UMBER_SPIDER = modBiomeTag("has_monster/umber_spider");
    public static final TagKey<Biome> HAS_VOLT = modBiomeTag("has_monster/volt");

    public static final TagKey<Biome> HAS_CREAM_CAP = modBiomeTag("has_feature/cream_cap");

    public static final TagKey<Biome> WITH_DEFAULT_MONSTER_SPAWNS = forgeBiomeTag("with_default_monster_spawns");
    public static final TagKey<Biome> WITHOUT_DEFAULT_MONSTER_SPAWNS = forgeBiomeTag("without_default_monster_spawns");

    private static TagKey<Biome> modBiomeTag(String name) {
        return biomeTag(OpposingForce.MOD_ID, name);
    }

    private static TagKey<Biome> forgeBiomeTag(String name) {
        return biomeTag("forge", name);
    }

    public static TagKey<Biome> biomeTag(String modid, String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(modid, name));
    }
}
