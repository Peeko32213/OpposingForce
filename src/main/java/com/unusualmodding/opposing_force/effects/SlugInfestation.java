package com.unusualmodding.opposing_force.effects;

import com.unusualmodding.opposing_force.entity.Slug;
import com.unusualmodding.opposing_force.registry.OPEntities;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class SlugInfestation extends MobEffect {

    public SlugInfestation() {
        super(MobEffectCategory.HARMFUL, 0x6e5d34);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68020638", -0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }

    public static void onMobHurt(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getRandom().nextFloat() <= 0.6F) {
            int i = Mth.randomBetweenInclusive(livingEntity.getRandom(), 1, amplifier + 2);

            for (int j = 0; j < i; j++) {
                spawnSlugs(livingEntity.level(), livingEntity, livingEntity.getX(), livingEntity.getY() + (double) livingEntity.getBbHeight() / 2.0, livingEntity.getZ());
            }
        }
    }

    private static void spawnSlugs(Level level, LivingEntity entity, double x, double y, double z) {
        Slug slug = OPEntities.SLUG.get().create(level);
        if (slug != null) {
            RandomSource randomsource = entity.getRandom();
            float f1 = Mth.randomBetween(randomsource, (float) (-Math.PI / 2), (float) (Math.PI / 2));
            Vector3f vector3f = entity.getLookAngle().toVector3f().mul(0.3F).mul(1.0F, 1.5F, 1.0F).rotateY(f1);
            slug.moveTo(x, y, z, level.getRandom().nextFloat() * 360.0F, 0.0F);
            slug.setDeltaMovement(new Vec3(vector3f));
            level.addFreshEntity(slug);
            slug.playSound(OPSoundEvents.SLUG_ATTACK.get());
        }
    }
}
