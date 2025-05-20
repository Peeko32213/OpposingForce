package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class OPItemProperties {
    public static void addItemProperties(){
        makeTeslaBow(OPItems.TESLA_BOW.get());
    }

    private static void makeTeslaBow(Item item){
        ItemProperties.register(item, new ResourceLocation("pull"), (p_174610_, p_174611_, p_174612_, p_174613_) -> {
            if (p_174612_ == null) {
                return 0.0F;
            } else {
                return CrossbowItem.isCharged(p_174610_) ? 0.0F : (float)(p_174610_.getUseDuration() - p_174612_.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(p_174610_);
            }
        });
        ItemProperties.register(item, new ResourceLocation("pulling"), (p_174605_, p_174606_, p_174607_, p_174608_) -> {
            return p_174607_ != null && p_174607_.isUsingItem() && p_174607_.getUseItem() == p_174605_ && !CrossbowItem.isCharged(p_174605_) ? 1.0F : 0.0F;
        });
        ItemProperties.register(item, new ResourceLocation("charged"), (p_275891_, p_275892_, p_275893_, p_275894_) -> {
            return CrossbowItem.isCharged(p_275891_) ? 1.0F : 0.0F;
        });
        ItemProperties.register(item, new ResourceLocation("firework"), (p_275887_, p_275888_, p_275889_, p_275890_) -> {
            return CrossbowItem.isCharged(p_275887_) && CrossbowItem.containsChargedProjectile(p_275887_, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
        });
    }
}
