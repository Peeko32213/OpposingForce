package com.unusualmodding.opposing_force.utils;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.renderer.items.OPArmorRenderProperties;
import com.unusualmodding.opposing_force.events.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    public void init() {
    }

    public void clientInit() {
        MinecraftForge.EVENT_BUS.register(new MiscClientEvents());
    }

    @Override
    public Object getArmorRenderProperties() {
        return new OPArmorRenderProperties();
    }

    @Override
    public void screenShake(ScreenShakeEvent event) {
        MiscClientEvents.SCREEN_SHAKE_EVENTS.add(event);
    }
}
