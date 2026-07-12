package com.barl_inc.opposing_force.utils;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.OpposingForceConfig;
import com.barl_inc.opposing_force.events.ScreenShakeEvent;
import com.barl_inc.opposing_force.world.SkyvernSpawner;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = OpposingForce.MOD_ID)
public class CommonProxy {

    private static final Map<ServerLevel, SkyvernSpawner> SKYVERN_SPAWNER_MAP = new HashMap<>();

    public void init() {
    }

    public void clientInit() {
    }

    public boolean isKeyDown(int keyType) {
        return false;
    }

    public Player getClientSidePlayer() {
        return null;
    }

    public boolean isFirstPersonPlayer(Entity entity) {
        return false;
    }

    public void blockRenderingEntity(UUID id) {
    }

    public void releaseRenderingEntity(UUID id) {
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
    public void onServerTick(LevelTickEvent.Post tick) {
        if (!tick.getLevel().isClientSide && tick.getLevel() instanceof ServerLevel serverLevel && OpposingForceConfig.SKYVERN_SPAWNING.get() && tick.getLevel().getDifficulty() != Difficulty.PEACEFUL) {
            if (SKYVERN_SPAWNER_MAP.get(serverLevel) == null) {
                SKYVERN_SPAWNER_MAP.put(serverLevel, new SkyvernSpawner(serverLevel));
            }
            SkyvernSpawner spawner = SKYVERN_SPAWNER_MAP.get(serverLevel);
            spawner.tick();
        }
    }
}
