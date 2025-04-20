package com.unusualmodding.opposingforce.client.sound;

import com.unusualmodding.opposingforce.common.entity.custom.projectile.ElectricBall;
import com.unusualmodding.opposingforce.core.registry.OPSounds;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElectricBallSoundInstance extends AbstractTickableSoundInstance {

    protected final ElectricBall electricBall;

    public ElectricBallSoundInstance(ElectricBall smallElectricBall) {
        super(OPSounds.ELECTRIC_CHARGE.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.electricBall = smallElectricBall;
        this.x = (float) smallElectricBall.getX();
        this.y = (float) smallElectricBall.getY();
        this.z = (float) smallElectricBall.getZ();
        this.looping = true;
        this.delay = 0;
        this.volume = 0.6f;
    }

    @Override
    public void tick() {
        if (this.electricBall.isRemoved()) {
            this.stop();
            return;
        }
        this.x = (float) this.electricBall.getX();
        this.y = (float) this.electricBall.getY();
        this.z = (float) this.electricBall.getZ();
        float horizontalDistance = (float) this.electricBall.getDeltaMovement().horizontalDistance();
        this.pitch = Mth.lerp(Mth.clamp(horizontalDistance, this.getMinPitch(), this.getMaxPitch()), this.getMinPitch(), this.getMaxPitch());
    }

    private float getMinPitch() {
        if (this.electricBall.getChargeScale() >= 2.0F) {
            return 0.85f;
        }
        else return 1.0f;
    }

    private float getMaxPitch() {
        if (this.electricBall.getChargeScale() >= 2.0F) {
            return 1.0f;
        }
        else return 1.2f;
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.electricBall.isSilent();
    }
}