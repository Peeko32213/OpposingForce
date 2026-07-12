package com.barl_inc.opposing_force.registry;

import com.barl_inc.opposing_force.blocks.*;
import com.mojang.datafixers.util.Pair;
import com.barl_inc.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.blocks.*;
import com.barl_inc.opposing_force.blocks.trees.AppleTreeGrower;
import com.barl_inc.opposing_force.blocks.utils.FruitLeaves;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class OPBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(OpposingForce.MOD_ID);
    public static List<DeferredBlock<? extends Block>> BLOCK_TRANSLATIONS = new ArrayList<>();
    public static List<Supplier<Block>> MOB_HEADS = new ArrayList<>();

    // Dicer
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> DICER_HEAD = registerMobHead("dicer_head", MobHeadBlock.Types.DICER, OPBlockProperties.mobHead(OPNoteBlockInstruments.DICER.get()));
    public static final DeferredBlock<Block> DICER_LENS_BLOCK = registerBlock("dicer_lens_block", () -> new Block(OPBlockProperties.DICER_LENS_BLOCK));

    // Frowzy
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> FROWZY_HEAD = registerMobHead("frowzy_head", MobHeadBlock.Types.FROWZY, OPBlockProperties.mobHead(OPNoteBlockInstruments.FROWZY.get()));

    // Guzzler
    public static final DeferredBlock<Block> GUZZLER_SCALE_BLOCK = registerBlock("guzzler_scale_block", () -> new Block(OPBlockProperties.GUZZLER_SCALE_BLOCK));

    public static final DeferredBlock<Block> GUZZLER_SCALE_BRICKS = registerBlock("guzzler_scale_bricks", () -> new Block(OPBlockProperties.GUZZLER_SCALE_BLOCK));
    public static final DeferredBlock<Block> GUZZLER_SCALE_BRICK_STAIRS = registerBlock("guzzler_scale_brick_stairs", () -> new StairBlock(() -> GUZZLER_SCALE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(GUZZLER_SCALE_BRICKS.get())));
    public static final DeferredBlock<Block> GUZZLER_SCALE_BRICK_SLAB = registerBlock("guzzler_scale_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(GUZZLER_SCALE_BRICKS.get())));

    public static final DeferredBlock<Block> TREMBLING_GUZZLER_SCALE_BRICKS = registerBlock("trembling_guzzler_scale_bricks", () -> new Block(OPBlockProperties.GUZZLER_SCALE_BLOCK));
    public static final DeferredBlock<Block> TREMBLING_GUZZLER_SCALE_BRICK_STAIRS = registerBlock("trembling_guzzler_scale_brick_stairs", () -> new StairBlock(() -> TREMBLING_GUZZLER_SCALE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(TREMBLING_GUZZLER_SCALE_BRICKS.get())));
    public static final DeferredBlock<Block> TREMBLING_GUZZLER_SCALE_BRICK_SLAB = registerBlock("trembling_guzzler_scale_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(TREMBLING_GUZZLER_SCALE_BRICKS.get())));

    public static final DeferredBlock<Block> TREMBLING_GUZZLER_SCALE_SHINGLES = registerBlock("trembling_guzzler_scale_shingles", () -> new Block(OPBlockProperties.GUZZLER_SCALE_BLOCK));
    public static final DeferredBlock<Block> TREMBLING_GUZZLER_SCALE_SHINGLE_STAIRS = registerBlock("trembling_guzzler_scale_shingle_stairs", () -> new StairBlock(() -> TREMBLING_GUZZLER_SCALE_SHINGLES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(TREMBLING_GUZZLER_SCALE_SHINGLES.get())));
    public static final DeferredBlock<Block> TREMBLING_GUZZLER_SCALE_SHINGLE_SLAB = registerBlock("trembling_guzzler_scale_shingle_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(TREMBLING_GUZZLER_SCALE_SHINGLES.get())));

    public static final DeferredBlock<Block> INFERNO_PIE = registerBlock("inferno_pie", () -> new InfernoPieBlock(OPBlockProperties.INFERNO_PIE));

    // Rambler
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> ANGRY_RAMBLER_SKULL = registerMobHeadNoLang("angry_rambler_skull", MobHeadBlock.Types.RAMBLER_ANGRY, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> CLASSIC_RAMBLER_SKULL = registerMobHeadNoLang("classic_rambler_skull", MobHeadBlock.Types.RAMBLER_CLASSIC, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> EVIL_RAMBLER_SKULL = registerMobHeadNoLang("evil_rambler_skull", MobHeadBlock.Types.RAMBLER_EVIL, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> GRINNING_RAMBLER_SKULL = registerMobHeadNoLang("grinning_rambler_skull", MobHeadBlock.Types.RAMBLER_GRINNING, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> SKELETAL_RAMBLER_SKULL = registerMobHeadNoLang("skeletal_rambler_skull", MobHeadBlock.Types.RAMBLER_SKELETAL, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> SMILING_RAMBLER_SKULL = registerMobHeadNoLang("smiling_rambler_skull", MobHeadBlock.Types.RAMBLER_SMILING, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> STRANGE_RAMBLER_SKULL = registerMobHeadNoLang("strange_rambler_skull", MobHeadBlock.Types.RAMBLER_STRANGE, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> CRUNDLY_RAMBLER_SKULL = registerMobHeadNoLang("crundly_rambler_skull", MobHeadBlock.Types.RAMBLER_CRUNDLY, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> DWARVEN_RAMBLER_SKULL = registerMobHeadNoLang("dwarven_rambler_skull", MobHeadBlock.Types.RAMBLER_DWARVEN, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> IMPRISONED_RAMBLER_SKULL = registerMobHeadNoLang("imprisoned_rambler_skull", MobHeadBlock.Types.RAMBLER_IMPRISONED, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> INDOMITABLE_RAMBLER_SKULL = registerMobHeadNoLang("indomitable_rambler_skull", MobHeadBlock.Types.RAMBLER_INDOMITABLE, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> LEERING_RAMBLER_SKULL = registerMobHeadNoLang("leering_rambler_skull", MobHeadBlock.Types.RAMBLER_LEERING, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> MAGMATIC_RAMBLER_SKULL = registerMobHeadNoLang("magmatic_rambler_skull", MobHeadBlock.Types.RAMBLER_MAGMATIC, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> MUSICAL_RAMBLER_SKULL = registerMobHeadNoLang("musical_rambler_skull", MobHeadBlock.Types.RAMBLER_MUSICAL, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> NOSY_RAMBLER_SKULL = registerMobHeadNoLang("nosy_rambler_skull", MobHeadBlock.Types.RAMBLER_NOSY, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> VALIANT_RAMBLER_SKULL = registerMobHeadNoLang("valiant_rambler_skull", MobHeadBlock.Types.RAMBLER_VALIANT, OPBlockProperties.mobHead(OPNoteBlockInstruments.RAMBLER.get()));

    // Skyvern
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> SKYVERN_HEAD = registerMobHead("skyvern_head", MobHeadBlock.Types.SKYVERN, OPBlockProperties.mobHead(OPNoteBlockInstruments.SKYVERN.get()));

    // Slug
    public static final DeferredBlock<Block> SLUG_EGGS = registerBlock("slug_eggs", () -> new SlugEggBlock(OPBlockProperties.SLUG_EGGS));
    public static final DeferredBlock<Block> VILE_STONE = registerBlock("vile_stone", () -> new Block(OPBlockProperties.VILE_STONE));
    public static final DeferredBlock<Block> VILE_STONE_STAIRS = registerBlock("vile_stone_stairs", () -> new StairBlock(() -> VILE_STONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(VILE_STONE.get())));
    public static final DeferredBlock<Block> VILE_STONE_SLAB = registerBlock("vile_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(VILE_STONE.get())));
    public static final DeferredBlock<Block> VILE_COBBLESTONE = registerBlock("vile_cobblestone", () -> new Block(OPBlockProperties.VILE_COBBLESTONE));
    public static final DeferredBlock<Block> VILE_COBBLESTONE_STAIRS = registerBlock("vile_cobblestone_stairs", () -> new StairBlock(() -> VILE_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(VILE_COBBLESTONE.get())));
    public static final DeferredBlock<Block> VILE_COBBLESTONE_SLAB = registerBlock("vile_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(VILE_COBBLESTONE.get())));
    public static final DeferredBlock<Block> VILE_COBBLESTONE_WALL = registerBlock("vile_cobblestone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(VILE_COBBLESTONE.get())));
    public static final DeferredBlock<Block> VILE_STONE_BRICKS = registerBlock("vile_stone_bricks", () -> new Block(OPBlockProperties.VILE_STONE));
    public static final DeferredBlock<Block> VILE_STONE_BRICK_STAIRS = registerBlock("vile_stone_brick_stairs", () -> new StairBlock(() -> VILE_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(VILE_STONE_BRICKS.get())));
    public static final DeferredBlock<Block> VILE_STONE_BRICK_SLAB = registerBlock("vile_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(VILE_STONE_BRICKS.get())));
    public static final DeferredBlock<Block> VILE_STONE_BRICK_WALL = registerBlock("vile_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(VILE_STONE_BRICKS.get())));
    public static final DeferredBlock<Block> CHISELED_VILE_STONE_BRICKS = registerBlock("chiseled_vile_stone_bricks", () -> new Block(OPBlockProperties.VILE_STONE));

    // Tart
    public static final DeferredBlock<Block> APPLE_SAPLING = registerBlock("apple_sapling", () -> new SaplingBlock(new AppleTreeGrower(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<Block> POTTED_APPLE_SAPLING = registerBlockWithoutItemNoLang("potted_apple_sapling", () -> new FlowerPotBlock(APPLE_SAPLING.get(), registerFlowerPot()));
    public static final DeferredBlock<Block> APPLE_LEAVES = registerBlock("apple_leaves", () -> new GrowingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), FruitLeaves.FruitState.FRUITLESS));
    public static final DeferredBlock<Block> FLOWERING_APPLE_LEAVES = registerBlock("flowering_apple_leaves", () -> new GrowingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), FruitLeaves.FruitState.FLOWERING));
    public static final DeferredBlock<Block> FRUITFUL_APPLE_LEAVES = registerBlock("fruitful_apple_leaves", () -> new FruitfulLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), () -> Items.APPLE, OPBlocks.APPLE_LEAVES));
    public static final DeferredBlock<Block> INFESTED_APPLE_LEAVES = registerBlock("infested_apple_leaves", () -> new GrowingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), FruitLeaves.FruitState.FRUITLESS));
    public static final DeferredBlock<Block> FLOWERING_INFESTED_APPLE_LEAVES = registerBlock("flowering_infested_apple_leaves", () -> new GrowingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), FruitLeaves.FruitState.FLOWERING));
    public static final DeferredBlock<Block> FRUITFUL_INFESTED_APPLE_LEAVES = registerBlock("fruitful_infested_apple_leaves", () -> new InfestedLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), OPEntities.TART, OPBlocks.INFESTED_APPLE_LEAVES));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> TART_HEAD = registerMobHead("tart_head", MobHeadBlock.Types.TART, OPBlockProperties.mobHead(OPNoteBlockInstruments.TART.get()));

    // Trembler
    public static final DeferredBlock<Block> TREMBLER_SHELL = registerBlock("trembler_shell", () -> new TremblerShellBlock(OPBlockProperties.TREMBLING_BLOCK));
    public static final DeferredBlock<Block> TREMBLING_SHINGLES = registerBlock("trembling_shingles", () -> new Block(OPBlockProperties.TREMBLING_BLOCK));
    public static final DeferredBlock<Block> TREMBLING_SHINGLE_STAIRS = registerBlock("trembling_shingle_stairs", () -> new StairBlock(() -> TREMBLING_SHINGLES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(TREMBLING_SHINGLES.get())));
    public static final DeferredBlock<Block> TREMBLING_SHINGLE_SLAB = registerBlock("trembling_shingle_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(TREMBLING_SHINGLES.get())));
    public static final DeferredBlock<Block> TREMBLING_BLOCK = registerBlock("trembling_block", () -> new Block(OPBlockProperties.TREMBLING_BLOCK));

    // Umber Spider
    public static final DeferredBlock<Block> DEEP_WEB = registerBlock("deep_web", () -> new DeepWebBlock(OPBlockProperties.DEEP_WEB));
    public static final DeferredBlock<Block> DEEP_SILK_BLOCK = registerBlock("deep_silk_block", () -> new Block(OPBlockProperties.DEEP_SILK_BLOCK));

    // Whizz
    public static final DeferredBlock<Block> INFESTED_AMETHYST_BLOCK = registerBlockNoLang("infested_amethyst_block", () -> new InfestationBlock(Blocks.AMETHYST_BLOCK, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).sound(SoundType.AMETHYST), OPEntities.WHIZZ, 0.5F));
    public static final Pair<DeferredBlock<Block>, DeferredBlock<Block>> WHIZZ_HEAD = registerMobHead("whizz_head", MobHeadBlock.Types.WHIZZ, OPBlockProperties.mobHead(OPNoteBlockInstruments.WHIZZ.get()));

    private static <B extends Block> DeferredBlock<B> registerBlock(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        OPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        BLOCK_TRANSLATIONS.add(block);
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerBlockNoLang(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        OPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerBlockWithoutItem(String name, Supplier<? extends B> supplier) {
        DeferredBlock<B> block = BLOCKS.register(name, supplier);
        BLOCK_TRANSLATIONS.add(block);
        return block;
    }

    private static <B extends Block> DeferredBlock<B> registerBlockWithoutItemNoLang(String name, Supplier<? extends B> supplier) {
        return BLOCKS.register(name, supplier);
    }

    private static BlockBehaviour.Properties registerFlowerPot(FeatureFlag... featureFlags) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        if (featureFlags.length > 0) {
            properties = properties.requiredFeatures(featureFlags);
        }
        return properties;
    }

    public static Pair<DeferredBlock<Block>, DeferredBlock<Block>> registerMobHead(String name, MobHeadBlock.Types type, BlockBehaviour.Properties properties) {
        DeferredBlock<Block> skull = registerBlockWithoutItemNoLang(name, () -> new MobHeadBlock(type, properties));
        DeferredBlock<Block> wallSkull = registerBlockWithoutItemNoLang("wall_" + name, () -> new WallMobHeadBlock(type, properties.lootFrom(skull)));
        BLOCK_TRANSLATIONS.add(skull);
        MOB_HEADS.add(skull);
        return Pair.of(skull, wallSkull);
    }

    public static Pair<DeferredBlock<Block>, DeferredBlock<Block>> registerMobHeadNoLang(String name, MobHeadBlock.Types type, BlockBehaviour.Properties properties) {
        DeferredBlock<Block> skull = registerBlockWithoutItemNoLang(name, () -> new MobHeadBlock(type, properties));
        DeferredBlock<Block> wallSkull = registerBlockWithoutItemNoLang("wall_" + name, () -> new WallMobHeadBlock(type, properties.lootFrom(skull)));
        MOB_HEADS.add(skull);
        return Pair.of(skull, wallSkull);
    }
}
