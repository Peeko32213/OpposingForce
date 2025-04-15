package com.peeko32213.hole.common.item;


import com.peeko32213.hole.common.entity.projectile.EntitySmallElectricBall;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ElectricChargeItem extends Item {
    private final Supplier<? extends EntityType<?>> entity;
    private SoundEvent soundEvent;

    public ElectricChargeItem(Supplier<? extends EntityType<?>> dartEntityType, Properties properties) {
        super(properties);
        this.entity = dartEntityType;
    }

    @Nullable
    public EntitySmallElectricBall createDart(Level level, LivingEntity shooter) {
        EntitySmallElectricBall dart = this.createDart(level);
        if (dart != null) {
            dart.setPos(shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
            dart.setOwner(shooter);
            return dart;
        } else {
            return null;
        }
    }


    @Nullable
    public EntitySmallElectricBall createDart(Level level) {
        Entity entity = this.getEntity().get().create(level);
        if (entity instanceof EntitySmallElectricBall dart) {
            return dart;
        } else {
            return null;
        }
    }

    public Supplier<? extends EntityType<?>> getEntity() {
        return this.entity;
    }
}