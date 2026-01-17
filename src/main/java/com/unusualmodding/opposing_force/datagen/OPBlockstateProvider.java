package com.unusualmodding.opposing_force.datagen;

import com.mojang.datafixers.util.Pair;
import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static com.unusualmodding.opposing_force.registry.OPBlocks.*;

public class OPBlockstateProvider extends BlockStateProvider {

    public OPBlockstateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, OpposingForce.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.cubeAllBlock(GUZZLER_SCALE_BLOCK);
        this.cubeAllBlock(GUZZLER_SCALE_BRICKS);
        this.stairs(GUZZLER_SCALE_BRICK_STAIRS, this.blockTexture(GUZZLER_SCALE_BRICKS.get()));
        this.slab(GUZZLER_SCALE_BRICK_SLAB, this.blockTexture(GUZZLER_SCALE_BRICKS.get()));

        this.cubeAllBlock(TREMBLING_GUZZLER_SCALE_BRICKS);
        this.stairs(TREMBLING_GUZZLER_SCALE_BRICK_STAIRS, this.blockTexture(TREMBLING_GUZZLER_SCALE_BRICKS.get()));
        this.slab(TREMBLING_GUZZLER_SCALE_BRICK_SLAB, this.blockTexture(TREMBLING_GUZZLER_SCALE_BRICKS.get()));

        this.cubeAllBlock(TREMBLING_GUZZLER_SCALE_SHINGLES);
        this.stairs(TREMBLING_GUZZLER_SCALE_SHINGLE_STAIRS, this.blockTexture(TREMBLING_GUZZLER_SCALE_SHINGLES.get()));
        this.slab(TREMBLING_GUZZLER_SCALE_SHINGLE_SLAB, this.blockTexture(TREMBLING_GUZZLER_SCALE_SHINGLES.get()));

        this.cubeAllBlock(TREMBLING_BLOCK);
        this.cubeAllBlock(TREMBLING_SHINGLES);
        this.stairs(TREMBLING_SHINGLE_STAIRS, this.blockTexture(TREMBLING_SHINGLES.get()));
        this.slab(TREMBLING_SHINGLE_SLAB, this.blockTexture(TREMBLING_SHINGLES.get()));
        this.simpleCross(DEEP_WEB);
        this.cubeAllBlock(DEEP_SILK_BLOCK);

        this.cubeAllBlock(VILE_STONE);
        this.stairs(VILE_STONE_STAIRS, this.blockTexture(VILE_STONE.get()));
        this.slab(VILE_STONE_SLAB, this.blockTexture(VILE_STONE.get()));

        this.cubeAllBlock(VILE_COBBLESTONE);
        this.stairs(VILE_COBBLESTONE_STAIRS, this.blockTexture(VILE_COBBLESTONE.get()));
        this.slab(VILE_COBBLESTONE_SLAB, this.blockTexture(VILE_COBBLESTONE.get()));
        this.wall(VILE_COBBLESTONE_WALL, this.blockTexture(VILE_COBBLESTONE.get()));

        this.cubeAllBlock(VILE_STONE_BRICKS);
        this.stairs(VILE_STONE_BRICK_STAIRS, this.blockTexture(VILE_STONE_BRICKS.get()));
        this.slab(VILE_STONE_BRICK_SLAB, this.blockTexture(VILE_STONE_BRICKS.get()));
        this.wall(VILE_STONE_BRICK_WALL, this.blockTexture(VILE_STONE_BRICKS.get()));
        this.cubeAllBlock(CHISELED_VILE_STONE_BRICKS);

        this.pottedPlant(APPLE_SAPLING, POTTED_APPLE_SAPLING);

