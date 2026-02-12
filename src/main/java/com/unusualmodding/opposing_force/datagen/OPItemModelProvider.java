package com.unusualmodding.opposing_force.datagen;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Supplier;

import static com.unusualmodding.opposing_force.registry.OPItems.*;

public class OPItemModelProvider extends ItemModelProvider {

    public OPItemModelProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), OpposingForce.MOD_ID, event.getExistingFileHelper());
    }

    @Override
    protected void registerModels() {

        this.item(OPPOSING_FORCE);

        // Dicer
        this.item(DICER_LENS);
        this.item(LASER_CORE);

        // Fire Slime
        this.item(FIRE_BOMB);

        // Frowzy

        // Guzzler
        this.item(GUZZLER_SCALES);
        this.item(OPBlocks.INFERNO_PIE);
        this.item(INFERNO_PIE_SLICE);

        // Hanging Spider

        // Ladybug
        this.item(LADYBUG_HUSK);

        // Rambler
        this.item(HEAVY_BONE);
        this.handheldItem(BONE_SWORD);
        this.handheldItem(BONE_PICKAXE);
        this.handheldItem(BONE_AXE);
        this.handheldItem(BONE_SHOVEL);
        this.handheldItem(BONE_HOE);
        this.item(BONE_HELMET);
        this.item(BONE_CHESTPLATE);
        this.item(BONE_LEGGINGS);
        this.item(BONE_BOOTS);

        // Skyvern
        this.item(SKYVERN_CLAW);
        this.item(SKYVERN_EGG);

        // Slug
        this.item(OPBlocks.SLUG_EGGS);
        this.item(SLUG_BARON_HELMET);
        this.item(SLUG_BARON_CHESTPLATE);
        this.item(SLUG_BARON_LEGGINGS);
        this.item(SLUG_BARON_BOOTS);

        // Tart
        this.item(RAW_TART);
        this.item(COOKED_TART);

        // Terror
        this.handheldItem(TERROR_LEG);
        this.handheldItem(FRIED_TERROR_LEG);
        this.handheldItem(SPICY_TERROR_LEG);
        this.handheldItem(TERROR_SAW);

        // Trembler
        this.item(OPBlocks.TREMBLER_SHELL);

        // Umber Spider
        this.item(DEEP_SILK);
        this.item(DEEPWOVEN_HAT);
        this.item(DEEPWOVEN_TUNIC);
        this.item(DEEPWOVEN_PANTS);
        this.item(DEEPWOVEN_BOOTS);
        this.item(UMBER_FANG);
        this.handheldItem(UMBER_DAGGER);

        // Volt
        this.item(ELECTRIC_CHARGE);
        this.item(ELECTRIC_ALLOY);
        this.item(LIGHTNING_BOMB);
        this.item(RECON_KNIGHT_HELMET);
        this.item(RECON_KNIGHT_CHESTPLATE);
        this.item(RECON_KNIGHT_LEGGINGS);
        this.item(RECON_KNIGHT_BOOTS);

        // Whizz
        this.item(CAPTURED_WHIZZ);
        this.item(WHIZZ_BOMB);

        // Emerald
        this.handheldItem(EMERALD_SWORD);
        this.handheldItem(EMERALD_PICKAXE);
        this.handheldItem(EMERALD_AXE);
        this.handheldItem(EMERALD_SHOVEL);
        this.handheldItem(EMERALD_HOE);
        this.item(EMERALD_MASK);
        this.item(EMERALD_CHESTPLATE);
        this.item(EMERALD_LEGGINGS);
        this.item(EMERALD_BOOTS);

        // Wooden
        this.item(WOODEN_MASK);
        this.item(WOODEN_CHESTPLATE);
        this.item(WOODEN_COVER);
        this.item(WOODEN_BOOTS);

        // Stone
        this.item(STONE_HELMET);
        this.item(STONE_CHESTPLATE);
        this.item(STONE_LEGGINGS);
        this.item(STONE_BOOTS);

        // Treasure
        this.item(LEAPING_LEGGINGS);
        this.item(MOON_SHOES);

        // Misc
        this.handheldItem(TOMAHAWK);
        this.item(KINETIC_BOMB);
        this.item(DONUT);

        // Music Discs
        this.item(WALTZ_OF_THE_SLUG_DISC);

        // spawn eggs
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof SpawnEggItem && Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getNamespace().equals(OpposingForce.MOD_ID)) {
                this.getBuilder(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath()).parent(getExistingFile(new ResourceLocation("item/template_spawn_egg")));
            }
        }

        OPBlocks.MOB_HEADS.forEach(this::mobHeads);
    }

    // item
    private void item(RegistryObject<?> item) {
        this.generated(item.getId().getPath(), modLoc("item/" + item.getId().getPath()));
    }

    private void handheldItem(RegistryObject<?> item) {
        this.handheld(item.getId().getPath(), modLoc("item/" + item.getId().getPath()));
    }

    private void mobHeads(Supplier<? extends Block> skull) {
        this.getBuilder(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(skull.get())).getPath()).parent(getExistingFile(new ResourceLocation("item/template_skull")));
    }

    // utils
    private void generated(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/generated");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
    }

    private void handheld(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, "item/handheld");
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
    }
}
