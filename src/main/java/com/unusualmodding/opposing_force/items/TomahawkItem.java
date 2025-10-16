package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.entity.projectile.Tomahawk;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class TomahawkItem extends ThrowableWeaponItem {

    public TomahawkItem(Tier tier, int damage, float attackSpeed, Properties properties) {
        super(tier, damage, attackSpeed, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, 20);
        if (!level.isClientSide()) {
            Tomahawk tomahawk = new Tomahawk(level, player);
            tomahawk.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.25F, 0.75F);
            level.addFreshEntity(tomahawk);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.hurtAndBreak(3, player, (player1) -> player1.broadcastBreakEvent(hand));
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
