package com.unusualmodding.opposing_force.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.unusualmodding.opposing_force.entity.FireSlime;
import com.unusualmodding.opposing_force.registry.OPEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class InfernoStaffItem extends Item implements Vanishable {

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public InfernoStaffItem(Properties properties) {
        super(properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", 3.0D, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", -3.0F, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(equipmentSlot);
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (super.hurtEnemy(stack, target, attacker)) {
            target.setSecondsOnFire(3);
            return true;
        }
        return false;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 0.5F, 1.25F / (level.random.nextFloat() * 0.4F + 0.8F));
        player.getCooldowns().addCooldown(this, 50);
        if (!level.isClientSide()) {
            summonFireSlime(player);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    private void summonFireSlime(Player summoner) {
        FireSlime fireSlime = OPEntities.FIRE_SLIME.get().create(summoner.level());
        if (fireSlime != null) {
            float yRot = summoner.getViewYRot(0.0F);
            fireSlime.shootFromRotation(summoner, summoner.getXRot(), summoner.getYRot(), 0.0F, 1.5F, 0.75F);
            fireSlime.setPos(summoner.getEyePosition().add(fireSlime.getDeltaMovement().normalize()));
            fireSlime.yBodyRot = yRot;
            fireSlime.setYHeadRot(yRot);
            fireSlime.tame(summoner);
            fireSlime.setFromSummon(true);
            fireSlime.finalizeSpawn((ServerLevel) summoner.level(), summoner.level().getCurrentDifficultyAt(summoner.blockPosition()), MobSpawnType.TRIGGERED, null, null);
            summoner.level().addFreshEntity(fireSlime);
            fireSlime.copyTarget(summoner);
        }
    }
}
