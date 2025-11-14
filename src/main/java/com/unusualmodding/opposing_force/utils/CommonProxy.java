package com.unusualmodding.opposing_force.utils;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.OpposingForceConfig;
import com.unusualmodding.opposing_force.events.ScreenShakeEvent;
import com.unusualmodding.opposing_force.world.SkyvernSpawner;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {

    private static final Map<ServerLevel, SkyvernSpawner> SKYVERN_SPAWNER_MAP = new HashMap<>();

    public void init() {
    }

    public void clientInit() {
    }

    public Object getArmorRenderProperties() {
        return null;
    }

    public void screenShake(ScreenShakeEvent event) {
    }

    public void playWorldSound(@Nullable Object soundEmitter, byte type) {
    }

    public void clearSoundCacheFor(Entity entity) {
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.LevelTickEvent tick) {
        if (!tick.level.isClientSide && tick.level instanceof ServerLevel serverLevel && OpposingForceConfig.SKYVERN_SPAWNING.get() && tick.level.getDifficulty() != Difficulty.PEACEFUL) {
            if (SKYVERN_SPAWNER_MAP.get(serverLevel) == null) {
                SKYVERN_SPAWNER_MAP.put(serverLevel, new SkyvernSpawner(serverLevel));
            }
            SkyvernSpawner spawner = SKYVERN_SPAWNER_MAP.get(serverLevel);
            spawner.tick();
        }
    }
}
