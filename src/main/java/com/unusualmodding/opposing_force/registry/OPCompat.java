package com.unusualmodding.opposing_force.registry;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FireBlock;

public class OPCompat {

    public static void registerCompat() {
        registerCompostables();
        registerFlammables();
    }

    public static void registerCompostables() {
        registerCompostable(OPBlocks.CAVE_PATTY.get(), 0.65F);
        registerCompostable(OPBlocks.COPPER_ENOKI.get(), 0.65F);
        registerCompostable(OPBlocks.RAINCAP.get(), 0.65F);
        registerCompostable(OPBlocks.CREAM_CAP.get(), 0.65F);
        registerCompostable(OPBlocks.CHICKEN_OF_THE_CAVES.get(), 0.65F);
        registerCompostable(OPBlocks.PRINCESS_JELLY.get(), 0.65F);
        registerCompostable(OPBlocks.BLUE_TRUMPET.get(), 0.65F);
        registerCompostable(OPBlocks.POWDER_GNOME.get(), 0.65F);
        registerCompostable(OPBlocks.BLACKCAP.get(), 0.65F);
        registerCompostable(OPBlocks.CAP_OF_EYE.get(), 0.65F);
        registerCompostable(OPBlocks.GREEN_FUNK.get(), 0.65F);
        registerCompostable(OPBlocks.LIME_NUB.get(), 0.65F);
        registerCompostable(OPBlocks.POP_CAP.get(), 0.65F);
        registerCompostable(OPBlocks.PURPLE_KNOB.get(), 0.65F);
        registerCompostable(OPBlocks.QUEEN_IN_MAGENTA.get(), 0.65F);
        registerCompostable(OPBlocks.SLATESHROOM.get(), 0.65F);
        registerCompostable(OPBlocks.SLIPPERY_TOP.get(), 0.65F);
        registerCompostable(OPBlocks.WHITECAP.get(), 0.65F);
    }

    public static void registerFlammables() {
    }

    public static void registerCompostable(ItemLike item, float chance) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
    }

    public static void registerFlammable(Block block, int igniteChance, int burnChance) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, igniteChance, burnChance);
    }
}
