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
    ATTACK_START,
    ATTACKING,
    ATTACK_END,
    OPEN_JAWS,
    CLOSE_JAWS,
    SLASHING,
    CROSS_SLASHING,
    LASERING,
    LAUNCHED,
    LANDING,
    SHOOTING;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}
