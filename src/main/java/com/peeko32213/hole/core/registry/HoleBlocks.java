package com.peeko32213.hole.core.registry;

import com.peeko32213.hole.Hole;
import com.peeko32213.hole.common.block.HoleMushroomBlock;
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

import java.util.function.Supplier;

public class HoleBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            Hole.MODID);

    public static final RegistryObject<Block> CHICKEN_OF_THE_CAVES = register("chicken_of_the_caves", () ->
            new HoleMushroomBlock(BlockBehaviour.Properties
                    .of().mapColor(MapColor.TERRACOTTA_YELLOW)
                    .replaceable().noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_CHICKEN_OF_THE_CAVES = registerBlockNoItem("potted_chicken_of_the_caves",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HoleBlocks.CHICKEN_OF_THE_CAVES, BlockBehaviour
                    .Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));

    public static final RegistryObject<Block> RAINCAP = register("raincap", () ->
            new HoleMushroomBlock(BlockBehaviour.Properties
                    .of().mapColor(MapColor.COLOR_PURPLE)
                    .replaceable().noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_RAINCAP = registerBlockNoItem("potted_raincap",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HoleBlocks.RAINCAP, BlockBehaviour
                    .Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));

    public static final RegistryObject<Block> CAVE_PATTY = register("cave_patty", () ->
            new HoleMushroomBlock(BlockBehaviour.Properties
                    .of().mapColor(MapColor.TERRACOTTA_YELLOW)
                    .replaceable().noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_CAVE_PATTY = registerBlockNoItem("potted_cave_patty",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HoleBlocks.CAVE_PATTY, BlockBehaviour
                    .Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));

    public static final RegistryObject<Block> POWDER_GNOME = register("powder_gnome", () ->
            new HoleMushroomBlock(BlockBehaviour.Properties
                    .of().mapColor(MapColor.COLOR_RED)
                    .replaceable().noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_POWDER_GNOME = registerBlockNoItem("potted_powder_gnome",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HoleBlocks.POWDER_GNOME, BlockBehaviour
                    .Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));

    public static final RegistryObject<Block> CREAM_CAP = register("cream_cap", () ->
            new HoleMushroomBlock(BlockBehaviour.Properties
                    .of().mapColor(MapColor.TERRACOTTA_WHITE)
                    .replaceable().noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_CREAM_CAP = registerBlockNoItem("potted_cream_cap",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HoleBlocks.CREAM_CAP, BlockBehaviour
                    .Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));

    public static final RegistryObject<Block> COPPER_ENOKI = register("copper_enoki", () ->
            new HoleMushroomBlock(BlockBehaviour.Properties
                    .of().mapColor(MapColor.COLOR_ORANGE)
                    .replaceable().noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_COPPER_ENOKI = registerBlockNoItem("potted_copper_enoki",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HoleBlocks.COPPER_ENOKI, BlockBehaviour
                    .Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));

    public static final RegistryObject<Block> BLUE_TRUMPET = register("blue_trumpet", () ->
            new HoleMushroomBlock(BlockBehaviour.Properties
                    .of().mapColor(MapColor.TERRACOTTA_YELLOW)
                    .replaceable().noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_BLUE_TRUMPET = registerBlockNoItem("potted_blue_trumpet",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HoleBlocks.BLUE_TRUMPET, BlockBehaviour
                    .Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));

    public static final RegistryObject<Block> PRINCESS_JELLY = register("princess_jelly", () ->
            new HoleMushroomBlock(BlockBehaviour.Properties
                    .of().mapColor(MapColor.COLOR_PINK)
                    .replaceable().noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .ignitedByLava()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> POTTED_PRINCESS_JELLY = registerBlockNoItem("potted_princes_jelly",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, HoleBlocks.PRINCESS_JELLY, BlockBehaviour
                    .Properties.copy(Blocks.POTTED_DANDELION).noOcclusion()));


    public static <T extends Block> RegistryObject<T> register(String name, Supplier<Block> block) {
        RegistryObject<? extends Block> ret = BLOCKS.register(name, block);
        HoleItems.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties()));
        return (RegistryObject<T>) ret;
    }

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        HoleItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static <B extends Block> RegistryObject<B> registerBlockNoItem(String name, Supplier<? extends B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        return block;
    }

}
