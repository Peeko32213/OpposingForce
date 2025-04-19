package com.unusualmodding.opposingforce.common.message;

import com.unusualmodding.opposingforce.core.registry.OPParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class ParticleSyncS2CPacket {

    private final Vec3 blockPos;
    private final Vec3 attackPos;

    public ParticleSyncS2CPacket(Vec3 blockPos, Vec3 attackPos) {
        this.blockPos = blockPos;
        this.attackPos = attackPos;
    }

    public ParticleSyncS2CPacket(FriendlyByteBuf buf) {
        float bpx = buf.readFloat();
        float bpy = buf.readFloat();
        float bpz = buf.readFloat();
        float apx = buf.readFloat();
        float apy = buf.readFloat();
        float apz = buf.readFloat();


        this.blockPos = new Vec3(bpx, bpy, bpz);
        this.attackPos = new Vec3(apx, apy, apz);
    }

    public void toBytes(FriendlyByteBuf buf) {
        float bpx = (float) blockPos.x();
        float bpy = (float) blockPos.y();
        float bpz = (float) blockPos.z();
        float apx = (float) attackPos.x();
        float apy = (float) attackPos.y();
        float apz = (float) attackPos.z();
        buf.writeFloat(bpx);
        buf.writeFloat(bpy);
        buf.writeFloat(bpz);
        buf.writeFloat(apx);
        buf.writeFloat(apy);
        buf.writeFloat(apz);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            if(level != null) {
                level.addParticle(OPParticles.ELECTRIC_ORB.get(), blockPos.x, blockPos.y, blockPos.z, attackPos.x, attackPos.y, attackPos.z);
            }
        });
        return true;
    }
}