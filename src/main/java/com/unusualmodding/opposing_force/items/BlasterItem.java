package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.entity.projectile.LaserBolt;
import com.unusualmodding.opposing_force.registry.OPEnchantments;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BlasterItem extends Item {

    public BlasterItem(Properties properties) {
        super(properties);
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), OPSoundEvents.BLASTER_SHOOT.get(), SoundSource.PLAYERS, 1.0F, (level.getRandom().nextFloat() * 0.5F + (itemstack.getEnchantmentLevel(OPEnchantments.RAPID_FIRE.get()) > 0 ? 1F : 0.8F)));
        player.getCooldowns().addCooldown(this, 15 - (itemstack.getEnchantmentLevel(OPEnchantments.RAPID_FIRE.get()) * 3));

        if (!level.isClientSide()) {
            Vec3 vector3d = player.getViewVector(1.0F);
            Vec3 vec3 = vector3d.normalize();
            float yRot = (float) (Mth.atan2(vec3.z, vec3.x) * (180F / Math.PI)) + 90F;
            float xRot = (float) -(Mth.atan2(vec3.y, Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)) * (180F / Math.PI));

            LaserBolt laserBolt = new LaserBolt(player, level, player.position().x(), player.getEyePosition().y(), player.position().z());
            Vec3 relativePos = new Vec3(0.0, 0, 0.75F).xRot(-player.getXRot() * ((float) Math.PI / 180F)).yRot(-player.getYHeadRot() * ((float) Math.PI / 180F));
            Vec3 laserPos = player.position().add(0, player.getBbHeight() * 0.8F, 0).add(relativePos);
            laserBolt.setPos(laserPos);


            laserBolt.shootFromRotation(laserBolt, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0.5F);

            laserBolt.setYRot(yRot);
            laserBolt.setXRot(xRot);
            laserBolt.setOwner(player);

            if (itemstack.getEnchantmentLevel(OPEnchantments.SPLITTING.get()) > 0) {
                laserBolt.setDisruptor(true);
                laserBolt.setDisruptorLevel(itemstack.getEnchantmentLevel(OPEnchantments.SPLITTING.get()));
            }
            if (itemstack.getEnchantmentLevel(OPEnchantments.RAPID_FIRE.get()) > 0) {
                laserBolt.setRapidFire(true);
            }

            level.addFreshEntity(laserBolt);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
        }
        return InteractionResultHolder.sidedSuccess(itemstack, false);
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.is(OPItems.BLASTER.get()) || !newStack.is(OPItems.BLASTER.get());
    }
}
