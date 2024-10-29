package com.peeko32213.hole.core.datagen;


import com.peeko32213.hole.core.registry.HoleBlocks;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.HashSet;
import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {
    private final Set<Block> knownBlocks = new HashSet<>();
    private static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    private static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    public BlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void add(Block pBlock, LootTable.Builder pBuilder) {
        super.add(pBlock, pBuilder);
        knownBlocks.add(pBlock);
    }

    @Override
    protected void generate() {
        dropSelf(HoleBlocks.CAVE_PATTY.get());
        dropSelf(HoleBlocks.CREAM_CAP.get());
        dropSelf(HoleBlocks.CHICKEN_OF_THE_CAVES.get());
        dropSelf(HoleBlocks.COPPER_ENOKI.get());
        dropSelf(HoleBlocks.BLUE_TRUMPET.get());
        dropSelf(HoleBlocks.RAINCAP.get());
        dropSelf(HoleBlocks.PRINCESS_JELLY.get());
        dropSelf(HoleBlocks.POWDER_GNOME.get());
        dropSelf(HoleBlocks.BLACKCAP.get());
        dropSelf(HoleBlocks.CAP_OF_EYE.get());
        dropSelf(HoleBlocks.GREEN_FUNK.get());
        dropSelf(HoleBlocks.LIME_NUB.get());
        dropSelf(HoleBlocks.POP_CAP.get());
        dropSelf(HoleBlocks.PURPLE_KNOB.get());
        dropSelf(HoleBlocks.QUEEN_IN_PURPLE.get());
        dropSelf(HoleBlocks.SLATESHROOM.get());
        dropSelf(HoleBlocks.SLIPPERY_TOP.get());
        dropSelf(HoleBlocks.WHITECAP.get());

        createPotFlowerItemTable(HoleBlocks.POTTED_CAVE_PATTY.get(),HoleBlocks.CAVE_PATTY.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_CREAM_CAP.get(),HoleBlocks.CREAM_CAP.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_CHICKEN_OF_THE_CAVES.get(),HoleBlocks.CHICKEN_OF_THE_CAVES.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_COPPER_ENOKI.get(),HoleBlocks.COPPER_ENOKI.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_BLUE_TRUMPET.get(),HoleBlocks.BLUE_TRUMPET.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_RAINCAP.get(),HoleBlocks.RAINCAP.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_PRINCESS_JELLY.get(),HoleBlocks.PRINCESS_JELLY.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_POWDER_GNOME.get(),HoleBlocks.POWDER_GNOME.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_BLACKCAP.get(),HoleBlocks.BLACKCAP.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_CAP_OF_EYE.get(),HoleBlocks.CAP_OF_EYE.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_GREEN_FUNK.get(),HoleBlocks.GREEN_FUNK.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_LIME_NUB.get(),HoleBlocks.LIME_NUB.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_POP_CAP.get(),HoleBlocks.POP_CAP.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_PURPLE_KNOB.get(),HoleBlocks.PURPLE_KNOB.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_QUEEN_IN_PURPLE.get(),HoleBlocks.QUEEN_IN_PURPLE.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_SLATESHROOM.get(),HoleBlocks.SLATESHROOM.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_SLIPPERY_TOP.get(),HoleBlocks.SLIPPERY_TOP.get());
        createPotFlowerItemTable(HoleBlocks.POTTED_WHITECAP.get(),HoleBlocks.WHITECAP.get());

    }
    protected void createPotFlowerItemTable(Block flowerpotBlock, ItemLike pItem) {
        add(flowerpotBlock ,LootTable.lootTable()
                .withPool(this.applyExplosionCondition(Blocks.FLOWER_POT, LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Blocks.FLOWER_POT))))
                .withPool(this.applyExplosionCondition(pItem, LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(pItem)))));
    }


    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected LootTable.Builder createMultipleDrops(Block pBlock, Item item1, Item item2) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item1)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                        .then(LootItem.lootTableItem(item2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    protected LootTable.Builder createMultipleBlockDrops(Block pBlock, Block item1, Block item2) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                                LootItem.lootTableItem(item1)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))
                        .then(LootItem.lootTableItem(item2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
