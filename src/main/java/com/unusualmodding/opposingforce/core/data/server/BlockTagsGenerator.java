package com.unusualmodding.opposingforce.core.data.server;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.core.registry.OPTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BlockTagsGenerator extends BlockTagsProvider {
    public BlockTagsGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, OpposingForce.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        tag(OPTags.HOLE_MUSHROOM_BLOCKS)
                .addTag(BlockTags.MUSHROOM_GROW_BLOCK)
                .addTag(BlockTags.BASE_STONE_OVERWORLD)

        ;


    }


    @Override
    public String getName() {
        return OpposingForce.MODID + " Block Tags";
    }
}
