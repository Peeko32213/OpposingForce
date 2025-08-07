package com.unusualmodding.opposing_force;

import com.unusualmodding.opposing_force.data.*;
import com.unusualmodding.opposing_force.events.ServerEvents;
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
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Mod(OpposingForce.MOD_ID)
public class OpposingForce {

    public static final String MOD_ID = "opposing_force";

    public OpposingForce() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::dataSetup);

        OPBlocks.BLOCKS.register(modEventBus);
        OPItems.ITEMS.register(modEventBus);
        OPCreativeTab.CREATIVE_TABS.register(modEventBus);
        OPEffects.MOB_EFFECTS.register(modEventBus);
        OPEntities.ENTITY_TYPES.register(modEventBus);
        OPSoundEvents.SOUND_EVENTS.register(modEventBus);
        OPParticles.PARTICLE_TYPES.register(modEventBus);
        OPEnchantments.ENCHANTMENTS.register(modEventBus);
        OPPotions.POTIONS.register(modEventBus);
        OPAttributes.ATTRIBUTES.register(modEventBus);
        OPLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        OPBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(new ServerEvents());

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            OPCompat.registerCompat();
            OPBrewingRecipes.registerPotionRecipes();
        });
        OPNetwork.registerNetwork();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
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
        generator.addProvider(server, new OPBiomeTagProvider(output, provider, helper));

        generator.addProvider(server, new OPDamageTypeTagProvider(output, provider, helper));
        generator.addProvider(server, OPLootProvider.register(output));
        generator.addProvider(server, new OPRecipeProvider(output));

        generator.addProvider(server, OPAdvancementProvider.register(output, provider, helper));

        boolean client = data.includeClient();

        generator.addProvider(client, new OPBlockstateProvider(data));
        generator.addProvider(client, new OPItemModelProvider(data));
        generator.addProvider(client, new OPSoundDefinitionsProvider(output, helper));
        generator.addProvider(client, new OPLanguageProvider(data));
    }

    public static ResourceLocation modPrefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }
}

