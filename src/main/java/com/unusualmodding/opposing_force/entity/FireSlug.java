package com.unusualmodding.opposing_force.entity;

import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class FireSlug extends Slug {

    public FireSlug(EntityType<? extends Slug> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (super.doHurtTarget(entity)) {
            entity.setSecondsOnFire(3);
            this.playSound(OPSoundEvents.SLUG_ATTACK.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            return true;
        } else {
            return false;
        }
    }
}
