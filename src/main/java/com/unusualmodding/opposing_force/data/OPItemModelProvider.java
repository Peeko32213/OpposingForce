package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

import static com.unusualmodding.opposing_force.registry.OPItems.*;

public class OPItemModelProvider extends ItemModelProvider {

    public OPItemModelProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), OpposingForce.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void registerModels() {

        this.item(DEEP_SILK);
        this.item(ELECTRIC_CHARGE);
        this.item(SLUG_EGGS);
        this.item(CAPTURED_WHIZZ);
        this.item(DEEPWOVEN_HAT);
        this.item(DEEPWOVEN_TUNIC);
        this.item(DEEPWOVEN_PANTS);
        this.item(DEEPWOVEN_BOOTS);
        this.item(UMBER_FANG);
        this.item(DICER_LENS);

        this.handheldItem(EMERALD_SWORD);
        this.handheldItem(EMERALD_PICKAXE);
        this.handheldItem(EMERALD_AXE);
        this.handheldItem(EMERALD_SHOVEL);
        this.handheldItem(EMERALD_HOE);

//        item(WOODEN_MASK);
//        item(WOODEN_CHESTPLATE);
//        item(WOODEN_COVER);
//        item(WOODEN_BOOTS);
        this.item(EMERALD_MASK);
        this.item(EMERALD_CHESTPLATE);
        this.item(EMERALD_LEGGINGS);
        this.item(EMERALD_BOOTS);
        this.item(STONE_HELMET);
        this.item(STONE_CHESTPLATE);
        this.item(STONE_LEGGINGS);
        this.item(STONE_BOOTS);

        // spawn eggs
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof SpawnEggItem && Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getNamespace().equals(OpposingForce.MOD_ID)) {
                getBuilder(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath()).parent(getExistingFile(new ResourceLocation("item/template_spawn_egg")));
            }
        }
    }

    // item
    private ItemModelBuilder item(RegistryObject<?> item) {
        return generated(item.getId().getPath(), modLoc("item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<?> item) {
        return handheld(item.getId().getPath(), modLoc("item/" + item.getId().getPath()));
    }

    // utils
    private ItemModelBuilder generated(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private ItemModelBuilder handheld(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/handheld");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }
}
