package com.unusualmodding.opposing_force.items.armor;

import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class WoodenArmorItem extends ConfigurableArmorItem {

    public WoodenArmorItem(Type type, Properties properties) {
        super(type, properties, OPArmorDefinitions.WOODEN);
    }

    public static int getExtraSaturationFromArmor(LivingEntity entity) {
        int i = 0;
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(OPItems.WOODEN_MASK.get())) i++;
        if (entity.getItemBySlot(EquipmentSlot.CHEST).is(OPItems.WOODEN_CHESTPLATE.get())) i++;
        if (entity.getItemBySlot(EquipmentSlot.LEGS).is(OPItems.WOODEN_COVER.get())) i++;
        if (entity.getItemBySlot(EquipmentSlot.FEET).is(OPItems.WOODEN_BOOTS.get())) i++;
        return i;
    }
}
