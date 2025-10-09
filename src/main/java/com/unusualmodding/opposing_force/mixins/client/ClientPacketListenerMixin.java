package com.unusualmodding.opposing_force.mixins.client;

import com.unusualmodding.opposing_force.client.sounds.*;
import com.unusualmodding.opposing_force.entity.Whizz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Inject(at = @At(value = "HEAD"), method = "postAddEntitySoundInstance")
    private void opposingForce$postAddEntitySoundInstance(Entity entity, CallbackInfo ci) {
        if (entity instanceof Whizz whizz) {
            Minecraft.getInstance().getSoundManager().queueTickingSound(new WhizzFlightSound(whizz));
        }
    }
}