package com.peeko32213.hole.core.datagen;

import com.peeko32213.hole.Hole;
import com.peeko32213.hole.core.registry.HoleEntities;
import com.peeko32213.hole.core.registry.HoleTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;


public class EntityTagGenerator extends EntityTypeTagsProvider {


    public EntityTagGenerator(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_256095_, p_256572_, Hole.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(HoleTags.PALE_SPIDER).add(HoleEntities.PALE_SPIDER.get());
        tag(HoleTags.UMBER_SPIDER).add(HoleEntities.UMBER_SPIDER.get());
    }
}