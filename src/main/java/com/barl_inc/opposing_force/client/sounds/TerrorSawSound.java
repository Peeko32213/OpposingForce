package com.barl_inc.opposing_force.client.sounds;

import com.barl_inc.opposing_force.entity.Terror;
import com.barl_inc.opposing_force.entity.utils.OPPoses;
import com.barl_inc.opposing_force.registry.OPSoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TerrorSawSound extends AbstractTickableSoundInstance {

    protected final Terror entity;

    public TerrorSawSound(Terror entity) {
        super(OPSoundEvents.TERROR_SAW.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
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
        this.pitch = this.entity.getVoicePitch();
        this.volume = 2.0F;
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.entity.isSilent() && this.entity.isAlive() && this.entity.isAggressive() && this.entity.getPose() == OPPoses.SAWING.get();
    }

    public boolean isSameEntity(Terror entity) {
        return this.entity.isAlive() && this.entity.getId() == entity.getId();
    }
}
