package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.items.LaserBladeItem;
import com.unusualmodding.opposing_force.items.TeslaCannonItem;
import net.minecraft.client.renderer.item.ItemProperties;

import static com.unusualmodding.opposing_force.OpposingForce.modPrefix;

public class OPItemProperties {

    public static void registerItemProperties(){
        registerLaserBlade();
        registerStratoBow();
        registerTeslaCannon();
    }

    private static void registerLaserBlade() {
        ItemProperties.register(OPItems.LASER_BLADE.get(), modPrefix("dyed"), (itemStack, clientLevel, entity, useTicks) -> LaserBladeItem.isDyed(itemStack) ? 1.0F : 0.0F);
    }

    private static void registerStratoBow(){
        ItemProperties.register(OPItems.STRATO_BOW.get(), modPrefix("pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(OPItems.STRATO_BOW.get(), modPrefix("pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }

    private static void registerTeslaCannon() {
        ItemProperties.register(OPItems.TESLA_CANNON.get(), modPrefix("pull"), (itemStack, clientLevel, entity, useTicks) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return TeslaCannonItem.isCharged(itemStack) ? 0.0F : (float) (itemStack.getUseDuration() - entity.getUseItemRemainingTicks()) / (float) TeslaCannonItem.getChargeDuration(itemStack);
            }
        });
        ItemProperties.register(OPItems.TESLA_CANNON.get(), modPrefix("pulling"), (itemStack, clientLevel, entity, useTicks) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack && !TeslaCannonItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemProperties.register(OPItems.TESLA_CANNON.get(), modPrefix("charged"), (itemStack, clientLevel, entity, useTicks) -> TeslaCannonItem.isCharged(itemStack) ? 1.0F : 0.0F);
    }
}
