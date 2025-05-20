package com.unusualmodding.opposing_force;

import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

@Mod(OpposingForce.MOD_ID)
@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID)
public class OpposingForce {

    public static final String MOD_ID = "opposing_force";
    public static final Logger LOGGER = LogManager.getLogger();

    public OpposingForce() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);

        OPItems.ITEMS.register(bus);
        OPCreativeTabs.DEF_REG.register(bus);
        OPEntities.ENTITIES.register(bus);
        OPSounds.DEF_REG.register(bus);
        OPBlocks.BLOCKS.register(bus);
        OPEffects.EFFECT_DEF_REG.register(bus);
        OPParticles.PARTICLE_TYPES.register(bus);
        OPEnchantments.ENCHANTMENTS.register(bus);
        OPWorldGen.register();

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(OPEntityPlacement::entityPlacement);
        event.enqueueWork(OPCompat::registerCompat);
        OPMessages.register();
    }

    public static ResourceLocation modPrefix(String name) {
        return new ResourceLocation(MOD_ID, name.toLowerCase(Locale.ROOT));
    }
}

