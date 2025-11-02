package com.unusualmodding.opposing_force.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPParticles;
import com.unusualmodding.opposing_force.registry.enums.OPTiers.OPArmorMaterials;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public class MoonShoesItem extends ArmorItem {

    public MoonShoesItem(Properties properties) {
        super(OPArmorMaterials.MOON_SHOES, Type.BOOTS, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(super.getAttributeModifiers(slot, stack));
        UUID uuid = ArmorItem.ARMOR_MODIFIER_UUID_PER_TYPE.get(this.type);
        builder.put(OPAttributes.JUMP_POWER.get(), new AttributeModifier(uuid, "Jump power", 1.0F, AttributeModifier.Operation.ADDITION));
        builder.put(OPAttributes.AIR_SPEED.get(), new AttributeModifier(uuid, "Air speed", 0.15F, AttributeModifier.Operation.MULTIPLY_BASE));
        builder.put(ForgeMod.ENTITY_GRAVITY.get(), new AttributeModifier(uuid, "Gravity", -0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL));
        return slot == this.getEquipmentSlot() ? builder.build() : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean isSelected) {
        if (stack.is(OPItems.MOON_SHOES.get()) && entity instanceof LivingEntity livingEntity) {
            if (livingEntity.getItemBySlot(EquipmentSlot.FEET) == stack) {
                if (!livingEntity.onGround() && !livingEntity.onClimbable() && !livingEntity.isInWaterOrBubble()) {
                    if (level.getRandom().nextFloat() < 0.5F) {
                        livingEntity.level().addParticle(OPParticles.MOON_SHOES.get(), livingEntity.position().x, livingEntity.position().y, livingEntity.position().z, (level.getRandom().nextFloat() - 0.5F) / 3.0F, 0.0D, (level.getRandom().nextFloat() - 0.5F) / 3.0F);
                    }
                }
                livingEntity.resetFallDistance();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) OpposingForce.PROXY.getArmorRenderProperties());
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return OpposingForce.MOD_ID + ":textures/models/armor/moon_shoes_layer_1.png";
    }
}
