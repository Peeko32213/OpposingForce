package com.unusualmodding.opposing_force.entity.ai.control;

import com.unusualmodding.opposing_force.entity.base.OPMonster;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class OPBodyRotationControl extends BodyRotationControl {

    protected final OPMonster monster;

    public OPBodyRotationControl(OPMonster monster) {
        super(monster);
        this.monster = monster;
    }

    @Override
    public void clientTick() {
        if (!monster.refuseToLook()) super.clientTick();
    }
}
