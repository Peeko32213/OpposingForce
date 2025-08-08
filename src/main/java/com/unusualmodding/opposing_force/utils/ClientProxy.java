package com.unusualmodding.opposing_force.utils;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.renderer.items.OPArmorRenderProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    public void init() {
    }

    public void clientInit() {
    }

    @Override
    public Object getArmorRenderProperties() {
        return new OPArmorRenderProperties();
    }
}
