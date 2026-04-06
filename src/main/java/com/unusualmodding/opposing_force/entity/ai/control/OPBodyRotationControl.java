package com.unusualmodding.opposing_force.entity.ai.control;

import com.unusualmodding.opposing_force.entity.base.OPMonster;

public class OPBodyRotationControl extends SmoothBodyRotationControl {

    protected final OPMonster monster;

    public OPBodyRotationControl(OPMonster mob) {
        this(mob, 0.5F, 30.0F, 0.25F, 20.0F, 0.8F, 180.0F);
    }

    public OPBodyRotationControl(OPMonster monster, float bodyLagMoving, float bodyMaxMoving, float bodyLagStill, float bodyMaxStill, float headLag, float headMax) {
        super(monster, bodyLagMoving, bodyMaxMoving, bodyLagStill, bodyMaxStill, headLag, headMax);
        this.monster = monster;
    }

    @Override
    public void clientTick() {
        if (!monster.refuseToLook()) super.clientTick();
    }
}
