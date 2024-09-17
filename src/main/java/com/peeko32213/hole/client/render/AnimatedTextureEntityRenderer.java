package com.peeko32213.hole.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.peeko32213.hole.common.entity.projectile.AnimatedTextureEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

/**
 * Renderer for entities with animated textures.
 * This renderer handles scaling, orientation, and rendering of the animated textures.
 *
 * @param <T> The type of AnimatedTextureEntity being rendered.
 */
public class AnimatedTextureEntityRenderer<T extends AnimatedTextureEntity> extends EntityRenderer<T> {

    /**
     * Constructs an AnimatedTextureEntityRenderer.
     *
     * @param pContext The rendering context that provides access to necessary tools like buffer sources.
     */
    public AnimatedTextureEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    /**
     * Renders the given AnimatedTextureEntity. It applies scaling, orientation,
     * and renders the animated texture for the entity.
     *
     * @param pEntity       The entity to render.
     * @param pEntityYaw    The yaw rotation of the entity.
     * @param pPartialTicks The partial tick time.
     * @param pMatrixStack  The pose stack used to handle transformations like scaling and translation.
     * @param pBuffer       The buffer to write vertex data into.
     * @param pPackedLight  The packed light value for the entity.
     */
    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        // Prepare the matrix stack for transformations
        pMatrixStack.pushPose();

        // Scale the entity based on its size
        pMatrixStack.scale(pEntity.size(), pEntity.size(), pEntity.size());

        // Orient the entity to face the camera
        pMatrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());

        // Rotate the entity to face upwards
        pMatrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));

        // Get the current pose (translation, rotation, scale) from the pose stack
        PoseStack.Pose currentPose = pMatrixStack.last();
        Matrix4f matrix4f = currentPose.pose();  // The position transformation matrix
        Matrix3f matrix3f = currentPose.normal();  // The normal transformation matrix

        // Get the vertex consumer for the animated texture
        VertexConsumer vertexConsumer = pBuffer.getBuffer(RenderType.entityCutout(pEntity.animate(
                pEntity.getTextureLocation(),
                pEntity.getTextureCount(),
                pEntity.animationSpeed(),
                pEntity.animationTime()
        )));

        // Render the vertices of the entity's quad (sprite)
        renderVertices(vertexConsumer, matrix4f, matrix3f, pPackedLight);

        // Pop the matrix stack to revert transformations
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    /**
     * Renders the vertices of the entity's quad (2D plane with 4 corners).
     *
     * @param vertexConsumer The vertex consumer to use for rendering the entity.
     * @param matrix4f       The matrix for transforming the entity's position.
     * @param matrix3f       The matrix for transforming the entity's normals.
     * @param pPackedLight   The packed light value to apply to the vertices.
     */
    private void renderVertices(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int pPackedLight) {
        // Define the 4 corners of the entity's quad (sprite) and apply textures and lighting
        vertex(vertexConsumer, matrix4f, matrix3f, pPackedLight, 0.0F, 0, 0, 1);  // Bottom-left corner
        vertex(vertexConsumer, matrix4f, matrix3f, pPackedLight, 1.0F, 0, 1, 1);  // Bottom-right corner
        vertex(vertexConsumer, matrix4f, matrix3f, pPackedLight, 1.0F, 1, 1, 0);  // Top-right corner
        vertex(vertexConsumer, matrix4f, matrix3f, pPackedLight, 0.0F, 1, 0, 0);  // Top-left corner
    }

    /**
     * Adds a vertex to the current entity's quad with its position, color, texture coordinates, and lighting.
     *
     * @param vertexConsumer The vertex consumer to receive the vertex data.
     * @param positionMatrix The matrix for transforming vertex positions.
     * @param normalMatrix   The matrix for transforming vertex normals.
     * @param lightLevel     The packed light value.
     * @param xOffset        The x-offset of the vertex.
     * @param yOffset        The y-offset of the vertex.
     * @param textureU       The U coordinate of the texture (horizontal).
     * @param textureV       The V coordinate of the texture (vertical).
     */
    private static void vertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, int lightLevel, float xOffset, int yOffset, int textureU, int textureV) {
        // Add a single vertex with position, color, texture UV coordinates, and lighting
        vertexConsumer.vertex(positionMatrix, xOffset - 0.5F, (float) yOffset - 0.5F, 0.0F)
                .color(255, 255, 255, 255)  // Set vertex color to white (fully opaque)
                .uv((float) textureU, (float) textureV)  // Set texture UV coordinates
                .overlayCoords(OverlayTexture.NO_OVERLAY)  // No special overlays (like damage)
                .uv2(lightLevel)  // Set the light level for shading
                .normal(normalMatrix, 0.0F, 1.0F, 0.0F)  // Set normal for lighting calculations
                .endVertex();  // Finalize this vertex
    }

    /**
     * Gets the texture location for the animated entity.
     *
     * @param pEntity The animated entity.
     * @return The {@link ResourceLocation} of the entity's texture.
     */
    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return pEntity.getTextureLocation();
    }
}
