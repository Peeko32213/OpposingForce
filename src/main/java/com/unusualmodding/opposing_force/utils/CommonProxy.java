package com.unusualmodding.opposing_force.utils;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {
    public void init() {
    }

    public void clientInit() {
    }

    public Object getArmorRenderProperties() {
        return null;
    }
}
