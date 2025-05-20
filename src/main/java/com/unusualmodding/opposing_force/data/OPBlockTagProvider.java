package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.tags.OPTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class OPBlockTagProvider extends BlockTagsProvider {
    public OPBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, OpposingForce.MOD_ID, existingFileHelper);
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
        return OpposingForce.MOD_ID + " Block Tags";
    }
}
