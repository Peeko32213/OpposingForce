package com.peeko32213.hole.core.datagen;

import com.peeko32213.hole.core.registry.HoleTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class HoleBiomeTagsProvider extends BiomeTagsProvider {

    public HoleBiomeTagsProvider(String modid, PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper fileHelper) {
        super(output, lookupProvider, modid, fileHelper);
    }


    @Override
    public void addTags(Provider provider) {

        this.tag(HoleTags.IS_DEEP_UNDERGROUND).addTag(HoleTags.WITH_DEFAULT_MONSTER_SPAWNS);
        this.tag(HoleTags.IS_UNDERGROUND).addTag(HoleTags.WITH_DEFAULT_MONSTER_SPAWNS);

        this.tag(HoleTags.HAS_DICER)
                .addTag(HoleTags.WITH_DEFAULT_MONSTER_SPAWNS);

        this.tag(HoleTags.HAS_PALE_SPIDER)
                .addTag(HoleTags.WITH_DEFAULT_MONSTER_SPAWNS);

        this.tag(HoleTags.HAS_RAMBLE)
                .addTag(HoleTags.WITH_DEFAULT_MONSTER_SPAWNS);

        this.tag(HoleTags.HAS_TREMBLE)
                .addTag(HoleTags.WITH_DEFAULT_MONSTER_SPAWNS);

        this.tag(HoleTags.HAS_UMBER_SPIDER)
                .addTag(HoleTags.WITH_DEFAULT_MONSTER_SPAWNS);

        TagAppender<Biome> withMonsterSpawns = this.tag(HoleTags.WITH_DEFAULT_MONSTER_SPAWNS);
        MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD.usedBiomes().forEach((biome) -> {
            if (biome != Biomes.MUSHROOM_FIELDS && biome != Biomes.DEEP_DARK)
                withMonsterSpawns.add(biome);
        });

    }
}