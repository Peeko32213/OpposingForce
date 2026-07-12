package com.barl_inc.opposing_force.utils;

import com.barl_inc.opposing_force.OpposingForce;
import com.barl_inc.opposing_force.client.renderer.items.OPArmorRenderProperties;
import com.barl_inc.opposing_force.client.sounds.*;
import com.unusualmodding.opposing_force.client.sounds.*;
import com.barl_inc.opposing_force.entity.Frowzy;
import com.barl_inc.opposing_force.entity.Skyvern;
import com.barl_inc.opposing_force.entity.Terror;
import com.barl_inc.opposing_force.entity.Whizz;
import com.barl_inc.opposing_force.entity.projectile.DicerLaser;
import com.barl_inc.opposing_force.entity.projectile.ElectricCharge;
import com.barl_inc.opposing_force.entity.projectile.ThrownLaserBlade;
import com.barl_inc.opposing_force.events.ClientForgeEvents;
import com.barl_inc.opposing_force.events.ScreenShakeEvent;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EventBusSubscriber(modid = OpposingForce.MOD_ID)
public class ClientProxy extends CommonProxy {

    public static final Int2ObjectMap<AbstractTickableSoundInstance> ENTITY_SOUND_INSTANCE_MAP = new Int2ObjectOpenHashMap<>();

    public static List<UUID> blockedEntityRenders = new ArrayList<>();

    public void init() {
    }

    public void clientInit() {
        NeoForge.EVENT_BUS.register(new ClientForgeEvents());
    }

    @Override
    public boolean isKeyDown(int keyType) {
        if (keyType == 0) {
            return Minecraft.getInstance().options.keyJump.isDown();
        }
        if (keyType == 1) {
            return Minecraft.getInstance().options.keySprint.isDown();
        }
        if (keyType == 3) {
            return Minecraft.getInstance().options.keyAttack.isDown();
        }
        if (keyType == 4) {
            return Minecraft.getInstance().options.keyShift.isDown();
        }
        return false;
    }

    @Override
    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public boolean isFirstPersonPlayer(Entity entity) {
        return entity.equals(Minecraft.getInstance().cameraEntity) && Minecraft.getInstance().options.getCameraType().isFirstPerson();
    }

    @Override
    public void blockRenderingEntity(UUID id) {
        blockedEntityRenders.add(id);
    }

    @Override
    public void releaseRenderingEntity(UUID id) {
        blockedEntityRenders.remove(id);
    }

    @Override
    public Object getArmorRenderProperties() {
        return new OPArmorRenderProperties();
    }

    @Override
    public void screenShake(ScreenShakeEvent event) {
        ClientForgeEvents.SCREEN_SHAKE_EVENTS.add(event);
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
            case 2:
                if (soundEmitter instanceof Whizz entity) {
                    WhizzFlightSound sound;
                    AbstractTickableSoundInstance oldSound = ENTITY_SOUND_INSTANCE_MAP.get(entity.getId());
                    if (oldSound == null || !(oldSound instanceof WhizzFlightSound sound1 && sound1.isSameEntity(entity))) {
                        sound = new WhizzFlightSound(entity);
                        ENTITY_SOUND_INSTANCE_MAP.put(entity.getId(), sound);
                    } else {
                        sound = (WhizzFlightSound) oldSound;
                    }
                    if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                    }
                }
                break;
            case 3:
                if (soundEmitter instanceof Frowzy entity) {
                    FrowzyPantingSound sound;
                    AbstractTickableSoundInstance oldSound = ENTITY_SOUND_INSTANCE_MAP.get(entity.getId());
                    if (oldSound == null || !(oldSound instanceof FrowzyPantingSound sound1 && sound1.isSameEntity(entity))) {
                        sound = new FrowzyPantingSound(entity);
                        ENTITY_SOUND_INSTANCE_MAP.put(entity.getId(), sound);
                    } else {
                        sound = (FrowzyPantingSound) oldSound;
                    }
                    if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                    }
                }
                break;
            case 4:
                if (soundEmitter instanceof ThrownLaserBlade entity) {
                    LaserBladeSound sound;
                    AbstractTickableSoundInstance oldSound = ENTITY_SOUND_INSTANCE_MAP.get(entity.getId());
                    if (oldSound == null || !(oldSound instanceof LaserBladeSound sound1 && sound1.isSameEntity(entity))) {
                        sound = new LaserBladeSound(entity);
                        ENTITY_SOUND_INSTANCE_MAP.put(entity.getId(), sound);
                    } else {
                        sound = (LaserBladeSound) oldSound;
                    }
                    if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                    }
                }
                break;
            case 5:
                if (soundEmitter instanceof Terror entity) {
                    TerrorSawSound sound;
                    AbstractTickableSoundInstance oldSound = ENTITY_SOUND_INSTANCE_MAP.get(entity.getId());
                    if (oldSound == null || !(oldSound instanceof TerrorSawSound sound1 && sound1.isSameEntity(entity))) {
                        sound = new TerrorSawSound(entity);
                        ENTITY_SOUND_INSTANCE_MAP.put(entity.getId(), sound);
                    } else {
                        sound = (TerrorSawSound) oldSound;
                    }
                    if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                    }
                }
                break;
            case 6:
                if (soundEmitter instanceof LivingEntity livingEntity) {
                    SawbladeSound sound;
                    AbstractTickableSoundInstance old = ENTITY_SOUND_INSTANCE_MAP.get(livingEntity.getId());
                    if (old == null || !(old instanceof SawbladeSound gauntletSound && gauntletSound.isSameEntity(livingEntity))) {
                        sound = new SawbladeSound(livingEntity);
                        ENTITY_SOUND_INSTANCE_MAP.put(livingEntity.getId(), sound);
                    } else {
                        sound = (SawbladeSound) old;
                    }
                    if (!isSoundPlaying(sound) && sound.canPlaySound()) {
                        Minecraft.getInstance().getSoundManager().queueTickingSound(sound);
                    }
                }
                break;
            case 7:
                if (soundEmitter instanceof Skyvern entity) {
                    SkyvernSound sound;
                    AbstractTickableSoundInstance oldSound = ENTITY_SOUND_INSTANCE_MAP.get(entity.getId());
                    if (oldSound == null || !(oldSound instanceof SkyvernSound sound1 && sound1.isSameEntity(entity))) {
                        sound = new SkyvernSound(entity);
                        ENTITY_SOUND_INSTANCE_MAP.put(entity.getId(), sound);
                    } else {
                        sound = (SkyvernSound) oldSound;
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
