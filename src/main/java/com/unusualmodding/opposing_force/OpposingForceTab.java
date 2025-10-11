package com.unusualmodding.opposing_force;

import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class OpposingForceTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OpposingForce.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_TABS.register(OpposingForce.MOD_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + OpposingForce.MOD_ID))
            .icon(() -> new ItemStack(OPItems.CAPTURED_WHIZZ.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .displayItems((enabledFeatures, output) -> {
                for (RegistryObject<Item> item : OPItems.ITEMS.getEntries()){

                    // Spawn eggs
                    OPItems.ITEMS.getEntries().forEach(spawnEgg -> {
                        if ((spawnEgg.get() instanceof ForgeSpawnEggItem)) {
                            output.accept(spawnEgg.get());
                        }
                    });

                    output.accept(OPItems.TOMAHAWK.get());

                    output.accept(OPItems.WOODEN_MASK.get());
                    output.accept(OPItems.WOODEN_CHESTPLATE.get());
                    output.accept(OPItems.WOODEN_COVER.get());
                    output.accept(OPItems.WOODEN_BOOTS.get());

                    output.accept(OPItems.STONE_HELMET.get());
                    output.accept(OPItems.STONE_CHESTPLATE.get());
                    output.accept(OPItems.STONE_LEGGINGS.get());
                    output.accept(OPItems.STONE_BOOTS.get());

                    output.accept(OPItems.MOON_SHOES.get());

                    output.accept(OPItems.EMERALD_SWORD.get());
                    output.accept(OPItems.EMERALD_SHOVEL.get());
                    output.accept(OPItems.EMERALD_PICKAXE.get());
                    output.accept(OPItems.EMERALD_AXE.get());
                    output.accept(OPItems.EMERALD_HOE.get());
                    output.accept(OPItems.EMERALD_MASK.get());
                    output.accept(OPItems.EMERALD_CHESTPLATE.get());
                    output.accept(OPItems.EMERALD_LEGGINGS.get());
                    output.accept(OPItems.EMERALD_BOOTS.get());

                    output.accept(OPItems.SLUG_EGGS.get());
                    output.accept(OPBlocks.VILE_STONE.get());
                    output.accept(OPBlocks.VILE_STONE_STAIRS.get());
                    output.accept(OPBlocks.VILE_STONE_SLAB.get());
                    output.accept(OPBlocks.VILE_STONE_BRICKS.get());
                    output.accept(OPBlocks.VILE_STONE_BRICK_STAIRS.get());
                    output.accept(OPBlocks.VILE_STONE_BRICK_SLAB.get());
                    output.accept(OPBlocks.VILE_STONE_BRICK_WALL.get());
                    output.accept(OPBlocks.CHISELED_VILE_STONE_BRICKS.get());
                    output.accept(OPBlocks.VILE_COBBLESTONE.get());
                    output.accept(OPBlocks.VILE_COBBLESTONE_STAIRS.get());
                    output.accept(OPBlocks.VILE_COBBLESTONE_SLAB.get());
                    output.accept(OPBlocks.VILE_COBBLESTONE_WALL.get());

                    output.accept(OPItems.VILE_BOULDER.get());
                    output.accept(OPItems.ELECTRIC_CHARGE.get());
                    output.accept(OPItems.TESLA_CANNON.get());
                    output.accept(OPItems.CAPTURED_WHIZZ.get());
                    output.accept(OPBlocks.TREMBLER_SHELL.get());
                    output.accept(OPBlocks.TREMBLING_BLOCK.get());
                    output.accept(OPBlocks.TREMBLING_SLAB.get());
                    output.accept(OPBlocks.CHISELED_TREMBLING_BLOCK.get());
                    output.accept(OPBlocks.TREMBLING_SHINGLES.get());
                    output.accept(OPBlocks.TREMBLING_SHINGLE_STAIRS.get());
                    output.accept(OPBlocks.TREMBLING_SHINGLE_SLAB.get());
                    output.accept(OPItems.DEEP_SILK.get());
                    output.accept(OPBlocks.DEEP_SILK_BLOCK.get());
                    output.accept(OPBlocks.DEEP_WEB.get());
                    output.accept(OPItems.DEEPWOVEN_HAT.get());
                    output.accept(OPItems.DEEPWOVEN_TUNIC.get());
                    output.accept(OPItems.DEEPWOVEN_PANTS.get());
                    output.accept(OPItems.DEEPWOVEN_BOOTS.get());
                    output.accept(OPItems.UMBER_FANG.get());
                    output.accept(OPItems.UMBER_DAGGER.get());
                    output.accept(OPItems.DICER_LENS.get());
                    output.accept(OPItems.BLASTER.get());
                    output.accept(OPItems.GUZZLER_SCALES.get());
                    output.accept(OPItems.FIRE_GEL.get());
                    output.accept(OPBlocks.INFERNO_PIE.get());

                    output.accept(OPBlocks.INFESTED_AMETHYST_BLOCK.get());

                    output.accept(OPItems.DICER_HEAD.get());
                    output.accept(OPItems.FROWZY_HEAD.get());
                    output.accept(OPItems.RAMBLE_SKULL.get());

                    OPEnchantments.addAllEnchantsToCreativeTab(output, OPEnchantments.TESLA_BOW);
                    OPEnchantments.addAllEnchantsToCreativeTab(output, OPEnchantments.BLASTER);
                    OPEnchantments.addAllEnchantsToCreativeTab(output, OPEnchantments.VILE_BOULDER);
                    OPEnchantments.addAllEnchantsToCreativeTab(output, OPEnchantments.WOODEN_ARMOR);
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
