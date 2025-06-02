package com.unusualmodding.opposing_force;

import com.unusualmodding.opposing_force.data.*;
import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Mod(OpposingForce.MOD_ID)
@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID)
public class OpposingForce {

    public static final String MOD_ID = "opposing_force";
    public static final Logger LOGGER = LogManager.getLogger();

    public OpposingForce() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        OPItems.ITEMS.register(bus);
        OPCreativeTabs.DEF_REG.register(bus);
        OPEntities.ENTITIES.register(bus);
        OPSounds.DEF_REG.register(bus);
        OPBlocks.BLOCKS.register(bus);
        OPEffects.MOB_EFFECT.register(bus);
        OPParticles.PARTICLE_TYPES.register(bus);
        OPEnchantments.ENCHANTMENTS.register(bus);
        OPWorldGen.register();

        bus.addListener(this::commonSetup);
        bus.addListener(this::dataSetup);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(OPEntityPlacement::entityPlacement);
        event.enqueueWork(OPCompat::registerCompat);
        OPMessages.register();
    }

    private void dataSetup(GatherDataEvent data) {
        DataGenerator generator = data.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<Provider> provider = data.getLookupProvider();
        ExistingFileHelper helper = data.getExistingFileHelper();

        boolean server = data.includeServer();

        OPDatapackBuiltinEntriesProvider datapackEntries = new OPDatapackBuiltinEntriesProvider(output, provider);
        generator.addProvider(server, datapackEntries);
        provider = datapackEntries.getRegistryProvider();

        OPBlockTagProvider blockTags = new OPBlockTagProvider(output, provider, helper);
        generator.addProvider(server, blockTags);
        generator.addProvider(server, new OPItemTagProvider(output, provider, blockTags.contentsGetter(), helper));
        generator.addProvider(server, new OPEntityTagProvider(output, provider, helper));
        generator.addProvider(server, new OPDamageTypeTagProvider(output, provider, helper));
        generator.addProvider(server, OPLootProvider.register(output));
        generator.addProvider(server, new OPRecipeProvider(output));

        boolean client = data.includeClient();

        generator.addProvider(client, new OPBlockstateProvider(data));
        generator.addProvider(client, new OPItemModelProvider(data));
        generator.addProvider(client, new OPSoundDefinitionsProvider(output, helper));
        generator.addProvider(client, new OPLangProvider(data));
    }

    public static ResourceLocation modPrefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }
}

