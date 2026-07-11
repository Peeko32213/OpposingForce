package com.barl_inc.opposing_force.enchantments;

import com.barl_inc.opposing_force.registry.OPEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;

public class KickbackEnchantment extends Enchantment {

    public KickbackEnchantment() {
        super(Rarity.UNCOMMON, OPEnchantments.TESLA_CANNON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    protected boolean checkCompatibility(Enchantment enchantment) {
        return !enchantment.equals(Enchantments.QUICK_CHARGE) && super.checkCompatibility(enchantment);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public static void doKickback(Player player, ItemStack itemStack) {
        int kickback = EnchantmentHelper.getTagEnchantmentLevel(OPEnchantments.KICKBACK.get(), itemStack);
        Vec3 lookVec = player.getLookAngle().multiply(-1,-1,-1);
        player.setDeltaMovement(new Vec3(0.5 + (kickback * 0.5), player.getDeltaMovement().y + (0.25 + kickback * 0.25), 0.5 + (kickback * 0.5)).multiply(lookVec));
    }
}
