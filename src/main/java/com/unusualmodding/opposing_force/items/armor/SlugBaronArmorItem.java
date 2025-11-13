package com.unusualmodding.opposing_force.items.armor;

import com.unusualmodding.opposing_force.entity.Slug;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPItems;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;

public class SlugBaronArmorItem extends ConfigurableArmorItem {

    public SlugBaronArmorItem(Type type, Properties properties) {
        super(type, properties, OPArmorDefinitions.SLUG_BARON);
    }

    public static void onHurt(LivingEntity livingEntity) {
        if (livingEntity.getRandom().nextFloat() <= 0.1F) {
            int i = Mth.randomBetweenInclusive(livingEntity.getRandom(), 1, 2);
            for (int j = 0; j < i; j++) {
                spawnSlugs(livingEntity, livingEntity.getX(), livingEntity.getY() + (double) livingEntity.getBbHeight() / 2.0, livingEntity.getZ());
            }
        }
    }

    private static void spawnSlugs(LivingEntity summoner, double x, double y, double z) {
        Slug slug = OPEntities.SLUG.get().create(summoner.level());
        if (slug != null) {
            RandomSource random = summoner.getRandom();
            float yRot = Mth.randomBetween(random, (float) (-Math.PI / 2), (float) (Math.PI / 2));
            Vector3f vector3f = summoner.getLookAngle().toVector3f().mul(0.3F).mul(1.0F, 1.5F, 1.0F).rotateY(yRot);
            slug.moveTo(x, y, z, summoner.level().getRandom().nextFloat() * 360.0F, 0.0F);
            slug.setDeltaMovement(new Vec3(vector3f));
            slug.setFromSummon(true);
            if (summoner instanceof Player player) {
                slug.tame(player);
            }
            slug.finalizeSpawn((ServerLevel) summoner.level(), summoner.level().getCurrentDifficultyAt(summoner.blockPosition()), MobSpawnType.TRIGGERED, null, null);
            summoner.level().addFreshEntity(slug);
            slug.copyTarget(summoner);
            slug.playSound(OPSoundEvents.SLUG_ATTACK.get());
        }
    }

    public static boolean wearingFullSlugBaronSet(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(OPItems.SLUG_BARON_HELMET.get())
                && entity.getItemBySlot(EquipmentSlot.CHEST).is(OPItems.SLUG_BARON_CHESTPLATE.get())
                && entity.getItemBySlot(EquipmentSlot.LEGS).is(OPItems.SLUG_BARON_LEGGINGS.get())
                && entity.getItemBySlot(EquipmentSlot.FEET).is(OPItems.SLUG_BARON_BOOTS.get());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> component, @NotNull TooltipFlag flag) {
        component.add(this.getDisplayName().withStyle(ChatFormatting.BLUE));
    }

    public MutableComponent getDisplayName() {
        return Component.translatable("item.opposing_force.slug_baron_set_bonus.desc");
    }
}
