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
                TREMBLER_SHELL.get(),
                TREMBLING_SHINGLES.get(),
                TREMBLING_SHINGLE_STAIRS.get(),
                TREMBLING_SHINGLE_SLAB.get(),
                TREMBLING_BLOCK.get(),
                VILE_STONE.get(),
                VILE_STONE_STAIRS.get(),
                VILE_STONE_SLAB.get(),
                VILE_COBBLESTONE.get(),
                VILE_COBBLESTONE_STAIRS.get(),
                VILE_COBBLESTONE_SLAB.get(),
                VILE_COBBLESTONE_WALL.get(),
                VILE_STONE_BRICKS.get(),
                VILE_STONE_BRICK_STAIRS.get(),
                VILE_STONE_BRICK_SLAB.get(),
                VILE_STONE_BRICK_WALL.get(),
                CHISELED_VILE_STONE_BRICKS.get()
        );

        this.tag(BlockTags.SWORD_EFFICIENT).add(
                DEEP_WEB.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                DEEP_SILK_BLOCK.get()
        );

        this.tag(BlockTags.FALL_DAMAGE_RESETTING).add(
                DEEP_WEB.get()
        );

        this.tag(BlockTags.STAIRS).add(
                TREMBLING_SHINGLE_STAIRS.get(),
                VILE_STONE_STAIRS.get(),
                VILE_COBBLESTONE_STAIRS.get(),
                VILE_STONE_BRICK_STAIRS.get()
        );

        this.tag(BlockTags.SLABS).add(
                TREMBLING_SHINGLE_SLAB.get(),
                VILE_STONE_SLAB.get(),
                VILE_COBBLESTONE_SLAB.get(),
                VILE_STONE_BRICK_SLAB.get()
        );

        this.tag(BlockTags.WALLS).add(
                VILE_COBBLESTONE_WALL.get(),
                VILE_STONE_BRICK_WALL.get()
        );
    }
}
