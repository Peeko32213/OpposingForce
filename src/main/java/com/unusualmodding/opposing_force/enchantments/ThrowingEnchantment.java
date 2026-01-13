package com.unusualmodding.opposing_force.enchantments;

import com.unusualmodding.opposing_force.entity.projectile.ThrownLaserBlade;
import com.unusualmodding.opposing_force.registry.OPEnchantments;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ThrowingEnchantment extends Enchantment {

    public ThrowingEnchantment() {
        super(Rarity.RARE, OPEnchantments.LASER_BLADE, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public static void throwBlade(Level level, Player player, InteractionHand hand, ItemStack itemStack) {
        if (player instanceof ServerPlayer) {
            Vec3 position = player.position().add(0, player.getBbHeight() * 0.5F, 0);
            ThrownLaserBlade entity = new ThrownLaserBlade(level, position.x, position.y, position.z);
            entity.setData(player, 8, itemStack);
            entity.setItem(itemStack);
            entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0F, 2.25F, 0F);
            level.addFreshEntity(entity);
            if (!player.getAbilities().instabuild) {
                player.getInventory().removeItem(itemStack);
            }
            level.playSound(null, entity.blockPosition(), OPSoundEvents.LASER_BLADE_SWING.get(), SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            player.swing(hand, true);
        }
    }

    public static void addCooldown(ServerPlayer player, ThrownLaserBlade laserBlade) {
        if (laserBlade.isAlive() && laserBlade.distanceTo(player) < 3.0F && !player.isCreative()) {
            laserBlade.level().playSound(null, player.blockPosition(), OPSoundEvents.LASER_BLADE_CATCH.get(), SoundSource.PLAYERS, 0.5F, 1.0F / (laserBlade.level().getRandom().nextFloat() * 0.4F + 0.8F));
            int enchantmentLevel = laserBlade.getItem().getEnchantmentLevel(OPEnchantments.THROWING.get());
            if (enchantmentLevel < 5) {
                int duration = 100 - 25 * (enchantmentLevel - 1);
                if (laserBlade.enemiesHit > 0) duration -= (laserBlade.enemiesHit+1) * 20;
                if (duration > 0) player.getCooldowns().addCooldown(laserBlade.getItem().getItem(), duration);
            }
        }
    }
}
