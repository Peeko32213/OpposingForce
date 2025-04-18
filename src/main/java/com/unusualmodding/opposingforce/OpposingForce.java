package com.unusualmodding.opposingforce;

import com.unusualmodding.opposingforce.core.registry.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(OpposingForce.MODID)
public class OpposingForce {
    public static final String MODID = "opposingforce";
    public static final Logger LOGGER = LogManager.getLogger();


    public OpposingForce() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.addListener(ClientEvents::init));
        modEventBus.addListener(this::commonSetup);

        // SFBlocks.BLOCKS.register(modEventBus);
        OPItems.ITEMS.register(modEventBus);
        OPCreativeTabs.DEF_REG.register(modEventBus);
        OPEntities.ENTITIES.register(modEventBus);
        OPSounds.DEF_REG.register(modEventBus);
        OPBlocks.BLOCKS.register(modEventBus);
        OPEffects.EFFECT_DEF_REG.register(modEventBus);
        OPWorldGen.register();

        MinecraftForge.EVENT_BUS.register(this);

    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(OPEntityPlacement::entityPlacement);

        addToFlowerPot(OPBlocks.CAVE_PATTY, OPBlocks.POTTED_CAVE_PATTY);
        addToFlowerPot(OPBlocks.COPPER_ENOKI, OPBlocks.POTTED_COPPER_ENOKI);
        addToFlowerPot(OPBlocks.RAINCAP, OPBlocks.POTTED_RAINCAP);
        addToFlowerPot(OPBlocks.CREAM_CAP, OPBlocks.POTTED_CREAM_CAP);
        addToFlowerPot(OPBlocks.CHICKEN_OF_THE_CAVES, OPBlocks.POTTED_CHICKEN_OF_THE_CAVES);
        addToFlowerPot(OPBlocks.PRINCESS_JELLY, OPBlocks.POTTED_PRINCESS_JELLY);
        addToFlowerPot(OPBlocks.BLUE_TRUMPET, OPBlocks.POTTED_BLUE_TRUMPET);
        addToFlowerPot(OPBlocks.POWDER_GNOME, OPBlocks.POTTED_POWDER_GNOME);
        addToFlowerPot(OPBlocks.BLACKCAP, OPBlocks.POTTED_BLACKCAP);
        addToFlowerPot(OPBlocks.CAP_OF_EYE, OPBlocks.POTTED_CAP_OF_EYE);
        addToFlowerPot(OPBlocks.GREEN_FUNK, OPBlocks.POTTED_GREEN_FUNK);
        addToFlowerPot(OPBlocks.LIME_NUB, OPBlocks.POTTED_LIME_NUB);
        addToFlowerPot(OPBlocks.POP_CAP, OPBlocks.POTTED_POP_CAP);
        addToFlowerPot(OPBlocks.PURPLE_KNOB, OPBlocks.POTTED_PURPLE_KNOB);
        addToFlowerPot(OPBlocks.QUEEN_IN_PURPLE, OPBlocks.POTTED_QUEEN_IN_PURPLE);
        addToFlowerPot(OPBlocks.SLATESHROOM, OPBlocks.POTTED_SLATESHROOM);
        addToFlowerPot(OPBlocks.SLIPPERY_TOP, OPBlocks.POTTED_SLIPPERY_TOP);
        addToFlowerPot(OPBlocks.WHITECAP, OPBlocks.POTTED_WHITECAP);

        addToComposter(OPBlocks.CAVE_PATTY, 0.7F);
        addToComposter(OPBlocks.COPPER_ENOKI, 0.3F);
        addToComposter(OPBlocks.RAINCAP, 0.5F);
        addToComposter(OPBlocks.CREAM_CAP, 0.6F);
        addToComposter(OPBlocks.CHICKEN_OF_THE_CAVES, 1.3F);
        addToComposter(OPBlocks.PRINCESS_JELLY, 0.3F);
        addToComposter(OPBlocks.BLUE_TRUMPET, 0.5F);
        addToComposter(OPBlocks.POWDER_GNOME, 0.6F);
        addToComposter(OPBlocks.BLACKCAP, 0.5F);
        addToComposter(OPBlocks.CAP_OF_EYE, 0.5F);
        addToComposter(OPBlocks.GREEN_FUNK, 0.5F);
        addToComposter(OPBlocks.LIME_NUB, 0.5F);
        addToComposter(OPBlocks.POP_CAP, 0.6F);
        addToComposter(OPBlocks.PURPLE_KNOB, 0.3F);
        addToComposter(OPBlocks.QUEEN_IN_PURPLE, 1.3F);
        addToComposter(OPBlocks.SLATESHROOM, 0.5F);
        addToComposter(OPBlocks.SLIPPERY_TOP, 0.6F);
        addToComposter(OPBlocks.WHITECAP, 0.5F);
    }

    public static void addToFlowerPot(RegistryObject<Block> plantBlockLoc, Supplier<? extends Block> pottedPlantBlock){
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(plantBlockLoc.getId(),pottedPlantBlock);
    }
    public static void addToComposter(RegistryObject<Block> item, float amountOfCompost){
        ComposterBlock.COMPOSTABLES.put(item.get().asItem(), amountOfCompost);
    }



    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name.toLowerCase(Locale.ROOT));
    }

}

