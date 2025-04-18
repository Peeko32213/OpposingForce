package com.unusualmodding.opposingforce.core.datagen.server;

import com.unusualmodding.opposingforce.core.registry.OPTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class OPBiomeTagsProvider extends BiomeTagsProvider {

    public OPBiomeTagsProvider(String modid, PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper) {
        super(output, lookupProvider, modid, fileHelper);
    }


    @Override
    public void addTags(Provider provider) {

        this.tag(OPTags.IS_DEEP_UNDERGROUND).addTag(OPTags.WITH_DEFAULT_MONSTER_SPAWNS);
        this.tag(OPTags.IS_UNDERGROUND).addTag(OPTags.WITH_DEFAULT_MONSTER_SPAWNS);

        this.tag(OPTags.HAS_DICER)
                .addTag(OPTags.WITH_DEFAULT_MONSTER_SPAWNS);

        this.tag(OPTags.HAS_PALE_SPIDER)
                .addTag(OPTags.WITH_DEFAULT_MONSTER_SPAWNS);

        this.tag(OPTags.HAS_RAMBLE)
                .addTag(OPTags.WITH_DEFAULT_MONSTER_SPAWNS);

        this.tag(OPTags.HAS_TREMBLE)
                .addTag(OPTags.WITH_DEFAULT_MONSTER_SPAWNS);

        this.tag(OPTags.HAS_UMBER_SPIDER)
                .addTag(OPTags.WITH_DEFAULT_MONSTER_SPAWNS);

        TagAppender<Biome> withMonsterSpawns = this.tag(OPTags.WITH_DEFAULT_MONSTER_SPAWNS);
        MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD.usedBiomes().forEach((biome) -> {
            if (biome != Biomes.MUSHROOM_FIELDS && biome != Biomes.DEEP_DARK)
                withMonsterSpawns.add(biome);
        });

    }
}