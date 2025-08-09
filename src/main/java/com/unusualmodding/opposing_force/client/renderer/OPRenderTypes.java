package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public abstract class OPRenderTypes extends RenderType {

    public OPRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable clearTask) {
        super(name, format, mode, bufferSize, useDelegate, needsSorting, setupTask, clearTask);
    }

    public static RenderType glowingEffect(ResourceLocation resourceLocation) {
        RenderStateShard.TextureStateShard shard = new RenderStateShard.TextureStateShard(resourceLocation, false, false);
        RenderType.CompositeState rendertype$state = RenderType.CompositeState.builder().setTextureState(shard).setShaderState(RENDERTYPE_BEACON_BEAM_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setOverlayState(OVERLAY).setWriteMaskState(COLOR_WRITE).createCompositeState(false);
        return create("glow_effect", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, rendertype$state);
    }

    public static RenderType glowingZOffset(ResourceLocation resourceLocation) {
        RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER).setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setWriteMaskState(COLOR_WRITE).setLayeringState(VIEW_OFFSET_Z_LAYERING).createCompositeState(false);
        return create("glowing_z_offset", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, rendertype$compositestate);
    }

    public static RenderType glowingEyes(ResourceLocation location) {
        RenderType.CompositeState rendertype$compositestate = CompositeState.builder().setShaderState(RENDERTYPE_EYES_SHADER).setTextureState(new TextureStateShard(location, false, false)).setTransparencyState(ADDITIVE_TRANSPARENCY).setCullState(NO_CULL).setWriteMaskState(COLOR_WRITE).setOverlayState(OVERLAY).createCompositeState(false);
        return create("glowing_eyes", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536,false,true, rendertype$compositestate);
    }
}
