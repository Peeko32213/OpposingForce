package com.barl_inc.opposing_force.registry;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.items.TeslaCannonItem;
import net.minecraft.client.renderer.item.ItemProperties;

public class OPItemProperties {

    public static void registerItemProperties(){
        registerStratoBow();
        registerTeslaCannon();
        registerSawblade();
    }

    private static void registerStratoBow(){
        ItemProperties.register(OPItems.STRATO_BOW.get(), OpposingForce.location("pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(OPItems.STRATO_BOW.get(), OpposingForce.location("pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }

    private static void registerTeslaCannon() {
        ItemProperties.register(OPItems.TESLA_CANNON.get(), OpposingForce.location("pull"), (itemStack, clientLevel, entity, useTicks) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return TeslaCannonItem.isCharged(itemStack) ? 0.0F : (float) (itemStack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / (float) TeslaCannonItem.getChargeDuration(itemStack);
            }
        });
        ItemProperties.register(OPItems.TESLA_CANNON.get(), OpposingForce.location("pulling"), (itemStack, clientLevel, entity, useTicks) -> entity != null && entity.isUsingItem() && entity.getUseItem() == itemStack && !TeslaCannonItem.isCharged(itemStack) ? 1.0F : 0.0F);
        ItemProperties.register(OPItems.TESLA_CANNON.get(), OpposingForce.location("charged"), (itemStack, clientLevel, entity, useTicks) -> TeslaCannonItem.isCharged(itemStack) ? 1.0F : 0.0F);
    }

    private static void registerSawblade() {
        ItemProperties.register(OPItems.SAWBLADE.get(), OpposingForce.location("active"), (itemStack, clientLevel, entity, useTicks) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.isUsingItem() && entity.getUseItem().is(OPItems.SAWBLADE.get()) ? 1.0F : 0.0F;
            }
        });
    }
}
