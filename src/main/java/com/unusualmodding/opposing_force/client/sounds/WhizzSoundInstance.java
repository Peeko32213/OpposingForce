package com.unusualmodding.opposing_force.client.sounds;

import com.unusualmodding.opposing_force.entity.Whizz;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WhizzSoundInstance extends AbstractTickableSoundInstance {

    protected final Whizz entity;

    public WhizzSoundInstance(Whizz entity) {
        super(OPSoundEvents.WHIZZ_FLY.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.entity = entity;
        this.x = (float) entity.getX();
        this.y = (float) entity.getY();
        this.z = (float) entity.getZ();
        this.looping = true;
        this.delay = 0;
        this.volume = 0.0f;
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
        if (horizontalDistance >= 0.01F) {
            this.pitch = Mth.lerp(Mth.clamp(horizontalDistance, 1.1F, 1.3F), 1.1F, 1.3F);
            this.volume = Mth.lerp(Mth.clamp(horizontalDistance, 0.0F, 0.3F), 0.2F, 0.3F);
        } else {
            this.pitch = 0.0F;
            this.volume = 0.0F;
        }
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.entity.isSilent();
    }
}