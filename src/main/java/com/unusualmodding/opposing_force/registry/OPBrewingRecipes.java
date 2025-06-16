package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.effects.OPBrewingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class OPBrewingRecipes {

    public static void registerPotionRecipes() {
        BrewingRecipeRegistry.addRecipe(new OPBrewingRecipe(Ingredient.of(registerPotion(Potions.POISON)), Ingredient.of(OPItems.UMBER_FANG.get()), registerPotion(OPPotions.GLOOM_TOXIN_POTION)));
        BrewingRecipeRegistry.addRecipe(new OPBrewingRecipe(Ingredient.of(registerPotion(OPPotions.GLOOM_TOXIN_POTION)), Ingredient.of(Items.REDSTONE), registerPotion(OPPotions.LONG_GLOOM_TOXIN_POTION)));
        BrewingRecipeRegistry.addRecipe(new OPBrewingRecipe(Ingredient.of(registerPotion(OPPotions.GLOOM_TOXIN_POTION)), Ingredient.of(Items.GLOWSTONE_DUST), registerPotion(OPPotions.STRONG_GLOOM_TOXIN_POTION)));

        BrewingRecipeRegistry.addRecipe(new OPBrewingRecipe(Ingredient.of(registerPotion(Potions.AWKWARD)), Ingredient.of(OPItems.SLUG_EGGS.get()), registerPotion(OPPotions.SLUG_INFESTATION_POTION)));
    }

    public static ItemStack registerPotion(RegistryObject<Potion> potion) {
        return registerPotion(potion.get());
    }

    public static ItemStack registerPotion(Potion potion) {
        return PotionUtils.setPotion(new ItemStack(Items.POTION), potion);
    }

    public static ItemStack registerSplashPotion(Potion potion) {
        return PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), potion);
    }

    public static ItemStack registerLingeringPotion(Potion potion) {
        return PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), potion);
    }

    public static ItemStack registerTippedArrow(Potion potion) {
        return PotionUtils.setPotion(new ItemStack(Items.TIPPED_ARROW), potion);
    }
}
