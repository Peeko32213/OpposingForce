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
public class FrowzyHelmetLayer extends RenderLayer<Frowzy, FrowzyModel> {

    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    private final HumanoidModel<?> defaultBipedModel;

    public FrowzyHelmetLayer(FrowzyRenderer render, EntityRendererProvider.Context context) {
        super(render);
        this.defaultBipedModel = new HumanoidModel<>(context.bakeLayer(ModelLayers.ARMOR_STAND_OUTER_ARMOR));
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
        // todo: fix baby frowzy head pivot
        if (this.getParentModel().young) return;
        poseStack.pushPose();
        ItemStack helmet = frowzy.getItemBySlot(EquipmentSlot.HEAD);
        if (helmet.getItem() instanceof ArmorItem armoritem) {
            if (helmet.canEquip(EquipmentSlot.HEAD, frowzy)) {
                HumanoidModel<?> bipedModel = defaultBipedModel;
                bipedModel = getArmorModelHook(frowzy, helmet, EquipmentSlot.HEAD, bipedModel);
                final boolean notAVanillaModel = bipedModel != defaultBipedModel;
                this.setModelSlotVisible(bipedModel, EquipmentSlot.HEAD);
                this.getParentModel().translateToHead(poseStack);
                final boolean enchanted = helmet.hasFoil();
                if (armoritem instanceof DyeableLeatherItem) {
                    final int dyeColor = ((DyeableLeatherItem) armoritem).getColor(helmet);
                    final float red = (float) (dyeColor >> 16 & 255) / 255.0F;
                    final float green = (float) (dyeColor >> 8 & 255) / 255.0F;
                    final float blue = (float) (dyeColor & 255) / 255.0F;
                    renderHelmet(frowzy, poseStack, bufferSource, packedLightIn, enchanted, bipedModel, red, green, blue, getArmorResource(frowzy, helmet, EquipmentSlot.HEAD, null), notAVanillaModel);
                    renderHelmet(frowzy, poseStack, bufferSource, packedLightIn, enchanted, bipedModel, 1.0F, 1.0F, 1.0F, getArmorResource(frowzy, helmet, EquipmentSlot.HEAD, "overlay"), notAVanillaModel);
                } else {
                    renderHelmet(frowzy, poseStack, bufferSource, packedLightIn, enchanted, bipedModel, 1.0F, 1.0F, 1.0F, getArmorResource(frowzy, helmet, EquipmentSlot.HEAD, null), notAVanillaModel);
                }
            }
        } else {
            this.getParentModel().translateToHead(poseStack);
            poseStack.translate(0, -0.25F, 0.0F);
            poseStack.mulPose((new Quaternionf()).rotateX(Mth.PI));
            poseStack.mulPose((new Quaternionf()).rotateY(Mth.PI));
            poseStack.scale(1.25F, 1.25F, 1.25F);
            Minecraft.getInstance().getItemRenderer().renderStatic(helmet, ItemDisplayContext.FIXED, packedLightIn, OverlayTexture.NO_OVERLAY, poseStack, bufferSource, frowzy.level(), 0);
        }
        poseStack.popPose();
    }

    private void renderHelmet(Frowzy entity, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, boolean glint, HumanoidModel humanoidModel, float red, float green, float blue, ResourceLocation armorResource, boolean notAVanillaModel) {
        VertexConsumer vertexConsumer = ItemRenderer.getFoilBuffer(bufferSource, RenderType.entityCutoutNoCull(armorResource), false, glint);
        this.getParentModel().copyPropertiesTo(humanoidModel);
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