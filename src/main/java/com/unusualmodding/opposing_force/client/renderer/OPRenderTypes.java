package com.unusualmodding.opposing_force.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public abstract class OPRenderTypes extends RenderType {

    public OPRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable clearTask) {
        super(name, format, mode, bufferSize, useDelegate, needsSorting, setupTask, clearTask);
    }

    protected static final RenderStateShard.TexturingStateShard GLINT_TEXTURING = new RenderStateShard.TexturingStateShard("entity_glint_texturing", () -> setupGlintTexturing(1F, 8L), RenderSystem::resetTextureMatrix);

    public static RenderType specialGlint(ResourceLocation resourceLocation, boolean blur, boolean outline) {
        RenderStateShard.TextureStateShard shard = new RenderStateShard.TextureStateShard(resourceLocation, blur, false);
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
                .setTextureState(shard)
                .setCullState(NO_CULL)
                .setDepthTestState(EQUAL_DEPTH_TEST)
                .setTransparencyState(ADDITIVE_TRANSPARENCY)
                .setTexturingState(GLINT_TEXTURING)
                .setOverlayState(OVERLAY)
                .setWriteMaskState(COLOR_WRITE)
                .createCompositeState(outline);
        return create("special_glint", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, compositeState);
    }

    public static RenderType glowingEffect(ResourceLocation resourceLocation) {
        RenderStateShard.TextureStateShard shard = new RenderStateShard.TextureStateShard(resourceLocation, false, false);
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                .setTextureState(shard).setShaderState(RENDERTYPE_BEACON_BEAM_SHADER)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setOverlayState(OVERLAY)
                .setWriteMaskState(COLOR_WRITE)
                .createCompositeState(false);
        return create("glow_effect", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, compositeState);
    }

    public static RenderType glowingZOffset(ResourceLocation resourceLocation) {
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setWriteMaskState(COLOR_WRITE)
                .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                .createCompositeState(false);
        return create("glowing_z_offset", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, compositeState);
    }

    public static RenderType eyesZOffset(ResourceLocation resourceLocation) {
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_EYES_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
                .setTransparencyState(ADDITIVE_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setWriteMaskState(COLOR_WRITE)
                .setOverlayState(OVERLAY)
                .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                .createCompositeState(false);
        return create("eyes_z_offset", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true, compositeState);
    }

    public static RenderType laserBolt(ResourceLocation resourceLocation) {
        RenderType.CompositeState compositeState = CompositeState.builder()
                .setTextureState(new TextureStateShard(resourceLocation, false, false))
                .setShaderState(RenderType.RENDERTYPE_ENERGY_SWIRL_SHADER)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLightmapState(LIGHTMAP)
                .createCompositeState(false);
        return create("laser_bolt", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256,false,true, compositeState);
    }

    private static void setupGlintTexturing(float in, long time) {
        long i = Util.getMillis() * time;
        float f = (float)(i % 30000L) / 30000.0F;
        Matrix4f matrix4f = (new Matrix4f()).translation(0, f, 0.0F);
        matrix4f.scale(in);
        RenderSystem.setTextureMatrix(matrix4f);
    }
}
