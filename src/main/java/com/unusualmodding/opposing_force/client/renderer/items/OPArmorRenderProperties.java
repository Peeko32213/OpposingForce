package com.unusualmodding.opposing_force.client.renderer.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.unusualmodding.opposing_force.client.models.armor.*;
import com.unusualmodding.opposing_force.client.models.armor.base.OPArmorModel;
import com.unusualmodding.opposing_force.events.ClientEvents;
import com.unusualmodding.opposing_force.items.*;
import com.unusualmodding.opposing_force.registry.OPModelLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class OPArmorRenderProperties implements IClientItemExtensions {

    private static boolean init;

    public static DeepwovenArmorModel DEEPWOVEN_MODEL;
    public static WoodenArmorModel WOODEN_MODEL;
    public static EmeraldArmorModel EMERALD_MODEL;
    public static StoneArmorModel STONE_MODEL;
    public static CloudBootsModel CLOUD_BOOTS_MODEL;

    public static void initializeModels() {
        init = true;
//        DEEPWOVEN_MODEL = new DeepwovenArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(OPModelLayers.DEEPWOVEN_ARMOR));
//        WOODEN_MODEL = new WoodenArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(OPModelLayers.WOODEN_ARMOR));
//        EMERALD_MODEL = new EmeraldArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(OPModelLayers.EMERALD_ARMOR));
//        STONE_MODEL = new StoneArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(OPModelLayers.STONE_ARMOR));
        CLOUD_BOOTS_MODEL = new CloudBootsModel(Minecraft.getInstance().getEntityModels().bakeLayer(OPModelLayers.CLOUD_BOOTS));
    }

    @Override
    public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> humanoidModel) {

        if (!init) {
            initializeModels();
        }

//        OPArmorModel model = ClientEvents.STONE_ARMOR;

        final var item = itemStack.getItem();

//        if (item instanceof DeepwovenArmorItem) {
//            return DEEPWOVEN_MODEL;
//        }
//        if (item instanceof WoodenArmorItem) {
//            return WOODEN_MODEL;
//        }
//        if (item instanceof EmeraldArmorItem) {
//            return EMERALD_MODEL;
//        }
//        if (item instanceof StoneArmorItem) {
//            return STONE_MODEL;
//        }
        if (item instanceof CloudBootsItem) {
            return entity == null ? CLOUD_BOOTS_MODEL : CLOUD_BOOTS_MODEL.withAnimations(entity);
        }

        return humanoidModel;
    }

    public static void renderCustomArmor(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, ItemStack itemStack, ArmorItem armorItem, Model armorModel, boolean legs, ResourceLocation texture) {

    }
}
