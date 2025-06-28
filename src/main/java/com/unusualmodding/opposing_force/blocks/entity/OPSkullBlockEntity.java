package com.unusualmodding.opposing_force.blocks.entity;
import com.unusualmodding.opposing_force.registry.OPBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class OPSkullBlockEntity extends BlockEntity {
    @Nullable
    private ResourceLocation noteBlockSound;
    private int animationTickCount;
    private boolean isAnimating;

    public OPSkullBlockEntity(BlockPos p_155731_, BlockState p_155732_) {
        super(OPBlockEntityTypes.MOB_HEAD.get(), p_155731_, p_155732_);
    }

    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if (this.noteBlockSound != null) {
            compoundTag.putString("note_block_sound", this.noteBlockSound.toString());
        }

    }

    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        if (compoundTag.contains("note_block_sound", 8)) {
            this.noteBlockSound = ResourceLocation.tryParse(compoundTag.getString("note_block_sound"));
        }

    }

    public static void animation(Level p_261710_, BlockPos p_262153_, BlockState p_262021_, OPSkullBlockEntity p_261594_) {
        if (p_261710_.hasNeighborSignal(p_262153_)) {
            p_261594_.isAnimating = true;
            ++p_261594_.animationTickCount;
        } else {
            p_261594_.isAnimating = false;
        }

    }

    public float getAnimation(float p_262053_) {
        return this.isAnimating ? (float)this.animationTickCount + p_262053_ : (float)this.animationTickCount;
    }

    @Nullable
    public ResourceLocation getNoteBlockSound() {
        return this.noteBlockSound;
    }

    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

}