package com.peeko32213.hole;

import com.peeko32213.hole.core.datagen.HoleBiomeTagsProvider;
import com.peeko32213.hole.core.registry.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

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
        HoleBlocks.BLOCKS.register(modEventBus);
        HoleEffects.EFFECT_DEF_REG.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);

    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(HoleEntityPlacement::entityPlacement);

        addToFlowerPot(HoleBlocks.CAVE_PATTY, HoleBlocks.POTTED_CAVE_PATTY);
        addToFlowerPot(HoleBlocks.COPPER_ENOKI, HoleBlocks.POTTED_COPPER_ENOKI);
        addToFlowerPot(HoleBlocks.RAINCAP, HoleBlocks.POTTED_RAINCAP);
        addToFlowerPot(HoleBlocks.CREAM_CAP, HoleBlocks.POTTED_CREAM_CAP);
        addToFlowerPot(HoleBlocks.CHICKEN_OF_THE_CAVES, HoleBlocks.POTTED_CHICKEN_OF_THE_CAVES);
        addToFlowerPot(HoleBlocks.PRINCESS_JELLY, HoleBlocks.POTTED_PRINCESS_JELLY);
        addToFlowerPot(HoleBlocks.BLUE_TRUMPET, HoleBlocks.POTTED_BLUE_TRUMPET);
        addToFlowerPot(HoleBlocks.POWDER_GNOME, HoleBlocks.POTTED_POWDER_GNOME);

        addToComposter(HoleBlocks.CAVE_PATTY, 0.7F);
        addToComposter(HoleBlocks.COPPER_ENOKI, 0.3F);
        addToComposter(HoleBlocks.RAINCAP, 0.5F);
        addToComposter(HoleBlocks.CREAM_CAP, 0.6F);
        addToComposter(HoleBlocks.CHICKEN_OF_THE_CAVES, 1.3F);
        addToComposter(HoleBlocks.PRINCESS_JELLY, 0.3F);
        addToComposter(HoleBlocks.BLUE_TRUMPET, 0.5F);
        addToComposter(HoleBlocks.POWDER_GNOME, 0.6F);
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
