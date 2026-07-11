package com.barl_inc.opposing_force.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

public class PlayerData {

    public static final Codec<PlayerData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.BOOL.fieldOf("hasGottenEndMessage").forGetter(PlayerData::hasGottenEndMessage),
                    Codec.BOOL.fieldOf("hasGottenNetherMessage").forGetter(PlayerData::hasGottenNetherMessage)
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

    public void sendNetherMessageAndMarkComplete(Player player) {
        MutableComponent component = Component.translatable("opposing_force.nether_progression.enabled");
        component = component.withStyle(ChatFormatting.RED);
        player.sendSystemMessage(component);
        setHasGottenNetherMessage(true);
    }

    public void sendEndMessageAndMarkComplete(Player player) {
        MutableComponent component = Component.translatable("opposing_force.end_progression.enabled");
        component = component.withStyle(ChatFormatting.LIGHT_PURPLE);
        player.sendSystemMessage(component);
        setHasGottenEndMessage(true);
    }
}
