package com.unusualmodding.opposing_force.client.animations.items;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.unusualmodding.opposing_force.client.models.item.HierarchicalItemModel;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class ItemKeyframeAnimations {

    public static void animate(HierarchicalItemModel model, AnimationDefinition definition, long accumulatedTime, float scale, Vector3f vectorCache) {
        float elapsedSeconds = getElapsedSeconds(definition, accumulatedTime);
        for (Map.Entry<String, List<AnimationChannel>> stringListEntry : definition.boneAnimations().entrySet()) {
            Optional<ModelPart> modelParts = model.getAnyDescendantWithName(stringListEntry.getKey());
            List<AnimationChannel> animationChannels = stringListEntry.getValue();
            modelParts.ifPresent((part) -> animationChannels.forEach((channel) -> {
                    Keyframe[] keyframes = channel.keyframes();
                    int max = Math.max(0, Mth.binarySearch(0, keyframes.length, (i) -> elapsedSeconds <= keyframes[i].timestamp()) - 1);
                    int min = Math.min(keyframes.length - 1, max + 1);
                    Keyframe maxKeyframe = keyframes[max];
                    Keyframe minKeyframe = keyframes[min];
                    float time = elapsedSeconds - maxKeyframe.timestamp();
                    float f;
                    if (min != max) {
                        f = Mth.clamp(time / (minKeyframe.timestamp() - maxKeyframe.timestamp()), 0.0F, 1.0F);
                    } else {
                        f = 0.0F;
                    }
                    minKeyframe.interpolation().apply(vectorCache, f, keyframes, max, min, scale);
                    channel.target().apply(part, vectorCache);
                })
            );
        }
    }

    private static float getElapsedSeconds(AnimationDefinition definition, long accumulatedTime) {
        float time = (float) accumulatedTime / 1000.0F;
        return definition.looping() ? time % definition.lengthInSeconds() : time;
    }
}
