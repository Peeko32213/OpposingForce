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

    private final float blockX, blockY, blockZ;
    private final float attackX, attackY, attackZ;

    public ParticleSyncS2CPacket(float blockX, float blockY, float blockZ, float attackX, float attackY, float attackZ) {
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
        this.attackX = attackX;
        this.attackY = attackY;
        this.attackZ = attackZ;
    }


    public ParticleSyncS2CPacket(FriendlyByteBuf buf) {
        this.blockX = buf.readFloat();
        this.blockY = buf.readFloat();
        this.blockZ = buf.readFloat();
        this.attackX = buf.readFloat();
        this.attackY = buf.readFloat();
        this.attackZ = buf.readFloat();
    }


    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(blockX);
        buf.writeFloat(blockY);
        buf.writeFloat(blockZ);
        buf.writeFloat(attackX);
        buf.writeFloat(attackY);
        buf.writeFloat(attackZ);
    }


    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            if(level != null) {
                level.addParticle(OPParticles.ELECTRIC_ORB.get(),
                        blockX, blockY, blockZ,
                        attackX, attackY, attackZ);
            }
        });
        return true;
    }
}