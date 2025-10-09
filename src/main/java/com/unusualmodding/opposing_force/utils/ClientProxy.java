package com.unusualmodding.opposing_force.utils;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.client.renderer.items.OPArmorRenderProperties;
import com.unusualmodding.opposing_force.client.sounds.DicerLaserSound;
import com.unusualmodding.opposing_force.client.sounds.ElectricChargeSound;
import com.unusualmodding.opposing_force.entity.projectile.DicerLaser;
import com.unusualmodding.opposing_force.entity.projectile.ElectricCharge;
import com.unusualmodding.opposing_force.events.*;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, value = Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    public static final Int2ObjectMap<AbstractTickableSoundInstance> ENTITY_SOUND_INSTANCE_MAP = new Int2ObjectOpenHashMap<>();

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

    @Override
    public void playWorldSound(@Nullable Object soundEmitter, byte type) {
        if (soundEmitter instanceof Entity entity && !entity.level().isClientSide) {
            return;
        }
        switch (type) {
            case 0:
                if (soundEmitter instanceof DicerLaser entity) {
                    DicerLaserSound sound;
                    AbstractTickableSoundInstance oldSound = ENTITY_SOUND_INSTANCE_MAP.get(entity.getId());
                    if (oldSound == null || !(oldSound instanceof DicerLaserSound sound1 && sound1.isSameEntity(entity))) {
                        sound = new DicerLaserSound(entity);
                        ENTITY_SOUND_INSTANCE_MAP.put(entity.getId(), sound);
                    } else {
                        sound = (DicerLaserSound) oldSound;
                    }
                    if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                    }
                }
                break;
            case 1:
                if (soundEmitter instanceof ElectricCharge entity) {
                    ElectricChargeSound sound;
                    AbstractTickableSoundInstance oldSound = ENTITY_SOUND_INSTANCE_MAP.get(entity.getId());
                    if (oldSound == null || !(oldSound instanceof ElectricChargeSound sound1 && sound1.isSameEntity(entity))) {
                        sound = new ElectricChargeSound(entity);
                        ENTITY_SOUND_INSTANCE_MAP.put(entity.getId(), sound);
                    } else {
                        sound = (ElectricChargeSound) oldSound;
                    }
                    if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                    }
                }
                break;
        }
    }

    private boolean isSoundPlaying(AbstractTickableSoundInstance sound) {
        return Minecraft.getInstance().getSoundManager().soundEngine.queuedTickableSounds.contains(sound) || Minecraft.getInstance().getSoundManager().soundEngine.tickingSounds.contains(sound);
    }

    @Override
    public void clearSoundCacheFor(Entity entity) {
        ENTITY_SOUND_INSTANCE_MAP.remove(entity.getId());
    }
}
