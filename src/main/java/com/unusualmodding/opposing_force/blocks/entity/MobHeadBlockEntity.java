package com.unusualmodding.opposing_force.blocks.entity;

import com.unusualmodding.opposing_force.registry.OPBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class MobHeadBlockEntity extends BlockEntity {

    @Nullable
    private ResourceLocation noteBlockSound;
    private int animationTickCount;
    private boolean isAnimating;

    public MobHeadBlockEntity(BlockPos p_155731_, BlockState p_155732_) {
        super(OPBlockEntityTypes.MOB_HEAD.get(), p_155731_, p_155732_);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if (this.noteBlockSound != null) {
            compoundTag.putString("note_block_sound", this.noteBlockSound.toString());
        }
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        if (compoundTag.contains("note_block_sound", 8)) {
            this.noteBlockSound = ResourceLocation.tryParse(compoundTag.getString("note_block_sound"));
        }
    }

    public static void animation(Level level, BlockPos pos, BlockState state, MobHeadBlockEntity blockEntity) {
        if (level.hasNeighborSignal(pos)) {
            blockEntity.isAnimating = true;
            ++blockEntity.animationTickCount;
        } else {
            blockEntity.isAnimating = false;
        }
    }

    public float getAnimation(float partialTicks) {
        return this.isAnimating ? (float) this.animationTickCount + partialTicks : (float) this.animationTickCount;
    }

    @Nullable
    public ResourceLocation getNoteBlockSound() {
        return this.noteBlockSound;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

}