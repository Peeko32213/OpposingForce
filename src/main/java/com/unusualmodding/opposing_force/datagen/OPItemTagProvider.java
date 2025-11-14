package com.unusualmodding.opposing_force.datagen;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPBlocks;
import com.unusualmodding.opposing_force.registry.tags.OPItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.unusualmodding.opposing_force.registry.OPItems.*;

public class OPItemTagProvider extends ItemTagsProvider {

    public OPItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> lookup, ExistingFileHelper helper) {
        super(output, provider, lookup, OpposingForce.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(OPItemTags.KNIVES).add(
                UMBER_DAGGER.get()
        ).addOptionalTag(new ResourceLocation("farmersdelight", "tools/knives"));

        this.tag(OPItemTags.PIE_CUTTERS).addTag(OPItemTags.KNIVES).addTag(ItemTags.SWORDS);

        this.tag(OPItemTags.RAMBLER_SKULLS).add(
                ANGRY_RAMBLER_SKULL.get(),
                CLASSIC_RAMBLER_SKULL.get(),
                EVIL_RAMBLER_SKULL.get(),
                GRINNING_RAMBLER_SKULL.get(),
                SKELETAL_RAMBLER_SKULL.get(),
                SMILING_RAMBLER_SKULL.get(),
                STRANGE_RAMBLER_SKULL.get()
        );

        this.tag(OPItemTags.BLASTER_AMMO).add(
                Items.REDSTONE
        );

        this.tag(OPItemTags.BERRIES).add(
                Items.SWEET_BERRIES,
                Items.GLOW_BERRIES
        );

        this.tag(OPItemTags.FRUITS).add(Items.APPLE, Items.MELON, Items.CHORUS_FRUIT).addTag(OPItemTags.BERRIES);

        this.tag(OPItemTags.VEGETABLES).add(
                Items.CARROT,
                Items.POTATO,
                Items.BEETROOT
        );

        this.tag(OPItemTags.RAW_VEGETABLES).addTag(OPItemTags.FRUITS).addTag(OPItemTags.VEGETABLES);

        this.tag(ItemTags.MUSIC_DISCS).add(
                WALTZ_OF_THE_SLUG_DISC.get()
        );

        this.tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(
                DEEPWOVEN_HAT.get(),
                DEEPWOVEN_TUNIC.get(),
                DEEPWOVEN_PANTS.get(),
                DEEPWOVEN_BOOTS.get()
        );

        this.tag(ItemTags.SWORDS).add(
                BONE_SWORD.get(),
                EMERALD_SWORD.get()
        );

        this.tag(ItemTags.PICKAXES).add(
                BONE_PICKAXE.get(),
                EMERALD_PICKAXE.get()
        );

        this.tag(ItemTags.AXES).add(
                BONE_AXE.get(),
                EMERALD_AXE.get()
        );

        this.tag(ItemTags.SHOVELS).add(
                BONE_SHOVEL.get(),
                EMERALD_SHOVEL.get()
        );

        this.tag(ItemTags.HOES).add(
                BONE_HOE.get(),
                EMERALD_HOE.get()
        );

        this.tag(Tags.Items.ARMORS).add(
                DEEPWOVEN_HAT.get(),
                DEEPWOVEN_TUNIC.get(),
                DEEPWOVEN_PANTS.get(),
                DEEPWOVEN_BOOTS.get(),
                WOODEN_MASK.get(),
                WOODEN_CHESTPLATE.get(),
                WOODEN_COVER.get(),
                WOODEN_BOOTS.get(),
                EMERALD_MASK.get(),
                EMERALD_CHESTPLATE.get(),
                EMERALD_LEGGINGS.get(),
                EMERALD_BOOTS.get(),
                STONE_HELMET.get(),
                STONE_CHESTPLATE.get(),
                STONE_LEGGINGS.get(),
                STONE_BOOTS.get(),
                MOON_SHOES.get(),
                SLUG_BARON_HELMET.get(),
                SLUG_BARON_CHESTPLATE.get(),
                SLUG_BARON_LEGGINGS.get(),
                SLUG_BARON_BOOTS.get()
        );

        this.tag(Tags.Items.ARMORS_HELMETS).add(
                DEEPWOVEN_HAT.get(),
                WOODEN_MASK.get(),
                EMERALD_MASK.get(),
                STONE_HELMET.get(),
                SLUG_BARON_HELMET.get()
        );

        this.tag(Tags.Items.ARMORS_CHESTPLATES).add(
                DEEPWOVEN_TUNIC.get(),
                WOODEN_CHESTPLATE.get(),
                EMERALD_CHESTPLATE.get(),
                STONE_CHESTPLATE.get(),
                SLUG_BARON_CHESTPLATE.get()
        );

        this.tag(Tags.Items.ARMORS_LEGGINGS).add(
                DEEPWOVEN_PANTS.get(),
                WOODEN_COVER.get(),
                EMERALD_LEGGINGS.get(),
                STONE_LEGGINGS.get(),
                SLUG_BARON_LEGGINGS.get()
        );

        this.tag(Tags.Items.ARMORS_BOOTS).add(
                DEEPWOVEN_BOOTS.get(),
                WOODEN_BOOTS.get(),
                EMERALD_BOOTS.get(),
                STONE_BOOTS.get(),
                MOON_SHOES.get(),
                SLUG_BARON_BOOTS.get()
        );

        this.tag(Tags.Items.STRING).add(DEEP_SILK.get());

        this.tag(Tags.Items.EGGS).add(OPBlocks.SLUG_EGGS.get().asItem());
    }
}
