package com.unusualmodding.opposing_force.items.mob_heads;

import com.unusualmodding.opposing_force.items.MobHeadItem;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class DicerHeadItem extends MobHeadItem {

    public DicerHeadItem(Block skull, Block wallSkull, Item.Properties properties, Direction direction) {
        super(skull, wallSkull, properties, direction);
    }

    @Override
    public SoundEvent getSound() {
        return OPSoundEvents.NOTE_BLOCK_IMITATE_DICER.get();
    }
}
