package com.unusualmodding.opposing_force.registry;

import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Rarity;

import java.awt.*;

public class OPRarities {

    public static final Rarity RAINBOW = Rarity.create("opposing_force:rainbow", style -> style.withColor(Color.HSBtoRGB((System.currentTimeMillis() % 8000) / 8000F, 1F, 1F)));
//    public static final Rarity LEGENDARY = Rarity.create("opposing_force:legendary", style -> interpolateColors(style, new Color(245, 66, 147), new Color(245, 158, 66)));
    public static final Rarity LEGENDARY = Rarity.create("opposing_force:legendary", style -> style.withColor(0xff4f38));

    private static Style interpolateColors(Style style, Color start, Color end) {
        float time = (System.currentTimeMillis() % 8000L) / 8000F;
        time = (float) (0.5F - 0.5F * Math.cos(time * Math.PI * 2));

        int r = (int) (start.getRed() + time * (end.getRed() - start.getRed()));
        int g = (int) (start.getGreen() + time * (end.getGreen() - start.getGreen()));
        int b = (int) (start.getBlue() + time * (end.getBlue() - start.getBlue()));

        return style.withColor(new Color(r, g, b).getRGB());
    }
}
