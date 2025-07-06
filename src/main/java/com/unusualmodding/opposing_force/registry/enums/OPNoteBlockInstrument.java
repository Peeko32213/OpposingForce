package com.unusualmodding.opposing_force.registry.enums;

import com.unusualmodding.opposing_force.registry.OPSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

public enum OPNoteBlockInstrument {

    DICER("dicer", OPSoundEvents.NOTE_BLOCK_IMITATE_DICER, NoteBlockInstrument.Type.MOB_HEAD);

    private final String string;
    private final Holder<SoundEvent> soundEvent;
    private final NoteBlockInstrument.Type type;

    OPNoteBlockInstrument(String string, Holder sound, NoteBlockInstrument.Type type) {
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
