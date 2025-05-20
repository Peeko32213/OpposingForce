package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.entity.projectile.ElectricBall;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Supplier;

public class ElectricChargeItem extends Item {

    private final Supplier<? extends EntityType<?>> entity;
    private SoundEvent soundEvent;
    private final Random random = new Random();

    public ElectricChargeItem(Supplier<? extends EntityType<?>> chargeEntity, Properties properties) {
        super(properties);
        this.entity = chargeEntity;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player user, InteractionHand hand) {
        ItemStack itemstack = user.getItemInHand(hand);
        user.gameEvent(GameEvent.ITEM_INTERACT_START);
        level.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        user.getCooldowns().addCooldown(this, 12);

        if (!level.isClientSide) {
            ElectricBall electricBall = new ElectricBall(user, level, user.position().x(), user.getEyePosition().y(), user.position().z());
            electricBall.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.0F, 2.0F);
            level.addFreshEntity(electricBall);
        }
        user.awardStat(Stats.ITEM_USED.get(this));
        if (!user.getAbilities().instabuild) {
            itemstack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    // Tesla bow stuff
    @Nullable
    public ElectricBall shootCharge(Level level, LivingEntity shooter) {
        ElectricBall charge = this.createCharge(level);
        if (charge != null) {
            charge.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
            charge.setOwner(shooter);
            return charge;
        } else {
            return null;
        }
    }

    @Nullable
    public ElectricBall createCharge(Level level) {
        Entity entity = this.getEntity().get().create(level);
        if (entity instanceof ElectricBall charge) {
            return charge;
        } else {
            return null;
        }
    }

    public Supplier<? extends EntityType<?>> getEntity() {
        return this.entity;
    }
}