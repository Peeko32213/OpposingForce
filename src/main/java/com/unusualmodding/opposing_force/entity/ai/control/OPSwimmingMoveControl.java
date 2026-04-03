package com.unusualmodding.opposing_force.entity.ai.control;

import com.unusualmodding.opposing_force.entity.base.OPMonster;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;

public class OPSwimmingMoveControl extends SmoothSwimmingMoveControl {

    protected final OPMonster monster;

    public OPSwimmingMoveControl(OPMonster monster, int maxTurnX, int maxTurnY, float speedModifier) {
        super(monster, maxTurnX, maxTurnY, speedModifier, 1.0F, false);
        this.monster = monster;
    }

    @Override
    public void tick() {
        if (!monster.refuseToMove()) super.tick();
    }
}
