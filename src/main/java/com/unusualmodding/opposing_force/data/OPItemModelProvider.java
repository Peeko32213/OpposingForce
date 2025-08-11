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

        item(DEEP_SILK);
        item(ELECTRIC_CHARGE);
        item(SLUG_EGGS);
        item(CAPTURED_WHIZZ);
        item(DEEPWOVEN_HAT);
        item(DEEPWOVEN_TUNIC);
        item(DEEPWOVEN_PANTS);
        item(DEEPWOVEN_BOOTS);
        item(UMBER_FANG);

        handheldItem(EMERALD_SWORD);
        handheldItem(EMERALD_PICKAXE);
        handheldItem(EMERALD_AXE);
        handheldItem(EMERALD_SHOVEL);
        handheldItem(EMERALD_HOE);

//        item(WOODEN_MASK);
//        item(WOODEN_CHESTPLATE);
//        item(WOODEN_COVER);
//        item(WOODEN_BOOTS);
        item(EMERALD_MASK);
        item(EMERALD_CHESTPLATE);
        item(EMERALD_LEGGINGS);
        item(EMERALD_BOOTS);
//        item(STONE_HELMET);
//        item(STONE_CHESTPLATE);
//        item(STONE_LEGGINGS);
//        item(STONE_BOOTS);

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
