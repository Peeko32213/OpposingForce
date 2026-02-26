package com.unusualmodding.opposing_force.datagen;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.tags.OPEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class OPEntityTagProvider extends EntityTypeTagsProvider {

    public OPEntityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
        super(output, provider, OpposingForce.MOD_ID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(OPEntityTypeTags.HANGING_SPIDER_TARGETS).add(
                EntityType.IRON_GOLEM
        );

        this.tag(OPEntityTypeTags.NO_LEAF_COLLISIONS).add(
                OPEntities.TART.get()
        );
    }
}