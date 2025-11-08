package com.unusualmodding.opposing_force.enchantments;

import com.unusualmodding.opposing_force.registry.OPEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.phys.Vec3;

public class DoubleJumpEnchantment extends Enchantment {

    private static int jumps = 0;

    public DoubleJumpEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    public static void doubleJump(Player player) {
        player.fallDistance = 0;
        double upwardsMotion = 0.5;
        if (player.hasEffect(MobEffects.JUMP)) {
            upwardsMotion += 0.1 * (player.getEffect(MobEffects.JUMP).getAmplifier() + 1);
        }
        if (player.isSprinting()) {
            upwardsMotion *= 1 + 0.1;
        }

        Vec3 motion = player.getDeltaMovement();
        double motionMultiplier = player.isSprinting() ? 0.1 : 0;
        float direction = (float) (player.getYRot() * Math.PI / 180);
        player.setDeltaMovement(player.getDeltaMovement().add(-Mth.sin(direction) * motionMultiplier, upwardsMotion - motion.y, Mth.cos(direction) * motionMultiplier));

        player.hasImpulse = true;
        player.awardStat(Stats.JUMP);
        if (player.isSprinting()) {
            player.causeFoodExhaustion(0.2F);
        } else {
            player.causeFoodExhaustion(0.05F);
        }
        player.playSound(SoundEvents.WOOL_FALL, 1, 0.9F + player.getRandom().nextFloat() * 0.2F);
    }

    public static void performJump(Player player) {
        if (!player.onGround()) {
            boolean hasDoubleJump = false;
            int level;

            ItemStack stack = player.getItemBySlot(EquipmentSlot.FEET);
            if (stack.is(Items.AIR)) {
                return;
            }
            ListTag nbtList = stack.getEnchantmentTags();
            level = stack.getEnchantmentLevel(OPEnchantments.DOUBLE_JUMP.get());

            for (int i = 0; i < nbtList.size(); i++) {
                CompoundTag idTag = nbtList.getCompound(i);
                if (idTag.getString("id").equals(OPEnchantments.DOUBLE_JUMP.getId().toString())) {
                    hasDoubleJump = true;
                    level = idTag.getInt("lvl");
                }
            }
            if (hasDoubleJump && jumps < level) {
                jumps++;
                doubleJump(player);
            }
        }
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }
}
