package com.unusualmodding.opposing_force.client.sounds;

import com.unusualmodding.opposing_force.entity.Skyvern;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkyvernSound extends AbstractTickableSoundInstance {

    protected final Skyvern entity;

    public SkyvernSound(Skyvern entity) {
        super(OPSoundEvents.SKYVERN_LOOP.get(), SoundSource.HOSTILE, SoundInstance.createUnseededRandom());
        this.entity = entity;
        this.x = (float) entity.getX();
        this.y = (float) entity.getY();
        this.z = (float) entity.getZ();
        this.looping = true;
        this.delay = 0;
        this.volume = 0.0F;
    }

    @Override
    public void tick() {
        if (this.entity.isRemoved()) {
            this.stop();
            return;
        }
        this.x = (float) this.entity.getX();
        this.y = (float) this.entity.getY();
        this.z = (float) this.entity.getZ();
        float horizontalDistance = (float) this.entity.getDeltaMovement().horizontalDistance();
        if (entity.isAlive()) {
            this.pitch = Mth.lerp(Mth.clamp(horizontalDistance, 0.75F, 1.25F), 0.75F, 1.25F);
            this.volume = Mth.lerp(Mth.clamp(horizontalDistance, 0.25F, 2.0F), 0.25F, 2.0F);
        } else {
            this.volume = 0.0F;
            this.pitch = 0.0F;
        }
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.entity.isSilent() && this.entity.isAlive();
    }

    public boolean isSameEntity(Skyvern entity) {
        return this.entity.isAlive() && this.entity.getId() == entity.getId();
    }
}
