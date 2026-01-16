package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.entity.projectile.KineticBomb;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class KineticBombItem extends Item {

    public KineticBombItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, 10);
        if (!level.isClientSide()) {
            KineticBomb kineticBomb = new KineticBomb(level, player);
            kineticBomb.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.9F, 0.0F);
            kineticBomb.setPos(player.getEyePosition().add(kineticBomb.getDeltaMovement().normalize()));
            level.addFreshEntity(kineticBomb);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}