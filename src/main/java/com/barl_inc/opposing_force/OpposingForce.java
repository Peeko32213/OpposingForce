package com.barl_inc.opposing_force;

import com.barl_inc.opposing_force.registry.*;
import com.barl_inc.opposing_force.utils.ClientProxy;
import com.barl_inc.opposing_force.utils.CommonProxy;
import com.barl_inc.opposing_force.utils.OPLoadedMods;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Locale;
import java.util.function.Supplier;

@Mod(OpposingForce.MOD_ID)
public class OpposingForce {

    public static final CommonProxy PROXY = unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    public static final String MOD_ID = "opposing_force";

    public OpposingForce(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::loadComplete);
        modEventBus.addListener(this::dataSetup);


        OPTreeFeatures.INIT();
        OPTreeGrowers.INIT();

        OPBlocks.BLOCKS.register(modEventBus);
        OPItems.ITEMS.register(modEventBus);
        OpposingForceTab.CREATIVE_TABS.register(modEventBus);
        OPMobEffects.MOB_EFFECTS.register(modEventBus);
        OPEntities.ENTITY_TYPE.register(modEventBus);
        OPSoundEvents.SOUND_EVENTS.register(modEventBus);
        OPParticles.PARTICLE_TYPES.register(modEventBus);
        OPEnchantments.ENCHANTMENTS.register(modEventBus);
        OPPotions.POTIONS.register(modEventBus);
        OPAttributes.ATTRIBUTES.register(modEventBus);
        OPLootModifiers.LOOT_MODIFIERS.register(modEventBus);
        OPBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        OPRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(PROXY);
        PROXY.init();
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            OPCompat.registerCompat();
            OPBrewingRecipes.registerPotionRecipes();
        });
        OPNetwork.registerNetwork();
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(PROXY::clientInit);
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(OPLoadedMods::afterAllModsLoaded);
    }

    private void dataSetup(GatherDataEvent data) {
//        DataGenerator generator = data.getGenerator();
//        PackOutput output = generator.getPackOutput();
//        CompletableFuture<Provider> provider = data.getLookupProvider();
//        ExistingFileHelper helper = data.getExistingFileHelper();
//
//        boolean server = data.includeServer();
//
//        OPDatapackProvider datapackEntries = new OPDatapackProvider(output, provider);
//        generator.addProvider(server, datapackEntries);
//        provider = datapackEntries.getRegistryProvider();
//
//        OPBlockTagProvider blockTags = new OPBlockTagProvider(output, provider, helper);
//        generator.addProvider(server, blockTags);
//        generator.addProvider(server, new OPItemTagProvider(output, provider, blockTags.contentsGetter(), helper));
//        generator.addProvider(server, new OPEntityTagProvider(output, provider, helper));
//        generator.addProvider(server, new OPBiomeTagProvider(output, provider, helper));
//
//        generator.addProvider(server, new OPDamageTypeTagProvider(output, provider, helper));
//        generator.addProvider(server, OPLootProvider.register(output));
//        generator.addProvider(server, new OPRecipeProvider(output));

//        generator.addProvider(server, OPAdvancementProvider.register(output, provider, helper));

//        boolean client = data.includeClient();
//
//        generator.addProvider(client, new OPBlockstateProvider(output, helper));
//        generator.addProvider(client, new OPItemModelProvider(data));
//        generator.addProvider(client, new OPSoundDefinitionsProvider(output, helper));
//        generator.addProvider(client, new OPLanguageProvider(data));
    }

    public static ResourceLocation modPrefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name.toLowerCase(Locale.ROOT));
    }

    private static <T> T unsafeRunForDist(Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        return switch (FMLEnvironment.dist) {
            case CLIENT -> clientTarget.get().get();
            case DEDICATED_SERVER -> serverTarget.get().get();
        };
    }
}

