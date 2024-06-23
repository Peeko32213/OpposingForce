package com.peeko32213.hole.client.model;

import com.peeko32213.hole.Hole;
import net.minecraft.resources.ResourceLocation;

public class ModelLocations {

    public static ModelData PALE_SPIDER = new ModelData("pale_spider");
    public static ModelData UMBER_SPIDER = new ModelData("umber_spider");

    public static class ModelData {
        private ResourceLocation model;
        private ResourceLocation texture;
        private ResourceLocation animation;


        public ModelData(String name, String texture){
            this.model = new ResourceLocation(Hole.MODID, "geo/" + name + ".geo.json");
            this.texture = new ResourceLocation(Hole.MODID, "textures/entity/" + texture + ".png");
            this.animation = new ResourceLocation(Hole.MODID, "animations/" + name + ".animation.json");
        }
        public ModelData(String name){
            this.model = new ResourceLocation(Hole.MODID, "geo/" + name + ".geo.json");
            this.texture = new ResourceLocation(Hole.MODID, "textures/entity/" + name + ".png");
            this.animation = new ResourceLocation(Hole.MODID, "animations/" + name + ".animation.json");
        }


        public ResourceLocation getAnimation() {
            return animation;
        }

        public ResourceLocation getModel() {
            return model;
        }

        public ResourceLocation getTexture() {
            return texture;
        }

    }
}
