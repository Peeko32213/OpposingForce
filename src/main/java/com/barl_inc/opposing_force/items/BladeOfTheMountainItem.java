package com.barl_inc.opposing_force.items;

import com.barl_inc.opposing_force.registry.enums.OPTiers.OPItemTiers;
import net.minecraft.world.item.SwordItem;

public class BladeOfTheMountainItem extends SwordItem {

    public BladeOfTheMountainItem(Properties properties) {
        super(OPItemTiers.MOUNTAIN, 7, -3.4F, properties);
    }
}
