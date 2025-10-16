package com.unusualmodding.opposing_force.entity.utils;

import net.minecraft.world.entity.Pose;

public enum OPPoses {

    SPINNING_WEB;

    public Pose get() {
        return Pose.valueOf(this.name());
    }
}
