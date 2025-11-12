package com.unusualmodding.opposing_force.utils;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.entity.projectile.ElectricCharge;
import com.unusualmodding.opposing_force.entity.projectile.Tomahawk;
import com.unusualmodding.opposing_force.entity.projectile.UmberDagger;
import com.unusualmodding.opposing_force.events.ScreenShakeEvent;
import com.unusualmodding.opposing_force.items.MobHeadItem;
import com.unusualmodding.opposing_force.registry.OPItems;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = OpposingForce.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {

    public void init() {
    }

    public void clientInit() {
    }

    public Object getArmorRenderProperties() {
        return null;
    }

    public void screenShake(ScreenShakeEvent event) {
    }

    public void playWorldSound(@Nullable Object soundEmitter, byte type) {
    }

    public void clearSoundCacheFor(Entity entity) {
    }
}
