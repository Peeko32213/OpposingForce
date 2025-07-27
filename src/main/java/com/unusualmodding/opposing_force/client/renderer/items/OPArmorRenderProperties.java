package com.unusualmodding.opposing_force.client.renderer.items;

import com.unusualmodding.opposing_force.client.models.armor.DeepwovenArmorModel;
import com.unusualmodding.opposing_force.client.models.armor.WoodenArmorModel;
import com.unusualmodding.opposing_force.items.DeepwovenArmorItem;
import com.unusualmodding.opposing_force.items.WoodenArmorItem;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class OPArmorRenderProperties implements IClientItemExtensions {

    private static boolean init;

    public static DeepwovenArmorModel DEEPWOVEN_MODEL;
    public static WoodenArmorModel WOODEN_MODEL;

    public static void initializeModels() {
        init = true;
        DEEPWOVEN_MODEL = new DeepwovenArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(OPModelLayers.DEEPWOVEN_ARMOR));
        WOODEN_MODEL = new WoodenArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(OPModelLayers.WOODEN_ARMOR));

    }

    @Override
    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> humanoidModel) {

        if (!init) {
            initializeModels();
        }

        final var item = itemStack.getItem();

        if (item instanceof DeepwovenArmorItem) {
            return DEEPWOVEN_MODEL;
        }
        if (item instanceof WoodenArmorItem) {
            return WOODEN_MODEL;
        }
        return humanoidModel;
    }
}