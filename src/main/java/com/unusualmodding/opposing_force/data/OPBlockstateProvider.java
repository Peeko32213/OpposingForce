package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.unusualmodding.opposing_force.registry.OPBlocks.*;

public class OPBlockstateProvider extends BlockStateProvider {

    public OPBlockstateProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), OpposingForce.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void registerStatesAndModels() {
        this.pottedPlant(CAVE_PATTY, POTTED_CAVE_PATTY);
        this.pottedPlant(COPPER_ENOKI, POTTED_COPPER_ENOKI);
        this.pottedPlant(RAINCAP, POTTED_RAINCAP);
        this.pottedPlant(CREAM_CAP, POTTED_CREAM_CAP);
        this.pottedPlant(CHICKEN_OF_THE_CAVES, POTTED_CHICKEN_OF_THE_CAVES);
        this.pottedPlant(PRINCESS_JELLY, POTTED_PRINCESS_JELLY);
        this.pottedPlant(BLUE_TRUMPET, POTTED_BLUE_TRUMPET);
        this.pottedPlant(POWDER_GNOME, POTTED_POWDER_GNOME);

        this.pottedPlant(BLACKCAP, POTTED_BLACKCAP);
        this.pottedPlant(CAP_OF_EYE, POTTED_CAP_OF_EYE);
        this.pottedPlant(GREEN_FUNK, POTTED_GREEN_FUNK);
        this.pottedPlant(LIME_NUB, POTTED_LIME_NUB);
        this.pottedPlant(POP_CAP, POTTED_POP_CAP);
        this.pottedPlant(PURPLE_KNOB, POTTED_PURPLE_KNOB);
        this.pottedPlant(QUEEN_IN_MAGENTA, POTTED_QUEEN_IN_MAGENTA);
        this.pottedPlant(SLATESHROOM, POTTED_SLATESHROOM);
        this.pottedPlant(SLIPPERY_TOP, POTTED_SLIPPERY_TOP);
        this.pottedPlant(WHITECAP, POTTED_WHITECAP);
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

    private void pottedPlant(RegistryObject<Block> plant, RegistryObject<Block> pot) {
        this.pot(pot, this.blockTexture(plant.get()));
        this.simpleCross(plant);
        this.generatedItem(plant.get(), TextureFolder.BLOCK);
    }

    private void simpleCross(RegistryObject<Block> block) {
        this.simpleBlock(block.get(), this.models().cross(getItemName(block.get()), this.blockTexture(block.get())).renderType("cutout"));
    }

    private void pot(RegistryObject<Block> pot, ResourceLocation texture) {
        ModelFile model = this.models().withExistingParent(getBlockName(pot.get()), "block/flower_pot_cross").texture("plant", texture).renderType("cutout");
        this.simpleBlock(pot.get(), model);
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
