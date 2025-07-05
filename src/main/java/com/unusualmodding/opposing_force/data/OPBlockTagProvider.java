package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.unusualmodding.opposing_force.registry.OPBlocks.*;

public class OPBlockTagProvider extends BlockTagsProvider {

    public OPBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
        super(output, provider, OpposingForce.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                TREMBLER_SHELL.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                CREAM_CAP_BLOCK.get(),
                COPPER_ENOKI_BLOCK.get(),
                SLIPPERY_TOP_BLOCK.get(),

                CAP_OF_EYE.get(),
                CAVE_PATTY.get(),
                CHICKEN_OF_THE_CAVES.get(),
                COPPER_ENOKI.get(),
                CREAM_CAP.get(),
                POWDER_GNOME.get(),
                PURPLE_KNOB.get(),
                RAINCAP.get(),
                SLIPPERY_TOP.get()
        );

        this.tag(BlockTags.ENDERMAN_HOLDABLE).add(
                CAP_OF_EYE.get(),
                CAVE_PATTY.get(),
                CHICKEN_OF_THE_CAVES.get(),
                COPPER_ENOKI.get(),
                CREAM_CAP.get(),
                POWDER_GNOME.get(),
                PURPLE_KNOB.get(),
                RAINCAP.get(),
                SLIPPERY_TOP.get()
        );

        this.tag(BlockTags.SWORD_EFFICIENT).add(
                CAP_OF_EYE.get(),
                CAVE_PATTY.get(),
                CHICKEN_OF_THE_CAVES.get(),
                COPPER_ENOKI.get(),
                CREAM_CAP.get(),
                POWDER_GNOME.get(),
                PURPLE_KNOB.get(),
                RAINCAP.get(),
                SLIPPERY_TOP.get()
        );
    }
}
