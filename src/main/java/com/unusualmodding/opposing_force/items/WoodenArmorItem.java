package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class WoodenArmorItem extends ArmorItem {

    public WoodenArmorItem(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) OpposingForce.PROXY.getArmorRenderProperties());
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        if (slot.equals(EquipmentSlot.LEGS)) return OpposingForce.MOD_ID + ":textures/models/armor/wooden_armor_layer_2.png";
        else return OpposingForce.MOD_ID + ":textures/models/armor/wooden_armor_layer_1.png";
    }

    public static int getExtraSaturationFromArmor(LivingEntity entity) {
        int i = 0;
        if (entity.getItemBySlot(EquipmentSlot.HEAD).is(OPItems.WOODEN_MASK.get())) {
            i++;
        }
        if (entity.getItemBySlot(EquipmentSlot.CHEST).is(OPItems.WOODEN_CHESTPLATE.get())) {
            i++;
        }
        if (entity.getItemBySlot(EquipmentSlot.LEGS).is(OPItems.WOODEN_COVER.get())) {
            i++;
        }
        if (entity.getItemBySlot(EquipmentSlot.FEET).is(OPItems.WOODEN_BOOTS.get())) {
            i++;
        }
        return i;
    }
}
