package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.entity.projectile.TremblerShell;
import com.unusualmodding.opposing_force.registry.OPEntities;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TremblerShellItem extends Item {

    public TremblerShellItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            player.swing(hand);

            TremblerShell tremblerShell = OPEntities.TREMBLER_SHELL.get().create(player.level());
            tremblerShell.setPos(player.position().add(0, player.getBbHeight() * 0.35F, 0));

            tremblerShell.setYRot(180 + player.getYHeadRot() * 15);
            tremblerShell.setSpinSpeed(16F);

            tremblerShell.setSpinRadius(3.5F);
            tremblerShell.setOwner(player);
            tremblerShell.setStartAngle(360 / (float) 3);
            level.addFreshEntity(tremblerShell);

            level.playSound(null, player.blockPosition(), SoundEvents.SHULKER_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
            player.getCooldowns().addCooldown(this, 60);
        }

        itemStack.hurtAndBreak(1, player, (player1) -> {
            player1.broadcastBreakEvent(player1.getUsedItemHand());
        });
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
