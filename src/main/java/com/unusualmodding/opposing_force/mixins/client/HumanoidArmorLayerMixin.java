package com.unusualmodding.opposing_force.mixins.client;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.unusualmodding.opposing_force.client.renderer.items.OPArmorRenderProperties;
import com.unusualmodding.opposing_force.items.interfaces.CustomArmorRender;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin extends RenderLayer {

    private static final Map<String, ResourceLocation> OP_ARMOR_LOCATION_CACHE = Maps.newHashMap();
    private ItemStack lastArmorItemStackRendered = ItemStack.EMPTY;

    @Shadow
    protected abstract void setPartVisibility(HumanoidModel humanoidModel, EquipmentSlot equipmentSlot);

    public HumanoidArmorLayerMixin(RenderLayerParent renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(method = {"renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;)V"}, at = @At(value = "HEAD"), cancellable = true)
    private void opposingForce$renderArmorPiece(PoseStack poseStack, MultiBufferSource multiBufferSource, LivingEntity livingEntity, EquipmentSlot equipmentSlot, int light, HumanoidModel humanoidModel, CallbackInfo ci) {
        ItemStack itemstack = livingEntity.getItemBySlot(equipmentSlot);
        if (itemstack.getItem() instanceof CustomArmorRender) {
            ci.cancel();
            lastArmorItemStackRendered = livingEntity.getItemBySlot(equipmentSlot);
            Item item = itemstack.getItem();
            if (item instanceof ArmorItem armorItem) {
                if (armorItem.getEquipmentSlot() == equipmentSlot) {
                    boolean legs = equipmentSlot == EquipmentSlot.LEGS;
                    HumanoidModel model = this.getParentModel() instanceof HumanoidModel humanoidModel1 ? humanoidModel1 : humanoidModel;
                    Model armorModel = ForgeHooksClient.getArmorModel(livingEntity, itemstack, equipmentSlot, model);
                    setPartVisibility((HumanoidModel) armorModel, equipmentSlot);
                    ResourceLocation texture = opposingForce$getArmorResource(livingEntity, itemstack, equipmentSlot, null);
                    OPArmorRenderProperties.renderCustomArmor(poseStack, multiBufferSource, light, lastArmorItemStackRendered, armorItem, armorModel, legs, texture);
                }
            }
        }
    }

    @Unique
    private ResourceLocation opposingForce$getArmorResource(LivingEntity entity, ItemStack stack, EquipmentSlot slot, @Nullable String type) {
        ArmorItem item = (ArmorItem) stack.getItem();
        String texture = item.getMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s1 = String.format(java.util.Locale.ROOT, "%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (slot == EquipmentSlot.LEGS ? 2 : 1), type == null ? "" : String.format(java.util.Locale.ROOT, "_%s", type));

        s1 = ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
        ResourceLocation resourcelocation = OP_ARMOR_LOCATION_CACHE.get(s1);

        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s1);
            OP_ARMOR_LOCATION_CACHE.put(s1, resourcelocation);
        }

        return resourcelocation;
    }
}
