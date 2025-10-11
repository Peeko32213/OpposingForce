package com.unusualmodding.opposing_force.registry;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class OPItemProperties {

    public static void addItemProperties(){
        makeTeslaBow(OPItems.TESLA_CANNON.get());
    }

    private static void makeTeslaBow(Item item){
        ItemProperties.register(item, new ResourceLocation("pull"), (itemStack, clientLevel, entity, useTicks) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return CrossbowItem.isCharged(itemStack) ? 0.0F : (float) (itemStack.getUseDuration() - entity.getUseItemRemainingTicks()) / (float) CrossbowItem.getChargeDuration(itemStack);
            }
        });
        ItemProperties.register(item, new ResourceLocation("pulling"), (itemStack, clientLevel, entity, useTicks) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack && !CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemProperties.register(item, new ResourceLocation("charged"), (itemStack, clientLevel, entity, useTicks) -> CrossbowItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemProperties.register(item, new ResourceLocation("firework"), (itemStack, clientLevel, entity, useTicks) -> CrossbowItem.isCharged(itemStack) && CrossbowItem.containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
    }
}
