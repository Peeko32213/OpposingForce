package com.unusualmodding.opposing_force.data;

import com.google.common.collect.Sets;
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

import static com.unusualmodding.opposing_force.OpposingForce.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

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
        generator.addProvider(true,new OPBlockstateProvider(packOutput, helper));
        generator.addProvider(true,new OPLangProvider(packOutput));
        generator.addProvider(true,new OPItemModelProvider(packOutput, helper));
        generator.addProvider(true, new OPEntityTagProvider(packOutput, lookupProvider, helper));
        generator.addProvider(evt.includeServer(), OPLootProvider.create(packOutput));
        //generator.addProvider(evt.includeServer(), new DamageTypeTagsGenerator(packOutput, lookupProvider, helper));
        //generator.addProvider(true,new AdvancementGenerator(generator, helper));
        generator.addProvider(true, new OPBiomeTagsProvider(MOD_ID, packOutput, lookupProvider, helper));
        DatapackBuiltinEntriesProvider datapackProvider = new RegistryDataGenerator(packOutput, lookupProvider);
        CompletableFuture<HolderLookup.Provider> customLookupProvider = datapackProvider.getRegistryProvider();
        OPBlockTagProvider blockTagGenerator = generator.addProvider(evt.includeServer(),
                new OPBlockTagProvider(packOutput, lookupProvider, helper));
        generator.addProvider(true, datapackProvider);
    }

}
