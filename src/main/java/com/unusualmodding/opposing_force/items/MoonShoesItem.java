package com.unusualmodding.opposing_force.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPAttributes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.UUID;

public class MoonShoesItem extends ArmorItem {

    public MoonShoesItem(ArmorMaterial armorMaterial, Properties properties) {
        super(armorMaterial, Type.BOOTS, properties);
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
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!player.onGround() && !player.onClimbable() && !player.isInWaterOrBubble() && player.fallDistance > 0.1D) {
            player.level().addParticle(ParticleTypes.CLOUD, player.position().x, player.position().y, player.position().z, (level.getRandom().nextFloat() - 0.5F) / 3.0F, 0.0D, (level.getRandom().nextFloat() - 0.5F) / 3.0F);
        }
        player.resetFallDistance();
    }

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
