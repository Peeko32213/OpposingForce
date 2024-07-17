package com.peeko32213.hole.common.entity.util.helper;

import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class MathHelpers {

    public static Vec2 OrizontalAimVector(Vec3 aim){

        Vec2 vec = new Vec2((float)(aim.x/(1-Math.abs(aim.y))), (float)(aim.z/(1-Math.abs(aim.y))));

        return vec;
    }

    public static Vec3 AimVector(Vec3 pos, Vec3 targetPos){

        double d = Math.sqrt((targetPos.x - pos.x)*(targetPos.x - pos.x) + (targetPos.z - pos.z)*(targetPos.z - pos.z) + (targetPos.y - pos.y)*(targetPos.y - pos.y));


        Vec3 aim = new Vec3((targetPos.x - pos.x)/d, (targetPos.y - pos.y)/d, (targetPos.z - pos.z)/d);


        return aim;


    }

}