package com.unusualmodding.opposing_force.items;

import com.unusualmodding.opposing_force.registry.enums.OPItemTiers;
import net.minecraft.world.item.SwordItem;

public class BladeOfTheMountainItem extends SwordItem {

    public BladeOfTheMountainItem(Properties properties) {
        super(OPItemTiers.MOUNTAIN_BLADE, 7, -3.4F, properties);
    }
}
