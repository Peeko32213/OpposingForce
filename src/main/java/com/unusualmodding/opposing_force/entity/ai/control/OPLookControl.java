package com.unusualmodding.opposing_force.entity.ai.control;

import com.unusualmodding.opposing_force.entity.base.OPMonster;
import net.minecraft.world.entity.ai.control.LookControl;

public class OPLookControl extends LookControl {

    protected final OPMonster monster;

    public OPLookControl(OPMonster monster) {
        super(monster);
        this.monster = monster;
    }

    @Override
    public void tick() {
        if (!monster.refuseToLook()) {
            super.tick();
        }
    }
}
