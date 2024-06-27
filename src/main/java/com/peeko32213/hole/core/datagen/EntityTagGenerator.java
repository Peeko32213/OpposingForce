package com.peeko32213.hole.core.datagen;

import com.peeko32213.hole.Hole;
import com.peeko32213.hole.core.registry.HoleEntities;
import com.peeko32213.hole.core.utils.HoleTags;
import com.scouter.goalsmith.GoalSmith;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.scouter.goalsmith.GoalSmith.prefix;

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