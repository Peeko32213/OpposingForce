package com.unusualmodding.opposingforce.core.registry;

import com.unusualmodding.opposingforce.OpposingForce;
import com.unusualmodding.opposingforce.common.item.OPTabBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class OPCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> DEF_REG = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OpposingForce.MODID);

    public static final RegistryObject<CreativeModeTab> TAB = DEF_REG.register(OpposingForce.MODID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + OpposingForce.MODID))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .icon(() -> new ItemStack(OPItems.PALE_SPIDER_SPAWN_EGG.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .displayItems((enabledFeatures, output) -> {
                for(RegistryObject<Item> item : OPItems.ITEMS.getEntries()){
                    if(item.get() instanceof OPTabBehavior customTabBehavior){
                        customTabBehavior.fillItemCategory(output);
                    }else{
                        output.accept(item.get());
                    }
                }
            })
            .build());
}
