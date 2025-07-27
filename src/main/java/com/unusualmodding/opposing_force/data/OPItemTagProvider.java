package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
                WOODEN_BOOTS.get()
        );

        this.tag(Tags.Items.ARMORS_HELMETS).add(DEEPWOVEN_HAT.get()).add(WOODEN_MASK.get());

        this.tag(Tags.Items.ARMORS_CHESTPLATES).add(DEEPWOVEN_TUNIC.get()).add(WOODEN_CHESTPLATE.get());

        this.tag(Tags.Items.ARMORS_LEGGINGS).add(DEEPWOVEN_PANTS.get()).add(WOODEN_COVER.get());

        this.tag(Tags.Items.ARMORS_BOOTS).add(DEEPWOVEN_BOOTS.get()).add(WOODEN_BOOTS.get());

        this.tag(Tags.Items.STRING).add(DEEP_SILK.get());

        this.tag(Tags.Items.EGGS).add(SLUG_EGGS.get());

        this.tag(Tags.Items.MUSHROOMS).add(
                OPBlocks.CAP_OF_EYE.get().asItem(),
                OPBlocks.CAVE_PATTY.get().asItem(),
                OPBlocks.CHICKEN_OF_THE_CAVES.get().asItem(),
                OPBlocks.COPPER_ENOKI.get().asItem(),
                OPBlocks.CREAM_CAP.get().asItem(),
                OPBlocks.POWDER_GNOME.get().asItem(),
                OPBlocks.PURPLE_KNOB.get().asItem(),
                OPBlocks.RAINCAP.get().asItem(),
                OPBlocks.SLIPPERY_TOP.get().asItem()
        );
    }
}
