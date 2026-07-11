package com.barl_inc.opposing_force.utils;

import com.barl_inc.opposing_force.network.LightningSyncPacket;
import com.barl_inc.opposing_force.registry.OPNetwork;

public class ParticleUtils {

    public static void spawnLightningParticles(double posX, double posY, double posZ, int lightningLength, float red, float green, float blue) {
        LightningSyncPacket packet = LightningSyncPacket.builder()
                .pos(posX, posY, posZ)
                .range(lightningLength)
                .size(0.08F)
                .color(red, green, blue, 1F)
                .build();
        OPNetwork.sendPacketToClients(packet);
    }
}
