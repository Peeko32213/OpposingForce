package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.entity.projectile.Tomahawk;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.Random;

public class TomahawkItem extends Item {

    private final Random random = new Random();

    public TomahawkItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int pTimeLeft) {
        if (entity instanceof Player player) {
            int i = this.getUseDuration(itemStack) - pTimeLeft;
            if (i >= 7) {
                if (!level.isClientSide) {
                    Tomahawk tomahawk = new Tomahawk(level, player);
                    tomahawk.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.3F, 0.0F);
                    if (player.getAbilities().instabuild) {
                        tomahawk.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }
                    level.addFreshEntity(tomahawk);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }
                }
                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pHand));
    }
}
