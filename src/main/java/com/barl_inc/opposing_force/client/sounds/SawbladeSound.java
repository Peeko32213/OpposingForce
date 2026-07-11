package com.barl_inc.opposing_force.client.sounds;

import com.barl_inc.opposing_force.items.SawbladeItem;
import com.barl_inc.opposing_force.registry.OPItems;
import com.barl_inc.opposing_force.registry.OPSoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class SawbladeSound extends ItemTickableSound {

    public SawbladeSound(LivingEntity user) {
        super(user, OPSoundEvents.SAWBLADE_SAW.get());
    }

    public void tickVolume(ItemStack itemStack) {
        float useAmount = SawbladeItem.getLerpedUseTime(itemStack, 1.0F) / 5F;
        this.volume = useAmount;
        this.pitch = 0.2F + 0.8F * useAmount;
    }

    @Override
    public boolean isValidItem(ItemStack itemStack) {
        return itemStack.is(OPItems.SAWBLADE.get());
    }
}