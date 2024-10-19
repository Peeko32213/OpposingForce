package com.peeko32213.hole.core.datagen;

import com.mojang.logging.LogUtils;
import com.peeko32213.hole.Hole;
import com.peeko32213.hole.core.registry.HoleBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static com.peeko32213.hole.Hole.prefix;
import static net.minecraftforge.client.model.generators.ModelProvider.BLOCK_FOLDER;

public class BlockstateGenerator extends BlockStateProvider {
    public BlockstateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Hole.MODID, exFileHelper);
    }

    private static final Logger LOGGER = LogUtils.getLogger();

    private String blockName(Block block) {
        return block.getLootTable().getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(Hole.MODID, "block/" + path);
    }

    public ModelFile existingModel(Block block) {
        return new ModelFile.ExistingModelFile(resourceBlock(blockName(block)), models().existingFileHelper);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
    }

    public void simpleBlockItem(Block block, ModelFile model) {
        itemModels().getBuilder(key(block).getPath()).parent(model);
    }

    @Override
    protected void registerStatesAndModels() {
        /**Example**/
        //simpleBlock(UPBlocks.STRIPPED_GINKGO_LOG.get());
        //logBlock(UPBlocks.GINKGO_LOG.get());
        //simpleBlockItem(UPBlocks.STRIPPED_PETRIFIED_WOOD.get(), existingModel((getName(UPBlocks.STRIPPED_PETRIFIED_WOOD.get()))));
        //trapdoorBlock(UPBlocks.PETRIFIED_WOOD_TRAPDOOR.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_wood_trapdoor"), true);
        //slabBlock(UPBlocks.PETRIFIED_WOOD_SLAB.get(), resourceBlock(getName(UPBlocks.PETRIFIED_WOOD_PLANKS.get())), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        //stairsBlock(UPBlocks.PETRIFIED_WOOD_STAIRS.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        //doorBlock(UPBlocks.PETRIFIED_WOOD_DOOR.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_wood_door_bottom"),new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_wood_door_top"));
        //pressurePlateBlock(UPBlocks.PETRIFIED_WOOD_PRESSURE_PLATE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        //buttonBlock(UPBlocks.PETRIFIED_WOOD_BUTTON.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        //fenceBlock(UPBlocks.PETRIFIED_WOOD_FENCE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        //fenceGateBlock(UPBlocks.PETRIFIED_WOOD_FENCE_GATE.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_planks"));
        //signBlock((StandingSignBlock) UPBlocks.PETRIFIED_WOOD_SIGN.get(), (WallSignBlock) UPBlocks.PETRIFIED_WOOD_WALL_SIGN.get(), new ResourceLocation(UnusualPrehistory.MODID, "block/petrified_wood_sign"));

        createPottedPlant(HoleBlocks.CAVE_PATTY, HoleBlocks.POTTED_CAVE_PATTY, "cutout");
        createPottedPlant(HoleBlocks.COPPER_ENOKI, HoleBlocks.POTTED_COPPER_ENOKI, "cutout");
        createPottedPlant(HoleBlocks.RAINCAP, HoleBlocks.POTTED_RAINCAP, "cutout");
        createPottedPlant(HoleBlocks.CREAM_CAP, HoleBlocks.POTTED_CREAM_CAP, "cutout");
        createPottedPlant(HoleBlocks.CHICKEN_OF_THE_CAVES, HoleBlocks.POTTED_CHICKEN_OF_THE_CAVES, "cutout");
        createPottedPlant(HoleBlocks.PRINCESS_JELLY, HoleBlocks.POTTED_PRINCESS_JELLY, "cutout");
        createPottedPlant(HoleBlocks.BLUE_TRUMPET, HoleBlocks.POTTED_BLUE_TRUMPET, "cutout");
        createPottedPlant(HoleBlocks.POWDER_GNOME, HoleBlocks.POTTED_POWDER_GNOME, "cutout");
    }



    private void createPottedPlant(RegistryObject<Block> plant, RegistryObject<Block> pottedPlant, String renderType){
        ConfiguredModel cFfile = new ConfiguredModel(pottedPlant(name(pottedPlant.get()), blockTexture(plant.get()), renderType));
        getVariantBuilder(pottedPlant.get()).partialState().setModels(cFfile);
        //impleBlockItem(plant.get(), file);
    }

    public ModelFile pottedPlant(String name, ResourceLocation plant, String renderType) {
        return singleTexture(name, BLOCK_FOLDER + "/flower_pot_cross", "plant", plant, renderType);
    }


    private void createWallFan(RegistryObject<Block> b, String renderType) {
        ModelFile file = new ConfiguredModel(wallCoral(name(b.get()), blockTexture(b.get()), renderType)).model;
        getVariantBuilder(b.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(file)
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .build()
                );
        simpleBlockItem(b.get(), file);
    }




    private void createTintedCross(RegistryObject<Block> b, String renderType) {
        getVariantBuilder(b.get()).partialState().setModels(new ConfiguredModel(tintedCross(name(b.get()), blockTexture(b.get()), renderType)));
    }

    public ModelFile tintedCross(String name, ResourceLocation cross, String renderType) {
        return singleTexture(name, BLOCK_FOLDER + "/tinted_cross", "cross", cross, renderType);
    }
    private void createDoubleCross(RegistryObject<Block> b, String renderType) {
        String baseName = name(b.get());
        getVariantBuilder(b.get()).forAllStatesExcept(e -> {
            DoubleBlockHalf val = e.getValue(DoublePlantBlock.HALF);
            String mod = val == DoubleBlockHalf.LOWER ? "bottom" : "top";
            ResourceLocation texture = prefix("block/" + baseName + "_" + mod);
            ModelFile text = cross(baseName + "_" + mod, texture, renderType);
            return ConfiguredModel.builder().modelFile(text).build();
        });

        //ModelFile file = models().getExistingFile(prefix(baseName + "_top"));
        singleTex(b.get(), baseName + "_top");
    }

    private void createCross(RegistryObject<Block> b, String renderType) {
        ConfiguredModel cFfile = new ConfiguredModel(cross(name(b.get()), blockTexture(b.get()), renderType));
        ModelFile file = cFfile.model;
        String baseName = name(b.get());
        getVariantBuilder(b.get()).partialState().setModels(cFfile);

        singleTex(b.get(), baseName);
        //simpleBlockItem(b.get(), file);
    }

    public ModelFile cross(String name, ResourceLocation cross, String renderType) {
        return singleTexture(name, BLOCK_FOLDER + "/cross", "cross", cross, renderType);
    }

    private ModelFile singleTexture(String name, String parent, String textureKey, ResourceLocation texture, String renderType) {
        return singleTexture(name, mcLoc(parent), textureKey, texture, renderType);
    }

    public ModelFile wallCoral(String name, ResourceLocation fan, String renderType) {
        return singleTexture(name, BLOCK_FOLDER + "/coral_wall_fan", "fan", fan, renderType);
    }

    private void createCoralFan(RegistryObject<Block> b, String renderType) {
        getVariantBuilder(b.get()).partialState().setModels(new ConfiguredModel(coralFan(name(b.get()), blockTexture(b.get()), renderType)));
        singleTex(b.get());
    }

    public ModelFile coralFan(String name, ResourceLocation fan, String renderType) {
        return singleTexture(name, BLOCK_FOLDER + "/coral_fan", "fan", fan, renderType);
    }

    public ModelFile singleTexture(String name, ResourceLocation parent, String textureKey, ResourceLocation texture, String renderType) {
        return models().withExistingParent(name, parent)
                .texture(textureKey, texture).renderType(renderType);
    }

    private BlockModelBuilder generatedWall(String name, ResourceLocation... layers) {
        BlockModelBuilder builder = models().withExistingParent("block/" + name, "block/wall_inventory");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private BlockModelBuilder generatedSlab(String name, ResourceLocation... layers) {
        BlockModelBuilder builder = models().withExistingParent("block/" + name, "block/slab");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private void blockWithTop(RegistryObject<Block> blockRegistryObject) {
        horizontalBlock(blockRegistryObject.get(), prefix("block/" + key(blockRegistryObject.get()).getPath()), prefix("block/" + key(blockRegistryObject.get()).getPath()), prefix("block/" + key(blockRegistryObject.get()).getPath() + "_top"));
        simpleBlockItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private BlockModelBuilder singleTexItem(Block block, ResourceLocation name) {
        return generated(name(block), name);
    }
    private BlockModelBuilder singleTexItem(Block block) {
        return generated(name(block), new ResourceLocation(Hole.MODID, "item/" + name(block)));
    }

    private BlockModelBuilder singleTex(Block block) {
        return singleTex(block,  name(block));
    }

    private BlockModelBuilder singleTex(Block block, String name) {
        return generated(name(block), new ResourceLocation(Hole.MODID, "block/" + name));
    }

    private BlockModelBuilder generated(String name, ResourceLocation... layers) {
        BlockModelBuilder builder = models().withExistingParent("item/" + name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }


    public ModelFile createFlatWaterEgg(Block block, String modifier){
        String baseName = getName(block);
        return models().singleTexture("block/eggs/" + modifier + baseName.replace(Hole.MODID + ":", ""), new ResourceLocation(Hole.MODID, "block/template_eggs/template_flat_water_egg"), blockTextureEggs(block));
    }

    public ResourceLocation blockTextureEggs(Block block) {
        ResourceLocation name = key(block);
        return new ResourceLocation(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/eggs/" + name.getPath());
    }
    
    private String name(Block block) {
        return key(block).getPath();
    }

    private String getName(Block block) {
        return key(block).toString().replace(Hole.MODID + ":", "");
    }


    private Block getBlock(ResourceLocation resourceLocation) {
        return BuiltInRegistries.BLOCK.get(resourceLocation);
    }

    @Override
    public String getName() {
        return "Block States: " + Hole.MODID;
    }
}
