package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

import static com.unusualmodding.opposing_force.registry.OPBlocks.*;

public class OPBlockstateProvider extends BlockStateProvider {

    public OPBlockstateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, OpposingForce.MOD_ID, exFileHelper);
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(OpposingForce.MOD_ID, "block/" + path);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
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

    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private String getName(Block block) {
        return key(block).toString().replace(OpposingForce.MOD_ID + ":", "");
    }

    public void block(Block block) {
        this.simpleBlock(block);
        this.blockItem(block);
    }

    public void block(RegistryObject<Block> block) {
        this.block(block.get());
    }

    public void blockItem(Block block) {
        this.simpleBlockItem(block, new ModelFile.ExistingModelFile(blockTexture(block), this.models().existingFileHelper));
    }

    public void blockItem(RegistryObject<Block> block) {
        this.blockItem(block.get());
    }

    public void directionalBlock(RegistryObject<Block> block, ResourceLocation sideTexture, ResourceLocation bottomTexture, ResourceLocation topTexture) {
        this.directionalBlock(block.get(), models().cubeBottomTop(name(block.get()), sideTexture, bottomTexture, topTexture));
        this.blockItem(block);
    }

    public void directionalBlock(RegistryObject<Block> block) {
        ResourceLocation blockTexture = blockTexture(block.get());
        this.directionalBlock(block, suffix(blockTexture, "_side"), suffix(blockTexture, "_bottom"), suffix(blockTexture, "_top"));
    }

    public static ResourceLocation suffix(ResourceLocation resourceLocation, String suffix) {
        return new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath() + suffix);
    }

    @Override
    public String getName() {
        return "Block States: " + OpposingForce.MOD_ID;
    }

    // updated

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

    private void cubeAllBlockWithRenderType(RegistryObject<Block> block, String renderType) {
        this.simpleBlock(block.get(), models().getBuilder(name(block.get())).parent(new ModelFile.UncheckedModelFile(new ResourceLocation("block/cube_all"))).renderType(renderType).texture("all", blockTexture(block.get())));
        this.itemModel(block);
    }

    private void glassBlock(RegistryObject<Block> block) {
        this.simpleBlock(block.get(), models().getBuilder(name(block.get())).parent(new ModelFile.UncheckedModelFile(new ResourceLocation("block/cube_all"))).renderType("translucent").texture("all", blockTexture(block.get())));
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

    private void pillar(RegistryObject<Block> pillar) {
        this.axisBlock((RotatedPillarBlock) pillar.get(), this.blockTexture(pillar.get()), this.modLoc("block/" + getItemName(pillar.get()) + "_top"));
        this.itemModel(pillar);
    }

    public void pillarWithBottom(RegistryObject<Block> pillar) {
        ResourceLocation blockTexture = this.blockTexture(pillar.get());
        this.directionalBlock(pillar, suffix(blockTexture, "_side"), suffix(blockTexture, "_bottom"), suffix(blockTexture, "_top"));
        this.itemModel(pillar);
    }

    private void pillarNoTop(RegistryObject<Block> pillar) {
        this.axisBlock((RotatedPillarBlock) pillar.get(), this.blockTexture(pillar.get()), this.modLoc("block/" + getItemName(pillar.get()) + "_top"));
        this.itemModel(pillar);
    }

    private void glassPane(RegistryObject<Block> pane, ResourceLocation texture) {
        this.paneBlockWithRenderType((IronBarsBlock) pane.get(), texture, this.modLoc("block/" + getItemName(pane.get()) + "_top"), "translucent");
        this.itemModels().withExistingParent(getItemName(pane.get()), "item/generated").texture("layer0", this.modLoc("block/" + getItemName(pane.get()).replace("_pane", "")));
    }

    private void wood(RegistryObject<Block> log, ResourceLocation texture) {
        this.axisBlock((RotatedPillarBlock) log.get(), texture, texture);
        this.itemModel(log);
    }

    private void fence(RegistryObject<Block> fence, ResourceLocation texture) {
        this.fenceBlock((FenceBlock) fence.get(), texture);
        this.itemModels().fenceInventory(getItemName(fence.get()), texture);
    }

    private void fenceGate(RegistryObject<Block> gate, ResourceLocation texture) {
        this.fenceGateBlock((FenceGateBlock) gate.get(), texture);
        this.itemModel(gate);
    }

    private void trapdoor(RegistryObject<Block> trapdoor) {
        this.trapdoorBlock((TrapDoorBlock) trapdoor.get(), this.blockTexture(trapdoor.get()), true);
        this.itemModels().withExistingParent(getItemName(trapdoor.get()), this.modLoc("block/" + getItemName(trapdoor.get()) + "_bottom"));
    }

    private void trapdoorCutout(RegistryObject<Block> trapdoor) {
        this.trapdoorBlockWithRenderType((TrapDoorBlock) trapdoor.get(), this.blockTexture(trapdoor.get()), true, "cutout");
        this.itemModels().withExistingParent(getItemName(trapdoor.get()), this.modLoc("block/" + getItemName(trapdoor.get()) + "_bottom"));
    }

    private void door(RegistryObject<Block> door) {
        String name = getItemName(door.get());
        this.doorBlock((DoorBlock) door.get(), name.replace("_door", ""), this.modLoc("block/" + name + "_bottom"), this.modLoc("block/" + name + "_top"));
        this.generatedItem(door.get(), TextureFolder.ITEM);
    }

    private void doorCutout(RegistryObject<Block> door) {
        String name = getItemName(door.get());
        this.doorBlockWithRenderType((DoorBlock) door.get(), name.replace("_door", ""), this.modLoc("block/" + name + "_bottom"), this.modLoc("block/" + name + "_top"), "cutout");
        this.generatedItem(door.get(), TextureFolder.ITEM);
    }

    private void button(RegistryObject<Block> button, ResourceLocation texture) {
        this.buttonBlock((ButtonBlock) button.get(), texture);
        this.itemModels().buttonInventory(getItemName(button.get()), texture);
    }

    private void pressurePlate(RegistryObject<Block> pressurePlate, ResourceLocation texture) {
        this.pressurePlateBlock((PressurePlateBlock) pressurePlate.get(), texture);
        this.itemModel(pressurePlate);
    }

    private void leaves(RegistryObject<Block> leaves) {
        this.simpleBlock(leaves.get(), this.models().withExistingParent(getItemName(leaves.get()), "block/leaves").texture("all", this.blockTexture(leaves.get())));
        this.itemModel(leaves);
    }

    private void simpleCross(RegistryObject<Block> block) {
        this.simpleBlock(block.get(), this.models().cross(getItemName(block.get()), this.blockTexture(block.get())).renderType("cutout"));
        this.generatedItem(block.get(), TextureFolder.BLOCK);
    }

    private void tallPlant(RegistryObject<Block> flower) {
        String name = getItemName(flower.get());
        Function<String, ModelFile> model = s -> this.models().cross(name + "_" + s, this.modLoc("block/" + name + "_" + s)).renderType("cutout");
        this.itemModels().withExistingParent(name, "item/generated").texture("layer0", this.modLoc("block/" + name + "_top"));
        this.getVariantBuilder(flower.get()).partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(model.apply("top"))).partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(model.apply("bottom")));
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

    private void randomBlock(RegistryObject<Block> block, int variants) {
        String name = getBlockName(block.get());
        Function<Integer, ModelFile> model = i -> this.models().cubeAll(name + "_" + i, this.modLoc("block/" + name + "_" + i));
        for (int j = 0; j <= variants - 1; j++) {
            this.getVariantBuilder(block.get()).partialState().addModels(new ConfiguredModel(model.apply(j)));
        }
        this.itemModels().withExistingParent(name, this.modLoc("block/" + name + "_0"));
    }

    private void floweringJungleLeaves(RegistryObject<Block> leaves) {
        String name = getItemName(leaves.get());
        ResourceLocation base_texture = this.blockTexture(Blocks.JUNGLE_LEAVES);
        ResourceLocation flowers_0 = new ResourceLocation(this.blockTexture(leaves.get()).getNamespace(), this.blockTexture(leaves.get()).getPath() + "_overlay_0");
        ResourceLocation flowers_1 = new ResourceLocation(this.blockTexture(leaves.get()).getNamespace(), this.blockTexture(leaves.get()).getPath() + "_overlay_1");
        ModelFile flowering_leaves_0 = this.models().withExistingParent(name + "_0", "seafarer:block/overlay_leaves").texture("all", base_texture).texture("overlay", flowers_0);
        ModelFile flowering_leaves_1 = this.models().withExistingParent(name + "_1", "seafarer:block/overlay_leaves").texture("all", base_texture).texture("overlay", flowers_1);
        this.itemModels().withExistingParent(name, this.modLoc("block/" + name + "_0"));
        this.getVariantBuilder(leaves.get()).partialState().addModels(new ConfiguredModel(flowering_leaves_0), new ConfiguredModel(flowering_leaves_1));
    }

    private void emissiveOverlayBlock(RegistryObject<Block> block) {
        ResourceLocation texture = this.blockTexture(block.get());
        ResourceLocation glow = new ResourceLocation(texture.getNamespace(), texture.getPath() + "_glow");
        this.simpleBlock(block.get(), this.models().withExistingParent(getItemName(block.get()), "seafarer:block/emissive_block").texture("all", texture).texture("glow", glow));
        this.itemModel(block);
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
