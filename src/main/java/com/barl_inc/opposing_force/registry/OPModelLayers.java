package com.barl_inc.opposing_force.registry;

import com.barl_inc.opposing_force.OpposingForce;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class OPModelLayers {

    public static final ModelLayerLocation DICER = register("dicer");
    public static final ModelLayerLocation DICER_HEAD = register("dicer_head");
    public static final ModelLayerLocation EMERALDFISH = register("emeraldfish");
    public static final ModelLayerLocation FIRE_SLIME = register("fire_slime");
    public static final ModelLayerLocation FROWZY = register("frowzy");
    public static final ModelLayerLocation FROWZY_HEAD = register("frowzy_head");
    public static final ModelLayerLocation GUZZLER = register("guzzler");
    public static final ModelLayerLocation HANGING_SPIDER = register("hanging_spider");
    public static final ModelLayerLocation LADYBUG = register("ladybug");
    public static final ModelLayerLocation NYMPH = register("nymph");
    public static final ModelLayerLocation RAMBLER = register("rambler");
    public static final ModelLayerLocation RAMBLER_SKULL = register("rambler_skull");
    public static final ModelLayerLocation SKYVERN = register("skyvern");
    public static final ModelLayerLocation SKYVERN_BODY = register("skyvern_body");
    public static final ModelLayerLocation SKYVERN_TAIL = register("skyvern_tail");
    public static final ModelLayerLocation SKYVERN_HEAD = register("skyvern_head");
    public static final ModelLayerLocation SLUG = register("slug");
    public static final ModelLayerLocation TART = register("tart");
    public static final ModelLayerLocation TART_HEAD = register("tart_head");
    public static final ModelLayerLocation TERROR = register("terror");
    public static final ModelLayerLocation TREMBLER = register("trembler");
    public static final ModelLayerLocation UMBER_SPIDER = register("umber_spider");
    public static final ModelLayerLocation VOLT = register("volt");
    public static final ModelLayerLocation VOLT_CHARGED = register("volt_charged");
    public static final ModelLayerLocation WHIZZ = register("whizz");
    public static final ModelLayerLocation WHIZZ_HEAD = register("whizz_head");

    public static final ModelLayerLocation BONE_ARMOR = register("bone_armor");
    public static final ModelLayerLocation DEEPWOVEN_ARMOR = register("deepwoven_armor");
    public static final ModelLayerLocation EMERALD_ARMOR = register("emerald_armor");
    public static final ModelLayerLocation MOON_SHOES = register("moon_shoes");
    public static final ModelLayerLocation RECON_KNIGHT_ARMOR = register("recon_knight_armor");
    public static final ModelLayerLocation SLUG_BARON_ARMOR = register("slug_baron_armor");
    public static final ModelLayerLocation STONE_ARMOR = register("stone_armor");
    public static final ModelLayerLocation WOODEN_ARMOR = register("wooden_armor");

    public static final ModelLayerLocation BLASTER = register("blaster");
    public static final ModelLayerLocation LASER_BOLT = register("laser_bolt");

    private static ModelLayerLocation register(String id) {
        return new ModelLayerLocation(OpposingForce.location(id), "main");
    }
}
