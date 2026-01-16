package com.unusualmodding.opposing_force;

import com.unusualmodding.opposing_force.registry.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class OpposingForceTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OpposingForce.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_TABS.register(OpposingForce.MOD_ID,
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(OPItems.OPPOSING_FORCE.get()))
                .title(Component.translatable("itemGroup." + OpposingForce.MOD_ID))
                .displayItems((enabledFeatures, output) -> {

                    // Spawn eggs
                    OPItems.ITEMS.getEntries().forEach(spawnEgg -> {
                        if ((spawnEgg.get() instanceof ForgeSpawnEggItem)) {
                            output.accept(spawnEgg.get());
                        }
                    });

                    // Dicer
                    output.accept(OPItems.DICER_LENS.get());
                    output.accept(OPItems.BLASTER.get());
                    output.accept(OPItems.WHITE_BLASTER.get());
                    output.accept(OPItems.LIGHT_GRAY_BLASTER.get());
                    output.accept(OPItems.GRAY_BLASTER.get());
                    output.accept(OPItems.BLACK_BLASTER.get());
                    output.accept(OPItems.BROWN_BLASTER.get());
                    output.accept(OPItems.RED_BLASTER.get());
                    output.accept(OPItems.ORANGE_BLASTER.get());
                    output.accept(OPItems.YELLOW_BLASTER.get());
                    output.accept(OPItems.LIME_BLASTER.get());
                    output.accept(OPItems.GREEN_BLASTER.get());
                    output.accept(OPItems.CYAN_BLASTER.get());
                    output.accept(OPItems.LIGHT_BLUE_BLASTER.get());
                    output.accept(OPItems.BLUE_BLASTER.get());
                    output.accept(OPItems.PURPLE_BLASTER.get());
                    output.accept(OPItems.MAGENTA_BLASTER.get());
                    output.accept(OPItems.PINK_BLASTER.get());

                    output.accept(OPItems.LASER_BLADE.get());
                    output.accept(OPItems.WHITE_LASER_BLADE.get());
                    output.accept(OPItems.LIGHT_GRAY_LASER_BLADE.get());
                    output.accept(OPItems.GRAY_LASER_BLADE.get());
                    output.accept(OPItems.BLACK_LASER_BLADE.get());
                    output.accept(OPItems.BROWN_LASER_BLADE.get());
                    output.accept(OPItems.RED_LASER_BLADE.get());
                    output.accept(OPItems.ORANGE_LASER_BLADE.get());
                    output.accept(OPItems.YELLOW_LASER_BLADE.get());
                    output.accept(OPItems.LIME_LASER_BLADE.get());
                    output.accept(OPItems.GREEN_LASER_BLADE.get());
                    output.accept(OPItems.CYAN_LASER_BLADE.get());
                    output.accept(OPItems.LIGHT_BLUE_LASER_BLADE.get());
                    output.accept(OPItems.BLUE_LASER_BLADE.get());
                    output.accept(OPItems.PURPLE_LASER_BLADE.get());
                    output.accept(OPItems.MAGENTA_LASER_BLADE.get());
                    output.accept(OPItems.PINK_LASER_BLADE.get());

                    output.accept(OPItems.DICER_HEAD.get());

                    // Frowzy
                    output.accept(OPItems.FROWZY_HEAD.get());

                    // Guzzler
                    output.accept(OPItems.GUZZLER_SCALES.get());
                    output.accept(OPItems.FIRE_GEL.get());
                    output.accept(OPItems.FIRE_BOMB.get());
                    output.accept(OPBlocks.INFERNO_PIE.get());
                    output.accept(OPItems.INFERNO_PIE_SLICE.get());
                    output.accept(OPItems.INFERNO_STAFF.get());

                    // Ladybug
                    output.accept(OPItems.LADYBUG_HUSK.get());

                    // Rambler
                    output.accept(OPItems.HEAVY_BONE.get());
                    output.accept(OPItems.BONE_SWORD.get());
                    output.accept(OPItems.BONE_SHOVEL.get());
                    output.accept(OPItems.BONE_PICKAXE.get());
                    output.accept(OPItems.BONE_AXE.get());
                    output.accept(OPItems.BONE_HOE.get());
                    output.accept(OPItems.BONE_HELMET.get());
                    output.accept(OPItems.BONE_CHESTPLATE.get());
                    output.accept(OPItems.BONE_LEGGINGS.get());
                    output.accept(OPItems.BONE_BOOTS.get());
                    output.accept(OPItems.ANGRY_RAMBLER_SKULL.get());
                    output.accept(OPItems.CLASSIC_RAMBLER_SKULL.get());
                    output.accept(OPItems.EVIL_RAMBLER_SKULL.get());
                    output.accept(OPItems.GRINNING_RAMBLER_SKULL.get());
                    output.accept(OPItems.SKELETAL_RAMBLER_SKULL.get());
                    output.accept(OPItems.SMILING_RAMBLER_SKULL.get());
                    output.accept(OPItems.STRANGE_RAMBLER_SKULL.get());

                    // Skyvern
                    output.accept(OPItems.SKYVERN_CLAW.get());
                    output.accept(OPItems.SKYVERN_EGG.get());
                    output.accept(OPItems.STRATO_BOW.get());
                    output.accept(OPItems.SKYVERN_HEAD.get());

                    // Slug
                    output.accept(OPBlocks.SLUG_EGGS.get());
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
                    output.accept(OPItems.VILE_STAFF.get());
                    output.accept(OPItems.SLUG_BARON_HELMET.get());
                    output.accept(OPItems.SLUG_BARON_CHESTPLATE.get());
                    output.accept(OPItems.SLUG_BARON_LEGGINGS.get());
                    output.accept(OPItems.SLUG_BARON_BOOTS.get());
                    output.accept(OPItems.WALTZ_OF_THE_SLUG_DISC.get());

                    // Tart
                    output.accept(OPBlocks.APPLE_SAPLING.get());
                    output.accept(OPBlocks.APPLE_LEAVES.get());
                    output.accept(OPBlocks.FLOWERING_APPLE_LEAVES.get());
                    output.accept(OPBlocks.FRUITFUL_APPLE_LEAVES.get());
                    output.accept(OPBlocks.INFESTED_APPLE_LEAVES.get());
                    output.accept(OPBlocks.FLOWERING_INFESTED_APPLE_LEAVES.get());
                    output.accept(OPBlocks.FRUITFUL_INFESTED_APPLE_LEAVES.get());
                    output.accept(OPItems.RAW_TART.get());
                    output.accept(OPItems.COOKED_TART.get());
                    output.accept(OPItems.TART_HEAD.get());

                    // Terror
                    output.accept(OPItems.TERROR_LEG.get());
                    output.accept(OPItems.FRIED_TERROR_LEG.get());
                    output.accept(OPItems.SPICY_TERROR_LEG.get());
                    output.accept(OPItems.SAWBLADE.get());

                    // Trembler
                    output.accept(OPBlocks.TREMBLER_SHELL.get());
                    output.accept(OPBlocks.TREMBLING_BLOCK.get());
                    output.accept(OPBlocks.TREMBLING_SHINGLES.get());
                    output.accept(OPBlocks.TREMBLING_SHINGLE_STAIRS.get());
                    output.accept(OPBlocks.TREMBLING_SHINGLE_SLAB.get());

                    // Umber Spider
                    output.accept(OPItems.DEEP_SILK.get());
                    output.accept(OPBlocks.DEEP_SILK_BLOCK.get());
                    output.accept(OPBlocks.DEEP_WEB.get());
                    output.accept(OPItems.DEEPWOVEN_HAT.get());
                    output.accept(OPItems.DEEPWOVEN_TUNIC.get());
                    output.accept(OPItems.DEEPWOVEN_PANTS.get());
                    output.accept(OPItems.DEEPWOVEN_BOOTS.get());
                    output.accept(OPItems.UMBER_FANG.get());
                    output.accept(OPItems.UMBER_DAGGER.get());

                    // Volt
                    output.accept(OPItems.ELECTRIC_CHARGE.get());
                    output.accept(OPItems.LIGHTNING_BOMB.get());
                    output.accept(OPItems.ELECTRIC_ALLOY.get());
                    output.accept(OPItems.TESLA_CANNON.get());
                    output.accept(OPItems.SPARK_BLADE.get());
                    output.accept(OPItems.RECON_KNIGHT_HELMET.get());
                    output.accept(OPItems.RECON_KNIGHT_CHESTPLATE.get());
                    output.accept(OPItems.RECON_KNIGHT_LEGGINGS.get());
                    output.accept(OPItems.RECON_KNIGHT_BOOTS.get());

                    // Whizz
                    output.accept(OPBlocks.INFESTED_AMETHYST_BLOCK.get());
                    output.accept(OPItems.CAPTURED_WHIZZ.get());
                    output.accept(OPItems.WHIZZ_BOMB.get());
                    output.accept(OPItems.WHIZZ_HEAD.get());

                    output.accept(OPItems.TOMAHAWK.get());
                    output.accept(OPItems.KINETIC_BOMB.get());
                    output.accept(OPItems.BLADE_OF_THE_MOUNTAIN.get());

                    output.accept(OPItems.WOODEN_MASK.get());
                    output.accept(OPItems.WOODEN_CHESTPLATE.get());
                    output.accept(OPItems.WOODEN_COVER.get());
                    output.accept(OPItems.WOODEN_BOOTS.get());

                    output.accept(OPItems.STONE_HELMET.get());
                    output.accept(OPItems.STONE_CHESTPLATE.get());
                    output.accept(OPItems.STONE_LEGGINGS.get());
                    output.accept(OPItems.STONE_BOOTS.get());

                    output.accept(OPItems.EMERALD_SWORD.get());
                    output.accept(OPItems.EMERALD_SHOVEL.get());
                    output.accept(OPItems.EMERALD_PICKAXE.get());
                    output.accept(OPItems.EMERALD_AXE.get());
                    output.accept(OPItems.EMERALD_HOE.get());
                    output.accept(OPItems.EMERALD_MASK.get());
                    output.accept(OPItems.EMERALD_CHESTPLATE.get());
                    output.accept(OPItems.EMERALD_LEGGINGS.get());
                    output.accept(OPItems.EMERALD_BOOTS.get());

                    output.accept(OPItems.MOON_SHOES.get());

                    OPEnchantments.addAllEnchantsToCreativeTab(output, OPEnchantments.TESLA_CANNON);
                    OPEnchantments.addAllEnchantsToCreativeTab(output, OPEnchantments.BLASTER);
                    OPEnchantments.addAllEnchantsToCreativeTab(output, OPEnchantments.LASER_BLADE);
                    OPEnchantments.addAllEnchantsToCreativeTab(output, OPEnchantments.VILE_BOULDER);
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
            })
            .build());
}
