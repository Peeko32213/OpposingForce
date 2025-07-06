package com.unusualmodding.opposing_force.mixins;

import com.unusualmodding.opposing_force.blocks.entity.MobHeadBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin extends Block {

    public NoteBlockMixin(Properties p_49795_) {
        super(p_49795_);
    }

    @Inject(method = "getCustomSoundId", at = @At("HEAD"), cancellable = true)
    public void getMobHeadCustomSoundId(Level level, BlockPos pos, CallbackInfoReturnable<ResourceLocation> callback) {
        BlockEntity blockentity = level.getBlockEntity(pos.above());
        if (blockentity instanceof MobHeadBlockEntity mobHeadBlockEntity) {
            callback.setReturnValue(mobHeadBlockEntity.getNoteBlockSound());
        }
    }
}
