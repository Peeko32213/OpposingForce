package com.unusualmodding.opposing_force.client.renderer.gui;

import com.unusualmodding.opposing_force.OpposingForce;
import com.unusualmodding.opposing_force.registry.OPEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.tuple.Pair;

public enum GloomToxinHeartType {

    GLOOM_TOXIN(0);

    public static final ResourceLocation ATLAS = new ResourceLocation(OpposingForce.MOD_ID, "textures/gui/heart_type/gloom_toxin.png");

    public static final int ATLAS_W = 64;
    public static final int ATLAS_H = 9;
    private static final int SIZE = 9;

    private final int verticalIndex;

    GloomToxinHeartType(int verticalIndex) {
        this.verticalIndex = verticalIndex;
    }

    public Pair<Integer, Integer> getHeartPos(boolean hardcore) {
        int y = this.verticalIndex * SIZE;
        int xMult = hardcore ? 2 : 0;
        int x = xMult * SIZE;
        return Pair.of(x, y);
    }

    public Pair<Integer, Integer> getHalfHeartPos(boolean hardcore) {
        int y = this.verticalIndex * SIZE;
        int xMult = hardcore ? 3 : 1;
        int x = xMult * SIZE;
        return Pair.of(x, y);
    }

    public static GloomToxinHeartType getType(Player player) {
        GloomToxinHeartType type = null;
        if (player.hasEffect(OPEffects.GLOOM_TOXIN.get())) {
            type = GloomToxinHeartType.GLOOM_TOXIN;
        }
        return type;
    }
}
