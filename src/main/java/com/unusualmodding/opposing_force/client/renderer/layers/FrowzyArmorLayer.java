package com.unusualmodding.opposing_force.client.renderer.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.unusualmodding.opposing_force.client.models.entity.FrowzyModel;
import com.unusualmodding.opposing_force.client.renderer.FrowzyRenderer;
import com.unusualmodding.opposing_force.entity.Frowzy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import org.joml.Quaternionf;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class FrowzyArmorLayer extends RenderLayer<Frowzy, FrowzyModel> {

    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    private final HumanoidModel<?> defaultBipedModel;
    private final FrowzyRenderer renderer;

    public FrowzyArmorLayer(FrowzyRenderer render, EntityRendererProvider.Context context) {
        super(render);
        defaultBipedModel = new HumanoidModel<>(context.bakeLayer(ModelLayers.ARMOR_STAND_OUTER_ARMOR));
        this.renderer = render;
    }

    public static ResourceLocation getArmorResource(Entity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type) {
        ArmorItem item = (ArmorItem) stack.getItem();
        String texture = item.getMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (1), type == null ? "" : String.format("_%s", type));

        s1 = ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
        ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s1);

        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s1);
            ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
        }

        return resourcelocation;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn, Frowzy frowzy, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        poseStack.pushPose();
        poseStack.pushPose();
        ItemStack helmet = frowzy.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.getItem() instanceof ArmorItem armoritem) {
            if (helmet.canEquip(EquipmentSlot.HEAD, frowzy)) {
                HumanoidModel<?> bipedModel = defaultBipedModel;
                bipedModel = getArmorModelHook(frowzy, helmet, EquipmentSlot.HEAD, bipedModel);
                final boolean notAVanillaModel = bipedModel != defaultBipedModel;
                this.setModelSlotVisible(bipedModel, EquipmentSlot.HEAD);
                translateToHead(poseStack);
                final boolean enchanted = helmet.hasFoil();
                if (armoritem instanceof DyeableLeatherItem) {
                    final int i = ((DyeableLeatherItem) armoritem).getColor(helmet);
                    final float f = (float) (i >> 16 & 255) / 255.0F;
                    final float f1 = (float) (i >> 8 & 255) / 255.0F;
                    final float f2 = (float) (i & 255) / 255.0F;
                    renderHelmet(frowzy, poseStack, bufferSource, packedLightIn, enchanted, bipedModel, f, f1, f2, getArmorResource(frowzy, helmet, EquipmentSlot.HEAD, null), notAVanillaModel);
                    renderHelmet(frowzy, poseStack, bufferSource, packedLightIn, enchanted, bipedModel, 1.0F, 1.0F, 1.0F, getArmorResource(frowzy, helmet, EquipmentSlot.HEAD, "overlay"), notAVanillaModel);
                } else {
                    renderHelmet(frowzy, poseStack, bufferSource, packedLightIn, enchanted, bipedModel, 1.0F, 1.0F, 1.0F, getArmorResource(frowzy, helmet, EquipmentSlot.HEAD, null), notAVanillaModel);
                }
            }
        } else {
            translateToHead(poseStack);
            poseStack.translate(0, -0.2, -0.1F);
            poseStack.mulPose((new Quaternionf()).rotateX(Mth.PI));
            poseStack.mulPose((new Quaternionf()).rotateY(Mth.PI));
            poseStack.scale(1.0F, 1.0F, 1.0F);
            Minecraft.getInstance().getItemRenderer().renderStatic(helmet, ItemDisplayContext.FIXED, packedLightIn, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, frowzy.level(), 0);
        }
//        poseStack.popPose();
//        poseStack.pushPose();
//        ItemStack chestplate = frowzy.getItemBySlot(EquipmentSlot.CHEST);
//        if (chestplate.getItem() instanceof ArmorItem armoritem) {
//            if (armoritem.getEquipmentSlot() == EquipmentSlot.CHEST) {
//                HumanoidModel<?> bipedModel = defaultBipedModel;
//                bipedModel = getArmorModelHook(frowzy, chestplate, EquipmentSlot.CHEST, bipedModel);
//                boolean notAVanillaModel = bipedModel != defaultBipedModel;
//                this.setModelSlotVisible(bipedModel, EquipmentSlot.CHEST);
//                translateToChest(poseStack);
//                poseStack.translate(0.0F, -0.4F, 0.025F);
//                poseStack.scale(1.0F, 1.0F, 1.0F);
//                boolean flag1 = chestplate.hasFoil();
//                if (armoritem instanceof net.minecraft.world.item.DyeableLeatherItem) {
//                    int i = ((net.minecraft.world.item.DyeableLeatherItem) armoritem).getColor(chestplate);
//                    float f = (float) (i >> 16 & 255) / 255.0F;
//                    float f1 = (float) (i >> 8 & 255) / 255.0F;
//                    float f2 = (float) (i & 255) / 255.0F;
//                    renderChestplate(frowzy, poseStack, bufferSource, packedLightIn, flag1, bipedModel, f, f1, f2, getArmorResource(frowzy, chestplate, EquipmentSlot.CHEST, null), notAVanillaModel);
//                    renderChestplate(frowzy, poseStack, bufferSource, packedLightIn, flag1, bipedModel, 1.0F, 1.0F, 1.0F, getArmorResource(frowzy, chestplate, EquipmentSlot.CHEST, "overlay"), notAVanillaModel);
//                } else {
//                    renderChestplate(frowzy, poseStack, bufferSource, packedLightIn, flag1, bipedModel, 1.0F, 1.0F, 1.0F, getArmorResource(frowzy, chestplate, EquipmentSlot.CHEST, null), notAVanillaModel);
//                }
//
//            }
//        }
        poseStack.popPose();
        poseStack.popPose();
    }

    private void translateToChest(PoseStack poseStack) {
        this.renderer.getModel().root.translateAndRotate(poseStack);
        this.renderer.getModel().Body.translateAndRotate(poseStack);
    }

    private void translateToHead(PoseStack poseStack) {
        this.renderer.getModel().root.translateAndRotate(poseStack);
        this.renderer.getModel().Body.translateAndRotate(poseStack);
        this.renderer.getModel().Head.translateAndRotate(poseStack);
        poseStack.translate(0.0F, -0.025F, 0.025F);
        if (this.renderer.getModel().young) {
            poseStack.scale(0.75F, 0.75F, 0.75F);
            poseStack.translate(0.05F, 0.0F, 0.0F);
        }
    }

