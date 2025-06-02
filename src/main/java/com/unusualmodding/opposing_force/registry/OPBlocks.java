package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.blocks.InfestedAmethyst;
import com.unusualmodding.opposing_force.blocks.OPMushroomBlock;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

    public static final RegistryObject<Block> CHICKEN_OF_THE_CAVES = registerBlock("chicken_of_the_caves", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.TERRACOTTA_YELLOW)));
    public static final RegistryObject<Block> POTTED_CHICKEN_OF_THE_CAVES = registerBlockWithoutItem("potted_chicken_of_the_caves", () -> new FlowerPotBlock(OPBlocks.CHICKEN_OF_THE_CAVES.get(), registerFlowerPot()));

    public static final RegistryObject<Block> RAINCAP = registerBlock("raincap", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> POTTED_RAINCAP = registerBlockWithoutItem("potted_raincap", () -> new FlowerPotBlock(OPBlocks.RAINCAP.get(), registerFlowerPot()));

    public static final RegistryObject<Block> CAVE_PATTY = registerBlock("cave_patty", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_BROWN)));
    public static final RegistryObject<Block> POTTED_CAVE_PATTY = registerBlockWithoutItem("potted_cave_patty", () -> new FlowerPotBlock(OPBlocks.CAVE_PATTY.get(), registerFlowerPot()));

    public static final RegistryObject<Block> POWDER_GNOME = registerBlock("powder_gnome", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_RED)));
    public static final RegistryObject<Block> POTTED_POWDER_GNOME = registerBlockWithoutItem("potted_powder_gnome", () -> new FlowerPotBlock(OPBlocks.POWDER_GNOME.get(), registerFlowerPot()));

    public static final RegistryObject<Block> CREAM_CAP = registerBlock("cream_cap", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.TERRACOTTA_WHITE)));
    public static final RegistryObject<Block> POTTED_CREAM_CAP = registerBlockWithoutItem("potted_cream_cap", () -> new FlowerPotBlock(OPBlocks.CREAM_CAP.get(), registerFlowerPot()));

    public static final RegistryObject<Block> COPPER_ENOKI = registerBlock("copper_enoki", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_ORANGE)));
    public static final RegistryObject<Block> POTTED_COPPER_ENOKI = registerBlockWithoutItem("potted_copper_enoki", () -> new FlowerPotBlock(OPBlocks.COPPER_ENOKI.get(), registerFlowerPot()));

    public static final RegistryObject<Block> BLUE_TRUMPET = registerBlock("blue_trumpet", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_BLUE)));
    public static final RegistryObject<Block> POTTED_BLUE_TRUMPET = registerBlockWithoutItem("potted_blue_trumpet", () -> new FlowerPotBlock(OPBlocks.BLUE_TRUMPET.get(), registerFlowerPot()));

    public static final RegistryObject<Block> PRINCESS_JELLY = registerBlock("princess_jelly", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> POTTED_PRINCESS_JELLY = registerBlockWithoutItem("potted_princess_jelly", () -> new FlowerPotBlock(OPBlocks.PRINCESS_JELLY.get(), registerFlowerPot()));

    public static final RegistryObject<Block> BLACKCAP = registerBlock("blackcap", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block> POTTED_BLACKCAP = registerBlockWithoutItem("potted_blackcap", () -> new FlowerPotBlock(OPBlocks.PRINCESS_JELLY.get(), registerFlowerPot()));

    public static final RegistryObject<Block> CAP_OF_EYE = registerBlock("cap_of_eye", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> POTTED_CAP_OF_EYE = registerBlockWithoutItem("potted_cap_of_eye", () -> new FlowerPotBlock(OPBlocks.CAP_OF_EYE.get(), registerFlowerPot()));

    public static final RegistryObject<Block> GREEN_FUNK = registerBlock("green_funk", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_GREEN)));
    public static final RegistryObject<Block> POTTED_GREEN_FUNK = registerBlockWithoutItem("potted_green_funk", () -> new FlowerPotBlock(OPBlocks.GREEN_FUNK.get(), registerFlowerPot()));

    public static final RegistryObject<Block> LIME_NUB = registerBlock("lime_nub", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_LIGHT_GREEN)));
    public static final RegistryObject<Block> POTTED_LIME_NUB = registerBlockWithoutItem("potted_lime_nub", () -> new FlowerPotBlock(OPBlocks.LIME_NUB.get(), registerFlowerPot()));

    public static final RegistryObject<Block> POP_CAP = registerBlock("pop_cap", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<Block> POTTED_POP_CAP = registerBlockWithoutItem("potted_pop_cap", () -> new FlowerPotBlock(OPBlocks.POP_CAP.get(), registerFlowerPot()));

    public static final RegistryObject<Block> PURPLE_KNOB = registerBlock("purple_knob", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> POTTED_PURPLE_KNOB = registerBlockWithoutItem("potted_purple_knob", () -> new FlowerPotBlock(OPBlocks.PURPLE_KNOB.get(), registerFlowerPot()));

    public static final RegistryObject<Block> QUEEN_IN_MAGENTA = registerBlock("queen_in_magenta", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_MAGENTA)));
    public static final RegistryObject<Block> POTTED_QUEEN_IN_MAGENTA = registerBlockWithoutItem("potted_queen_in_magenta", () -> new FlowerPotBlock(OPBlocks.QUEEN_IN_MAGENTA.get(), registerFlowerPot()));

    public static final RegistryObject<Block> SLATESHROOM = registerBlock("slateshroom", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_PURPLE)));
    public static final RegistryObject<Block> POTTED_SLATESHROOM = registerBlockWithoutItem("potted_slateshroom", () -> new FlowerPotBlock(OPBlocks.SLATESHROOM.get(), registerFlowerPot()));

    public static final RegistryObject<Block> SLIPPERY_TOP = registerBlock("slippery_top", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_YELLOW)));
    public static final RegistryObject<Block> POTTED_SLIPPERY_TOP = registerBlockWithoutItem("potted_slippery_top", () -> new FlowerPotBlock(OPBlocks.SLIPPERY_TOP.get(), registerFlowerPot()));

    public static final RegistryObject<Block> WHITECAP = registerBlock("whitecap", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.TERRACOTTA_WHITE)));
    public static final RegistryObject<Block> POTTED_WHITECAP = registerBlockWithoutItem("potted_whitecap", () -> new FlowerPotBlock(OPBlocks.WHITECAP.get(), registerFlowerPot()));

    public static final RegistryObject<Block> INFESTED_AMETHYST_BLOCK = registerBlockNoLang("infested_amethyst_block", () -> new InfestedAmethyst(Blocks.AMETHYST_BLOCK, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).sound(SoundType.AMETHYST)));

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
