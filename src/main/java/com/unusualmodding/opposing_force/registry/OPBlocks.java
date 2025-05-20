package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.blocks.OPMushroomBlock;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class OPBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OpposingForce.MOD_ID);

    public static final RegistryObject<Block> CHICKEN_OF_THE_CAVES = register("chicken_of_the_caves", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.TERRACOTTA_YELLOW), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_CHICKEN_OF_THE_CAVES = registerBlockWithoutItem("potted_chicken_of_the_caves", () -> new FlowerPotBlock(OPBlocks.CHICKEN_OF_THE_CAVES.get(), registerFlowerPot()));

    public static final RegistryObject<Block> RAINCAP = register("raincap", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_PURPLE), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_RAINCAP = registerBlockWithoutItem("potted_raincap", () -> new FlowerPotBlock(OPBlocks.RAINCAP.get(), registerFlowerPot()));

    public static final RegistryObject<Block> CAVE_PATTY = register("cave_patty", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_BROWN), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_CAVE_PATTY = registerBlockWithoutItem("potted_cave_patty", () -> new FlowerPotBlock(OPBlocks.CAVE_PATTY.get(), registerFlowerPot()));

    public static final RegistryObject<Block> POWDER_GNOME = register("powder_gnome", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_RED), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_POWDER_GNOME = registerBlockWithoutItem("potted_powder_gnome", () -> new FlowerPotBlock(OPBlocks.POWDER_GNOME.get(), registerFlowerPot()));

    public static final RegistryObject<Block> CREAM_CAP = register("cream_cap", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.TERRACOTTA_WHITE), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_CREAM_CAP = registerBlockWithoutItem("potted_cream_cap", () -> new FlowerPotBlock(OPBlocks.CREAM_CAP.get(), registerFlowerPot()));

    public static final RegistryObject<Block> COPPER_ENOKI = register("copper_enoki", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_ORANGE), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_COPPER_ENOKI = registerBlockWithoutItem("potted_copper_enoki", () -> new FlowerPotBlock(OPBlocks.COPPER_ENOKI.get(), registerFlowerPot()));

    public static final RegistryObject<Block> BLUE_TRUMPET = register("blue_trumpet", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_BLUE), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_BLUE_TRUMPET = registerBlockWithoutItem("potted_blue_trumpet", () -> new FlowerPotBlock(OPBlocks.BLUE_TRUMPET.get(), registerFlowerPot()));

    public static final RegistryObject<Block> PRINCESS_JELLY = register("princess_jelly", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_PURPLE), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_PRINCESS_JELLY = registerBlockWithoutItem("potted_princess_jelly", () -> new FlowerPotBlock(OPBlocks.PRINCESS_JELLY.get(), registerFlowerPot()));

    public static final RegistryObject<Block> BLACKCAP = register("blackcap", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_BLACK), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_BLACKCAP = registerBlockWithoutItem("potted_blackcap", () -> new FlowerPotBlock(OPBlocks.PRINCESS_JELLY.get(), registerFlowerPot()));

    public static final RegistryObject<Block> CAP_OF_EYE = register("cap_of_eye", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_PURPLE), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_CAP_OF_EYE = registerBlockWithoutItem("potted_cap_of_eye", () -> new FlowerPotBlock(OPBlocks.CAP_OF_EYE.get(), registerFlowerPot()));

    public static final RegistryObject<Block> GREEN_FUNK = register("green_funk", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_GREEN), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_GREEN_FUNK = registerBlockWithoutItem("potted_green_funk", () -> new FlowerPotBlock(OPBlocks.GREEN_FUNK.get(), registerFlowerPot()));

    public static final RegistryObject<Block> LIME_NUB = register("lime_nub", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_LIGHT_GREEN), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_LIME_NUB = registerBlockWithoutItem("potted_lime_nub", () -> new FlowerPotBlock(OPBlocks.LIME_NUB.get(), registerFlowerPot()));

    public static final RegistryObject<Block> POP_CAP = register("pop_cap", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_YELLOW), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_POP_CAP = registerBlockWithoutItem("potted_pop_cap", () -> new FlowerPotBlock(OPBlocks.POP_CAP.get(), registerFlowerPot()));

    public static final RegistryObject<Block> PURPLE_KNOB = register("purple_knob", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_PURPLE), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_PURPLE_KNOB = registerBlockWithoutItem("potted_purple_knob", () -> new FlowerPotBlock(OPBlocks.PURPLE_KNOB.get(), registerFlowerPot()));

    public static final RegistryObject<Block> QUEEN_IN_MAGENTA = register("queen_in_magenta", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_MAGENTA), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_QUEEN_IN_MAGENTA = registerBlockWithoutItem("potted_queen_in_magenta", () -> new FlowerPotBlock(OPBlocks.QUEEN_IN_MAGENTA.get(), registerFlowerPot()));

    public static final RegistryObject<Block> SLATESHROOM = register("slateshroom", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_PURPLE), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_SLATESHROOM = registerBlockWithoutItem("potted_slateshroom", () -> new FlowerPotBlock(OPBlocks.SLATESHROOM.get(), registerFlowerPot()));

    public static final RegistryObject<Block> SLIPPERY_TOP = register("slippery_top", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.COLOR_YELLOW), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_SLIPPERY_TOP = registerBlockWithoutItem("potted_slippery_top", () -> new FlowerPotBlock(OPBlocks.SLIPPERY_TOP.get(), registerFlowerPot()));

    public static final RegistryObject<Block> WHITECAP = register("whitecap", () -> new OPMushroomBlock(OPBlockProperties.mushroom(MapColor.TERRACOTTA_WHITE), TreeFeatures.HUGE_BROWN_MUSHROOM));
    public static final RegistryObject<Block> POTTED_WHITECAP = registerBlockWithoutItem("potted_whitecap", () -> new FlowerPotBlock(OPBlocks.WHITECAP.get(), registerFlowerPot()));

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<Block> block) {
        RegistryObject<? extends Block> ret = BLOCKS.register(name, block);
        OPItems.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()));
        return (RegistryObject<T>) ret;
    }

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        OPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static <B extends Block> RegistryObject<B> registerBlockWithoutItem(String name, Supplier<? extends B> supplier) {
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
