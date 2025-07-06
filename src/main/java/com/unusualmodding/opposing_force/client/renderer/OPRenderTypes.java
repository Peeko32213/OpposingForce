package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public abstract class OPRenderTypes extends RenderType {

    public OPRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable clearTask) {
        super(name, format, mode, bufferSize, useDelegate, needsSorting, setupTask, clearTask);
    }

    public static RenderType glowingEffect(ResourceLocation resourceLocation) {
        RenderStateShard.TextureStateShard shard = new RenderStateShard.TextureStateShard(resourceLocation, false, false);
        RenderType.CompositeState rendertype$state = RenderType.CompositeState.builder().setTextureState(shard).setShaderState(RENDERTYPE_BEACON_BEAM_SHADER).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setOverlayState(OVERLAY).setWriteMaskState(COLOR_WRITE).createCompositeState(false);
        return create("glow_effect", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, rendertype$state);
    }
}
