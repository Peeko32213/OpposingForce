package com.unusualmodding.opposingforce.core.datagen.server;

import com.google.common.collect.Sets;
import com.unusualmodding.opposingforce.core.datagen.client.BlockstateGenerator;
import com.unusualmodding.opposingforce.core.datagen.client.ItemModelGenerator;
import com.unusualmodding.opposingforce.core.datagen.client.LanguageGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static com.unusualmodding.opposingforce.OpposingForce.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent evt) {
        if (evt.includeServer())
            registerServerProviders(evt.getGenerator(), evt);

    }
    private static void registerServerProviders(DataGenerator generator, GatherDataEvent evt) {
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper helper = evt.getExistingFileHelper();
        Set<BlockStateGenerator> set = Sets.newHashSet();
        Consumer<BlockStateGenerator> consumer = set::add;
        CompletableFuture<HolderLookup.Provider> lookupProvider = evt.getLookupProvider();
        generator.addProvider(true,new BlockstateGenerator(packOutput, helper));
        generator.addProvider(true,new LanguageGenerator(packOutput));
        generator.addProvider(true,new ItemModelGenerator(packOutput, helper));
        generator.addProvider(true, new EntityTagGenerator(packOutput, lookupProvider, helper));
        generator.addProvider(evt.includeServer(), LootGenerator.create(packOutput));
        //generator.addProvider(evt.includeServer(), new DamageTypeTagsGenerator(packOutput, lookupProvider, helper));
        //generator.addProvider(true,new AdvancementGenerator(generator, helper));
        generator.addProvider(true, new OPBiomeTagsProvider(MODID, packOutput, lookupProvider, helper));
        DatapackBuiltinEntriesProvider datapackProvider = new RegistryDataGenerator(packOutput, lookupProvider);
        CompletableFuture<HolderLookup.Provider> customLookupProvider = datapackProvider.getRegistryProvider();
        BlockTagsGenerator blockTagGenerator = generator.addProvider(evt.includeServer(),
                new BlockTagsGenerator(packOutput, lookupProvider, helper));
        generator.addProvider(true, datapackProvider);
    }

}
