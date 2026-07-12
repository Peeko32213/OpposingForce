package com.barl_inc.opposing_force.events;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.OpposingForceConfig;
import com.barl_inc.opposing_force.effects.SlugInfestation;
import com.barl_inc.opposing_force.entity.Frowzy;
import com.barl_inc.opposing_force.entity.UmberSpider;
import com.barl_inc.opposing_force.items.LaserBladeItem;
import com.barl_inc.opposing_force.items.SawbladeItem;
import com.barl_inc.opposing_force.items.TremblingSlammer;
import com.barl_inc.opposing_force.items.armor.SlugBaronArmorItem;
import com.barl_inc.opposing_force.registry.*;
import com.barl_inc.opposing_force.registry.OPTrades.MultipleInputsTrade;
import com.barl_inc.opposing_force.registry.tags.OPBiomeTags;
import com.barl_inc.opposing_force.registry.tags.OPBlockTags;
import com.barl_inc.opposing_force.registry.tags.OPEntityTypeTags;
import com.barl_inc.opposing_force.registry.tags.OPItemTags;
import com.barl_inc.opposing_force.world.OPPlayerSavedData;
import com.barl_inc.opposing_force.world.OPWorldData;
import com.barl_inc.opposing_force.world.PlayerData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.tags.DamageTypeTags;
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
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import static com.barl_inc.opposing_force.OpposingForceConfig.*;

import java.util.Collection;
import java.util.List;

@EventBusSubscriber(modid = OpposingForce.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        OPBrewingRecipes.registerPotionRecipes(event.getBuilder());
    }



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
    public static void checkProgressionSpawns(MobSpawnEvent.SpawnPlacementCheck event) {
        EntityType<?> entityType = event.getEntityType();
        ServerLevel level = event.getLevel().getLevel();
        if (entityType.is(OPEntityTypeTags.POST_NETHER)) {
            boolean nether = !OPWorldData.get(level).hasNetherBeenEnteredBefore();
            nether = nether && POST_NETHER.get();
            if (nether) {
                event.setResult(Event.Result.DENY);
                return;
            }
        }
        if (entityType.is(OPEntityTypeTags.POST_END)) {
            boolean postDragon = level.getServer().getWorldData().endDragonFightData().dragonKilled() || level.getServer().getWorldData().endDragonFightData().previouslyKilled();
            postDragon = postDragon && POST_END.get();
            if (!postDragon) {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    //Has been removed
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
        if (event.getTo().equals(Level.NETHER) && !worldData.hasNetherBeenEnteredBefore()) {
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
        if (worldData.hasNetherBeenEnteredBefore() && !data.hasGottenNetherMessage()) {
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
        if (event.getEntity() instanceof Player player && event.getSource().is(DamageTypes.FALL) && (player.getItemBySlot(EquipmentSlot.FEET).is(OPItems.MOON_SHOES.get()))) {
            player.fallDistance = 0.0F;
            event.setCanceled(true);
        }
    }

    //TODO look at this
    @SubscribeEvent
    public static void onMobHurt(LivingDamageEvent.Post event) {
        LivingEntity entity = event.getEntity();
        DamageSource damageSource = event.getSource();
        float damage = damageSource.getOriginalDamage();

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

        if (damageSource.is(DamageTypeTags.IS_PROJECTILE) && damageSource.getDirectEntity() instanceof LivingEntity attacker) {
            float rangedDamage = 0.0F;
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack stack = attacker.getItemBySlot(slot);
                Collection<AttributeModifier> modifiers = stack.getAttributeModifiers(slot).get(OPAttributes.RANGED_DAMAGE.get());
                if (!modifiers.isEmpty()) {
                    rangedDamage += (float) modifiers.stream().mapToDouble(AttributeModifier::getAmount).sum();
                }
            }

            if (rangedDamage > 0.0F) {
                event.setAmount(damage + rangedDamage);
            }
        }
    }

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        Entity target = event.getTarget();
        ItemStack stack = player.getMainHandItem();

        if (player.level() instanceof ServerLevel && event.isVanillaCritical() && stack.getItem() instanceof TremblingSlammer) {
            TremblingSlammer.tremblingSlammerCriticalHit(stack, player, target);
            event.setDamageModifier(1.75F);
        }
    }
    //TODO look at this
    @SubscribeEvent
    public static void onMobAttack(final Living event) {
        final Entity entity = event.getTarget();
        final Entity attacker = event.getEntity();

        if (entity != null && attacker != null) {
            LaserBladeItem.parryAttack(entity, attacker, event);
        }
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        final Projectile projectile = event.getProjectile();
        final Entity entity = event.getEntity();
        HitResult hitResult = event.getRayTraceResult();
        LaserBladeItem.deflectProjectiles(entity, projectile, hitResult, event);
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Level level = event.getPlayer().level();
        Player player = event.getPlayer();
        BlockPos pos = event.getPos();

        if (player.getMainHandItem().getItem() instanceof SawbladeItem && !player.isShiftKeyDown()) {
            SawbladeItem.chopTree(level, pos, player);
        }

        if (player.getMainHandItem().getItem() instanceof TremblingSlammer && !player.isShiftKeyDown()) {
            TremblingSlammer.breakBlocksAroundMinedBlock(level, pos, player);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
            SawbladeItem.onPlayerTick(event.getEntity());
    }
}
