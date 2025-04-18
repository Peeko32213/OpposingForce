package com.unusualmodding.opposingforce.core.datagen.server;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.core.registry.OPEntities;
import com.unusualmodding.opposingforce.core.registry.OPTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;


public class EntityTagGenerator extends EntityTypeTagsProvider {


    public EntityTagGenerator(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256095_, p_256572_, OpposingForce.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(OPTags.PALE_SPIDER).add(OPEntities.PALE_SPIDER.get());
        tag(OPTags.UMBER_SPIDER).add(OPEntities.UMBER_SPIDER.get());
    }
}