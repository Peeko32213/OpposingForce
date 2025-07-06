package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.client.renderer.items.OPItemRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.List;
import java.util.function.Consumer;

public class MobHeadItem extends StandingAndWallBlockItem {

    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        protected ItemStack execute(BlockSource blockSource, ItemStack stack) {
            return MobHeadItem.dispenseHead(blockSource, stack) ? stack : super.execute(blockSource, stack);
        }
    };

    public static boolean dispenseHead(BlockSource blockSource, ItemStack stack) {
        BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
        List<LivingEntity> list = blockSource.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(blockpos), EntitySelector.NO_SPECTATORS.and(new EntitySelector.MobCanWearArmorEntitySelector(stack)));
        if (list.isEmpty()) return false;
        else {
            LivingEntity entity = list.get(0);
            EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(stack);
            ItemStack itemstack = stack.split(1);
            entity.setItemSlot(equipmentslot, itemstack);
            if (entity instanceof Mob) {
                ((Mob) entity).setDropChance(equipmentslot, 2.0F);
                ((Mob) entity).setPersistenceRequired();
            }
            return true;
        }
    }

    public MobHeadItem(Block block, Block wallBlock, Properties properties, Direction direction) {
        super(block, wallBlock, properties, direction);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public OPItemRenderers getCustomRenderer() {
                return OPItemRenderers.getInstance();
            }
        });
    }

    public SoundEvent getSound() {
        return null;
    }
}
