package com.peeko32213.hole.common.item;

import com.peeko32213.hole.common.entity.projectile.EntityAbstractElectricBall;
import com.peeko32213.hole.common.entity.projectile.EntitySmallElectricBall;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ElectricChargeItem extends Item {
    private final Supplier<? extends EntityType<?>> dartEntityType;
    private SoundEvent soundEvent;

    public ElectricChargeItem(Supplier<? extends EntityType<?>> dartEntityType, Properties properties) {
        super(properties);
        this.dartEntityType = dartEntityType;
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
        Entity entity = this.getDartEntityType().get().create(level);
        if (entity instanceof EntitySmallElectricBall dart) {
            return dart;
        } else {
            return null;
        }
    }

    /**
     * @return A {@link Supplier Supplier&lt;? extends EntityType&lt;?&gt;&gt;} that gives the entity type correlating with the projectile that can be created by this dart.
     */
    public Supplier<? extends EntityType<?>> getDartEntityType() {
        return this.dartEntityType;
    }



}
