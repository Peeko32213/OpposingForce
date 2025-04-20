package com.unusualmodding.opposingforce.core.registry;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.common.enchantments.OPEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class OPCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> DEF_REG = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OpposingForce.MODID);

    public static final RegistryObject<CreativeModeTab> TAB = DEF_REG.register(OpposingForce.MODID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + OpposingForce.MODID))
            .icon(() -> new ItemStack(OPItems.SLUG_EGGS.get()))
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

                    // Items
                    output.accept(OPItems.SLUG_EGGS.get());
                    output.accept(OPItems.ELECTRIC_CHARGE.get());
                    output.accept(OPItems.DEEP_SILK.get());

                    // Mushrooms
                    output.accept(OPBlocks.BLACKCAP.get());
                    output.accept(OPBlocks.BLUE_TRUMPET.get());
                    output.accept(OPBlocks.CAP_OF_EYE.get());
                    output.accept(OPBlocks.CAVE_PATTY.get());
                    output.accept(OPBlocks.CHICKEN_OF_THE_CAVES.get());
                    output.accept(OPBlocks.COPPER_ENOKI.get());
                    output.accept(OPBlocks.CREAM_CAP.get());
                    output.accept(OPBlocks.GREEN_FUNK.get());
                    output.accept(OPBlocks.LIME_NUB.get());
                    output.accept(OPBlocks.POP_CAP.get());
                    output.accept(OPBlocks.POWDER_GNOME.get());
                    output.accept(OPBlocks.PRINCESS_JELLY.get());
                    output.accept(OPBlocks.PURPLE_KNOB.get());
                    output.accept(OPBlocks.QUEEN_IN_MAGENTA.get());
                    output.accept(OPBlocks.RAINCAP.get());
                    output.accept(OPBlocks.SLATESHROOM.get());
                    output.accept(OPBlocks.SLIPPERY_TOP.get());
                    output.accept(OPBlocks.WHITECAP.get());

                    OPEnchantments.addAllEnchantsToCreativeTab(output, OPEnchantments.TESLA_BOW);
                    OPEnchantments.addAllEnchantsToCreativeTab(output, EnchantmentCategory.CROSSBOW);

                    // Unsorted stuff
                    if (!(item.get() instanceof ForgeSpawnEggItem)) {
                        output.accept(item.get());
                    }
                }
            })
            .build());
}
