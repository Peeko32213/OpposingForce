package com.barl_inc.opposing_force.items;

import com.barl_inc.opposing_force.entity.projectile.Tomahawk;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TomahawkItem extends ThrowableWeaponItem {

    public TomahawkItem(Properties properties) {
        super(Tiers.IRON, 1, -2.4F, properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, 10);
        if (!level.isClientSide()) {
            Tomahawk tomahawk = new Tomahawk(level, player);
            tomahawk.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.25F, 0.75F);
            level.addFreshEntity(tomahawk);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.hurtAndBreak(2, player, (player1) -> player1.broadcastBreakEvent(hand));
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
