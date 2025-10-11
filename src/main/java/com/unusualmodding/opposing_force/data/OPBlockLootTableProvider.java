package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;

import java.util.HashSet;
import java.util.Set;

import static com.unusualmodding.opposing_force.registry.OPBlocks.*;

public class OPBlockLootTableProvider extends BlockLootSubProvider {

    private final Set<Block> knownBlocks = new HashSet<>();
    public OPBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        super.add(block, builder);
        knownBlocks.add(block);
    }

    @Override
    protected void generate() {
        this.dropSelf(TREMBLER_SHELL.get());
        this.dropSelf(TREMBLING_SHINGLES.get());
        this.dropSelf(TREMBLING_SHINGLE_STAIRS.get());
        this.add(TREMBLING_SHINGLE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(TREMBLING_BLOCK.get());
        this.add(TREMBLING_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(CHISELED_TREMBLING_BLOCK.get());
        this.dropSelf(DEEP_SILK_BLOCK.get());
        createSilkTouchOrShearsDispatchTable(DEEP_WEB.get(), this.applyExplosionCondition(DEEP_WEB.get(), LootItem.lootTableItem(OPItems.DEEP_SILK.get())));
        this.dropSelf(VILE_STONE.get());
        this.dropSelf(VILE_STONE_STAIRS.get());
        this.add(VILE_STONE_SLAB.get(), this::createSlabItemTable);

        this.dropSelf(VILE_COBBLESTONE.get());
        this.dropSelf(VILE_COBBLESTONE_STAIRS.get());
        this.add(VILE_COBBLESTONE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(VILE_COBBLESTONE_WALL.get());

        this.dropSelf(VILE_STONE_BRICKS.get());
        this.dropSelf(VILE_STONE_BRICK_STAIRS.get());
        this.add(VILE_STONE_BRICK_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(VILE_STONE_BRICK_WALL.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
