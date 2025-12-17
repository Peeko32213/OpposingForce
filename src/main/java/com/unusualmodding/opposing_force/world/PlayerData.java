package com.unusualmodding.opposing_force.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class PlayerData {

    public static final Codec<PlayerData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("hasGottenEndMessage")
                            .forGetter(PlayerData::hasGottenEndMessage),
                    Codec.BOOL.fieldOf("hasGottenNetherMessage")
                            .forGetter(PlayerData::hasGottenNetherMessage)
            ).apply(instance, PlayerData::new)
    );

    private boolean hasGottenEndMessage;
    private boolean hasGottenNetherMessage;


    public PlayerData() {
        this(false, false);
    }

    public PlayerData(boolean hasGottenEndMessage, boolean hasGottenNetherMessage) {
        this.hasGottenEndMessage = hasGottenEndMessage;
        this.hasGottenNetherMessage = hasGottenNetherMessage;
    }


    public boolean hasGottenEndMessage() {
        return hasGottenEndMessage;
    }

    public boolean hasGottenNetherMessage() {
        return hasGottenNetherMessage;
    }

    public void setHasGottenEndMessage(boolean value) {
        this.hasGottenEndMessage = value;
    }

    public void setHasGottenNetherMessage(boolean value) {
        this.hasGottenNetherMessage = value;
    }
}
