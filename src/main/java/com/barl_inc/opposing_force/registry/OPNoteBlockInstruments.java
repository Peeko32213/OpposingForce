package com.barl_inc.opposing_force.registry;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public enum OPNoteBlockInstruments {

    DICER("dicer", OPSoundEvents.NOTE_BLOCK_IMITATE_DICER, NoteBlockInstrument.Type.MOB_HEAD),
    FROWZY("frowzy", OPSoundEvents.NOTE_BLOCK_IMITATE_FROWZY, NoteBlockInstrument.Type.MOB_HEAD),
    RAMBLER("rambler", OPSoundEvents.NOTE_BLOCK_IMITATE_RAMBLER, NoteBlockInstrument.Type.MOB_HEAD),
    SKYVERN("skyvern", SoundEvents.NOTE_BLOCK_IMITATE_ENDER_DRAGON, NoteBlockInstrument.Type.MOB_HEAD),
    TART("tart", OPSoundEvents.NOTE_BLOCK_IMITATE_TART, NoteBlockInstrument.Type.MOB_HEAD),
    WHIZZ("whizz", OPSoundEvents.NOTE_BLOCK_IMITATE_WHIZZ, NoteBlockInstrument.Type.MOB_HEAD);

    private final String string;
    private final Holder<SoundEvent> soundEvent;
    private final NoteBlockInstrument.Type type;

    OPNoteBlockInstruments(String string, Holder<SoundEvent> sound, NoteBlockInstrument.Type type) {
        this.string = string;
        this.soundEvent = sound;
        this.type = type;
    }

    public NoteBlockInstrument.Type getType() {
        return type;
    }

    public Holder<SoundEvent> getSoundEvent() {
        return soundEvent;
    }

    public String getString() {
        return string;
    }

    public NoteBlockInstrument get() {
        return NoteBlockInstrument.valueOf(this.name());
    }
}
