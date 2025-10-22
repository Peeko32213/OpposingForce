package com.unusualmodding.opposing_force.entity.utils;

import net.minecraft.world.entity.Pose;

public enum OPPoses {

    SPINNING_WEB,
    GROWING_LEGS,
    RETRACTING_LEGS,
    START_SAWING,
    SAWING;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}
