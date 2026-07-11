package com.barl_inc.opposing_force.datagen;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.registry.tags.OPBiomeTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class OPBiomeTagProvider extends BiomeTagsProvider {

    public OPBiomeTagProvider(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper helper) {
        super(output, provider, OpposingForce.MOD_ID, helper);
    }

    @Override
    public void addTags(@NotNull Provider provider) {

        this.tag(OPBiomeTags.HAS_DICER).addTag(BiomeTags.IS_OVERWORLD).remove(Biomes.DEEP_DARK, Biomes.MUSHROOM_FIELDS);
        this.tag(OPBiomeTags.HAS_FROWZY).addTag(BiomeTags.IS_OVERWORLD).remove(Biomes.DEEP_DARK, Biomes.MUSHROOM_FIELDS);
        this.tag(OPBiomeTags.HAS_GUZZLER).addTag(BiomeTags.IS_OVERWORLD).remove(Biomes.DEEP_DARK, Biomes.MUSHROOM_FIELDS);
        this.tag(OPBiomeTags.HAS_HANGING_SPIDER).addTag(BiomeTags.IS_OVERWORLD).remove(Biomes.DEEP_DARK, Biomes.MUSHROOM_FIELDS);
        this.tag(OPBiomeTags.HAS_RAMBLE).addTag(BiomeTags.IS_OVERWORLD).remove(Biomes.DEEP_DARK, Biomes.MUSHROOM_FIELDS);
        this.tag(OPBiomeTags.HAS_SLUG).addTag(BiomeTags.IS_OVERWORLD).remove(Biomes.DEEP_DARK, Biomes.MUSHROOM_FIELDS);
        this.tag(OPBiomeTags.HAS_TERROR).addTag(BiomeTags.IS_OVERWORLD).remove(Biomes.DEEP_DARK, Biomes.MUSHROOM_FIELDS);
        this.tag(OPBiomeTags.HAS_TREMBLER).addTag(BiomeTags.IS_OVERWORLD).remove(Biomes.DEEP_DARK, Biomes.MUSHROOM_FIELDS);
        this.tag(OPBiomeTags.HAS_UMBER_SPIDER).addTag(BiomeTags.IS_OVERWORLD).remove(Biomes.DEEP_DARK, Biomes.MUSHROOM_FIELDS);
        this.tag(OPBiomeTags.HAS_VOLT).addTag(BiomeTags.IS_OVERWORLD).remove(Biomes.DEEP_DARK, Biomes.MUSHROOM_FIELDS);

        this.tag(OPBiomeTags.HAS_APPLE_TREES).add(
                Biomes.FOREST,
                Biomes.FLOWER_FOREST
        );

        this.tag(OPBiomeTags.WITHOUT_DEFAULT_MONSTER_SPAWNS).add(Biomes.MUSHROOM_FIELDS, Biomes.DEEP_DARK);
        this.tag(OPBiomeTags.WITH_DEFAULT_MONSTER_SPAWNS).addTag(BiomeTags.IS_OVERWORLD).remove(OPBiomeTags.WITHOUT_DEFAULT_MONSTER_SPAWNS);
    }
}