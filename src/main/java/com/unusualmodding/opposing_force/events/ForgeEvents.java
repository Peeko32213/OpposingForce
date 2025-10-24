package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.OpposingForceConfig;
import com.unusualmodding.opposing_force.effects.SlugInfestation;
import com.unusualmodding.opposing_force.entity.Frowzy;
import com.unusualmodding.opposing_force.entity.UmberSpider;
import com.unusualmodding.opposing_force.registry.*;
import com.unusualmodding.opposing_force.registry.OPTrades.*;
import com.unusualmodding.opposing_force.registry.tags.OPBiomeTags;
import com.unusualmodding.opposing_force.registry.tags.OPBlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.List;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {

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
    public static void onLivingSpawn(MobSpawnEvent.FinalizeSpawn event) {
        LivingEntity entity = event.getEntity();
        LevelAccessor level = event.getLevel();
        boolean validSpawn = event.getSpawnType() == MobSpawnType.NATURAL || event.getSpawnType() == MobSpawnType.CHUNK_GENERATION;
        if (event.getResult() != Event.Result.DENY) {
            if (validSpawn && entity.getType() == EntityType.ZOMBIE && event.getY() < OpposingForceConfig.FROWZY_SPAWN_HEIGHT.get()) {
                Zombie zombie = (Zombie) entity;
                if (level.getBlockState(zombie.blockPosition().below()).is(OPBlockTags.FROWZY_SPAWNABLE_ON) && level.getBiome(entity.blockPosition()).is(OPBiomeTags.HAS_FROWZY) && level.getRandom().nextDouble() < OpposingForceConfig.FROWZY_REPLACE_ZOMBIE_CHANCE.get()) {
                    Frowzy frowzy = OPEntities.FROWZY.get().create((Level) level);
                    if (frowzy != null) {
                        frowzy.copyPosition(zombie);
                        level.addFreshEntity(frowzy);
                    }
                    event.setSpawnCancelled(true);
                    event.setResult(Event.Result.DENY);
                }
            }
            if (validSpawn && entity.getType() == EntityType.SPIDER && event.getY() < OpposingForceConfig.UMBER_SPIDER_SPAWN_HEIGHT.get()) {
                Spider spider = (Spider) entity;
                if (level.getBlockState(spider.blockPosition().below()).is(OPBlockTags.UMBER_SPIDER_SPAWNABLE_ON) && level.getBiome(entity.blockPosition()).is(OPBiomeTags.HAS_UMBER_SPIDER) && level.getRandom().nextDouble() < OpposingForceConfig.UMBER_SPIDER_REPLACE_SPIDER_CHANCE.get()) {
                    UmberSpider umberSpider = OPEntities.UMBER_SPIDER.get().create((Level) level);
                    if (umberSpider != null) {
                        umberSpider.copyPosition(spider);
                        level.addFreshEntity(umberSpider);
                    }
                    event.setSpawnCancelled(true);
                    event.setResult(Event.Result.DENY);
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

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();

        float jumpPower = 0.0F;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(slot).get(OPAttributes.JUMP_POWER.get());
            if (!modifiers.isEmpty()) {
                jumpPower += (float) modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
            }
        }

        float jumpPowerModifier = jumpPower / 10;
        if (jumpPower > 0.0F) {
            entity.setDeltaMovement(entity.getDeltaMovement().x(), entity.getDeltaMovement().y() + jumpPowerModifier, entity.getDeltaMovement().z());
        }
    }

    @SubscribeEvent
    public static void increaseXpGained(PlayerXpEvent.XpChange event) {
        Player player = event.getEntity();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = player.getItemBySlot(slot);
            Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(slot).get(OPAttributes.EXPERIENCE_GAIN.get());
            if (!modifiers.isEmpty()) {
                float experienceBoost = event.getAmount() * (float) modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
                int base = Mth.floor(experienceBoost);
                float bonus = Mth.frac(experienceBoost);
                if (bonus != 0.0F && Math.random() < bonus) {
                    ++base;
                }
                event.setAmount(event.getAmount() + base);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingVisibility(LivingEvent.LivingVisibilityEvent event) {
        if (event.getLookingEntity() != null) {
            double attributeValue = 0.0D;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack stack = event.getEntity().getItemBySlot(slot);
                Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(slot).get(OPAttributes.STEALTH.get());
                if (!modifiers.isEmpty()) {
                    attributeValue += modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
                }
            }
            if (attributeValue > 0.0D) {
                event.modifyVisibility(Math.max(1.0D - attributeValue, 0.0D));
            }
        }
    }

    @SubscribeEvent
    public static void onMobHurt(final LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource damageSource = event.getSource();

        for (MobEffectInstance activeEffect : entity.getActiveEffects()) {
            if (activeEffect.getEffect() == OPEffects.SLUG_INFESTATION.get()) {
                SlugInfestation.onMobHurt(entity, activeEffect.getAmplifier());
            }
        }

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (slot.isArmor()) {
                if (damageSource.is(DamageTypes.ON_FIRE) || damageSource.is(DamageTypes.IN_FIRE)) {
                    if (stack.is(OPItems.WOODEN_MASK.get()) || stack.is(OPItems.WOODEN_CHESTPLATE.get()) || stack.is(OPItems.WOODEN_COVER.get()) || stack.is(OPItems.WOODEN_BOOTS.get())) {
                        stack.hurtAndBreak(2, entity, (livingEntity) -> livingEntity.broadcastBreakEvent(slot));
                    }
                }
            }
        }

        if (damageSource.is(OPDamageTypes.ELECTRIFIED) || damageSource.is(OPDamageTypes.ELECTRIC)) {
            float electricResistance = 0.0F;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack stack = entity.getItemBySlot(slot);
                Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(slot).get(OPAttributes.ELECTRIC_RESISTANCE.get());
                if (!modifiers.isEmpty()) {
                    electricResistance += (float) modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
                }
            }

            if (electricResistance > 0.0F) {
                event.setAmount(event.getAmount() - event.getAmount() * electricResistance);
            }
        }
    }
}
