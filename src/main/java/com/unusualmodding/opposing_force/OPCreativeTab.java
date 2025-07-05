package com.unusualmodding.opposing_force;

import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class OPCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OpposingForce.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_TABS.register(OpposingForce.MOD_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + OpposingForce.MOD_ID))
            .icon(() -> new ItemStack(OPItems.CAPTURED_WHIZZ.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .displayItems((enabledFeatures, output) -> {
                for(RegistryObject<Item> item : OPItems.ITEMS.getEntries()){

                    // Spawn eggs
                    OPItems.ITEMS.getEntries().forEach(spawnEgg -> {
                        if ((spawnEgg.get() instanceof ForgeSpawnEggItem)) {
                            output.accept(spawnEgg.get());
                        }
                    });

                    // Weapons
                    output.accept(OPItems.TOMAHAWK.get());
                    output.accept(OPItems.TESLA_BOW.get());
                    output.accept(OPItems.VILE_BOULDER.get());

                    // Armors
                    output.accept(OPItems.DEEPWOVEN_HAT.get());
                    output.accept(OPItems.DEEPWOVEN_TUNIC.get());
                    output.accept(OPItems.DEEPWOVEN_PANTS.get());
                    output.accept(OPItems.DEEPWOVEN_BOOTS.get());

                    // Items
                    output.accept(OPItems.SLUG_EGGS.get());
                    output.accept(OPItems.ELECTRIC_CHARGE.get());
                    output.accept(OPItems.CAPTURED_WHIZZ.get());
                    output.accept(OPBlocks.TREMBLER_SHELL.get());
                    output.accept(OPItems.DEEP_SILK.get());
                    output.accept(OPItems.UMBER_FANG.get());

                    // Mushrooms
                    output.accept(OPBlocks.CAP_OF_EYE.get());
                    output.accept(OPBlocks.CAVE_PATTY.get());
                    output.accept(OPBlocks.CHICKEN_OF_THE_CAVES.get());
                    output.accept(OPBlocks.COPPER_ENOKI.get());
                    output.accept(OPBlocks.CREAM_CAP.get());
                    output.accept(OPBlocks.POWDER_GNOME.get());
                    output.accept(OPBlocks.PURPLE_KNOB.get());
                    output.accept(OPBlocks.RAINCAP.get());
                    output.accept(OPBlocks.SLIPPERY_TOP.get());
//                    output.accept(OPBlocks.COPPER_ENOKI_BLOCK.get());
//                    output.accept(OPBlocks.CREAM_CAP_BLOCK.get());
//                    output.accept(OPBlocks.SLIPPERY_TOP_BLOCK.get());

                    output.accept(OPBlocks.INFESTED_AMETHYST_BLOCK.get());

                    output.accept(OPItems.DICER_HEAD.get());

                    OPEnchantments.addAllEnchantsToCreativeTab(output, OPEnchantments.TESLA_BOW);
                    OPEnchantments.addAllEnchantsToCreativeTab(output, EnchantmentCategory.CROSSBOW);

                    output.accept(OPBrewingRecipes.registerPotion(OPPotions.GLOOM_TOXIN_POTION.get()));
                    output.accept(OPBrewingRecipes.registerPotion(OPPotions.LONG_GLOOM_TOXIN_POTION.get()));
                    output.accept(OPBrewingRecipes.registerPotion(OPPotions.STRONG_GLOOM_TOXIN_POTION.get()));
                    output.accept(OPBrewingRecipes.registerSplashPotion(OPPotions.GLOOM_TOXIN_POTION.get()));
                    output.accept(OPBrewingRecipes.registerSplashPotion(OPPotions.LONG_GLOOM_TOXIN_POTION.get()));
                    output.accept(OPBrewingRecipes.registerSplashPotion(OPPotions.STRONG_GLOOM_TOXIN_POTION.get()));
                    output.accept(OPBrewingRecipes.registerLingeringPotion(OPPotions.GLOOM_TOXIN_POTION.get()));
                    output.accept(OPBrewingRecipes.registerLingeringPotion(OPPotions.LONG_GLOOM_TOXIN_POTION.get()));
                    output.accept(OPBrewingRecipes.registerLingeringPotion(OPPotions.STRONG_GLOOM_TOXIN_POTION.get()));
                    output.accept(OPBrewingRecipes.registerTippedArrow(OPPotions.GLOOM_TOXIN_POTION.get()));
                    output.accept(OPBrewingRecipes.registerTippedArrow(OPPotions.LONG_GLOOM_TOXIN_POTION.get()));
                    output.accept(OPBrewingRecipes.registerTippedArrow(OPPotions.STRONG_GLOOM_TOXIN_POTION.get()));

                    output.accept(OPBrewingRecipes.registerPotion(OPPotions.SLUG_INFESTATION_POTION.get()));
                    output.accept(OPBrewingRecipes.registerSplashPotion(OPPotions.SLUG_INFESTATION_POTION.get()));
                    output.accept(OPBrewingRecipes.registerLingeringPotion(OPPotions.SLUG_INFESTATION_POTION.get()));
                    output.accept(OPBrewingRecipes.registerTippedArrow(OPPotions.SLUG_INFESTATION_POTION.get()));
                }
            })
            .build());
}
