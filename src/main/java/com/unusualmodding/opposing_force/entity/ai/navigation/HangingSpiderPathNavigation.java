package com.unusualmodding.opposing_force.entity.ai.navigation;

import com.unusualmodding.opposing_force.entity.HangingSpider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class HangingSpiderPathNavigation extends SmoothGroundPathNavigation {

    private final HangingSpider spider;

    public HangingSpiderPathNavigation(HangingSpider spider, Level level) {
        super(spider, level);
        this.spider = spider;
    }

    @Override
    public void tick() {
        this.tick++;
    }

    @Override
    public boolean moveTo(double x, double y, double z, double speed) {
        this.spider.getMoveControl().setWantedPosition(x, y, z, speed);
        return true;
    }

    @Override
    public boolean moveTo(Entity entity, double speed) {
        this.spider.getMoveControl().setWantedPosition(entity.getX(), entity.getY(), entity.getZ(), speed);
        return true;
    }
}