package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPEnchantments;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPTrades.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public static void wandererTradesEvent(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        rareTrades.add(new MultipleInputsTrade(Items.DIAMOND_SWORD, 1, 32, OPItems.EMERALD_SWORD.get(), 1, 1, 20));
        rareTrades.add(new MultipleInputsTrade(Items.DIAMOND_SHOVEL, 1, 32, OPItems.EMERALD_SHOVEL.get(), 1, 1, 20));
        rareTrades.add(new MultipleInputsTrade(Items.DIAMOND_PICKAXE, 1, 32, OPItems.EMERALD_PICKAXE.get(), 1, 1, 20));
        rareTrades.add(new MultipleInputsTrade(Items.DIAMOND_AXE, 1, 32, OPItems.EMERALD_AXE.get(), 1, 1, 20));
        rareTrades.add(new MultipleInputsTrade(Items.DIAMOND_HOE, 1, 32, OPItems.EMERALD_HOE.get(), 1, 1, 20));
        rareTrades.add(new MultipleInputsTrade(Items.DIAMOND_HELMET, 1, 32, OPItems.EMERALD_MASK.get(), 1, 1, 20));
        rareTrades.add(new MultipleInputsTrade(Items.DIAMOND_CHESTPLATE, 1, 32, OPItems.EMERALD_CHESTPLATE.get(), 1, 1, 20));
        rareTrades.add(new MultipleInputsTrade(Items.DIAMOND_LEGGINGS, 1, 32, OPItems.EMERALD_LEGGINGS.get(), 1, 1, 20));
        rareTrades.add(new MultipleInputsTrade(Items.DIAMOND_BOOTS, 1, 32, OPItems.EMERALD_BOOTS.get(), 1, 1, 20));

    }

    @SubscribeEvent
    public static void onMobHurt(final LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource source = event.getSource();

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (slot.isArmor()) {
                if (source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.IN_FIRE)) {
                    if (stack.is(OPItems.WOODEN_MASK.get()) || stack.is(OPItems.WOODEN_CHESTPLATE.get()) || stack.is(OPItems.WOODEN_COVER.get()) || stack.is(OPItems.WOODEN_BOOTS.get())) {
                        stack.hurtAndBreak(2, entity, (livingEntity) -> livingEntity.broadcastBreakEvent(slot));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.getCommandSenderWorld();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (stack.is(OPItems.WOODEN_MASK.get()) || stack.is(OPItems.WOODEN_CHESTPLATE.get()) || stack.is(OPItems.WOODEN_COVER.get()) || stack.is(OPItems.WOODEN_BOOTS.get())) {
                if (stack.getEnchantmentLevel(OPEnchantments.PHOTOSYNTHESIS.get()) > 0) {
                    if (!stack.isEmpty() && level.getGameTime() % 600 == 0 && level.getBrightness(LightLayer.SKY, entity.blockPosition()) >= 14 && level.isDay()) {
                        entity.heal(1);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void stopFarmlandTrample(BlockEvent.FarmlandTrampleEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.getItemBySlot(EquipmentSlot.FEET).is(OPItems.WOODEN_BOOTS.get())) {
                event.setCanceled(true);
            }
        }
    }
}
