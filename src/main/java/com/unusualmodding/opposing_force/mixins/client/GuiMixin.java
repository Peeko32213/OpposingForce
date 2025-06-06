package com.unusualmodding.opposing_force.mixins.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.unusualmodding.opposing_force.client.renderer.gui.ElectrifiedHeartType;
import com.unusualmodding.opposing_force.client.renderer.gui.GloomToxinHeartType;
import com.unusualmodding.opposing_force.registry.OPEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    private static boolean drawForHeartType(Gui.HeartType type) {
        return type != Gui.HeartType.CONTAINER && type != Gui.HeartType.ABSORBING && type != Gui.HeartType.FROZEN;
    }

    private static boolean hasAnyCustomHearts(Player player) {
        if (player.hasEffect(OPEffects.ELECTRIFIED.get())) {
            return true;
        }
        return player.hasEffect(OPEffects.GLOOM_TOXIN.get());
    }

    @Inject(method = "renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIIZZ)V", at = @At("HEAD"), cancellable = true)
    private void renderHeart(GuiGraphics stack, Gui.HeartType __, int x, int y, int v, boolean blinking, boolean halfHeart, CallbackInfo cbi) {
        if (!blinking && drawForHeartType(__) && Minecraft.getInstance().cameraEntity instanceof Player player && hasAnyCustomHearts(player)) {

            GloomToxinHeartType gloomToxin = GloomToxinHeartType.getType(player);
            ElectrifiedHeartType electrified = ElectrifiedHeartType.getType(player);

            if (gloomToxin != null) {
                boolean hardcore = player.level().getLevelData().isHardcore();
                Pair<Integer, Integer> pos = gloomToxin.getHeartPos(hardcore);
                if (halfHeart) {
                    pos = gloomToxin.getHalfHeartPos(hardcore);
                }
                RenderSystem.setShaderTexture(0, GloomToxinHeartType.ATLAS);
                stack.blit(GloomToxinHeartType.ATLAS, x, y, pos.getLeft(), pos.getRight(), 9, 9, GloomToxinHeartType.ATLAS_W, GloomToxinHeartType.ATLAS_H);
                RenderSystem.setShaderTexture(0, Gui.GUI_ICONS_LOCATION);
                cbi.cancel();
            }

            if (electrified != null) {
                boolean hardcore = player.level().getLevelData().isHardcore();
                Pair<Integer, Integer> pos = electrified.getHeartPos(hardcore);
                if (halfHeart) {
                    pos = electrified.getHalfHeartPos(hardcore);
                }
                RenderSystem.setShaderTexture(0, ElectrifiedHeartType.ATLAS);
                stack.blit(ElectrifiedHeartType.ATLAS, x, y, pos.getLeft(), pos.getRight(), 9, 9, ElectrifiedHeartType.ATLAS_W, ElectrifiedHeartType.ATLAS_H);
                RenderSystem.setShaderTexture(0, Gui.GUI_ICONS_LOCATION);
                cbi.cancel();
            }
        }
    }
}
