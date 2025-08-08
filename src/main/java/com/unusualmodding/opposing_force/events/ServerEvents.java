package com.unusualmodding.opposing_force.events;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        Level world = entity.getCommandSenderWorld();
        MobEffectInstance effectInstance = entity.getEffect(MobEffects.JUMP);
        float f = effectInstance == null ? 0.0F : (float) (effectInstance.getAmplifier() + 1);
        int damage = Mth.ceil((event.getDistance() - 3.0F - f) * event.getDamageMultiplier());
        if (entity instanceof Player player) {
            if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == OPItems.STONE_BOOTS.get()) {
                if (damage > 0) {
                    for (LivingEntity target : world.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(1.0D, 0.0D, 1.0D))) {
                        if (entity != target) target.hurt(target.damageSources().fall(), damage);
                    }
                }
            }
        }
    }
}
