package com.unusualmodding.opposing_force.utils;

import net.minecraftforge.fml.ModList;

public class OPLoadedMods {

    private static boolean entityCullingLoaded;
    private static boolean oculusLoaded;

    public static void afterAllModsLoaded() {
        entityCullingLoaded = ModList.get().isLoaded("entityculling");
        oculusLoaded = ModList.get().isLoaded("oculus");
    }

    public static boolean isEntityCullingLoaded() {
        return entityCullingLoaded;
    }

    public static boolean isOculusLoaded() {
        return oculusLoaded;
    }
}
