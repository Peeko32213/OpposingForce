package com.unusualmodding.opposing_force.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class MobItem extends BucketItem {

    private final Supplier<? extends EntityType<?>> entityType;
    private final SoundEvent sound;

    public MobItem(Supplier<EntityType<?>> entityType, SoundEvent sound, Properties properties) {
        super(Fluids.EMPTY, properties);
        this.entityType = entityType;
        this.sound = sound;
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();

        if (!level.isClientSide) {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockPos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = level.getBlockState(blockPos);

            BlockPos blockPos1;
            if (blockstate.getCollisionShape(level, blockPos).isEmpty()) {
                blockPos1 = blockPos;
            } else {
                blockPos1 = blockPos.relative(direction);
            }

            if (entityType != null) {
                if (player != null && !player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                this.spawn((ServerLevel) level, itemstack, blockPos1);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), this.sound, SoundSource.NEUTRAL, 0.5F, 1.0F);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, world, tooltip, flag);
    }

    private void spawn(ServerLevel level, ItemStack itemStack, BlockPos blockPos) {
        Entity entity = getEntityType().spawn(level, itemStack, null, blockPos, MobSpawnType.BUCKET, true, false);
        if (entity instanceof Bucketable bucketable) {
            bucketable.loadFromBucketTag(itemStack.getOrCreateTag());
            bucketable.setFromBucket(true);
        }
    }

    protected EntityType<?> getEntityType() {
        return entityType.get();
    }
}