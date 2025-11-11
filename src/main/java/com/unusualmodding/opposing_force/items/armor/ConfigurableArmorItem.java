package com.unusualmodding.opposing_force.items.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.UUID;
import java.util.function.Consumer;

public class ConfigurableArmorItem extends ArmorItem {

    private final ArmorDefinition def;

    public ConfigurableArmorItem(Type type, Properties props, ArmorDefinition def) {
        super(def.material(), type, props);
        this.def = def;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot != getEquipmentSlot()) return super.getAttributeModifiers(slot, stack);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(super.getAttributeModifiers(slot, stack));

        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(this.type);
        for (var entry : def.attributes()) {
            builder.put(entry.attribute(),
                    new AttributeModifier(uuid, entry.attribute().getDescriptionId(),
                            entry.value(), entry.operation()));
        }
        return builder.build();
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return def.canWalkOnPowderedSnow();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) OpposingForce.PROXY.getArmorRenderProperties());
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return def.textureFunction().apply(slot);
    }
}
