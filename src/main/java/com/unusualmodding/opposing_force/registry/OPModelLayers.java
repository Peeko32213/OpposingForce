package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OPModelLayers {

    public static final ModelLayerLocation DICER_LAYER = main("dicer");
    public static final ModelLayerLocation EMERALDFISH_LAYER = main("emeraldfish");
    public static final ModelLayerLocation FIRE_SLIME_LAYER = main("fire_slime");
    public static final ModelLayerLocation FROWZY_LAYER = main("frowzy");
    public static final ModelLayerLocation GUZZLER_LAYER = main("guzzler");
    public static final ModelLayerLocation PALE_SPIDER_LAYER = main("pale_spider");
    public static final ModelLayerLocation RAMBLE_LAYER = main("ramble");
    public static final ModelLayerLocation SLUG_LAYER = main("slug");
    public static final ModelLayerLocation TERROR_LAYER = main("terror");
    public static final ModelLayerLocation TREMBLER_LAYER = main("trembler");
    public static final ModelLayerLocation UMBER_SPIDER_LAYER = main("umber_spider");
    public static final ModelLayerLocation VOLT_LAYER = main("volt");
    public static final ModelLayerLocation WHIZZ_LAYER = main("whizz");

    public static final ModelLayerLocation TREMBLER_SHELL_LAYER = main("trembler_shell");

    public static final ModelLayerLocation DEEPWOVEN_ARMOR_LAYER = main("deepwoven_armor");

    private static ModelLayerLocation register(String id, String name) {
        return new ModelLayerLocation(new ResourceLocation(OpposingForce.MOD_ID, id), name);
    }

    private static ModelLayerLocation main(String id) {
        return register(id, "main");
    }
}
