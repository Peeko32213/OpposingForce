package com.unusualmodding.opposing_force.mixins;

import com.unusualmodding.opposing_force.interfaces.CreeperExtension;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.network.syncher.EntityDataAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Creeper.class)
public abstract class CreeperMixin implements CreeperExtension {


    @Final
    @Shadow
    private static EntityDataAccessor<Boolean> DATA_IS_POWERED;

    @Override
    public void opposingForce$setCharged(boolean charged) {
        Creeper entity = (Creeper)(Object) this;
        entity.getEntityData().set(DATA_IS_POWERED, charged);
    }
}

