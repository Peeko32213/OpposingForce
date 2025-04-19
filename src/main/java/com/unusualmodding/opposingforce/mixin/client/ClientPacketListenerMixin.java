package com.unusualmodding.opposingforce.mixin.client;

import com.unusualmodding.opposingforce.client.sound.ElectricBallSoundInstance;
import com.unusualmodding.opposingforce.common.entity.custom.projectile.ElectricBall;
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
    private void handleAddMob(Entity entity, CallbackInfo ci) {
        if (entity instanceof ElectricBall electricBall) {
            Minecraft.getInstance().getSoundManager().queueTickingSound(new ElectricBallSoundInstance(electricBall));
        }
    }
}