package com.unusualmodding.opposing_force.client.sounds;

import com.unusualmodding.opposing_force.entity.projectile.ElectricCharge;
import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElectricChargeSound extends AbstractTickableSoundInstance {

    protected final ElectricCharge electricCharge;

    public ElectricChargeSound(ElectricCharge electricCharge) {
        super(OPSoundEvents.ELECTRIC_CHARGE.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
        this.electricCharge = electricCharge;
        this.x = (float) electricCharge.getX();
        this.y = (float) electricCharge.getY();
        this.z = (float) electricCharge.getZ();
        this.looping = true;
        this.delay = 0;
        this.volume = 2.0F;
    }

    @Override
    public void tick() {
        if (this.electricCharge.isRemoved()) {
            this.stop();
            return;
        }
        this.x = (float) this.electricCharge.getX();
        this.y = (float) this.electricCharge.getY();
        this.z = (float) this.electricCharge.getZ();
        float horizontalDistance = (float) this.electricCharge.getDeltaMovement().horizontalDistance();
        this.pitch = Mth.lerp(Mth.clamp(horizontalDistance, this.getMinPitch(), this.getMaxPitch()), this.getMinPitch(), this.getMaxPitch());
    }

    private float getMinPitch() {
        if (this.electricCharge.getChargeScale() >= 2.0F) {
            return 0.85F;
        }
        else return 1.0f;
    }

    private float getMaxPitch() {
        if (this.electricCharge.getChargeScale() >= 2.0F) {
            return 1.0F;
        }
        else return 1.2F;
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.electricCharge.isSilent();
    }
}