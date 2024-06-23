package com.peeko32213.hole.core.registry;

import com.peeko32213.hole.Hole;
import com.peeko32213.hole.common.item.HoleTabBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class HoleCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> DEF_REG = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Hole.MODID);

    public static final RegistryObject<CreativeModeTab> TAB = DEF_REG.register(Hole.MODID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Hole.MODID))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .icon(() -> new ItemStack(HoleItems.PALE_SPIDER_SPAWN_EGG.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .displayItems((enabledFeatures, output) -> {
                for(RegistryObject<Item> item : HoleItems.ITEMS.getEntries()){
                    if(item.get() instanceof HoleTabBehavior customTabBehavior){
                        customTabBehavior.fillItemCategory(output);
                    }else{
                        output.accept(item.get());
                    }
                }
            })
            .build());
}
