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
    public static final ModelLayerLocation FROWZY = main("frowzy");
    public static final ModelLayerLocation GUZZLER = main("guzzler");
    public static final ModelLayerLocation PALE_SPIDER = main("pale_spider");
    public static final ModelLayerLocation RAMBLE = main("ramble");
    public static final ModelLayerLocation SLUG = main("slug");
    public static final ModelLayerLocation TERROR = main("terror");
    public static final ModelLayerLocation TREMBLER = main("trembler");
    public static final ModelLayerLocation UMBER_SPIDER = main("umber_spider");
    public static final ModelLayerLocation VOLT = main("volt");
    public static final ModelLayerLocation VOLT_CHARGED = register("volt", "charged_volt");
    public static final ModelLayerLocation WHIZZ = main("whizz");

    public static final ModelLayerLocation DEEPWOVEN_ARMOR = main("deepwoven_armor");

    private static ModelLayerLocation register(String id, String name) {
        return new ModelLayerLocation(new ResourceLocation(OpposingForce.MOD_ID, id), name);
    }

    private static ModelLayerLocation main(String id) {
        return register(id, "main");
    }
}
