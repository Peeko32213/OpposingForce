package com.unusualmodding.opposing_force.mixins;

import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("unused")
@Mixin(NoteBlockInstrument.class)
public class NoteBlockInstrumentMixin {

    @Shadow
    @Final
    @Mutable
    private static NoteBlockInstrument[] $VALUES;

    private static final NoteBlockInstrument DICER = addVariant("DICER", "dicer", OPSoundEvents.NOTE_BLOCK_IMITATE_DICER);

    NoteBlockInstrumentMixin(String enumName, int ordinal, String name, Holder<SoundEvent> soundEvent, NoteBlockInstrument.Type type) {
        throw new UnsupportedOperationException("Replaced by Mixin");
    }

    private static NoteBlockInstrument addVariant(String enumName, String name, Holder<SoundEvent> soundEvent) {
        assert NoteBlockInstrumentMixin.$VALUES != null;
        ArrayList<NoteBlockInstrument> noteBlockInstruments = new ArrayList<>(Arrays.asList(NoteBlockInstrumentMixin.$VALUES));
        NoteBlockInstrument noteBlockInstrument = (NoteBlockInstrument)(Object)new NoteBlockInstrumentMixin(enumName, noteBlockInstruments.get(noteBlockInstruments.size() - 1).ordinal() + 1, name, soundEvent, NoteBlockInstrument.Type.MOB_HEAD);
        noteBlockInstruments.add(noteBlockInstrument);
        $VALUES = noteBlockInstruments.toArray(new NoteBlockInstrument[0]);
        return noteBlockInstrument;
    }
}