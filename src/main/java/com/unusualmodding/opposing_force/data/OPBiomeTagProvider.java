package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.tags.OPBiomeTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class OPBiomeTagProvider extends BiomeTagsProvider {

    public OPBiomeTagProvider(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper helper) {
        super(output, provider, OpposingForce.MOD_ID, helper);
    }

    @Override
    public void addTags(Provider provider) {

        this.tag(OPBiomeTags.HAS_DICER).addTag(OPBiomeTags.WITH_DEFAULT_MONSTER_SPAWNS);
        this.tag(OPBiomeTags.HAS_FROWZY).addTag(OPBiomeTags.WITH_DEFAULT_MONSTER_SPAWNS);
        this.tag(OPBiomeTags.HAS_GUZZLER).addTag(OPBiomeTags.WITH_DEFAULT_MONSTER_SPAWNS);
        this.tag(OPBiomeTags.HAS_PALE_SPIDER).addTag(OPBiomeTags.WITH_DEFAULT_MONSTER_SPAWNS);
        this.tag(OPBiomeTags.HAS_RAMBLE).addTag(OPBiomeTags.WITH_DEFAULT_MONSTER_SPAWNS);
        this.tag(OPBiomeTags.HAS_SLUG).addTag(OPBiomeTags.WITH_DEFAULT_MONSTER_SPAWNS);
        this.tag(OPBiomeTags.HAS_TERROR).addTag(OPBiomeTags.WITH_DEFAULT_MONSTER_SPAWNS);
        this.tag(OPBiomeTags.HAS_TREMBLER).addTag(OPBiomeTags.WITH_DEFAULT_MONSTER_SPAWNS);
        this.tag(OPBiomeTags.HAS_UMBER_SPIDER).addTag(OPBiomeTags.WITH_DEFAULT_MONSTER_SPAWNS);
        this.tag(OPBiomeTags.HAS_VOLT).addTag(OPBiomeTags.WITH_DEFAULT_MONSTER_SPAWNS);

        this.tag(OPBiomeTags.HAS_CREAM_CAP).add(Biomes.DARK_FOREST);

        this.tag(OPBiomeTags.WITHOUT_DEFAULT_MONSTER_SPAWNS).add(Biomes.MUSHROOM_FIELDS, Biomes.DEEP_DARK);
        TagAppender<Biome> withMonsterSpawns = this.tag(OPBiomeTags.WITH_DEFAULT_MONSTER_SPAWNS);
        MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD.usedBiomes().forEach((biome) -> {
            if (biome != Biomes.MUSHROOM_FIELDS && biome != Biomes.DEEP_DARK)
                withMonsterSpawns.add(biome);
        });
    }
}