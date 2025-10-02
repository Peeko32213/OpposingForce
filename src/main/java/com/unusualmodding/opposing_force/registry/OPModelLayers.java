package com.unusualmodding.opposing_force.registry;

import com.unusualmodding.opposing_force.OpposingForce;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OPModelLayers {

    public static final ModelLayerLocation DICER = main("dicer");
    public static final ModelLayerLocation DICER_HEAD = main("dicer_head");
    public static final ModelLayerLocation EMERALDFISH = main("emeraldfish");
    public static final ModelLayerLocation FIRE_SLIME = main("fire_slime");
    public static final ModelLayerLocation FIRE_SLUG = main("fire_slug");
    public static final ModelLayerLocation FROWZY = main("frowzy");
    public static final ModelLayerLocation FROWZY_HEAD = main("frowzy_head");
    public static final ModelLayerLocation GUZZLER = main("guzzler");
    public static final ModelLayerLocation PALE_SPIDER = main("pale_spider");
    public static final ModelLayerLocation RAMBLE = main("ramble");
    public static final ModelLayerLocation RAMBLE_SKULL = main("ramble_skull");
    public static final ModelLayerLocation SLUG = main("slug");
    public static final ModelLayerLocation TERROR = main("terror");
    public static final ModelLayerLocation TREMBLER = main("trembler");
    public static final ModelLayerLocation UMBER_SPIDER = main("umber_spider");
    public static final ModelLayerLocation VOLT = main("volt");
    public static final ModelLayerLocation VOLT_CHARGED = register("volt", "charged_volt");
    public static final ModelLayerLocation WHIZZ = main("whizz");

    public static final ModelLayerLocation MOON_SHOES = main("moon_shoes");
    public static final ModelLayerLocation DEEPWOVEN_ARMOR = main("deepwoven_armor");
    public static final ModelLayerLocation EMERALD_ARMOR = main("emerald_armor");
    public static final ModelLayerLocation STONE_ARMOR = main("stone_armor");
    public static final ModelLayerLocation WOODEN_ARMOR = main("wooden_armor");

    public static final ModelLayerLocation LASER_BOLT = main("laser_bolt");

    private static ModelLayerLocation register(String id, String name) {
        return new ModelLayerLocation(new ResourceLocation(OpposingForce.MOD_ID, id), name);
    }

    private static ModelLayerLocation main(String id) {
        return register(id, "main");
    }
}
