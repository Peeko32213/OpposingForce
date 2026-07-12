package com.barl_inc.opposing_force.registry;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;

public class OPBrewingRecipes {

    public static void registerPotionRecipes(PotionBrewing.Builder builder) {
        // gloom toxin
        builder.addMix(Potions.POISON.getDelegate(), OPItems.UMBER_FANG.get(), OPPotions.GLOOM_TOXIN_POTION);
        builder.addMix(OPPotions.GLOOM_TOXIN_POTION, Items.REDSTONE, OPPotions.LONG_GLOOM_TOXIN_POTION);
        builder.addMix(OPPotions.GLOOM_TOXIN_POTION, Items.GLOWSTONE_DUST, OPPotions.STRONG_GLOOM_TOXIN_POTION);

        // slug infestation
        builder.addMix(Potions.AWKWARD.getDelegate(), OPBlocks.SLUG_EGGS.asItem(), OPPotions.SLUG_INFESTATION_POTION);
    }
}
