package com.unusualmodding.opposing_force.entity.utils;

import net.minecraft.world.entity.Pose;

public enum OPPoses {

    GROWING_LEGS,
    RETRACTING_LEGS,
    START_SAWING,
    SAWING,
    SPIN_SAW,
    START_FLAILING,
    FLAILING,
    STOP_FLAILING,
    RECOVERING,
    JAB,
    JAB_RUSH,
    START_ROLLING,
    ROLLING,
    STOP_ROLLING,
    ATTACKING,
    OPEN_JAWS,
    CLOSE_JAWS,
    SLASHING,
    CROSS_SLASHING,
    TAIL_SPINNING,
    LASERING,
    LAUNCHED;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}
