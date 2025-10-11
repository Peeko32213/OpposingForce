package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.tags.ForgeItemTags;
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

import java.util.concurrent.CompletableFuture;

import static com.unusualmodding.opposing_force.registry.OPItems.*;

public class OPItemTagProvider extends ItemTagsProvider {

    public OPItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> lookup, ExistingFileHelper helper) {
        super(output, provider, lookup, OpposingForce.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(OPItemTags.KNIVES).add(
                UMBER_DAGGER.get()
        ).addOptionalTag(new ResourceLocation("farmersdelight", "tools/knives"));

        this.tag(OPItemTags.PIE_CUTTERS).addTag(OPItemTags.KNIVES).addTag(ItemTags.SWORDS);

        this.tag(OPItemTags.BLASTER_AMMO).add(
                Items.REDSTONE
        );

        this.tag(ForgeItemTags.BERRIES).add(
                Items.SWEET_BERRIES,
                Items.GLOW_BERRIES
        );

        this.tag(ForgeItemTags.FRUITS).add(Items.APPLE, Items.MELON, Items.CHORUS_FRUIT).addTag(ForgeItemTags.BERRIES);

        this.tag(ForgeItemTags.VEGETABLES).add(
                Items.CARROT,
                Items.POTATO,
                Items.BEETROOT
        );

        this.tag(OPItemTags.RAW_VEGETABLES).addTag(ForgeItemTags.FRUITS).addTag(ForgeItemTags.VEGETABLES);

        this.tag(ItemTags.FREEZE_IMMUNE_WEARABLES).add(
                DEEPWOVEN_HAT.get(),
                DEEPWOVEN_TUNIC.get(),
                DEEPWOVEN_PANTS.get(),
                DEEPWOVEN_BOOTS.get()
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
                MOON_SHOES.get()
        );

        this.tag(Tags.Items.ARMORS_HELMETS).add(
                DEEPWOVEN_HAT.get(),
                WOODEN_MASK.get(),
                EMERALD_MASK.get(),
                STONE_HELMET.get()
        );

        this.tag(Tags.Items.ARMORS_CHESTPLATES)
                .add(DEEPWOVEN_TUNIC.get()).add(WOODEN_CHESTPLATE.get()).add(EMERALD_CHESTPLATE.get()).add(STONE_CHESTPLATE.get());

        this.tag(Tags.Items.ARMORS_LEGGINGS)
                .add(DEEPWOVEN_PANTS.get()).add(WOODEN_COVER.get()).add(EMERALD_LEGGINGS.get()).add(STONE_LEGGINGS.get());

        this.tag(Tags.Items.ARMORS_BOOTS).add(
                DEEPWOVEN_BOOTS.get(),
                WOODEN_BOOTS.get(),
                EMERALD_BOOTS.get(),
                STONE_BOOTS.get(),
                MOON_SHOES.get()
        );

        this.tag(Tags.Items.STRING).add(DEEP_SILK.get());

        this.tag(Tags.Items.EGGS).add(SLUG_EGGS.get());
    }
}
