package com.unusualmodding.opposing_force.registry;

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
    public static List<RegistryObject<? extends Block>> AUTO_TRANSLATE = new ArrayList<>();

    public static final RegistryObject<Block> INFESTED_AMETHYST_BLOCK = registerBlockNoLang("infested_amethyst_block", () -> new InfestedAmethyst(Blocks.AMETHYST_BLOCK, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> TREMBLER_SHELL = registerBlock("trembler_shell", () -> new TremblerShellBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(2.0F, 2.0F)));

    public static final RegistryObject<Block> DICER_HEAD = registerBlockWithoutItemNoLang("dicer_head", () -> new MobHeadBlock(MobHeadBlock.Types.DICER, BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.CUSTOM_HEAD).strength(1.0F).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> DICER_WALL_HEAD = registerBlockWithoutItemNoLang("dicer_wall_head", () -> new WallMobHeadBlock(MobHeadBlock.Types.DICER, BlockBehaviour.Properties.of().dropsLike(DICER_HEAD.get()).strength(1.0F).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> FROWZY_HEAD = registerBlockWithoutItemNoLang("frowzy_head", () -> new MobHeadBlock(MobHeadBlock.Types.FROWZY, BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.CUSTOM_HEAD).strength(1.0F).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> FROWZY_WALL_HEAD = registerBlockWithoutItemNoLang("frowzy_wall_head", () -> new WallMobHeadBlock(MobHeadBlock.Types.FROWZY, BlockBehaviour.Properties.of().dropsLike(FROWZY_HEAD.get()).strength(1.0F).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> RAMBLE_SKULL = registerBlockWithoutItemNoLang("ramble_skull", () -> new MobHeadBlock(MobHeadBlock.Types.RAMBLE, BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.CUSTOM_HEAD).strength(1.0F).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> RAMBLE_WALL_SKULL = registerBlockWithoutItemNoLang("ramble_wall_skull", () -> new WallMobHeadBlock(MobHeadBlock.Types.RAMBLE, BlockBehaviour.Properties.of().dropsLike(RAMBLE_SKULL.get()).strength(1.0F).pushReaction(PushReaction.DESTROY)));

    private static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        OPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        AUTO_TRANSLATE.add(block);
        return block;
    }

    private static <B extends Block> RegistryObject<B> registerBlockNoLang(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        OPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    private static <B extends Block> RegistryObject<B> registerBlockWithoutItem(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        AUTO_TRANSLATE.add(block);
        return block;
    }

    private static <B extends Block> RegistryObject<B> registerBlockWithoutItemNoLang(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        return block;
    }

    private static BlockBehaviour.Properties registerFlowerPot(FeatureFlag... featureFlags) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        if (featureFlags.length > 0) {
            properties = properties.requiredFeatures(featureFlags);
        }
        return properties;
    }

}
