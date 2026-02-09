package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.OpposingForceConfig;
import com.unusualmodding.opposing_force.effects.SlugInfestation;
import com.unusualmodding.opposing_force.entity.Frowzy;
import com.unusualmodding.opposing_force.entity.UmberSpider;
import com.unusualmodding.opposing_force.items.LaserBladeItem;
import com.unusualmodding.opposing_force.items.armor.SlugBaronArmorItem;
import com.unusualmodding.opposing_force.items.tools.SawbladeItem;
import com.unusualmodding.opposing_force.registry.*;
import com.unusualmodding.opposing_force.registry.OPTrades.MultipleInputsTrade;
import com.unusualmodding.opposing_force.registry.tags.OPBiomeTags;
import com.unusualmodding.opposing_force.registry.tags.OPBlockTags;
import com.unusualmodding.opposing_force.registry.tags.OPItemTags;
import com.unusualmodding.opposing_force.world.OPPlayerSavedData;
import com.unusualmodding.opposing_force.world.OPWorldData;
import com.unusualmodding.opposing_force.world.PlayerData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
                        if (zombie.isBaby()) frowzy.setBaby(true);
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
        LivingEntity entity = event.getEntity();
        EntityType<?> looking = event.getLookingEntity().getType();
        ItemStack headStack = entity.getItemBySlot(EquipmentSlot.HEAD);
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

            if (
                    looking == OPEntities.DICER.get() && headStack.is(OPItems.DICER_HEAD.get()) ||
                            looking == OPEntities.FROWZY.get() && headStack.is(OPItems.FROWZY_HEAD.get()) ||
                            looking == OPEntities.RAMBLER.get() && headStack.is(OPItemTags.RAMBLER_SKULLS) ||
                            looking == OPEntities.SKYVERN.get() && headStack.is(OPItems.SKYVERN_HEAD.get()) ||
                            looking == OPEntities.TART.get() && headStack.is(OPItems.TART_HEAD.get()) ||
                            looking == OPEntities.WHIZZ.get() && headStack.is(OPItems.WHIZZ_HEAD.get())
            ) {
                event.modifyVisibility(0.5F);
            }
        }
    }

    @SubscribeEvent
    public static void changedDimensionEvent(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        ServerLevel level = player.getServer().overworld();
        OPWorldData worldData = OPWorldData.get(level);
        if (event.getTo().equals(Level.NETHER) && !worldData.isHasNetherBeenEnteredBefore()) {
            worldData.setHasNetherBeenEnteredBefore(true);
            MinecraftServer server = level.getServer();
            PlayerList list = server.getPlayerList();
            List<ServerPlayer> players = list.getPlayers();
            OPPlayerSavedData playerSavedData = OPPlayerSavedData.get(level);

            for (ServerPlayer serverPlayer : players) {
                playerSavedData.sendNetherMessageAndMarkComplete(serverPlayer);
            }
        }
    }

    @SubscribeEvent
    public static void performLoginThings(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        ServerLevel level = player.getServer().overworld();
        OPWorldData worldData = OPWorldData.get(level);
        OPPlayerSavedData playerSavedData = OPPlayerSavedData.get(level);
        PlayerData data = playerSavedData.getPlayerData(player.getUUID());
        if (worldData.isHasNetherBeenEnteredBefore() && !data.hasGottenNetherMessage()) {
            playerSavedData.sendNetherMessageAndMarkComplete(player);
        }
    }

    // @SubscribeEvent
    // public static void hatEquippedEvent(LivingEquipmentChangeEvent event) {
    //     if (event.getTo().is(OPItems.DEEPWOVEN_HAT.get()) || event.getFrom().is(OPItems.DEEPWOVEN_HAT.get())) {
    //         if (event.getEntity() instanceof Player player) {
    //             player.refreshDisplayName();
    //         }
    //     }
    // }

    @SubscribeEvent
    public void livingHurt(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource().is(DamageTypes.FALL) && (player.getItemBySlot(EquipmentSlot.FEET).is(OPItems.MOON_SHOES.get()) || player.getItemBySlot(EquipmentSlot.LEGS).is(OPItems.LEAPING_LEGGINGS.get()))) {
            player.fallDistance = 0.0F;
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onMobHurt(final LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource damageSource = event.getSource();

        for (MobEffectInstance activeEffect : entity.getActiveEffects()) {
            if (activeEffect.getEffect() == OPMobEffects.SLUG_INFESTATION.get()) {
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

        if (SlugBaronArmorItem.wearingFullSlugBaronSet(entity)) {
            SlugBaronArmorItem.onHurt(entity);
        }
    }


    @SubscribeEvent
    public static void onMobAttack(final LivingAttackEvent event) {
        final LivingEntity entity = event.getEntity();
        final Entity attacker = event.getSource().getDirectEntity();

        if (entity != null && attacker != null) {
            Vec3 lookVec = entity.getLookAngle().normalize();
            Vec3 directionToTarget = attacker.position().subtract(entity.position()).normalize();
            double dot = lookVec.dot(directionToTarget);

            // laser blade parry
            if (entity.isUsingItem() && entity.getUseItem().getItem() instanceof LaserBladeItem && entity.getUseItem().getUseDuration() - entity.getUseItemRemainingTicks() <= 5) {
                if (dot > 0.0) {
                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), OPSoundEvents.LASER_BLADE_BLOCK.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (entity.level().getRandom().nextFloat() * 0.4F + 0.8F));
                    if (attacker instanceof LivingEntity livingAttacker) {
                        double knockbackMulti = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.KNOCKBACK, entity.getUseItem());
                        if (knockbackMulti != 0) {
                            knockbackMulti = 1 + knockbackMulti * 0.5D;
                        } else {
                            knockbackMulti = 1;
                        }
                        int fireAspect = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.FIRE_ASPECT, entity.getUseItem());
                        if (fireAspect > 0) {
                            livingAttacker.setSecondsOnFire(3 * fireAspect);
                        }
                        if (livingAttacker instanceof Warden && entity instanceof Player) {
                            OPCriterion.PARRY_WARDEN_WITH_LASER_BLADE.trigger((ServerPlayer) entity);
                        }
                        livingAttacker.knockback(0.55F * knockbackMulti, attacker.getDeltaMovement().x, attacker.getDeltaMovement().z);
                        livingAttacker.knockback(0.5F * knockbackMulti, entity.getX() - livingAttacker.getX(), entity.getZ() - livingAttacker.getZ());
                    }
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        final Projectile projectile = event.getProjectile();
        final Entity entity = event.getEntity();

        // laser blade deflection
        if (event.getRayTraceResult() instanceof EntityHitResult result) {
            Entity resultEntity = result.getEntity();
            if (entity != null && resultEntity instanceof LivingEntity entityBlocking) {
                if (entityBlocking.isUsingItem() && entityBlocking.getUseItem().getItem() instanceof LaserBladeItem && entityBlocking.getUseItem().getUseDuration() - entityBlocking.getUseItemRemainingTicks() <= 5) {
                    Vec3 lookVec = entityBlocking.getLookAngle().normalize();
                    Vec3 directionToTarget = entity.position().subtract(entityBlocking.position()).normalize();
                    double dot = lookVec.dot(directionToTarget);

                    if (dot > 0.0) {
                        projectile.setOwner(entityBlocking);
                        Vec3 rebound = entityBlocking.getLookAngle();
                        projectile.shoot(rebound.x(), rebound.y(), rebound.z(), 1.5F, 0.0F);
                        if (projectile instanceof AbstractHurtingProjectile hurting) {
                            hurting.xPower = rebound.x() * 0.1D;
                            hurting.yPower = rebound.y() * 0.1D;
                            hurting.zPower = rebound.z() * 0.1D;
                        }
                        entityBlocking.level().playSound(null, entityBlocking.getX(), entityBlocking.getY(), entityBlocking.getZ(), OPSoundEvents.LASER_BLADE_BLOCK.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (entityBlocking.level().getRandom().nextFloat() * 0.4F + 0.8F));
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Level level = event.getPlayer().level();
        Player player = event.getPlayer();
        BlockPos pos = event.getPos();

        if (player.getMainHandItem().getItem() instanceof SawbladeItem && !player.isShiftKeyDown()) {
            SawbladeItem.chopTree(level, pos, player);
        }
    }
}
