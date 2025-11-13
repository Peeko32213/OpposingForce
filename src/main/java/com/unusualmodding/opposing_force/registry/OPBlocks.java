package com.unusualmodding.opposing_force.registry;

import com.mojang.datafixers.util.Pair;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.blocks.*;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class OPBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OpposingForce.MOD_ID);
    public static List<RegistryObject<? extends Block>> BLOCK_TRANSLATIONS = new ArrayList<>();

    // Dicer
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> DICER_HEAD = registerMobHead("dicer_head", MobHeadBlock.Types.DICER, OPNoteBlockInstruments.DICER.get());

    // Frowzy
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> FROWZY_HEAD = registerMobHead("frowzy_head", MobHeadBlock.Types.FROWZY, OPNoteBlockInstruments.FROWZY.get());

    // Guzzler
    public static final RegistryObject<Block> INFERNO_PIE = registerBlock("inferno_pie", () -> new InfernoPieBlock(OPBlockProperties.INFERNO_PIE));

    // Rambler
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> ANGRY_RAMBLER_SKULL = registerMobHeadNoLang("angry_rambler_skull", MobHeadBlock.Types.RAMBLER_ANGRY, OPNoteBlockInstruments.RAMBLER.get());
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> CLASSIC_RAMBLER_SKULL = registerMobHeadNoLang("classic_rambler_skull", MobHeadBlock.Types.RAMBLER_CLASSIC, OPNoteBlockInstruments.RAMBLER.get());
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> EVIL_RAMBLER_SKULL = registerMobHeadNoLang("evil_rambler_skull", MobHeadBlock.Types.RAMBLER_EVIL, OPNoteBlockInstruments.RAMBLER.get());
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> GRINNING_RAMBLER_SKULL = registerMobHeadNoLang("grinning_rambler_skull", MobHeadBlock.Types.RAMBLER_GRINNING, OPNoteBlockInstruments.RAMBLER.get());
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> SKELETAL_RAMBLER_SKULL = registerMobHeadNoLang("skeletal_rambler_skull", MobHeadBlock.Types.RAMBLER_SKELETAL, OPNoteBlockInstruments.RAMBLER.get());
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> SMILING_RAMBLER_SKULL = registerMobHeadNoLang("smiling_rambler_skull", MobHeadBlock.Types.RAMBLER_SMILING, OPNoteBlockInstruments.RAMBLER.get());
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> STRANGE_RAMBLER_SKULL = registerMobHeadNoLang("strange_rambler_skull", MobHeadBlock.Types.RAMBLER_STRANGE, OPNoteBlockInstruments.RAMBLER.get());

    // dev skulls
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> CRUNDLY_RAMBLER_SKULL = registerMobHeadNoLang("crundly_rambler_skull", MobHeadBlock.Types.RAMBLER_CRUNDLY, OPNoteBlockInstruments.RAMBLER.get());
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> DWARVEN_RAMBLER_SKULL = registerMobHeadNoLang("dwarven_rambler_skull", MobHeadBlock.Types.RAMBLER_DWARVEN, OPNoteBlockInstruments.RAMBLER.get());
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> IMPRISONED_RAMBLER_SKULL = registerMobHeadNoLang("imprisoned_rambler_skull", MobHeadBlock.Types.RAMBLER_IMPRISONED, OPNoteBlockInstruments.RAMBLER.get());
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> INDOMITABLE_RAMBLER_SKULL = registerMobHeadNoLang("indomitable_rambler_skull", MobHeadBlock.Types.RAMBLER_INDOMITABLE, OPNoteBlockInstruments.RAMBLER.get());
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> LEERING_RAMBLER_SKULL = registerMobHeadNoLang("leering_rambler_skull", MobHeadBlock.Types.RAMBLER_LEERING, OPNoteBlockInstruments.RAMBLER.get());
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> MAGMATIC_RAMBLER_SKULL = registerMobHeadNoLang("magmatic_rambler_skull", MobHeadBlock.Types.RAMBLER_MAGMATIC, OPNoteBlockInstruments.RAMBLER.get());
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> MUSICAL_RAMBLER_SKULL = registerMobHeadNoLang("musical_rambler_skull", MobHeadBlock.Types.RAMBLER_MUSICAL, OPNoteBlockInstruments.RAMBLER.get());
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> NOSY_RAMBLER_SKULL = registerMobHeadNoLang("nosy_rambler_skull", MobHeadBlock.Types.RAMBLER_NOSY, OPNoteBlockInstruments.RAMBLER.get());
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> VALIANT_RAMBLER_SKULL = registerMobHeadNoLang("valiant_rambler_skull", MobHeadBlock.Types.RAMBLER_VALIANT, OPNoteBlockInstruments.RAMBLER.get());

    // Slug
    public static final RegistryObject<Block> SLUG_EGGS = registerBlock("slug_eggs", () -> new SlugEggBlock(OPBlockProperties.SLUG_EGGS));
    public static final RegistryObject<Block> VILE_STONE = registerBlock("vile_stone", () -> new Block(OPBlockProperties.VILE_STONE));
    public static final RegistryObject<Block> VILE_STONE_STAIRS = registerBlock("vile_stone_stairs", () -> new StairBlock(() -> VILE_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(VILE_STONE.get())));
    public static final RegistryObject<Block> VILE_STONE_SLAB = registerBlock("vile_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(VILE_STONE.get())));
    public static final RegistryObject<Block> VILE_COBBLESTONE = registerBlock("vile_cobblestone", () -> new Block(OPBlockProperties.VILE_COBBLESTONE));
    public static final RegistryObject<Block> VILE_COBBLESTONE_STAIRS = registerBlock("vile_cobblestone_stairs", () -> new StairBlock(() -> VILE_COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(VILE_COBBLESTONE.get())));
    public static final RegistryObject<Block> VILE_COBBLESTONE_SLAB = registerBlock("vile_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(VILE_COBBLESTONE.get())));
    public static final RegistryObject<Block> VILE_COBBLESTONE_WALL = registerBlock("vile_cobblestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(VILE_COBBLESTONE.get())));
    public static final RegistryObject<Block> VILE_STONE_BRICKS = registerBlock("vile_stone_bricks", () -> new Block(OPBlockProperties.VILE_STONE));
    public static final RegistryObject<Block> VILE_STONE_BRICK_STAIRS = registerBlock("vile_stone_brick_stairs", () -> new StairBlock(() -> VILE_STONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(VILE_STONE_BRICKS.get())));
    public static final RegistryObject<Block> VILE_STONE_BRICK_SLAB = registerBlock("vile_stone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(VILE_STONE_BRICKS.get())));
    public static final RegistryObject<Block> VILE_STONE_BRICK_WALL = registerBlock("vile_stone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(VILE_STONE_BRICKS.get())));
    public static final RegistryObject<Block> CHISELED_VILE_STONE_BRICKS = registerBlock("chiseled_vile_stone_bricks", () -> new Block(OPBlockProperties.VILE_STONE));

    // Tart
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> TART_HEAD = registerMobHead("tart_head", MobHeadBlock.Types.TART, OPNoteBlockInstruments.TART.get());

    // Trembler
    public static final RegistryObject<Block> TREMBLER_SHELL = registerBlock("trembler_shell", () -> new TremblerShellBlock(OPBlockProperties.TREMBLING_BLOCK));
    public static final RegistryObject<Block> TREMBLING_SHINGLES = registerBlock("trembling_shingles", () -> new Block(OPBlockProperties.TREMBLING_BLOCK));
    public static final RegistryObject<Block> TREMBLING_SHINGLE_STAIRS = registerBlock("trembling_shingle_stairs", () -> new StairBlock(() -> TREMBLING_SHINGLES.get().defaultBlockState(), BlockBehaviour.Properties.copy(TREMBLING_SHINGLES.get())));
    public static final RegistryObject<Block> TREMBLING_SHINGLE_SLAB = registerBlock("trembling_shingle_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(TREMBLING_SHINGLES.get())));
    public static final RegistryObject<Block> TREMBLING_BLOCK = registerBlock("trembling_block", () -> new Block(OPBlockProperties.TREMBLING_BLOCK));

    // Umber Spider
    public static final RegistryObject<Block> DEEP_WEB = registerBlock("deep_web", () -> new DeepWebBlock(OPBlockProperties.DEEP_WEB));
    public static final RegistryObject<Block> DEEP_SILK_BLOCK = registerBlock("deep_silk_block", () -> new Block(OPBlockProperties.DEEP_SILK_BLOCK));

    // Whizz
    public static final RegistryObject<Block> INFESTED_AMETHYST_BLOCK = registerBlockNoLang("infested_amethyst_block", () -> new InfestedAmethystBlock(Blocks.AMETHYST_BLOCK, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).sound(SoundType.AMETHYST)));
    public static final Pair<RegistryObject<Block>, RegistryObject<Block>> WHIZZ_HEAD = registerMobHead("whizz_head", MobHeadBlock.Types.WHIZZ, OPNoteBlockInstruments.WHIZZ.get());

    private static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        OPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        BLOCK_TRANSLATIONS.add(block);
        return block;
    }

    private static <B extends Block> RegistryObject<B> registerBlockNoLang(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        OPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static <B extends Block> RegistryObject<B> registerBlockWithoutItem(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        BLOCK_TRANSLATIONS.add(block);
        return block;
    }

    private static <B extends Block> RegistryObject<B> registerBlockWithoutItemNoLang(String name, Supplier<? extends B> supplier) {
        return BLOCKS.register(name, supplier);
    }

    private static BlockBehaviour.Properties registerFlowerPot(FeatureFlag... featureFlags) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        if (featureFlags.length > 0) {
            properties = properties.requiredFeatures(featureFlags);
        }
        return properties;
    }

    public static Pair<RegistryObject<Block>, RegistryObject<Block>> registerMobHead(String name, MobHeadBlock.Types type, NoteBlockInstrument instrument) {
        RegistryObject<Block> skull = registerBlockWithoutItemNoLang(name, () -> new MobHeadBlock(type, OPBlockProperties.MOB_HEAD.instrument(instrument)));
        RegistryObject<Block> wallSkull = registerBlockWithoutItemNoLang("wall_" + name, () -> new WallMobHeadBlock(type, OPBlockProperties.MOB_HEAD.lootFrom(skull)));
        BLOCK_TRANSLATIONS.add(skull);
        return Pair.of(skull, wallSkull);
    }

    public static Pair<RegistryObject<Block>, RegistryObject<Block>> registerMobHeadNoLang(String name, MobHeadBlock.Types type, NoteBlockInstrument instrument) {
        RegistryObject<Block> skull = registerBlockWithoutItemNoLang(name, () -> new MobHeadBlock(type, OPBlockProperties.MOB_HEAD.instrument(instrument)));
        RegistryObject<Block> wallSkull = registerBlockWithoutItemNoLang("wall_" + name, () -> new WallMobHeadBlock(type, OPBlockProperties.MOB_HEAD.lootFrom(skull)));
        return Pair.of(skull, wallSkull);
    }
}