//    private void renderChestplate(Frowzy entity, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, boolean glint, HumanoidModel humanoidModel, float red, float green, float blue, ResourceLocation armorResource, boolean notAVanillaModel) {
//        VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(bufferSource, RenderType.entityCutoutNoCull(armorResource), false, glint);
//        renderer.getModel().copyPropertiesTo(humanoidModel);
//        humanoidModel.rightArm.x = renderer.getModel().Arm2.x;
//        humanoidModel.rightArm.y = renderer.getModel().Arm2.y;
//        humanoidModel.rightArm.z = renderer.getModel().Arm2.z;
//        humanoidModel.rightArm.xRot = renderer.getModel().Arm2.xRot;
//        humanoidModel.rightArm.yRot = renderer.getModel().Arm2.yRot;
//        humanoidModel.rightArm.zRot = renderer.getModel().Arm2.zRot;
//        humanoidModel.leftArm.x = renderer.getModel().Arm1.x;
//        humanoidModel.leftArm.y = renderer.getModel().Arm1.y;
//        humanoidModel.leftArm.z = renderer.getModel().Arm1.z;
//        humanoidModel.leftArm.xRot = renderer.getModel().Arm1.xRot;
//        humanoidModel.leftArm.yRot = renderer.getModel().Arm1.yRot;
//        humanoidModel.leftArm.zRot = renderer.getModel().Arm1.zRot;
//        humanoidModel.leftArm.y = renderer.getModel().Arm1.y + 7;
//        humanoidModel.rightArm.y = renderer.getModel().Arm2.y + 7;
//        humanoidModel.leftArm.z = renderer.getModel().Arm1.z - 1;
//        humanoidModel.rightArm.z = renderer.getModel().Arm2.z - 1;
//        humanoidModel.leftArm.x = renderer.getModel().Arm1.z + 6;
//        humanoidModel.rightArm.x = renderer.getModel().Arm2.z - 6;
//        humanoidModel.body.visible = false;
//        humanoidModel.renderToBuffer(poseStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
//        humanoidModel.body.visible = true;
//        humanoidModel.rightArm.visible = false;
//        humanoidModel.leftArm.visible = false;
//        humanoidModel.renderToBuffer(poseStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
//        humanoidModel.rightArm.visible = true;
//        humanoidModel.leftArm.visible = true;
//    }

    private void renderHelmet(Frowzy entity, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, boolean glint, HumanoidModel humanoidModel, float red, float green, float blue, ResourceLocation armorResource, boolean notAVanillaModel) {
        VertexConsumer vertexConsumer = ItemRenderer.getFoilBuffer(bufferSource, RenderType.entityCutoutNoCull(armorResource), false, glint);
        renderer.getModel().copyPropertiesTo(humanoidModel);
        humanoidModel.head.xRot = 0F;
        humanoidModel.head.yRot = 0F;
        humanoidModel.head.zRot = 0F;
        humanoidModel.hat.xRot = 0F;
        humanoidModel.hat.yRot = 0F;
        humanoidModel.hat.zRot = 0F;
        humanoidModel.head.x = 0F;
        humanoidModel.head.y = 0F;
        humanoidModel.head.z = 0F;
        humanoidModel.hat.x = 0F;
        humanoidModel.hat.y = 0F;
        humanoidModel.hat.z = 0F;
        humanoidModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }

    protected void setModelSlotVisible(HumanoidModel<?> model, EquipmentSlot slot) {
        this.setModelVisible(model);
        switch (slot) {
            case HEAD -> {
                model.head.visible = true;
                model.hat.visible = true;
            }
            case CHEST -> {
                model.body.visible = true;
                model.rightArm.visible = true;
                model.leftArm.visible = true;
            }
            case LEGS -> {
                model.body.visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
            case FEET -> {
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
        }
    }

    protected void setModelVisible(HumanoidModel<?> model) {
        model.setAllVisible(false);
    }

    protected HumanoidModel<?> getArmorModelHook(LivingEntity entity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel<?> model) {
         Model basicModel = ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
         return basicModel instanceof HumanoidModel ? (HumanoidModel<?>) basicModel : model;
    }
}