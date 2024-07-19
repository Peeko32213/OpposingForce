package com.peeko32213.hole;

import com.peeko32213.hole.core.datagen.HoleBiomeTagsProvider;
import com.peeko32213.hole.core.registry.*;
import net.minecraft.core.HolderLookup;
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

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Hole.MODID)
public class Hole {
    public static final String MODID = "hole";
    public static final Logger LOGGER = LogManager.getLogger();


    public Hole() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.addListener(ClientEvents::init));
        modEventBus.addListener(this::commonSetup);

        // SFBlocks.BLOCKS.register(modEventBus);
        HoleItems.ITEMS.register(modEventBus);
        HoleCreativeTabs.DEF_REG.register(modEventBus);
        HoleEntities.ENTITIES.register(modEventBus);
        HoleSounds.DEF_REG.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);

    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(HoleEntityPlacement::entityPlacement);
    }


    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }

}
