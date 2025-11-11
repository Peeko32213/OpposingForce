package com.unusualmodding.opposing_force.client.renderer.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.opposing_force.client.models.armor.*;
import com.unusualmodding.opposing_force.items.*;
import com.unusualmodding.opposing_force.items.armor.MoonShoesItem;
import com.unusualmodding.opposing_force.items.armor.WoodenArmorItem;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class OPArmorRenderProperties implements IClientItemExtensions {

    private static boolean init;

    public static DeepwovenArmorModel DEEPWOVEN_MODEL;
    public static WoodenArmorModel WOODEN_MODEL;
    public static EmeraldArmorModel EMERALD_MODEL;
    public static StoneArmorModel STONE_MODEL;
    public static MoonShoesModel MOON_SHOES_MODEL;
    public static SlugBaronArmorModel SLUG_BARON_MODEL;

    public static void initializeModels() {
        init = true;
        DEEPWOVEN_MODEL = new DeepwovenArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(OPModelLayers.DEEPWOVEN_ARMOR));
        WOODEN_MODEL = new WoodenArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(OPModelLayers.WOODEN_ARMOR));
        EMERALD_MODEL = new EmeraldArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(OPModelLayers.EMERALD_ARMOR));
        STONE_MODEL = new StoneArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(OPModelLayers.STONE_ARMOR));
        MOON_SHOES_MODEL = new MoonShoesModel(Minecraft.getInstance().getEntityModels().bakeLayer(OPModelLayers.MOON_SHOES));
        SLUG_BARON_MODEL = new SlugBaronArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(OPModelLayers.SLUG_BARON_ARMOR));
    }

    @Override
    public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlot armorSlot, HumanoidModel<?> humanoidModel) {

        if (!init) {
            initializeModels();
        }

        final var item = stack.getItem();

        if (stack.is(OPItems.DEEPWOVEN_BOOTS.get())
                || stack.is(OPItems.DEEPWOVEN_HAT.get())
                || stack.is(OPItems.DEEPWOVEN_PANTS.get())
                || stack.is(OPItems.DEEPWOVEN_TUNIC.get())) {
            return DEEPWOVEN_MODEL;
        }

        if (item instanceof WoodenArmorItem) {
            return WOODEN_MODEL;
        }
        if (stack.is(OPItems.STONE_HELMET.get())
                || stack.is(OPItems.STONE_CHESTPLATE.get())
                || stack.is(OPItems.STONE_LEGGINGS.get())
                || stack.is(OPItems.STONE_BOOTS.get())) {
            return STONE_MODEL;
        }

        if (stack.is(OPItems.EMERALD_MASK.get())
                || stack.is(OPItems.EMERALD_CHESTPLATE.get())
                || stack.is(OPItems.EMERALD_LEGGINGS.get())
                || stack.is(OPItems.EMERALD_BOOTS.get())) {
            return EMERALD_MODEL;
        }

        if (stack.is(OPItems.SLUG_BARON_BOOTS.get())
                || stack.is(OPItems.SLUG_BARON_CHESTPLATE.get())
                || stack.is(OPItems.SLUG_BARON_HELMET.get())
                || stack.is(OPItems.SLUG_BARON_LEGGINGS.get())) {
            return SLUG_BARON_MODEL;
        }


        if (item instanceof MoonShoesItem) {
            return entity == null ? MOON_SHOES_MODEL : MOON_SHOES_MODEL.withAnimations(entity);
        }

        return humanoidModel;
    }

    public static void renderCustomArmor(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, ItemStack itemStack, ArmorItem armorItem, Model armorModel, boolean legs, ResourceLocation texture) {

    }
}
