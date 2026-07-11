package com.barl_inc.opposing_force.client.sounds;

import com.barl_inc.opposing_force.entity.projectile.ElectricCharge;
import com.barl_inc.opposing_force.registry.OPSoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElectricChargeSound extends AbstractTickableSoundInstance {

    protected final ElectricCharge entity;

    public ElectricChargeSound(ElectricCharge entity) {
        super(OPSoundEvents.ELECTRIC_CHARGE.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
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
        this.pitch = Mth.lerp(Mth.clamp(horizontalDistance, this.getMinPitch(), this.getMaxPitch()), this.getMinPitch(), this.getMaxPitch());
        this.volume = 2.0F;
    }

    private float getMinPitch() {
        return 0.75F;
    }

    private float getMaxPitch() {
        return 1.25F;
    }

    @Override
    public boolean canStartSilent() {
        return true;
    }

    @Override
    public boolean canPlaySound() {
        return !this.entity.isSilent();
    }

    public boolean isSameEntity(ElectricCharge entity) {
        return this.entity.isAlive() && this.entity.getId() == entity.getId();
    }
}