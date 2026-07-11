package com.barl_inc.opposing_force.datagen;

import com.barl_inc.opposing_force.registry.OPItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static com.barl_inc.opposing_force.registry.OPBlocks.*;

public class OPBlockLootTableProvider extends BlockLootSubProvider {

    private final Set<Block> knownBlocks = new HashSet<>();
    public OPBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void add(@NotNull Block block, LootTable.@NotNull Builder builder) {
        super.add(block, builder);
        this.knownBlocks.add(block);
    }

    @Override
    protected void generate() {
        this.dropSelf(DICER_LENS_BLOCK.get());

        this.dropSelf(GUZZLER_SCALE_BRICKS.get());
        this.dropSelf(GUZZLER_SCALE_BRICK_STAIRS.get());
        this.add(GUZZLER_SCALE_BRICK_SLAB.get(), this::createSlabItemTable);

        this.dropSelf(TREMBLING_GUZZLER_SCALE_BRICKS.get());
        this.dropSelf(TREMBLING_GUZZLER_SCALE_BRICK_STAIRS.get());
        this.add(TREMBLING_GUZZLER_SCALE_BRICK_SLAB.get(), this::createSlabItemTable);

        this.dropSelf(TREMBLING_GUZZLER_SCALE_SHINGLES.get());
        this.dropSelf(TREMBLING_GUZZLER_SCALE_SHINGLE_STAIRS.get());
        this.add(TREMBLING_GUZZLER_SCALE_SHINGLE_SLAB.get(), this::createSlabItemTable);

        this.dropSelf(TREMBLING_SHINGLES.get());
        this.dropSelf(TREMBLING_SHINGLE_STAIRS.get());
        this.add(TREMBLING_SHINGLE_SLAB.get(), this::createSlabItemTable);

        this.dropSelf(TREMBLER_SHELL.get());
        this.dropSelf(TREMBLING_SHINGLES.get());
        this.dropSelf(TREMBLING_SHINGLE_STAIRS.get());
        this.add(TREMBLING_SHINGLE_SLAB.get(), this::createSlabItemTable);
        this.dropSelf(TREMBLING_BLOCK.get());

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

        this.dropSelf(DICER_HEAD.getFirst().get());
        this.dropSelf(FROWZY_HEAD.getFirst().get());

        this.dropSelf(ANGRY_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(CLASSIC_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(EVIL_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(GRINNING_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(SKELETAL_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(SMILING_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(STRANGE_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(CRUNDLY_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(DWARVEN_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(IMPRISONED_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(INDOMITABLE_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(LEERING_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(MAGMATIC_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(MUSICAL_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(NOSY_RAMBLER_SKULL.getFirst().get());
        this.dropSelf(VALIANT_RAMBLER_SKULL.getFirst().get());

        this.dropSelf(SKYVERN_HEAD.getFirst().get());
        this.dropSelf(TART_HEAD.getFirst().get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
