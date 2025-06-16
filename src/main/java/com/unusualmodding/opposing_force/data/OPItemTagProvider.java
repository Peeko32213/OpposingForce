package com.unusualmodding.opposing_force.data;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
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
                DEEPWOVEN_BOOTS.get()
        );

        this.tag(Tags.Items.ARMORS_HELMETS).add(DEEPWOVEN_HAT.get());

        this.tag(Tags.Items.ARMORS_CHESTPLATES).add(DEEPWOVEN_TUNIC.get());

        this.tag(Tags.Items.ARMORS_LEGGINGS).add(DEEPWOVEN_PANTS.get());

        this.tag(Tags.Items.ARMORS_BOOTS).add(DEEPWOVEN_BOOTS.get());
    }
}