        this.mobHead(DICER_HEAD);
        this.mobHead(FROWZY_HEAD);
        this.mobHead(ANGRY_RAMBLER_SKULL);
        this.mobHead(CLASSIC_RAMBLER_SKULL);
        this.mobHead(EVIL_RAMBLER_SKULL);
        this.mobHead(GRINNING_RAMBLER_SKULL);
        this.mobHead(SKELETAL_RAMBLER_SKULL);
        this.mobHead(SMILING_RAMBLER_SKULL);
        this.mobHead(STRANGE_RAMBLER_SKULL);
        this.mobHead(CRUNDLY_RAMBLER_SKULL);
        this.mobHead(DWARVEN_RAMBLER_SKULL);
        this.mobHead(IMPRISONED_RAMBLER_SKULL);
        this.mobHead(INDOMITABLE_RAMBLER_SKULL);
        this.mobHead(LEERING_RAMBLER_SKULL);
        this.mobHead(MAGMATIC_RAMBLER_SKULL);
        this.mobHead(MUSICAL_RAMBLER_SKULL);
        this.mobHead(NOSY_RAMBLER_SKULL);
        this.mobHead(VALIANT_RAMBLER_SKULL);
        this.mobHead(SKYVERN_HEAD);
        this.mobHead(TART_HEAD);
        this.mobHead(WHIZZ_HEAD);
    }

    @Override
    public @NotNull String getName() {
        return "Block States: " + OpposingForce.MOD_ID;
    }

    // item
    private void itemModel(RegistryObject<Block> block) {
        this.itemModels().withExistingParent(getItemName(block.get()), this.blockTexture(block.get()));
    }

    private void generatedItem(ItemLike item, TextureFolder folder) {
        String name = getItemName(item);
        this.itemModels().withExistingParent(name, "item/generated").texture("layer0", this.modLoc(folder.format(name)));
    }

    // block
    private void cubeAllBlock(RegistryObject<Block> block) {
        this.simpleBlock(block.get());
        this.itemModel(block);
    }

    private void stairs(RegistryObject<Block> stairs, ResourceLocation texture) {
        this.stairsBlock((StairBlock) stairs.get(), texture);
        this.itemModel(stairs);
    }

    private void slab(RegistryObject<Block> slab, ResourceLocation texture) {
        this.slabBlock((SlabBlock) slab.get(), texture, texture);
        this.itemModel(slab);
    }

    private void wall(RegistryObject<Block> wall, ResourceLocation texture) {
        this.wallBlock((WallBlock) wall.get(), texture);
        this.itemModels().wallInventory(getItemName(wall.get()), texture);
    }

    private void simpleCross(RegistryObject<Block> block) {
        this.simpleBlock(block.get(), this.models().cross(getItemName(block.get()), this.blockTexture(block.get())).renderType("cutout"));
        this.generatedItem(block.get(), TextureFolder.BLOCK);
    }


    private void mobHead(Pair<RegistryObject<Block>, RegistryObject<Block>> skull) {
        this.getVariantBuilder(skull.getFirst().get()).forAllStatesExcept(blockstate -> ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/" + "skull"))).build(), SkullBlock.ROTATION);
        this.getVariantBuilder(skull.getSecond().get()).forAllStatesExcept(blockstate -> ConfiguredModel.builder().modelFile(models().getExistingFile(new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/" + "skull"))).build(), WallSkullBlock.FACING);
    }

    private void pot(RegistryObject<Block> pot, ResourceLocation texture) {
        ModelFile model = this.models().withExistingParent(getBlockName(pot.get()), "block/flower_pot_cross").texture("plant", texture).renderType("cutout");
        this.simpleBlock(pot.get(), model);
    }

    private void pottedPlant(RegistryObject<Block> plant, RegistryObject<Block> pot) {
        this.pot(pot, this.blockTexture(plant.get()));
        this.simpleCross(plant);
        this.generatedItem(plant.get(), TextureFolder.BLOCK);
    }

    // utils
    private static String getItemName(ItemLike item) {
        return ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
    }

    private static String getBlockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    private enum TextureFolder {
        ITEM, BLOCK;
        public String format(String itemName) {
            return this.name().toLowerCase() + '/' + itemName;
        }
    }
}
