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

    public static final RegistryObject<Block> CHICKEN_OF_THE_CAVES = registerBlock("chicken_of_the_caves", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.TERRACOTTA_YELLOW), false));
    public static final RegistryObject<Block> POTTED_CHICKEN_OF_THE_CAVES = registerBlockWithoutItem("potted_chicken_of_the_caves", () -> new FlowerPotBlock(OPBlocks.CHICKEN_OF_THE_CAVES.get(), registerFlowerPot()));

    public static final RegistryObject<Block> RAINCAP = registerBlock("raincap", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_PURPLE), false));
    public static final RegistryObject<Block> POTTED_RAINCAP = registerBlockWithoutItem("potted_raincap", () -> new FlowerPotBlock(OPBlocks.RAINCAP.get(), registerFlowerPot()));

    public static final RegistryObject<Block> CAVE_PATTY = registerBlock("cave_patty", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_BROWN), false));
    public static final RegistryObject<Block> POTTED_CAVE_PATTY = registerBlockWithoutItem("potted_cave_patty", () -> new FlowerPotBlock(OPBlocks.CAVE_PATTY.get(), registerFlowerPot()));

    public static final RegistryObject<Block> POWDER_GNOME = registerBlock("powder_gnome", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_RED), false));
    public static final RegistryObject<Block> POTTED_POWDER_GNOME = registerBlockWithoutItem("potted_powder_gnome", () -> new FlowerPotBlock(OPBlocks.POWDER_GNOME.get(), registerFlowerPot()));

    public static final RegistryObject<Block> CREAM_CAP = registerBlock("cream_cap", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.TERRACOTTA_WHITE), false));
    public static final RegistryObject<Block> POTTED_CREAM_CAP = registerBlockWithoutItem("potted_cream_cap", () -> new FlowerPotBlock(OPBlocks.CREAM_CAP.get(), registerFlowerPot()));
    public static final RegistryObject<Block> CREAM_CAP_BLOCK = registerBlock("cream_cap_block", () -> new HugeMushroomBlock(OPBlockProperties.hugeMushroomBlock(MapColor.TERRACOTTA_WHITE)));

    public static final RegistryObject<Block> COPPER_ENOKI = registerBlock("copper_enoki", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_ORANGE), false));
    public static final RegistryObject<Block> POTTED_COPPER_ENOKI = registerBlockWithoutItem("potted_copper_enoki", () -> new FlowerPotBlock(OPBlocks.COPPER_ENOKI.get(), registerFlowerPot()));
    public static final RegistryObject<Block> COPPER_ENOKI_BLOCK = registerBlock("copper_enoki_block", () -> new HugeMushroomBlock(OPBlockProperties.hugeMushroomBlock(MapColor.COLOR_ORANGE)));

    public static final RegistryObject<Block> CAP_OF_EYE = registerBlock("cap_of_eye", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_PURPLE), false));
    public static final RegistryObject<Block> POTTED_CAP_OF_EYE = registerBlockWithoutItem("potted_cap_of_eye", () -> new FlowerPotBlock(OPBlocks.CAP_OF_EYE.get(), registerFlowerPot()));

    public static final RegistryObject<Block> PURPLE_KNOB = registerBlock("purple_knob", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_PURPLE), false));
    public static final RegistryObject<Block> POTTED_PURPLE_KNOB = registerBlockWithoutItem("potted_purple_knob", () -> new FlowerPotBlock(OPBlocks.PURPLE_KNOB.get(), registerFlowerPot()));

    public static final RegistryObject<Block> SLIPPERY_TOP = registerBlock("slippery_top", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_YELLOW), false));
    public static final RegistryObject<Block> POTTED_SLIPPERY_TOP = registerBlockWithoutItem("potted_slippery_top", () -> new FlowerPotBlock(OPBlocks.SLIPPERY_TOP.get(), registerFlowerPot()));
    public static final RegistryObject<Block> SLIPPERY_TOP_BLOCK = registerBlock("slippery_top_block", () -> new HugeMushroomBlock(OPBlockProperties.hugeMushroomBlock(MapColor.COLOR_YELLOW)));

    public static final RegistryObject<Block> INFESTED_AMETHYST_BLOCK = registerBlockNoLang("infested_amethyst_block", () -> new InfestedAmethyst(Blocks.AMETHYST_BLOCK, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> TREMBLER_SHELL = registerBlock("trembler_shell", () -> new TremblerShellBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_GREEN).instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.DRIPSTONE_BLOCK).requiresCorrectToolForDrops().strength(2.0F, 2.0F)));

    public static final RegistryObject<Block> DICER_HEAD = BLOCKS.register("dicer_head", () -> new MobHeadBlock(MobHeadBlock.Types.DICER, BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.valueOf("DICER")).strength(1.0F).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> DICER_WALL_HEAD = BLOCKS.register("dicer_wall_head", () -> new WallMobHeadBlock(MobHeadBlock.Types.DICER, BlockBehaviour.Properties.of().dropsLike(DICER_HEAD.get()).strength(1.0F).pushReaction(PushReaction.DESTROY)));

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

    private static BlockBehaviour.Properties registerFlowerPot(FeatureFlag... featureFlags) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        if (featureFlags.length > 0) {
            properties = properties.requiredFeatures(featureFlags);
        }
        return properties;
    }

}
