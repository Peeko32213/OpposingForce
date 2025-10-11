package com.unusualmodding.opposing_force.client.sounds;

import com.unusualmodding.opposing_force.entity.Frowzy;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FrowzyPantingSound extends AbstractTickableSoundInstance {

    protected final Frowzy entity;

    public FrowzyPantingSound(Frowzy entity) {
        super(OPSoundEvents.FROWZY_RUN.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
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
        this.pitch = 1.0F;
        this.volume = 1.0F;
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.entity.isSilent() && this.entity.isAlive();
    }

    public boolean isSameEntity(Frowzy entity) {
        return this.entity.isAlive() && this.entity.getId() == entity.getId();
    }
}
