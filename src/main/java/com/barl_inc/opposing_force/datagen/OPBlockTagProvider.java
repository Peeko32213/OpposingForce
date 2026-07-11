package com.barl_inc.opposing_force.datagen;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.registry.tags.OPBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.barl_inc.opposing_force.registry.OPBlocks.*;

public class OPBlockTagProvider extends BlockTagsProvider {

    public OPBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
        super(output, provider, OpposingForce.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(OPBlockTags.CAVE_MOB_SPAWNABLE_ON).addTag(BlockTags.BASE_STONE_OVERWORLD).addTag(Tags.Blocks.ORES).add(
                Blocks.GRAVEL
        );

        this.tag(OPBlockTags.FROWZY_SPAWNABLE_ON).addTag(OPBlockTags.CAVE_MOB_SPAWNABLE_ON);
        this.tag(OPBlockTags.UMBER_SPIDER_SPAWNABLE_ON).addTag(OPBlockTags.CAVE_MOB_SPAWNABLE_ON);

        this.tag(BlockTags.LEAVES).add(
                APPLE_LEAVES.get(),
                FRUITFUL_APPLE_LEAVES.get(),
                FLOWERING_APPLE_LEAVES.get(),
                INFESTED_APPLE_LEAVES.get(),
                FRUITFUL_INFESTED_APPLE_LEAVES.get(),
                FLOWERING_INFESTED_APPLE_LEAVES.get()
        );

        this.tag(BlockTags.SAPLINGS).add(
                APPLE_SAPLING.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                DICER_LENS_BLOCK.get(),
                GUZZLER_SCALE_BRICKS.get(),
                GUZZLER_SCALE_BRICK_STAIRS.get(),
                GUZZLER_SCALE_BRICK_SLAB.get(),
                GUZZLER_SCALE_BLOCK.get(),
                TREMBLING_GUZZLER_SCALE_BRICKS.get(),
                TREMBLING_GUZZLER_SCALE_BRICK_STAIRS.get(),
                TREMBLING_GUZZLER_SCALE_BRICK_SLAB.get(),
                TREMBLING_GUZZLER_SCALE_SHINGLES.get(),
                TREMBLING_GUZZLER_SCALE_SHINGLE_STAIRS.get(),
                TREMBLING_GUZZLER_SCALE_SHINGLE_SLAB.get(),
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
                DEEP_SILK_BLOCK.get(),
                APPLE_LEAVES.get(),
                FRUITFUL_APPLE_LEAVES.get(),
                FLOWERING_APPLE_LEAVES.get(),
                INFESTED_APPLE_LEAVES.get(),
                FRUITFUL_INFESTED_APPLE_LEAVES.get(),
                FLOWERING_INFESTED_APPLE_LEAVES.get()
        );

        this.tag(BlockTags.FALL_DAMAGE_RESETTING).add(
                DEEP_WEB.get()
        );

        this.tag(BlockTags.STAIRS).add(
                GUZZLER_SCALE_BRICK_STAIRS.get(),
                TREMBLING_GUZZLER_SCALE_BRICK_STAIRS.get(),
                TREMBLING_GUZZLER_SCALE_SHINGLE_STAIRS.get(),
                TREMBLING_SHINGLE_STAIRS.get(),
                VILE_STONE_STAIRS.get(),
                VILE_COBBLESTONE_STAIRS.get(),
                VILE_STONE_BRICK_STAIRS.get()
        );

        this.tag(BlockTags.SLABS).add(
                GUZZLER_SCALE_BRICK_SLAB.get(),
                TREMBLING_GUZZLER_SCALE_BRICK_SLAB.get(),
                TREMBLING_GUZZLER_SCALE_SHINGLE_SLAB.get(),
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
