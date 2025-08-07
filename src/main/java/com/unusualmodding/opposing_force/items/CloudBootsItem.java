package com.unusualmodding.opposing_force.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.models.armor.base.OPArmorModel;
import com.unusualmodding.opposing_force.events.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Consumer;

public class CloudBootsItem extends ArmorItem {

    public CloudBootsItem(ArmorMaterial armorMaterial, Properties properties) {
        super(armorMaterial, Type.BOOTS, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(super.getAttributeModifiers(slot, stack));
        UUID uuid = ArmorItem.ARMOR_MODIFIER_UUID_PER_TYPE.get(this.type);
        builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, "Movement speed", 0.1F, AttributeModifier.Operation.MULTIPLY_BASE));
        return slot == this.getEquipmentSlot() ? builder.build() : super.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.BINDING_CURSE || enchantment == Enchantments.VANISHING_CURSE || enchantment.category == EnchantmentCategory.BREAKABLE;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if ((player.fallDistance > 0.0F || player.isSprinting()) && !player.onClimbable()) {
            for (int i = 0; i < 3; i++) {
                player.level().addParticle(ParticleTypes.CLOUD, player.position().x, player.position().y + 0.1D, player.position().z, (level.getRandom().nextFloat() - 0.5F) / 4.0F, 0.05D, (level.getRandom().nextFloat() - 0.5F) / 4.0F);
            }
        }
        player.resetFallDistance();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public OPArmorModel getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel humanoidModel) {
                float ticks = Minecraft.getInstance().getFrameTime();
                float yBodyRot = Mth.rotLerp(ticks, entity.yBodyRotO, entity.yBodyRot);
                float yHeadRot = Mth.rotLerp(ticks, entity.yHeadRotO, entity.yHeadRot);
                float netHeadYaw = yHeadRot - yBodyRot;
                float netHeadPitch = Mth.lerp(ticks, entity.xRotO, entity.getXRot());
                OPArmorModel model = ClientEvents.CLOUD_BOOTS;
                model.slot = armorSlot;
                model.copyFromDefault(humanoidModel);
                model.setupAnim(entity, entity.walkAnimation.position(), entity.walkAnimation.speed(), entity.tickCount + ticks, netHeadYaw, netHeadPitch);
                return model;
            }
        });
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return OpposingForce.MOD_ID + ":textures/models/armor/cloud_boots.png";
    }

//    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> component, TooltipFlag flag) {
//        component.add(this.getDisplayName().withStyle(ChatFormatting.BLUE));
//    }
//
//    public MutableComponent getDisplayName() {
//        return Component.translatable(this.getDescriptionId() + ".desc");
//    }
}
